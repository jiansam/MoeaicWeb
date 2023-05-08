package gov.moeaic.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.moeaic.web.bean.OptionManager;
import gov.moeaic.web.bean.URLManager;

public class CommonFilter implements Filter {
	boolean console = false;
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest http_request = (HttpServletRequest)request;
		HttpServletResponse http_response = (HttpServletResponse)response;
		
		if(console) {
			String uri_path = http_request.getRequestURI();
			uri_path = uri_path.indexOf("/") == -1 ? "" : uri_path.substring(0, uri_path.lastIndexOf("/"));
			if(uri_path.endsWith("/chinese") || uri_path.endsWith("/english")) {
				http_response.sendRedirect(http_request.getContextPath() + "/console");
				return;
			}
		}
		
		request.setCharacterEncoding("utf-8");
		http_response.setHeader("Content-Security-Policy", "default-src * 'unsafe-inline' 'unsafe-eval';frame-ancestors *");
//		HttpSession session = ((HttpServletRequest)request).getSession();
//		session.setAttribute("user", UserDAO.get(1));
		
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		URLManager urlManager = new URLManager();
		OptionManager optionManager = new OptionManager();
		ServletContext applicationContext = config.getServletContext();
		applicationContext.setAttribute("urlManager", urlManager);
		applicationContext.setAttribute("optionManager", optionManager);
		
		console = "true".equalsIgnoreCase(applicationContext.getInitParameter("console"));
	}
}
