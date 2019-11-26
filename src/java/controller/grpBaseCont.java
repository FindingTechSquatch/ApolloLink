/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.dbSignIn;
import database.getObjs;
import database.grpObjs;
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
            url = "index.jsp";
            session.invalidate();
            session = request.getSession();
        } else {
            if (action.equalsIgnoreCase("nn")) {
                url = "/grpLst.jsp";

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
                                    LinkedHashMap <Integer, Event> evtPass = new LinkedHashMap<>();
                                    
                                    upcomingEvents.forEach((r) -> {
                                        Event temp = getObjs.getEventsFromRID(r.getRID());
                                        evtPass.put(r.getRID(), temp);
                                    });
                                    
                                    pastEvents.forEach((r) -> {
                                        Event temp = getObjs.getEventsFromRID(r.getRID());
                                        evtPass.put(r.getRID(), temp);
                                    });
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
