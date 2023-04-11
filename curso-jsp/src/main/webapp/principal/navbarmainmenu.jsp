<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>

                  <nav class="pcoded-navbar">
                      <div class="sidebar_toggle"><a href="#"><i class="icon-close icons"></i></a></div>
                      <div class="pcoded-inner-navbar main-menu">
                          <div class="">
                              <div class="main-menu-header">
                              	  <c:if test="${imagemUser != '' && imagemUser != null}">
                                  	<img src="${imagemUser}" class="img-radius">
                                  </c:if>
                                  <c:if test="${imagemUser == '' || imagemUser == null}">
                                  	<img src="<%= request.getContextPath() %>/assets/images/user.png" class="img-radius">
                                  </c:if>
                                  <div class="user-details">
                                      <span id="more-details"><%= session.getAttribute("usuario") %><i class="fa fa-caret-down"></i></span>
                                  </div>
                              </div>
        
                              <div class="main-menu-content">
                                  <ul>
                                      <li class="more-details">
                                          <a href="<%= request.getContextPath() %>/ServletLoginController?acao=logout"><i class="ti-layout-sidebar-left"></i>Logout</a>
                                      </li>
                                  </ul>
                              </div>
                          </div>

                          <div class="pcoded-navigation-label" data-i18n="nav.category.navigation">Opções</div>
                          <ul class="pcoded-item pcoded-left-item">
                              <li class="active">
                                  <a href="<%= request.getContextPath() %>/principal/principal.jsp" class="waves-effect waves-dark">
                                      <span class="pcoded-micon"><i class="ti-home"></i><b>D</b></span>
                                      <span class="pcoded-mtext" data-i18n="nav.dash.main">Home</span>
                                      <span class="pcoded-mcaret"></span>
                                  </a>
                              </li>
                              <li class="pcoded-hasmenu">
                                  <a href="javascript:void(0)" class="waves-effect waves-dark">
                                      <span class="pcoded-micon"><i class="ti-layout-grid2-alt"></i></span>
                                      <span class="pcoded-mtext"  data-i18n="nav.basic-components.main">Cadastro</span>
                                      <span class="pcoded-mcaret"></span>
                                  </a>
                                  <ul class="pcoded-submenu">
                                  	<c:if test="${perfil == 'ADMIN'}">
                                      <li class=" ">
                                          <a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=listarUsers" class="waves-effect waves-dark">
                                              <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
                                              <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Usuário</span>
                                              <span class="pcoded-mcaret"></span>
                                          </a>
                                      </li>
                                    </c:if>
                                  </ul>
                              </li>
                          </ul>
                          
                          <div class="pcoded-navigation-label" data-i18n="nav.category.forms">Relatório</div>
                          <ul class="pcoded-item pcoded-left-item">
                              <li>
                                  <a href="<%= request.getContextPath() %>/principal/reluser.jsp" class="waves-effect waves-dark">
                                      <span class="pcoded-micon"><i class="ti-layers"></i><b>FC</b></span>
                                      <span class="pcoded-mtext" data-i18n="nav.form-components.main">Usuário</span>
                                      <span class="pcoded-mcaret"></span>
                                  </a>
                              </li>
                              <li>
                                  <a href="<%= request.getContextPath() %>/principal/relusergrafico.jsp" class="waves-effect waves-dark">
                                      <span class="pcoded-micon"><i class="ti-layers"></i><b>FC</b></span>
                                      <span class="pcoded-mtext" data-i18n="nav.form-components.main">Gráfico Salário</span>
                                      <span class="pcoded-mcaret"></span>
                                  </a>
                              </li>
                          </ul>
                          
                      </div>
                  </nav>