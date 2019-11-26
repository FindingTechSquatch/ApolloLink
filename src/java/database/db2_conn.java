/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 *
 * @author tyleryork
 */
public class db2_conn {

    public static Connection getConnection() {
        Connection db2con = null;
        String jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
        //Need to Import: db2java.zip, db2jcc.jar, db2jcc_license_cisuz.jar into WebContent/Web-INF/lib
        String dbURL = "jdbc:db2://zos.kctr.marist.edu:5040/DALLASC";
        try {
            Class.forName(jdbcDriver);

            db2con = DriverManager.getConnection(dbURL, "kc02511", "FALL2019");
        } catch (ClassNotFoundException e) {
            System.out.print("JDBC driver not found:" + jdbcDriver);
        } catch (SQLException e) {
            System.out.print("Unable to connect to: " + dbURL + e);
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
        return db2con;
    }

  

}
