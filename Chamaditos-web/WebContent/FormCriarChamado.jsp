
<%@page import="sun.reflect.ReflectionFactory.GetReflectionFactoryAction"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="core.aplicacao.Resultado, dominio.*, java.util.*"%>
<%@page import="core.util.ConverteDate"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>:::: CADASTRO DE CHAMADO ::::</title>
		<%
		int Processando = 0;
		int Pendente = 0;
		int Solucionado = 0;
		int Fechado = 0;%>
<!-- Teste de JS -->
<script>
var myVar=setInterval(function(){myTimer()},1000);
function myTimer() {
    var d = new Date();
    document.getElementById("demo").innerHTML = d.toLocaleTimeString();
}
</script>

<script>
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("TBchamados");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc"; 
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++; 
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
</script>


<!-- Teste de JS -->
</head>
<body>
	<%
	//objetos usuais
	Resultado resultado = (Resultado) session.getAttribute("resultado");					//usado na mensagem
	Resultado resultadoconsultar = (Resultado) session.getAttribute("resultadoconsultar");	//usado na lista
	Chamado chamado = (Chamado) request.getAttribute("chamado");
	
	//objetos para preencher select
	
	Resultado resultadoGrupo = (Resultado)getServletContext().getAttribute("resultadoGrupo");
	Resultado resultadoTipoServico = (Resultado)getServletContext().getAttribute("resultadoTipoServico");
	
	//Listas de objetos para preencher o select
	
	List<EntidadeDominio> grupos = resultadoGrupo.getEntidades();
	List<EntidadeDominio> tiposdeservico = resultadoTipoServico.getEntidades();
	%>
		
	<form action="GerenciarChamado" method="post">
		<div class="container-fluid">
			<br>
			<br>
			<br>
			<h1 class="text-center">Chamados</h1>
			<br>
			<br>
