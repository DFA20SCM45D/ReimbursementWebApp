package JDBC;



import Model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataGetterService{

    private ConnectionManager cm;

    public DataGetterService(ConnectionManager cm) {
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
}
