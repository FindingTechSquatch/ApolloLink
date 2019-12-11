/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import obj.*;
import database.*;
import encrypt.*;
import validation.*;

/**
 *
 * @author tyleryork
 */
public class loginCont extends HttpServlet {

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
        String url = "/index.jsp";

        HttpSession session = request.getSession();

        String action = request.getParameter("act");
        if (action == null) {
            action = "n";
            session.invalidate();
            session = request.getSession();
        }

        if (action.equalsIgnoreCase("n")) { //brand new, just accessing the site
            session.invalidate();
            session = request.getSession();
            session.setAttribute("hd1", "hidden");
            session.setAttribute("hd2", "hidden");
            ArrayList<String> lgError1 = new ArrayList();
            ArrayList<String> lgError2 = new ArrayList();
            session.setAttribute("er1", lgError1);
            session.setAttribute("er2", lgError1);
            ArrayList<School> schl = dbSignIn.getAllSchools();
            session.setAttribute("schl", schl);

        } else if (action.equalsIgnoreCase("su")) { //sign up
            session.setAttribute("hd1", "hidden");
            session.setAttribute("hd2", "hidden");
            ArrayList<String> lgError1 = new ArrayList();
            ArrayList<String> lgError2 = new ArrayList();
            session.setAttribute("er1", lgError1);
            session.setAttribute("er2", lgError2);

            String fn = request.getParameter("fn");
            String ln = request.getParameter("ln");
            String ph = request.getParameter("ph");
            String em = request.getParameter("em");
            String pw = request.getParameter("pw");
            String rpw = request.getParameter("rpw");

            ArrayList<School> schl = (ArrayList<School>) session.getAttribute("schl");
            String sl = request.getParameter("sl");
            School s1 = null;

            String errString = "";
            //<<<<<<<<<<<<<<<< First Name Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(fn)) {
                if (!IVString.IsNumeric(fn)) {
                    if (!IVString.ContainsSpecials(fn)) {
                        //DO SOMETHING
                    } else {
                        errString += "fn";
                        lgError2.add("First Name contained invalid characters.");
                    }
                } else {
                    errString += "fn";
                    lgError2.add("First Name contained invalid characters.");
                }
            } else {
                errString += "fn";
                lgError2.add("A First Name was not entered.");
            }
            //<<<<<<<<<<<<<<<< Last Name Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(ln)) {
                if (!IVString.IsNumeric(fn)) {
                    if (!IVString.ContainsSpecials(fn)) {
                        //DO SOMETHING
                    } else {
                        errString += "ln";
                        lgError2.add("Last Name contained invalid characters.");
                    }
                } else {
                    errString += "ln";
                    lgError2.add("Last Name contained invalid characters.");
                }
            } else {
                errString += "ln";
                lgError2.add("A Last Name was not entered.");
            }
            //<<<<<<<<<<<<<<<< Phone Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(ph)) {
                if (IVString.IsNumeric(ph)) {
                    //DO SOMETHING
                } else {
                    errString += "ph";
                    lgError2.add("Phone Number contained invalid characters.");
                }
            } else {
                errString += "ph";
                lgError2.add("A Phone Number was not entered.");
            }
            //<<<<<<<<<<<<<<<< Email Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(em)) {
                if (IVString.MatchesRegex(em, IVString.regexLib("email"))) {
                    if (!dbSignIn.chkUserExists(em)) {
                        //DO SOMETHING
                    } else {
                        errString += "em";
                        lgError2.add("Email already exists in system.");
                    }
                } else {
                    errString += "em";
                    lgError2.add("An invalid Email Address was entered.");
                }
            } else {
                errString += "em";
                lgError2.add("An Email Address was not entered.");
            }
            //<<<<<<<<<<<<<<<< Password Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(pw) && IVString.ContainsText(rpw)) {
                if (pw.equals(rpw)) {
                    //DO SOMETHING
                } else {
                    errString += "pw";
                    lgError2.add("Passwords did not match.");
                }
            } else {
                errString += "pw";
                lgError2.add("A Password was not entered.");
            }
            //<<<<<<<<<<<<<<<< School Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(sl)) {
                if (IVString.IsNumeric(sl)) {
                    int s2 = Integer.parseInt(sl);
                    for (int i = 0; i < schl.size(); i++) {
                        if (schl.get(i).getSID() == s2) {
                            s1 = schl.get(i);
                        }
                    }
                    if (s1 != null) {
                        //DO SOMETHING
                    } else {
                        errString += "sl";
                        lgError2.add("School was not found.");
                    }
                } else if (sl.equalsIgnoreCase("ZZZ")) {
                    errString += "zz";
                } else {
                    errString += "sl";
                    lgError2.add("School was not found.");
                }
            } else {
                errString += "sl";
                lgError2.add("A School was not entered.");
            }

            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
            if (errString == null || errString.equals("")) {
                session.setAttribute("hd2", "hidden");
                url = "/grpBaseCont";
                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                uDir newDir = new uDir();
                newDir.setfName(fn);
                newDir.setlName(ln);
                newDir.setPhone(ph);
                newDir.setUus(em);
                newDir.setHus(ec.EC_dus(em));
                newDir.setHpw(ec.EC_dpw(pw));
                ArrayList<School> insertSchool = new ArrayList<School>();
                insertSchool.add(s1);
                newDir.setSchls(insertSchool);
                newDir = dbSignIn.newUser(newDir);
                if (newDir == null) {
                    url = "/err500.jsp";
                } else {
                    session.setAttribute("usr", newDir.touBase());
                }
            } else if (errString.equalsIgnoreCase("zz")) {
                url = "/addSchl.jsp";
            } else {
                session.setAttribute("hd2", "");
                url = "/index.jsp";
                if (!errString.contains("fn")) {
                    session.setAttribute("fn", fn);
                    session.setAttribute("fnact", "active");
                }
                if (!errString.contains("ln")) {
                    session.setAttribute("ln", ln);
                    session.setAttribute("lnact", "active");
                }
                if (!errString.contains("ph")) {
                    session.setAttribute("ph", ph);
                    session.setAttribute("phact", "active");
                }
                if (!errString.contains("em")) {
                    session.setAttribute("em", em);
                    session.setAttribute("emact", "active");
                }
                if (!errString.contains("sl")) {
                    session.setAttribute("sl", sl);
                    session.setAttribute("slact", "active");
                }
            }

            session.setAttribute("er1", lgError1);
            session.setAttribute("er2", lgError2);

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<< Log In Action >>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>
        } else if (action.equalsIgnoreCase("lg")) { //regular log in
            session.setAttribute("hd1", "hidden");
            session.setAttribute("hd2", "hidden");
            ArrayList<String> lgError1 = new ArrayList();
            ArrayList<String> lgError2 = new ArrayList();
            session.setAttribute("er1", lgError1);
            session.setAttribute("er2", lgError1);
            String us = request.getParameter("us");
            String pw = request.getParameter("pw");

            String errString = "";
            //<<<<<<<<<<<<<<<< Username Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(us)) {
                if (IVString.MatchesRegex(us, IVString.regexLib("email"))) {
                    //do something
                } else {
                    errString += "us";
                    lgError1.add("An invalid Email Address was entered.");
                }
            } else {
                errString += "us";
                lgError1.add("An Email Address was not entered.");
            }
            //<<<<<<<<<<<<<<<< Password Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(pw)) {
                //Do Something
            } else {
                errString += "pw";
                lgError1.add("A Password was not entered.");
            }

            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
            if (errString == null || errString.equals("")) {
                session.setAttribute("hd1", "hidden");
                url = "/grpBaseCont";
                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                uBase frstUsr = new uBase();
                frstUsr.setUus(us);
                frstUsr.setHus(ec.EC_dus(us));
                frstUsr.setHpw(ec.EC_dpw(pw));
                
                
                if (dbSignIn.loginUser(frstUsr) == 1) {
                    //get user values
                    //uDir lgnDir = dbSignIn.getDirectorfromUS(frstUsr);
                    url = "/grpBaseCont";
                    session.setAttribute("usr", frstUsr); //setting the session user to only contain username/password info
                    session.removeAttribute("hd1");
                    session.removeAttribute("hd2");
                    session.removeAttribute("er1");
                    session.removeAttribute("er2");

                } else {
                    url = "/index.jsp";
                    session.setAttribute("hd1", "");
                    lgError1.add("Username/Password was incorrect.");
                }
            } else {
                session.setAttribute("hd1", "");
                url = "/index.jsp";
            }

            session.setAttribute("er1", lgError1);
            session.setAttribute("er2", lgError2);
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<< Event Manager Button Click >>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>
        } else if (action.equalsIgnoreCase("ev")) { //clicks event manager button
            url = "/evtlgn.jsp";
            session.setAttribute("hd1", "hidden");
            ArrayList<String> lgError1 = new ArrayList();
            session.setAttribute("er1", lgError1);
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<< Event Manager Log In Action >>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>
        } else if (action.equalsIgnoreCase("elg")) { //event manager login
            session.setAttribute("hd1", "hidden");
            ArrayList<String> lgError1 = new ArrayList();
            session.setAttribute("er1", lgError1);
            String us = request.getParameter("us");
            String pw = request.getParameter("pw");

            String errString = "";
            //<<<<<<<<<<<<<<<< Username Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(us)) {
                if (IVString.MatchesRegex(us, IVString.regexLib("email"))) {
                    //do something
                } else {
                    errString += "us";
                    lgError1.add("An invalid Email Address was entered.");
                }
            } else {
                errString += "us";
                lgError1.add("An Email Address was not entered.");
            }
            //<<<<<<<<<<<<<<<< Password Validation >>>>>>>>>>>>>>>>
            if (IVString.ContainsText(pw)) {
                //Do Something
            } else {
                errString += "pw";
                lgError1.add("A Password was not entered.");
            }

            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<< Final Validation >>>>>>>>>>>>>>>>
            //<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>
            if (errString == null || errString.equals("")) {
                session.setAttribute("hd1", "hidden");
                url = "/evtBaseCont";
                //<<<<<<<<<<<<<<<< Object Creation >>>>>>>>>>>>>>>>
                uBase frstUsr = new uBase(); //Event Manager
                frstUsr.setUus(us);
                frstUsr.setHus(ec.EC_eus(us));
                frstUsr.setHpw(ec.EC_epw(pw));


                if (dbSignIn.loginUser(frstUsr) == 1) {
                    //get user values
                    
                    url = "/evtBaseCont";
                    session.setAttribute("usr", frstUsr); //setting the session user to only contain username/password info
                    session.removeAttribute("hd1");
                    session.removeAttribute("hd2");
                    session.removeAttribute("er1");
                    session.removeAttribute("er2");

                } else {
                    url = "/evtlgn.jsp";
                    session.setAttribute("hd1", "");
                    lgError1.add("Username/Password was incorrect.");
                }
            } else {
                session.setAttribute("hd1", "");
                url = "/evtlgn.jsp";
            }
        } else {
            url = "index.jsp";
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
