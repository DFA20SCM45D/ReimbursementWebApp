package JDBC;

import AccountDetails.Account;
import User.Bankuser;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataSetter {
    private final ConnectionManager connectionManager;
    public DataSetter(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Driver.class.getName());

    /**
     * updates the balance to the account in account table in database
     *
     * @param accountNo account number of the bank user
     * @param updatedBalance new balance after deposit or withdraw activity
     * @return boolean if account table is updated
     * @throws SQLException
     */
    public boolean setBalanceAccountTable(String accountNo, double updatedBalance) throws SQLException {

        Connection connection = null;

        try{
        String QUERY = "UPDATE account SET balance = ? where accountno = ? ";


                connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(QUERY);

                int rs = 0;

                stmt.setDouble(1,updatedBalance);
                stmt.setString(2,accountNo);

                rs = stmt.executeUpdate();
                rs++;
                if(rs > 0) { logger.debug("balance updated"); }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return true;
    }

    /**
     * Updates the transaction table after each deposit and withdraw method
     *
     * @param accNo account number of the bank user
     * @param transactionID transaction id of each transaction
     * @param typeOfTransaction transaction type, credit if deposit or debit if withdraw from account
     * @param amount transaction amount
     * @param remBalance new balance after transaction
     * @return boolean if transaction table is updated
     * @throws SQLException
     */
    public boolean updateTransactionTable(String accNo,String transactionID, String typeOfTransaction, double amount, double remBalance) throws SQLException {

        Connection connection = null;

        try{

        String QUERY = "INSERT INTO transaction(accountno, transactionid, typeoftransaction, amount, remainingbalance) VALUES (?,?,?,?,?) ";


                 connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(QUERY);

                int rs = 0;

                stmt.setString(1,accNo);
                stmt.setString(2,transactionID);
                stmt.setString(3,typeOfTransaction);
                stmt.setDouble(4,amount);
                stmt.setDouble(5,remBalance);

                rs = stmt.executeUpdate();
                rs++;
            if(rs > 0) { logger.debug("transaction added"); }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return true;
    }

    /**
     * add a new account to the associated bank user
     *
     * @param b Bank user to whom account is to be added
     * @param a Account details of a new account
     * @return returns boolean after adding account
     * @throws SQLException
     */
    public boolean addNewAccount(Bankuser b, Account a) throws SQLException {

        Connection connection = null;
        try{

        String QUERY = "INSERT INTO bankuser(id,customerid, name, emailid, loginid, password, accountno, creditscore) VALUES (?,?,?,?,?,?,?,?) ";
        String QUERY1 = "INSERT INTO account(accountno, accounttype, balance) VALUES (?,?,?)";

                connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(QUERY);
                 PreparedStatement stmt1 = connection.prepareStatement(QUERY1);

                int rs = 0;
                int rs1 = 0;

                stmt.setInt(1,b.getIdPointerDB());
                stmt.setString(2,b.getCustomerID());
                stmt.setString(3,b.getName());
                stmt.setString(4,b.getEmailID());
                stmt.setString(5,b.getLoginID());
                stmt.setString(6,b.getPassword());
                stmt.setString(7, b.getAccountNo());
                stmt.setInt(8,b.getCreditScore());
                stmt1.setString(1,a.getAccountNo());
                stmt1.setString(2, a.getAccountType());
                stmt1.setDouble(3,a.getAccountBalance());

                rs = stmt.executeUpdate();
                rs1 = stmt1.executeUpdate();
                rs++;
                rs1++;
                if(rs > 0 && rs1 > 0)  { logger.debug("New account added to the bankuser"); }

            } catch (SQLException e) {
                e.printStackTrace();
            } return true;
        }

    /**
     * saves the credit request in the database
     * @param customerID - customer id of a bank user
     * @param creditScore - credit score of a bank user
     * @return boolean
     * @throws SQLException
     */
        public boolean createCreditRequest(String customerID, int creditScore) throws SQLException {

        Connection connection = null;
        try{
            String sql = "insert into creditlinerequests(customerid, creditscore, creditapprovalstatus) values (?,?,?)";

            connection = connectionManager.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);

            int rs = 0;

            psmt.setString(1, customerID);
            psmt.setInt(2,creditScore);
            psmt.setString(3,"Pending");
            rs = psmt.executeUpdate();
            rs++;
        } catch (SQLException e) {
            e.printStackTrace();
        } return true;

        }

    /**
     * manually updates the credit request status
     * @param cID - customer id of a bank user
     * @param status - credit score of a bank user
     * @return boolean
     * @throws SQLException
     */
        public boolean setCreditRequestStatus(String cID, String status) throws SQLException {
        Connection connection = null;

        try{
            String sql = "update creditlinerequests set creditapprovalstatus = ? where customerid = ?";

            connection = connectionManager.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);

            int rs = 0;

            psmt.setString(1, status);
            psmt.setString(2,cID);

            rs = psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } return true;
        }


}