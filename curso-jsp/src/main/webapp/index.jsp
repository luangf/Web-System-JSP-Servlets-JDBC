<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>JSP + Servlets + JDBC</title>
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
		  rel="stylesheet"
		  integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
		  crossorigin="anonymous">
		  
	<style type="text/css">
		form{
			position: absolute;
			top: 40%;
			left: 33%;
			right: 33%;
		}
		
		h5{
			position: absolute;
			top: 30%;
			left: 33%;
		}
		
		.msg{
			position: absolute;
			top: 10%;
			left: 33%;
			font-size: 15px;
			color: #664d03;
			background-color: #fff3cd;
			border-color: #ffecb5;
		}
	</style>
		 
</head>

<body>
	<h5>JSP + Servlets + JDBC</h5>

	<form action="<%= request.getContextPath() %>/ServletLoginController" method="post" class="row g-3 needs-validation" novalidate>
		<input type="hidden" value="<%= request.getParameter("url") %>" name="url">
		
		<div class="mb-3">
			<label for="login" class="form-label">Login:</label>
	   		<input type="text" class="form-control" name="login" id="login" required="required">
	   		<div class="valid-feedback">
	      		Ok
	    	</div>
	    	<div class="invalid-feedback">
		    	Por favor digite o login
		    </div>
		</div>
		
		<div class="mb-3">
			<label for="senha" class="form-label">Senha:</label>
	   		<input type="password" class="form-control" name="senha" id="senha" required="required">
	   		<div class="valid-feedback">
	      		Ok
	    	</div>
	    	<div class="invalid-feedback">
		    	Por favor digite a senha
		    </div>
		</div>
		
		<button type="submit" class="btn btn-primary">Acessar</button>
	</form>

	<h5 class="msg">${msg}</h5>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
			crossorigin="anonymous"></script>
			
	<script type="text/javascript">
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(() => {
		  'use strict'
	
		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  const forms = document.querySelectorAll('.needs-validation')
	
		  // Loop over them and prevent submission
		  Array.from(forms).forEach(form => {
		    form.addEventListener('submit', event => {
		      if (!form.checkValidity()) {
		        event.preventDefault()
		        event.stopPropagation()
		      }
	
		      form.classList.add('was-validated')
		    }, false)
		  })
		})()
	</script>
</body>
</html>