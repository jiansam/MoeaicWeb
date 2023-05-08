package gov.moeaic.sql.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import gov.moeaic.sql.bean.Business_category;
import gov.moeaic.sql.dao.Business_categoryDAO;


public class UploadExcelServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		InputStream is = null;
		ArrayList<Business_category> list = null;
		if(ServletFileUpload.isMultipartContent(request)){
			
			ServletFileUpload sfu = new ServletFileUpload();
			sfu.setHeaderEncoding("UTF-8");
			
			try {
				// 開啟 inputStream
				FileItemIterator iter = sfu.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream fis = iter.next();
					is = fis.openStream();
					
					
					// 取得Excel工作簿
					Workbook wb = WorkbookFactory.create(is);
					
					
					// 取得分頁，取得每一行的資料，再放入LIST中
					list = new ArrayList<>();
					for(int i=0; i<wb.getNumberOfSheets(); i++){
						Sheet sheet = wb.getSheetAt(i);
						String name = sheet.getSheetName();
//						System.out.println("工作表="+name);
//						System.out.println("幾row="+ sheet.getLastRowNum());
						
						// 取得每一行
						for(int r=1; r<=sheet.getLastRowNum(); r++){
							Row row = sheet.getRow(r);
							if(row == null){
								continue;
							}
							if(row.getCell(0) == null){
								continue;
							}
//							System.out.println(row.getCell(0) == null);
						    String year = row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC ?
						    		String.valueOf(row.getCell(0).getNumericCellValue()) :
						    		ToolsUtil.trim(row.getCell(0).getStringCellValue());
//						    System.out.println("年度="+year );
						    if(year.indexOf(".") != -1){
						    	int num = year.indexOf(".");
						    	year = year.substring(0, num);
						    }
						    double amount = row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC ? 
						    		row.getCell(1).getNumericCellValue() : 0.0;
						    		
						    Business_category bc = new Business_category();
						    bc.setType(name);
						    bc.setYear(year);
						    bc.setAmount(amount);
						    bc.setSeq(i);
						    list.add(bc);
						}
					}					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(is!=null){
					try {
						is.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			String result = Business_categoryDAO.insert(list);
			
			response.setContentType("text/plain; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();
			return;
		}
	}

//	public static void main(String[] args)
//	{
//		ArrayList<Business_category> list = null;
//		BufferedInputStream bif = null;
//		try {
//			bif = new BufferedInputStream(new FileInputStream(new File("C:/Users/IBT-004/Downloads/業務統計-統計資料.xlsx")));
//			
//			
//			// 取得Excel工作簿
//			Workbook wb = WorkbookFactory.create(bif);
//			
//			// 取得分頁，取得每一行的資料，再放入LIST中
//			list = new ArrayList<>();
//			for(int i=0; i<wb.getNumberOfSheets(); i++){
//				Sheet sheet = wb.getSheetAt(i);
//				String name = sheet.getSheetName();
//				
//				// 取得每一行
//				for(int r=1; r<=sheet.getLastRowNum(); r++){
//					Row row = sheet.getRow(r);
//					if(row == null){
//						continue;
//					}
//				    String year = row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC ?
//				    		String.valueOf(row.getCell(0).getNumericCellValue()) :
//				    		ToolsUtil.trim(row.getCell(0).getStringCellValue());
//				    double amount = row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC ? 
//				    		row.getCell(1).getNumericCellValue() : 0.0;
//				    		
//				    Business_category bc = new Business_category();
//				    bc.setType(name);
//				    bc.setYear(year);
//				    bc.setAmount(amount);
//				    list.add(bc);
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(bif!=null){
//				try {
//					bif.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		System.out.println(list);
//
//	}

}
