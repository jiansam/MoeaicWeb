package gov.moeaic.sql.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.dasin.io.StreamCopier;
import org.dasin.tools.dTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.Images;

public class ImagesDAO {
	public static HashMap<String, String> upload(HttpServletRequest request){
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		if(ServletFileUpload.isMultipartContent(request)){
			SQL sqltools = new SQL();

			try{
				ServletFileUpload sfu = new ServletFileUpload();
				sfu.setHeaderEncoding("UTF-8");
				
				FileItemIterator iter = sfu.getItemIterator(request);
				PreparedStatement statement = null;

				while(iter.hasNext()){
					FileItemStream fis = iter.next();
					InputStream is = fis.openStream();

					// Note: normal form fields MUST be placed before file input.
					if(fis.isFormField()){
						parameters.put(fis.getFieldName(), Streams.asString(is, "utf-8"));
					}else{
						ServletContext context = request.getServletContext();
						
						if(!dTools.isEmpty(fis.getName())
							&& dTools.trim(context.getMimeType(fis.getName())).startsWith("image/")){
							parameters.put("filepath", context.getInitParameter("images_upload_directory") + "/" 
									+ newFilename(fis.getName()));
							
							File outFile = new File(context.getRealPath(parameters.get("filepath")));
							FileOutputStream fos = new FileOutputStream(outFile);
							StreamCopier.copy(is, fos);
							fos.flush();
							fos.getFD().sync();
							fos.close();
							
							Thread.sleep(5000);
						}
						
						int id = dTools.parseInt(parameters.get("id"));
						if(id == 0){
							statement = sqltools.prepare(
								"INSERT INTO TB_images (filepath, description, uploadtime "
								+ ") VALUES (?, ?, GETDATE()) ", new String[]{"id"}
							);
							
							statement.setString(1, dTools.trim(parameters.get("filepath")));
							statement.setString(2, parameters.get("description"));
							
							statement.executeUpdate();
							
							ResultSet rs = statement.getGeneratedKeys();
							
							if(rs.next()){
								parameters.put("id", String.valueOf(id = rs.getInt(1)));
							}
							rs.close();
							statement.close();
						}else{
							Images image = get(id);
							
							if(parameters.get("filepath") == null){
								parameters.put("filepath", image.getFilepath());
							}else{
								if(!dTools.isEmpty(image.getFilepath())){
									File file = new File(request.getServletContext().getRealPath(image.getFilepath()));
									file.delete();
								}
							}
							
							statement = sqltools.prepare(
								"UPDATE TB_images SET "
								+ "filepath = ?, "
								+ "description = ?, "
								+ "uploadtime = GETDATE() "
								+ "WHERE id = ? "
							);
							
							statement.setString(1, parameters.get("filepath"));
							statement.setString(2, parameters.get("description"));
							statement.setInt(3, id);
							
							statement.executeUpdate();
							statement.close();
						}
						
						//寫入檔案並呼叫遠端同步
						if(!dTools.isEmpty(fis.getName())
							&& dTools.trim(context.getMimeType(fis.getName())).startsWith("image/")){
							
							FileInputStream file_is = new FileInputStream(new File(context.getRealPath(parameters.get("filepath"))));
							
							statement = sqltools.prepare(
								"UPDATE TB_images SET "
								+ "file_content = ? "
								+ "WHERE id = ? "
							);
							
							statement.setBinaryStream(1, file_is);
							statement.setInt(2, id);
							
							statement.executeUpdate();
							statement.close();
							
							file_is.close();
							
							synchronize(request, id);
						}
					}

					is.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(sqltools != null){
						sqltools.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return parameters;
	}
	
	public static Images get(int id){
		Images result = new Images();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"SELECT id, filepath, description, uploadtime FROM TB_images WHERE id = ? "
			);
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				result.setId(rs.getInt("id"));
				result.setFilepath(rs.getString("filepath"));
				result.setDescription(rs.getString("description"));
				result.setUploadtime(rs.getString("uploadtime"));
			}
			rs.close();
			statement.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sqltools.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static ArrayList<Images> list(){
		ArrayList<Images> result = new ArrayList<Images>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement stmt = sqltools.prepare(
				"SELECT id, filepath, description, uploadtime "
				+ "FROM TB_images ORDER BY uploadtime DESC "
			);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				Images image = new Images();
				image.setId(rs.getInt("id"));
				image.setFilepath(rs.getString("filepath"));
				image.setDescription(rs.getString("description"));
				image.setUploadtime(rs.getString("uploadtime"));

				result.add(image);
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
		
		return result;
	}
	
	public static void delete(HttpServletRequest request, int id){
		SQL sqltool = new SQL();

		try{
			Images image = get(id);
			if(!dTools.isEmpty(image.getFilepath())){
				File file = new File(request.getServletContext().getRealPath(image.getFilepath()));
				file.delete();
			}
			
			PreparedStatement stmt = sqltool.prepare("DELETE FROM TB_images WHERE id = ? ");
			stmt.setInt(1, id);

			stmt.executeUpdate();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(sqltool != null){
					sqltool.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public static void synchronize(HttpServletRequest request, int id) {
		try {
			HttpURLConnection conn = (HttpURLConnection)(
				new URL(request.getServletContext().getInitParameter("SynchronizeURL") + "/imagesynchronize?id=" + id).openConnection());
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
//			System.out.println(request.getServletContext().getInitParameter("SynchronizeURL") + "/imagesynchronize?id=" + id);
//			is.readAllBytes();
			is.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void recoverFromDB() {
		
	}
	
	public static String newFilename(String filename){
		if(filename.lastIndexOf(".") != -1){
			return UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		}else{
			return UUID.randomUUID().toString();
		}
	}
}
