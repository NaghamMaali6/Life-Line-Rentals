/*this class is to establish a connection to the database*/

package application ;

import java.sql.* ;  //Import the Java SQL package to handle database connections and operations

public class JDBC 
{
	//Constants to store the database connection details:
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/Medical_Equipment_Rental" ;   //The URL specifies the protocol (jdbc), the database type (mysql), the host (127.0.0.1), and the database name (Medical_Equipment_Rental)
    private static final String USER = "root" ;  //The username for accessing the database
    private static final String PASS = "0597874994N" ;  //The password for accessing the database

    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(DB_URL , USER , PASS) ;  //Returns a Connection object using the DriverManager class with the provided URL, username, and password
    }
}