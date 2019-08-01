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
<title>:::: VISUALIZAÇÃO DE CHAMADO ::::</title>
</head>
<body>
	
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
			<a href=GerenciarChamado?operacao=CONSULTAR><input type="button" id="operacao" name="operacao" value="Chamados"/></a>
		</div>
		</div>
		<%
		//objetos usuais
			Resultado resultado = (Resultado) session.getAttribute("resultado");					//usado na mensagem
			Resultado resultadoconsultar = (Resultado) session.getAttribute("resultadoconsultar");	//usado na lista
			Chamado chamado = (Chamado) session.getAttribute("chamado");
			Usuario usuario = (Usuario) session.getAttribute("usuariologin");
			
			//objetos para preencher select
			
			Resultado resultadoGrupo = (Resultado)getServletContext().getAttribute("resultadoGrupo");
			Resultado resultadoTipoServico = (Resultado)getServletContext().getAttribute("resultadoTipoServico");
			
			//Listas de objetos para preencher o select
			
			List<EntidadeDominio> grupos = resultadoGrupo.getEntidades();
			List<EntidadeDominio> tiposdeservico = resultadoTipoServico.getEntidades();
		%>
		
		<form action="GerenciarChamado" method="post">
		<div class="container-fluid">
	<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-2">
			<label for="txtID" class="control-label">ID:</label> 
			<input type="text" id="txtID" name="txtID"  placeholder="Default" class="form-control" readonly="true"
			value="<%if(chamado != null)
			{
			out.print(chamado.getId());
			}%>"/>
			</div> 
			<div class="col-sm-2">
			<label for="txtDataCriação" class="control-label">Data Criação:</label> 
			<input type="text" id="txtDtCriacao" name="txtDtCriacao" class="form-control" readonly="true"
			value="<%if(chamado != null)
			{
				out.print(ConverteDate.converteDateStringHora(chamado.getDataCriacao()));
			}%>"/> 
			</div>
			<div class="col-sm-2">
			<label for="txtDataEncerramento" class="control-label">Data Encerramento:</label> 
			<input type="text" id="txtDataEncerramento" name="txtDataEncerramento" class="form-control" readonly="true"
			value="<%if(chamado != null)
			{
				if(chamado.getStatus().equals("Fechado")){
					out.print(ConverteDate.converteDateStringHora(chamado.getDataEncerramento()));
				}
				else{
					out.print("Chamado Não Fechado Ainda!");
				}
				
			}%>"/> 
			</div>
			<div class="col-sm-2">
				<label for="txtCbStatus" class="control-label">Status:</label> 
				<select id="txtCbStatus" name="txtCbStatus" class="form-control">
				<%
				if(chamado.getStatus().equals("Processando"))
				{%>
					 <option value="Processando"<%if(chamado != null && chamado.getStatus().equals("Processando")){out.print("selected=\"selected\"");}%>>Processando</option>
				    <option value="Pendente"<%if(chamado != null && chamado.getStatus().equals("Pendente")){out.print("selected=\"selected\"");}%>>Pendente</option>
				    <%if(usuario.getGrupo().getId() == chamado.getGrupoAtribuido().getId()){%><option value="Solucionado"<%if(chamado != null && chamado.getStatus().equals("Solucionado")){out.print("selected=\"selected\"");}%>>Solucionado</option><%}%>
					<%if(usuario.getGrupo().getId() == chamado.getGrupoAutor().getId()){%><option value="Fechado"<%if(chamado != null && chamado.getStatus().equals("Fechado")){out.print("selected=\"selected\"");}%>>Fechado</option><%}%>
					
				<%}%>
				<%
				if(chamado.getStatus().equals("Pendente"))
				{%>
					<option value="Pendente"<%if(chamado != null && chamado.getStatus().equals("Pendente")){out.print("selected=\"selected\"");}%>>Pendente</option>
					 <option value="Processando"<%if(chamado != null && chamado.getStatus().equals("Processando")){out.print("selected=\"selected\"");}%>>Processando</option> 
				    <%if(usuario.getGrupo().getId() == chamado.getGrupoAtribuido().getId()){%><option value="Solucionado"<%if(chamado != null && chamado.getStatus().equals("Solucionado")){out.print("selected=\"selected\"");}%>>Solucionado</option><%}%>
					<%if(usuario.getGrupo().getId() == chamado.getGrupoAutor().getId()){%><option value="Fechado"<%if(chamado != null && chamado.getStatus().equals("Fechado")){out.print("selected=\"selected\"");}%>>Fechado</option><%}%>
					
				<%}%>
				<%
				if(chamado.getStatus().equals("Solucionado"))
				{%>
					<option value="Solucionado"<%if(chamado != null && chamado.getStatus().equals("Solucionado")){out.print("selected=\"selected\"");}%>>Solucionado</option>
					<option value="Processando"<%if(chamado != null && chamado.getStatus().equals("Processando")){out.print("selected=\"selected\"");}%>>Processando</option>
				    <option value="Pendente"<%if(chamado != null && chamado.getStatus().equals("Pendente")){out.print("selected=\"selected\"");}%>>Pendente</option>
					<%if(usuario.getGrupo().getId() == chamado.getGrupoAutor().getId()){%><option value="Fechado"<%if(chamado != null && chamado.getStatus().equals("Fechado")){out.print("selected=\"selected\"");}%>>Fechado</option><%}%>
				<%}%>
				<%
				if(chamado.getStatus().equals("Fechado"))
				{%>
					<option value="Fechado"<%if(chamado != null && chamado.getStatus().equals("Fechado")){out.print("selected=\"selected\"");}%>>Fechado</option>
					<option value="Processando"<%if(chamado != null && chamado.getStatus().equals("Processando")){out.print("selected=\"selected\"");}%>>Processando</option>
				    <option value="Pendente"<%if(chamado != null && chamado.getStatus().equals("Pendente")){out.print("selected=\"selected\"");}%>>Pendente</option>
				    <%if(usuario.getGrupo().getId() == chamado.getGrupoAtribuido().getId()){%><option value="Solucionado"<%if(chamado != null && chamado.getStatus().equals("Solucionado")){out.print("selected=\"selected\"");}%>>Solucionado</option><%}%>
				<%}%> 
				</select>			
			</div>
			<div class="col-sm-2"></div>
		</div>
		</br>
	<!-- row -->
	<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-4">
				<label for="txtGrupoAutor" class="control-label">Grupo Autor:</label> 	
				<input type="text" id="txtGrupoAutor" name="txtGrupoAutor" class="form-control"  readonly="true"
				value="<%if(chamado != null){
				out.print(chamado.getGrupoAutor().getNomeGrupo());
				}%>"/> 
				</br>
			</div>
			<div class="col-sm-4">
				<label for="txtUsuarioAutor" class="control-label">Usuario Autor:</label> 	
				<input type="text" id="txtUsuarioAutor" name="txtUsuarioAutor" class="form-control text-capitalize"  readonly="true"
				value="<%if(chamado != null){
				out.print(chamado.getUsuarioAutor().getNome() + " " + chamado.getUsuarioAutor().getSobrenome());
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
				<label for="txtGrupo" class="control-label">Grupo Atribuido:</label> 	
				<select id="txtGrupo" name="txtGrupo" class="form-control"><%
					if(grupos != null)	//teste de nulidade da lista
					{
						for(EntidadeDominio e : grupos)				//fazendo a lista
						{
							Grupo g = (Grupo)e;
							if(!g.isDesativado())
							{
							%>
							<option value="<% out.print(g.getId()); %>" <%
							if(chamado != null && chamado.getGrupoAtribuido().getId() == g.getId())
							{
								out.print("selected=\"selected\"");
							}%>>
							<% out.print(g.getNomeGrupo()); %>
							</option>
					  <%}} 
					}%>
			 	</select>
				</br>
			</div>
			<div class="col-sm-4">
				<label for="txtTipo" class="control-label">Tipo de Serviço:</label> 	
				<select id="txtTipo" name="txtTipo" class="form-control"><%
					if(tiposdeservico != null)	//teste de nulidade da lista
					{
						for(EntidadeDominio e : tiposdeservico)				//fazendo a lista
						{
							TipoDeServico t = (TipoDeServico)e;%>
							<option value="<% out.print(t.getId()); %>" <%
							if(chamado != null && chamado.getTipoDeServico().getId() == t.getId())
							{
								out.print("selected=\"selected\"");
							}%>>
							<% out.print(t.getGrupo().getNomeGrupo() + " - " + t.getNome()); %>
							</option>
					  <%} 
					}%>
			 	</select>			      
			</div>
			<div class="col-sm-2"></div>			
		</div>	
	<!-- row -->		
	<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<label for="txtTitulo" class="control-label">Título:</label> 
				<input type="text" id="txtTitulo" name="txtTitulo" class="form-control"
				value="<%if(chamado != null){
				out.print(chamado.getTitulo());
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
				<label for="txtDescricao" class="control-label">Descrição:</label> 
				<textarea rows="4" cols="100" id="txtDescricao" name="txtDescricao" class="form-control" ><%
				if(chamado != null){
				out.print(chamado.getDescricao());
				}
				%></textarea>				
			</div>
			<div class="col-sm-2"></div>
		</div>
	<!-- row -->
	<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<!-- MENSAGEM -->
				
				<%if(resultado !=null && resultado.getMsg() != null)
				{
					out.print(resultado.getMsg());
					session.setAttribute("resultado", null);
				}
				%>
				</br>
				</br>				
			</div>
			<div class="col-sm-2"></div>
		</div>
	<!-- row -->
	<!-- row -->
		<div class="row">
			<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<input type="submit" id="operacao" name="operacao" value="ALTERAR" class="form-control btn-primary"/>
					</br>
				
				</div>
			<div class="col-sm-4"></div>	
		</div>		
		</div>
		<hr />
		</br>
