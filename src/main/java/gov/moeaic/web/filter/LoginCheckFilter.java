package gov.moeaic.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dasin.tools.dTools;
import org.dasin.tools.dWebTools;

import gov.moeaic.sql.bean.User;
import gov.moeaic.sql.dao.UserDAO;


public class LoginCheckFilter implements Filter{
	List<String> ip_list;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		
		HashMap<String, String> errors = new HashMap<String, String>(); 

		String ip = dWebTools.getClientIpAddr(request);
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String captcha = request.getParameter("captcha");
		String captcha_sen = dTools.trim((String)session.getAttribute("captcha"));
		
		if(account != null && password != null){
			User user = null;

			if(captcha_sen.equalsIgnoreCase(captcha)){
				user = UserDAO.login(account, password);
			}
			
			if(user != null){
				session.setAttribute("user", user);
				System.out.println("Console Logged In From : " + ip);
			}else{
				errors.put("inputER", "輸入錯誤，請重新輸入！");
				session.setAttribute("errors", errors);
			}
		}
		
		User user = (User)session.getAttribute("user");
		if(!request.getRequestURI().endsWith("login.jsp")){
			if(user == null){
				response.sendRedirect(request.getContextPath() + "/console/login.jsp");
				return;
			}
		}else{
			//Allow login only for valid IPs.
			if(!ip_list.contains("*")){
				boolean valid = false;
				
				for(String ip_range : ip_list){
					if(ip.startsWith(ip_range)){
						valid = true;
						break;
					}
				}
				
				if(!valid){
					response.sendRedirect(request.getContextPath() + "/error-page.jsp");
					return;
				}
			}
		}
		
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ip_list = Arrays.asList(config.getServletContext().getInitParameter("IPList").split("[,\\s]+"));
	}
}
