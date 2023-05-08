package gov.moeaic.sql.controller;

import java.awt.Color;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dasin.tools.dWebTools;

import com.google.gson.Gson;

import gov.moeaic.sql.bean.Business_category;
import gov.moeaic.sql.bean.Option;
import gov.moeaic.sql.dao.PagesService;
import gov.moeaic.web.bean.OptionManager;
import gov.moeaic.web.tool.CSVTools;

public class FrontExcelCategoryServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	Map<String, String> month_en = null;
	ArrayList<Business_category> list = null;
	String lang = null;
	String type = null;
	
	@Override
	public void init() throws ServletException
	{
		month_en = new HashMap<>();
		month_en.put("01", "January");
		month_en.put("02", "February");
		month_en.put("03", "March");
		month_en.put("04", "April");
		month_en.put("05", "May");
		month_en.put("06", "June");
		month_en.put("07", "July");
		month_en.put("08", "August");
		month_en.put("09", "September");
		month_en.put("10", "October");
		month_en.put("11", "November");
		month_en.put("12", "December");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int seq = ToolsUtil.parseInt(request.getParameter("seq"));
		lang = request.getParameter("lang");
		OptionManager optionManager = (OptionManager)request.getServletContext().getAttribute("optionManager");
		
		PagesService psService = new PagesService();
		list = psService.get_BSCT(seq);
		String last_year = null;
		String end_month = null;
		String end_month_range = null;
		
		ArrayList<Business_category> toBeRemoved = new ArrayList<>(); 
		double amount_e = 0;
		String year_e = "";
		if(list!=null && !list.isEmpty()){
			
			last_year = list.get(list.size()-1).getYear();
			
			// 如果有不完整年（201601 201602 201603）就加上截止月份，excel也要一份range月份
			if(last_year.length() > 4){
				
				type = list.get(0).getType();
				if("ch".equalsIgnoreCase(lang)){
					end_month = type + " (截至 " + last_year.substring(0, 4) + " 年 " + last_year.substring(4) + " 月止)";
					end_month_range = "(截至" + last_year.substring(4) + "月)";
				}else{
					// 若option內有符合的中文，就可以取出對應的英文，若沒有就用原來的中文顯示
					Option option = optionManager.getEnName("business_category", type);
					type = (option == null) ? list.get(0).getType() : option.getEname();
					end_month = type + "( up to  " + month_en.get(last_year.substring(4)) + " " + last_year.substring(0, 4) + " )";
					end_month_range = "(up to " + month_en.get(last_year.substring(4)) + ")";
				}

			}else{
			// 如果都是完整年，就只要秀出type名稱即可
				type = list.get(0).getType();
				if("ch".equalsIgnoreCase(lang)){
					end_month = type;
					end_month_range = "";
				}else{
					// 若option內有符合的中文，就可以取出對應的英文，若沒有就用原來的中文顯示
					Option option = optionManager.getEnName("business_category", type);
					type = (option == null) ? list.get(0).getType() : option.getEname();
					end_month = type;
					end_month_range = "";
				}
			}
			
			
			for(Business_category bean : list){
//				String year = "";
				
				/*  未滿一年：
				 *  取出年份 - 前4個字
				 *  金額加總 - 未滿一年的每個月資料（放入一個LIST等下刪除），並結算出輸入總計資料
				 */
				if(bean.getYear().length() > 4){
					year_e = bean.getYear().substring(0,4);
					amount_e = amount_e + bean.getAmount();
					toBeRemoved.add(bean);
				}
//				// 如果是一年的就只要列出年份
//				else{
//					if("ch".equalsIgnoreCase(lang)){
//						year = String.valueOf(bean.getYear());
//					}else{
//						year = String.valueOf(bean.getYear());
//					}
//				}
//				bean.setYear(year);
			}
		}
		
		
		/* 看看是要出網頁還是出excel檔案，會影響之後的list資料
		 * 如果是出excel，可以先把最後一年(截至X月)先放入bean裡面，就不用之後再組合了
		 */
		String doThing = request.getParameter("do");
		if(doThing != null && "dwn".equalsIgnoreCase(doThing)){
			
			/* 出excel報表  */
			if(toBeRemoved != null && !toBeRemoved.isEmpty()){
				list.removeAll(toBeRemoved);
				Business_category end_bean = new Business_category();
				end_bean.setYear(year_e + end_month_range);
				end_bean.setAmount(amount_e);
				list.add(end_bean);
			}
			
			tobeExcel(request, response);
			return;
			
		}else{
			
			/*  如果 toBeRemoved 不是null也不是空的
			 *  在資料的LIST內刪除 toBeRemoved（未滿一年的每個月資料
			 *  在LIST內加入一筆（最後一年 + range & 總計資料）
			 */
			if(toBeRemoved != null && !toBeRemoved.isEmpty()){
				list.removeAll(toBeRemoved);
				Business_category end_bean = new Business_category();
				end_bean.setYear(year_e);
				end_bean.setAmount(amount_e);
				list.add(end_bean);
			}
			
			// ["type":"僑外來臺投資" , "first_year":first_year , "last_year":last_year] 
			Gson gson = new Gson();
			String listToJson = gson.toJson(list);
			request.setAttribute("typeforTitle", type);
			request.setAttribute("type", end_month);
			request.setAttribute("end_month_range", end_month_range);
			request.setAttribute("list", listToJson);
			request.setAttribute("excel", list);
			
			/*  下拉選單：先取出後台update了幾個excel工作頁名稱
			 *  再去option尋找 "business_category"分類下，該中文的英文名稱
			 *  如果沒有英文名稱，仍以中文名稱替代顯示
			 */
			ArrayList<Business_category> list2 = psService.get_BSCTType(); 
			for(Business_category temp : list2){
				Option option = optionManager.getEnName("business_category", temp.getType());
				temp.setType_en(option == null ? temp.getType() : option.getEname());
			}
			request.setAttribute("BSCT_type", list2); //下拉選單用
			request.setAttribute("seq", seq); //下拉選單用
			
			
			if("ch".equalsIgnoreCase(lang)){
				request.getRequestDispatcher("/chinese/business_category.jsp").forward(request, response);		
			}else{
				request.getRequestDispatcher("/english/business_category.jsp").forward(request, response);
			}
		}//end else
		
	}

	private void tobeExcel(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream out = null;
		try{
			/* 有中文字的檔名要先處理 */
			String h1 = null;
			if("ch".equalsIgnoreCase(lang)){
				h1 = type + "_業務統計報表";
			}else{
				h1 = type + "_StatisticsList";
			}
			
			/* 設定瀏覽器不要cache舊資料 */
			dWebTools.setResponseNoCache(response);
//			response.setContentType("application/vnd.ms-excel; charset=utf-8"); //這種寫法在iphone會開啟失敗，改下方寫法
			response.setContentType("application/x-download; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" 
																+ URLEncoder.encode(h1, "utf-8") + ".csv" + "\"");
			
		/* 1.產生一個EXCEL =============================================================================*/
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet(type);
			int rowNum = 0;
			

			
		/* 2.標題 =============================================================================*/

			Row header_row1 = sheet.createRow(rowNum++); //合併儲存格
			int header1_colNum = 0;
			/* 設定字型  - setFont(int style, Workbook wb); */
			Font hearder_font = setFont(0, wb); //int style, wb

			
			/* 標題群組:中文 或 英文
			 */
			Map<String, String[]> colName = new HashMap<>();
			colName.put("ch", new String[]{"年度","金額  (美金千元 )"});
			colName.put("en", new String[]{"Years","Amount(US$1,000)"});
			
			/* 設定Cellstyle - setCellStyle(int style, Workbook wb, int r, int g, int b, Font font); */
			CellStyle headerStyle_yellow = setCellStyle(2, wb, 255, 242, 204, hearder_font); //淺黃色
			
			for(String name : colName.get(lang)){
				createCell(0, header1_colNum++, header_row1, headerStyle_yellow, name);
			}

			
			
		/* 3.內文 =============================================================================*/
			
			/* 設定字型  - setFont(int style, Workbook wb); */
			Font data_font = setFont(1, wb);
			
			/* 設定Cellstyle - setCellStyle(int style, wb, r, g, b, font); */
			CellStyle dataStyle_center = setCellStyle(1, wb, 0, 0, 0, data_font); //水平對齊
			
			
			for(Business_category bean : list){
				Row data_row = sheet.createRow(rowNum++);
				int data_colNum = 0;
				
				createCell(0, data_colNum++, data_row, dataStyle_center, bean.getYear());  //年度
				createCell(2, data_colNum++, data_row, dataStyle_center, bean.getAmount());  //金額  (美金千元 )
				
			}
			
			
		/* 4.處理格式設定 ========================================================================*/
			
			
			//凍結列 int colSplit, int rowSplit：左側從第0開始切割，上方從第1列開始切割 （index從0開始）
			sheet.createFreezePane(0, 1);
			
			/* 調整欄寬：取標題欄的欄位數，一個一個autoSize，再設定成autoSize後的兩倍寬（因為中文字是兩倍）
			 */
			for(int i = 0;i < header_row1.getLastCellNum();i++){
				//sheet.autoSizeColumn(i);
				//sheet.setColumnWidth(i, sheet.getColumnWidth(i)*2);
				//sheet.setColumnWidth(i, Math.min(sheet.getColumnWidth(i)*1, 60*256)); //欄寬1倍 或 欄寬60 取最小
				sheet.setColumnWidth(i, 20*256);
			}
			
			/* 設定列高 */
			header_row1.setHeightInPoints((short)22);
			
			
		/* 5.輸出 ===============================================================================*/			
			out = response.getOutputStream();
			CSVTools csv = new CSVTools(wb);
			//wb.write(out);
			csv.write(out);
			wb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out!=null){
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void createCell(int style, int colNum, Row row, CellStyle cellStyle, Object value){

		if(style == 0){
			Cell cell = row.createCell(colNum, CellType.STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(String.valueOf(value==null ? "" : value));
		}
		else if(style == 1){
			Cell cell = row.createCell(colNum, CellType.NUMERIC);
			cell.setCellStyle(cellStyle);
			cell.setCellValue((Integer)(value==null ? 0 : value));
		}
		else if(style == 2){
			DecimalFormat nf = new DecimalFormat("###,###,###,##0.000");
			Cell cell = row.createCell(colNum, CellType.NUMERIC);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(nf.format((double)value));
		}
		else if(style == 3){
			String temp = "";
			if (value != null && String.valueOf(value).trim().length() != 0) {
				temp = ToolsUtil.parseNumToFinancial(Long.valueOf( (String)value ));
			}else{
				temp = String.valueOf(value==null ? "" : value);
			}
			Cell cell = row.createCell(colNum, CellType.STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(temp);
			
		}
	}
	
	/* 設定標題字型 */
	public Font setFont(int style, Workbook wb){
		Font font = wb.createFont();
		
		/* 標題通常粗體 */
		if(style == 0){
			font.setColor(IndexedColors.BLACK.index);     //黑色
			font.setBold(true);; //粗體
			font.setFontHeightInPoints((short) 11);   //設定字體大小
			font.setFontName("Times New Roman");
		}
		
		/* 內容通常有特定字型，不粗體 */
		else if(style == 1){
			font.setColor(IndexedColors.BLACK.index);   //黑色
			font.setFontHeightInPoints((short) 11); //設定字體大小
			font.setFontName("微軟正黑體"); 
		}
		return font;
	}

	
	/* 設定沒有顏色的style */
	public XSSFCellStyle setCellStyle(int style, Workbook wb, int r, int g, int b, Font font)
	{
		XSSFCellStyle cellStyle = (XSSFCellStyle)(wb.createCellStyle());
		
		/* 靠左換行 */
		if(style == 0){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直置中
			cellStyle.setWrapText(true); // 自動換行
		}

		/* 置中 */
		if(style == 1){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直置中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平置中
		}
		
		/* 置中 + 背景色 */
		else if(style == 2){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直置中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平置中
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//設置可填充儲存格底色
			cellStyle.setFillForegroundColor(new XSSFColor( new Color(r, g, b))); //粉橘 253, 253, 253
			/* 設定背景色：想要自訂顏色，就必須將CellStyle轉型成 XSSFCellStyle 才可以自訂顏色
			 * 原版寫法[HSSFColor.DARK_TEAL.index]   另一寫法[new XSSFColor( new Color(r, g, b))] 
			 */
		}

		/* 置中 + 背景色 + 自動換行 */
		else if(style == 3){ 
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直置中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平置中
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//設置可填充儲存格底色
			cellStyle.setFillForegroundColor(new XSSFColor( new Color(r, g, b))); 
			cellStyle.setWrapText(true); // 自動換行
		}
		
		/* 靠左 + 背景色 */
		else if(style == 4){ 
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直置中 + 靠左對齊
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//設置可填充儲存格底色
			cellStyle.setWrapText(true); // 自動換行
			cellStyle.setFillForegroundColor(new XSSFColor( new Color(r, g, b))); 
		}
		
		/* 靠右  */
		if(style == 5){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直置中
			cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 水平靠右
		}
		
		cellStyle.setBorderTop(BorderStyle.THIN);    //儲存格外框 - 上方
		cellStyle.setTopBorderColor(new XSSFColor( new Color(217, 217, 217)));
		cellStyle.setBorderBottom(BorderStyle.THIN); //儲存格外框 - 底線
		cellStyle.setBottomBorderColor(new XSSFColor( new Color(217, 217, 217)));
		cellStyle.setBorderLeft(BorderStyle.THIN);   //儲存格外框 - 左側
		cellStyle.setLeftBorderColor(new XSSFColor( new Color(217, 217, 217)));
		cellStyle.setBorderRight(BorderStyle.THIN);  //儲存格外框 - 右側
		cellStyle.setRightBorderColor(new XSSFColor( new Color(217, 217, 217))); //208, 206, 206
		cellStyle.setFont(font); // 設定字體
		return cellStyle;
	}

}
