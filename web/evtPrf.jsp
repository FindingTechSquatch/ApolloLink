<%-- 
    Document   : index
    Created on : Oct 2, 2019, 12:07:38 PM
    Author     : tyleryork
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.ArrayList, java.util.LinkedHashMap, obj.*"%>
<%
    ArrayList<String> allPerfTypes = (ArrayList<String>) session.getAttribute("allPerfTypes");
    ArrayList<String> er1 = (ArrayList) session.getAttribute("er1");
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
                    <h2> Performance Information </h2>
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
                                    <p class="mb-1 mt-3 font-weight-semibold">Allen Moreno</p>
                                    <p class="font-weight-light text-muted mb-0">allenmoreno@gmail.com</p>
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
                        <!-- Page Title Header Starts-->

                        <!-- Page Title Header Ends-->
                        <div class="alert" ${hd1}>
                            <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span>
                            <ul>
                                <%if(er1 != null && er1.size() > 0) {%>
                                <% for (String e : er1) {%>
                                <li>
                                    <%=e%>
                                </li>
                                <% }%>
                                <% }%>
                            </ul>
                        </div>
                        <form action="regBaseCont" method="post">
                            <input type="hidden" name="act" value="rp">
                            <div class="row">
                                <div class="col-md-6 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h5 class="card-title mb-0"> Performance Title </h5>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfTtl" maxlength="50">
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h5 class="card-title mb-0"> Performance Type </h5>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-6 grid-margin stretch-card">
                                                    <select class="input grpInfo" type="select" id="prfTyp" name="prfTyp">
                                                        <%for (String s : allPerfTypes) {%>
                                                        <option value="<%=s%>"><%=s%></option>
                                                        <%}%>
                                                    </select>
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h5 class="card-title mb-0"> Additional Staff </h5>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfStaff" maxlength="150">
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h5 class="card-title mb-0"> Buses </h5>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-2 grid-margin stretch-card">
                                                    <select class="input grpInfo" type="select" id="prfBus" name="prfBus">
                                                        <option value="0" selected>0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="10">10</option>
                                                    </select>
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h5 class="card-title mb-0"> Equipment Trucks </h5>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-2 grid-margin stretch-card">
                                                    <select class="input grpInfo" type="select" id="prfTrk" name="prfTrk">
                                                        <option value="0" selected>0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="10">10</option>
                                                    </select>
                                                </div>            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Song 1 </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfSng1" maxlength="30">
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Song 2 </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfSng2" maxlength="30">
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Song 3 </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfSng3" maxlength="30">
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Song 4 </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfSng4" maxlength="30">
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Song 5 </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <input class="input" type="text" name="prfSng5" maxlength="30">
                                                </div>            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Pre-Performance Announcement </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <textarea class="input" cols="100" maxlength="500" rows ="5" name="prfPre"></textarea>
                                                </div>            
                                            </div>
                                            <div class="row">
                                                <div class="d-flex flex-column flex-lg-row">
                                                    <h6 class="card-title mb-0"> Post-Performance Announcement </h6>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="card-list d-flex flex-column flex-lg-row col-md-12 grid-margin stretch-card">
                                                    <textarea class="input" cols="100" maxlength="500" rows ="5" name="prfPost"></textarea>
                                                </div>              
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>  
                            <div class="row">
                                <div class="col-md-12 grid-margin">
                                    <div class="card card-clickable">
                                        <div class="card-body">
                                            <input type="submit" class="card-clickable-title card-title mb-0" value="Review Registration">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
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
