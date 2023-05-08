package gov.moeaic.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dasin.io.StreamCopier;
import org.dasin.tools.dTools;

import gov.moeaic.sql.SQL;

public class ImageSynchronizeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		int id = dTools.parseInt(request.getParameter("id"));
		
		if(id == 0) {
			return;
		}
		
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement stmt = sqltools.prepare(
				"SELECT id, filepath, file_content FROM TB_Images WHERE id = ? "
			);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()){
				File file = new File(request.getServletContext().getRealPath(""), dTools.trim(rs.getString("filepath")));
				
				if(!file.exists()) {
					FileOutputStream fos = new FileOutputStream(file);
					StreamCopier.copy(rs.getBinaryStream("file_content"), fos);
					fos.close();
				}
			}

			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(sqltools != null){
					sqltools.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
