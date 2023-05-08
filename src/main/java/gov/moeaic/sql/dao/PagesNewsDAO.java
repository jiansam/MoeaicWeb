package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.dasin.tools.dWebTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.DeletedPageNews;
import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.controller.ToolsUtil;

public class PagesNewsDAO
{
	
	public static PagesNews get(int id)
	{
		PagesNews result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_PagesNews WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = setPagesNews(rs, new PagesNews());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static PagesNews get(int id, String lang)
	{
		PagesNews result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			if("en".equalsIgnoreCase(lang)){
				sql = "SELECT * FROM TB_PagesNews WHERE id = ? AND LEN(en_title) > 0";
			}else{
				sql = "SELECT * FROM TB_PagesNews WHERE id = ? AND LEN(ch_title) > 0";
			}
			pstmt = sqltools.prepare(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = setPagesNews(rs, new PagesNews());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static ArrayList<PagesNews> get(String type)
	{
		ArrayList<PagesNews> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_PagesNews "
								   + "WHERE type = ? "
								   + "ORDER BY showTop DESC, convert(int, sort), publish_date DESC");
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				result.add(setPagesNews(rs, new PagesNews()));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	

	/* 2020/10/01 by Nekobe Wu
	 * 
	 * 新增可搜尋日期區間與keyword的DAO
	 */
	public static ArrayList<PagesNews> getFrontNewsFiltered(String type, String lang, String date_from, String date_to, String keyword)
	{
		ArrayList<PagesNews> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Date d_date_from = null;
		Date d_date_to = null;
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		boolean use_date = false;
		boolean use_keyword = false;
		
		
		if (date_from != null && !date_from.trim().isEmpty() && date_to != null && !date_to.trim().isEmpty() ) {
			try {
				d_date_from = sdf.parse(date_from);
				d_date_to = sdf.parse(date_to);
				use_date = true;
			} catch (Exception ex) {
				use_date = false;
			}
		}
		
		if (keyword != null && !keyword.trim().isEmpty()) {
			use_keyword = true;
		} else {
			use_keyword = false;
		}
		
		
		
		try {
			
			
			
			pstmt = sqltools.prepare("SELECT * FROM TB_PagesNews "
								   + "WHERE type = ? "
								   + "AND publish_date < GETDATE() "
								   + "ORDER BY showTop DESC, convert(int, sort), publish_date DESC");
			
			int column = 1;
			pstmt.setString(column++, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				
				if (use_date) {
					Date publish_date = rs.getDate("publish_date");
					if ( publish_date.before(d_date_from) || publish_date.after(d_date_to)) {
						continue;
					}
				}
				
				if (use_keyword) {
					if("en".equalsIgnoreCase(lang)){
						/*
						if (!rs.getString("en_title").contains(keyword) && !rs.getString("en_content").contains(keyword) ) {
							continue;
						}
						*/
						if (!org.apache.commons.lang3.StringUtils.containsIgnoreCase(rs.getString("en_title"), keyword)) {
							continue;
						}
					} else {
						if (!rs.getString("ch_title").contains(keyword) && !rs.getString("ch_content").contains(keyword) ) {
							continue;
						}						
					}
				}
				
				
				
				if("en".equalsIgnoreCase(lang)){
					if(rs.getString("en_title").length() == 0 ){
						continue;
					}
				}else{
					if(rs.getString("ch_title").length() == 0 ){
						continue;
					}
				}
				result.add(setPagesNews(rs, new PagesNews()));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	

	public static ArrayList<PagesNews> getFrontNews(String type, String lang)
	{
		ArrayList<PagesNews> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_PagesNews "
								   + "WHERE type = ? "
								   + "AND publish_date < GETDATE() "
								   + "ORDER BY showTop DESC, convert(int, sort), publish_date DESC");
			int column = 1;
			pstmt.setString(column++, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				if("en".equalsIgnoreCase(lang)){
					if(rs.getString("en_title").length() == 0 ){
						continue;
					}
				}else{
					if(rs.getString("ch_title").length() == 0 ){
						continue;
					}
				}
				result.add(setPagesNews(rs, new PagesNews()));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	public static ArrayList<PagesNews> getIndexNews(String type, String lang)
	{
		ArrayList<PagesNews> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT TOP 5 * FROM TB_PagesNews "
					   + "WHERE type = ? "
					   + "AND publish_date < GETDATE() ";
		
		if("ch".equalsIgnoreCase(lang)){
			sql = sql + "AND LEN(ch_title) > 0 "
					  + "ORDER BY showTop DESC, sort, publish_date DESC" ;
		}else{
			sql = sql + "AND LEN(en_title) > 0 "
					  + "ORDER BY showTop DESC, sort, publish_date DESC" ;
		}
		
		
		
		try {
			pstmt = sqltools.prepare(sql);
			int column = 1;
			pstmt.setString(column++, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				result.add(setPagesNews(rs, new PagesNews()));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	public static ArrayList<PagesNews> getFrontNews_CSS(String type, String lang)
	{
		ArrayList<PagesNews> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_PagesNews "
								   + "WHERE type = ? "
								   + "AND publish_date < GETDATE() "
								   + "AND publish_date > '2013-01-01' "
								   + "ORDER BY showTop DESC, sort, publish_date DESC");
			int column = 1;
			pstmt.setString(column++, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				if("en-us".equalsIgnoreCase(lang)){
					if(rs.getString("en_title").length() == 0 ){
						continue;
					}
				}else{
					if(rs.getString("ch_title").length() == 0 ){
						continue;
					}
				}
				result.add(setPagesNews(rs, new PagesNews()));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	public static int insert(PagesNews bean)
	{
		int result = 0;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("INSERT INTO TB_PagesNews VALUES(?,?,?,?,?,?,?,?,?,?)", new String[]{"id"});
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_content()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_content()));
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setBoolean(column++, bean.isShowTop());
			pstmt.setInt(column++, bean.getSort());
			pstmt.setString(column++, bean.getType());
			pstmt.setString(column++, bean.getRss_text());
			pstmt.setString(column++, bean.getRss_image());
			int x = pstmt.executeUpdate();
			if(x != 0){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					result = rs.getInt(1);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public static boolean update_ch(PagesNews bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_PagesNews SET "
								   + "ch_title = ?, "
								   + "ch_content = ?, "
								   + "publish_date = ?, "
								   + "showTop = ?, "
								   + "sort = ? "
								   + "WHERE id = ? ");
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_content()));
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setBoolean(column++, bean.isShowTop());
			pstmt.setInt(column++, bean.getSort());
			pstmt.setInt(column++, bean.getId());
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static boolean update_en(PagesNews bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_PagesNews SET "
								   + "en_title = ?, "
								   + "en_content = ?, "
								   + "publish_date = ?, "
								   + "showTop = ?, "
								   + "sort = ?, "
								   + "rss_text = ?, "
								   + "rss_image = ? "
								   + "WHERE id = ? ");
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_content()));
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setBoolean(column++, bean.isShowTop());
			pstmt.setInt(column++, bean.getSort());
			pstmt.setString(column++, bean.getRss_text());
			pstmt.setString(column++, bean.getRss_image());
			pstmt.setInt(column++, bean.getId());
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public static boolean update_topSort(PagesNews bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_PagesNews SET "
								   + "showTop = ?, "
								   + "sort = ? "
								   + "WHERE id = ? ");
			int column = 1;
			pstmt.setBoolean(column++, bean.isShowTop());
			pstmt.setInt(column++, bean.getSort());
			pstmt.setInt(column++, bean.getId());
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public static boolean record_deleteId(int id)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare(
					"INSERT INTO TB_DeletedPageNews(pagesNew_id, delete_date) VALUES(?, GETDATE())");
			pstmt.setInt(1, id);
			int x = pstmt.executeUpdate();
			if(x!=0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static ArrayList<DeletedPageNews> newsBeDeleted()
	{
		ArrayList<DeletedPageNews> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare(
					"SELECT * FROM TB_DeletedPageNews "
				  + "WHERE DATEDIFF(dd,0,delete_date) BETWEEN DATEDIFF(dd,0,getdate())-30 and DATEDIFF(dd,0,getdate())");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				DeletedPageNews bean = new DeletedPageNews();
				bean.setId(rs.getInt("id"));
				bean.setPagesNew_id(rs.getInt("pagesNew_id"));
				bean.setDelete_date(rs.getTimestamp("delete_date"));
				result.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static boolean delete(int id)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_PagesNews WHERE id = ?");
			pstmt.setInt(1, id);
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	//107-05-22 最新消息 置頂，調整順序時，可設定自動排列。如 4變1，則原本的1,2,3自動+1
	public static boolean setAutoSort_news(String type, int newSort, int oldSort)
	{
		
		StringBuilder sql = new StringBuilder();
		//狀況1： 4 變 1，原本的1,2,3都 +1
		if(newSort != 99 && oldSort != 99 && newSort<oldSort) {
			sql.append("UPDATE TB_PagesNews "
					 + "SET sort = sort+1 "
					 + "WHERE (sort>=? AND sort<?) " //(sort>=1 AND sort<4)
					 + "AND type = ? AND showTop = 1 ");
		}
		//狀況2： 2 變 5，原本的3,4,5都 -1
		else if(newSort != 99 && oldSort != 99 && newSort>oldSort) {
			sql.append("UPDATE TB_PagesNews "
					 + "SET sort = sort-1 "
					 + "WHERE (sort<=? AND sort>?) " //(sort<=5 AND sort>2)
					 + "AND type = ? AND showTop = 1 ");
		}		
		//狀況3： 1 變 99，原本的2,3,4都 -1
		else if(newSort == 99 && oldSort < 99) {
			sql.append("UPDATE TB_PagesNews "
					 + "SET sort = sort-1 "
					 + "WHERE (sort<? AND sort>?) " //(sort<99 AND sort>1)
					 + "AND type = ? AND showTop = 1 ");
		}
		//狀況4： 99 變 1，原本的1,2,3都 +1
		else if(newSort < 99 && oldSort == 99) {
			sql.append("UPDATE TB_PagesNews "
					 + "SET sort = sort+1 "
					 + "WHERE (sort>=? AND sort<?) " //(sort>=1 AND sort<99)
					 + "AND type = ? AND showTop = 1 ");
		}	
		//狀況5；兩個都99，不處理
		else if(newSort == 99 && oldSort == 99) {
			return true;
		}
		
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare(sql.toString());
			int col = 1;
			pstmt.setInt(col++, newSort);
			pstmt.setInt(col++, oldSort);
			pstmt.setString(col++, type);
			pstmt.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	private static PagesNews setPagesNews(ResultSet rs, PagesNews bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setCh_title(rs.getString("ch_title"));
		bean.setCh_content(rs.getString("ch_content"));
		bean.setEn_title(rs.getString("en_title"));
		bean.setEn_content(rs.getString("en_content"));
		bean.setPublish_date(rs.getTimestamp("publish_date"));
		bean.setShowTop(rs.getBoolean("showTop"));
		bean.setSort(rs.getInt("sort"));
		bean.setType(rs.getString("type"));
		bean.setRss_text(rs.getString("rss_text"));
		bean.setRss_image(rs.getString("rss_image"));
		return bean;
	}
}
