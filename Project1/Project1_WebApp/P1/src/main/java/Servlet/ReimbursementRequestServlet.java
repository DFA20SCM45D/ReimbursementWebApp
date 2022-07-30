package Servlet;

import JDBC.ConnectionManager;
import JDBC.ReimbursementDao;
import Model.Reimbursement;
import Service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Reimbursement reimbursement = om.readValue(req.getInputStream(), Reimbursement.class);

        System.out.println(reimbursement.getReimburseAmount());

        ConnectionManager cm = (ConnectionManager) getServletContext().getAttribute("database");

        ReimbursementDao reimbursementDao = new ReimbursementDao(cm);
        EmployeeService es = new EmployeeService(reimbursementDao);

        es.submitReimbursementRequest(reimbursement.getEmpID(), reimbursement);

        resp.setStatus(201);
    }
}
