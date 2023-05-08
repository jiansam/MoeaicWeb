package gov.moeaic.sql.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.bean.BusinessPub;
import gov.moeaic.sql.bean.BusinessPubFile;
import gov.moeaic.sql.bean.BusinessPubOrder;
import gov.moeaic.sql.bean.Business_category;
import gov.moeaic.sql.bean.OpenData;
import gov.moeaic.sql.bean.OpenDataFile;
import gov.moeaic.sql.bean.Pages;
import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.bean.PagesNewsFile;
import gov.moeaic.sql.bean.User;
import gov.moeaic.sql.bean.WebName;
import gov.moeaic.sql.bean.WebOrder;
import gov.moeaic.sql.bean.WebTitle;

public class PagesService {
	
	public Pages getPages(int id) {
		return PagesDAO.get(id);
	}
	public ArrayList<Pages> getPages(String type) {
		return PagesDAO.get(type);
	}
	
	public Pages getPages(String type, String language) {
		return PagesDAO.get(type, language);
	}
	
	public Pages insertPages(Pages bean) {
		return PagesDAO.insert(bean);
	}
	
	public Pages updatePages(Pages bean) {
		return PagesDAO.update(bean);
	}
	
	
	
	public WebTitle getWebTitle(int id) {
		return WebTitleDAO.get(id);
	}
	
	//map={ ("ch" , [投資臺灣, 在臺工作, 來臺就學]) , ("en" , [xxx,xxx,xxx]) }
	public Map<String, WebTitle> getWebTitle_names() {
		
		Map<String, WebTitle> map = null;
		ArrayList<WebTitle> list = WebTitleDAO.get();
		
		if(list!=null && list.size()!=0){
			map = new HashMap<>();
			for(WebTitle bean : list){
				String id = String.valueOf(bean.getId());
				map.put(id , bean);
			}
		}
		return map;
	}
	
	public ArrayList<WebTitle> getWebTitle() {
		return WebTitleDAO.get();
	}
	
	public boolean insertWebTitle(WebTitle bean) {
		return WebTitleDAO.insert(bean);
	}
	
	public boolean updateWebTitle(WebTitle bean) {
		return WebTitleDAO.update(bean);
	}

	public boolean deleteWebTitle(int id) {
		return WebTitleDAO.delete(id);
	}
	
	public boolean checkNeedRemove(){
		return WebTitleDAO.checkNeedRemove();
	}
	
	
	
	public WebName getWebName(int id) {
		return WebNameDAO.get(id);
	}
	public ArrayList<WebName> getWebName() {
		return WebNameDAO.get();
	}
	
	public int insertWebName(WebName bean) {
		return WebNameDAO.insert(bean);
	}
	
	public boolean updateWebName(WebName bean) {
		return WebNameDAO.update(bean);
	}
	
	public boolean updateWebName_Titles(int id, String web_titles) {
		return WebNameDAO.update_Titles(id, web_titles);
	}

	public boolean deleteWebName(int id) {
		return WebNameDAO.delete(id);
	}
	
	
	
	public ArrayList<WebOrder> getWebOrder(int parent_id){
		return WebOrderDAO.get(parent_id);
	}
	
	public ArrayList<WebOrder> getWebOrder(){
		return WebOrderDAO.get();
	}
	
	public Map<String, ArrayList<WebOrder>> getWebOrder_map(){
		Map<String, ArrayList<WebOrder>> result = null;
		ArrayList<WebOrder> list = WebOrderDAO.get();
		
		if(list!=null && !list.isEmpty()){
			result = new HashMap<>();
			
			for(WebOrder bean : list){
				if("ch".equalsIgnoreCase(bean.getLang())){
					String ch_title = bean.getCh_title();
					if(result.get(ch_title)==null){
						result.put(ch_title, new ArrayList<>());
					}
					result.get(ch_title).add(bean);
				}else {
					String en_title = bean.getEn_title();
					if(result.get(en_title)==null){
						result.put(en_title, new ArrayList<>());
					}
					result.get(en_title).add(bean);
				}
			}
		}
		return result;
	}
	
