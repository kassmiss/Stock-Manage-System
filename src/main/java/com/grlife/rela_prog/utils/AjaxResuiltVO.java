package com.grlife.rela_prog.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AjaxResuiltVO {
	private String resultCode = "E";
	private String resultMessage = null;
	private List Data = null;
	private Map Param = null;

	
	public Map getParam() {
		return Param;
	}

	public void addParam(String key, Object val) {
		if(Param == null) Param = new HashMap<Object, Object>();
		Param.put(key, val);
	}

	public void addParam(Map m) {
		if(Param == null) Param = new HashMap();
		Iterator it = m.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
			Object val = m.get(key);
			Param.put(key, val);
		}
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultSuccess() {
		this.resultCode = "0";
	}

	public void setResultError() {
		this.resultCode = "E";
	}

	public void setResultAuthError() {
		this.resultCode = "A";
	}

	public void setResultMessageError() {
		this.resultCode = "M";
	}

	public String getResultMessage() {
		return resultMessage;
	}


	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List getData() {
		return Data;
	}


	public void setData(List Data) {
		this.Data = Data;
	}

	public void setData(Map Data) {
		List t = new ArrayList();
		if(Data != null) {
			t.add(Data);
		}
		this.Data = t;
	}

}
