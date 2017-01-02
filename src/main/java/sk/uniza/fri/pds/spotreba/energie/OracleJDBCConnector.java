package sk.uniza.fri.pds.spotreba.energie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleJDBCConnector {

    public static Connection getConnection() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "patrik",
                    "prestigio1");
            System.out.println("You made it, take control your database now!");
            return connection;

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        }
    }

}