	public ArrayList<WebOrder> getWebOrderByWebName(int webName_id){
		return WebOrderDAO.getByWebName(webName_id);
	}
	
	public void insertWebOrder(int webName_id, ArrayList<WebOrder> list) {
		WebOrderDAO.insert(webName_id, list);
	}
	
	public void updateWebOrder(int webName_id, ArrayList<WebOrder> list) {
		WebOrderDAO.update(webName_id, list);
	}

	public void updateWebOrder_sort(int id, String sort) {
		WebOrderDAO.update_sort(id, sort);
	}
	
	public void deleteWebOrder(int id) {
		WebOrderDAO.delete(id);
	}
	
	public void deleteWebOrderByWebName(int webName_id) {
		WebOrderDAO.deleteByWebName(webName_id);;
	}
	
	public void deleteWebOrderByWebTitle(int parent_id) {
		WebOrderDAO.deleteByWebTitle(parent_id);;
	}
	
	public PagesNews getPagesNews_One(int id) {
		return PagesNewsDAO.get(id);
	}
	public PagesNews getFrontPagesNews_One(int id , String lang) {
		return PagesNewsDAO.get(id , lang);
	}
	public ArrayList<PagesNews> getPagesNews(String type) {
		return PagesNewsDAO.get(type);
	}
	
	public ArrayList<PagesNews> getFrontPagesNews(String type, String lang) {
		return PagesNewsDAO.getFrontNews(type, lang);
	}
	
	public ArrayList<PagesNews> getFrontPagesNewsFiltered(String type, String lang, String date_from, String date_to, String keyword) {
		return PagesNewsDAO.getFrontNewsFiltered(type, lang, date_from, date_to, keyword);
	}
	
	public ArrayList<PagesNews> getFrontPagesNews_CSS(String type, String lang) {
		return PagesNewsDAO.getFrontNews_CSS(type, lang);
	}
	
	public int insertPagesNews(PagesNews bean) {
		return PagesNewsDAO.insert(bean);
	}
	
	public boolean updatePagesNews_ch(PagesNews bean) {
		return PagesNewsDAO.update_ch(bean);
	}
	
	public boolean updatePagesNews_en(PagesNews bean) {
		return PagesNewsDAO.update_en(bean);
	}
	
	public boolean updatePagesNews_topSort(PagesNews bean){
		return PagesNewsDAO.update_topSort(bean);
	}
	
	public boolean deletePagesNews(int id) {
		return PagesNewsDAO.delete(id);
	}
	
	public boolean record_deleteId(int id){
		return PagesNewsDAO.record_deleteId(id);
	}
	//107-05-22 最新消息 置頂，調整順序時，可設定自動排列。如 4變1，則原本的1,2,3自動+1
	public boolean setAutoSort_news(String type, int newSort, int oldSort) {
		return PagesNewsDAO.setAutoSort_news(type, newSort, oldSort);
	}
	
	
	public Map<Integer, ArrayList<PagesNewsFile>> getPagesNewsFile_map() {
		return PagesNewsFileDAO.get();
	}
	
	public Map<Integer, ArrayList<PagesNewsFile>> getPagesNewsFile_map(String lang) {
		return PagesNewsFileDAO.get(lang);
	}
	
	public PagesNewsFile getPagesNewsFile_One(int id) {
		return PagesNewsFileDAO.get(id);
	}
	
	public ArrayList<PagesNewsFile> getPagesNewsFile(int pagesNews_id) {
		return PagesNewsFileDAO.getByPagesNewsFile(pagesNews_id);
	}
	
	public boolean insertPagesNewsFile(ArrayList<PagesNewsFile> beans) {
		return PagesNewsFileDAO.insert(beans);
	}
	
	public boolean updatePagesNewsFile(PagesNewsFile bean) {
		return PagesNewsFileDAO.update(bean);
	}
	
