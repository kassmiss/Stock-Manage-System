/**
 * Project     : Enzin_web_app
 * FileName    : PRExceptionUtil.java
 * Package     : 
 * Company     : ZIN Corporation
 * Creator     : 김경원
 * Create-Date : 2019. 12. 11. 오후 5:37:09
 * REMARK      :
 * HISTORY     :
 * Modify-Date   Modifier                 변경내용
 * ----------------------------------------------
 * 2019. 12. 11.       김경원         신규작성       	
 */
package com.grlife.rela_prog.utils;

import javax.servlet.http.HttpSession;

public class ExceptionUtil {
	public static void setExceptionMessage(AjaxResuiltVO ret, Exception e) {
		ret.setResultError();
		ret.setResultMessage(e.getMessage());
	}

	public static void setMessageExceptionStatus(AjaxResuiltVO ret, MessageException e) {
		ret.setResultMessageError();;
		ret.setResultMessage(e.getMessage());
	}

}
