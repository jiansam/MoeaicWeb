package org.dasin.tools;

import javax.servlet.http.*;

public class dWebTools {
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static void setResponseNoCache(HttpServletResponse  response){
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
	    long time = System.currentTimeMillis();
	    response.setDateHeader("Last-Modified", time);
	    response.setDateHeader("Date", time);
	    response.setDateHeader("Expires", 0);
	}
	
	public static String escapeXssSymbols(String inString){
//		return dTools.trim(inString)
//				.replace(">", "＞")
//				.replace("<", "＜")
//				.replace("'", "\uff07")
//				.replace("\"", "\uff02");
		return inString;
	}
}
