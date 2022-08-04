package Servlet;

import JDBC.ConnectionManager;
import Service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(EmployeeService.class.getName());
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
        logger.debug("connection manager object created");
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

