package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author benja
 */
public class Conexion {
    
    String url = "jdbc:mysql://localhost:3306/db_java_mvc";
    String user = "root";
    String pass = "";
    Connection con;
    
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        }catch(Exception e){}
        return con;
    }
}
