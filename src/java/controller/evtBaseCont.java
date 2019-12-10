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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
            throws ServletException, IOException 
    {
        //String url = "/evtLst.jsp";
         HttpSession session = request.getSession();

        String url = "/evtLst.jsp";
        String action = request.getParameter("evtAction");
        if (action == null) 
        {
            action = "new";

        }
        uBase usr = (uBase) session.getAttribute("usr");
        if (usr == null) 
        {
            url = "/loginCont";
            session.invalidate();
        } 
        else 
        {
            if (action.equalsIgnoreCase("new")) 
            {
                url = "/evtLst.jsp";
                session.setAttribute("hd1", "hidden");
                session.setAttribute("hd2", "hidden");
                
                try 
                {
                    uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                    ArrayList<Event> mgrEvents = evt.getEvts();
                    LinkedHashMap<Integer, ArrayList<School>> regSchools = new LinkedHashMap<>();
                    
                    for(Event e : mgrEvents)
                    {
                        ArrayList<School> schl = getObjs.getRegisteredSchoolsFromEID(e.getEID());
                        regSchools.put(e.getEID(), schl);
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
                    eventID = Integer.parseInt(request.getParameter("evt"));
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
            else if (action.equalsIgnoreCase("editEvt1")) 
            {
                url = "/evtNfo.jsp";
                uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                session.setAttribute("hd1", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                try {
                    Event event1;
                    event1 = (Event) session.getAttribute("event1");
                    if (event1 == null) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } 
                    else 
                    {
                        try {
                            String evtNm;
                            String evtHost;
                            String evtTyp;
                            String start;
                            String end;
                            LocalDateTime strtDteTme;
                            LocalDateTime endDteTme;
                            String blkSze;
                            
                            evtNm = request.getParameter("evtNm");
                            evtHost = request.getParameter("evtHost");
                            evtTyp = request.getParameter("evtTyp");
                            start = request.getParameter("strtDteTme");
                            end = request.getParameter("endDteTme");
                            blkSze = request.getParameter("blkSze");
                            
                            String errString = "";
                            LocalDateTime now = LocalDateTime.now();
                            
                            //<<<<<<<<<<<<<<<< Validation >>>>>>>>>>>>>>>>
                            //---Event Name---
                            if (IVString.ContainsText(evtNm)) 
                            {
                                //do something
                            } 
                            else 
                            {
                                errString += "name";
                                lgError1.add("An Event Name was not entered.");
                            }
                             
                            //---Event Host Name---
                            if (IVString.ContainsText(evtHost)) 
                            {
                                //do something
                            } 
                            else 
                            {
                                errString += "host";
                                lgError1.add("An Event Host Name was not entered.");
                            }
                            
                            //---Event Type---
                            if (IVString.ContainsText(evtTyp)) 
                            {
                                //do something
                            } 
                            else 
                            {
                                errString += "type";
                                lgError1.add("An Event Type was not selected.");
                            }
                            
                            //---Event Start Date and Time---
                            if (IVString.ContainsText(start)) 
                            {
                                try
                                {
                                    //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                                    //strtDteTme = (LocalDateTime)formatter.parse(start);
                                    strtDteTme = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                                    //LocalDateTime dateTime = LocalDateTime.parse(start, formatter);
                                    
                                        if(strtDteTme.compareTo(now) < 0)
                                        {
                                            errString += "start3";
                                            lgError1.add("The Event Start Date and Time can't be before today.");
                                        }
                                }
                                catch(Exception ex)
                                {
                                    errString += "start2";
                                    lgError1.add("The Event Start Date and Time was not entered correctly. Use this format: .");
                                }
                            } 
                            else 
                            {
                                errString += "start";
                                lgError1.add("An Event Start Date and Time was not selected.");
                            }
                            
                            //---Event End Date and Time---
                            if (IVString.ContainsText(end)) 
                            {
                                 try
                                {
                                    //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                                    //strtDteTme = (LocalDateTime)formatter.parse(start);
                                    strtDteTme = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                                    endDteTme = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
                                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                                    //LocalDateTime dateTime = LocalDateTime.parse(start, formatter);
                                    
                                        if(endDteTme.compareTo(now) < 0 || endDteTme.compareTo(strtDteTme) < 0)
                                        {
                                            errString += "end3";
                                            lgError1.add("The Event End Date and Time can't be before today or the Start Date.");
                                        }
                                        
                                        
                                }
                                catch(Exception ex)
                                {
                                    errString += "end2";
                                    lgError1.add("The Event End Date and Time was not entered correctly. Use this format: .");
                                }
                            } 
                            else 
                            {
                                errString += "end";
                                lgError1.add("An Event End Date and Time was not selected.");
                            }
                            //---Event Host Name---
                            if (IVString.ContainsText(evtHost)) 
                            {
                                //do something
                            } 
                            else 
                            {
                                errString += "host";
                                lgError1.add("An Event Host Name was not entered.");
                            }
                            
                            //---Event Time Block Size---
                            if (IVString.ContainsText(blkSze)) 
                            {
                                if(IVString.IsNumeric(blkSze))
                                {
                                    
                                }
                                else
                                {
                                    errString += "blockSize2";
                                    lgError1.add("A Time Block Size must be numeric.");
                                }
                            } 
                            else 
                            {
                                errString += "blockSize";
                                lgError1.add("A Time Block Size was not entered.");
                            }
                            
                            
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) 
                            {
                                session.setAttribute("hd1", "hidden");
                                url = "/evtNfo.jsp";
                                strtDteTme = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                                endDteTme = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                                if(updtInfo.updateEventInfoFromEID1(event1.getEID(), evtNm, evtHost, evtTyp, strtDteTme, endDteTme, blkSze)) 
                                {
                                    evt = dbSignIn.getEvtManagerFromUS(usr);
                                    
                                    for(Event e : evt.getEvts()) 
                                    {
                                        if(event1.getEID() == e.getEID())
                                        {
                                            event1 = e;
                                        }
                                    }
                                }
                            } 
                            else 
                            {
                                session.setAttribute("hd1", "");
                                url = "/evtNfo.jsp";
                            }
                            
                            session.setAttribute("er1", lgError1);
                        } 
                        catch (Exception ex) 
                        {
                            System.out.println(ex);
                            url = "/index.jsp";
                            session.invalidate();
                            //session = request.getSession();
                        }
                    }
                        //ArrayList<String> evtTyp = getRef.getGrpTyps();
                        
                        //session.setAttribute("evtTyp", evtTyp);
                        session.setAttribute("event1", event1);
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    //session = request.getSession();
                }

            } 
            else if (action.equalsIgnoreCase("editEvt2")) 
            {
                url = "/evtNfo.jsp";
                uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                try {
                    Event event1;
                    event1 = (Event) session.getAttribute("event1");
                    if (event1 == null) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } 
                    else 
                    {
                        try {
                            String locTitle;
                            String addr1;
                            String addr2;
                            String city;
                            String state;
                            
                            
                            String errString = "";
                            
                            //evtNm = request.getParameter("evtNm");
                            //evtTyp = request.getParameter("evtTyp");
                            //evtHost = request.getParameter("evtHost");
                            //strtDteTme = request.getParameter("strtDteTme");
                            //endDteTime
                            //blkSze
                             //<<<<<<<<<<<<<<<< Validation >>>>>>>>>>>>>>>>
                            
                             
                             
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) 
                            {
                                session.setAttribute("hd1", "hidden");
                                url = "/grpNfo.jsp";
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
//                                if(updtInfo.updateEventFromEID1(event1.getEID(), evtNm, evtHost, evtTyp, strtDteTme,endDteTme, blkSze)) 
//                                {
//                                    evt = dbSignIn.getEvtManagerFromUS(usr);
//                                    
//                                    for(School s : dir.getSchls()) {
//                                        for(Group g : s.getGrps()) {
//                                            if(grpPass.getGID() == g.getGID()) {
//                                                grpPass = g;
//                                            }
//                                        }
//                                    }
//                                    
//                                }
                                
                           
                            } else {
                                session.setAttribute("hd1", "");
                                url = "/grpNfo.jsp";
                            }
                            
                            session.setAttribute("er1", lgError1);
                            

                        } catch (Exception ex) {
                            System.out.println(ex);
                            url = "/index.jsp";
                            session.invalidate();
                            //session = request.getSession();
                        }
                    }
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    //session = request.getSession();
                }
     
        }
            else if(action.equalsIgnoreCase("editEvt3"))
            {
                
                url = "/evtNfo.jsp";
                uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                try {
                    Event event1;
                    event1 = (Event) session.getAttribute("event1");
                    if (event1 == null) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } 
                    else 
                    {
                        try {
                            LocalDate regEarlyStrtDate;
                            LocalDate regEarlyEndDte;
                            LocalDate regRegStrtDte;
                            LocalDate regRegEndDte;
                            LocalDate regLtStrtDte;
                            LocalDate regLtEndDte;
                            double regEarlyCst;
                            double regRegCst;
                            double regLtCst;
                            
                            String errString = "";
                            
                            //evtNm = request.getParameter("evtNm");
                            //evtTyp = request.getParameter("evtTyp");
                            //evtHost = request.getParameter("evtHost");
                            //strtDteTme = request.getParameter("strtDteTme");
                            //endDteTime
                            //blkSze
                             //<<<<<<<<<<<<<<<< Validation >>>>>>>>>>>>>>>>
                            
                             
                             
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) 
                            {
                                session.setAttribute("hd1", "hidden");
                                url = "/grpNfo.jsp";
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
//                                if(updtInfo.updateEventFromEID1(event1.getEID(), evtNm, evtHost, evtTyp, strtDteTme,
//                                endDteTme, blkSze)) 
//                                {
//                                    evt = dbSignIn.getEvtManagerFromUS(usr);
//                                    
//                                    for(School s : dir.getSchls()) {
//                                        for(Group g : s.getGrps()) {
//                                            if(grpPass.getGID() == g.getGID()) {
//                                                grpPass = g;
//                                            }
//                                        }
//                                    }
//                                    
//                                }
                                
                           
                            } else {
                                session.setAttribute("hd1", "");
                                url = "/grpNfo.jsp";
                            }
                            
                            session.setAttribute("er1", lgError1);
                            

                        } catch (Exception ex) {
                            System.out.println(ex);
                            url = "/index.jsp";
                            session.invalidate();
                            //session = request.getSession();
                        }
                    }
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    //session = request.getSession();
                }
            }

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
            throws ServletException, IOException 
    {
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
            throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }// </editor-fold>

}
