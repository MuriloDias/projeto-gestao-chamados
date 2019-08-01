package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import dominio.Grupo;
import dominio.TipoDeServico;

public class TipoDeServicoDAO extends AbstractDAO {

	public TipoDeServicoDAO() {
		super("tb_tipodeservico", "id");
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
		PreparedStatement pst = null;
		TipoDeServico tipodeservico = (TipoDeServico)entidade; 
		//------------------------------SQL---------------------------------
		String sql = "SELECT * FROM tb_tipodeservico WHERE"; 			//inicio da string que será usada no banco
		if(tipodeservico.getId() !=  null)
		{
			sql += " id = ? AND";			
		}
		if(sql.endsWith(" AND"))										//se o final do sql houve um "AND"
		{
			sql = sql.substring(0, sql.length() - 4);					//pega o mapa de char da string e remove as 4 ultimas letras, concatena com o ";" e salva no sql
		}
		else															//se não houver alteração no sql pelos IFs
		{
			sql = sql.substring(0, sql.length() - 6);					//pega o mapa de char da string e remove as 6 ultimas letras, concatena com o ";" e salva no sql
		}
		sql += " ORDER BY grupo;";
		//-------------------------PST--------------------------------------
		try {
			pst = connection.prepareStatement(sql);
			int i = 1;
			if(tipodeservico.getId() != null)
			{
				pst.setInt(i, tipodeservico.getId());
				i++;
			}
			//---------------------------Tratamento do Retorno do metodo---------------------
			ResultSet rs = pst.executeQuery(); 					//classe do java que trata os resultados de uma query slq
			List<EntidadeDominio> tipodeservicos = new ArrayList<EntidadeDominio>();
			while(rs.next())
			{
				TipoDeServico t = new TipoDeServico();
				t.setId(rs.getInt("id"));
				t.setNome(rs.getString("nome"));
				t.setSeveridade(rs.getInt("severidade"));
				t.setUrgencia(rs.getInt("urgencia"));
				t.setTempoEstimado(Integer.parseInt(rs.getString("tempoestimado")));

				//------------grupo---------------------------------------------------
				Grupo grupo = new Grupo();							//Cria variavel local endereco do tipo Endereco
				grupo.setId(rs.getInt("grupo"));					//seta no endereco o ID que trouxe do banco pelo RS
				GrupoDAO daoG = new GrupoDAO();						//Cria variavel local dao do tipo EnderecoDAO
				List<EntidadeDominio> listG = daoG.consultar(grupo);//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				grupo = (Grupo) listG.get(0);						//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				t.setGrupo(grupo);									//seta o endereco inteiro dentro do objeto de endereco no usuario
				
				
				tipodeservicos.add(t);
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
			return tipodeservicos;
		}

		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
