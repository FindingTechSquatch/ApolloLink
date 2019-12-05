/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.dbSignIn;
import database.getObjs;
import database.getRef;
import database.grpObjs;
import database.updtInfo;
import encrypt.ec;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import obj.*;
import validation.IVString;

/**
 *
 * @author mckay
 */
public class evtBaseCont extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String url = "/evtLst.jsp";
         HttpSession session = request.getSession();

        String url = "/evtLst.jsp";
        String action = request.getParameter("evtAction");
        if (action == null) {
            action = "new";

        }
        uBase usr = (uBase) session.getAttribute("usr");
        if (usr == null) {
            url = "/loginCont";
            session.invalidate();
        } else {
            if (action.equalsIgnoreCase("new")) {
                url = "/evtLst.jsp";
                try {
                    uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                    ArrayList<Event> mgrEvents = evt.getEvts();
                    LinkedHashMap<Integer, School> regSchools = new LinkedHashMap<>();
                    for (int i = 0; i < evt.getEvts().size(); i++) 
                    {
                        //update once select statement in getRegistrationsFromEID is fixed
//                        for (int j = 0; j < evt.getEvts().get(i).getRegs().size(); j++) 
//                        {
//                            
//                               Event reg = getObjs.getRegistrationsFromEID(evt.getEID());
//                        }
                    }

                    session.setAttribute("evt", evt);
                    session.setAttribute("mgrEvents", mgrEvents);
                    session.setAttribute("regSchools", regSchools);
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }

            } 
            else if (action.equalsIgnoreCase("nfo")) 
            {
                url = "/evtNfo.jsp";
                uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                try 
                {
                    int eventID;
                    eventID = Integer.parseInt(request.getParameter("event"));
                    if (eventID <= 0) 
                    {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } 
                    else 
                    {
                        Event event1 = new Event();
                        for (Event e : evt.getEvts()) 
                        {
                            if (e.getEID() == eventID)
                            {
                                event1 = e;
                            }
                        }
                        session.setAttribute("event1", event1);
                    }
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }

            } 
            else if (action.equalsIgnoreCase("newEvt")) 
            {
                url = "/evtNfo.jsp";
                uDir dir = dbSignIn.getDirectorfromUS(usr);
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                try {
                    Event event1;
                    event1 = (Event) session.getAttribute("event1");
                    if (event1 == null) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } else 
                    {
                        try {
                            String evtNm;
                            String evtHost;
                            String evtTyp;
                            LocalDateTime strtDteTme;
                            LocalDateTime endDteTime;
                            int blkSze;
                            String locTitle;
                            String addr1;
                            String addr2;
                            String city;
                            String state;
                            LocalDate regEarlyStrtDate;
                            LocalDate regEarlyEndDte;
                            LocalDate regRegStrtDte;
                            LocalDate regRegEndDte;
                            LocalDate regLtStrtDte;
                            LocalDate regLtEndDte;
                            double regEarlyCst;
                            double regRegCst;
                            double regLtCst;
                            
                            evtNm = request.getParameter("evtNm");
                            evtTyp = request.getParameter("evtTyp");
                            evtHost = request.getParameter("evtHost");
                            //strtDteTme = request.getParameter("strtDteTme");
                            //endDteTime
                            //blkSze
                            
                            //finish error validation
                        session.setAttribute("event1", event1);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    //session = request.getSession();
                }
            }
            //more?
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
