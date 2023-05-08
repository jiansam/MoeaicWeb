package gov.moeaic.sql.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.dasin.cryptography.dCipher;
import gov.moeaic.sql.SQL;

public class FileLoadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		super.doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String id = "";
		String doThing = "";
		SQL sqltools = new SQL();
		ServletOutputStream out = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		byte[] fileBytes = new byte[0];
		
		try {
			id = ( request.getParameter("id") == null ) ? "" : request.getParameter("id");
			doThing = ( request.getParameter("do") == null ) ? "" : request.getParameter("do");
			if(id.length() > 0){

				String sql = "";
				if("BP".equalsIgnoreCase(doThing)){
					sql = "SELECT * FROM TB_BusinessPubFile WHERE id = ?";
				}else if("OD".equalsIgnoreCase(doThing)){
					sql = "SELECT * FROM TB_OpenDataFile WHERE id = ?";
				}else{
					sql = "SELECT * FROM TB_PagesNewsFile WHERE id = ?";
				}
				stmt = sqltools.prepare(sql);
				stmt.setInt(1, Integer.valueOf(dCipher.decrypt(id)));
				rs = stmt.executeQuery();
				
				String filename = "";
				if(rs.next()){
					filename = rs.getString("file_name");
					fileBytes = rs.getBytes("file_content");
				}
				
				if(fileBytes.length > 0){
					if(request.getProtocol().equalsIgnoreCase("HTTP/1.0")){
						response.setHeader("Pragma", "no-cache");
					}else if(request.getProtocol().equalsIgnoreCase("HTTP/1.1")){
						response.setHeader("Cache-Control", "no-cache, must-revalidate");
					}
					
					try {
						out = response.getOutputStream();					
						response.setContentType("application/x-download");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, "utf-8") + "\"" );
						response.setContentLength(fileBytes.length);
						out.write(fileBytes);
						out.flush();
					}catch(ClientAbortException cae){
					}finally {
						try {
							out.close();
						}catch(ClientAbortException cae){
						}
					}
				}
				
			}
		} catch(NumberFormatException ne) {
			System.out.println("fileId="+id);
		} catch (Exception e) {
			System.out.println("fileId="+id);
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/error-page.jsp");
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			fileBytes = null;
		}
	}
}
