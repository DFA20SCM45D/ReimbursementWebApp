package JDBC;

import AccountDetails.Account;
import AccountDetails.CreditRequest;
import AccountDetails.Transaction;
import DataStructure.MySinglyLinkedList;
import User.Bankuser;
import org.apache.logging.log4j.LogManager;

import java.sql.*;

public class DataGetter {
        private final ConnectionManager connectionManager; //check final if creates problem with test connection
        public DataGetter(ConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }

        private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Driver.class.getName());

        /**
         * checks for login and password in database to login
         *
         * @param login Login ID of the bank user
         * @param password password of the bank user
         * @return List of Bankuser from a bankuser table with all account numbers assigned to particular bankuser
         * @throws SQLException
         */
       public MySinglyLinkedList<Bankuser> ifLogInPasswordMatch(String login, String password) throws SQLException {
            Connection connection = null;

           MySinglyLinkedList<Bankuser> bArr = new MySinglyLinkedList<>();

           try{
            String QUERY = "SELECT id, customerid, name, emailid, loginid, password, accountno, creditscore FROM bankuser where loginid = ? AND password = ? ";

                    connection = connectionManager.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(QUERY);

                    stmt.setString(1,login.trim());
                    stmt.setString(2,password.trim());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        //Display values
                        System.out.println("\n");
                        System.out.print("ID: " + rs.getInt("id"));
                        System.out.print(", Name: " + rs.getString("name"));
                        System.out.print(", Account No: " + rs.getString("accountno"));
                        Bankuser b = new Bankuser();

                            b.setIdPointerDB(rs.getInt("id"));
                            b.setCustomerID(rs.getString("customerid"));
                            b.setName(rs.getString("name"));
                            b.setEmailID(rs.getString("emailid"));
                            b.setLoginID(rs.getString("loginid"));
                            b.setPassword(rs.getString("password"));
                            b.setAccountNo((rs.getString("accountno")));
                            b.setCreditScore((rs.getInt("creditscore")));
                            logger.debug("Bank user with matching login and password added to the list");
                            bArr.add(b);
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
           logger.debug("All entry of bank user's account with same login password are returned");
           return bArr;
            }

        /**
         * Returns account object based on account number form account database
         *
         * @param accountno account number of the bank user
         * @return account object
         * @throws SQLException
         */
        public Account getAccountDB(String accountno) throws SQLException{

            Connection connection = null;
            Account account = new Account();

            try{
            String QUERY = "SELECT accountno, balance, accounttype FROM account where accountno = ? ";

                    connection = connectionManager.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(QUERY);

                    stmt.setString(1,accountno.trim());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        System.out.println("\n");
                        account.setAccountNo(rs.getString("accountno"));
                        account.setAccountBalance(rs.getDouble("balance"));
                        account.setAccountType(rs.getString("accounttype"));
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            logger.debug("Account object corresponding to account Number is returned");
            return account;
            }

        /**
         * Returns list of transaction from transaction table as per account number
         *
         * @param accountNo account number of the bank user
         * @return list of transaction of a particular account number
         * @throws SQLException
         */

            public MySinglyLinkedList<Transaction> getTransactionDB(String accountNo) throws SQLException{

            Connection connection = null;
            MySinglyLinkedList<Transaction> transactionArr = new MySinglyLinkedList<>();

           try
           {
               String QUERY = "SELECT * FROM transaction where accountno = ?";
               connection = connectionManager.getConnection();
               PreparedStatement stmt = connection.prepareStatement(QUERY);

                    stmt.setString(1,accountNo.trim());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        //Display values
                        Transaction transaction = new Transaction();
                        System.out.println("\n");
                        transaction.setAccountNo(rs.getString("accountno"));
                        transaction.setTransactionID(rs.getString("transactionid"));
                        transaction.setTypeOfTransaction(rs.getString("typeoftransaction"));
                        transaction.setTransactionAmount(rs.getDouble("amount"));
                        transaction.setRemainingBalance(rs.getDouble("remainingbalance"));
                        transactionArr.add(transaction);
                        if(transactionArr != null) { logger.debug("Transaction object added to the list");}
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
           logger.debug("Returned transaction list");
           return transactionArr;
        }

        /**
         * get last transaction id
         *
         * @return last transaction id
         * @throws SQLException
         */

        public String getLastTransactionID() throws SQLException{

            Connection connection = null;
            String transactionID = null;
            try{

            String QUERYSORT = "SELECT * FROM transaction ORDER BY transactionid";
            connection = connectionManager.getConnection();
                    PreparedStatement stmtSort = connection.prepareStatement(QUERYSORT);

                    ResultSet rssort = stmtSort.executeQuery();

                    while(rssort.next()) {
                        transactionID = rssort.getString("transactionid");
                    }

                    rssort.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            logger.debug("Returned last transaction id");
            return transactionID;
            }

        /**
         * Returns bank user from the bank user table as per customer id
         *
         * @param customerid customer id represents a unique customers
         * @return bank user object
         * @throws SQLException
         */

        public Bankuser getBankUserByCustomerID(String customerid) throws SQLException{
            Connection connection = null;
            Bankuser bankuser = new Bankuser();

            try {
                String QUERY = "SELECT * FROM bankuser where customerid = ?";

                connection = connectionManager.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(QUERY);

                    stmt.setString(1,customerid);

                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()) {
                        bankuser.setCustomerID(rs.getString("customerid"));
                        bankuser.setLoginID(rs.getString("loginid"));
                        bankuser.setPassword(rs.getString("password"));
                        bankuser.setName(rs.getString("name"));
                        bankuser.setEmailID(rs.getString("emailid"));
                        bankuser.setAccountNo(rs.getString("accountno"));
                        bankuser.setCreditScore(rs.getInt("creditscore"));
                        if(bankuser != null){
                            logger.debug("bankuser object created");
                        }
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            logger.debug("bankuser object returned");
            return bankuser;
        }


        /**
         * gets last bank user id from the bank user table
         *
         * @return int last bankuser id
         */

        public int getLastBankUserID(){
            Connection connection = null;
            int ID = 0;

            try{

            String QUERYSORT = "SELECT * FROM bankuser ORDER BY id";

            connection = connectionManager.getConnection();
            PreparedStatement stmtSort = connection.prepareStatement(QUERYSORT);

            ResultSet rssort = stmtSort.executeQuery();

                    while(rssort.next()) {
                        ID = rssort.getInt("id");
                    }

                    rssort.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            logger.debug("Last bankuser id returned");
            return ID;
            }

        /**
         * returns all the pending credit requests
         * @return MySinglyLinkedList<CreditRequest> list of pending credit requests
         * @throws SQLException
         */
            public MySinglyLinkedList<CreditRequest> getCreditRequests() throws SQLException {

            Connection connection = null;
            MySinglyLinkedList<CreditRequest> creditRequestList = new MySinglyLinkedList<CreditRequest>();

            try{
                String sql = "select * from creditlinerequests where creditapprovalstatus = 'Pending'";

                connection = connectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql);

                ResultSet rs = psmt.executeQuery();

                while(rs.next()){
                    CreditRequest creditRequest = new CreditRequest();
                    creditRequest.setCustomerID(rs.getString("customerid"));
                    creditRequest.setCreditScore(rs.getInt("creditscore"));
                    creditRequest.setCreditStatus(rs.getString("creditapprovalstatus"));
                    creditRequestList.add(creditRequest);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return creditRequestList;
        }

    }


