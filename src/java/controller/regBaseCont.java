/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.dbSignIn;
import database.getObjs;
import database.getRef;
import database.reg;
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
public class regBaseCont extends HttpServlet {

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
        HttpSession session = request.getSession();

        String url = "/uEvtLst.jsp";
        String action = request.getParameter("act");
        if (action == null) {
            action = "nn"; //new, not char
            session.setAttribute("hd1", "hidden");
        }
        uBase usr = (uBase) session.getAttribute("usr");
        if (usr == null) {
            url = "/loginCont";
            session.invalidate();
            //session = request.getSession();
        } else {
            if (action.equalsIgnoreCase("nn")) {
                try {

                    uDir dir = dbSignIn.getDirectorfromUS(usr);
                    Group grpPass;
                    grpPass = (Group) session.getAttribute("grpPass");
                    ArrayList<Event> availableEvents = reg.getAvailableEventsFromGID(grpPass.getGID());
                    ArrayList<LocalDate> availableEventDates = new ArrayList<>();
                    LinkedHashMap<Integer, ArrayList<timeBlock>> timeBlockMap = new LinkedHashMap<>();
                    ArrayList<timeBlock> timeBlockArray = new ArrayList<>();

                    for (Event e : availableEvents) {
                        LocalDateTime tempTime;

                        tempTime = e.getStrtDteTm();
                        if (!availableEventDates.contains(e.getStrtDteTm().toLocalDate())) {
                            availableEventDates.add(e.getStrtDteTm().toLocalDate());
                        }
                        while (tempTime.isBefore(e.getEndDteTm())) {
                            timeBlockArray.add(new timeBlock(tempTime, tempTime.plusMinutes(e.getBlckSize()), getObjs.getAvailableTimeBlockFromEIDTime(e.getEID(), tempTime)));

                            tempTime = tempTime.plusMinutes(e.getBlckSize());
                        }
                        timeBlockMap.put(e.getEID(), timeBlockArray);
                    }

                    for (timeBlock t : timeBlockArray) {

                    }

                    session.setAttribute("availableEvents", availableEvents);
                    session.setAttribute("timeBlockMap", timeBlockMap);
                    session.setAttribute("availableEventDates", availableEventDates);
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }
            } else if (action.equalsIgnoreCase("rg")) {
                try {
                    session.setAttribute("hd1", "hidden");
                    uDir dir = dbSignIn.getDirectorfromUS(usr);
                    Group grpPass;
                    grpPass = (Group) session.getAttribute("grpPass");
                    ArrayList<Event> availableEvents = (ArrayList<Event>) session.getAttribute("availableEvents");
                    LinkedHashMap<Integer, ArrayList<timeBlock>> timeBlockMap = (LinkedHashMap<Integer, ArrayList<timeBlock>>) session.getAttribute("timeBlockMap");
                    LinkedHashMap<Integer, timeBlock> regEvents = new LinkedHashMap<>();
                    ArrayList<String> allPerfTypes = new ArrayList<>();

                    for (Event e : availableEvents) {
                        Object temp = (Object) request.getParameter(String.valueOf(e.getEID()));
                        if (temp != null) {
                            try {
                                String tempString = temp.toString();
                                timeBlock tempTB = new timeBlock();
                                if (tempString.equalsIgnoreCase("zz:zz")) {

                                } else {
                                    ArrayList<timeBlock> tempTBAL = timeBlockMap.get(e.getEID());
                                    for (timeBlock t : tempTBAL) {
                                        if (t.toString().equalsIgnoreCase(tempString)) {
                                            tempTB = t;
                                        }
                                    }
                                    regEvents.put(e.getEID(), tempTB);
                                }

                            } catch (Exception ex) {
                                System.out.println(ex);
                                url = "/regBaseCont";
                            }
                        }

                    }
                    allPerfTypes = getRef.getPrfTyps();

                    if (regEvents.size() > 0) {
                        session.setAttribute("regEvents", regEvents);
                        if (allPerfTypes.size() > 0) {
                            session.setAttribute("allPerfTypes", allPerfTypes);
                        }
                        url = "/evtPrf.jsp";
                    } else {
                        session.setAttribute("hd1", "");
                        ArrayList<String> lgError1 = new ArrayList();
                        session.setAttribute("er1", lgError1);
                        lgError1.add("No Events were chosen.");
                        session.setAttribute("er1", lgError1);
                        url = "/uEvtLst.jsp";
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }
            } else if (action.equalsIgnoreCase("rp")) {
                try {
                    session.setAttribute("hd1", "hidden");
                    ArrayList<String> lgError1 = new ArrayList();
                    session.setAttribute("er1", lgError1);
                    String errString = "";
                    uDir dir = dbSignIn.getDirectorfromUS(usr);
                    Group grpPass;
                    grpPass = (Group) session.getAttribute("grpPass");
                    School regSchlPass = new School();
                    for (School s : dir.getSchls()) {
                        for (Group g : s.getGrps()) {
                            if (g.getGID() == grpPass.getGID()) {
                                regSchlPass = s;
                                ArrayList<Group> tempALG = new ArrayList<>();
                                tempALG.add(g);
                                regSchlPass.setGrps(tempALG);
                            }
                        }
                    }
                    session.setAttribute("regSchlPass", regSchlPass);
                    ArrayList<Event> availableEvents = (ArrayList<Event>) session.getAttribute("availableEvents");
                    LinkedHashMap<Integer, timeBlock> regEvents = (LinkedHashMap<Integer, timeBlock>) session.getAttribute("regEvents");
                    LinkedHashMap<Event, timeBlock> regInfo = new LinkedHashMap<>();

                    /* Request Variables */
                    String s_prfTitle = request.getParameter("prfTtl");
                    String s_prfType = request.getParameter("prfTyp");
                    String s_prfStaff = request.getParameter("prfStaff");
                    String s_prfBus = request.getParameter("prfBus");
                    String s_prfTrk = request.getParameter("prfTrk");
                    String s_prfSng1 = request.getParameter("prfSng1");
                    String s_prfSng2 = request.getParameter("prfSng2");
                    String s_prfSng3 = request.getParameter("prfSng3");
                    String s_prfSng4 = request.getParameter("prfSng4");
                    String s_prfSng5 = request.getParameter("prfSng5");
                    String s_prfPre = request.getParameter("prfPre");
                    String s_prfPost = request.getParameter("prfPost");

                    //<<<<<<<<<<<<<<<< Performance Title Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfTitle)) {
                        if (IVString.LengthLTET(s_prfTitle, 50)) {
                            //do something
                        } else {
                            errString += "a1";
                            lgError1.add("Performance Title must be less than 50 Characters.");
                        }
                    } else {
                        errString += "a1";
                        lgError1.add("A Performance Title was not entered.");
                    }
                    //<<<<<<<<<<<<<<<< Performance Type Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfType)) {

                    } else {
                        errString += "b2";
                        lgError1.add("A Performance Type was not entered.");
                    }
                    //<<<<<<<<<<<<<<<< Performance Additional Staff Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfStaff)) {
                        if (IVString.LengthLTET(s_prfStaff, 150)) {
                            //do something
                        } else {
                            errString += "c3";
                            lgError1.add("Performance Title must be less than 150 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Bus Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfBus)) {
                        if (IVString.IsNumeric(s_prfBus)) {
                            //do something
                        } else {
                            errString += "d4";
                            lgError1.add("Number of Buses must be numeric.");
                        }
                    } else {
                        errString += "d4";
                        lgError1.add("Number of Buses were not entered.");
                    }
                    //<<<<<<<<<<<<<<<< Truck Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfTrk)) {
                        if (IVString.IsNumeric(s_prfTrk)) {
                            //do something
                        } else {
                            errString += "e5";
                            lgError1.add("Number of Equipment Trucks must be numeric.");
                        }
                    } else {
                        errString += "e5";
                        lgError1.add("Number of Equipment Trucks were not entered.");
                    }
                    //<<<<<<<<<<<<<<<< Performance Song Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfSng1)) {
                        if (IVString.LengthLTET(s_prfSng1, 30)) {
                            //do something
                        } else {
                            errString += "f6";
                            lgError1.add("Song 1 must be less than 30 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Performance Song Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfSng2)) {
                        if (IVString.LengthLTET(s_prfSng2, 30)) {
                            //do something
                        } else {
                            errString += "g7";
                            lgError1.add("Song 2 must be less than 30 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Performance Song Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfSng3)) {
                        if (IVString.LengthLTET(s_prfSng3, 30)) {
                            //do something
                        } else {
                            errString += "h8";
                            lgError1.add("Song 3 must be less than 30 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Performance Song Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfSng4)) {
                        if (IVString.LengthLTET(s_prfSng4, 30)) {
                            //do something
                        } else {
                            errString += "i9";
                            lgError1.add("Song 4 must be less than 30 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Performance Song Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfSng5)) {
                        if (IVString.LengthLTET(s_prfSng5, 30)) {
                            //do something
                        } else {
                            errString += "j1";
                            lgError1.add("Song 5 must be less than 30 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Performance Pre-Announcement Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfPre)) {
                        if (IVString.LengthLTET(s_prfPre, 500)) {
                            //do something
                        } else {
                            errString += "k2";
                            lgError1.add("Pre-Announcement must be less than 500 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }
                    //<<<<<<<<<<<<<<<< Performance Pre-Announcement Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(s_prfPost)) {
                        if (IVString.LengthLTET(s_prfPost, 500)) {
                            //do something
                        } else {
                            errString += "l3";
                            lgError1.add("Post-Announcement must be less than 500 Characters.");
                        }
                    } else {
                        //doing nothing intentionally. Not required field, but don't want to submit null to LTET
                    }

                    if (errString == null || errString.equals("")) {
                        session.setAttribute("hd1", "hidden");
                        url = "/regRvw.jsp";
                        //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                        Registration reg = new Registration();
                        reg.setPerfTitle(s_prfTitle);
                        reg.setType(s_prfType);
                        reg.setAddlStff(s_prfStaff);
                        reg.setBus(Integer.parseInt(s_prfBus));
                        reg.setTruck(Integer.parseInt(s_prfTrk));
                        reg.setSong1(s_prfSng1);
                        reg.setSong2(s_prfSng2);
                        reg.setSong3(s_prfSng3);
                        reg.setSong4(s_prfSng4);
                        reg.setSong5(s_prfSng5);
                        reg.setPreAnnounce(s_prfPre);
                        reg.setPostAnnounce(s_prfPost);

                        session.setAttribute("reg", reg);

                        for (Event e : availableEvents) {
                            if (regEvents.get(e.getEID()) != null) {
                                regInfo.put(e, regEvents.get(e.getEID()));
                            }
                        }
                        session.setAttribute("regInfo", regInfo);

                    } else {
                        session.setAttribute("hd1", "");
                        url = "/evtPrf.jsp";
                    }

                    session.setAttribute("er1", lgError1);

                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }
            } else if (action.equalsIgnoreCase("ra")) {
                session.setAttribute("hd1", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                String errString = "";
                uDir dir = dbSignIn.getDirectorfromUS(usr);
                Group grpPass;
                grpPass = (Group) session.getAttribute("grpPass");
                Registration reg = (Registration) session.getAttribute("reg");
                ArrayList<obj.Event> availableEvents = (ArrayList<obj.Event>) session.getAttribute("availableEvents");
                LinkedHashMap<obj.Event, timeBlock> regInfo = (LinkedHashMap<obj.Event, timeBlock>) session.getAttribute("regInfo");

                for (Event e : availableEvents) {
                    if (regInfo.get(e) != null) {
                        //Insert into database
                        //pass reg,e.getEID(), grpPass.getGID(), timeblock
                        if(database.reg.registerForEvents(grpPass.getGID(), e.getEID(), reg, regInfo.get(e))) {
                            //email here
                        }
                    }
                }
                
                url = "/grpBaseCont";

            } else {

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
