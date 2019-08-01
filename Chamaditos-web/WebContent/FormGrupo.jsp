<%@page import="core.aplicacao.Resultado, dominio.*, java.util.*"%>
<%@page import="core.util.ConverteDate"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE GRUPO::::</title>
</head>
<body>

	<%
	Resultado resultado = (Resultado) session.getAttribute("resultado");						//usado na mensagem
	Resultado resultadoconsultar = (Resultado) session.getAttribute("resultadoconsultar");		//usado na lista
	
	%>

	<form action="GerenciarGrupo" method="post">
<!-- row -->
	<div class="container-fluid">
			<br>
			<br>
			<br>
			<h1 class="text-center">Grupos</h1>
			<br>
			<br>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-2">
				<label for="txtID" class="control-label">ID:</label>
				<input type="text" id="txtID" name="txtID" placeholder="Default" class="form-control"/>
			</div>
			<div class="col-sm-6">
				<label for="txtNome" class="control-label">Nome:</label>
				<input type="text" id="txtNome" name="txtNome" class="form-control"/>	
			</div>
			<div class="col-sm-2"></div>
		</div>
		<br>
<!-- row -->
		<div class="row">
			<div class="col-sm-5"></div>
			<div class="col-sm-2">	
				<%if(resultado !=null && resultado.getMsg() != null)
				{
					out.print(resultado.getMsg());
					session.setAttribute("resultado", null);
				}
				%>
			</div>	
			<div class="col-sm-5"></div>	
		</div>
<!-- row -->			
	
<!-- row -->
</br>
</br>
<!-- row -->
		<div class="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-1">
				<input type="submit" id="operacao" name="operacao" value="SALVAR" class="form-control btn-primary"/> 
			</div>
			<div class="col-sm-1">
				<input type="submit" id="operacao" name="operacao" value="ALTERAR" class="form-control btn-primary"/>
			</div>
			<div class="col-sm-1">
				<input type="submit" id="operacao" name="operacao" value="EXCLUIR" class="form-control btn-primary"/>
			</div>
			<div class="col-sm-1">
				<input type="submit" id="operacao" name="operacao" value="CONSULTAR" class="form-control btn-primary"/>
			</div>	
			<div class="col-sm-4"></div>	
		</div>
		<hr />
<!-- row -->		
		

	</div>


		</br> 
		<a href=GerenciarGrupo?operacao=CONSULTAR>Consultar Grupos</a> 
		<table class="table table-striped">
			<tr>
				<th>ID</th>
				<th>Nome</th>
			</tr>
			<%
			if(resultadoconsultar != null && resultadoconsultar.getEntidades() != null)	//teste de nulidade da lista
			{
				for(EntidadeDominio e : resultadoconsultar.getEntidades())				//fazendo a lista
				{
					Grupo g = (Grupo)e;
					if(!g.isDesativado())
					{%>
			<tr>
				<td>
					<% out.print(g.getId()); %>
				</td>
				<td>
					<% out.print(g.getNomeGrupo()); %>
				</td>
				
			</tr>
			<%}}} %>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>

<nav class="navbar navbar-default navbar-fixed-top ">
  <div class="container">
<!-- row -->
	<div class="row">
		<div class="col-sm-2">
		<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#navbar-collapse"
			aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">Home</a>
		</div>
	</div>
		</div>
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2">
		</div>
		<div class="col-sm-2">
		</div>	
		<div class="col-sm-2">
		</div>
	</div>
	
<!-- row -->
  </div>
</nav>
	</form>
</body>
</html>