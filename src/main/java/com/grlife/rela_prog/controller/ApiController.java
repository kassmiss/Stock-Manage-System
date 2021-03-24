package com.grlife.rela_prog.controller;

import com.grlife.rela_prog.service.ApiService;
import com.grlife.rela_prog.service.RelaService;
import com.grlife.rela_prog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApiService apiService;
    private final RelaService relaService;

    public ApiController(ApiService apiService, RelaService relaService) {
        this.apiService = apiService;
        this.relaService = relaService;
    }

    @RequestMapping("/api/getItemInfo")
    @ResponseBody
    public AjaxResuiltVO getItemInfo(@RequestBody ProcessVO param) {
        Map<String, Object> data = param.getData();
        ParamUtil.setInt2Double(data);

        AjaxResuiltVO ret = new AjaxResuiltVO();

        try {

            Map<String, Object> result = apiService.getItemInfo(data);

            /* 등록 여부 체크 *//*
            List<Map<String, Object>> list = relaService.selectItems(result);

            if(list.size() > 0) {
                throw new MessageException("이미 존재합니다.");
            }*/

            ret.setResultSuccess();
            ret.setData(result);

        } catch (MessageException e) {
            logger.error(e.getMessage(), e);
            ExceptionUtil.setMessageExceptionStatus(ret, e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;

    }

    @RequestMapping("/api/getInfo")
    public String dashboard(Model model, @RequestParam String code) {
        Map<String, Object> map = new HashMap();

        try {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("code", code);

            map = apiService.getInfo(data);

            model.addAttribute("map", map);
        } catch(IOException e) {
            map.put("color", "gray");
            map.put("name", "");
            map.put("code", "");
            map.put("price", "");
            map.put("upDown", "");
            map.put("cPrice", "");
            map.put("cPer", "");
            map.put("error", e.getMessage());
            model.addAttribute("map", map);
        } catch (MessageException e) {
            map.put("color", "gray");
            map.put("name", "");
            map.put("code", "");
            map.put("price", "");
            map.put("upDown", "");
            map.put("cPrice", "");
            map.put("cPer", "");
            map.put("error", e.getMessage());
            model.addAttribute("map", map);
        }

        return "page/info";
    }

}
