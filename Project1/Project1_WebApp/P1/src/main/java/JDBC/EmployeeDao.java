package JDBC;

import Model.Employee;
import Model.Reimbursement;
import Service.ManagerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private ConnectionManager cm;

    public EmployeeDao(ConnectionManager cm) {
        this.cm = cm;
    }

    public List<Employee> viewAllEmployees(){

        Connection connection = null;
        List<Employee> employeeList = new ArrayList<>();

        try{

            String QUERY = "select * from employee";

            connection = cm.getConnection();
            PreparedStatement stmt = connection.prepareStatement(QUERY);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                //Display values
                System.out.println("\n");
                System.out.print("ID: " + rs.getInt("empid"));
                System.out.print(", Name: " + rs.getString("fname"));
                System.out.print(", Account No: " + rs.getString("lname"));

                Employee e = new Employee();
                e.setEmpID(rs.getInt("empid"));
                e.setFirstName(rs.getString("fname"));
                e.setLastName(rs.getString("lname"));
                employeeList.add(e);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeList;
    }

    public Employee viewProfileInformation(String login){

        Connection connection = null;
        Employee employee = new Employee();

        try{
            String QUERY = "select * from employee where login = ?";

            connection = cm.getConnection();
            PreparedStatement stmt = connection.prepareStatement(QUERY);

            stmt.setString(1,login.trim());
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                //Display values
                System.out.println("\n");
                System.out.print("ID: " + rs.getInt("empid"));
                System.out.print(", Name: " + rs.getString("fname"));
                System.out.print(", Account No: " + rs.getString("lname"));

                employee.setEmpID(rs.getInt("empid"));
                employee.setFirstName(rs.getString("fname"));
                employee.setLastName(rs.getString("lname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    public boolean updateProfileInformation(Employee e) {

        Connection connection = null;

        try {
            String QUERY = "update table employee(fname, lname, emailid, password) values(?,?,?,?)";

            connection = cm.getConnection();
            PreparedStatement stmt = connection.prepareStatement(QUERY);

            stmt.setString(1,e.getFirstName());
            stmt.setString(2,e.getLastName());
            stmt.setString(3,e.getEmailID());
            stmt.setString(4,e.getPassword());

            return true;

        } catch (SQLException es) {
            throw new RuntimeException(es);

        }
    }
}
