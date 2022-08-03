package Servlet;

import JDBC.AuthenticationDao;
import JDBC.ConnectionManager;
import Model.Employee;
import Model.Manager;
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

@WebServlet(name ="AuthenticationManager" , initParams = {})
public class ManagerAuthenticationServlet extends HttpServlet{

        private ObjectMapper om = new ObjectMapper();

        @Override
        public void init() throws ServletException {
            out.println("Initializing Servlet");
            om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        }

    /**
     * Request to authenticate Manager Login
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            HttpSession userSession = req.getSession(true);

            Manager manager = om.readValue(req.getInputStream(), Manager.class);

            ConnectionManager cm = (ConnectionManager) getServletContext().getAttribute("database");

            AuthenticationDao authenticationDao = new AuthenticationDao(cm);
            ManagerService ms = new ManagerService(authenticationDao);

            Manager m = ms.managerLogin(manager.getLoginID(), manager.getPassword());

            userSession.setAttribute("managerid", m.getManagerID());

            if (m != null) {

                out.println("Login Successful" + m.getFirstName() + " " + m.getLastName());
                Cookie cookieM = new Cookie("loginM", "true");
                cookieM.setMaxAge(7 * 24 * 60 * 60);
                resp.addCookie(cookieM);

            }

            resp.setStatus(201);
        }

}
