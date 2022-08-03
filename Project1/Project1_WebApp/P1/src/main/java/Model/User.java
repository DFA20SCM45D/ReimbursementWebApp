package Model;

public class User {
    /**
     * User Class
     * Attributes
     * firstName - first Name of Employee or manager
     * lastName - last name of Employee or manager
     * emailID - email ID of Employee or manager
     * loginID - login ID of Employee or manager
     * password - password of Employee or manager
     *
     */

    private String firstName;
    private String lastName;
    private String emailID;
    private String loginID;
    private String password;

    public User() {
    }
    public User(String firstName, String lastName, String emailID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.password = password;
    }

    public User(String firstName, String lastName, String emailID, String loginID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.loginID = loginID;
        this.password = password;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
