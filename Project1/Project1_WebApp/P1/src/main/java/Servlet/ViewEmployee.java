package Servlet;

import JDBC.ConnectionManager;
import JDBC.DataGetterService;
import Model.Employee;
import Model.EmployeeResponse;
import Service.ManagerService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name="ViewEmployee", initParams = {})
public class ViewEmployee extends HttpServlet {

    private ObjectMapper om = new ObjectMapper();
    private static final ConnectionManager cm = new ConnectionManager("jdbc:postgresql://java-revature-dhairya.cibikjimihdn.us-east-2.rds.amazonaws.com:5432/project1", "postgres", "dhairyadixit", new org.postgresql.Driver());
    private static DataGetterService dgs = new DataGetterService(cm);

    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Servlet");
        om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI());

        ManagerService ms = new ManagerService(dgs);
        List<Employee> employee = new ArrayList<>();
        employee.addAll(ms.viewAllEmployees());

        EmployeeResponse eList = new EmployeeResponse();
        eList.setEmpList(employee);


        resp.setContentType("application/json");
        resp.getWriter().write(om.writeValueAsString(eList));
        resp.setStatus(200);
        return;



    }
}
