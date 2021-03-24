package com.grlife.rela_prog.utils;

import java.util.Map;

public class ProcessVO {
	private Map<String, Object> data;
	private Map<String, String> pgmInfo;
	private Map<String, String> addParam;
	private Map<String, Object> pivotParam;


	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getData() {
        return data;
    }

	public Map<String, String> getPgmInfo() {
		return pgmInfo;
	}


	public void setPgmInfo(Map<String, String> pgmInfo) {
		this.pgmInfo = pgmInfo;
	}


	public Map<String, String> getAddParam() {
		return addParam;
	}


	public void setAddParam(Map<String, String> addParam) {
		this.addParam = addParam;
	}

}
