package gov.moeaic.sql.tool;

import java.util.Date;

import gov.moeaic.sql.controller.ToolsUtil;

public class LogTest
{

	public static void main(String[] args)
	{
		String startDate = "2017/01/01";
		String endDate = "2017/01/04";
		
		String inputError = "";
		Date start = null;
		Date end = null;
		
		/* 驗證開始日期不為空 */
		if(startDate == null || startDate.trim().length() == 0) {
			inputError = "請輸入日期" ;
			/* 如果有錯誤字串就轉回頁面 */
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
		
		
	}

}
