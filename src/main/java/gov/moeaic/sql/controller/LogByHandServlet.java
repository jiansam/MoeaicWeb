package gov.moeaic.sql.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.moeaic.sql.dao.LogAccessService;
import gov.moeaic.sql.tool.LogAccessRead;
import gov.moeaic.sql.tool.LogAccessRead_IPXCountry;

public class LogByHandServlet extends HttpServlet
{
	
	/* 只用於第一次安裝於正式主機時指定日期，之後就不再使用，
	 * 程式改為只會runlog.doLogAccess()版
	 * 
	 * LogAccessRead.java             搭配 [TB_LogAccess]           -->記錄  各頁面_每日點擊數量
	 * LogAccessRead_IP.java          搭配 [TB_LogAccessIP]         -->記錄  各IP_index_每日點擊數量
	 * LogAccessRead_IPXCountry.java  搭配 [TB_LogAccessIPXCountry] -->記錄  各IP_各頁面_每日點擊數量
	 * 
	 * 因為資料庫取得的日期是已經處理完畢的【最後一天】，這天不需讀取
	 * 而程式啟動在凌晨一點，所以當天是新的開始，LOG還無法讀，所以這【最後一天】天也不能用
	 * 
	 * 因此假設自訂txt讀取範圍是   01/01 ～ 01/31   
	 * 那日期起始要設定                   12/31 ～ 02/01
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String startDate = request.getParameter("startDate"); //2017/01/01
		String endDate = request.getParameter("endDate"); //2017/01/01
		System.out.println("start="+startDate+", end="+endDate);
		String inputError = "";
		Date start = null;
		Date end = null;
		
		
		/* 驗證開始日期不為空 */
		if(startDate == null || startDate.trim().length() == 0) {
			inputError = "請輸入日期" ;
			/* 如果有錯誤字串就轉回頁面 */
			returnToPage(inputError, request, response);
			return;
		}else {
			start = ToolsUtil.dateToChange(startDate, "yyyy/MM/dd");
		}
		
		/* 結束日期若是空白就取今天 */
		if(endDate == null || endDate.trim().length() == 0) {
			end = new Date();
		}else {
			end = ToolsUtil.dateToChange(endDate, "yyyy/MM/dd");
		}
		
		/* 資料庫已有LOG紀錄：驗證輸入日期晚於資料庫最後LOG日期，否則就停止。 
		 * 資料庫沒有LOG紀錄：直接開始LOG 
		Date userkeyIn = ToolsUtil.dateToChange(startDate, "yyyy/MM/dd");
		LogAccessService service = new LogAccessService();
		Date logLastDate = service.getLastLogDate();
		if(logLastDate != null) {
			if(userkeyIn.before(logLastDate)) {
				inputError = "日期不得早於資料庫最後紀錄日期";
				// 如果有錯誤字串就轉回頁面 
				returnToPage(inputError, request, response);
				return;
			}
		}
		*/
		
		System.out.println("日期檢查通過，開始LOG");
		System.out.println("start="+start+", end="+end);
		Date timeStart = new Date();
		// 日期通過檢驗，開始依指定日期作LOG
		LogAccessRead log = new LogAccessRead(request.getServletContext());
		log.doLogAccess(start, end);
		

		/* 107-12-13 只建立在測試機(23)的讀取 [IP + 所有網頁點擊率] 的表格 
		 * 109-02-23 已加入正式機 程式打開  並加入在 log.doLogAccess(start, end) 之後
		LogAccessRead_IPXCountry log_IPXCountry = new LogAccessRead_IPXCountry(request.getServletContext());
		log_IPXCountry.doLogAccessIPXCountry(start, end); */
		
		
		Date timeEnd = new Date();
		System.out.println("開始更新時間為:["+ ToolsUtil.dateToChange(timeStart, "yyyy-MM-dd HH:mm:SS") +"],  結束時間:["+ ToolsUtil.dateToChange(timeEnd, "yyyy-MM-dd HH:mm:SS") +"]");
		returnToPage("更新完畢", request, response);
		return;
		
		
		//log.doLogAccess();
		//log.doLogAccess(ToolsUtil.dateToChange("2017/11/30", "yyyy/MM/dd"));
	}

	private void returnToPage(String value, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(value);
		out.close();
	}

	
	
}