</form>	
<!-- ------------------------------------------------------------------FIM DO FORM-------------------------------------------------------------------------- -->
		<!-- ---------------------------Acompanhamento------------------------------>
		<form action="GerenciarFollowUps" method="post">
<!-- row -->	
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<input type="hidden" id="txtID" name="txtID"
				value="<%if(chamado != null && chamado.getId() != null)
				{
					out.print(chamado.getId());
				}%>" />
				<textarea rows="4" cols="100" id="txtFollowUps" name="txtFollowUps" class="form-control"  
				 <%
				if(chamado.getStatus().equals("Fechado"))
				{
					%> readonly=true placeholder="												Abra o Chamado Novamente Primeiro! 
					Saiba que ao Reabrir o Chamado, a SLA não mudará e o tempo aumentará, recomendamos que crie um novo chamado."<%
				}
				else
				{
					%> placeholder="Adicionar Novo Acompanhamento:"<%
				}
				%>
				></textarea>
			</div>
			<div class="col-sm-2"></div>
		</div>
<!-- row -->
<!-- row -->
		</br>
		</br>
		<div class="row">
			<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<input type="submit" id="operacao" name="operacao" value="SALVAR" class="form-control btn-primary"/>				
				</div>
			<div class="col-sm-4"></div>	
		</div>	
<!-- row -->
		<a href=GerenciarFollowUps?operacao=CONSULTAR>Recarregar Chamado</a>
		
		<!-- -------------------TABELA-------------- -->
		<table class="table table-striped">
			<tr>
				<th>Data</th>
				<th>Proprietário</th>
				<th>Follow Up</th>
			</tr>
			<%
			if(chamado != null && chamado.getFollowUps() != null)	//teste de nulidade da lista
			{
				for(FollowUpsChamados e : chamado.getFollowUps())				//fazendo a lista
				{
					FollowUpsChamados f = (FollowUpsChamados)e;%>
			<tr>
				<td>
					<% out.print(ConverteDate.converteDateStringHora(f.getDataCriacao())); %>
				</td>
				<td>
					<% out.print(f.getProprietario().getNome() + " " + f.getProprietario().getSobrenome()); %>
				</td>
				<td>
					<%out.print(f.getFollowUps());%>
				</td>
			</tr>
			<%} } %>
		</table>				
	</form>		
</body>
</html>