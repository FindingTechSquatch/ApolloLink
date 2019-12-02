/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.dbSignIn;
import database.reg;
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
                    

                    for (Event e : availableEvents) {
                        LocalDateTime tempTime;
                        ArrayList<timeBlock> timeBlockArray = new ArrayList<>();
                        tempTime = e.getStrtDteTm();
                        if(!availableEventDates.contains(e.getStrtDteTm().toLocalDate())){
                            availableEventDates.add(e.getStrtDteTm().toLocalDate());
                        }
                        while (tempTime.isBefore(e.getEndDteTm())) {
                            timeBlockArray.add(new timeBlock(tempTime, tempTime.plusMinutes(e.getBlckSize())));

                            tempTime = tempTime.plusMinutes(e.getBlckSize());
                        }
                        timeBlockMap.put(e.getEID(), timeBlockArray);
                    }
                    
                    session.setAttribute("availableEvents", availableEvents);
                    session.setAttribute("timeBlockMap", timeBlockMap);
                    session.setAttribute("availableEventDates",availableEventDates);
                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    session = request.getSession();
                }
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
