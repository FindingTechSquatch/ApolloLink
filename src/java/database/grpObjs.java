/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static database.db2_conn.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import obj.Registration;

/**
 *
 * @author tyleryork
 */
public class grpObjs {
     public static ArrayList<Registration> getUpcomingRegistrationsFromGID(int gid) {
        ArrayList<Registration> returnList = new ArrayList<Registration>();
        Connection db2 = getConnection();
        
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "SELECT SCM.R_DETAIL.RID, SCM.R_DETAIL.REG_TIME, SCM.R_DETAIL.REG_TYPE, SCM.R_DETAIL.REG_SEL_TIME_SLOT, SCM.R_DETAIL.REG_ADDL_STAFF, SCM.R_DETAIL.REG_BUSES, SCM.R_DETAIL.REG_TRUCK, ";
            sql += "SCM.R_PERFORMANCE.REG_PERF_TITLE, SCM.R_PERFORMANCE.REG_SONG1, SCM.R_PERFORMANCE.REG_SONG2, SCM.R_PERFORMANCE.REG_SONG3, SCM.R_PERFORMANCE.REG_SONG4, SCM.R_PERFORMANCE.REG_SONG5, SCM.R_PERFORMANCE.REG_PRE_ANNOUNCE, SCM.R_PERFORMANCE.REG_POST_ANNOUNCE ";
            sql += " FROM SCM.R_DETAIL JOIN SCM.R_PERFORMANCE ON SCM.R_DETAIL.RID = SCM.R_PERFORMANCE.RID ";
            sql += " JOIN SCM.X_GID_RID ON SCM.R_DETAIL.RID = SCM.X_GID_RID.RID WHERE SCM.X_GID_RID.GID = ?  AND SCM.R_DETAIL.REG_SEL_TIME_SLOT >= CURRENT TIMESTAMP ORDER BY REG_SEL_TIME_SLOT";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, gid);
            rs = ps.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setRID(rs.getInt("RID"));
                r.setRegDteTm(LocalDateTime.parse(rs.getString("REG_TIME"),f)); 
                r.setType(rs.getString("REG_TYPE"));
                r.setSelDteTm(LocalDateTime.parse(rs.getString("REG_SEL_TIME_SLOT"),f));
                r.setAddlStff(rs.getString("REG_ADDL_STAFF"));
                r.setBus(rs.getInt("REG_BUSES"));
                r.setTruck(rs.getInt("REG_TRUCK"));
                r.setPerfTitle(rs.getString("REG_PERF_TITLE"));
                r.setSong1(rs.getString("REG_SONG1"));
                r.setSong2(rs.getString("REG_SONG2"));
                r.setSong3(rs.getString("REG_SONG3"));
                r.setSong4(rs.getString("REG_SONG4"));
                r.setSong5(rs.getString("REG_SONG5"));
                r.setPreAnnounce(rs.getString("REG_PRE_ANNOUNCE"));
                r.setPostAnnounce(rs.getString("REG_POST_ANNOUNCE"));

                returnList.add(r);
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

        return returnList;
    }
    public static ArrayList<Registration> getPastRegistrationsFromGID(int gid) {
        ArrayList<Registration> returnList = new ArrayList<Registration>();
        Connection db2 = getConnection();
        
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "SELECT SCM.R_DETAIL.RID, SCM.R_DETAIL.REG_TIME, SCM.R_DETAIL.REG_TYPE, SCM.R_DETAIL.REG_SEL_TIME_SLOT, SCM.R_DETAIL.REG_ADDL_STAFF, SCM.R_DETAIL.REG_BUSES, SCM.R_DETAIL.REG_TRUCK, ";
            sql += "SCM.R_PERFORMANCE.REG_PERF_TITLE, SCM.R_PERFORMANCE.REG_SONG1, SCM.R_PERFORMANCE.REG_SONG2, SCM.R_PERFORMANCE.REG_SONG3, SCM.R_PERFORMANCE.REG_SONG4, SCM.R_PERFORMANCE.REG_SONG5, SCM.R_PERFORMANCE.REG_PRE_ANNOUNCE, SCM.R_PERFORMANCE.REG_POST_ANNOUNCE ";
            sql += " FROM SCM.R_DETAIL JOIN SCM.R_PERFORMANCE ON SCM.R_DETAIL.RID = SCM.R_PERFORMANCE.RID ";
            sql += " JOIN SCM.X_GID_RID ON SCM.R_DETAIL.RID = SCM.X_GID_RID.RID WHERE SCM.X_GID_RID.GID = ? AND SCM.R_DETAIL.REG_SEL_TIME_SLOT <= CURRENT TIMESTAMP ORDER BY REG_SEL_TIME_SLOT";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, gid);
            rs = ps.executeQuery();
            while (rs.next()) {
                Registration r = new Registration();
                r.setRID(rs.getInt("RID"));
                r.setRegDteTm(LocalDateTime.parse(rs.getString("REG_TIME"),f)); 
                r.setType(rs.getString("REG_TYPE"));
                r.setSelDteTm(LocalDateTime.parse(rs.getString("REG_SEL_TIME_SLOT"),f));
                r.setAddlStff(rs.getString("REG_ADDL_STAFF"));
                r.setBus(rs.getInt("REG_BUSES"));
                r.setTruck(rs.getInt("REG_TRUCK"));
                r.setPerfTitle(rs.getString("REG_PERF_TITLE"));
                r.setSong1(rs.getString("REG_SONG1"));
                r.setSong2(rs.getString("REG_SONG2"));
                r.setSong3(rs.getString("REG_SONG3"));
                r.setSong4(rs.getString("REG_SONG4"));
                r.setSong5(rs.getString("REG_SONG5"));
                r.setPreAnnounce(rs.getString("REG_PRE_ANNOUNCE"));
                r.setPostAnnounce(rs.getString("REG_POST_ANNOUNCE"));

                returnList.add(r);
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

        return returnList;
    }
}
