/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tyleryork
 */
public class mysql_conn {
    public static Connection getConnection() {
        Connection db2con = null;
        String jdbcDriver = "com.mysql.jdbc.Driver";
        //Need to Import: db2java.zip, db2jcc.jar, db2jcc_license_cisuz.jar into WebContent/Web-INF/lib
        String dbURL = "jdbc:mysql://awszeusdb.tryhardlab.com:3306/APOLLO";
        try {
            Class.forName(jdbcDriver);

            db2con = DriverManager.getConnection(dbURL, "baseApollo", "FALL2019");
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
