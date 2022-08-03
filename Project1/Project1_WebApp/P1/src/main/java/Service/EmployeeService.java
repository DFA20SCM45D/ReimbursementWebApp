package Service;

import JDBC.AuthenticationDao;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.BankAccount;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;

import java.util.List;

public class EmployeeService {

    private ReimbursementDao reimbursementDao;
    public EmployeeService(ReimbursementDao reimbursementDao) {
        this.reimbursementDao = reimbursementDao;
    }

    private EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    private AuthenticationDao authenticationDao;

    public EmployeeService(AuthenticationDao authenticationDao) {
        this.authenticationDao = authenticationDao;
    }

    public Employee employeeLogin(String login, String password){
        return authenticationDao.loginAuthenticationEmployee(login, password);
    }

    public List<Reimbursement> viewResolvedReimbursementRequest(int empid) {
        return reimbursementDao.viewResolvedReimbursementRequest(empid);
    }

    public List<Reimbursement> viewPendingReimbursementRequest(int empid){
        return reimbursementDao.viewPendingReimbursementRequest(empid);
    }

    public boolean submitReimbursementRequest(int empid, Reimbursement r) {
        return reimbursementDao.submitReimbursementRequest(empid, r);
    }


    public List<Employee> viewProfileInformation(int login){
        return employeeDao.viewProfileInformation(login);
    }

    public boolean updateProfileInformation(Employee employee, int empid) {
        return employeeDao.updateProfileInformation(employee, empid);
    }

}
