package Model;

import java.sql.Date;

public class Reimbursement {

    private int reimbursementID;
    private double reimburseAmount;
    private String bankAccount;
    private int empID;
    private int managerID;
    private String status;

    public Reimbursement() {
    }

    public Reimbursement(int reimbursementID, double reimburseAmount, String bankAccount, int empID, int managerID, String status) {
        this.reimbursementID = reimbursementID;

        this.reimburseAmount = reimburseAmount;
        this.bankAccount = bankAccount;
        this.empID = empID;
        this.managerID = managerID;
        this.status = status;
    }

    public Reimbursement(int reimbursementID, double reimburseAmount, String bankAccount, int empID, String status) {
        this.reimbursementID = reimbursementID;

        this.reimburseAmount = reimburseAmount;
        this.bankAccount = bankAccount;
        this.empID = empID;
        this.status = status;
    }

    public int getReimbursmentID() {
        return reimbursementID;
    }

    public void setReimbursmentID(int reimbursmentID) {
        this.reimbursementID = reimbursmentID;
    }

    public double getReimburseAmount() {
        return reimburseAmount;
    }

    public void setReimburseAmount(double reimburseAmount) {
        this.reimburseAmount = reimburseAmount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
