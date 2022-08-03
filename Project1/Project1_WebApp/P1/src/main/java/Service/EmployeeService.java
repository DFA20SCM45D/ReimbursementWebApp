package Service;

import JDBC.AuthenticationDao;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.BankAccount;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static Logger logger = LogManager.getLogger(EmployeeService.class.getName());

    /**
     * requests for login of employee
     * @param login loginID of employee
     * @param password password of employee
     * @return Employee object
     */
    public Employee employeeLogin(String login, String password){
        logger.debug("employee login requested");
        return authenticationDao.loginAuthenticationEmployee(login, password);
    }

    /**
     * requests for list of resolved reimbursement requests
     * @param empid Employee ID
     * @return list of resolved reimbursement requests
     */
    public List<Reimbursement> viewResolvedReimbursementRequest(int empid) {
        logger.debug("requested to view resolved reimbursement requests");
        return reimbursementDao.viewResolvedReimbursementRequest(empid);
    }

    /**
     * requests for list of pending reimbursement requests
     * @param empid Employee ID
     * @return list of pending reimbursement requests
     */
    public List<Reimbursement> viewPendingReimbursementRequest(int empid){
        logger.debug("requested to view pending reimbursement requests");
        return reimbursementDao.viewPendingReimbursementRequest(empid);
    }

    /**
     * requests to submit a new reimbursement request
     * @param empid Employee ID
     * @param r Reimbursement request
     * @return true if reimbursement request is submitted
     */
    public boolean submitReimbursementRequest(int empid, Reimbursement r) {
        logger.debug("Requested to submit new reimbursement request");
        return reimbursementDao.submitReimbursementRequest(empid, r);
    }

    /**
     * Requests to get profile information of Employee
     * @param login Login ID of employee
     * @return list of employees with a size of 1
     */
    public List<Employee> viewProfileInformation(int login){
        logger.debug("requested to view profile information");
        return employeeDao.viewProfileInformation(login);
    }

    /**
     * Requests to update profile information of employee
     * @param employee Employee
     * @param empid Employee ID
     * @return true if profile information updated
     */
    public boolean updateProfileInformation(Employee employee, int empid) {
        logger.debug("requested to update profile information");
        return employeeDao.updateProfileInformation(employee, empid);
    }

}
