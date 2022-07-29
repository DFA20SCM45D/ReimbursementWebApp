package Model;

public class Employee extends User {

    private int empID;
    private int managerID;

    private String bankAccount;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String emailID, String loginID, String password, int empID, int managerID) {
        super(firstName, lastName, emailID, loginID, password);
        this.empID = empID;
        this.managerID = managerID;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
