<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>
	<!-- Pre-loader end -->

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>
						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
												<h1 style="color:#4DC423;">Fluxos funcionalidades do sistema:</h1>
												<h3><span style="color:#4DC423;">Cadastro -> Usuário:</span> Cadastra novos Usuários no sistema. Vê os usuários existentes. Pesquisa por usuários. Exclui usuários do sistema.</h3>
												<h3><span style="color:#4DC423;">Relatório -> Usuário:</span> Exibe o id, o nome e os telefones dos usuários cadastrados no banco de dados. Pode filtrar por data dos usuários cadastrados. Pode imprimir PDF.</h3>
												<h3><span style="color:#4DC423;">Relatório -> Gráfico Salário:</span> Gráfico de média salarial por tipo de usuário do sistema, por exemplo, a média salarial entre admin, auxiliar e secretaria. Pode Filtrar por data dos usuários cadastrados.</h3>
										</div>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>
</body>

</html>