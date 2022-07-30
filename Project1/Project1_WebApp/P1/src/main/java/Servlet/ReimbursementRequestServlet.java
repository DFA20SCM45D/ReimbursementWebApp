package Servlet;

import JDBC.ConnectionManager;
import JDBC.ReimbursementDao;
import Model.Employee;
import Model.Reimbursement;
import Service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name="ReimbursementRequest", initParams = {})
public class ReimbursementRequestServlet extends HttpServlet {

    private ObjectMapper om = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        out.println("Initializing Servlet");
        om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        Cookie[] allCookies = req.getCookies();

        Cookie cookie1 = allCookies[allCookies.length-1];
        out.println("cookie 1 value " + cookie1.getValue() );
        out.println("cookie 1 name "+ cookie1.getName());

        if(cookie1.getValue().equals(true)) {

            Reimbursement reimbursement = om.readValue(req.getInputStream(), Reimbursement.class);

            System.out.println(reimbursement.getReimburseAmount());

            ConnectionManager cm = (ConnectionManager) getServletContext().getAttribute("database");

            ReimbursementDao reimbursementDao = new ReimbursementDao(cm);
            EmployeeService es = new EmployeeService(reimbursementDao);

            es.submitReimbursementRequest(reimbursement.getEmpID(), reimbursement);

            resp.setStatus(201);

        } else {
            out.println("not logged in to perform this action");

            //delete cookie code random
           // Cookie cookie = new Cookie("username", "");
           // cookie.setMaxAge(0);
           // response.addCookie(cookie);
        }
    }
}
