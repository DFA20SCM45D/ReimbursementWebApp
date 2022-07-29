package Service;

import JDBC.AuthenticationDao;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;

import java.util.ArrayList;
import java.util.List;

public class ManagerService {
    private EmployeeDao employeeDao;

    public ManagerService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    private ReimbursementDao reimbursementDao;

    public ManagerService(ReimbursementDao reimbursementDao) {
        this.reimbursementDao = reimbursementDao;
    }

    private AuthenticationDao authenticationDao;

    public ManagerService(AuthenticationDao authenticationDao) {
        this.authenticationDao = authenticationDao;
    }

    public Manager managerLogin(String login, String password){
        return authenticationDao.loginAuthenticationManager(login, password);
    }
    public boolean processReimbursementRequest(int requestid, int managerid, String status){

        return reimbursementDao.processReimbursementRequest(requestid, managerid, status);

    }

    public List<Reimbursement> viewPendingRequestAllEmployee(){

        return reimbursementDao.ViewPendingRequestAllEmployee();

    }

    public List<Reimbursement> viewResolvedRequestWithManager() {
        return reimbursementDao.viewResolvedReimbursementRequest();
    }

    public List<Employee> viewAllEmployees(){

        return employeeDao.viewAllEmployees();

    }

    public List<Reimbursement> viewReimbursementRequestSingleEmployee(int empid){
            List<Reimbursement> rList = new ArrayList<>();
            rList.addAll(reimbursementDao.viewPendingReimbursementRequest(empid));
            rList.addAll(reimbursementDao.viewResolvedReimbursementRequest(empid));
            return rList;
    }

    public boolean registerNewEmployee() {
        return true;

    }
}
