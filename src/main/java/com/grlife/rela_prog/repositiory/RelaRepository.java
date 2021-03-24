package com.grlife.rela_prog.repositiory;

import com.grlife.rela_prog.utils.MessageException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RelaRepository {

    List<Map<String, Object>> selectDiagram();
    List<Map<String, Object>> selectRelationGroups(Map<String, Object> data);
    void saveRelationGroup(Map<String, Object> data) throws MessageException, IOException;
    void deleteRelationGroup(Map<String, Object> data) throws MessageException, IOException;

}