<!-- row -->
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-4">
				<select id="txtGrupo" name="txtGrupo" class="form-control">
				<option disabled selected value> -- Departamento que Atenderá a Solicitação -- </option><%
					if(grupos != null)	//teste de nulidade da lista
					{
						for(EntidadeDominio e : grupos)				//fazendo a lista
						{
							Grupo g = (Grupo)e;
							if(!g.isDesativado())
							{
							%>
							<option value="<% out.print(g.getId()); %>">
							<% out.print(g.getNomeGrupo()); %>
							</option>
					  <%}}
					}%>
			 	</select>
				</br>			
			</div>
			<div class="col-sm-4">	
				<select id="txtTipo" name="txtTipo" class="form-control">
				<option disabled selected value> -- Tipo de Serviço que o Departamento Prestará -- </option><%
					if(tiposdeservico != null)	//teste de nulidade da lista
					{
						for(EntidadeDominio e : tiposdeservico)				//fazendo a lista
						{
							TipoDeServico t = (TipoDeServico)e;%>
							<option value="<% out.print(t.getId()); %>">
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
				<input type="text" id="txtTitulo" name="txtTitulo" class="form-control" placeholder="Coloque seu Título aqui..."
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
				<textarea rows="4" cols="100" id="txtDescricao" name="txtDescricao" class="form-control" placeholder="Coloque sua Descrição aqui..."><%if(chamado != null){out.print(chamado.getDescricao());}%></textarea>
			</div>	
			<div class="col-sm-2"></div>
		</div>
<!-- row -->
<!-- row -->
		<div class="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-4">
				<%if(resultado !=null && resultado.getMsg() != null)
				{
					out.print(resultado.getMsg());
					session.setAttribute("resultado", null);
				}
				%>	
			</div>
			<div class="col-sm-4"></div>
		</div>
</br>
</br>
<!-- row -->
		<div class="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-4">
				<input type="submit" id="operacao" name="operacao" value="SALVAR" class="form-control btn-primary"/>
				</br>			
			</div>
			<div class="col-sm-4"></div>
		</div>
<!-- row -->
</div>
<hr />
		<a href=GerenciarChamado?operacao=CONSULTAR>Consultar Chamados</a>
		<!-- -------------------TABELA-------------- -->
		<table id="TBchamados" class="table table-striped">
		<tr>
			<th onclick="sortTable(0)"><u>ID</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(1)"><u>Titulo</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(2)"><u>Data Criação</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(3)"><u>Grupo Autor</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(4)"><u>Grupo Atribuído</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(5)"><u>Status</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(6)"><u>Data Resolução Estimada</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th onclick="sortTable(7)"><u>SLA</u><i class="glyphicon glyphicon-sort pull-right"></i></th>
			<th></th>


		</tr>
		<%		
		if(resultadoconsultar != null && resultadoconsultar.getEntidades() != null)	//teste de nulidade da lista
		{
			for(EntidadeDominio e : resultadoconsultar.getEntidades())				//fazendo a lista
			{
				Chamado c = (Chamado) e;
				//-----------------------------------------------------------------------------------------------
				// pra pegar o nome do grupo
				if(grupos != null)	//teste de nulidade da lista
				{
					for(EntidadeDominio e1 : grupos)				//fazendo a lista
					{
						Grupo g = (Grupo)e1;
						if(c.getGrupoAutor().getId() == g.getId())
						{
							c.setGrupoAutor(g);
						}
						if(c.getGrupoAtribuido().getId() == g.getId())
						{
							c.setGrupoAtribuido(g);
						}
				    } 
				}%>
		<tr class="primary">
			<td>
				<% out.print(c.getId()); %>
			</td>
			
			<td>
				<% out.print(c.getTitulo()); %>
			</td>
			<td>
			<%out.print(ConverteDate.converteDateStringHora(c.getDataCriacao())); %>
			</td>
			<td><% out.print(c.getGrupoAutor().getNomeGrupo());%></td>
			
			<td><% out.print(c.getGrupoAtribuido().getNomeGrupo());%></td>
			
			<td><%out.print(c.getStatus()); 
			//calculo na NAV da quantidade de cada tipo de status
			if(c.getStatus().contains("Processando")){
				Processando = Processando + 1;
			}
			if(c.getStatus().contains("Pendente")){
				Pendente = Pendente + 1;
			}
			if(c.getStatus().contains("Solucionado")){
				Solucionado = Solucionado + 1;
			}
			if(c.getStatus().contains("Fechado")){
				Fechado = Fechado + 1;
			}
			%></td>
			
			<td><%out.print(ConverteDate.converteDateStringHora(c.getDtEstimada()));%></td>
			
			
			<td class="data <%
			if(c.getSla() != null)
			{
				if(c.getSla().getStatus().equals("Fechado fora do prazo!!"))
					out.print("text-uppercase danger");
				else if(c.getSla().getStatus().equals("Fechado dentro do prazo!!"))
					out.print("text-uppercase success");
				else if(c.getSla().getStatus().equals("Fora do Prazo!!"))
					out.print("danger");
				else if(c.getSla().getStatus().equals("Próximo ao Prazo!!!"))
					out.print("warning");
				else if(c.getSla().getStatus().equals("Dentro do Prazo.."))
					out.print("info");
			}	
			%>">
				<abbr class="control-label" title="
				<%//out.print("Severidade e Urgencia");
				if(c.getTipoDeServico().getSeveridade() == 3)
				{
					%>Severidade: Alta!&#013;<%
					if(c.getTipoDeServico().getUrgencia() == 3)
					{
						%>Urgência: Alta!<%
					}
					else if(c.getTipoDeServico().getUrgencia() == 2)
					{
						%>Urgência: Media!<%
					}
					else
					{
						%>Urgência: Baixa!<%
					}
				}
				else if(c.getTipoDeServico().getSeveridade() == 2)
				{
					%>Severidade: Media!&#013;<%
					if(c.getTipoDeServico().getUrgencia() == 3)
					{
						%>Urgência: Alta!<%
					}
					else if(c.getTipoDeServico().getUrgencia() == 2)
					{
						%>Urgência: Media!<%
					}
					else
					{
						%>Urgência: Baixa!<%
					}
				}
				else
				{
					%>Severidade: Baixa!&#013;<%
					if(c.getTipoDeServico().getUrgencia() == 3)
					{
						%>Urgência: Alta!<%
					}
					else if(c.getTipoDeServico().getUrgencia() == 2)
					{
						%>Urgência: Media!<%
					}
					else
					{
						%>Urgência: Baixa!<%
					}
				}
				%>">
				<%out.print(c.getSla().getStatus()); %></abbr>
			
			<div class="hidden">print</div>
			<%
			/*if(c.getSla() != null)
			{
				int s = c.getTipoDeServico().getSeveridade() + c.getTipoDeServico().getUrgencia();
				if(s == 6 || s == 5)
				{
					out.print("Severidade e Urgência Altas!");
				}
				else if(s == 4)
				{
					out.print("Severidade e Urgência Medias!");
				}
				else if(s == 2 || s == 3)
				{
					out.print("Severidade e Urgência Baixas!");
				}
			}*/
			%>
			
			</td>
			
			
			<td>
				<a class="btn btn-primary btn-xs" 
				href="<% out.print("GerenciarChamado?txtID=" + c.getId() + "&operacao=VISUALIZAR");%>">
					<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>
					Visualizar
				</a>
			</td>
		</tr>
		<%} } %>
	</table>
	</div>
</form>
<nav class="navbar navbar-default navbar-fixed-top "> 
<form action="BuscarChamado" method="post"> 
	<div>  
	<!-- row -->
		<div class="row">
			<div class="col-sm-1">
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
			<div class="col-sm-1">
				<a class="btn btn-default btn-lg disabled btn-block list-group-item-danger" role="button"><b><i>Processando <span class="badge"><%out.print(Processando);%></span></a></i></b>
			</div>
			<div class="col-sm-1">
			<a class="btn btn-default btn-lg disabled btn-block list-group-item-warning" role="button"><b><i>Pendente <span class="badge"><%out.print(Pendente);%></span></a></i></b>
			</div>
			<div class="col-sm-1">
			<a class="btn btn-default btn-lg disabled btn-block list-group-item-success" role="button"><b><i>Solucionado <span class="badge"><%out.print(Solucionado);%></span></a></i></b>
			</div>
			<div class="col-sm-1">
			<a class="btn btn-default btn-lg disabled btn-block list-group-item-info " role="button"><b><i>Fechado <span class="badge"><%out.print(Fechado);%></span></a></i></b>
			</div>
			<div class="col-sm-5">
				<div class="col-sm-3">
				<input type="text" id="txtID" name="txtID"  class="form-control" placeholder="ID que busca.."
					value="<%if(chamado != null)
					{
					out.print(chamado.getId());
					}%>"/>
				</div>
				<div class="col-sm-3 class="center-block">
				<input type="text" id="txtTitulo" name="txtTitulo" class="form-control" placeholder="Titulo que busca.."
					value="<%if(chamado != null){
					out.print(chamado.getTitulo());
					}%>"/>
				</div>
				<div class="col-sm-3 class="center-block">
				<input type="text" id="txtDescricao" name="txtDescricao" class="form-control" placeholder="Descrição que busca.."
					value="<%if(chamado != null){
					out.print(chamado.getTitulo());
					}%>"/>
				</div>
				<div class="col-sm-3 class="center-block">
				<input type="submit" id="operacao" name="operacao" value="CONSULTAR" class="form-control btn-primary"/>
				</div>
			
			</div>
			<div class="col-sm-2 class="center-block">
			<a class="btn-lg disabled btn-block text-right" role="button"><b><i> Hora: <span id="demo"></span></a></i></b>
			</div>		
		</div>
	<!-- row -->
	</div>
</form>
</nav>
	<br>
	<hr />
	<br>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>