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
                session.setAttribute("hd3", "hidden");
                
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
                
                ArrayList<String> evtTyp = new ArrayList<String>();
                evtTyp.add("Marching Band");
                evtTyp.add("Choir");
                evtTyp.add("Jazz Band");
                evtTyp.add("Concert Band");
                evtTyp.add("Orchestra");
                
                session.setAttribute("evtTyp", evtTyp);
                
                ArrayList<String> stateList = new ArrayList<String>();
                
                String[] states = new String[]{"AK","AL","AR","AZ","CA","CO","CT","DC",
                    "DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA",
                    "MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE",
                    "NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI",
                    "SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};

                for (String s : states)
                {
                    stateList.add(s);
                }
                
                session.setAttribute("evtState", stateList);
                
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                session.setAttribute("er2", lgError1);
                session.setAttribute("er3", lgError1);
                
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
                            String evtType;
                            String start;
                            String end;
                            LocalDateTime strtDteTme;
                            LocalDateTime endDteTme;
                            String blkSze;
                            
                            evtNm = request.getParameter("evtNm");
                            evtHost = request.getParameter("evtHst");
                            evtType = request.getParameter("evtType");
                            start = request.getParameter("evtStrtDteTm");
                            end = request.getParameter("evtEndDteTm");
                            blkSze = request.getParameter("evtBlkSz");
                            
                            String errString = "";
                            LocalDateTime now = LocalDateTime.now();
                            
                            //<<<<<<<<<<<<<<<< Validation >>>>>>>>>>>>>>>>
                            //---Event Name---
                            if (IVString.ContainsText(evtNm)) 
                            {
                                //good
                            } 
                            else 
                            {
                                errString += "name";
                                lgError1.add("An Event Name was not entered.");
                            }
                             
                            //---Event Host Name---
                            if (IVString.ContainsText(evtHost)) 
                            {
                                //good
                            } 
                            else 
                            {
                                errString += "host";
                                lgError1.add("An Event Host Name was not entered.");
                            }
                            
                            //---Event Type---
                            if (IVString.ContainsText(evtType)) 
                            {
                                //good
                            } 
                            else 
                            {
                                errString += "type";
                                lgError1.add("An Event Type was not selected.");
                            }
                            
                            String dateErr = "";
                            
                            //---Event Start and End Date and Time---
                            if(!IVString.ContainsText(end) && !IVString.ContainsText(start))
                            {
                                errString += "start1";
                                lgError1.add("An Event Start Date and Time was not selected.");
                                errString += "end1";
                                lgError1.add("An Event End Date and Time was not selected.");
                            }
                            else if(IVString.ContainsText(end) && !IVString.ContainsText(start))
                            {
                                try
                                {
                                    endDteTme = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
                                    
                                    if(endDteTme.compareTo(now) < 0)
                                    {
                                        errString += "end3";
                                        lgError1.add("The Event End Date and Time can't be before today or the Start Date.");
                                    }
                                }
                                catch(Exception ex)
                                {
                                    errString += "end2";
                                    lgError1.add("The Event End Date was not entered correctly. Format: MM/DD/YYYY HH:MM PM/AM.");
                                }
                                
                                errString += "start1";
                                lgError1.add("An Event Start Date and Time was not selected.");
                            }
                            else if(!IVString.ContainsText(end) && IVString.ContainsText(start))
                            {
                                try
                                {
                                    strtDteTme = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                                    
                                    if(strtDteTme.compareTo(now) < 0)
                                    {
                                        errString += "start3";
                                        lgError1.add("The Event Start Date and Time can't be before today.");
                                    }
                                }
                                catch(Exception ex)
                                {
                                    errString += "start2";
                                    lgError1.add("The Event Start Date was not entered correctly. Format: MM/DD/YYYY HH:MM PM/AM.");
                                }
                                
                                errString += "end1";
                                lgError1.add("An Event End Date and Time was not selected.");
                            }
                            else if(IVString.ContainsText(end) && IVString.ContainsText(start)) 
                            {
                                try
                                {
                                    strtDteTme = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "start2";
                                    errString += "start2";
                                    lgError1.add("The Event Start Date was not entered correctly. Format: MM/DD/YYYY HH:MM PM/AM.");
                                }
                                    
                                    
                                try
                                {
                                    endDteTme = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "end2";
                                    errString += "end2";
                                    lgError1.add("The Event End Date was not entered correctly. Format: MM/DD/YYYY HH:MM PM/AM.");
                                }
                                    
                                if(dateErr == null || dateErr.equals(""))
                                {
                                    strtDteTme = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                                    endDteTme = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
                                        
                                    if(strtDteTme.compareTo(now) < 0)
                                    {
                                        errString += "start3";
                                        lgError1.add("The Event Start Date and Time can't be before today.");
                                    }
                                    
                                    if(endDteTme.compareTo(now) < 0 || endDteTme.compareTo(strtDteTme) < 0)
                                    {
                                        errString += "end3";
                                        lgError1.add("The Event End Date and Time can't be before today or the Start Date.");
                                    }
                                } 
                            }
                            
                            //---Event Time Block Size---
                            if (IVString.ContainsText(blkSze)) 
                            {
                                if(IVString.IsNumeric(blkSze))
                                {
                                    //good
                                }
                                else
                                {
                                    errString += "blockSize2";
                                    lgError1.add("A Time Block Size must be numeric.");
                                }
                            } 
                            else 
                            {
                                errString += "blockSize1";
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
                                if(updtInfo.updateEventInfoFromEID1(event1.getEID(), evtNm, evtHost, evtType, strtDteTme, endDteTme, blkSze)) 
                                {
                                    evt = dbSignIn.getEvtManagerFromUS(usr);
                                    
                                    for(Event e : evt.getEvts()) 
                                    {
                                        if(event1.getEID() == e.getEID())
                                        {
                                            event1 = e;
                                            //session.setAttribute("er1", "YEP");
                                        }
                                    }
                                }
//                                else
//                                {
//                                    session.setAttribute("er1", "NOPE");
//                                }
                            } 
                            else 
                            {
                                session.setAttribute("er1", lgError1);
                                session.setAttribute("hd1", "");
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
                session.setAttribute("hd2", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er2", lgError1);
                
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
                            
                            locTitle = request.getParameter("evtLoc");
                            addr1 = request.getParameter("evtAddr1");
                            addr2 = request.getParameter("evtAddr2");
                            city = request.getParameter("evtCity");
                            state = request.getParameter("evtState");
                            
                            String errString = "";
                            
                             //<<<<<<<<<<<<<<<< Validation >>>>>>>>>>>>>>>>
                            //---Event Location Title---
                            if (IVString.ContainsText(locTitle)) 
                            {
                                //good
                            } 
                            else 
                            {
                                errString += "location";
                                lgError1.add("A Location Title was not entered.");
                            }
                             
                            //---Event Address 1---
                            if (IVString.ContainsText(addr1)) 
                            {
                                //good
                            } 
                            else 
                            {
                                errString += "address1";
                                lgError1.add("Event Address 1 was not entered.");
                            }
                            
                            //---Event City---
                            if (IVString.ContainsText(city)) 
                            {
                                //good
                            } 
                            else 
                            {
                                errString += "city";
                                lgError1.add("Event City was not entered.");
                            }
                             
                            //---Event State---
                            if (IVString.ContainsText(state)) 
                            {
                                if(IVString.LengthGTET(state, 2) && IVString.LengthLTET(state, 2))
                                {
                                    //good
                                }
                                else
                                {
                                    errString += "state2";
                                    lgError1.add("Event State must be only .");
                                }
                            } 
                            else 
                            {
                                errString += "state1";
                                lgError1.add("Event State was not entered.");
                            }
                            
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) 
                            {
                                session.setAttribute("hd1", "hidden");
                                url = "/evtNfo.jsp";
                                
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                                if(updtInfo.updateEventInfoFromEID2(event1.getEID(), locTitle, addr1, addr2, city, state)) 
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
                                session.setAttribute("er2", lgError1);
                                session.setAttribute("hd2", "");
                            }
                            
                            session.setAttribute("er2", lgError1);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println(ex);
                            url = "/index.jsp";
                            session.invalidate();
                            //session = request.getSession();
                        }
                    }
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
            else if(action.equalsIgnoreCase("editEvt3"))
            {
                url = "/evtNfo.jsp";
                uEvt evt = dbSignIn.getEvtManagerFromUS(usr);
                session.setAttribute("hd3", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er3", lgError1);
                
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
                            String earlyStrtDte;
                            String earlyEndDte;
                            String regStrtDte;
                            String regEndDte;
                            String ltStrtDte;
                            String ltEndDte;
                            
                            Date regEarlyStrtDte;
                            Date regEarlyEndDte;
                            String earlyCst;
                            Date regRegStrtDte;
                            Date regRegEndDte;
                            String regCst;
                            Date regLtStrtDte;
                            Date regLtEndDte;
                            String ltCst;
                            
                            earlyStrtDte = request.getParameter("evtEarlyStrtDte");
                            earlyEndDte = request.getParameter("evtEarlyEndDte");
                            earlyCst = request.getParameter("evtEarlyCst");
                            regStrtDte = request.getParameter("evtRegStrtDte");
                            regEndDte = request.getParameter("evtRegEndDte");
                            regCst = request.getParameter("evtRegCst");
                            ltStrtDte = request.getParameter("evtLateStrtDte");
                            ltEndDte = request.getParameter("evtLateEndDte");
                            ltCst = request.getParameter("evtLateCst");
                            
                            String errString = "";
                            
                            //<<<<<<<<<<<<<<<< Validation >>>>>>>>>>>>>>>>
                            
                            /////Early Cost/////
                            if (!IVString.ContainsText(earlyStrtDte) || !IVString.ContainsText(earlyEndDte)
                                     || !IVString.ContainsText(regStrtDte) || !IVString.ContainsText(regEndDte)
                                     || !IVString.ContainsText(ltStrtDte) || !IVString.ContainsText(ltEndDte)) 
                            {
                                //Early Start//
                                if(!IVString.ContainsText(earlyStrtDte))
                                {
                                    errString += "noEarlyStrt";
                                    lgError1.add("An Early Registration Start Date was not entered.");
                                }
                                
                                //Early End//
                                if(!IVString.ContainsText(earlyEndDte))
                                {
                                    errString += "noEarlyEnd";
                                    lgError1.add("An Early Registration End Date was not entered.");
                                }
                                
                                //Regular Start//
                                if(!IVString.ContainsText(regStrtDte))
                                {
                                    errString += "noRegStrt";
                                    lgError1.add("A Regular Registration Start Date was not entered.");
                                }
                                
                                //Regular End//
                                if(!IVString.ContainsText(regEndDte))
                                {
                                    errString += "noRegEnd";
                                    lgError1.add("A Regular Registration End Date was not entered.");
                                }
                                
                                //Late Start//
                                if(!IVString.ContainsText(ltStrtDte))
                                {
                                    errString += "noLateStrt";
                                    lgError1.add("A Late Registration Start Date was not entered.");
                                }
                                
                                //Late End//
                                if(!IVString.ContainsText(ltEndDte))
                                {
                                    errString += "noLateEnd";
                                    lgError1.add("A Late Registration End Date was not entered.");
                                }
                                
                            } 
                            else if(IVString.ContainsText(earlyStrtDte) && IVString.ContainsText(earlyEndDte)
                                     && IVString.ContainsText(regStrtDte) && IVString.ContainsText(regEndDte)
                                     && IVString.ContainsText(ltStrtDte) && IVString.ContainsText(ltEndDte)) 
                            {
                                String dateErr = "";
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                
                                //////Early Dates/////
                                try
                                {
                                    regEarlyStrtDte = formatter.parse(earlyStrtDte);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "eStrt1";
                                    errString += "eStrt1";
                                    lgError1.add("The Early Registration Start Date was not entered correctly. Format: MM/DD/YYYY");
                                }
                                    
                                try
                                {
                                    regEarlyEndDte = formatter.parse(earlyEndDte);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "eEnd1";
                                    errString += "eEnd1";
                                    lgError1.add("The Early Registration End Date was not entered correctly. Format: MM/DD/YYYY");
                                }
                                
                                //////Regular Dates/////
                                try
                                {
                                    regRegStrtDte = formatter.parse(regStrtDte);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "rStrt1";
                                    errString += "rStrt1";
                                    lgError1.add("The Regular Registration Start Date was not entered correctly. Format: MM/DD/YYYY");
                                }
                                    
                                try
                                {
                                    regRegEndDte = formatter.parse(regEndDte);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "rEnd1";
                                    errString += "rEnd1";
                                    lgError1.add("The Regular Registration End Date was not entered correctly. Format: MM/DD/YYYY");
                                }
                                
                                //////Late Dates/////
                                try
                                {
                                    regLtStrtDte = formatter.parse(ltStrtDte);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "lStrt1";
                                    errString += "lStrt1";
                                    lgError1.add("The Late Registration Start Date was not entered correctly. Format: MM/DD/YYYY");
                                }
                                    
                                try
                                {
                                    regLtEndDte = formatter.parse(ltEndDte);
                                }
                                catch(Exception ex)
                                {
                                    dateErr += "lEnd1";
                                    errString += "lEnd1";
                                    lgError1.add("The Late Registration End Date was not entered correctly. Format: MM/DD/YYYY");
                                }
                               
                                    
                                if(dateErr == null || dateErr.equals(""))
                                {
                                    regEarlyStrtDte = formatter.parse(earlyStrtDte);
                                    regEarlyEndDte = formatter.parse(earlyEndDte);
                                    regRegStrtDte = formatter.parse(regStrtDte);
                                    regRegEndDte = formatter.parse(regEndDte);
                                    regLtStrtDte = formatter.parse(ltStrtDte);
                                    regLtEndDte = formatter.parse(ltEndDte);
                                    
                                    
                                    /////Ends Before Starts?/////
                                    if(regEarlyEndDte.compareTo(regEarlyStrtDte) < 0)
                                    {
                                        errString += "eEnd2";
                                        lgError1.add("The Early Registration End Date can't be before the Start Date.");
                                    }
                                    
                                    if(regRegEndDte.compareTo(regRegStrtDte) < 0)
                                    {
                                        errString += "rEnd2";
                                        lgError1.add("The Regular Registration End Date can't be before the Start Date.");
                                    }
                                    
                                    if(regLtEndDte.compareTo(regLtStrtDte) < 0)
                                    {
                                        errString += "lEnd2";
                                        lgError1.add("The Late Registration End Date can't be before the Start Date.");
                                    }
                                    
                                    /////Regular Before Early?/////
                                    if(regRegStrtDte.compareTo(regEarlyStrtDte) < 0 || regRegStrtDte.compareTo(regEarlyEndDte) < 0)
                                    {
                                        errString += "rStrt2";
                                        lgError1.add("The Regular Registration Start Date can't be before the Early Registration Dates.");
                                    }
                                    
                                    if(regRegEndDte.compareTo(regEarlyStrtDte) < 0 || regRegEndDte.compareTo(regEarlyEndDte) < 0)
                                    {
                                        errString += "rEnd3";
                                        lgError1.add("The Regular Registration End Date can't be before the Early Registration Dates.");
                                    }
                                    
                                    /////Late Before Regular or Early?/////
                                    if(regLtStrtDte.compareTo(regRegStrtDte) < 0 || regLtStrtDte.compareTo(regRegEndDte) < 0 ||
                                            regLtStrtDte.compareTo(regEarlyStrtDte) < 0 || regLtStrtDte.compareTo(regEarlyEndDte) < 0)
                                    {
                                        errString += "lStrt2";
                                        lgError1.add("The Late Registration Start Date can't be before the Early or Regular Registration Dates.");
                                    }
                                    
                                    if(regLtEndDte.compareTo(regRegStrtDte) < 0 || regLtEndDte.compareTo(regRegEndDte) < 0 ||
                                            regLtEndDte.compareTo(regEarlyStrtDte) < 0 || regLtEndDte.compareTo(regEarlyEndDte) < 0)
                                    {
                                        errString += "lEnd3";
                                        lgError1.add("The Late Registration End Date can't be before the Early or Regular Registration Dates.");
                                    }
                                }
                            }
                            
                            ////////Costs/////////
                                String cstErr = "";
                                
                                /////Early Cost/////
                                if (IVString.ContainsText(earlyCst)) 
                                {
                                    if(IVString.IsNumeric(earlyCst))
                                    {
                                        //good
                                    }
                                    else
                                    {
                                        cstErr += "earlyCst2";
                                        errString += "earlyCst2";
                                        lgError1.add("Early Registration Cost must be numeric.");
                                    }
                                } 
                                else 
                                {
                                    cstErr += "earlyCst1";
                                    errString += "earlyCst1";
                                    lgError1.add("An Early Registration Cost was not entered.");
                                }
                                
                                /////Regular Cost/////
                                if (IVString.ContainsText(regCst)) 
                                {
                                    if(IVString.IsNumeric(regCst))
                                    {
                                        //good
                                    }
                                    else
                                    {
                                        cstErr += "regCst2";
                                        errString += "regCst2";
                                        lgError1.add("Regular Registration Cost must be numeric.");
                                    }
                                } 
                                else 
                                {
                                    cstErr += "regCst1";
                                    errString += "regCst1";
                                    lgError1.add("A Regular Registration Cost was not entered.");
                                }
                                
                                /////Late Cost/////
                                if (IVString.ContainsText(ltCst)) 
                                {
                                    if(IVString.IsNumeric(ltCst))
                                    {
                                        //good
                                    }
                                    else
                                    {
                                        cstErr += "ltCst2";
                                        errString += "ltCst2";
                                        lgError1.add("Late Registration Cost must be numeric.");
                                    }
                                } 
                                else 
                                {
                                    cstErr += "ltCst1";
                                    errString += "ltCst1";
                                    lgError1.add("A Late Registration Cost was not entered.");
                                }
                                
                                /////Cost Scale/////
                                if(cstErr == null || cstErr.equals(""))
                                {
                                    double early = Double.parseDouble(earlyCst);
                                    double reg = Double.parseDouble(regCst);
                                    double late = Double.parseDouble(ltCst);
                                    
                                    /////Late Cost Largest Amount?/////
                                    if(late <= reg || late <= early)
                                    {
                                        errString += "ltCst3";
                                        lgError1.add("Late Registration Cost must be greater than the Early and Regular Costs.");
                                    }
                                    
                                    /////Regular Cost Larger than Early/////
                                    if(reg <= early)
                                    {
                                        errString += "regCst3";
                                        lgError1.add("Regular Registration Cost must be greater than the Early Cost.");
                                    }
                                }
                             
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) 
                            {
                                session.setAttribute("hd3", "hidden");
                                url = "/evtNfo.jsp";
                                
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                
                                regEarlyStrtDte = formatter.parse(earlyStrtDte);
                                regEarlyEndDte = formatter.parse(earlyEndDte);
                                regRegStrtDte = formatter.parse(regStrtDte);
                                regRegEndDte = formatter.parse(regEndDte);
                                regLtStrtDte = formatter.parse(ltStrtDte);
                                regLtEndDte = formatter.parse(ltEndDte);
                                    
                                double early = Double.parseDouble(earlyCst);
                                double reg = Double.parseDouble(regCst);
                                double late = Double.parseDouble(ltCst);
                                
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                                if(updtInfo.updateEventInfoFromEID3(event1.getEID(), regEarlyStrtDte, regEarlyEndDte, early, regRegStrtDte, regRegEndDte, reg, regLtStrtDte, regLtEndDte, late)) 
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
                                session.setAttribute("er3", lgError1);
                                session.setAttribute("hd3", "");
                            }
                            
                            session.setAttribute("er3", lgError1);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println(ex);
                            url = "/index.jsp";
                            session.invalidate();
                            //session = request.getSession();
                        }
                    }
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
