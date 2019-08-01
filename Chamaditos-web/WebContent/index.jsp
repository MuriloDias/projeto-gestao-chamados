<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="core.aplicacao.Resultado, dominio.*, java.util.*"%>
<%@page import="core.util.ConverteDate"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>Index Page</title>
</head>
<body>
	<div class="row container-fluid">	
		<form action="IndexPage">
		<%
		session.setAttribute("resultado", null);
		Usuario usuario = (Usuario) session.getAttribute("usuariologin");
		%>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<h1 class="text-center">Chamaditos</h1>
		<br>
		<br>
		<br>				
		<hr />
		<br>
		<br>
		<br>
			<div class="container-fluid">
<!-- row -->
				<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-2">
					<a href=GerenciarGrupo?operacao=CONSULTAR><input type="button" id="operacao" name="operacao" value="Grupos" class="btn btn-primary btn-block btn-lg"/></a>
				</div>
				<div class="col-sm-2">
					<a href=GerenciarUsuario?operacao=CONSULTAR><input type="button" id="operacao" name="operacao" value="Usuarios" class="btn btn-primary btn-block btn-lg"/></a>
				</div>
				<div class="col-sm-2">
					<a href=GerenciarChamado?operacao=CONSULTAR><input type="button" id="operacao" name="operacao" value="Chamados" class="btn btn-primary btn-block btn-lg"/></a>
				</div>
				<div class="col-sm-2">
					<a href="FormAnalise2.jsp" class="btn btn-primary btn-block btn-lg">Análise</a>
				</div>
				<div class="col-sm-2"></div>
				</div>
<!-- row -->
			</div>
<nav class="navbar navbar-default navbar-fixed-top ">
  <div class="container">
<!-- row -->
	<div class="row">
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2 text-right text-capitalize navbar-brand">
			<strong>
			<% out.print(usuario.getNome() + " " + usuario.getSobrenome()); %>
			</strong>
		</div>	
		<div class="col-sm-2 text-right text-capitalize navbar-brand">
			<a href="login.jsp" >Sair</a>
		</div>
	</div>
	
<!-- row -->
  </div>
</nav>
		</form>
	</div>
</body>
</html>