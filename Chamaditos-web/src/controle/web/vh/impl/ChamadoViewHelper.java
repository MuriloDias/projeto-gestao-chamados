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
import dominio.Grupo;
import dominio.TipoDeServico;
import dominio.Usuario;

public class ChamadoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String id = request.getParameter("txtID");
		String titulo = request.getParameter("txtTitulo");
		String descricao = request.getParameter("txtDescricao");
		String grupo  = request.getParameter("txtGrupo");			//será passado o ID para obter para poder ser utilizado
		String tipodeservico = request.getParameter("txtTipo");
		String status = request.getParameter("txtCbStatus");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuariologin");
		
		
		String operacao = request.getParameter("operacao");
		Chamado c = new Chamado();
		if(!operacao.equals("VISUALIZAR")) {
			
			if(id != null && !id.trim().equals(""))
			{
				c.setId(Integer.valueOf(id));
			}
			if(titulo != null && !titulo.trim().equals(""))
			{
				c.setTitulo(titulo);
			}
			if(descricao != null && !descricao.trim().equals(""))
			{
				c.setDescricao(descricao);
			}
			//Para montar a consulta relacionada com a pessoa logada, utilizamos o
			//objeto de grupo autor e atribuido para realizar a consulta do DAO onde o usuario
			//logado tivesse seu grupo relacionado com o chamado 
			if(usuario != null)
			{
				
				//PARA FAZER O CONSULTAR DO FORMCRIARCHAMADO PUXAR Os chamados relacionados com o usuario 
				//logado que ele criou e atribuiu -> chamadoDAO linha 172
				//Utilizado para consulta de chamados relacionados a pessoa logada
				//c.getGrupoAtribuido().setNomeGrupo(usuario.getGrupo().getNomeGrupo());
				//c.setGrupoAtribuido(usuario.getGrupo());
				c.setGrupoAutor(usuario.getGrupo());
				c.setUsuarioAutor(usuario);
			}
			if(grupo != null && !grupo.trim().equals(""))
			{
				Grupo g = new Grupo();
				g.setId(Integer.valueOf(grupo));//conseguiu converter? Então é um ID que veio do JSP
				//utilizado para salvar chamados novos de acordo com o grupo selecionado
				//c.getGrupoAtribuido().setId(g.getId());
				c.setGrupoAtribuido(g);
			}
			if(operacao.equals("CONSULTAR"))
			{
				c.setGrupoAtribuido(usuario.getGrupo());
				TipoDeServicoViewHelper tipo = new TipoDeServicoViewHelper();
				tipo.getEntidade(request);
			}
			if(tipodeservico != null && !tipodeservico.trim().equals(""))
			{
				TipoDeServico t = new TipoDeServico();
				t.setId(Integer.valueOf(tipodeservico));//conseguiu converter? Então é um ID que veio do JSP
				// pega a lista de tipos do contexto da servlet
				Resultado result = (Resultado) request.getServletContext().getAttribute("resultadoTipoServico");
				for(EntidadeDominio e : result.getEntidades())
				{
					if(e.getId() == t.getId())
					{
						t = (TipoDeServico)e;
						break;
					}
				}
				
				
				c.setTipoDeServico(t);
			}
			if(status != null && !status.trim().equals(""))
			{
				c.setStatus(status);
			}
			
		}
		else {
			HttpSession session = request.getSession();
			Resultado resultado = (Resultado) session.getAttribute("resultadoconsultar");
			String txtId = request.getParameter("txtID");
			int Id=0;
			
			if(txtId != null && !txtId.trim().equals("")){
				Id = Integer.parseInt(txtId);
			}
			for(EntidadeDominio e: resultado.getEntidades()){
				if(e.getId() == Id){
					c = (Chamado)e;
				}
			}
		}	
		return c;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		RequestDispatcher d=null;

		String operacao = request.getParameter("operacao");

		if(resultado.getMsg() != null) {
			if(operacao.equals("SALVAR")) {
				request.getSession().setAttribute("resultado", resultado);
				d = request.getRequestDispatcher("FormCriarChamado.jsp");
			}
			else if(operacao.equals("ALTERAR")) {
				request.getSession().setAttribute("resultado", resultado);
				d = request.getRequestDispatcher("GerenciarChamado?txtID=" + resultado.getEntidades().get(0).getId() + "&operacao=VISUALIZAR");
			}
		}
		//---------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("SALVAR")) {

			//resultado.setMsg("Chamado cadastrado com sucesso!");
			resultado.setMsg(
					"Chamado <a href=\"GerenciarChamado?txtID=" 
			+ resultado.getEntidades().get(0).getId() + "&operacao=VISUALIZAR\">" 
							+ resultado.getEntidades().get(0).getId() + 
							"</a> cadastrado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarChamado?operacao=CONSULTAR");
			
		}
		if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
			
			resultado.setMsg("Chamado alterado com sucesso!");
			d = request.getRequestDispatcher("GerenciarChamado?txtID=" + resultado.getEntidades().get(0).getId() + "&operacao=VISUALIZAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("EXCLUIR")) {
			
			resultado.setMsg("Chamado excluido com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarChamado?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		//----------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
			request.getSession().setAttribute("resultadoconsultar", resultado);
			d = request.getRequestDispatcher("FormCriarChamado.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
			List<EntidadeDominio> lista = resultado.getEntidades();
			EntidadeDominio ent = lista.get(0);
			request.getSession().setAttribute("chamado", ent);
			//sessionatributes<string, object>
			//request.getSession().setAttribute("chamado", resultado.getEntidades().get(0));
			d = request.getRequestDispatcher("FormVisualizaChamado.jsp");
		}
		
		d.forward(request,response);	
	}

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub

	}

}
