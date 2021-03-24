package com.grlife.rela_prog.service;

import com.grlife.rela_prog.repositiory.RelaRepository;
import com.grlife.rela_prog.utils.MessageException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelaService {

    private final RelaRepository relaRepository;

    public RelaService(RelaRepository relaRepository) {
        this.relaRepository = relaRepository;
    }

    public List<Map<String, Object>> selectDiagram(HashMap hashMap) {
        return relaRepository.selectDiagram();
    }

    public List<Map<String, Object>> selectRelationGroups(Map<String, Object> data) {
        return relaRepository.selectRelationGroups(data);
    }

    public void saveRelationGroup(Map<String, Object> data) throws MessageException, IOException {
        relaRepository.saveRelationGroup(data);
    }

    public void deleteRelationGroup(Map<String, Object> data) throws MessageException, IOException {
        relaRepository.deleteRelationGroup(data);
    }



}
