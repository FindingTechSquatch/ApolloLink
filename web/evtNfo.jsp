<%-- 
    Document   : index
    Created on : Oct 2, 2019, 12:07:38 PM
    Author     : tyleryork
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.ArrayList, java.util.LinkedHashMap, obj.*"%>
<%
    uEvt evt = (uEvt) session.getAttribute("evt");
    Event event1 = (Event) session.getAttribute("event1");
    LinkedHashMap<Integer, ArrayList<School>> regSchools = (LinkedHashMap<Integer,ArrayList<School>>) session.getAttribute("regSchools");
    //ArrayList<String> evtTyp = (ArrayList) session.getAttribute("evtTyp");
    ArrayList<String> er1 = (ArrayList) session.getAttribute("er1");
    ArrayList<String> er2 = (ArrayList) session.getAttribute("er2");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>ApolloLink</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="assets/iconfonts/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/iconfonts/ionicons/css/ionicons.css">
        <link rel="stylesheet" href="assets/iconfonts/typicons/src/font/typicons.css">
        <link rel="stylesheet" href="assets/iconfonts/flag-icon-css/css/flag-icon.min.css">
        <link rel="stylesheet" href="assets/css/card.css">
        <link rel="stylesheet" href="assets/css/buttons.css">
        <link rel="stylesheet" href="assets/css/start.css">
        <!-- endinject -->
        <!-- plugin css for this page -->
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="assets/css/vendor/style2.css">
        <!-- endinject -->
        <!-- Layout styles -->
        <link rel="stylesheet" href="assets/css/vendor/style1.css">
        <!-- End Layout styles -->
        <link rel="shortcut icon" href="assets/images/favicon.png" />
    </head>
    <body>
        <div class="container-scroller">
            <!-- partial:partials/_navbar.html -->
            <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                <div class="text-center navbar-brand-wrapper d-flex align-items-top justify-content-center" style="background:#111111">
                    <a class="navbar-brand brand-logo" href="index.html">
                        <img src="assets/images/logo/grad/White_Grad_Horizontal2.png" alt="logo" /> </a>
                    <a class="navbar-brand brand-logo-mini" href="index.html">
                        <img src="assets/images/logo/grad/White_Grad_Logo2.png" alt="logo" /> </a>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center">
                    <!-- TODO
                    <form class="ml-auto search-form d-none d-md-block" action="#">
                        <div class="form-group">
                            <input type="search" class="form-control" placeholder="Search Here">
                        </div>
                    </form> -->
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item dropdown d-none d-xl-inline-block user-dropdown">
                            <a class="nav-link dropdown-toggle" id="UserDropdown" href="#" data-toggle="dropdown" aria-expanded="false">
                                <img class="img-xs rounded-circle" src="assets/images/faces/face8.jpg" alt="Profile image"> </a>
                            <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="UserDropdown">
                                <div class="dropdown-header text-center">
                                    <img class="img-md rounded-circle" src="assets/images/faces/face8.jpg" alt="Profile image">
                                    <p class="mb-1 mt-3 font-weight-semibold"><%=evt.getfName()%> <%=evt.getlName()%></p>
                                    <p class="font-weight-light text-muted mb-0"><%=evt.getUus()%></p>
                                </div>
                                <a class="dropdown-item" href="configBaseCont?act=usr">User Settings<i class="dropdown-item-icon ti-dashboard"></i></a>
                                <a class="dropdown-item" href="configBaseCont?act=schl">School Settings<i class="dropdown-item-icon ti-comment-alt"></i></a>
                                <a class="dropdown-item" href="mailto:tyler@tylerryork.com">Contact Us<i class="dropdown-item-icon ti-comment-alt"></i></a>
                                <a class="dropdown-item" href="logout">Sign Out<i class="dropdown-item-icon ti-location-arrow"></i></a>
                                <!--TODO
                                <a class="dropdown-item">My Profile <span class="badge badge-pill badge-danger">1</span><i class="dropdown-item-icon ti-dashboard"></i></a>
                                <a class="dropdown-item">Messages<i class="dropdown-item-icon ti-comment-alt"></i></a>
                                <a class="dropdown-item">Activity<i class="dropdown-item-icon ti-location-arrow"></i></a>
                                <a class="dropdown-item">FAQ<i class="dropdown-item-icon ti-help-alt"></i></a>
                                <a class="dropdown-item">Sign Out<i class="dropdown-item-icon ti-power-off"></i></a>-->
                            </div>
                        </li>
                    </ul>
                    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                        <span class="mdi mdi-menu"></span>
                    </button>
                </div>
            </nav>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- partial:partials/_sidebar.html -->
                <nav class="evtsidebar sidebar-offcanvas" id="sidebar">
                    <ul class="nav">
                        <li class="nav-item nav-category">Main Menu</li>
                        <li class="nav-item">
                            <a class="nav-link" href="evtLst.jsp">
                                <i class="menu-icon typcn typcn-document-text"></i>
                                <span class="menu-title">My Events</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="evtAdd.jsp" value="addEvt">
                                <i class="menu-icon typcn typcn-shopping-bag"></i>
                                <span class="menu-title">Add Event</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- partial -->
                <div class="main-panel">
                    <div class="content-wrapper">
                        <!-- Page Title Header Starts-->
                        <div class="row page-title-header">
                            <div class="col-12">
                                <div class="page-header">
                                    <h4 class="page-title"></h4>
                                </div>
                            </div>

                        </div>
                        <!-- Page Title Header Ends-->

                        <div class="row">
                            
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Event Information</h4>
                                            <div class="topcorner" id="edtEvtNfo">
                                            <a style="border-radius: 0px 0px 0px 4px;" id="edtEvtNfotxt">Edit</a>
                                        </div>
                                        <div class="alert" ${hd1}>
                                            <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span>
                                            <ul>
                                                <%// for (String e : er1) {%>
                                                <li>
                                                    <%//=e%>
                                                </li>
                                                <%// }%>
                                            </ul>
                                        </div>
                                            <div class="d-flex flex-column flex-lg-row">
                                            <form action="evtBaseCont" method="post">
                                                <input type="hidden" name="evtAction" value="editEvt1">
                                                <table>
                                                    <tr>
                                                        <td><span class="card-li-title">Name</span></td>
                                                        <td><input class="input evtInfo" type="text" disabled="true" name="evtNm" id="edtEvtNm" value="<%=event1.getName()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Host</span></td>
                                                        <td><input class="input evtInfo" type="text" disabled="true" name="evtHst" id="edtEvtHst" value="<%=event1.getHost()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Type</span></td>
                                                        <td><!--<select class="input evtInfo" type="select"  name="evtTyp" id="edtEvtTyp">
                                                            <%//for(String e : evtTyp) {%>
                                                                <%//if(e.equalsIgnoreCase(event1.getType())) {%>
                                                                <option value="<%//=e%>" selected><%//=e%></option>
                                                                <%//} else {%>
                                                                <option value="<%//=e%>"><%//=e%></option>
                                                                <%// } }%>
                                                            </select>
                                                            <!--<datalist id="evtTypes">
                                                                <option value="Marching Band">
                                                                <option value="Choir">
                                                                <option value="Orchestra">
                                                            </datalist>
                                                             list="evtTypes"-->
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Start</span></td>
                                                        <td><input class="input evtInfo" type="datetime-local"  name="evtStrtDteTm" id="edtEvtStrtDteTm" value="<%=event1.getStrtDteTm()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">End</span></td>
                                                        <td><input class="input evtInfo" type="datetime-local" disabled="true" name="evtEndDteTm" id="edtEvtEndDteTm" value="<%=event1.getEndDteTm()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Block Size</span></td>
                                                        <td><input class="input evtInfo" type="text" disabled="true" name="evtBlkSz" id="edtEvtBlkSz" value="<%=event1.getBlckSize()%>"></td>
                                                    </tr>
                                                </table>
                                                <input type="submit" class="btn btn-dark btn-fw" value="Save" id="edtevtNfoBtn1" hidden>
                                            </form>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Event Location</h4>
                                            <div class="d-flex flex-column flex-lg-row">
                                            <form action="evtBaseCont" method="post">
                                                <table>
                                                    <tr>
                                                        <td><span class="card-li-title">Location</span></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtLoc" id="edtEvtLoc" value="<%=event1.getLocTitle()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Address 1</span></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtAddr1" id="edtEvtAddr1" value="<%=event1.getAddr1()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Address 2</span></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtAddr2" id="edtEvtAddr2" 
                                                                   <% if(event1.getAddr2() == null) {%>
                                                                        value="  "
                                                                   <% } else { %>
                                                                        value="<%=event1.getAddr2()%>"
                                                                   <% } %>
                                                                   ></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">City</span></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtCity" id="edtEvtCity" value="<%=event1.getCity()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">State</span></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtState" id="edtEvtNState" value="<%=event1.getState()%>"></td>
                                                    </tr>
                                                    
                                                </table>
                                                <input type="submit" class="btn btn-dark btn-fw" value="Update">
                                            </form>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="row">
                            <div class="col-md-12 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Registration Information</h4>
                                            <div class="d-flex flex-column flex-lg-row">
                                            <form action="evtBaseCont" method="post">
                                                <table>
                                                    <th></th>
                                                    <th class="card-li-title">Start Date</th>
                                                    <th class="card-li-title">End Date</th>
                                                    <th class="card-li-title">Cost</th>
                                                    <tr>
                                                        <td><span class="card-li-title">Early Registration</span></td>
                                                        <td><input class="input" type="date"  name="evtEarlyStrtDte" id="edtEvtEarlyStrtDte" value="<%=event1.getRegEarlyStrtDte()%>"></td>
                                                        <td><input class="input" type="date" disabled="true" name="evtEarlyEndDte" id="edtEvtEarlyEndDte" value="<%=event1.getRegEarlyEndDte()%>"></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtEarlyCst" id="edtEvtEarlyCst" value="<%=event1.getRegEarlyCst()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Regular Registration</span></td>
                                                        <td><input class="input" type="date" disabled="true" name="evtRegStrtDte" id="edtEvtRegStrtDte" value="<%=event1.getRegRegStrtDte()%>"></td>
                                                        <td><input class="input" type="date" disabled="true" name="evtRegEndDte" id="edtEvtRegEndDte" value="<%=event1.getRegRegEndDte()%>"></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtRegCst" id="edtEvtRegCst" value="<%=event1.getRegRegCst()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Late Registration</span></td>
                                                        <td><input class="input" type="date" disabled="true" name="evtLateStrtDte" id="edtEvtLateStrtDte" value="<%=event1.getRegLtStrtDte()%>"></td>
                                                        <td><input class="input" type="date" disabled="true" name="evtLateEndDte" id="edtEvtLateEndDte" value="<%=event1.getRegLtEndDte()%>"></td>
                                                        <td><input class="input" type="text" disabled="true" name="evtLateCst" id="edtEvtLateCst" value="<%=event1.getRegLtCst()%>"></td>
                                                    </tr>
                                                </table>
                                                <input type="submit" class="btn btn-dark btn-fw" value="Update">
                                            </form>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="row">
                            <div class="col-md-8 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Registered Performances</h4>
                                            <div class="card-list d-flex flex-column flex-lg-row">
                                                <table>
                                                    <!--update once sql statemnet is fixed-->
                                                    <tr>
                                                        <td>    
                                                            <% if(regSchools.containsKey(event1.getEID())) { %>
                                                                <% for (School s: regSchools.get(event1.getEID())) {%>
                                                                    <% if(s.getSchlName().toString() == null) {%>
                                                                        No Registered Schools
                                                                    <% } else { %>
                                                                        <%=s.getSchlName().toString()%>
                                                                    <% }%>
                                                                <% }%>
                                                            <% } else { %>
                                                                No Registered Schools
                                                            <% }%>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Blocks Available</h4>
                                            <div class="card-list d-flex flex-column flex-lg-row">
                                                <table>
                                                    <tr>
                                                        <td>
                                                            Block 1
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            Block 2
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- content-wrapper ends -->
                    <!-- partial:partials/_footer.html -->
                    <footer class="footer">
                        <div class="container-fluid clearfix">
                            <span class="text-muted d-block text-center text-sm-left d-sm-inline-block">Copyright &#169; 2019 FindingTechSquatch. A division of the TRYHard Lab. All rights reserved.</span>
                            </span>
                        </div>
                    </footer>
                    <!-- partial -->
                </div>
                <!-- main-panel ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <!-- container-scroller -->
        <!-- plugins:js -->
        <script src="assets/js/vendor/vendor.bundle.base.js"></script>
        <script src="assets/js/vendor/vendor.bundle.addons.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page-->
        <!-- End plugin js for this page-->
        <!-- inject:js -->
        <script src="assets/js/vendor/off-canvas.js"></script>
        <script src="assets/js/vendor/misc.js"></script>
        <!-- endinject -->
        <!-- Custom js for this page-->
        <script src="assets/js/vendor/dashboard.js"></script>
        <!-- End custom js for this page-->
    </body>
</html>
