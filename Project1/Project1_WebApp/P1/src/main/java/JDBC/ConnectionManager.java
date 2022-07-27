package JDBC;

import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private String url;
    private String username;
    private String password;

    private Driver driver;
    private boolean driverReady;

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Driver.class.getName());

    public ConnectionManager(String url, String username, String password, Driver driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.driverReady = false;
    }

    /**
     * Registers driver
     *
     * @throws SQLException
     */
    private void registerDriver() throws SQLException {
        if(!driverReady) {
            DriverManager.registerDriver(this.driver);
        }
    }

    /***
     * to get connection to the database url, username and password of the database
     * @return connection
     * @throws SQLException
     */

    public Connection getConnection() throws SQLException{
        if(!driverReady) {
            registerDriver();
        }
        logger.debug("Trying to establish connection with database at this location :" +url);
        return DriverManager.getConnection(url, username, password);
    }
}