package Service;

import JDBC.AuthenticationDao;
import JDBC.ConnectionManager;
import JDBC.EmployeeDao;
import JDBC.ReimbursementDao;
import Model.BankAccount;
import Model.Employee;
import Model.Manager;
import Model.Reimbursement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.h2.Driver;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeServiceTest {

private ConnectionManager cm;
private AuthenticationDao authenticationDao;
private EmployeeDao employeeDao;
private ReimbursementDao reimbursementDao;
private EmployeeService esAuthentication;
private EmployeeService esEmployee;
private EmployeeService esReimbursement;

    @Before
    public void init(){

        cm = new ConnectionManager("jdbc:h2:mem:test_db;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
                "sa", "", new Driver());

        authenticationDao = new AuthenticationDao(cm);
        employeeDao = new EmployeeDao(cm);
        reimbursementDao = new ReimbursementDao(cm);
        esAuthentication = new EmployeeService(authenticationDao);
        esEmployee = new EmployeeService(employeeDao);
        esReimbursement = new EmployeeService(reimbursementDao);
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
            String inserBankAccount = "insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000111123','chase bank','checking','454000454',1), ('000111999','chase bank','checking','454000454',2), ('000222111','bank of america','checking','787000787',3)";

            PreparedStatement pE = c.prepareStatement(insertEmployee);
            PreparedStatement pM = c.prepareStatement(insertManager);
            PreparedStatement pB = c.prepareStatement(inserBankAccount);
            pM.executeUpdate();
            pE.executeUpdate();
            pB.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void employeeShouldLogIN(){
        Employee e = esAuthentication.employeeLogin("ik111","ik111");
        Assert.assertEquals("employee not logged in", "ik111",e.getLoginID());
        Assert.assertEquals("Employee not logged in","Ishan",e.getFirstName());
    }

    @Test
    public void employeeShouldNotLogIn(){
        Employee e = esAuthentication.employeeLogin("ik1111","ik111");
        Assert.assertEquals("employee logged in", null,e.getLoginID());
        Assert.assertEquals("Employee logged in",null,e.getFirstName());
    }

    @Test
    public void shouldAbleToSubmitReimbursementRequest(){
        Reimbursement r = new Reimbursement(1,500.50,"000111123","Pending");
        Boolean expected = esReimbursement.submitReimbursementRequest(1,r);
        Assert.assertEquals("could not submit request",expected,true);
    }

    @Test
    public void shouldBeAbleToViewProfileInformation(){

        List<Employee> l = employeeDao.viewProfileInformation(1);
        Assert.assertEquals("Did not show profile information", l.get(0).getFirstName(),"Ishan");

    }

    @Test
    public void shouldShowPendingReimbursementRequest(){
        Reimbursement r = new Reimbursement(1,500.50,"000111123","Pending");
        Boolean expected = esReimbursement.submitReimbursementRequest(1,r);
        List<Reimbursement> lr = esReimbursement.viewPendingReimbursementRequest(1);
        Assert.assertEquals("Did not show pending requests",lr.get(0).getReimburseAmount(),500.50,0.0005);
        Assert.assertEquals("Did not show pending requests",lr.get(0).getReimbursmentID(),1);
    }

    @Test
    public void shouldReturnProfileInformation(){
        List<Employee> e = esEmployee.viewProfileInformation(1);
        Assert.assertEquals("Did not return profile information",e.get(0).getFirstName(),"Ishan");
        Assert.assertEquals("Did not return profile information",e.get(0).getLastName(),"Kishan");
        Assert.assertEquals("Did not return profile information",e.get(0).getManagerID(),1);
        Assert.assertEquals("Did not return profile information",e.get(0).getBankAccount(),"000111123");
    }

    @Test
    public void shouldUpdateEmployeeProfileInformation(){
        Employee newEmp = new Employee("Surya","Yadav","sky@india.com","sky666","999888777");
      //  BankAccount newBA = new BankAccount("999888777","015123142","Saving","5th2nd");
        boolean u = esEmployee.updateProfileInformation(newEmp, 1);
        Assert.assertEquals("Could not Update profile information",u,true);
        Assert.assertEquals("Could not Update profile information",esEmployee.viewProfileInformation(1).get(0).getFirstName(),newEmp.getFirstName());
       // Assert.assertEquals("Could not Update profile information",esEmployee.viewProfileInformation(1).get(0).getBankAccount(),newBA.getAccountNo());
    }

    @Test
    public void shouldShowResolvedReimbursementRequest(){
        Reimbursement r1 = new Reimbursement(1,500.50,"000111123","Pending");
        Boolean expected = esReimbursement.submitReimbursementRequest(1,r1);
        Reimbursement r2 = new Reimbursement(1,500.50,"000111123","Pending");
        esReimbursement.submitReimbursementRequest(1,r2);
        ManagerService ms = new ManagerService(reimbursementDao);
        ms.processReimbursementRequest(1,1,"Approved");
        List<Reimbursement> lr = esReimbursement.viewPendingReimbursementRequest(1);
        lr.addAll(esReimbursement.viewResolvedReimbursementRequest(1));

       Assert.assertEquals("Did not show pending requests",lr.get(1).getStatus(),"Approved");
        Assert.assertEquals("Did not show pending requests",lr.size(),2);
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
