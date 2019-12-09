/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.dbSignIn;
import database.getObjs;
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
                    LinkedHashMap<Integer, ArrayList<timeBlock>> timeBlockMap = (LinkedHashMap<Integer,ArrayList<timeBlock>>) session.getAttribute("timeBlockMap");
                    LinkedHashMap<Integer, timeBlock> regEvents = new LinkedHashMap<Integer, timeBlock>();

                    for (Event e : availableEvents) {
                        Object temp = (Object) request.getParameter(String.valueOf(e.getEID()));
                        if (temp != null) {
                            try {
                                String tempString = temp.toString();
                                timeBlock tempTB = new timeBlock();
                                if (tempString.equalsIgnoreCase("zz:zz")) {

                                } else {
                                    ArrayList<timeBlock> tempTBAL = timeBlockMap.get(e.getEID());
                                    for(timeBlock t : tempTBAL) {
                                        if(t.toString().equalsIgnoreCase(tempString)){
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
                    
                    if(regEvents.size()> 0){
                        session.setAttribute("regEvents", regEvents);
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
