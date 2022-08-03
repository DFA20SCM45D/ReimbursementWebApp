package JDBC;

import Model.BankAccount;
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

    /**
     * To display list of All employees
     * @return List of employees
     */

    public List<Employee> viewAllEmployees() {

        List<Employee> employeeList = new ArrayList<>();
        Connection connection = null;

            try {

                String QUERY = "select * from employee";

                connection = cm.getConnection();
                PreparedStatement stmt = connection.prepareStatement(QUERY);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
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

    /**
     * Gets employee profile information from database
     * @param login Employee id
     * @return Employee list of size 1
     */
    public List<Employee> viewProfileInformation(int login){

        Connection connection = null;
        List<Employee> eList = new ArrayList<>();

        try{
            String QUERY = "select * from employee join bankaccount on employee.empid = ? AND bankaccount.empid = ?";

            connection = cm.getConnection();
            PreparedStatement stmt = connection.prepareStatement(QUERY);

            stmt.setInt(1,login);
            stmt.setInt(2,login);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Employee employee = new Employee();
                //Display values
                System.out.println("\n");
                System.out.print("ID: " + rs.getInt("empid"));
                System.out.print(", Name: " + rs.getString("fname"));
                System.out.print(", Account No: " + rs.getString("lname"));

                employee.setEmpID(rs.getInt("empid"));
                employee.setFirstName(rs.getString("fname"));
                employee.setLastName(rs.getString("lname"));
                employee.setManagerID(rs.getInt("managerid"));
                employee.setEmailID(rs.getString("emailid"));
                employee.setBankAccount(rs.getString("bankaccountno"));
                eList.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eList;
    }

    /**
     * Updates the profile information in database
     * @param e employee
     * @param empid employee ID
     * @return true if profile details updated
     */

    public boolean updateProfileInformation(Employee e, int empid) {

        Connection connection = null;

        try {
            String QUERY = "update employee set fname = ?, lname = ?, emailid = ?, password = ? where empid = ?";

            connection = cm.getConnection();
            PreparedStatement stmt = connection.prepareStatement(QUERY);

            stmt.setString(1,e.getFirstName());
            stmt.setString(2,e.getLastName());
            stmt.setString(3,e.getEmailID());
            stmt.setString(4,e.getPassword());
            stmt.setInt(5,empid);

            stmt.executeUpdate();

           // updateBankAccountDetails(empid, b, connection);

            return true;

        } catch (SQLException es) {
            throw new RuntimeException(es);

        }
    }

    /**
     * Updates the Bank Account details of the Employee
     * @param empid Employee ID
     * @param b BankAccount object
     * @param connection connection to database
     * @return true if bank account detail is updated
     */
    private boolean updateBankAccountDetails(int empid, BankAccount b, Connection connection){

        try {
            String QUERY = "update bankaccount set bankaccountno = ?, bankname = ?, routingno = ?, acctype = ? where empid = ?";

            connection = cm.getConnection();
            PreparedStatement stmt = connection.prepareStatement(QUERY);

            stmt.setString(1,b.getAccountNo());
            stmt.setString(2,b.getBankName());
            stmt.setString(3,b.getRoutingNo());
            stmt.setString(4,b.getAccountType());
            stmt.setInt(5,empid);

            stmt.executeUpdate();

            return true;

        } catch (SQLException es) {
            throw new RuntimeException(es);

        }

    }
}
