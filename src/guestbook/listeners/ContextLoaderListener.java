package guestbook.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import guestbook.dao.ArticleDao;

/**
 * Application Lifecycle Listener implementation class ContextLoaderListener
 *
 */
@WebListener
public class ContextLoaderListener implements ServletContextListener {
	Connection conn;
    /**
     * Default constructor. 
     */
    public ContextLoaderListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
        try{
        	 ServletContext sc = event.getServletContext();
        	 
        	 Class.forName(sc.getInitParameter("driver"));
        	 conn = DriverManager.getConnection(
        			 sc.getInitParameter("url"),
        			 sc.getInitParameter("username"),
        			 sc.getInitParameter("password"));
        	 
        	 ArticleDao articleDao = new ArticleDao();
        	 articleDao.setConnection(conn);
        	 
        	 sc.setAttribute("articleDao",articleDao);
        	 
         } catch(Throwable e){
        	 e.printStackTrace();
         }
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    	try{
    		conn.close();
    	} catch(Exception e){}
    }
	
}
