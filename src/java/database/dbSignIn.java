/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.util.ArrayList;
import obj.*;
import database.*;
import static database.db2_conn.*;
import encrypt.ec;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tyleryork
 */
public class dbSignIn {

    public static ArrayList<School> getAllSchools() {// does not get groups for school
        ArrayList<School> returnList = new ArrayList<School>();
        try {
            Connection db2 = getConnection();

            Statement stmt = db2.createStatement();

            String sql = "SELECT * from SCM.S_DETAIL";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                School s = new School();
                s.setSID(rs.getInt("SID"));
                s.setSchlName(rs.getString("SCHL_NAME"));
                s.setSchlAddr1(rs.getString("SCHL_ADDR1"));
                s.setSchlAddr2(rs.getString("SCHL_ADDR2"));
                s.setSchlCity(rs.getString("SCHL_CITY"));
                s.setSchlST(rs.getString("SCHL_STATE"));
                s.setSchlZip(rs.getString("SCHL_ZIP"));
                s.setSchlSize(rs.getString("SCHL_SIZE"));
                s.setSchlPhone(rs.getString("SCHL_PHONE"));
                s.setLogoURL(rs.getString("SCHL_LOGO"));

                returnList.add(s);
            }
            rs.close();
            stmt.close();
            db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable.");
        }

