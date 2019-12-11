/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.dbSignIn;
import database.getRef;
import database.updtInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import obj.Group;
import obj.School;
import obj.grpLeader;
import obj.uBase;
import obj.uDir;
import validation.IVString;

/**
 *
 * @author mckay
 */
public class objCrtrCont extends HttpServlet {

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

        String url = "/grpAdd.jsp";
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
                url = "/loginCont";
                session.invalidate();
            } else if (action.equalsIgnoreCase("ng")) { //new group
                url = "/grpAdd.jsp";
                String schl = request.getParameter("schl");
                if (schl != null){
                    session.setAttribute("schl", schl);
                }
                 uDir dir = dbSignIn.getDirectorfromUS(usr);
                ArrayList<String> grpTyp = getRef.getGrpTyps();
                session.setAttribute("grpTyp", grpTyp);
                session.setAttribute("hd1", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);

            } else if (action.equalsIgnoreCase("pg")) { //process group
                url = "/grpAdd.jsp";
                 uDir dir = dbSignIn.getDirectorfromUS(usr);
                session.setAttribute("hd1", "hidden");
                ArrayList<String> lgError1 = new ArrayList();
                session.setAttribute("er1", lgError1);
                try {
                    String grpNm;
                    String grpSz;
                    String grpTyp;
                    grpNm = request.getParameter("grpNm");
                    grpTyp = request.getParameter("grpTyp");
                    grpSz = request.getParameter("grpSz");
                    int schl = Integer.parseInt((String)session.getAttribute("schl"));
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
                    if (IVString.ContainsText(grpNm)) {
                        //do something
                        if (IVString.LengthLTET(grpNm, 75)) {

                        } else {
                            errString += "gn";
                            lgError1.add("Group Name must be less than 75 characters.");
                        }
                    } else {
                        errString += "gn";
                        lgError1.add("A Group Name was not entered.");
                    }

                    //<<<<<<<<<<<<<<<< Group Type Validation >>>>>>>>>>>>>>>>
                    if (IVString.ContainsText(grpTyp)) {
                        //do something
                        if (IVString.LengthLTET(grpTyp, 20)) {

                        } else {
                            errString += "gt";
                            lgError1.add("Group Type must be less than 20 characters.");
                        }
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
                    if (!IVString.ContainsText(ldrFNAME01) && !IVString.ContainsText(ldrLNAME01) && !IVString.ContainsText(ldrTITLE01)) {
                        //do something
                    } else {
                        if (IVString.LengthLTET(ldrFNAME01, 30) && IVString.LengthLTET(ldrLNAME01, 30) && IVString.LengthLTET(ldrTITLE01, 30)) {
                            input.add(new grpLeader(ldrFNAME01, ldrLNAME01, ldrTITLE01));
                        }
                    }
                    if (!IVString.ContainsText(ldrFNAME02) && !IVString.ContainsText(ldrLNAME02) && !IVString.ContainsText(ldrTITLE02)) {
                        //do something
                    } else {
                        if (IVString.LengthLTET(ldrFNAME02, 30) && IVString.LengthLTET(ldrLNAME02, 30) && IVString.LengthLTET(ldrTITLE02, 30)) {
                            input.add(new grpLeader(ldrFNAME02, ldrLNAME02, ldrTITLE02));
                        }
                    }
                    if (!IVString.ContainsText(ldrFNAME03) && !IVString.ContainsText(ldrLNAME03) && !IVString.ContainsText(ldrTITLE03)) {
                        //do something
                    } else {
                        if (IVString.LengthLTET(ldrFNAME03, 30) && IVString.LengthLTET(ldrLNAME03, 30) && IVString.LengthLTET(ldrTITLE03, 30)) {
                            input.add(new grpLeader(ldrFNAME03, ldrLNAME03, ldrTITLE03));
                        }
                    }

                    if (input.size() == 0) {
                        errString += "er";
                        lgError1.add("No valid Group Leaders were entered.");
                    }
                    //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                    //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
                    //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
                    if (errString == null || errString.equals("")) {
                        session.setAttribute("hd1", "hidden");
                        url = "/grpBaseCont";
                        //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                        if(updtInfo.updateGroupLeadersFromGID(updtInfo.addGroup(schl, grpNm, grpTyp, grpSz), input)) {
                                    dir = dbSignIn.getDirectorfromUS(usr);
                        }

                    } else {
                        session.setAttribute("hd1", "");
                        url = "/grpAdd.jsp";
                    }

                    session.setAttribute("er1", lgError1);

                } catch (Exception ex) {
                    System.out.println(ex);
                    url = "/index.jsp";
                    session.invalidate();
                    //session = request.getSession();
                }

            } else if (action.equalsIgnoreCase("ne")) { //new event

            } else if (action.equalsIgnoreCase("pe")) { //process event

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
