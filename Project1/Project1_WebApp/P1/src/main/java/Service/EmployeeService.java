package Service;

import JDBC.AuthenticationDao;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;

import java.util.List;

public class EmployeeService {

    private int systemState;

    public int getSystemState() {
        return systemState;
    }

    public void setSystemState(int systemState) {
        this.systemState = systemState;
    }

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

        Employee e = authenticationDao.loginAuthenticationEmployee(login, password);
        return e;

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


    public List<Employee> viewProfileInformation(int login){ //needs login id from servlet

        return employeeDao.viewProfileInformation(login); //pass in login ID from servlet

    }

    public boolean updateProfileInformation(Employee employee, int empid) {
        return employeeDao.updateProfileInformation(employee, empid);
    }

    public boolean resetPassword() {
        return true;
    }

}
