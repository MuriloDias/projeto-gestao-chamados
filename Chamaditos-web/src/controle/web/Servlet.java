package controle.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controle.web.command.ICommand;
import controle.web.command.impl.AlterarCommand;
import controle.web.command.impl.ConsultarCommand;
import controle.web.command.impl.ExcluirCommand;
import controle.web.command.impl.SalvarCommand;
import controle.web.command.impl.VisualizarCommand;
import controle.web.vh.IViewHelper;
import controle.web.vh.impl.AnaliseViewHelper;
import controle.web.vh.impl.ChamadoViewHelper;
import controle.web.vh.impl.GerenciarFollowUpsViewHelper;
import controle.web.vh.impl.GrupoViewHelper;
import controle.web.vh.impl.LoginViewHelper;
import controle.web.vh.impl.TipoDeServicoViewHelper;
import controle.web.vh.impl.UsuarioViewHelper;
import core.aplicacao.Resultado;
import dominio.EntidadeDominio;

/**
 * Servlet implementation class servlet
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Map<String, ICommand> commands;
	private static Map<String, IViewHelper> vhs;
	private static String uri = null;
	private static HttpServletRequest request;
	private static String operacao = null;
	private static IViewHelper vh;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
    	commands = new HashMap<String, ICommand>();
    	
    	commands.put("SALVAR", new SalvarCommand());
    	commands.put("EXCLUIR", new ExcluirCommand());
    	commands.put("ALTERAR", new AlterarCommand()); 
    	commands.put("CONSULTAR", new ConsultarCommand());
    	commands.put("VISUALIZAR", new VisualizarCommand());
    	
    	vhs = new HashMap<String, IViewHelper>();
    	
    	vhs.put("/Chamaditos-web/GerenciarGrupo", new GrupoViewHelper());
    	vhs.put("/Chamaditos-web/GerenciarUsuario", new UsuarioViewHelper());
    	vhs.put("/Chamaditos-web/GerenciarChamado", new ChamadoViewHelper());
    	vhs.put("/Chamaditos-web/GerenciarTipoDeServico", new  TipoDeServicoViewHelper());
    	vhs.put("/Chamaditos-web/GerenciarFollowUps", new GerenciarFollowUpsViewHelper());
    	vhs.put("/Chamaditos-web/GerenciarLogin", new LoginViewHelper());
    	vhs.put("/Chamaditos-web/AnaliseViewHelper", new AnaliseViewHelper());
    	vhs.put("/Chamaditos-web/BuscarChamado", new ChamadoViewHelper());
    	
    }
    //metodo usado para inicialização dos objetos, lista e afins na session
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    			super.init(config);
    			// Parametros definidos como <init-param> no web.xml
    			Enumeration<String> parametros = config.getInitParameterNames();

    			while (parametros.hasMoreElements()) {
    				String parametro = parametros.nextElement();
    				uri = config.getInitParameter(parametro);
    				Resultado resultado = doProcess();

    				/*
    				 * Executa o método setView do view helper específico passando o resultado
    				 * da consulta com os dados de domínio que será colocado no contexto para montar 
    				 * as combos
    				 */
    				vh.setView(resultado, config);
    			}	
    }

	

	private void doProcessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		uri = request.getRequestURI();
		
		this.request = request;
		
		// Obtêm a operação executada
		operacao = request.getParameter("operacao");
		
		Resultado resultado = doProcess();
		
		vh.setView(resultado, request, response);
	}
	private Resultado doProcess() throws ServletException{
		// TODO Auto-generated method stub
		
		// Obtêm um viewhelper indexado pela uri que invocou esta servlet
		vh = vhs.get(uri);
		
		// O viewhelper retorna a entidade especifica para a tela que chamou esta
		// servlet
		EntidadeDominio entidade = vh.getEntidade(request);

		// Obtêm a operação executada

		if (request == null) {
			operacao = "CONSULTAR";
		} else {
			operacao = request.getParameter("operacao");
		}

		if(operacao != null) {
			// Obtêm o command para executar a respectiva operação
			ICommand command = commands.get(operacao);

			/*
			 * Executa o command que chamará a fachada para executar a operação requisitada
			 * o retorno é uma instância da classe resultado que pode conter mensagens derro
			 * ou entidades de retorno
			 */
			Resultado resultado = command.execute(entidade);

			return resultado;
		}
				
		return null;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcessRequest(request, response);
	}
}

























