<%@page import="sun.reflect.ReflectionFactory.GetReflectionFactoryAction"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="core.aplicacao.Resultado, dominio.*, java.util.*"%>
<%@page import="core.util.ConverteDate"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE USUARIO::::</title>
</head>
<body>
	<%
	//objetos usuais
	Resultado resultado = (Resultado) session.getAttribute("resultado");					//usado na mensagem
	Resultado resultadoconsultar = (Resultado) session.getAttribute("resultadoconsultar");	//usado na lista
	Usuario usuario = (Usuario) request.getAttribute("usuario");
	Usuario usuariologin = (Usuario) session.getAttribute("usuariologin");
	
	//objetos para preencher select
	
	Resultado resultadoGrupo = (Resultado)getServletContext().getAttribute("resultadoGrupo");
	
	
	//Listas de objetos para preencher o select
	
	List<EntidadeDominio> grupos = resultadoGrupo.getEntidades();
	%>
	
	<form action="GerenciarUsuario" method="post">
	
	<div class="container-fluid">
			<br>
			<br>
			<br>
			<h1 class="text-center">Usuários</h1>
			<br>
			<br>
<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-2">
				<label for="txtNome" class="control-label">ID:</label> 
				<input type="text" id="txtID" name="txtID" class="form-control"
				value="<%if(usuario != null)
					{
					out.print(usuario.getId());
					}%>"/> 
				</br>			
			</div>
			<div class="col-sm-3">
				<label for="txtNome" class="control-label">Nome:</label> 
				<input type="text" id="txtNome" name="txtNome" class="form-control"
				value="<%if(usuario != null){
					out.print(usuario.getNome());
				}%>"/>    
				</br>			
			</div>
			<div class="col-sm-3">
				<label for="txtNome" class="control-label">Sobrenome:</label> 
				<input type="text" id="txtSobrenome" name="txtSobrenome" class="form-control"
				value="<%if(usuario != null)
				{
					out.print(usuario.getSobrenome());
				}%>"/> 
				</br>			
			</div>			
			<div class="col-sm-2"></div>			
		</div>
<!-- row -->

<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-3">
				<label for="txtNome" class="control-label">Genero:</label> 
				<input type="text" id="txtGenero" name="txtGenero" class="form-control"
				value="<%if(usuario != null)
				{
					out.print(usuario.getGenero());
				}%>"/> 
				</br>
			</div>
			<div class="col-sm-2">
				<label for="txtNome" class="control-label">Data Nascimento:</label> 
				<input type="text" id="txtDtNascimento" name="txtDtNascimento" class="form-control"
				value="<%if(usuario != null)
				{
					out.print(ConverteDate.converteDateString(usuario.getDtnascimento()));
				}%>"/> 
				</br>			
			</div>
			<div class="col-sm-3">
				<!-- Lista de grupo ----------------------------------------------->
				<input type="hidden" id="txtIDGrupo" name="txtIDGrupo" 					
				value="<%if(usuario != null && usuario.getGrupo() != null)
				{
					out.print(usuario.getGrupo().getId());
				}%>" />
				
				<label for="txtGrupo" class="control-label">Grupos</label> 	
				 <select id="txtGrupo" name="txtGrupo" class="form-control"
				 	<%if(usuario != null)
				 	{
						if(!usuariologin.getGrupo().getId().equals(usuario.getGrupo().getId()))
						{
							%> disabled="disabled" <% 
						}}%>
				 >
				 <%if(grupos != null)	//teste de nulidade da lista
					{
					 //TESTES PARA PRINTAR GRUPO DO USUARIO VISUALIZADO NO TOPO DA LISTA E OS DEMAIS EM BAIXO
				 	if(usuario != null)										//teste se objeto do usuario não tem grupo vazio
					 	{																//se não tem
				 		%>
				 		<option value="<% out.print(usuario.getGrupo().getId()); 		//printa grupo do usuario primeiro%>">	
						<% out.print(usuario.getGrupo().getNomeGrupo()); %>
						</option>
							<%for(EntidadeDominio e : grupos)							//fazendo a lista
							{
								Grupo g = (Grupo)e;
								if(!g.isDesativado())
								{
								if(usuario.getGrupo().getId() != g.getId())				//se o grupo do objeto de usuario é diferente do grupo da lista geral de grupo
								{	//é diferente, printa o resto do grupo
								%>
								<option value="<% out.print(g.getId()); %>">
								<% out.print(g.getNomeGrupo()); %>
								</option>
						  		<%}}
							} 
						}
				 	//SE A LISTA NÃO É NULA ELE PRINTA A LISTA INTEIRA
				 	else
				 	{
				 		%><option disabled selected value> -- selecione uma opção -- </option><%
						for(EntidadeDominio e : grupos)							//fazendo a lista
						{
							Grupo g = (Grupo)e;
							if(!g.isDesativado())
							{
							%>
							<option value="<% out.print(g.getId()); %>">
							<% out.print(g.getNomeGrupo()); %>
							</option>
					  		<%}
						}
				 	}}%>
				 </select>		
			</div>			
			<div class="col-sm-2"></div>
		</div>
