package gov.moeaic.sql.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;


public class ToolsUtil{
	
	public static int parseInt(String inString){
		int result = 0;
		try {
			if(inString==null || inString.trim().length()==0){
				return result;
			}
			result = Integer.valueOf(inString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("字串中含有非數字, 字串="+inString);
		}
		return result;
	}
	
	public static String parseNumToFinancial(Object number){
		DecimalFormat nf = new java.text.DecimalFormat("###,###,##0.00");
		return nf.format(number);
	}
	
	/*
	 * String trim functions.
	 */
	public static String trim(String inString){
		return inString == null ? "" : inString.trim();
	}
	
	public static String trim(String inString, int length){
		String result = trim(inString);

		return result.length() > length ? result.substring(0, length) : result;
	}
	
	public static boolean isEmpty(String inString){
		return trim(inString).isEmpty();
	}
	
	//判定日期是否在幾天內
	public static boolean dateInRange(Date theDay, int range)
	{
		boolean isInRange = false;
		
		//根據現在時間，算出N天前的Date（90天前
		Calendar now = Calendar.getInstance(); 
//		System.out.println( dateToChange(now.getTime(), "yyyy/MM/dd") + " (now)");
		now.add(Calendar.DAY_OF_MONTH, range);
		Date the_range_firstDay = now.getTime();
		
		isInRange = theDay.after(the_range_firstDay); //如果該天跟今天比，是未超出180天-->true
//		System.out.println(dateToChange(theDay, "yyyy/MM/dd") + " (theDay)");
//		System.out.println(dateToChange(the_range_firstDay, "yyyy/MM/dd") + " (the_range_firstDay)");
//		System.out.println(range);
//		System.out.println(isInRange);
		return isInRange;
	}
	
	/* GMT 時制，台灣是 GMT+8
     * SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss" , Locale.CHINESE); //星期五, 22 七月 2016 12:00:00 GMT
	 * SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss" , Locale.ENGLISH); //Friday, 31 December 9999 04:23:50 GMT
	 * SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss" , Locale.ENGLISH);   //Wed, 14 Dec 2016 06:30:00 GMT
	 */
	public static String dateToChange_GMT(Date date){
		String result = "";
		if(date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR_OF_DAY, -8);
			date = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss" , Locale.ENGLISH);   //Wed, 14 Dec 2016 06:30:00 GMT
			result =  sdf.format(date)+" GMT"; 
		}
		return result;
	}
	public static String dateToChange_GMT(int year, int month, int date){ //自填日期
		String result = "";
		Calendar ca = Calendar.getInstance();
		ca.set(year, month, date);
		ca.add(Calendar.HOUR_OF_DAY, -8);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss" , Locale.ENGLISH);   //Wed, 14 Dec 2016 06:30:00 GMT

		result =  sdf.format(ca.getTime())+" GMT";
		return result;
	}	

	
	/*
	 * 把Date 轉成 Timestamp 
	 */
	public static Timestamp dateToTimestamp(Date date){
		Timestamp result = null;
		if(date!=null){
			result = new Timestamp(date.getTime());
		}
		return result;
	}
	
