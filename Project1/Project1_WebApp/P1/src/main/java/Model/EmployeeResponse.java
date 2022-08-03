package Model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class EmployeeResponse
 * To generate "Employee" in JSON response
 */

public class EmployeeResponse {

    private List<Employee> employee;

    public EmployeeResponse() {
    }

    public void setEmpList(List<Employee> empList) {
        this.employee = empList;
    }
}
