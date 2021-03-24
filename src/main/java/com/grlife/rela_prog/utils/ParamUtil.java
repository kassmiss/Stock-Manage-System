package com.grlife.rela_prog.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParamUtil {
	public static void setInt2Double(Map<String, Object> data) {
		if(data == null) data = new HashMap<String, Object>();
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			Object val = data.get(key);
			if(val != null && val instanceof Number) {
				data.put(key, ((Number)val).toString());
			}
		}
	}
	public static void setInt2Double(List<Map<String, Object>> data) {
		for(Map<String, Object> m : data) {
			setInt2Double(m);
		}
	}

	
	public static void mergeParam(Map<String, Object> data, Map<String, String> param) {
		if(data == null) return;
		if(param == null) return;
		Iterator<String> it = param.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			data.put(key, param.get(key));
		}
	}
	
	public static void mergeParam2(Map<String, Object> data, Map<String, Object> param) {
		if(data == null) return;
		if(param == null) return;
		Iterator<String> it = param.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			data.put(key, param.get(key));
		}
	}
	
	
	public static void mergeParam(List<Map<String, Object>> data, Map<String, String> param) {
		if(data == null) return;
		if(param == null) return;
		
		for(Map<String, Object> d : data) {
			mergeParam(d, param);
		}
	}

	private static DecimalFormat formatter = null;
	
	public static void mergeParam(Map<String, Object> paramData, List<Map<String, Object>> data) {
		for(int i = 0; i < data.size(); i++) {
			Map<String, Object> t = data.get(i);
			Iterator<String> it = t.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				Object o = paramData.get(key);
				Object d = t.get(key);
				
				String s;
				if(o == null || !(o instanceof String)) {
					s = "";
				} else {
					s = (String)o;
					s += "^";
				}

				if(d == null) {
					
				}else if(d instanceof String) {
					s += (String)d;
				}else if (d instanceof Double) {
					if(formatter == null) {
						formatter = new DecimalFormat();
						formatter.setGroupingUsed(false);
						formatter.setMaximumFractionDigits(20); 
						formatter.setMaximumIntegerDigits(20);
					}
					s += formatter.format((Double)d);
				} else if(d instanceof Number) {
					if(formatter == null) {
						formatter = new DecimalFormat();
						formatter.setGroupingUsed(false);
						formatter.setMaximumFractionDigits(20); 
						formatter.setMaximumIntegerDigits(20);
					}					
					s += formatter.format(((Number)d).doubleValue());
				} else {
					s += d.toString();
				}
				
				paramData.put(key, s);
			}			
		}
	}
}
