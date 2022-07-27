package Model;

public class Employee extends User {

    private int empID;
    private int managerID;

    private BankAccount bankAccount;

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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
