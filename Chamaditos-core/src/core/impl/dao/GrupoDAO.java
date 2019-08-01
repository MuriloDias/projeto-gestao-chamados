package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import dominio.Grupo;
import dominio.Usuario;

public class GrupoDAO extends AbstractDAO{

	public GrupoDAO() {
		//Passagem de Strings para o construtor do AbstractDAO
		//super("<nome da tabela>","<titulo da coluna(campo) da tabela>")
		super("tb_grupo", "id");
	}

	public void salvar(EntidadeDominio entidade) {
		openConnection();
		PreparedStatement pst=null;
		Grupo grupo = (Grupo)entidade;

		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_grupo (nome, desativado) VALUES (?, false)");

			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, grupo.getNomeGrupo());
			pst.executeUpdate();
			//Pegando o ID do enderco cadastrado para retornar ao DAO de usuário
			ResultSet rs = pst.getGeneratedKeys();
		    int id = 0;
		    while(rs.next())
		    {
			id = rs.getInt(1);
		    }
		    grupo.setId(id);
			
			connection.commit();			
		}
		catch(SQLException e) {
			try {
				connection.rollback();
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			try {
				pst.close();
				connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		// UPDATE <nome Tabela> SET <nome campo> = ?, <nome campo2> = ?, ...
		openConnection();
		PreparedStatement pst = null;
		Grupo grupo = (Grupo)entidade;
		try{ 								//tenta atualizar os dados alterados
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_grupo SET nome=? WHERE id=?;");
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, grupo.getNomeGrupo());
			pst.setInt(2, grupo.getId());
			pst.executeUpdate();
			connection.commit();
		}
		catch(SQLException e) { 			//não conseguiu atualizar?
			try {							//tenta executar rollback
				connection.rollback();
			}
			catch(SQLException e1)			//não conseguiu dar rollback
			{
				e1.printStackTrace();		//printa erro do rollback
			}
			e.printStackTrace();			//não conseguiu atualizar
		}
		finally 
		{
			try {							//tenta fechar conexão e fechar PST
				pst.close();				
				connection.close();
			}
			catch(SQLException e) {			//não conseguiu fechar?
				e.printStackTrace();		//printa problema dos closes
			}
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		Grupo grupo = (Grupo)entidade;
		//------------------------------SQL---------------------------------
		String sql = "SELECT * FROM tb_grupo WHERE"; 			//inicio da string que será usada no banco
		if(grupo.getId() != null)								
		{
			sql += " id = ? AND";
		}
		if(grupo.getNomeGrupo() != null && !grupo.getNomeGrupo().trim().equals("")) {
			sql += " nome = ? AND";
		}
		if(sql.endsWith(" AND"))								//se o final do sql houve um "AND"
		{
			sql = sql.substring(0, sql.length() - 4) + ";";		//pega o mapa de char da string e remove as 4 ultimas letras, concatena com o ";" e salva no sql
		}
		else													//se não houver alteração no sql pelos IFs
		{
			sql = sql.substring(0, sql.length() - 6) + ";";		//pega o mapa de char da string e remove as 6 ultimas letras, concatena com o ";" e salva no sql
		}
		//-------------------------PST--------------------------------------
		try {
			pst = connection.prepareStatement(sql);
			int i = 1;
			if(grupo.getId() != null)
			{
				pst.setInt(i,  grupo.getId());
				i++;
			}
			if(grupo.getNomeGrupo() != null && !grupo.getNomeGrupo().trim().equals("")) 
			{
				pst.setString(i, grupo.getNomeGrupo());
				i++;
			}
			//---------------------------Tratamento do Retorno do metodo---------------------
			ResultSet rs = pst.executeQuery(); 					//classe do java que trata os resultados de uma query slq
			List<EntidadeDominio> grupos = new ArrayList<EntidadeDominio>();
			while(rs.next())
			{
				Grupo g = new Grupo();
				g.setId(rs.getInt("id"));
				g.setNomeGrupo(rs.getString("nome"));
				g.setDesativado(rs.getBoolean("desativado"));
				grupos.add(g);
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
			return grupos;
		}

		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
}

	public void excluir(EntidadeDominio entidade) {
		
	    Grupo grupo = (Grupo) entidade;	    
	    openConnection();
		PreparedStatement pst=null;
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_grupo set desativado=true WHERE id=?;");

			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, grupo.getId());
			
			pst.executeUpdate();
			connection.commit();			
		}
		catch(SQLException e) {
			try {
				connection.rollback();
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			try {
				pst.close();
				connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}






































