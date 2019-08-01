package core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.ConverteDate;
import dominio.Analise;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.FollowUpsChamados;
import dominio.Grupo;
import dominio.TipoDeServico;
import dominio.Usuario;

public class AnaliseDAO extends AbstractDAO {

	public AnaliseDAO() {
		super("tb_chamado", "id");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst=null;
		Analise analise = (Analise)entidade;
		Chamado chamado = analise.getChamado();
		
		//PARTE DAS CONSULTAS
		//preparação do SQL para os inserts
		String sql = "SELECT *FROM tb_chamado WHERE";
		
		if(chamado.getStatus() != null && !chamado.getStatus().trim().equals("")) {						//status para ser buscado
			sql += " status = ? AND";
		}
		if(chamado.getGrupoAtribuido() != null && chamado.getGrupoAtribuido().getId() != null) {		//grupo que será buscado os chamados
			sql += " grupoatribuido = ? AND";
		}
		if(chamado.getDataCriacao() != null) {															//obj data criação usado para pesquisar no banco chamados a partir desse periodo
			sql += " datacriacao >= ? AND";
		}
		//NOTE QUE O SINAL DA QUERY DE DATACRIAÇÃO E DATAENCERRAMENTO SÃO DIFERENTES
		//SELECT * FROM tb_chamado WHERE datacriacao > 'dd-mm-yyyy' and datacriacao < 'dd-mm-yyyy' order by datacriacao asc;
		if(chamado.getDataEncerramento() != null) {														//obj data encerramento usado para pesquisar no banco chamados até desse periodo
			sql += " datacriacao <= (?::date + '1 day'::interval) AND";
			//sql += " datacriacao <= ? AND"; <- não funciona no banco por motivo desconhecido, esse trecho de query não busca os chamados do dia de hoje
			//e os operadores de <= ou between não funciona
		}
		//if(chamado.getId() != null)
		if(sql.endsWith(" AND"))								//se o final do sql houve um "AND"
		{
			sql = sql.substring(0, sql.length() - 4);			//pega o mapa de char da string e remove as 4 ultimas letras, concatena com o ";" e salva no sql
		}
		else													//se não houver alteração no sql pelos IFs
		{
			sql = sql.substring(0, sql.length() - 6);			//pega o mapa de char da string e remove as 6 ultimas letras, concatena com o ";" e salva no sql
		}
		 sql += " order by datacriacao asc;";
		//------------------------------PST-----------------------------------------
		try {
			pst = connection.prepareStatement(sql);
			int i = 1;
			if(chamado.getStatus() != null && !chamado.getStatus().trim().equals("")) {
				pst.setString(i, chamado.getStatus());
				i++;
			}
			if(chamado.getGrupoAtribuido() != null && chamado.getGrupoAtribuido().getId() != null) {
				pst.setInt(i, chamado.getGrupoAtribuido().getId());
				i++;
			}
			if(chamado.getDataCriacao() != null) {
				pst.setTimestamp(i, new java.sql.Timestamp(chamado.getDataCriacao().getTime()));
				i++;
			}
			if(chamado.getDataEncerramento() != null) {
				pst.setTimestamp(i, new java.sql.Timestamp(chamado.getDataEncerramento().getTime()));
				i++;
			}
			ResultSet rs = pst.executeQuery(); 					//classe do java que trata os resultados de uma query slq
			List<EntidadeDominio> chamados = new ArrayList<EntidadeDominio>();
			while(rs.next())
			{
				Chamado c = new Chamado();
				c.setId(rs.getInt("id"));
				c.setTitulo(rs.getString("titulo"));
				c.setDescricao(rs.getString("descricao"));
				c.setStatus(rs.getString("status"));
							
				java.sql.Timestamp sDate = rs.getTimestamp("datacriacao");
				long tempo = sDate.getTime();
				java.util.Date uDate = new Date(tempo);
				c.setDataCriacao(uDate);
				
				//teste para saber se o timestamp era vazio no banco de dados, pois o chamado não está encerrado ainda
				sDate = rs.getTimestamp("dataencerramento");
				if(sDate != null)
				{
					tempo = sDate.getTime();
					uDate = new Date(tempo);
					c.setDataEncerramento(uDate);
				}

				//tipo de serviço
				TipoDeServico tipo = new TipoDeServico();					//Cria variavel local endereco do tipo Endereco
				tipo.setId(rs.getInt("tipodeservico"));						//seta no endereco o ID que trouxe do banco pelo RS
				TipoDeServicoDAO daoT = new TipoDeServicoDAO();				//Cria variavel local dao do tipo EnderecoDAO
				List<EntidadeDominio> listT = daoT.consultar(tipo);			//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				tipo = (TipoDeServico) listT.get(0);						//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				c.setTipoDeServico(tipo);									//seta o endereco inteiro dentro do objeto de endereco no usuario
				
				//somar data de inicio com o tempo de resolução do chamado para obter a data estimada
				c.setDtEstimada(ConverteDate.somaMinutosDate(c.getTipoDeServico().getTempoEstimado(), c.getDataCriacao()));
				
				chamados.add(c);
		}
			try
			{
				pst.close();
				connection.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return chamados;
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
