package Model;

public class Manager extends User{

    private int managerID;

    public Manager() {
    }

    public Manager(String firstName, String lastName, String emailID, String loginID, String password, int managerID) {
        super(firstName, lastName, emailID, loginID, password);
        this.managerID = managerID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }
}