	public boolean deletePagesNewsFile(int id) {
		return PagesNewsFileDAO.delete(id);
	}
	
	public boolean deletePagesNewsFile_byPsId(int pagesNews_id) {
		return PagesNewsFileDAO.delete_byPsId(pagesNews_id);
	}
	
	
	
	public ArrayList<User> getAllUser(){
		return UserDAO.getALL();
	}
	
	public User getUser(int id){
		return UserDAO.get(id);
	}
	
	public int insertUser(User bean){
		return UserDAO.insert(bean);
	}
	
	public void updateUser(User bean){
		UserDAO.update(bean);
	}
	
	public void updateUserPassword(User bean){
		UserDAO.updatePassword(bean);
	}
	
	public void userDisable(int id, boolean disable){
		UserDAO.userDisable(id, disable);
	}
	
	public void userAdmin(int id, boolean admin){
		UserDAO.userAdmin(id, admin);
	}
	
	
	public void insert_BSCT(ArrayList<Business_category> list){
		Business_categoryDAO.insert(list);
	}
	
	public Map<Integer, ArrayList<Business_category>> get_BSCT(){
		return Business_categoryDAO.get();
	}
	
	public boolean updateShowIndex(String showIndex){
		return Business_categoryDAO.updateShowIndex(showIndex);
	}
	
	public String getShowIndex(){
		return Business_categoryDAO.getShowIndex();
	}
	
	public ArrayList<Business_category> get_BSCT(int seq){
		return Business_categoryDAO.get(seq);
	}
	
	public ArrayList<Business_category> get_BSCTType()
	{
		return Business_categoryDAO.getType();
	}
	
	
	
	public BusinessPub getBusinessPubOne(int id){
		return BusinessPubDAO.get(id);
	}
	
	public ArrayList<BusinessPub> isParentId_used_PB(String type){
		return BusinessPubDAO.get(type);
	}
	
	public BusinessPub getBusinessPubOne(int id, String lang){
		return BusinessPubDAO.get(id, lang);
	}
	
	public int insertBusinessPub(BusinessPub bean){
		return BusinessPubDAO.insert(bean);
	}
	
	public boolean updateBusinessPub_ch(BusinessPub bean){
		return BusinessPubDAO.update_ch(bean);
	}
	
	public boolean updateBusinessPub_en(BusinessPub bean){
		return BusinessPubDAO.update_en(bean);
	}
	
	public boolean deleteBusinessPub(int id){
		return BusinessPubDAO.delete(id);
	}
	
	
	
	public Map<String , ArrayList<BusinessPubFile>> getBusinessPubFile(){
		return BusinessPubFileDAO.get();
	}
	
	public Map<String , ArrayList<BusinessPubFile>> getBusinessPubFile(String lang){
		return BusinessPubFileDAO.get(lang);
	}
	
	public BusinessPubFile getBusinessPubFile(int id){
		return BusinessPubFileDAO.get(id);
	}
	
	public ArrayList<BusinessPubFile> getFileByBusinessPub(int businessPub_id){
		return BusinessPubFileDAO.getFileByBusinessPub(businessPub_id);
	}
	
	public boolean insertBusinessPubFile(ArrayList<BusinessPubFile> beans){
		return BusinessPubFileDAO.insert(beans);
	}
	
	public boolean updateBusinessPubFile(BusinessPubFile bean){
		return BusinessPubFileDAO.update(bean);
	}
	
	public boolean deleteBusinessPubFile(int id){
		return BusinessPubFileDAO.delete(id);
	}
	
	public boolean deleteBusinessPubFile_byBsId(int businessPub_id){
		return BusinessPubFileDAO.delete_byBsId(businessPub_id);
	}
	
	
	public ArrayList<BusinessPubOrder> getBusinessPubOrder(String op_id_one){
		return BusinessPubOrderDAO.get(op_id_one);
	}
	