<!-- row -->
<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-4">
				<label for="txtNome" class="control-label">Email:</label> 
				<input type="text" id="txtEmail" name="txtEmail" class="form-control"
				value="<%if(usuario != null)
				{
					out.print(usuario.getEmail());
				}%>"/> 
				</br>			
			</div>
			<div class="col-sm-4">
				<label for="txtNome" class="control-label">Senha:</label> 
				<input type="password" id="txtSenha" name="txtSenha" class="form-control"
				<%
				if(usuario != null)
					if(!usuariologin.getSenha().equals(usuario.getSenha()))%>readonly=true				
				value="<%if(usuario != null)
				{
					out.print(usuario.getSenha());
				}%>"/>
				</br>			
			</div>
			<div class="col-sm-2"></div>
		</div>
<!-- row -->
<!-- row -->
		<div class="row">
			<div class="col-sm-5"></div>
			<hr />
			<div class="col-sm-2"></div>	
			<div class="col-sm-5"></div>	
		</div>
<!-- row -->	
<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			
			<div class="col-sm-3">
				<label for="txtNome" class="control-label">CEP:</label> 
				<input type="text" id="txtCEP" name="txtCEP" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getCep());
				}%>"/>
				</br>
			</div>
			
			<div class="col-sm-2">
				<label for="txtNome" class="control-label">Estado:</label> 
				<input type="text" id="txtEstado" name="txtEstado" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getEstado());
				}%>"/> 
				</br>			
			</div>
			
			<div class="col-sm-3">
				<label for="txtNome" class="control-label">Cidade:</label> 
				<input type="text" id="txtCidade" name="txtCidade" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getCidade());
				}%>"/> 
				</br>			
			</div>
			<div class="col-sm-2"></div>
		</div>
<!-- row -->
<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-4">
				<input type="hidden" id="txtIDRua" name="txtIDRua" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getId());
				}%>" />
				<label for="txtNome" class="control-label">Rua:</label> 
				<input type="text" id="txtRua" name="txtRua" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getRua());
				}%>"/>
				</br> 		
			</div>
			<div class="col-sm-2">
				<label for="txtNome" class="control-label">Bairro:</label> 
				<input type="text" id="txtBairro" name="txtBairro" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getBairro());
				}%>"/> 
				</br>			
			</div>
			<div class="col-sm-2">
				<label for="txtNome" class="control-label">Numero:</label> 
				<input type="text" id="txtNumero" name="txtNumero" class="form-control"
				value="<%if(usuario != null && usuario.getEndereco() != null)
				{
					out.print(usuario.getEndereco().getNumero());
				}%>"/>
				</br> 			
			</div>			
			<div class="col-sm-2"></div>					
		</div>			
<!-- row -->			
<!-- row -->
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<label for="txtNome" class="control-label">Complemento:</label> 
			<input type="text" id="txtComplemento" name="txtComplemento" class="form-control"
			value="<%if(usuario != null && usuario.getEndereco() != null)
			{
				out.print(usuario.getEndereco().getComplemento());
			}%>"/>
			</br>		
		</div>
		<div class="col-sm-2"></div>
	</div>
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
			</br></div>	
			<div class="col-sm-5"></div>	
		</div>
<!-- row -->
<!-- row -->
		<div class="row">
			<div class="col-sm-5"></div>
			<div class="col-sm-2"></div>	
			<div class="col-sm-5"></div>	
		</div>
<!-- row -->	
<!-- row -->
		<div class="row">
			<div class="col-sm-4"></div>
					<div class="col-sm-1">
						<input type="submit" id="operacao" name="operacao" value="SALVAR" class="form-control btn-primary"/> 			
					</div>	
					<div class="col-sm-1">
						<input type="submit" id="operacao" name="operacao" value="ALTERAR" class="form-control btn-primary" />			
					</div>
					<div class="col-sm-1">
						<input type="submit" id="operacao" name="operacao" value="EXCLUIR" class="form-control btn-primary"
						<%
						if(usuario != null)
							if(usuariologin.getId().equals(usuario.getId()))
								out.print(" disabled");%>/>			
					</div>
					<div class="col-sm-1">
						<input type="submit" id="operacao" name="operacao" value="CONSULTAR" class="form-control btn-primary"/> 			
					</div>
			<div class="col-sm-4"></div>	
		</div>
<!-- row -->				
</div>
	</br>
	<hr />
	</br>
	</div>
	<a href=GerenciarUsuario?operacao=CONSULTAR>Consultar Usuários</a>
	<table class="table">
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>Sobrenome</th>
			<th>Data Nascimento</th>
			<th>Email</th>
			<th>Grupo</th>
			<th></th>
		</tr>
		<%
		if(resultadoconsultar != null && resultadoconsultar.getEntidades() != null)	//teste de nulidade da lista
		{
			for(EntidadeDominio e : resultadoconsultar.getEntidades())				//fazendo a lista
			{
				Usuario u = (Usuario) e;
				if(!u.isDesativado())
				{
				%>
		<tr>
			<td>
				<% out.print(u.getId()); %>
			</td>
			<td>
				<% out.print(u.getNome()); %>
			</td>
			<td>
				<% out.print(u.getSobrenome()); %>
			</td>
			<td>
				<% out.print(u.getDtnascimento()); %>
			</td>
			<td>
				<% out.print(u.getEmail()); %>
			</td>
			<td>
				<% out.print(u.getGrupo().getNomeGrupo()); %>
			</td>
			<td>
				<a class="btn btn-primary btn-xs" 
				href="<% out.print("GerenciarUsuario?txtID=" + u.getId() + "&operacao=VISUALIZAR");%>">
					<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>
					Visualizar
				</a>
			</td>
		</tr>
		<%}}} %>
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