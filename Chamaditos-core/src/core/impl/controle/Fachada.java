package core.impl.controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import core.IDAO;
import core.IFachada;
import core.IStrategy;
import core.aplicacao.Resultado;
import core.impl.dao.AnaliseDAO;
import core.impl.dao.ChamadoDAO;
import core.impl.dao.FollowUpsDAO;
import core.impl.dao.GrupoDAO;
import core.impl.dao.TipoDeServicoDAO;
import core.impl.dao.UsuarioDAO;
import core.impl.negocio.CalculoStatusSla;
import core.impl.negocio.ComplementaDataCriacao;
import core.impl.negocio.ComplementaDataCriacaoFollowUps;
import core.impl.negocio.ComplementaStatus;
import core.impl.negocio.SetaDataEncerramento;
import core.impl.negocio.VerificaDtNascSalvarUsuario;
import core.impl.negocio.VerificaEmailSalvarUsuario;
import core.impl.negocio.VerificaEnderecoUsuario;
import core.impl.negocio.VerificaExcluirGrupo;
import core.impl.negocio.VerificaGrupoIgualGrupoAtt;
import core.impl.negocio.VerificaGrupoSalvarUsuario;
import core.impl.negocio.VerificaIdENomeExcluir;
import core.impl.negocio.VerificaNomeAlterarGrupo;
import core.impl.negocio.VerificaNomeGrupo;
import core.impl.negocio.VerificaNuloDescricaoChamado;
import core.impl.negocio.VerificaNuloTituloChamado;
import core.impl.negocio.VerificaSenhaSalvarUsuario;
import core.impl.negocio.VerificaTamanhoDescricaoChamado;
import core.impl.negocio.VerificaTamanhoTituloChamado;
import dominio.Analise;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.FollowUpsChamados;
import dominio.Grupo;
import dominio.TipoDeServico;
import dominio.Usuario;

public class Fachada implements IFachada{

	//----------------------MAPEAMENTOS-----------------------

	//Mapa de DAOS, ser[a indexado pelo nome da entidade
	//o valor é uma instancia do DAO para uma dada entidade
	private Map<String, IDAO> daos;

	//Mapa para conter as regras de negocio de todas operações por entidade;
	//o valor é um mapa que de regras de negocio indexado pela operação
	private Map<String, Map<String, List<IStrategy>>> rns;

	private Resultado resultado;


