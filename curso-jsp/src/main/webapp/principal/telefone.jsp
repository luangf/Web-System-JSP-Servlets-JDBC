<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"/>

<body>
  <!-- Pre-loader start -->
  <jsp:include page="theme-loader.jsp"/>
  
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"/>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
              
				<jsp:include page="navbarmainmenu.jsp"/>
                  
                  <div class="pcoded-content">
                  
                      <!-- Page-header start -->
                      <jsp:include page="page-header.jsp"/>
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cad. Telefone</h4>
														<form id="formFone" class="form-material" action="<%= request.getContextPath() %>/ServletTelefone" method="post">
														
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" readonly="readonly" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
														
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="numero" id="numero" class="form-control" required="required">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Número:</label>
                                                            </div>
														
															<button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
                                                            <button class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-danger waves-effect waves-light">Excluir</button>
														</form>
													</div>
												</div>
											</div>
											</div>
											
											<span id="msg">${msg}</span>

											<div style="height: 300px; overflow: scroll;">
												<table class="table" id="tabelaResultadosView">
													<thead>
														<tr>
															<th scope="col">ID</th>
															<th scope="col">Número</th>
															<th scope="col">Excluir</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items='${modelTelefones}' var='f'>
															<tr>
																<td><c:out value="${f.id}"></c:out></td>
																<td><c:out value="${f.numero}"></c:out></td>
																<td><a href="<%=request.getContextPath()%>/ServletTelefone?acao=excluir&id=${f.id}&userpai=${modelLogin.id}" class="btn btn-danger waves-effect waves-light">Excluir</a></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
											
										</div>
									</div>
                                    <!-- Page-body end -->
                                </div>423432
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   
   	<jsp:include page="javascriptfile.jsp"/>

	<script type="text/javascript">
		$("#numero").keypress(function(event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
	</script>
</body>

</html>