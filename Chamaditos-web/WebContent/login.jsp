<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="core.aplicacao.Resultado, dominio.*, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<% Resultado resultado = (Resultado) session.getAttribute("resultadologin");						//usado na mensagem%>
		<form action="GerenciarLogin" method="post">
			<div class="container-fluid">
			</br>
			</br>
			</br>
			</br>
			</br>
			</br>
			</br>
			</br>
			</br>
			</br>
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2"><hr /></div>
					<div class="col-sm-5"></div>
				</div>
			<!-- row -->
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2">
						<h3 class="text-center">Chamaditos</h3>
					</div>
					<div class="col-sm-5"></div>
				</div>
				</br>
			<!-- row -->
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2">
						<input type="text" id="txtLogin" name="txtLogin" placeholder="Email:" class="form-control"/>
					</div>
					<div class="col-sm-5"></div>
				</div>
				</br>
				</br>
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2">
						<input type="password" id="txtSenha" name="txtSenha" placeholder="Senha:" class="form-control"/>			
					</div>
					<div class="col-sm-5"></div>
				</div>
				</br>
				</br>
			<!-- row -->
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2">
					 <!--	<a href="index.jsp"><input type="button" id="operacao" name="operacao" value="Login" class="btn btn-primary btn-block btn-lg"/></a> -->
					 <button type="submit" id="operacao" name="operacao" value="CONSULTAR" class="btn btn-primary btn-block btn-lg">Login</button>
						 
						 
					</div>
					<div class="col-sm-5"></div>
				</div>
				</br>
				</br>
			<!-- row -->
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2">

							<%if(resultado !=null && resultado.getMsg() != null)
							{
								out.print(resultado.getMsg());
								session.setAttribute("resultadologin", null);
							}
							%>					
					
					
					</div>
					<div class="col-sm-5"></div>
				</div>
			<!-- row -->
				<div class="row">
					<div class="col-sm-5"></div>
					<div class="col-sm-2"><hr /></div>
					<div class="col-sm-5"></div>
				</div>
			<!-- row -->
			</div>
		</form>
</body>
</html>