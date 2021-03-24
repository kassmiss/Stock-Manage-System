package com.grlife.rela_prog.repositiory;

import com.grlife.rela_prog.utils.MessageException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiRepositoryImpl implements ApiRepository {

    private final JdbcTemplate jdbcTemplate;

    public ApiRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Map<String, Object> getItemInfo(Map<String, Object> data) throws MessageException, IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        Boolean codeF = false;

        String url = "";
        Elements elem = null;

        if(!"".equals(data.get("code"))) codeF = true;

        if(codeF) url = "https://finance.naver.com/item/main.nhn?code=" + data.get("code");
        else url = "https://finance.naver.com/search/searchList.nhn?query=" + URLEncoder.encode((String) data.get("name"), "euc-kr");

        Document doc = Jsoup.connect(url).get();

        if(codeF) {
            elem = doc.select(".h_company .wrap_company h2 a");
        } else {
            elem = doc.select(".tbl_search .tit");
        }

        if(elem.size() == 0) {
            throw new MessageException("대상이 존재하지 않습니다.");
        }

        if(codeF) {
            result.put("code", data.get("code"));
            result.put("name", elem.get(0).text());
        } else {
            result.put("code", elem.first().select("a").attr("href").split("=")[1]);
            result.put("name", elem.first().text());
        }

        return result;

    }

    @Override
    public List<Map<String, Object>> selectChart(List<Map<String, Object>> list) throws IOException {

        String url = "https://finance.naver.com/item/main.nhn?code=";
        Document doc = null;
        Elements elem = null;

        List<Map<String, Object>> rList = new ArrayList();

        for(Map<String, Object> map : list) {

            String code = (String) map.get("code");
            String parent = (String) map.get("parent");

            map.put("upDown", "");
            map.put("price", "");
            map.put("cPrice", "");
            map.put("cPer", "");

            if(!"".equals(code) && !"".equals(parent)) {
                doc = Jsoup.connect(url+code).get();
                String price = doc.select(".today .no_today .blind").get(0).text();
                String cPrice = doc.select(".today .no_exday .blind").get(0).text();
                String cPer = doc.select(".today .no_exday .blind").get(1).text();

                Elements up = doc.select(".today .no_exday .no_up .plus");
                Elements down = doc.select(".today .no_exday .no_down .minus");

                String upDown = "";
                if(up.size() != 0) {
                    upDown = up.get(0).text();
                } else if(down.size() != 0) {
                    upDown = down.get(0).text();
                }

                map.put("upDown", upDown);
                map.put("price", price);
                map.put("cPrice", cPrice);
                map.put("cPer", cPer);

                System.out.println("upDown : " + upDown + ", price : " + price + ", cPrice : " + cPrice + ", cPer : " + cPer);

                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            rList.add(map);

        }

        return rList;
    }

    @Override
    public Map<String, Object> getInfo(Map<String, Object> data) throws MessageException, IOException {

        Map<String, Object> result = new HashMap<String, Object>();

        String url = "https://finance.naver.com/item/main.nhn?code=" + data.get("code");

        Document doc = Jsoup.connect(url).get();

        Elements elem = doc.select(".h_company .wrap_company h2 a");

        if(elem.size() == 0) {
            throw new MessageException("대상이 존재하지 않습니다.");
        }

        result.put("code", data.get("code"));
        result.put("name", elem.get(0).text());

        String price = doc.select(".today .no_today .blind").get(0).text();
        String cPrice = doc.select(".today .no_exday .blind").get(0).text();
        String cPer = doc.select(".today .no_exday .blind").get(1).text();

        Elements up = doc.select(".today .no_exday .no_up .plus");
        Elements down = doc.select(".today .no_exday .no_down .minus");

        String upDown = "";
        if(up.size() != 0) {
            upDown = up.get(0).text();
        } else if(down.size() != 0) {
            upDown = down.get(0).text();
        }

        result.put("upDown", upDown);
        result.put("price", price);
        result.put("cPrice", cPrice);
        result.put("cPer", cPer);

        if("+".equals(upDown)) {
            result.put("color", "red");
        } else if("-".equals(upDown)) {
            result.put("color", "blue");
        } else if("".equals(upDown)) {
            result.put("color", "gray");
        }

        result.put("error", "");

        return result;
    }

}

