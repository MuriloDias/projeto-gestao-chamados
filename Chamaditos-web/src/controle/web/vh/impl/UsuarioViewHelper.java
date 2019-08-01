package controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import core.util.ConverteDate;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Grupo;
import dominio.Usuario;

public class UsuarioViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String id = request.getParameter("txtID");
		String nome = request.getParameter("txtNome");
		String sobrenome = request.getParameter("txtSobrenome");
		String dtNascimento = request.getParameter("txtDtNascimento");
		String genero = request.getParameter("txtGenero");
		String email = request.getParameter("txtEmail");
		String senha = request.getParameter("txtSenha");
		String grupo = request.getParameter("txtGrupo");			//será passado o ID para poder ser utilizado
		
		String operacao = request.getParameter("operacao");
		Usuario u = new Usuario();					//criação da instancia de usuário
		if(!operacao.equals("VISUALIZAR")){
			EnderecoViewHelper e = new EnderecoViewHelper();
			
			Endereco end = (Endereco) e.getEntidade(request);
			u.setEndereco(end);
			
			if(id != null && !id.trim().equals(""))
			{
				u.setId(Integer.valueOf(id));
			}
			if(nome != null && !nome.trim().equals(""))
			{
				u.setNome(nome);
			}
			if(sobrenome != null && !sobrenome.trim().equals(""))
			{
				u.setSobrenome(sobrenome);
			}
			if(dtNascimento != null && !dtNascimento.trim().equals(""))
			{
				u.setDtnascimento(ConverteDate.converteStringDate(dtNascimento));
			}
			if(genero != null && !genero.trim().equals(""))
			{
				u.setGenero(genero);
			}
			if(email != null && !email.trim().equals(""))
			{
				u.setEmail(email);
			}
			if(senha != null && !senha.trim().equals(""))
			{
				u.setSenha(senha);
			}
			if(grupo != null && !grupo.trim().equals(""))
			{
				//u.setGrupo(new Grupo());
				//u.getGrupo().setId(Integer.valueOf(id));
				Grupo g = new Grupo();
				try {								//tenta converter string para int 
					g.setId(Integer.valueOf(grupo));//conseguiu converter? Então é um ID que veio do JSP
				}
				catch(Exception ex) {				//se não conseguiu converter
					g.setNomeGrupo(grupo);			//então é um nome do grupo
				}
				u.setGrupo(g);
			}
		}
		else{
			HttpSession session = request.getSession();
			Resultado resultado = (Resultado) session.getAttribute("resultadoconsultar");
			String txtId = request.getParameter("txtID");
			int Id=0;
			
			if(txtId != null && !txtId.trim().equals("")){
				Id = Integer.parseInt(txtId);
			}
			
			for(EntidadeDominio e: resultado.getEntidades()){
				if(e.getId() == Id){
					u = (Usuario)e;
				}
			}
		}
		return u;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		RequestDispatcher d=null;

		String operacao = request.getParameter("operacao");

		if(resultado.getMsg() != null) {
			if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				request.getSession().setAttribute("resultado", resultado);
				d = request.getRequestDispatcher("FormUsuario.jsp");
			}
		}
		//---------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("SALVAR")) {

			resultado.setMsg("Usuário cadastrado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarUsuario?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
			
		}
		if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
			
			resultado.setMsg("Usuário alterado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarUsuario?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("EXCLUIR")) {
			
			resultado.setMsg("Usuário excluido com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarUsuario?operacao=CONSULTAR");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		//----------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
			request.getSession().setAttribute("resultadoconsultar", resultado);
			d = request.getRequestDispatcher("FormUsuario.jsp");
		}
		if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
			
			request.setAttribute("usuario", resultado.getEntidades().get(0));
			d = request.getRequestDispatcher("FormUsuario.jsp");
		}
		
		d.forward(request,response);	
	}

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub
		
	}

}
