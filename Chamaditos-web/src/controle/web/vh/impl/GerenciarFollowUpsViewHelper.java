package controle.web.vh.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.FollowUpsChamados;
import dominio.Usuario;

public class GerenciarFollowUpsViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String operacao = request.getParameter("operacao");
		String followUps = request.getParameter("txtFollowUps");			
		String id = request.getParameter("txtID");
		
		Chamado chamado = (Chamado)request.getSession().getAttribute("chamado");
		//Integer codigoChamado = chamado.getId();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuariologin");
		//String proprietario = usuario.getNome() + " " + usuario.getSobrenome();
		
		FollowUpsChamados followups = new FollowUpsChamados();
		//Usuario usuario = new Usuario(); // (Usuario)request.getSession().getAttribute("Usuario");
		
		
		if(!operacao.equals("VISUALIZAR")) {
			if(id != null && !id.trim().equals(""))
			{
				followups.setId(Integer.valueOf(id));
			}
			if(followUps != null && !followUps.trim().equals(""))
			{
				followups.setFollowUps(followUps);
			}
			if(chamado != null)
			{
				followups.setCodigoChamado(chamado);
			}

			if(usuario != null)
			{
				followups.setProprietario(usuario);
			}

			//usuario.setId(1);
			//followups.setProprietario(usuario);
		}
		else
		{
			HttpSession session = request.getSession();
			Resultado resultado = (Resultado) session.getAttribute("resultadoconsultar");
			String txtId = request.getParameter("txtID");
			int Id=0;
			
			if(txtId != null && !txtId.trim().equals("")){
				Id = Integer.parseInt(txtId);
			}
			
			for(EntidadeDominio e: resultado.getEntidades()){
				if(e.getId() == Id){
					followups = (FollowUpsChamados)e;
				}
			}
		}
		return followups;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		RequestDispatcher d=null;

		String operacao = request.getParameter("operacao");
		Chamado chamado = (Chamado)request.getSession().getAttribute("chamado");

		if(resultado.getMsg() != null) {
			if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				request.getSession().setAttribute("resultado", resultado);
				d = request.getRequestDispatcher("FormVisualizaChamado.jsp");
			}
		}
		//---------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("SALVAR")) {

			resultado.setMsg("Follow Up cadastrado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarChamado?txtID=" + chamado.getId() + "&operacao=VISUALIZAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
			
		}
		if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
			
			resultado.setMsg("Grupo alterado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarUsuario?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("EXCLUIR")) {
			
			resultado.setMsg("Grupo excluido com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarUsuario?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		//----------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
			List<?> lista = resultado.getEntidades();
			chamado.setFollowUps((List<FollowUpsChamados>)lista);
			request.getSession().setAttribute("chamado", chamado);
			d = request.getRequestDispatcher("FormVisualizaChamado.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
			
			request.setAttribute("usuario", resultado.getEntidades().get(0));
			d = request.getRequestDispatcher("FormVisualizaChamado.jsp");
		}
		
		d.forward(request,response);	
	}

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub

	}

}
