package Service;

import JDBC.AuthenticationDao;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static Logger logger = LogManager.getLogger(EmployeeService.class.getName());
    /**
     * Requests for manager login authentication
     * @param login loginID of manager
     * @param password password of manager
     * @return Manager object
     */
    public Manager managerLogin(String login, String password){
        logger.debug("requested to authenticate manager login");
        return authenticationDao.loginAuthenticationManager(login, password);
    }

    /**
     * Requests to process reimbursement requests
     * @param requestid reimbursement request ID
     * @param managerid managerID of manager
     * @param status status of reimbursement request
     * @return true if reimbursement request is approved/denied
     */
    public boolean processReimbursementRequest(int requestid, int managerid, String status){
        logger.debug("requested to process reimbursement request");
        return reimbursementDao.processReimbursementRequest(requestid, managerid, status);
    }

    /**
     * requests to view pending reimbursement requests
     * @return list of pending reimbursement requests
     */
    public List<Reimbursement> viewPendingRequestAllEmployee(){
        logger.debug("requested to view pending requests of all employee");
        return reimbursementDao.ViewPendingRequestAllEmployee();
    }

    /**
     * requests to view resolved reimbursement requests
     * @return list of resolved reimbursement requests
     */
    public List<Reimbursement> viewResolvedRequestWithManager() {
        logger.debug("requested to view resolved requests of all employee");
        return reimbursementDao.viewResolvedReimbursementRequest();
    }

    /**
     * requests to return all employee
     * @return list of all employees
     */
    public List<Employee> viewAllEmployees(){
        logger.debug("Requested to view all employees");
        return employeeDao.viewAllEmployees();
    }

    /**
     * requests to view reimbursement request by Employee ID
     * @param empid Employee ID
     * @return list of reimbursement request of an Employee
     */
    public List<Reimbursement> viewReimbursementRequestSingleEmployee(int empid){
            List<Reimbursement> rList = new ArrayList<>();
            rList.addAll(reimbursementDao.viewPendingReimbursementRequest(empid));
            rList.addAll(reimbursementDao.viewResolvedReimbursementRequest(empid));
            logger.debug("requested to view reimbursement request by employee");
            return rList;
    }

}
