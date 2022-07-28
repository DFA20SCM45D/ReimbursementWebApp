package Service;

import JDBC.DataGetterService;
import Model.Employee;

import java.util.List;

public class ManagerService {


    DataGetterService dgs;

    public ManagerService(DataGetterService dgs) {
        this.dgs = dgs;
    }

    public boolean processReimbursementRequest(){

        return true;
    }

    public void viewPendingRequestAllEmployee(){


    }

    public void viewResolvedRequestWithManager() {

    }

    public List<Employee> viewAllEmployees(){

        return dgs.viewAllEmployees();

    }

    public void viewReimbursementRequestSingleEmployee(){

    }

    public boolean registerNewEmployee() {
        return true;

    }
}