	//--------------------CONSTRUTOR-------------------------
	public Fachada() {
		//instanciando MAP de DAOS
		daos = new HashMap<String, IDAO>();

		//instanciando MAP de regras de negocio
		//esta é uma estrutura de arvore, onde a primeira chave do mapa é o nome da classe.
		//Cada classe então tem seu mapa de regras, indexado pela operação. Exemplo:
		//Cliente tem quatro operações, então ao realizar rns.get(Cliente.class.getName())
		//sera retornado um mapa com 4 chaves(as 4 operações), e o valor de cada uma destas chaves
		//é uma lista de regras a serem executadas para aquela operação (Strategies)
		rns = new HashMap<String, Map<String, List<IStrategy>>>();

		//Criando instancias dos DAOS a serem utilizados
		//Adicionando cada dado no MAP indexado pelo nome da classe
		daos.put(Grupo.class.getName(), new GrupoDAO());		
		daos.put(Usuario.class.getName(), new UsuarioDAO());
		daos.put(Chamado.class.getName(), new ChamadoDAO());
		daos.put(TipoDeServico.class.getName(), new TipoDeServicoDAO());
		daos.put(FollowUpsChamados.class.getName(), new FollowUpsDAO());
		daos.put(Analise.class.getName(), new AnaliseDAO());
		

		//-------------------------Grupo-----------------------------------
		//Criando instancias das regras de negocio a serem utilizados
		//ValidadorCnpj vCnpj = new ValidadorCnpj(); <---exemplo
		VerificaNomeGrupo verificanomegrupo = new VerificaNomeGrupo();
		VerificaIdENomeExcluir verificaidenomeexcluir = new VerificaIdENomeExcluir();
		VerificaNomeAlterarGrupo verificanomealterargrupo = new VerificaNomeAlterarGrupo();
		VerificaExcluirGrupo verificaexcluirgrupo = new VerificaExcluirGrupo();

		//Criando uma lista para conter as regras de negocio de fornecedor
		//quando a operação for salvar - alterar e consultar
		List<IStrategy> rnsSalvarGrupo = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarGrupo = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirGrupo = new ArrayList<IStrategy>();

		//Adicionando as regras a serem utilizadas na operação SALVAR do grupo
		//rnsSalvarFornecedor.add(vCnpj);          <------exemplo
		rnsSalvarGrupo.add(verificanomegrupo);
		
		//Adicionando as regras a serem utilizadas na operação ALTERAR do grupo
		//rnsSalvarFornecedor.add(vCnpj);          <------exemplo
		rnsAlterarGrupo.add(verificanomegrupo);
		rnsAlterarGrupo.add(verificanomealterargrupo);
		
		//Adicionando as regras a serem utilizadas na operação EXCLUIR do grupo
		//rnsSalvarFornecedor.add(vCnpj);          <------exemplo
		rnsExcluirGrupo.add(verificaidenomeexcluir);
		rnsExcluirGrupo.add(verificaexcluirgrupo);
		
		//Cria o mapa que poderá conter todas as listas de regras de negocio especifica por
		//operação do forncecedor

		Map<String, List<IStrategy>> rnsGrupo = new HashMap<String, List<IStrategy>>();

		//Adiciona a lista rnsSalvarGrupo de regras na operação salvar no mapa do Grupo que é o 
		//rnsGrupo
		//A linha abaixo varia de acordo com as regras de negocio existentes para cada grupo e 
		//operação

		rnsGrupo.put("SALVAR", rnsSalvarGrupo);
		rnsGrupo.put("ALTERAR", rnsAlterarGrupo);
		rnsGrupo.put("EXCLUIR", rnsExcluirGrupo);
		//Adiciona o mapa rnsGrupo com as regras indexadas pelas operações no mapa geral indexado
		//pelo nome da entidade

		rns.put(Grupo.class.getName(), rnsGrupo);

		//---------------------Usuario-------------------------------------
		//Criando instancias das regras de negocio a serem utilizados
		VerificaDtNascSalvarUsuario verificadtnascsalvarusuario = new VerificaDtNascSalvarUsuario();
		VerificaGrupoSalvarUsuario verificagrupousuariosalvar = new VerificaGrupoSalvarUsuario();
		VerificaEmailSalvarUsuario verificaemailsalvarusuario = new VerificaEmailSalvarUsuario();
		VerificaSenhaSalvarUsuario verificasenhasalvarusuario = new VerificaSenhaSalvarUsuario();
		VerificaEnderecoUsuario verificaenredecousuario = new VerificaEnderecoUsuario();
		
		//Criando uma lista para conter as regras de negocio de fornecedor
		List<IStrategy> rnsSalvarUsuario = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarUsuario = new ArrayList<IStrategy>();
		
		//Adicionando as regras a serem utilizadas na operação SALVAR do usuario
		rnsSalvarUsuario.add(verificadtnascsalvarusuario);
		rnsSalvarUsuario.add(verificagrupousuariosalvar);
		rnsSalvarUsuario.add(verificaemailsalvarusuario);
		rnsSalvarUsuario.add(verificasenhasalvarusuario);
		rnsSalvarUsuario.add(verificaenredecousuario);
		
		//Adicionando as regras a serem utilizadas na operação Alterar do usuario
		rnsAlterarUsuario.add(verificadtnascsalvarusuario);
		rnsAlterarUsuario.add(verificagrupousuariosalvar);
		rnsAlterarUsuario.add(verificaemailsalvarusuario);
		rnsAlterarUsuario.add(verificasenhasalvarusuario);
		rnsAlterarUsuario.add(verificaenredecousuario);
		
		//Cria o mapa que poderá conter todas as listas de regras de negocio especifica por
		//operação do forncecedor

		Map<String, List<IStrategy>> rnsUsuario = new HashMap<String, List<IStrategy>>();
		
		//Adiciona a lista rnsSalvarGrupo de regras na operação salvar no mapa do Grupo que é o 
		//rnsUsuario
		//A linha abaixo varia de acordo com as regras de negocio existentes para cada grupo e 
		//operação

		rnsUsuario.put("SALVAR", rnsSalvarUsuario);
		rnsUsuario.put("ALTERAR", rnsSalvarUsuario);
		
		//Adiciona o mapa rnsGrupo com as regras indexadas pelas operações no mapa geral indexado
		//pelo nome da entidade

		rns.put(Usuario.class.getName(), rnsUsuario);

		//--------------------Chamado------------------------------
		//Criando instancias das regras de negocio a serem utilizados
		ComplementaDataCriacao complementadatacriacao = new ComplementaDataCriacao();
		ComplementaStatus complementastatus = new ComplementaStatus();
		VerificaGrupoIgualGrupoAtt verificagrupoigualatt = new VerificaGrupoIgualGrupoAtt();
		SetaDataEncerramento setadataencerramento = new SetaDataEncerramento();
		CalculoStatusSla calculastatussla = new CalculoStatusSla();
		VerificaTamanhoTituloChamado varificatamanhotitulochamado = new VerificaTamanhoTituloChamado();
		VerificaTamanhoDescricaoChamado verificatamanhodescricaochamado = new VerificaTamanhoDescricaoChamado();
		VerificaNuloTituloChamado verificanulotitulochamado = new VerificaNuloTituloChamado();
		VerificaNuloDescricaoChamado verificanulodescricaochamado = new VerificaNuloDescricaoChamado();
		//VerificaDataAnalise verificadataanalise = new VerificaDataAnalise();
		
		//Criando uma lista para conter as regras de negocio de fornecedor
		//quando a operação for salvar - alterar e consultar
		List<IStrategy> rnsSalvarChamado = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarChamado = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarChamado = new ArrayList<IStrategy>();
		//Adicionando as regras a serem utilizadas na operação SALVAR do grupo
		rnsSalvarChamado.add(complementadatacriacao);
		rnsSalvarChamado.add(complementastatus);
		rnsSalvarChamado.add(verificagrupoigualatt);
		rnsSalvarChamado.add(varificatamanhotitulochamado);
		rnsSalvarChamado.add(verificatamanhodescricaochamado);
		rnsSalvarChamado.add(verificanulotitulochamado);
		rnsSalvarChamado.add(verificanulodescricaochamado);
		rnsAlterarChamado.add(verificagrupoigualatt);
		rnsAlterarChamado.add(setadataencerramento);
		rnsAlterarChamado.add(varificatamanhotitulochamado);
		rnsAlterarChamado.add(verificatamanhodescricaochamado);
		rnsAlterarChamado.add(verificanulotitulochamado);
		rnsAlterarChamado.add(verificanulodescricaochamado);
		rnsConsultarChamado.add(calculastatussla);
		//rnsConsultarChamado.add(verificadataanalise);
		//Cria o mapa que poderá conter todas as listas de regras de negocio especifica por
		//operação do forncecedor
		Map<String, List<IStrategy>> rnsChamado = new HashMap<String, List<IStrategy>>();
		//Adiciona a lista rnsSalvarChamado de regras na operação salvar no mapa do Chamado que é o 
		//rnsChamado
		//A linha abaixo varia de acordo com as regras de negocio existentes para cada grupo e 
		//operação
		rnsChamado.put("SALVAR", rnsSalvarChamado);
		rnsChamado.put("ALTERAR", rnsAlterarChamado);
		rnsChamado.put("CONSULTAR", rnsConsultarChamado);
		//Adiciona o mapa rnsChamado com as regras indexadas pelas operações no mapa geral indexado
		//pelo nome da entidade
		rns.put(Chamado.class.getName(), rnsChamado);
		//-------------------------ANÁLISE----------------------------------
		rns.put(Analise.class.getName(), rnsChamado);
		
		//------------------FollowUps-------------------------------------------
		//Criando instancias das regras de negocio a serem utilizados
		ComplementaDataCriacaoFollowUps complementafollowups = new ComplementaDataCriacaoFollowUps();
		//Criando uma lista para conter as regras de negocio de fornecedor
		//quando a operação for salvar - alterar e consultar
		List<IStrategy> rnsSalvarFollowUp = new ArrayList<IStrategy>();
		//Adicionando as regras a serem utilizadas na operação SALVAR do grupo
		rnsSalvarFollowUp.add(complementafollowups);
		//Cria o mapa que poderá conter todas as listas de regras de negocio especifica por
		//operação do forncecedor
		Map<String, List<IStrategy>> rnsFollowUps = new HashMap<String, List<IStrategy>>();
		//Adiciona a lista rnsSalvarChamado de regras na operação salvar no mapa do Chamado que é o 
		//rnsChamado
		//A linha abaixo varia de acordo com as regras de negocio existentes para cada grupo e 
		//operação
		rnsFollowUps.put("SALVAR", rnsSalvarFollowUp);
		//Adiciona o mapa rnsChamado com as regras indexadas pelas operações no mapa geral indexado
		//pelo nome da entidade
		rns.put(FollowUpsChamados.class.getName(), rnsFollowUps);
	}
	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		
		String msg = executarRegras(entidade, "SALVAR");							//executa as regras de negocio
		
