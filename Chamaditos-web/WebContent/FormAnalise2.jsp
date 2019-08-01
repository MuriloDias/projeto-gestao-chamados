<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="core.aplicacao.Resultado, dominio.*, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>:::: ANÁLISE ::::</title>
<% Analise a = (Analise)request.getAttribute("analisado");
if(a != null)
{
List<String> linhas = a.getLinhas();%>
<!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = google.visualization.arrayToDataTable([
            <%
        	/*['Mes', 'SLA1', 'SLA2'],
            ['Jan', 1000, 400],
            ['Fev', 1170, 460],
            ['Mar', 660, 1120],
            ['Abr', 1030, 540]*/
            for(int i = 0; i < linhas.size(); i++)
            {
            		out.print(linhas.get(i));
            		// se não for a última, adiciona uma virgula
            		if(i != linhas.size() -1)
            			out.print(", ");
            }
            %>
        ]);

        // Set chart options
        var options = {'title':'SLA dos Chamados',
                       //'width':800,
                       'height':500,
                        hAxis: { title: 'Data'},
                        vAxis: { title: 'Qtde'}};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
<%} %>
</head>
<body>
<%
//objetos para preencher select

Resultado resultadoGrupo = (Resultado)getServletContext().getAttribute("resultadoGrupo");
Analise information = (Analise)request.getAttribute("information");


//Listas de objetos para preencher o select

List<EntidadeDominio> grupos = resultadoGrupo.getEntidades();

%>


<form action="AnaliseViewHelper" method="post">
	<div class="container-fluid">
			<br>
			<br>
			<br>
			<h1 class="text-center">Análise</h1>
			<br>
			<br>
	<!-- row -->
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-2">
					<!-- Lista de grupo ----------------------------------------------->
					<label for="txtGrupo" class="control-label">Grupos: </label> 	
					 <select id="txtGrupo" name="txtGrupo" class="form-control">
					 <%if(grupos != null && grupos != null)
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
							  		<%}}}
					 %>
					 </select>	
				</div>
				<div class="col-sm-2">
				<!-- Lista de Status ----------------------------------------------->
					<label for="txtStatus" class="control-label">Status: </label> 	
					 <select id="txtStatus" name="txtStatus" class="form-control">
						 <option value="Processando">Processando</option>
						 <option value="Pendente">Pendente</option>
						 <option value="Solucionado">Solucionado</option>
						 <option value="Fechado">Fechado</option>
					 </select>					
				</div>
				<div class="col-sm-2">
					<!-- Lista de Prazo ----------------------------------------------->
					<label for="txtBegin" class="control-label">De:</label><br>
					<input id="txtBegin" name="txtBegin" class="form-control" type="date"> 	
				</div>
								<div class="col-sm-2">
					<!-- Lista de Prazo ----------------------------------------------->
					<label for="txtUntil" class="control-label">Até:</label><br>
					<input id="txtUntil" name="txtUntil" class="form-control" type="date"> 	
				</div>
				<div class="col-sm-2"></div>
			</div>
			<br>
			<br>
	<!-- row -->
	<!-- row -->
			<div class="row">
				<div class="col-sm-5"></div>
				<div class="col-sm-2">
					<button type="submit" id="operacao" name="operacao" value="CONSULTAR" class="btn btn-primary btn-block btn-lg">Analisar</button>
				</div>
				<div class="col-sm-5"></div>
			</div>
	<!-- row -->	
	<!-- row -->
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<div><div id="chart_div"></div></div>
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