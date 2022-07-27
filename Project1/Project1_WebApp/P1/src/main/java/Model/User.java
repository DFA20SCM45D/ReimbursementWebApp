package Model;

public class User {

    private String firstName;
    private String lastName;
    private String emailID;
    private String loginID;
    private String password;
    private int systemStateID;

    public User() {
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

    public int getSystemStateID() {
        return systemStateID;
    }

    public void setSystemStateID(int systemStateID) {
        this.systemStateID = systemStateID;
    }
}