		if(msg == null) {															//mensagem é vazio? se sim não teve erro nas regras
			IDAO dao = daos.get(nmClasse);
			try {																	//tenta executar processo de salvar
				dao.salvar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				resultado.setMsg("Não foi possivel realizar o registro!");
			}
		}
		else
		{
			resultado.setMsg(msg);
		}
		return resultado;
	}
	
	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		
		String msg = executarRegras(entidade, "ALTERAR");
		
		if(msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				dao.alterar(entidade);
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");
			}
		}
		else
		{
			resultado.setMsg(msg);
		}
		return resultado;
	}
	@Override 
	public Resultado excluir(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		
		String msg = executarRegras(entidade, "EXCLUIR");
		
		if(msg == null) {
			IDAO dao = daos.get(nmClasse);
			try {
				dao.excluir(entidade);								//chamada do metodo excluir
				List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			}
			catch (SQLException e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possivel realizar o registro!");
			}
		}
		else
		{
			resultado.setMsg(msg);
		}
		return resultado;
	}
	
	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		
		String msg = null;//executarRegras(entidade, "CONSULTAR");	
		IDAO dao = daos.get(nmClasse);
		try {
			resultado.setEntidades(dao.consultar(entidade));
			for(EntidadeDominio e : resultado.getEntidades())
			{	
				msg = executarRegras(e, "CONSULTAR");
			}
			if(msg != null)
			{
				resultado.setMsg(msg);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			resultado.setMsg("Não foi possivel realizar a consulta!");
		}
		return resultado;
	}
	
	public Resultado visualizar(EntidadeDominio entidade) {
		resultado = new Resultado();
		resultado.setEntidades(new ArrayList<EntidadeDominio>(1));
		resultado.getEntidades().add(entidade);
		return resultado;
	}
	
	
	private String executarRegras(EntidadeDominio entidade, String operacao) {
		String nmClasse = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();

		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);		//verifica de acordo com o nome da entidade o mapa de lista de regras de negocio de acordo com a operação

		if(regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);				//verifica de acordo com a operação as regras de negocio da classe daquela operação

			if(regras != null) {												//se existem regras
				for(IStrategy s : regras) {										//percorre as regras da lista com o for
					// for(int i = 0; i < regras.length; i++)
					// IStrategy s = regras.get(i);
					String m = s.processar(entidade);							//executa na strategy corrente, passando a entidade que veio do servlet e será testada
					if(m != null)												//se possui mensagem, significa erro!
					{
						msg.append(m);											//adiciona mensagem a string
						msg.append("\n");										
					}
				}
			}		
		}
		if(msg.length()>0)														//se tamanho da string for maior que 0
		{
			return msg.toString();												//converte msg para string e retorna				
		}
		else
			return null;

	}
}
