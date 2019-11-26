<%-- 
    Document   : index
    Created on : Oct 2, 2019, 12:07:38 PM
    Author     : tyleryork
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, java.util.LinkedHashMap, obj.*"%>
<%
    uDir dir = (uDir) session.getAttribute("dir");
    Group grpPass = (Group) session.getAttribute("grpPass");
    ArrayList<obj.Registration> upcomingEvents = (ArrayList) session.getAttribute("upcomingEvents");
    ArrayList<obj.Registration> pastEvents = (ArrayList) session.getAttribute("pastEvents");
    LinkedHashMap<Integer, Event> evtPass = (LinkedHashMap<Integer, Event>) session.getAttribute("evtPass");

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
        <link rel="stylesheet" href="assets/css/start.css">
        <link rel="stylesheet" href="assets/css/card.css">
        <link rel="stylesheet" href="assets/css/buttons.css">
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
                <div class="text-center navbar-brand-wrapper d-flex align-items-top justify-content-center">
                    <a class="navbar-brand brand-logo" href="index.html">
                        <img src="assets/images/logo/grad/Black_Grad_Horizontal3_2.png" alt="logo" /> </a>
                    <a class="navbar-brand brand-logo-mini" href="index.html">
                        <img src="assets/images/logo/grad/Black_Grad_Logo3_2.png" alt="logo" /> </a>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center">
                    <h2> <%=grpPass.getGrpName()%> </h2>
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
                                    <p class="mb-1 mt-3 font-weight-semibold"><%=dir.getfName()%> <%=dir.getlName()%></p>
                                    <p class="font-weight-light text-muted mb-0"><%=dir.getUus()%></p>
                                </div>
                                <a class="dropdown-item">User Settings<i class="dropdown-item-icon ti-dashboard"></i></a>
                                <a class="dropdown-item">School Settings<i class="dropdown-item-icon ti-comment-alt"></i></a>
                                <a class="dropdown-item">Contact Us<i class="dropdown-item-icon ti-comment-alt"></i></a>
                                <a class="dropdown-item">Sign Out<i class="dropdown-item-icon ti-location-arrow"></i></a>
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
                <nav class="sidebar sidebar-offcanvas" id="sidebar">
                    <ul class="nav">
                        <li class="nav-item nav-category">Main Menu</li>
                        <li class="nav-item">
                            <a class="nav-link" href="grpLst.jsp">
                                <i class="menu-icon typcn typcn-document-text"></i>
                                <span class="menu-title">My Groups</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="grpAdd.jsp">
                                <i class="menu-icon typcn typcn-shopping-bag"></i>
                                <span class="menu-title">Add Group</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="">
                                <i class="menu-icon typcn typcn-th-large-outline"></i>
                                <span class="menu-title">Upcoming Events</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="">
                                <i class="menu-icon typcn typcn-bell"></i>
                                <span class="menu-title">Event Results</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <div class="main-panel">
                    <div class="content-wrapper">
                        <!-- Page Title Header Ends-->
                        <div class="row">
                            <div class="col-md-12 grid-margin">
                                <div class="card card-clickable">
                                    <div class="card-body">
                                        <form action="grpPages" method="post">
                                            <input type="hidden" name="grp" value="000">
                                            <input type="submit" class="card-clickable-title card-title mb-0" value="Register for Upcoming Events">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title mb-0"> Group Information </h4>
                                        <div class="topcorner" id="edtGrpNfo">
                                            <a style="border-radius: 0px 0px 0px 4px;" >Edit</a>
                                        </div>

                                        <div class="card-list d-flex flex-column flex-lg-row">
                                            <form action="grpPages" method="post">
                                                <table>
                                                    <tr>
                                                        <td><span class="card-li-title">Group Name</span></td>
                                                        <td><input class="input grpInfo" type="text" disabled="true" name="grpNm" id="edtGrpNm" value="<%=grpPass.getGrpName()%>"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Group Type</span></td>
                                                        <td><select class="input grpInfo" type="select" disabled="true" id="edtGrpTp" name="grpTyp" value="<%=grpPass.getGrpType()%>">
                                                                <option value="Marching Band">Marching Band</option>
                                                                <option value="Choir">Choir</option>
                                                                <option value="Orchestra">Orchestra</option>
                                                                <option value="Jazz Band">Jazz Band</option>
                                                                <option value="Concert Band">Concert Band</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td><span class="card-li-title">Group Size</span></td>
                                                        <td><input class="input grpInfo" type="text" disabled="true" name="grpSz" id="edtGrpSz" value="<%=grpPass.getGrpSize()%>"></td>
                                                    </tr>
                                                </table>
                                                <input type="submit" class="btn btn-dark btn-fw" value="Save" id="edtGrpNfobtn" hidden>
                                            </form>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Group Leaders</h4>
                                            <div class="topcorner">
                                                <a style="border-radius: 0px 0px 0px 4px;">Edit</a>
                                            </div>
                                            <div class="card-list d-flex flex-column flex-lg-row">
                                                <form action="grpPages" method="post">
                                                    <table>
                                                        <tr>
                                                            <th class="card-li-title grpShrt">First Name</th>
                                                            <th class="card-li-title grpShrt">Last Name</th>
                                                            <th class="card-li-title grpLng">Title</th>
                                                        </tr>
                                                        <% for (grpLeader l : grpPass.getLdrs()) {%>
                                                        <tr>
                                                            <td><input class="grpInput" type="text" disabled="true" name="ldrFNAME01" value="<%=l.getLdrFName()%>"</td>
                                                            <td><input class="grpInput" type="text" disabled="true" name="ldrLNAME" value="<%=l.getLdrLName()%>"</td>
                                                            <td><input class="grpInput" type="text" disabled="true" name="ldrTITLE" value="<%=l.getLdrTitle()%>"</td>
                                                    </tr>
                                                    <% }%>
                                                </table>
                                                <button class="btn btn-dark btn-fw">Add</button>
                                                <input type="submit" class="btn btn-dark btn-fw" value="Save">
                                            </form>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 grid-margin">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title mb-0"> Upcoming Events </h4>
                                    <%for (obj.Registration r : upcomingEvents) {%>
                                    <%String val = r.getSelDteTm().format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:m:sa"));%>
                                    <form action="grpBaseCont" method="post">
                                        <input type="hidden" name="grp" value="<%=r.getRID()%>">
                                        <input type="submit" class="card-clickable-title card-title mb-0" value="<%=r.getSelDteTm().format(DateTimeFormatter.ofPattern("MMMM dd, uuuu hh:mm:ssa"))%> - <%=evtPass.get(r.getRID()).getFullDisplay()%> ">
                                    </form>
                                    <%}%>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 grid-margin">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title mb-0"> Past Events </h4>
                                    <%for (obj.Registration r : pastEvents) {%>
                                    <%String val = r.getSelDteTm().format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:m:sa"));%>
                                    <form action="grpBaseCont" method="post">
                                        <input type="hidden" name="grp" value="<%=r.getRID()%>">
                                        <input type="submit" class="card-clickable-title card-title mb-0" value="<%=r.getSelDteTm().format(DateTimeFormatter.ofPattern("MMMM dd, uuuu hh:mm:ssa"))%> - <%=evtPass.get(r.getRID()).getFullDisplay()%> ">
                                    </form>
                                    <%}%>


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
    <script src="assets/js/grpAct.js"></script>
    <!-- endinject -->
    <!-- Custom js for this page-->
    <script src="assets/js/vendor/dashboard.js"></script>
    <!-- End custom js for this page-->
</body>
</html>
