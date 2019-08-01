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
import dominio.Usuario;

public class LoginViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String email = request.getParameter("txtLogin");
		String senha = request.getParameter("txtSenha");
		
		String operacao = request.getParameter("operacao");
		Usuario u = new Usuario();							//criação da instancia de usuário
		
		if(!operacao.equals("VISUALIZAR")){
			
			if(email != null && !email.trim().equals(""))
			{
				u.setEmail(email);
			}
			if(senha != null && !senha.trim().equals(""))
			{
				u.setSenha(senha);
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
		//----------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
			request.getSession().setAttribute("resultadologin", resultado);
			
			if(resultado.getEntidades() == null || resultado.getEntidades().isEmpty())
			{				
					resultado.setMsg("Email e/ou Senha incorretos..");
					d = request.getRequestDispatcher("login.jsp");
			}
			else
			{
				Usuario user = (Usuario) resultado.getEntidades().get(0);
				if(user.isDesativado())
				{
					resultado.setMsg("Usuário desativado..");
					d = request.getRequestDispatcher("login.jsp");
				}
				else
				{
					request.getSession().setAttribute("usuariologin", resultado.getEntidades().get(0));
					d = request.getRequestDispatcher("index.jsp");
				}

			}
			
		}
		if(resultado.getMsg() == null && operacao.equals("EXCLUIR")) {
			
			resultado.setMsg("Usuário excluido com sucesso!");
			request.getSession().setAttribute("resultadologin", null);
			d = request.getRequestDispatcher("login.jsp");
			//d = request.getRequestDispatcher("FormGrupo.jsp");
		}
		d.forward(request,response);	
	}

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub

	}

}
