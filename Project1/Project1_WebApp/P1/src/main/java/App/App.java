package App;

import JDBC.ConnectionManager;
import JDBC.DataGetterService;
import Service.ManagerService;

public class App {

    private static final ConnectionManager cm = new ConnectionManager("jdbc:postgresql://java-revature-dhairya.cibikjimihdn.us-east-2.rds.amazonaws.com:5432/project1", "postgres", "dhairyadixit", new org.postgresql.Driver());

    private static DataGetterService dgs = new DataGetterService(cm);

    public static void main(String[] args){


        ManagerService ms = new ManagerService(dgs);
        ms.viewAllEmployees();

    }

}
