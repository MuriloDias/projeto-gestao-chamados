package controle.web.vh.impl;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import core.util.ConverteDate;
import dominio.Analise;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.Grupo;

public class AnaliseViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String status = request.getParameter("txtStatus");
		String grupo  = request.getParameter("txtGrupo");
		String prazo = "";
		String begin = request.getParameter("txtBegin");
		String until = request.getParameter("txtUntil");

		
		
		String operacao = request.getParameter("operacao");
		Analise a = new Analise();
		Chamado c = new Chamado();
		if(!operacao.equals("VISUALIZAR")) {
			/*if(prazo != null && !prazo.trim().equals(""))
			{
				GregorianCalendar gc = new GregorianCalendar();
		        gc.setTime(new Date());
		        gc.add(Calendar.MONTH, -(Integer.parseInt(prazo)));
				c.setDataCriacao(gc.getTime());
				a.setTempo(prazo);
			}*/
			if(begin != null && !begin.trim().equals(""))								//data criação usada para passa no obj ao DAO a data inicial do periodo do chamados a consultar
			{
				c.setDataCriacao(ConverteDate.converteStringDateAnalise(begin));
				prazo = request.getParameter("De: " + "txtBegin") + " - ";
			}
			if(until != null && !until.trim().equals(""))								//data encerramento usado para passa no obj ao DAO a data inicial do periodo do chamados a consultar
			{
				c.setDataEncerramento(ConverteDate.converteStringDateAnalise(until));
				prazo += request.getParameter("Para: " + "txtUntil");
				//a.setTempo(prazo);
			}
			if(status != null && !status.trim().equals(""))
			{
				c.setStatus(status);
				//a.setStatus(status);
			}
			if(grupo != null && !grupo.trim().equals(""))
			{
				Grupo g = new Grupo();
				g.setId(Integer.valueOf(grupo));//conseguiu converter? Então é um ID que veio do JSP
				c.setGrupoAtribuido(g);
				//a.setGrupo(grupo);
			}
		}
		a.setChamado(c);
		request.setAttribute("information", a);
		return a;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub		
		List<EntidadeDominio> chamados = resultado.getEntidades();
		
		Analise a = new Analise();
		// Listas pra meses, tipos de SLA e as linhas da tabela do Google
		//List<Integer> mes = new ArrayList<Integer>();
		List<String> mes = new ArrayList<String>();
		List<String> sla = new ArrayList<String>();
		List<String> linhas = new ArrayList<String>();

		ConverteDate conv = new ConverteDate();
		// Separar quais são os meses e os tipos de SLA
		for(EntidadeDominio e : resultado.getEntidades())
		{
			Chamado c = (Chamado)e;
			
			//CARREGA DATAS EXISTENTES NÃO IGUAIS!
			String m = conv.converteDateString(c.getDataCriacao());
			//int m = c.getDataCriacao().getMonth();
			//if(!mes.contains(m))
			//mes.add(m);
			if(!mes.isEmpty())
			{
				//for(String s : mes)
				//{
					if(!mes.contains(m))
						mes.add(m);
				//} 
			}
			else
			{
				if(!mes.contains(m))
					mes.add(m);
			}

			
			//CARREGA SLAs EXISTENTES NÃO IGUAIS!
			//se o chamado estiver aberto os slas serão DENTRO, PROXIMO e FORA do prazo
			//sem o chamado estiver fechado as slas serão, FECHADO FORA E DENTRO DO PRAZO
			String s = c.getSla().getStatus();
			if(!sla.contains(s))
				sla.add(s);
		}

		// Montar a linha de titulo com os tipos de SLAs existentes na pesquisa 
		//(DENTRO, PROXIMO e FORA do prazo) ou (FECHADO FORA E DENTRO DO PRAZO)
		//String header = "['Mês', ";
		String header = "['Data', ";
		for(String s : sla)
	    {
	    	header += "'" + s + "', ";
	    }
	    header = header.substring(0, header.length() - 2) + "]";
	    linhas.add(header);

	    // Montar as linhas com os dados do gráfico
		//for(int m : mes)
	    for(String m : mes)
		{
			// ---------------String linha = "['" + String.valueOf(m+1) + "', ";
			//String fla = new DateFormatSymbols().getMonths()[m];
	    	String fla = m;
			String linha = "['" + fla + "', ";
			for(String s : sla)
			{
				int qtde = 0;

				for(EntidadeDominio e : resultado.getEntidades())
				{
					Chamado c = (Chamado)e;
					//if(c.getDataCriacao().getMonth() == m &&
						//c.getSla().getStatus().equals(s))
					//{
					//	qtde += 1;
					//}
					String data = conv.converteDateString(c.getDataCriacao());
					if(m.equals(data) && c.getSla().getStatus().equals(s))
					{
						qtde += 1;
					}
				}

				linha += qtde + ", ";
			}
			linha = linha.substring(0, linha.length() - 2) + "]";
			linhas.add(linha);
			//System.out.println(linha);
		}
		a.setLinhas(linhas);
		request.setAttribute("analisado", a);
		RequestDispatcher d = request.getRequestDispatcher("FormAnalise2.jsp");
		d.forward(request,response);
	}
	

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub

	}

}
