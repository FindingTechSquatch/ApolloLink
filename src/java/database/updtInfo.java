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
import java.lang.Object;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import obj.*;

/**
 *
 * @author tyleryork
 */
public class updtInfo {

    public static boolean updateGroupFromGID(int gid, String grpNm, String grpTyp, String grpSz) {
        Connection db2 = getConnection();
        boolean b = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "UPDATE SCM.G_DETAIL SET GRP_NAME = ?, GRP_TYPE = ?, GRP_SIZE = ? WHERE GID = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, grpNm);
            ps.setString(2, grpTyp);
            ps.setInt(3, Integer.parseInt(grpSz));
            ps.setInt(4, gid);
            ps.executeUpdate();

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            b = true;
            //rs.close();
            //ps.close();
            //db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable." + e);
            b = false;
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
        return b;
    }

    public static boolean updateGroupLeadersFromGID(int gid, ArrayList<grpLeader> gls) {
        Connection db2 = getConnection();
        boolean b = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int tempID = 0;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Set Old Leaders out >>>>>>>>>>>>>>>>
            String sql = "UPDATE SCM.L_DETAIL SET SCM.L_DETAIL.END_DTE = CURRENT TIMESTAMP WHERE SCM.L_DETAIL.LID IN (SELECT SCM.X_GID_LID.LID FROM SCM.X_GID_LID WHERE SCM.X_GID_LID.GID = ?) ";
            sql += " AND ((SCM.L_DETAIL.STRT_DTE <= CURRENT TIMESTAMP AND SCM.L_DETAIL.END_DTE >= CURRENT TIMESTAMP) OR (";
            sql += "SCM.L_DETAIL.STRT_DTE <= CURRENT TIMESTAMP AND SCM.L_DETAIL.END_DTE IS NULL))";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, gid);
            ps.executeUpdate();
            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            ps = null;
            sql = "";

            for (grpLeader l : gls) {
                sql = "INSERT INTO SCM.L_DETAIL (SCM.L_DETAIL.FIRST_NAME, SCM.L_DETAIL.LAST_NAME, SCM.L_DETAIL.TITLE, SCM.L_DETAIL.STRT_DTE, SCM.L_DETAIL.SCHL_YR) VALUES (?,?,?,CURRENT TIMESTAMP,'NA')";
                ps = db2.prepareStatement(sql);
                ps.setString(1, l.getLdrFName());
                ps.setString(2, l.getLdrLName());
                ps.setString(3, l.getLdrTitle());
                ps.executeUpdate();

                ps = null;
                sql = "";

                sql = "SELECT MAX(SCM.L_DETAIL.LID)  FROM SCM.L_DETAIL WHERE SCM.L_DETAIL.FIRST_NAME = ? AND SCM.L_DETAIL.LAST_NAME = ? AND SCM.L_DETAIL.TITLE = ? ";
                sql += " AND ((SCM.L_DETAIL.STRT_DTE <= CURRENT TIMESTAMP AND SCM.L_DETAIL.END_DTE >= CURRENT TIMESTAMP) OR (";
                sql += "SCM.L_DETAIL.STRT_DTE <= CURRENT TIMESTAMP AND SCM.L_DETAIL.END_DTE IS NULL))";
                ps = db2.prepareStatement(sql);
                ps.setString(1, l.getLdrFName());
                ps.setString(2, l.getLdrLName());
                ps.setString(3, l.getLdrTitle());

                rs = ps.executeQuery();

                while (rs.next()) {
                    tempID = rs.getInt(1);
                }

                ps = null;
                sql = "";
                rs = null;

                sql = "INSERT INTO SCM.X_GID_LID (GID, LID, STRT_DTE) VALUES (?,?, CURRENT TIMESTAMP)";
                ps = db2.prepareStatement(sql);
                ps.setInt(1, gid);
                ps.setInt(2, tempID);
                ps.executeUpdate();

            }

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            b = true;
            //rs.close();
            //ps.close();
            //db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable." + e);
            b = false;
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
        return b;
    }

    public static boolean updateEventInfoFromEID1(int eid, String evtNm, String evtHost, String evtTyp, LocalDateTime strtDteTme,
                                LocalDateTime endDteTme, String blkSze) 
    {
        Timestamp startTime;
        startTime = Timestamp.valueOf(strtDteTme);
        
        Timestamp endTime;
        endTime = Timestamp.valueOf(endDteTme);
        
        Connection db2 = getConnection();
        boolean b = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "UPDATE SCM.E_DETAIL SET EVT_NAME = ?, EVT_HOST = ?, EVT_TYPE = ?, EVT_STRT = ?, EVT_END = ?, EVT_BLCKS = ? WHERE EID = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, evtNm);
            ps.setString(2, evtHost);
            ps.setString(3, evtTyp);
            ps.setTimestamp(4, startTime);
            ps.setTimestamp(5, endTime);
            ps.setInt(3, Integer.parseInt(blkSze));
            ps.setInt(4, eid);
            ps.executeUpdate();

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            b = true;
            //rs.close();
            //ps.close();
            //db2.close();
        } catch (SQLException e) {
            System.out.println("Database currently unavailable." + e);
            b = false;
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
        return b;
    }

}
