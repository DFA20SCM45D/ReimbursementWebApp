package Servlet;

import JDBC.ConnectionManager;
import JDBC.ReimbursementDao;
import Model.Employee;
import Model.Reimbursement;
import Service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name="ReimbursementRequest", initParams = {})
public class ReimbursementRequestServlet extends HttpServlet {

    private ObjectMapper om = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(EmployeeService.class.getName());

    @Override
    public void init() throws ServletException {
        out.println("Initializing Reimbursement Servlet");
        om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * Requests to submit the reimbursement response
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.debug("Create new reimbursement request");

       Cookie[] allCookies = req.getCookies();
        Cookie cookie = null;

        for (Cookie cookieN : allCookies) {
            if (cookieN.getName().equalsIgnoreCase("login")) {
                cookieN.getValue();
                cookie = new Cookie(cookieN.getName(), cookieN.getValue());
                break;
            }
        }

        if (cookie != null) {
            out.println("cookie 1 value " + cookie.getValue());
            out.println("cookie 1 name " + cookie.getName());

            if (cookie.getValue().equals("true")) {

                Reimbursement reimbursement = om.readValue(req.getInputStream(), Reimbursement.class);

                System.out.println(reimbursement.getReimburseAmount());

                ConnectionManager cm = (ConnectionManager) getServletContext().getAttribute("database");

                ReimbursementDao reimbursementDao = new ReimbursementDao(cm);
                EmployeeService es = new EmployeeService(reimbursementDao);

                es.submitReimbursementRequest(reimbursement.getEmpID(), reimbursement);

                resp.setStatus(201);
                logger.debug("New reimbursement request created");

            } else {
                out.println("not logged in to perform this action");
            }
        }  else {
            out.println("not logged in to perform this action");
            resp.setStatus(403);
            resp.getWriter().write(om.writeValueAsString("Manager Not Logged In to Access this Facility"));
        }
    }
}
