package Service;

import JDBC.AuthenticationDao;
import JDBC.ConnectionManager;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;
import org.h2.Driver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ManagerServiceTest {
    private ConnectionManager cm;
    private AuthenticationDao authenticationDao;
    private EmployeeDao employeeDao;
    private ReimbursementDao reimbursementDao;
    private ManagerService msAuthentication;
    private ManagerService msEmployee;
    private ManagerService msReimbursement;

    @Before
    public void init(){

        cm = new ConnectionManager("jdbc:h2:mem:test_db;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
                "sa", "", new Driver());

        authenticationDao = new AuthenticationDao(cm);
        employeeDao = new EmployeeDao(cm);
        reimbursementDao = new ReimbursementDao(cm);
        msAuthentication = new ManagerService(authenticationDao);
        msEmployee = new ManagerService(employeeDao);
        msReimbursement = new ManagerService(reimbursementDao);
    }

    @Before
    public void initDb(){

        try {
            Connection c = cm.getConnection();

            String dropTable = "drop table employee";

            String employeeTable = "create table if not exists employee(\n" +
                    "empID serial primary key,\n" +
                    "fName varchar(30) not null,\n" +
                    "lName varchar(30) not null,\n" +
                    "emailID varchar(30) not null,\n" +
                    "login varchar (15) not null,\n" +
                    "password varchar(20) not null,\n" +
                    "managerID int,\n" +
                    "foreign key(managerID) references manager(managerID) on delete set null\n" +
                    ");";

            String managerTable = "create table if not exists manager(\n" +
                    "managerID serial primary key,\n" +
                    "fName varchar(30) not null,\n" +
                    "lName varchar(30) not null,\n" +
                    "emailID varchar(30) not null,\n" +
                    "login varchar(15) not null,\n" +
                    "password varchar(20) not null);";

            String bankAccountTable = "create table if not exists bankAccount(\n" +
                    "bankAccountNo char(9) primary key,\n" +
                    "bankName varchar(20) not null,\n" +
                    "routingNo char(9) not null,\n" +
                    "accType varchar(10) not null,\n" +
                    "empID int not null,\n" +
                    "foreign key(empID) references employee(empID) on delete set null\n" +
                    ");";

            String reimbursementTable = "create table if not exists reimbursement(\n" +
                    "requestID serial primary key,\n" +
                    "empID int not null,\n" +
                    "managerID int,\n" +
                    "amount Double precision not null,\n" +
                    "bankAccountNo char(9) not null,\n" +
                    "status varchar(10)\n" +
                    ");";

            Statement sM = c.createStatement();
            Statement sE = c.createStatement();
            Statement sB = c.createStatement();
            Statement sR = c.createStatement();

            sM.execute(managerTable);
            sE.execute(employeeTable);
            sB.execute(bankAccountTable);
            sR.execute(reimbursementTable);

            String insertEmployee = "insert into employee(fName,lName,emailid,login,\"password\",managerid) values('Ishan', 'Kishan', 'ik@india.com','ik111','ik111',1), ('Sanju', 'Samson', 'ss@india.com','ss121','ss121',1), ('Shreyas', 'Iyer', 'si@india.com','si999','si999',2);";
            String insertManager = "insert into manager(fName, lName, emailid, login, \"password\" ) values ('MS','Dhoni','msd@india.com','msd007','msd007'), ('Virat','Kohli','vk@india.com','vk001','vk001');";
            String insertBankAccount = "insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000111123','chase bank','checking','454000454',1), ('000111999','chase bank','checking','454000454',2), ('000222111','bank of america','checking','787000787',3)";
            String insertReimbursement = "insert into reimbursement(empid, amount, bankaccountno, status) values(1, 500.00, '000111123', 'Pending');";

            PreparedStatement pE = c.prepareStatement(insertEmployee);
            PreparedStatement pM = c.prepareStatement(insertManager);
            PreparedStatement pB = c.prepareStatement(insertBankAccount);
            PreparedStatement pR = c.prepareStatement(insertReimbursement);
            pM.executeUpdate();
            pE.executeUpdate();
            pB.executeUpdate();
            pR.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void managerShouldBeAbleToLogin(){
        Manager m = msAuthentication.managerLogin("msd007","msd007");
        Assert.assertEquals("Manager could not login",m.getManagerID(),1);
        Assert.assertEquals("Manager could not login",m.getLoginID(),"msd007");
    }

    @Test
    public void shouldReturnAllEmployee(){
        List<Employee> l = msEmployee.viewAllEmployees();
        Assert.assertEquals("DID NOT RETURN ALL EMPLOYEE",l.size(),3);
    }

    @Test
    public void shouldBeAbleToProcessReimbursementRequest(){

        boolean p = msReimbursement.processReimbursementRequest(1,1,"Approved");
        List<Reimbursement> r = msReimbursement.viewResolvedRequestWithManager();
        Assert.assertEquals("Manager could not process Request",p,true);
        Assert.assertEquals("Manager could not process Request",r.get(0).getStatus(),"Approved");
        Assert.assertEquals("Manager could not process request",r.get(0).getManagerID(),1);
    }

    @Test
    public void shouldBeAbleToShowPendingReimbursementRequest(){

        List<Reimbursement> r = msReimbursement.viewPendingRequestAllEmployee();
        Assert.assertEquals("Could not show all pending requests",r.size(),1);
        Assert.assertEquals("Could not show all pending requests",r.get(0).getEmpID(),1);
    }

    @Test
    public void shouldBeAbleToReturnReimbursementsByEmployee(){
        List<Reimbursement> r = msReimbursement.viewReimbursementRequestSingleEmployee(1);
        Assert.assertEquals("Could not show Reimbursement Reqests as per employee",r.size(),1);
        Assert.assertEquals("Could not show Reimbursement Reqests as per employee",r.get(0).getReimbursmentID(),1);
        Assert.assertEquals("Could not show Reimbursement Reqests as per employee",r.get(0).getEmpID(),1);

    }

    @After
    public void exit(){

        try {
            Connection c = cm.getConnection();

            String dropEmployee = "drop table employee";
            String dropManager = "drop table manager";
            String dropBankAccount = "drop table bankAccount";
            String dropReimbursement = "drop table reimbursement";

            Statement s = c.createStatement();
            s.execute(dropReimbursement);
            s.execute(dropBankAccount);
            s.execute(dropEmployee);
            s.execute(dropManager);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
