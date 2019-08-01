package controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.EntidadeDominio;
import dominio.Grupo;

public class GrupoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		Grupo g = new Grupo();
		if(request != null)
		{
			String nome = request.getParameter("txtNome");
			String id = request.getParameter("txtID");
	
			
			
			if(nome != null && !nome.trim().equals(""))
			{
				g.setNomeGrupo(nome);
			}
			if(id != null && !id.trim().equals(""))
			{
				g.setId(Integer.valueOf(id));
			}
		}
		return g;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		RequestDispatcher d=null;

		String operacao = request.getParameter("operacao");

		if(resultado.getMsg() != null) {
			if(operacao.equals("SALVAR") || operacao.equals("ALTERAR") || operacao.equals("EXCLUIR")) {
				request.getSession().setAttribute("resultado", resultado);
				d = request.getRequestDispatcher("FormGrupo.jsp");
			}
		}
		//---------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("SALVAR")) {

			resultado.setMsg("Grupo cadastrado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarGrupo?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
			
		}
		if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
			
			resultado.setMsg("Grupo alterado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarGrupo?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("EXCLUIR")) {
			
			resultado.setMsg("Grupo excluido com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarGrupo?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		//----------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
			request.getSession().setAttribute("resultadoconsultar", resultado);
			//atualiza objeto com a lista de grupo que será carregada na sessão
			request.getSession().getServletContext().setAttribute("resultadoGrupo", resultado);
			d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
			
			request.setAttribute("grupo", resultado.getEntidades().get(0));
			d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		d.forward(request,response);	
	}

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub
		config.getServletContext().setAttribute("resultadoGrupo", resultado);
	}
}