	public Map<String, BusinessPubOrder> getBusinessPubOrder(int businessPub_id){
		return BusinessPubOrderDAO.get(businessPub_id);
	}
	
	public ArrayList<BusinessPubOrder> getBusinessPubOrder(String op_id_one, String lang){
		return BusinessPubOrderDAO.get(op_id_one, lang);
	}
	
	public ArrayList<BusinessPubOrder> getFrontBusinessPubOrder(String op_id_one, String lang){
		return BusinessPubOrderDAO.getFrontBusinessPub(op_id_one, lang);
	}
	
	public void updateBusinessPubOrder(int businessPub_id, ArrayList<BusinessPubOrder> list){
		BusinessPubOrderDAO.update(businessPub_id, list);
	}
	
	public void updateBusinessPubOrder_sort(int id, String sort){
		BusinessPubOrderDAO.update_sort(id, sort);
	}
	
	public void insertBusinessPubOrder(int businessPub_id, ArrayList<BusinessPubOrder> list){
		BusinessPubOrderDAO.insert(businessPub_id, list);
	}
	
	public void deleteBusinessPubOrder(int id){
		BusinessPubOrderDAO.delete(id);
	}
	
	public void deleteBusinessPubOrder_byBsId(int businessPub_id){
		BusinessPubOrderDAO.deleteByBusinessPub(businessPub_id);
	}
	
	//107-05-22 申辦業務，調整順序時，可設定自動排列。
	public boolean setAutoSort_Bus(String op_id_one, String type, int newSort, int oldSort) {
		return BusinessPubOrderDAO.setAutoSort_Bus(op_id_one, type, newSort, oldSort);
	}
	
	
	
	public OpenData getOpenData(int id){
		return OpenDataDAO.get(id);
	}
	
	public ArrayList<OpenData> isParentId_used_OD(int type){
		return OpenDataDAO.isParentId_used_OD(type);
	}
	
	public ArrayList<OpenData> getOpenDataByType(int type){
		return OpenDataDAO.list(type);
	}
	
	public ArrayList<OpenData> getOpenDataByType(){
		return OpenDataDAO.list();
	}
	
	public ArrayList<OpenData> getFrontOpenDataByType(){
		return OpenDataDAO.getFrontlist();
	}
	
	public int insertOpenData(OpenData data){
		return OpenDataDAO.insertOpenData(data);
	}
	
	public boolean updateOpenData_ch(OpenData data){
		return OpenDataDAO.update_ch(data);
	}
	
	public boolean updateOpenData_en(OpenData data){
		return OpenDataDAO.update_en(data);
	}
	
	public boolean update_OpenDataSort(int id, int seq, boolean ontop){
		return OpenDataDAO.update_sort(id, seq, ontop);
	}
	
	public void deleteOpenData(int id){
		OpenDataDAO.delete(id);
	}
	
	
	public Map<String , ArrayList<OpenDataFile>> getOpenDataFile(){
		return OpenDataFileDAO.get();
	}
	
	public OpenDataFile getOpenDataFile(int id){
		return OpenDataFileDAO.get(id);
	}
	
	public ArrayList<OpenDataFile> getFileByOpenData(int openData_id){
		return OpenDataFileDAO.getFileByOpenData(openData_id);
	}
	
	public boolean insertOpenDataFile(ArrayList<OpenDataFile> beans){
		return OpenDataFileDAO.insert(beans);
	}
	
	public boolean updateOpenDataFile(OpenDataFile bean){
		return OpenDataFileDAO.update(bean);
	}
	
	public boolean deleteOpenDataFile(int id){
		return OpenDataFileDAO.delete(id);
	}
	
	public boolean deleteOpenDataFile_byODId(int openData_id){
		return OpenDataFileDAO.delete_byODId(openData_id);
	}
	
	
	
	public String getBusPub_QAType_used(String lang, String op_id_one){
		return BusinessPubDAO.getFrontBusPub_QAType_used(lang, op_id_one);
	}
}
