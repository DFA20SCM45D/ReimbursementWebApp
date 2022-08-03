package Servlet;

import JDBC.ConnectionManager;
import JDBC.ReimbursementDao;
import Model.Reimbursement;
import Service.EmployeeService;
import Service.ManagerService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name = "ProcessRequest", initParams = {})
public class ProcessReimbursementRequestServlet extends HttpServlet {

    private ObjectMapper om = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        out.println("Initializing Reimbursement Servlet");
        om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * requests to process reimbursement request
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession userSession = req.getSession(true);
        int managerid = (int) userSession.getAttribute("managerid");

        Cookie[] allCookies = req.getCookies();
        Cookie cookie = null;

        for (Cookie cookieN : allCookies) {
            if (cookieN.getName().equalsIgnoreCase("loginM")) {
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

                ConnectionManager cm = (ConnectionManager) getServletContext().getAttribute("database");

                ReimbursementDao reimbursementDao = new ReimbursementDao(cm);
                ManagerService ms = new ManagerService(reimbursementDao);

                ms.processReimbursementRequest(reimbursement.getReimbursmentID(),managerid,reimbursement.getStatus());

                resp.setStatus(201);

            } else {
                out.println("not logged in to perform this action");
            }
        }  else {
            out.println("not logged in to perform this action");
        }
    }
}
