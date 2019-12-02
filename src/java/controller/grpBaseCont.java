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
public class grpBaseCont extends HttpServlet {

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

        String url = "/grpLst.jsp";
        String action = request.getParameter("grpAct");
        if (action == null) {
            action = "nn"; //new, not char

        }
        uBase usr = (uBase) session.getAttribute("usr");
        if (usr == null) {
            url = "/loginCont";
            session.invalidate();
            //session = request.getSession();
        } else {
            if (action.equalsIgnoreCase("nn")) {
                url = "/grpLst.jsp";
                session.setAttribute("hd1", "hidden");
                session.setAttribute("hd2", "hidden");

                try {
                    //uDir usr = (uDir) session.getAttribute("usr");
                    uDir dir = dbSignIn.getDirectorfromUS(usr);
                    ArrayList<School> usr_Schls = dir.getSchls();
                    LinkedHashMap<Integer, Event> upcomingEvents = new LinkedHashMap<>();
                    for (int i = 0; i < dir.getSchls().size(); i++) {
                        for (int j = 0; j < dir.getSchls().get(i).getGrps().size(); j++) {
                            for (int k = 0; k < dir.getSchls().get(i).getGrps().get(j).getRegs().size(); k++) {
                                Event temp = getObjs.getEventsFromRID(dir.getSchls().get(i).getGrps().get(j).getRegs().get(k).getRID());

                                upcomingEvents.put(dir.getSchls().get(i).getGrps().get(j).getRegs().get(k).getRID(), temp);

                            }
                        }
                    }

                    session.setAttribute("dir", dir);
                    session.setAttribute("usr_Schls", usr_Schls);
                    session.setAttribute("up_Evts", upcomingEvents);
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }

            } else if (action.equalsIgnoreCase("cg")) {
                url = "/grpNfo.jsp";
                uDir dir = dbSignIn.getDirectorfromUS(usr);
                try {
                    int GID;
                    GID = Integer.parseInt(request.getParameter("grp"));
                    if (GID <= 0) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } else {
                        Group grpPass = new Group();
                        for (School s : dir.getSchls()) {
                            for (Group g : s.getGrps()) {
                                if (g.getGID() == GID) {
                                    grpPass = g;
                                    ArrayList<Registration> upcomingEvents = grpObjs.getUpcomingRegistrationsFromGID(g.getGID());
                                    ArrayList<Registration> pastEvents = grpObjs.getPastRegistrationsFromGID(g.getGID());
                                    LinkedHashMap<Integer, Event> evtPass = new LinkedHashMap<>();

                                    upcomingEvents.forEach((r) -> {
                                        Event temp = getObjs.getEventsFromRID(r.getRID());
                                        evtPass.put(r.getRID(), temp);
                                    });

                                    pastEvents.forEach((r) -> {
                                        Event temp = getObjs.getEventsFromRID(r.getRID());
                                        evtPass.put(r.getRID(), temp);
                                    });
                                    ArrayList<String> grpTyp = getRef.getGrpTyps();

                                    session.setAttribute("grpTyp", grpTyp);
                                    session.setAttribute("upcomingEvents", upcomingEvents);
                                    session.setAttribute("pastEvents", pastEvents);
                                    session.setAttribute("evtPass", evtPass);

                                }
                            }
                        }
                        session.setAttribute("grpPass", grpPass);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }

            } else if (action.equalsIgnoreCase("eg")) {
                url = "/grpNfo.jsp";
                uDir dir = dbSignIn.getDirectorfromUS(usr);
                session.setAttribute("hd1", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                try {
                    Group grpPass;
                    grpPass = (Group) session.getAttribute("grpPass");
                    if (grpPass == null) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } else {
                        try {
                            String grpNm;
                            String grpSz;
                            String grpTyp;
                            grpNm = request.getParameter("grpNm");
                            grpTyp = request.getParameter("grpTyp");
                            grpSz = request.getParameter("grpSz");
                            String errString = "";
                            //<<<<<<<<<<<<<<<< Group Name Validation >>>>>>>>>>>>>>>>
                            if (IVString.ContainsText(grpNm)) {
                                //do something
                            } else {
                                errString += "gn";
                                lgError1.add("A Group Name was not entered.");
                            }

                            //<<<<<<<<<<<<<<<< Group Type Validation >>>>>>>>>>>>>>>>
                            if (IVString.ContainsText(grpTyp)) {
                                //do something
                            } else {
                                errString += "gt";
                                lgError1.add("A Group Type was not entered.");
                            }
                            //<<<<<<<<<<<<<<<< Group Size Validation >>>>>>>>>>>>>>>>
                            if (IVString.ContainsText(grpSz)) {
                                if (IVString.IsNumeric(grpSz)) {

                                } else {
                                    errString += "gs";
                                    lgError1.add("Group Size contained invalid characters.");
                                }
                            } else {
                                errString += "gs";
                                lgError1.add("Group Size was not entered.");
                            }
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) {
                                session.setAttribute("hd1", "hidden");
                                url = "/grpNfo.jsp";
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                                if(updtInfo.updateGroupFromGID(grpPass.getGID(), grpNm, grpTyp, grpSz)) {
                                    dir = dbSignIn.getDirectorfromUS(usr);
                                    
                                    for(School s : dir.getSchls()) {
                                        for(Group g : s.getGrps()) {
                                            if(grpPass.getGID() == g.getGID()) {
                                                grpPass = g;
                                            }
                                        }
                                    }
                                    
                                }
                                
                           
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
                        ArrayList<Registration> upcomingEvents = grpObjs.getUpcomingRegistrationsFromGID(grpPass.getGID());
                        ArrayList<Registration> pastEvents = grpObjs.getPastRegistrationsFromGID(grpPass.getGID());
                        LinkedHashMap<Integer, Event> evtPass = new LinkedHashMap<>();

                        upcomingEvents.forEach((r) -> {
                            Event temp = getObjs.getEventsFromRID(r.getRID());
                            evtPass.put(r.getRID(), temp);
                        });

                        pastEvents.forEach((r) -> {
                            Event temp = getObjs.getEventsFromRID(r.getRID());
                            evtPass.put(r.getRID(), temp);
                        });
                        ArrayList<String> grpTyp = getRef.getGrpTyps();

                        session.setAttribute("grpTyp", grpTyp);
                        session.setAttribute("upcomingEvents", upcomingEvents);
                        session.setAttribute("pastEvents", pastEvents);
                        session.setAttribute("evtPass", evtPass);

                        session.setAttribute("grpPass", grpPass);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    //session = request.getSession();
                }

            } else if (action.equalsIgnoreCase("el")) {
                url = "/grpNfo.jsp";
                uDir dir = dbSignIn.getDirectorfromUS(usr);
                session.setAttribute("hd2", "hidden");
                ArrayList<String> lgError2 = new ArrayList();
                session.setAttribute("er2", lgError2);
                try {
                    Group grpPass;
                    grpPass = (Group) session.getAttribute("grpPass");
                    if (grpPass == null) {
                        url = "/index.jsp";
                        session.invalidate();
                        session = request.getSession();
                    } else {
                        try {
                            ArrayList<grpLeader> input = new ArrayList<>();
                            String ldrFNAME01, ldrFNAME02, ldrFNAME03;
                            String ldrLNAME01, ldrLNAME02, ldrLNAME03;
                            String ldrTITLE01, ldrTITLE02, ldrTITLE03;
                            
                            ldrFNAME01 = request.getParameter("ldrFNAME01");
                            ldrFNAME02 = request.getParameter("ldrFNAME02");
                            ldrFNAME03 = request.getParameter("ldrFNAME03");
                            ldrLNAME01 = request.getParameter("ldrLNAME01");
                            ldrLNAME02 = request.getParameter("ldrLNAME02");
                            ldrLNAME03 = request.getParameter("ldrLNAME03");
                            ldrTITLE01 = request.getParameter("ldrTITLE01");
                            ldrTITLE02 = request.getParameter("ldrTITLE02");
                            ldrTITLE03 = request.getParameter("ldrTITLE03");
                            
                            String errString = "";
                            //<<<<<<<<<<<<<<<< Group Name Validation >>>>>>>>>>>>>>>>
                            if (!IVString.ContainsText(ldrFNAME01)&&!IVString.ContainsText(ldrLNAME01)&&!IVString.ContainsText(ldrTITLE01)) {
                                //do something
                            } else {
                                input.add(new grpLeader(ldrFNAME01, ldrLNAME01, ldrTITLE01));
                            }
                            if (!IVString.ContainsText(ldrFNAME02)&&!IVString.ContainsText(ldrLNAME02)&&!IVString.ContainsText(ldrTITLE02)) {
                                //do something
                            } else {
                                input.add(new grpLeader(ldrFNAME02, ldrLNAME02, ldrTITLE02));
                            }
                            if (!IVString.ContainsText(ldrFNAME03)&&!IVString.ContainsText(ldrLNAME03)&&!IVString.ContainsText(ldrTITLE03)) {
                                //do something
                            } else {
                                input.add(new grpLeader(ldrFNAME03, ldrLNAME03, ldrTITLE03));
                            }

                            if(input.size() == 0) {
                                errString += "er";
                                lgError2.add("No valid Group Leaders were entered.");
                            }
                            
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                            if (errString == null || errString.equals("")) {
                                session.setAttribute("hd2", "hidden");
                                url = "/grpNfo.jsp";
                                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                                if(updtInfo.updateGroupLeadersFromGID(grpPass.getGID(), input)) {
                                    dir = dbSignIn.getDirectorfromUS(usr);
                                    
                                    for(School s : dir.getSchls()) {
                                        for(Group g : s.getGrps()) {
                                            if(grpPass.getGID() == g.getGID()) {
                                                grpPass = g;
                                            }
                                        }
                                    }
                                    
                                }
                                
                           
                            } else {
                                session.setAttribute("hd2", "");
                                url = "/grpNfo.jsp";
                            }

                            session.setAttribute("er1", lgError2);
                            

                        } catch (Exception ex) {
                            System.out.println(ex);
                            url = "/index.jsp";
                            session.invalidate();
                            //session = request.getSession();
                        }
                        ArrayList<Registration> upcomingEvents = grpObjs.getUpcomingRegistrationsFromGID(grpPass.getGID());
                        ArrayList<Registration> pastEvents = grpObjs.getPastRegistrationsFromGID(grpPass.getGID());
                        LinkedHashMap<Integer, Event> evtPass = new LinkedHashMap<>();

                        upcomingEvents.forEach((r) -> {
                            Event temp = getObjs.getEventsFromRID(r.getRID());
                            evtPass.put(r.getRID(), temp);
                        });

                        pastEvents.forEach((r) -> {
                            Event temp = getObjs.getEventsFromRID(r.getRID());
                            evtPass.put(r.getRID(), temp);
                        });
                        ArrayList<String> grpTyp = getRef.getGrpTyps();

                        session.setAttribute("grpTyp", grpTyp);
                        session.setAttribute("upcomingEvents", upcomingEvents);
                        session.setAttribute("pastEvents", pastEvents);
                        session.setAttribute("evtPass", evtPass);

                        session.setAttribute("grpPass", grpPass);
                    }
                } catch (Exception ex) {
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
