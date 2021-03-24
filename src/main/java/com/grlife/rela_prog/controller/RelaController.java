package com.grlife.rela_prog.controller;

import com.google.gson.Gson;
import com.grlife.rela_prog.service.ApiService;
import com.grlife.rela_prog.service.RelaService;
import com.grlife.rela_prog.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RelaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RelaService relaService;

    private final ApiService apiService;

    public RelaController(RelaService relaService, ApiService apiService) {
        this.relaService = relaService;
        this.apiService = apiService;
    }

    /* 대시보드 */
    @RequestMapping("/m/items")
    public String dashboard(Model model) {

        try {

            List<Map<String, Object>> list = relaService.selectDiagram(new HashMap());
            List<Map<String, Object>> sList = apiService.selectChart(list);

            model.addAttribute("list", sList);

        } catch(IOException e) {

        }

        return "page/manage/manage_items";
    }

    /* 연결 그룹 */
    @RequestMapping("/m/relation_group")
    public String goRelationGroup(Model model) {

        List<Map<String, Object>> list = relaService.selectRelationGroups(new HashMap());
        model.addAttribute("list", list);

        return "page/manage/manage_relation_group";
    }

    @PostMapping("/m/relation_group/save")
    @ResponseBody
    public AjaxResuiltVO saveRelationGroup(@RequestBody ProcessVO param) {
        Map<String, Object> data = param.getData();
        ParamUtil.setInt2Double(data);

        AjaxResuiltVO ret = new AjaxResuiltVO();

        try {
            relaService.saveRelationGroup(data);
            ret.setResultSuccess();
        } catch (MessageException e) {
            logger.error(e.getMessage(), e);
            ExceptionUtil.setMessageExceptionStatus(ret, e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @RequestMapping("/m/relation_group/delete")
    @ResponseBody
    public AjaxResuiltVO deleteRelationGroup(@RequestBody ProcessVO param) {
        Map<String, Object> data = param.getData();
        ParamUtil.setInt2Double(data);

        AjaxResuiltVO ret = new AjaxResuiltVO();

        try {
            relaService.deleteRelationGroup(data);
            ret.setResultSuccess();
        } catch (MessageException e) {
            logger.error(e.getMessage(), e);
            ExceptionUtil.setMessageExceptionStatus(ret, e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /* 연결 그룹 */
    @RequestMapping("/view/items")
    public String viewPage(Model model) {
        try {
            List<Map<String, Object>> list = relaService.selectDiagram(new HashMap());
            List<Map<String, Object>> sList = apiService.selectChart(list);

            System.out.println(new Gson().toJson(sList));

            model.addAttribute("list", sList);
        } catch(IOException e) {

        }

        return "page/view/view_item";
    }

}
