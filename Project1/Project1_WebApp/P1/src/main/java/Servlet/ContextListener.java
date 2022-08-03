package Servlet;

import JDBC.ConnectionManager;
import org.postgresql.Driver;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {

    /**
     * To initialize connection details
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Context Initialize");

        ServletContext context= servletContextEvent.getServletContext();
        String url = context.getInitParameter("URL");
        String username = context.getInitParameter("USERNAME");
        String password = context.getInitParameter("PASSWORD");

        ConnectionManager cm = new ConnectionManager(url,username,password, new Driver());
        context.setAttribute("database", cm);
    }

    /**
     * Destroys current content
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context Destroyed");
    }
}

