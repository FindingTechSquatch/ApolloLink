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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import obj.Event;

/**
 *
 * @author tyleryork
 */
public class reg {
    
    public static ArrayList<Event> getAvailableEventsFromGID(int gid) {
        ArrayList<Event> returnList = new ArrayList<Event>();
        Connection db2 = getConnection();
        
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            db2.setAutoCommit(false);

            //<<<<<<<<<<<<<<<< Get All School Info >>>>>>>>>>>>>>>>
            String sql = "SELECT SCM.E_DETAIL.EID, SCM.E_DETAIL.EVT_NAME, SCM.E_DETAIL.EVT_HOST, SCM.E_DETAIL.EVT_TYPE, SCM.E_DETAIL.EVT_STRT, SCM.E_DETAIL.EVT_END, SCM.E_DETAIL.EVT_BLCKS, "; 
            sql += "SCM.E_LOCATION.EVT_LOC_TITLE, SCM.E_LOCATION.EVT_ADDR1, SCM.E_LOCATION.EVT_ADDR2, SCM.E_LOCATION.EVT_CITY, SCM.E_LOCATION.EVT_STATE, ";
            sql += " SCM.E_COST.EVT_EARLY_STRT_DTE, SCM.E_COST.EVT_EARLY_END_DTE, SCM.E_COST.EVT_REG_STRT_DTE, SCM.E_COST.EVT_REG_END_DTE, SCM.E_COST.EVT_LATE_STRT_DTE, SCM.E_COST.EVT_LATE_END_DTE, SCM.E_COST.EVT_EARLY_COST, SCM.E_COST.EVT_REG_COST, SCM.E_COST.EVT_LATE_COST ";
            sql += " FROM SCM.E_DETAIL JOIN SCM.E_LOCATION on SCM.E_DETAIL.EID = SCM.E_LOCATION.EID";
            sql += " JOIN SCM.E_COST ON SCM.E_DETAIL.EID = SCM.E_COST.EID";
            sql += " WHERE SCM.E_DETAIL.EVT_TYPE IN (SELECT GRP_TYPE FROM SCM.G_DETAIL WHERE GID = ?) AND SCM.E_DETAIL.EVT_STRT > CURRENT TIMESTAMP";
            sql += " AND ((CURRENT DATE BETWEEN SCM.E_COST.EVT_EARLY_STRT_DTE AND SCM.E_COST.EVT_EARLY_END_DTE) OR ";
            sql += " (CURRENT DATE BETWEEN SCM.E_COST.EVT_REG_STRT_DTE AND SCM.E_COST.EVT_REG_END_DTE) OR ";
            sql += " (CURRENT DATE BETWEEN SCM.E_COST.EVT_LATE_STRT_DTE AND SCM.E_COST.EVT_LATE_END_DTE)) ORDER BY SCM.E_DETAIL.EVT_STRT";
            ps = db2.prepareStatement(sql);
            ps.setInt(1, gid);
            rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEID(rs.getInt("EID"));
                e.setName(rs.getString("EVT_NAME"));
                e.setHost(rs.getString("EVT_HOST"));
                e.setType(rs.getString("EVT_TYPE"));
                e.setStrtDteTm(LocalDateTime.parse(rs.getString("EVT_STRT"),f));
                e.setEndDteTm(LocalDateTime.parse(rs.getString("EVT_END"),f));
                e.setBlckSize(rs.getInt("EVT_BLCKS"));
                e.setLocTitle(rs.getString("EVT_LOC_TITLE"));
                e.setAddr1(rs.getString("EVT_ADDR1"));
                e.setAddr2(rs.getString("EVT_ADDR2"));
                e.setCity(rs.getString("EVT_CITY"));
                e.setState(rs.getString("EVT_STATE"));
                e.setRegEarlyStrtDte(LocalDate.parse(rs.getString("EVT_EARLY_STRT_DTE")));
                e.setRegEarlyEndDte(LocalDate.parse(rs.getString("EVT_EARLY_END_DTE")));
                e.setRegRegStrtDte(LocalDate.parse(rs.getString("EVT_REG_STRT_DTE")));
                e.setRegRegEndDte(LocalDate.parse(rs.getString("EVT_REG_END_DTE")));
                e.setRegLtStrtDte(LocalDate.parse(rs.getString("EVT_LATE_STRT_DTE")));
                e.setRegLtEndDte(LocalDate.parse(rs.getString("EVT_LATE_END_DTE")));
                e.setRegEarlyCst(rs.getDouble("EVT_EARLY_COST"));
                e.setRegRegCst(rs.getDouble("EVT_REG_COST"));
                e.setRegLtCst(rs.getDouble("EVT_LATE_COST"));
                returnList.add(e);
            }

            //<<<<<<<<<<<<<<<< Final Commit for New User >>>>>>>>>>>>>>>>
            db2.commit();
            //rs.close();
            //ps.close();
            //db2.close();
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