	/* String 轉 Date
	 */
	public static Date dateToChange(String dateStr , String pattern){
		Date result = null;
		if(dateStr!=null && dateStr.trim().length()!=0){
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
			try {
				result = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/* 把 Date日期 改成 字串日期
	 */
	public static String dateToChange(Date date , String pattern){
		String dateStr = "";
		if(date!=null){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
				dateStr = sdf.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dateStr;
	}
	
	/* 把 Date日期 改成 字串日期，而且是民國年
	 */
	public static String dateToChangeROC(Date date , String pattern){
		StringBuilder dateStr = new StringBuilder();
		dateStr.append("");
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
			String temp = sdf.format(date);
			int year = parseInt(temp.substring(0,4))-1911;
			dateStr.append(year + temp.substring(4));
		}
		return dateStr.toString();
	}
		
	/* 將時間轉換成上午 下午格式
	 */
	public static String dateToChangeTime(Date date , String pattern, String CHorEN){
		String dateStr = "";
		if(date!=null){
			SimpleDateFormat sdf = null;
			if("CH".equalsIgnoreCase(CHorEN)){
				sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
			}else{
				sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
			}
			
			dateStr = sdf.format(date);
		}
		return dateStr;
	}
	
	//把網頁上單選、複選欄選取的  每個代碼[value欄位]，用逗點隔開 合成代碼字串存入資料庫
	public static String valuesToString(String[] values){
		StringBuilder result = new StringBuilder();
		if(values==null || values.length ==0){
			result.append("");
		}else{
			for(int n=0 ; n<values.length ; n++){
				if(result.length()!=0){
					result.append(",");
				}
				result.append(values[n]);
			}
		}
		return result.toString();
	}

	
	/* 判斷某日期，是否在今天起算 加減range內 */
	public static boolean dateIsInRange(Date date, int range){
		boolean result = false;
		if(date!=null){
			Date d1 = date; //發佈日
			//根據現在時間計算
			Calendar now = Calendar.getInstance(); 
//			now.add(Calendar.DAY_OF_MONTH, -3); //現在時間的3天前
			now.add(Calendar.DAY_OF_MONTH, range); //現在時間的3天前
			Date d2 = now.getTime();
			result = d1.after(d2);
		}
		return result;
	}
	
	//兩個日期相差天數
	public static int daysOfTwo(Date befor, Date after) {
		Calendar cal_before = Calendar.getInstance();cal_before.setTime(befor);
		Calendar cal_after = Calendar.getInstance();cal_after.setTime(after);
		
        long m = cal_after.getTimeInMillis() - cal_before.getTimeInMillis();
        m=m/(24*60*60*1000);
        //判斷是不是同一天
        if(m==0 && cal_after.get(Calendar.DAY_OF_YEAR) != cal_before.get(Calendar.DAY_OF_YEAR)){
            m+=1;
        }
        return (int)m;
    }
	
	/* 今天日期 加減天數，取得日期  */
	public static String dateToOneDate(String pattern, int range){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		/* int範圍（10位數）無法承受 86400000豪秒 乘以 天數，會超過範圍(25開始)，所以一定要改成Long型態，否則超過範圍就變負數了 */
		return sdf.format( new Date( now.getTime() - (86400000 * Long.valueOf(range)) ));
		
	}

	/* 某一Date 加減天數 */
	public static Date dateToOneDate(Date date, int range){
		Date result = null;
		if(date!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, range);
			result = cal.getTime();
		}
		return result;
	}
	
	/* 今天時間 加減時數 */
	public static Date dateToDeleteHour(Date date, int range){
		Date result = null;
		if(date!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR_OF_DAY, range);
			result = cal.getTime();
		}
		return result;
	}
	
	
	/* 取出某年某月的天數 */
	public static String getTheDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1 , 1);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return String.valueOf(day);
	}
	
	//把字串日期 改成 Date日期
	public static Date dateToChange(String dateStr) throws ParseException{
		Date date = null;
		if(dateStr!=null && dateStr.trim().length()!=0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(dateStr);
		}
		return date;
	}
	
	
	//把 Date日期 改成 字串日期
	public static String dateToChange(Date date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	
	//將用固定符號分隔的字串，變成 String[]後傳回
	public static ArrayList<String> getValueToList(String value, String split){
		
		ArrayList<String> list = null;
		if(value.indexOf(split) == -1){
			list = new ArrayList<>( Arrays.asList( new String[]{value} ) );
		}else{
			list = new ArrayList<>( Arrays.asList( value.split(split) ) );
		}

		return list;
	}
	

	public static void main(String[] args){
//		Date d1 = ToolsUtil.dateToChange("2016-11-09", "yyyy-MM-dd"); //發佈日
		//根據現在時間計算
//		Calendar now = Calendar.getInstance(); 
//		now.add(Calendar.DAY_OF_MONTH, -3); //現在時間的3天前
//		Date d2 = now.getTime();
		
		
//		Date day = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm:ss");
//		System.out.println(sdf.format(day)+" GMT");
		
//		Calendar now = Calendar.getInstance(); 
//		now.add(Calendar.DAY_OF_MONTH, -180);
//		Date the_range_firstDay = now.getTime();
//		System.out.println(dateToChange(the_range_firstDay, "yyyy/MM/dd"));
		
//		Date theDay = dateToChange("2016/11/30", "yyyy/MM/dd");
//		dateInRange(theDay, -180);
		
		
		
//		Date date = new Date(99, 10, 10);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.HOUR_OF_DAY, 13);
//		System.out.println(cal.getTime());
		
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.HOUR_OF_DAY, -8);
//		date = cal.getTime();
//		System.out.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss" , Locale.ENGLISH);   //Wed, 14 Dec 2016 06:30:00 GMT
//		System.out.println(sdf.format(date)+" GMT");

		
//		DecimalFormat nf = new DecimalFormat("###,###,###,##0.00");
//		Long xLong = -25436789125L;
//		System.out.println(nf.format(xLong));
		
//		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		/* int範圍（10位數）無法承受 86400000豪秒 乘以 天數，會超過範圍(25開始)，所以一定要改成Long型態，否則超過範圍就變負數了 */
//		System.out.println(sdf.format(now));
//		System.out.println(sdf.format( new Date( now.getTime() - (86400000 * Long.valueOf(25)) ))  +", "+(86400000 * Long.valueOf(25)));
//		System.out.println(sdf.format( new Date( now.getTime() - (86400000 * 25) ))                +", "+(86400000 * 25));
//		
//		
//		System.out.println(dateToChange_GMT(2017, 02, 03));
	
//		Map<String, ArrayList<String>> map = new HashMap<>();
//		map.put("A", new ArrayList<>(Arrays.asList("A")));
//		System.out.println(map);
//		map.put("A", new ArrayList<>(Arrays.asList("A","B")));
//		System.out.println(map);
		


	}
	
	
}
