package gov.moeaic.web.listener;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import gov.moeaic.sql.tool.LogAccessRead;
import gov.moeaic.sql.tool.LogAccessRead_IPXCountry;


public class BackgroundJobManager implements ServletContextListener 
{
	private ScheduledExecutorService scheduler;

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		scheduler.shutdownNow();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
		ServletContext context = arg0.getServletContext();
		boolean console = Boolean.parseBoolean(context.getInitParameter("console")); //是否為後台
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Calendar c = Calendar.getInstance();
				//System.out.println("console=" + console);
				if(console == false) {
					System.out.println("c.get(Calendar.HOUR_OF_DAY)=" + c.get(Calendar.HOUR_OF_DAY));
					if(c.get(Calendar.HOUR_OF_DAY) == 1){
						System.out.println("是否為後台?["+console+"]，啟動LOGACCESS分析");
						LogAccessRead log = new LogAccessRead(context);						
						log.doLogAccess();
					}else {
						System.out.println("是否為後台?["+console+"]，非指定時間，不啟動LOGACCESS分析");
					}
				}
				
				System.gc();
			}//end run
		}, 0, 1, TimeUnit.HOURS); //0：第一次啟動就檢查， 1：之後每隔1小時再檢查
		
	}
	
	
}