        return returnList;
    }

    public static boolean chkUserExists(String s) { //checks database for email already existing (specifically Director //returns false if user does NOT exist
        boolean b = false;
        int count = 0;
        Connection db2 = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        

        try {
            db2.setAutoCommit(false);
            
            String sql = "SELECT COUNT(*) from SCM.U_DETAIL WHERE EMAIL = ? AND USR_TYPE = 'D'";
            ps = db2.prepareStatement(sql);
            ps.setString(1, s);

            rs = ps.executeQuery();

            while (rs.next()) {
                count += rs.getInt(1);
            }
            db2.commit();
            rs.close();
            ps.close();
            db2.close();

            b = (count >= 1);
        } catch (SQLException e) {
            System.out.println("Database currently unavailable.");
            try {
                if (db2 != null) {
                    db2.rollback();
                }
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable");
            }
        }

        return b;
    }

    public static uDir newUser(uDir u) { //returns good user on success // null on false;
        Connection db2 = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< INSERT INTO U_CRED >>>>>>>>>>>>>>>>
            String sql = "INSERT INTO SCM.U_CRED (USR_H, PSWD_H) VALUES (?,?)";
            ps = db2.prepareStatement(sql);
            ps.setString(1, u.getHus());
            ps.setString(2, u.getHpw());
            ps.executeUpdate();

            //<<<<<<<<<<<<<<<< GET UID FROM U_CRED >>>>>>>>>>>>>>>>
            sql = "SELECT UID FROM SCM.U_CRED WHERE USR_H = ? AND PSWD_H = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, u.getHus());
            ps.setString(2, u.getHpw());
            rs = ps.executeQuery();
            while (rs.next()) {
                u.setUid(rs.getInt("UID"));
            }

            //<<<<<<<<<<<<<<<< Initial Commit for New User >>>>>>>>>>>>>>>>
            //db2.commit();

            //<<<<<<<<<<<<<<<< INSERT INTO U_DETAIL >>>>>>>>>>>>>>>>
            sql = "INSERT INTO SCM.U_DETAIL (UID, USR_TYPE, FIRST_NAME, LAST_NAME, EMAIL, PHONE) VALUES (?,'D',?,?,?,?)";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, u.getUid());
            ps.setString(2, u.getfName());
            ps.setString(3, u.getlName());
            ps.setString(4, u.getUus());
            ps.setString(5, u.getPhone());
            ps.executeUpdate();

            //<<<<<<<<<<<<<<<< INSERT INTO X_UID_SID >>>>>>>>>>>>>>>>
            for (int i = 0; i < u.getSchls().size(); i++) {
                sql = "INSERT INTO SCM.X_UID_SID (UID, SID, STRT_DTE) VALUES (?,?, CURRENT TIMESTAMP)";
                ps = db2.prepareStatement(sql);
                ps.setInt(1, u.getUid());
                ps.setInt(2, u.getSchls().get(i).getSID());
                ps.executeUpdate();
            }

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            rs.close();
            ps.close();
            db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable. + e");
            
            try {
                if (db2 != null) {
                    db2.rollback();
                    //<<<<<<<<<<<<<<<< INSERT INTO U_CRED >>>>>>>>>>>>>>>>
                    String sql = "DELETE FROM SCM.U_CRED WHERE USR_H = ? AND PSWD_H = ?";
                    ps = db2.prepareStatement(sql);
                    ps.setString(1, u.getHus());
                    ps.setString(2, u.getHpw());
                    ps.executeUpdate();
                    rs = null;
                    ps = null;
                }
                u = null;
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable");
            }
        } finally {
            if (u != null) {
                if (u.getUid() <= 0) {
                    u = null;
                }
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db2 != null) {
                    db2.close();
                }
            } catch (SQLException se) {
                System.out.println("Database currently unavailable.");
            }
        }

        return u;
    }
    
    public static int loginUser(uBase u) {
        int success = 0;
        
        Connection db2 = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< INSERT INTO U_CRED >>>>>>>>>>>>>>>>
            String sql = "SELECT COUNT(*) FROM SCM.U_CRED WHERE EMAIL = ? AND PSWD_H = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, u.getHus());
            ps.setString(2, u.getHpw());
            rs = ps.executeQuery();
            while(rs.next()){
                success += rs.getInt(1);
            }

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();

        } catch (SQLException e) {
            System.out.println("Database currently unavailable." + e);
            
            try {
                if (db2 != null) {
                    db2.rollback();
                }
                u = null;
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable " + se);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db2 != null) {
                    db2.close();
                }
            } catch (SQLException se) {
                System.out.println("Database currently unavailable." + se);
            }
        }
        
        
        return success;
    }
    
    public static int getUIDfromUS(uBase u) {
        int returnInt = 0;
        Connection db2 = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "SELECT SCM.U_CRED.UID FROM SCM.U_CRED WHERE EMAIL = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, u.getHus());
            rs = ps.executeQuery();
            while (rs.next()) {
                returnInt = rs.getInt("UID");
            }

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            rs.close();
            ps.close();
            db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable." + e);

            try {
                if (db2 != null) {
                    db2.rollback();
                }
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable " + se);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db2 != null) {
                    db2.close();
                }
            } catch (SQLException se) {
                System.out.println("Database currently unavailable." + se);
            }
        }
        return returnInt;
    }
    
    public static uDir getDirectorfromUS(uBase u) {
        uDir v = new uDir();
        v.setUus(u.getUus());
        v.setHus(u.getHus());
        v.setHpw(u.getHpw());
        
        v.setUid(getUIDfromUS(u));
        Connection db2 = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "SELECT FIRST_NAME, LAST_NAME, PHONE FROM SCM.U_DETAIL WHERE UID = ?";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, v.getUid());
            rs = ps.executeQuery();
            while (rs.next()) {
                v.setfName(rs.getString("FIRST_NAME"));
                v.setlName(rs.getString("LAST_NAME"));
                v.setPhone(rs.getString("PHONE"));
            }

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            rs.close();
            ps.close();
            db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable." + e);

            try {
                if (db2 != null) {
                    db2.rollback();
                }
            } catch (SQLException se) {
                System.out.println("Database is currently unavailable " + se);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db2 != null) {
                    db2.close();
                }
            } catch (SQLException se) {
                System.out.println("Database currently unavailable." + se);
            }
        }
        
        v.setSchls(getObjs.getAllDirFromUID(v.getUid()));
        
        return v;
    }
            
}
