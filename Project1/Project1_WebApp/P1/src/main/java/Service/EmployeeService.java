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

    /**
     * requests for login of employee
     * @param login loginID of employee
     * @param password password of employee
     * @return Employee object
     */
    public Employee employeeLogin(String login, String password){
        return authenticationDao.loginAuthenticationEmployee(login, password);
    }

    /**
     * requests for list of resolved reimbursement requests
     * @param empid Employee ID
     * @return list of resolved reimbursement requests
     */
    public List<Reimbursement> viewResolvedReimbursementRequest(int empid) {
        return reimbursementDao.viewResolvedReimbursementRequest(empid);
    }

    /**
     * requests for list of pending reimbursement requests
     * @param empid Employee ID
     * @return list of pending reimbursement requests
     */
    public List<Reimbursement> viewPendingReimbursementRequest(int empid){
        return reimbursementDao.viewPendingReimbursementRequest(empid);
    }

    /**
     * requests to submit a new reimbursement request
     * @param empid Employee ID
     * @param r Reimbursement request
     * @return true if reimbursement request is submitted
     */
    public boolean submitReimbursementRequest(int empid, Reimbursement r) {
        return reimbursementDao.submitReimbursementRequest(empid, r);
    }

    /**
     * Requests to get profile information of Employee
     * @param login Login ID of employee
     * @return list of employees with a size of 1
     */
    public List<Employee> viewProfileInformation(int login){
        return employeeDao.viewProfileInformation(login);
    }

    /**
     * Requests to update profile information of employee
     * @param employee Employee
     * @param empid Employee ID
     * @return true if profile information updated
     */
    public boolean updateProfileInformation(Employee employee, int empid) {
        return employeeDao.updateProfileInformation(employee, empid);
    }

}
