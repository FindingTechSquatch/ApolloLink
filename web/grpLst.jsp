<%-- 
    Document   : index
    Created on : Oct 2, 2019, 12:07:38 PM
    Author     : tyleryork
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,java.util.LinkedHashMap, obj.*"%>
<%
    uDir dir = (uDir) session.getAttribute("dir");
    ArrayList<School> usr_Schls = (ArrayList) session.getAttribute("usr_Schls");
    LinkedHashMap<Integer,Event> upcomingEvents = (LinkedHashMap<Integer,Event>) session.getAttribute("up_Evts");
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
                    <a class="navbar-brand brand-logo" href="grpBaseCont">
                        <img src="assets/images/logo/grad/Black_Grad_Horizontal3_2.png" alt="logo" /> </a>
                    <a class="navbar-brand brand-logo-mini" href="index.html">
                        <img src="assets/images/logo/grad/Black_Grad_Logo3_2.png" alt="logo" /> </a>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center">
                    <h2> My Groups </h2>
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
                <!-- partial -->
                <div class="main-panel">
                    <div class="content-wrapper">
                        <!-- School Starts Here-->
                        <% for (School s : usr_Schls) {%>
                        <div class="row page-title-header">
                            <div class="col-12">
                                <div class="page-header">
                                    <h4 class="page-title"><%=s.getSchlName()%></h4>
                                </div>
                            </div>

                        </div>
                        <!-- Page Title Header Ends-->
                        <% for (Group g : s.getGrps()) {%>
                        <div class="row">
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card card-clickable">
                                    <div class="card-body">
                                        <form action="grpBaseCont" method="post">
                                            <input type="hidden" name="grpAct" value ="cg">
                                            <input type="hidden" name="grp" value="<%=g.getGID()%>">
                                            <input type="submit" class="card-clickable-title card-title mb-0" value="<%=g.getGrpName()%>">
                                        </form>
                                        
                                        <div class="card-list d-flex flex-column flex-lg-row">
                                            <table>
                                                <tr>
                                                    <td><span class="card-li-title">Type:</span></td>
                                                    <td style="padding-right: 4em"><span><%=g.getGrpType()%></span></td>
                                                </tr>
                                                <tr>
                                                    <td><span class="card-li-title">Size:</span></td>
                                                    <td style="padding-right: 4em"><span><%=g.getGrpSize()%></span></td>
                                                </tr>
                                            </table>
<!--                                            <ul class="card-ul">
                                                <li>
                                                    <span class="card-li-title">Type:</span> Marching Band
                                                </li>
                                                <li>
                                                    <span class="card-li-title">Size:</span> AA
                                                </li>
                                            </ul>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                             <%ArrayList<obj.Registration> r = new ArrayList<obj.Registration>();%>
                             <%for(obj.Registration r2 : g.getRegs()) {%>
                             <%if(r2.getSelDteTm().isAfter(LocalDateTime.now())) {%>
                             <% r.add(r2); } }%>
                            <div class="col-md-6 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body d-flex flex-column">
                                        <div class="wrapper">
                                            <h4 class="card-title mb-0">Upcoming Events</h4>
                                            <div class="card-list d-flex flex-column flex-lg-row">
                                                <table>
                                                    <tr>
                                                        <td><%if(r.size() == 0){%>
                                                            No Upcoming Events
                                                            <%} else {%>
                                                            <%=r.get(0).getSelDteTm().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))%> - <%=upcomingEvents.get(r.get(0).getRID()).getName()%>
                                                            <%}%></td>
                                                    </tr>
                                                    <tr>
                                                        <td><%if(r.size() < 2){%>
                                                            No Upcoming Events
                                                            <%} else {%>
                                                            <%=r.get(1).getSelDteTm()%> - <%=upcomingEvents.get(r.get(1).getRID()).getName()%>
                                                            <%}%></td>
                                                    </tr>
                                                </table>
<!--                                            <ul class="card-ul">
                                                <li>
                                                    Event 1
                                                </li>
                                                <li>
                                                    Event 2
                                                </li>
                                            </ul>-->
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <% }%>
                        <% }%>
                        <div class="row">
                            <div class="col-md-12 grid-margin">
                                <div class="card card-clickable">
                                    <div class="card-body">
                                        
                                        <form action="grpPages" method="post">
                                            <input type="hidden" name="grp" value="000">
                                            <input type="submit" class="card-clickable-title card-title mb-0" value="Add New Group">
                                        </form>
                                        
                                      
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
