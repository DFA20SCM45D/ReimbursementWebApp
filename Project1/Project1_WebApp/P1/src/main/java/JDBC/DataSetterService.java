package JDBC;

import AccountDetails.Account;
import User.Bankuser;

import java.sql.SQLException;

public class DataSetterService {

    private DataSetter dataSetter;

    /**
     * constructor for DataSetterService with dataSetter dependency injection
     * @param dataSetter
     */
    public DataSetterService(DataSetter dataSetter) {
        this.dataSetter = dataSetter;
    }

    /**
     * updates the account table with an updated balance to the account table
     * @param accountNo account number of the bank user
     * @param updatedBalance updated balance
     * @return boolean
     * @throws SQLException
     */
    public boolean setBalanceAccountTable(String accountNo, double updatedBalance) throws SQLException {
        dataSetter.setBalanceAccountTable(accountNo,updatedBalance);
        return true;
    }

    /**
     * update transaction table with each deposit or withdraw method
     * @param accNo account number of a bank user
     * @param transactionID transaction id of each transaction table
     * @param typeOfTransaction type of transaction, credit or debit
     * @param amount transaction amount
     * @param remBalance updated balance after
     * @return boolean
     * @throws SQLException
     */
    public boolean updateTransactionTable(String accNo,String transactionID, String typeOfTransaction, double amount, double remBalance) throws SQLException {
        dataSetter.updateTransactionTable(accNo, transactionID,typeOfTransaction,amount,remBalance);
        return true;
    }

    /**
     * add a new account
     * @param b bankuser object
     * @param a account object
     * @return boolean
     * @throws SQLException
     */
    public boolean addNewAccount(Bankuser b, Account a) throws SQLException {
        dataSetter.addNewAccount(b,a);
        return true;
    }

    /**
     * makes a credit create request
     * @param customerID - customer id number of a bank user
     * @param creditSCore - credit score of a bank user
     * @return boolean
     * @throws SQLException
     */
    public boolean createCreditRequest(String customerID, int creditSCore) throws SQLException {
    if(dataSetter.createCreditRequest(customerID,creditSCore))
    return true;
    else
        return false;
    }

    /**
     * sets credit request status
     * @param cID - customer id number of a bank user
     * @param status - status of the credit request
     * @return boolean
     * @throws SQLException
     */
    public boolean setCreditRequestStatus(String cID, String status) throws SQLException {
        if(dataSetter.setCreditRequestStatus(cID, status))
        return true;
        else
            return false;
    }

}
