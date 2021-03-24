package com.grlife.rela_prog.service;

import com.grlife.rela_prog.repositiory.ApiRepository;
import com.grlife.rela_prog.utils.MessageException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ApiService {

    private final ApiRepository apiRepository;

    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }


    public Map<String, Object> getItemInfo(Map<String, Object> data) throws MessageException, IOException {
        return apiRepository.getItemInfo(data);
    }

    public List<Map<String, Object>> selectChart(List<Map<String, Object>> list) throws IOException {
        return apiRepository.selectChart(list);
    }

    public Map<String, Object> getInfo(Map<String, Object> data) throws MessageException, IOException {
        return apiRepository.getInfo(data);
    }
}
