package com.grlife.rela_prog.repositiory;

import com.grlife.rela_prog.utils.MessageException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ApiRepository {

    Map<String, Object> getItemInfo(Map<String, Object> data) throws MessageException, IOException;

    List<Map<String, Object>> selectChart(List<Map<String, Object>> list) throws IOException ;

    Map<String, Object> getInfo(Map<String, Object> data) throws MessageException, IOException;
}
