/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static database.mysql_conn.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tyleryork
 */
public class getRef {
    
    public static ArrayList<String> getGrpTyps() { //checks database for email already existing (specifically Director //returns false if user does NOT exist
        ArrayList<String> returnList = new ArrayList<>();

        Connection mySql = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        

        try {
            mySql.setAutoCommit(false);
            
            String sql = "SELECT VAL from REF_GRPTYP";
            ps = mySql.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                returnList.add(rs.getString("VAL"));
            }
            mySql.commit();
            
        } catch (SQLException e) {
            System.out.println("Database currently unavailable.");
            try {
                if (mySql != null) {
                    mySql.rollback();
                }
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable");
            }
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (mySql != null) {
                    mySql.close();
                }
            } catch (SQLException se) {
                System.out.println("Database currently unavailable.");
            }
        }
        return returnList;
    }
    
    public static ArrayList<String> getPrfTyps() { //checks database for email already existing (specifically Director //returns false if user does NOT exist
        ArrayList<String> returnList = new ArrayList<>();

        Connection mySql = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        

        try {
            mySql.setAutoCommit(false);
            
            String sql = "SELECT VAL from REF_PRFTYP";
            ps = mySql.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                returnList.add(rs.getString("VAL"));
            }
            mySql.commit();
            
        } catch (SQLException e) {
            System.out.println("Database currently unavailable.");
            try {
                if (mySql != null) {
                    mySql.rollback();
                }
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable");
            }
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (mySql != null) {
                    mySql.close();
                }
            } catch (SQLException se) {
                System.out.println("Database currently unavailable.");
            }
        }
        return returnList;
    }
}
