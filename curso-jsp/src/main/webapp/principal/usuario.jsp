<%@page import="model.ModelLogin"%>
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
                                                        <h4 class="sub-title">Cad. Usuário</h4>
											
											<form id="formUser" enctype="multipart/form-data" class="form-material" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post">
                                                            <input type="hidden" name="acao" id="acao" value="">
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly"
                                                                	value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default input-group mb-4">
                                                            	<div class="input-group-prepend">
                                                            		<c:if test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}">
                                                            			<a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
                                                            				<img id="fotoEmBase64" alt="Imagem User" src="${modelLogin.fotouser}" width="70px">
                                                            			</a>
                                                            		</c:if>
                                                            		<c:if test="${modelLogin.fotouser == '' || modelLogin.fotouser == null}">
                                                            			<img id="fotoEmBase64" alt="Imagem User" src="assets/images/user.png" width="70px">
                                                            		</c:if>
                                                            	</div>
                                                            	<input id="fileFoto" name="fileFoto" type="file" accept="image/*" onchange="visualizarImg('fotoEmBase64', 'fileFoto');" class="form-control-file" style="margin-top: 15px; margin-left: 5px;">
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required"
                                                                	value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="dataNascimento" id="dataNascimento" class="form-control" required="required"
                                                                	value="${modelLogin.dataNascimento}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data Nascimento:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="rendaMensal" id="rendaMensal" class="form-control" required="required"
                                                                	autocomplete="off" value="${modelLogin.rendaMensal}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Renda Mensal:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" required="required"
                                                                	autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
	                                                            <select class="form-select form-control" aria-label="Default select example" name="perfil">
																  <option disabled="disabled">[Selecione o Perfil]</option>
																  <option value="ADMIN" <% 
																  
																  ModelLogin modelLogin=(ModelLogin) request.getAttribute("modelLogin");
																  
																  if(modelLogin !=null && modelLogin.getPerfil().equals("ADMIN")){
																	  out.print(" ");
																	  out.print("selected=\"selected\"");
																	  out.print(" ");
																  } %>>Admin</option>
																  <option value="GERENTE" <%
																  
																  modelLogin=(ModelLogin) request.getAttribute("modelLogin");
																  
																  if(modelLogin !=null && modelLogin.getPerfil().equals("GERENTE")){
																	  out.print(" ");
																	  out.print("selected=\"selected\"");
																	  out.print(" ");
																  } %>>Gerente</option>
																  <option value="AUXILIAR" <% 
																  
																  modelLogin=(ModelLogin) request.getAttribute("modelLogin");
																  
																  if(modelLogin !=null && modelLogin.getPerfil().equals("AUXILIAR")){
																	  out.print(" ");
																	  out.print("selected=\"selected\"");
																	  out.print(" ");
																  } %>>Auxiliar</option>
																</select>
																<span class="form-bar"></span>
                                                                <label class="float-label">Perfil:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
																<input onblur="pesquisaCep();" type="text" name="cep" id="cep" class="form-control" required="required" autocomplete="off" value="${modelLogin.cep}">
																<span class="form-bar"></span>
																<label class="float-label">Cep:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name=logradouro id="logradouro" class="form-control" required="required" autocomplete="off" value="${modelLogin.logradouro}">
																<span class="form-bar"></span>
																<label class="float-label">Logradouro:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="complemento" id="complemento" class="form-control" required="required" autocomplete="off" value="${modelLogin.complemento}">
																<span class="form-bar"></span>
																<label class="float-label">Complemento:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro" class="form-control" required="required" autocomplete="off" value="${modelLogin.bairro}">
																<span class="form-bar"></span>
																<label class="float-label">Bairro:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade" class="form-control" required="required" autocomplete="off" value="${modelLogin.localidade}">
																<span class="form-bar"></span>
																<label class="float-label">Localidade:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf" class="form-control" required="required" autocomplete="off" value="${modelLogin.uf}">
																<span class="form-bar"></span>
																<label class="float-label">Estado:</label>
															</div>

                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                            	<input type="radio" name="sexo" checked="checked" value="MASCULINO" <% 
																  
																  modelLogin=(ModelLogin) request.getAttribute("modelLogin");
																  
																  if(modelLogin !=null && modelLogin.getSexo().equals("MASCULINO")){
																	  out.print(" ");
																	  out.print("checked=\"checked\"");
																	  out.print(" ");
																  } %>>Masculino</>
                                                            	<input type="radio" name="sexo" value="FEMININO" <% 
																  
																  modelLogin=(ModelLogin) request.getAttribute("modelLogin");
																  
																  if(modelLogin !=null && modelLogin.getSexo().equals("FEMININO")){
																	  out.print(" ");
																	  out.print("checked=\"checked\"");
																	  out.print(" ");
																  } %>>Feminino</>
                                                            </div>
                                                            
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
                                                            <button class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDeleteComAjax();">Excluir</button>
															<c:if test="${modelLogin.id > 0}">
																<a href="<%= request.getContextPath() %>/ServletTelefone?iduser=${modelLogin.id}" class="btn btn-primary waves-effect waves-light">Telefone</a>
                                                        	</c:if>
                                                        	<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
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
												      <th scope="col">Nome</th>
												      <th scope="col">Ver</th>
												    </tr>
												  </thead>
												  <tbody>
												    <c:forEach items="${modelLogins}" var="ml">
												    	<tr>
												    		<td><c:out value="${ml.id}"></c:out></td>
												    		<td><c:out value="${ml.nome}"></c:out></td>
												    		<td><a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}" class="btn btn-success">Ver</a></td>
												    	</tr>
												    </c:forEach>
												  </tbody>
												</table>
											</div>

											<nav aria-label="Page navigation example">
												<ul class="pagination">
													<%
														int totalPaginas=(int) request.getAttribute("totalPaginas");
													
														for(int i = 0; i < totalPaginas; i++){
															String url=request.getContextPath()+"/ServletUsuarioController?acao=paginar&pagina="+(i*5);
															out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\">"+(i+1)+"</a></li>");
														}
													%>
												</ul>
											</nav>

									</div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de Usuário</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      
	      	<div class="input-group mb-3">
			  <input id="nomeBusca" type="text" class="form-control" placeholder="Nome" aria-label="nome" aria-describedby="basic-addon2">
			  <div class="input-group-append">
			    <button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
			  </div>
			</div>
			
			<div style="height: 300px; overflow: scroll;">
				<table class="table" id="tabelaResultados">
				  <thead>
				    <tr>
				      <th scope="col">ID</th>
				      <th scope="col">Nome</th>
				      <th scope="col">Ver</th>
				    </tr>
				  </thead>
				  <tbody>
				    
				  </tbody>
				</table>
			</div>

					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">

						</ul>
					</nav>

					<span id="totalResultados"></span>
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	  </div>
	</div>
   
   
   
   	<jsp:include page="javascriptfile.jsp"/>
   	
   	<script type="text/javascript">
   		$("#rendaMensal").maskMoney({showSymbol:true, symbol:"R$ ", decimal:",", thousands:"."});
   	
   		const formatter=new Intl.NumberFormat('pt-BR',{
			currency: 'BRL',
			minimumFractionDigits: 2
		});
		
		$("#rendaMensal").val(formatter.format($("#rendaMensal").val()));
		$("#rendaMensal").focus();
		
		var dataNascimento=$("#dataNascimento").val();
		
		if(dataNascimento != null && dataNascimento != ''){
			var dateFormat=new Date(dataNascimento);
			$("#dataNascimento").val(dateFormat.toLocaleDateString('pt-BR',{timeZone:'UTC'}));
		}
		
		$("#nome").focus();
   		
		 $( function() {
		  	 $("#dataNascimento").datepicker({
		  		    dateFormat: 'dd/mm/yy',
		  		    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
		  		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		  		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
		  		    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		  		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		  		    nextText: 'Próximo',
		  		    prevText: 'Anterior'
		  	});
		 } );
   	
	   	$("#complemento").keypress(function(event){
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
	   	$("#cep").keypress(function(event){
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
   	
	   	function pesquisaCep(){
			var cep=$("#cep").val();
			$.getJSON("https://viacep.com.br/ws/"+cep+"/json/?callback=?", function(dados) {
				if (!("erro" in dados)) {
					$("#cep").val(dados.cep);
					$("#logradouro").val(dados.logradouro);
					$("#complemento").val(dados.complemento);
		            $("#bairro").val(dados.bairro);
		            $("#localidade").val(dados.localidade);
		            $("#uf").val(dados.uf);
				}
			});
		}
   	
   		function visualizarImg(fotoEmBase64, fileFoto){
   			var preview=document.getElementById(fotoEmBase64); //fotoEmBase64 -> campo img do HTML
   			var fileUser=document.getElementById(fileFoto).files[0]; //pode ser q retorne vários, ai pega o primeiro
   			var reader=new FileReader();
   			
   			reader.onloadend=function(){
   				preview.src=reader.result; //carrega a foto na tela
   			};
   			
   			if(fileUser){
   				reader.readAsDataURL(fileUser); //preview da imagem
   			}else{
   				preview.src='';
   			}
   		}
   	
   		function verEditar(id){
   			var urlAction=document.getElementById('formUser').action;
   			
   			window.location.href=urlAction+'?acao=buscarEditar&id='+id;
   		}
   	
   		function buscaUserPagAjax(url){
   			
   			var urlAction=document.getElementById('formUser').action;
   			var nomeBusca = document.getElementById('nomeBusca').value;
   			
   			$.ajax({
				method: 'get',
				url: urlAction,
				data: url,
				success: function(response, textStatus, xhr){
					var json=JSON.parse(response);
					
					$('#tabelaResultados > tbody > tr').remove();
					$('#ulPaginacaoUserAjax > li').remove();
					
					for(var i = 0; i < json.length; i++){
						$('#tabelaResultados > tbody').append('<tr> <td>'+json[i].id+'</td> <td>'+json[i].nome+'</td> <td><button type="button" class="btn btn-info" onclick="verEditar('+json[i].id+');">Ver</button></td> </tr>');
					}
					
					document.getElementById('totalResultados').textContent='Resultados: '+json.length;
					
					var totalPaginas=xhr.getResponseHeader("totalPaginas");
					
					for(var i=0; i < totalPaginas; i++){
						var url='nomeBusca='+nomeBusca+'&acao=buscarUserAjaxPage&pagina='+(i*5);
						$('#ulPaginacaoUserAjax').append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+(i+1)+'</a></li>');
					}
					
				}
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao buscar usuário por nome: '+ xhr.responseText);
			});
   			
   		}
   		
   		function buscarUsuario(){
   			var nomeBusca=document.getElementById('nomeBusca').value;
   			
   			if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){
   				
   				var urlAction=document.getElementById('formUser').action;
   				
   				$.ajax({
					method: 'get',
					url: urlAction,
					data: 'nomeBusca='+nomeBusca+'&acao=buscarUserAjax',
					success: function(response, textStatus, xhr){
						var json=JSON.parse(response);
						
						$('#tabelaResultados > tbody > tr').remove();
						$('#ulPaginacaoUserAjax > li').remove();
						
						for(var i = 0; i < json.length; i++){
							$('#tabelaResultados > tbody').append('<tr> <td>'+json[i].id+'</td> <td>'+json[i].nome+'</td> <td><button type="button" class="btn btn-info" onclick="verEditar('+json[i].id+');">Ver</button></td> </tr>');
						}
						
						document.getElementById('totalResultados').textContent='Resultados: '+json.length;
						
						//Total de Páginas
						var totalPaginas=xhr.getResponseHeader("totalPaginas");
						
						for(var i=0; i < totalPaginas; i++){
							var url='nomeBusca='+nomeBusca+'&acao=buscarUserAjaxPage&pagina='+(i*5);
							$('#ulPaginacaoUserAjax').append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+(i+1)+'</a></li>');
						}
						
					}
				}).fail(function(xhr, status, errorThrown){
					alert('Erro ao buscar usuário por nome: '+ xhr.responseText);
				});
   				
   			}
   		}
   	
   		function criarDeleteComAjax(){
			if(confirm('Deseja realmente excluir os dados?')){
				var urlAction=document.getElementById('formUser').action;
				var idUser=document.getElementById('id').value;
				
				$.ajax({
					method: 'get',
					url: urlAction,
					data: 'id='+idUser+'&acao=deletarajax',
					success: function(response){
						limparForm();
						document.getElementById('msg').textContent=response;
					}
				}).fail(function(xhr, status, errorThrown){
					alert('Erro ao deletar usuário por id: '+ xhr.responseText);
				});
			}
		}
   	
		function criarDelete(){
   			if(confirm('Deseja realmente excluir os dados?')){
				document.getElementById('formUser').method='get';
	   			document.getElementById('acao').value='deletar';
	   			document.getElementById('formUser').submit();
   			}
   		}
   	
   		function limparForm(){
   			var elementos=document.getElementById('formUser').elements; //retorna os elementos HTML dentro do form (array)
   			for(i=0; i<elementos.length; i++){
   				elementos[i].value='';
   			}
   		}
   	</script>
</body>

</html>