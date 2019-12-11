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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

            String sql = "UPDATE SCM.E_DETAIL SET EVT_NAME = ?, EVT_HOST = ?, EVT_TYPE = ?, EVT_STRT = ?, EVT_END = ?, EVT_BLCKS = ? WHERE EID = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, evtNm);
            ps.setString(2, evtHost);
            ps.setString(3, evtTyp);
            ps.setTimestamp(4, startTime);
            ps.setTimestamp(5, endTime);
            ps.setInt(6, Integer.parseInt(blkSze));
            ps.setInt(7, eid);
            ps.executeUpdate();

            db2.commit();
            b = true;
            
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
    
    public static boolean updateEventInfoFromEID2(int eid, String evtLoc, String evtAddr1, String evtAddr2, String evtCity, String evtState)                    
    {
        Connection db2 = getConnection();
        boolean b = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            String sql = "UPDATE SCM.E_LOCATION SET EVT_LOC_TITLE = ?, EVT_ADDR1 = ?, EVT_ADDR2 = ?, EVT_CITY = ?, EVT_STATE = ? WHERE EID = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, evtLoc);
            ps.setString(2, evtAddr1);
            ps.setString(3, evtAddr2);
            ps.setString(4, evtCity);
            ps.setString(5, evtState);
            ps.setInt(6, eid);
            ps.executeUpdate();

            db2.commit();
            b = true;
            
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
    
    public static boolean updateEventInfoFromEID3(int eid, Date earlyStrtDte, Date earlyEndDte, Double earlyRegCst, Date regStrtDte, Date regEndDte, Double regRegCst,
                                Date lateStrtDte, Date lateEndDte, Double lateRegCst) 
    {
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
        java.sql.Date esd = new java.sql.Date(earlyStrtDte.getTime());
        java.sql.Date eed = new java.sql.Date(earlyEndDte.getTime());
        java.sql.Date rsd = new java.sql.Date(regStrtDte.getTime());
        java.sql.Date red = new java.sql.Date(regEndDte.getTime());
        java.sql.Date lsd = new java.sql.Date(lateStrtDte.getTime());
        java.sql.Date led = new java.sql.Date(lateEndDte.getTime());
        
        
        Connection db2 = getConnection();
        boolean b = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            String sql = "UPDATE SCM.E_COST SET EVT_EARLY_STRT_DTE = ?, EVT_EARLY_END_DTE = ?, EVT_EARLY_COST = ?, EVT_REG_STRT_DTE = ?, EVT_REG_END_DTE = ?, EVT_REG_COST = ?, EVT_LATE_STRT_DTE = ?, EVT_LATE_END_DTE = ?, EVT_LATE_COST = ? WHERE EID = ?";
            ps = db2.prepareStatement(sql);
            ps.setDate(1, esd);
            ps.setDate(2, eed);
            ps.setDouble(3, earlyRegCst);
            ps.setDate(4, rsd);
            ps.setDate(5, red);
            ps.setDouble(6, regRegCst);
            ps.setDate(7, lsd);
            ps.setDate(8, led);
            ps.setDouble(9, lateRegCst);
            ps.setInt(10, eid);
            ps.executeUpdate();

            db2.commit();
            b = true;
            
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

    public static int addGroup(int SID, String grpNm, String grpTyp, String grpSz) {
        Connection db2 = getConnection();
        boolean b = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int tempid = 0;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "INSERT INTO SCM.G_DETAIL (GRP_NAME, GRP_TYPE, GRP_SIZE)  VALUES (?,?,?)";
            ps = db2.prepareStatement(sql);
            ps.setString(1, grpNm);
            ps.setString(2, grpTyp);
            ps.setInt(3, Integer.parseInt(grpSz));
            ps.executeUpdate();

            ps = null;
            rs = null;
            
            sql = "SELECT SCM.G_DETAIL.GID FROM SCM.G_DETAIL WHERE GRP_NAME = ? AND GRP_TYPE = ? AND GRP_SIZE = ?";
            ps = db2.prepareStatement(sql);
            ps.setString(1, grpNm);
            ps.setString(2, grpTyp);
            ps.setInt(3, Integer.parseInt(grpSz));
            rs = ps.executeQuery();
            
            while(rs.next()) {
                tempid = rs.getInt(1);
            }
            ps = null;
            rs = null;
            
            sql = "INSERT INTO SCM.X_SID_GID (SID, GID, STRT_DTE)  VALUES (?,?, CURRENT TIMESTAMP)";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, SID);
            ps.setInt(2, tempid);
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
        return tempid;
    }
}
