package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database{
    static final String DB_URL = "jdbc:mysql://localhost/cms";
    static final String Username = "root";
    static final String Password = "";
        
    public Connection getConnection() {
        try{
            Connection connection = DriverManager.getConnection(DB_URL, Username, Password);
            
            System.out.print("Connecting to database....");
            connection.createStatement();
            
            System.out.println();

            
            if(connection!=null) {
                System.out.println("Database connected successfully!");
            }
            return connection;
            
        }catch(SQLException exc) {
            System.out.println("Something went wrong while connecting to the database.");
            System.out.println(exc);
        }
        
        return null;   
    }
}

