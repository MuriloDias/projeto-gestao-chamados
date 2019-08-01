package core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.aplicacao.Resultado;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.FollowUpsChamados;
import dominio.Grupo;
import dominio.Usuario;

public class FollowUpsDAO extends AbstractDAO {

	public FollowUpsDAO() {
		super("tb_followups", "id");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst=null;
		FollowUpsChamados followup = (FollowUpsChamados)entidade;
		
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_followups (followups, proprietario, datacriacao, chamado) VALUES (?,?,?,?)");

			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, followup.getFollowUps());
			pst.setInt(2, followup.getProprietario().getId());
			pst.setTimestamp(3, new java.sql.Timestamp(followup.getDataCriacao().getTime()));
			pst.setInt(4, followup.getCodigoChamado().getId());
			//followup.getCodigoChamado().getId()
			pst.executeUpdate();
			//Pegando o ID do chamado cadastrado para retornar ao DAO de FollowUps
			ResultSet rs = pst.getGeneratedKeys();
		    int id = 0;
		    while(rs.next())
		    {
			id = rs.getInt(1);
		    }
		    followup.setId(id);
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

	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst=null;
		FollowUpsChamados follow = (FollowUpsChamados)entidade;
		
		//PARTE DAS CONSULTAS
		//preparação do SQL para os inserts
		String sql = "SELECT *FROM tb_followups WHERE";
		if(follow.getId() != null)
		{
			sql += " id = ? AND";
		}
		/*if(follow.getProprietario() != null && follow.getProprietario().getId() != null) {
			sql += " proprietario = ? AND";		
		}*/
		if(follow.getCodigoChamado() != null && follow.getCodigoChamado().getId() != null) {
			sql += " chamado = ? AND";		
		}
		//if(chamado.getId() != null)
		if(sql.endsWith(" AND"))								//se o final do sql houve um "AND"
		{
			sql = sql.substring(0, sql.length() - 4);		//pega o mapa de char da string e remove as 4 ultimas letras, concatena com o ";" e salva no sql
		}
		else													//se não houver alteração no sql pelos IFs
		{
			sql = sql.substring(0, sql.length() - 6);		//pega o mapa de char da string e remove as 6 ultimas letras, concatena com o ";" e salva no sql
		}
		 sql += " order by datacriacao desc ;";
		//------------------------------PST-----------------------------------------
		try {
			pst = connection.prepareStatement(sql);
			int i = 1;
			if(follow.getId() != null)
			{
				pst.setInt(i, follow.getId());
				i++;
			}
//			if(follow.getProprietario() != null && follow.getProprietario().getId() != null) {
//				pst.setInt(i, follow.getProprietario().getId());
//				i++;		
//			}
			if(follow.getCodigoChamado() != null && follow.getCodigoChamado().getId() != null) {
				pst.setInt(i,follow.getCodigoChamado().getId());
				i++;	
			}
			//------------------------------tratamento do retorno do metodo------------------------
			ResultSet rs = pst.executeQuery(); 					//classe do java que trata os resultados de uma query slq
			List<EntidadeDominio> follows = new ArrayList<EntidadeDominio>();
			while(rs.next())
			{
				FollowUpsChamados f = new FollowUpsChamados();
				f.setId(rs.getInt("id"));
				f.setFollowUps(rs.getString("followups"));
				
				
				java.sql.Timestamp sDate = rs.getTimestamp("datacriacao");
				long tempo = sDate.getTime();
				java.util.Date uDate = new Date(tempo);
				f.setDataCriacao(uDate);
				
				//codigo chamado
				Chamado chamado = new Chamado();
				chamado.setId(rs.getInt("chamado"));
				f.setCodigoChamado(chamado);
				
				//proprietário
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("proprietario"));
				UsuarioDAO daoU = new UsuarioDAO();
				List<EntidadeDominio> listF = daoU.consultar(usuario);
				usuario = (Usuario) listF.get(0);
				f.setProprietario(usuario);
				
				follows.add(f);
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
			return follows;	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
