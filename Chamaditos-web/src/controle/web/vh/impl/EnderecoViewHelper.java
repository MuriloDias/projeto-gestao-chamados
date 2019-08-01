package controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.Endereco;
import dominio.EntidadeDominio;

public class EnderecoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String id = request.getParameter("txtIDRua");
		String rua = request.getParameter("txtRua");
		String cidade = request.getParameter("txtCidade");
		String bairro = request.getParameter("txtBairro");
		String estado = request.getParameter("txtEstado");
		String cep = request.getParameter("txtCEP");
		String complemento = request.getParameter("txtComplemento");
		String numero = request.getParameter("txtNumero");
		
		Endereco e = new Endereco();
		if(id != null && !id.trim().equals(""))
		{
			e.setId(Integer.valueOf(id));
		}
		if(rua != null && !rua.trim().equals(""))
		{
			e.setRua(rua);
		}
		if(cidade != null && !cidade.trim().equals(""))
		{
			e.setCidade(cidade);
		}
		if(bairro != null && !bairro.trim().equals(""))
		{
			e.setBairro(bairro);
		}
		if(estado != null && !estado.trim().equals(""))
		{
			e.setEstado(estado);
		}
		if(cep != null && !cep.trim().equals(""))
		{
			e.setCep(cep);
		}
		if(complemento != null && !complemento.trim().equals(""))
		{
			e.setComplemento(complemento);
		}
		if(numero != null && !numero.trim().equals(""))
		{
			e.setNumero(numero);
		}
		
		return e;
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
				d = request.getRequestDispatcher("");
			}
		}
		//---------------------------------------------------------------------------
		if(resultado.getMsg() == null && operacao.equals("SALVAR")) {

			resultado.setMsg("Grupo cadastrado com sucesso!");
			request.getSession().setAttribute("resultado", resultado);
			d = request.getRequestDispatcher("GerenciarUsuario?operacao=CONSULTAR");
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
			request.getSession().setAttribute("resultadoconsultar", resultado);
			d = request.getRequestDispatcher("");
		}
		if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
			
			request.setAttribute("grupo", resultado.getEntidades().get(0));
			d = request.getRequestDispatcher("");
		}
		
		d.forward(request,response);	
	}

	@Override
	public void setView(Resultado resultado, ServletConfig config) {
		// TODO Auto-generated method stub
		
	}

}
