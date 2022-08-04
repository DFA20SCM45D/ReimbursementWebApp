package Servlet;

import JDBC.ConnectionManager;
import JDBC.ReimbursementDao;
import Model.Reimbursement;
import Model.ReimbursementResponse;
import Service.EmployeeService;
import Service.ManagerService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name ="ManagerViewReimbursement", initParams = {})
public class ManagerViewReimbursementServlet extends HttpServlet {

    private ObjectMapper om = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(EmployeeService.class.getName());

    @Override
    public void init() throws ServletException {
        out.println("Initializing Servlet");
        om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * Request to view pending and resolved requests of al employees and view all reimbursement requests by an employee
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.debug("Manager requested to view reimbursement requests");

        String requestType = req.getRequestURI().split("/")[4];

        HttpSession userSession = req.getSession(true);

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

                ConnectionManager cm = (ConnectionManager) getServletContext().getAttribute("database");
                ReimbursementDao reimbursementDao = new ReimbursementDao(cm);

                ManagerService ms = new ManagerService(reimbursementDao);
                List<Reimbursement> r = new ArrayList<>();

                if(requestType.equalsIgnoreCase("pending")){
                    r = ms.viewPendingRequestAllEmployee();
                    logger.debug("Manager view pending requests of all employee");
                }
                else if(requestType.equalsIgnoreCase("resolved")){
                    r = ms.viewResolvedRequestWithManager();
                    logger.debug("Manager view pending requests of all employee");
                }
                else if(requestType.equalsIgnoreCase("empid")){
                    int empid = Integer.parseInt(req.getRequestURI().split("/")[5]);
                    r = ms.viewReimbursementRequestSingleEmployee(empid);
                    logger.debug("Manager view all reimbursement requests by employee ID");
                }

                ReimbursementResponse rList = new ReimbursementResponse();
                rList.setReimbursement(r);

                try {
                    if(r != null) {
                        resp.setContentType("application/json");
                        resp.getWriter().write(om.writeValueAsString(rList));
                        resp.setStatus(200);
                    }
                } catch  (JsonProcessingException e) {
                }
                catch (IOException ioException) {
                }
            }
        } else{
            resp.setStatus(403);
            resp.getWriter().write(om.writeValueAsString("Manager Not Logged In to Access this Facility"));
        }
    }

}
