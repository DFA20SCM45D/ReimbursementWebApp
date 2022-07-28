package Servlet;

import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {

    @Resource(name="jdbc/produceDB")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Context Initialize");
       // ProduceRepo produceRepo = new ProduceRepo(dataSource);

       // servletContextEvent.getServletContext().setAttribute("produceRepo", produceRepo);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context Destroyed");
    }
}

