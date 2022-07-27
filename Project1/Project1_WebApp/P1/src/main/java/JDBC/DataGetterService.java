package JDBC;

import AccountDetails.Account;
import AccountDetails.CreditRequest;
import AccountDetails.Transaction;
import DataStructure.MySinglyLinkedList;
import User.Bankuser;

import java.sql.SQLException;

public class DataGetterService{

    private DataGetter dataGetter;

    /**
     * constructor for DataGetterService with a dependency injection to the dataGetter class
     *
     * @param dataGetter
     */

    public DataGetterService(DataGetter dataGetter) {
        this.dataGetter = dataGetter;
    }

    /**
     * gets last bank user id
     * @return int last bank user id
     */
    public int getLastBankUserID(){
        return dataGetter.getLastBankUserID();
    }

    /**
     * get a bank user object from the database using dataGetter class
     * @param customerid
     * @return bankuser
     * @throws SQLException
     */
    public Bankuser getBankUserByCustomerID(String customerid) throws SQLException {
        return dataGetter.getBankUserByCustomerID(customerid);
    }

    /**
     * get a last transaction id
     * @return String transaction id
     * @throws SQLException
     */
    public String getLastTransactionID() throws SQLException {
        return dataGetter.getLastTransactionID();
    }

    /**
     * get a list of transaction as per account id
     * @param accountNo
     * @return a list of transaction
     * @throws SQLException
     */

    public MySinglyLinkedList<Transaction> getTransactionDB(String accountNo) throws SQLException {
        return dataGetter.getTransactionDB(accountNo);
    }

    /**
     * get account object
     * @param accountno
     * @return account object
     * @throws SQLException
     */
    public Account getAccountDB(String accountno) throws SQLException {
        return dataGetter.getAccountDB(accountno);
    }

    /**
     * get a list of bankuser objects with all account numbers registered
     * @param login
     * @param password
     * @return list of a bankuser object
     * @throws SQLException
     */
    public MySinglyLinkedList<Bankuser> ifLogInPasswordMatch(String login, String password) throws SQLException {
        return dataGetter.ifLogInPasswordMatch(login, password);
    }

    /**
     * gets list of all pending credit requests
     * @return boolean
     * @throws SQLException
     */
    public MySinglyLinkedList<CreditRequest> getCreditRequests() throws SQLException {
        return dataGetter.getCreditRequests();
    }


}
