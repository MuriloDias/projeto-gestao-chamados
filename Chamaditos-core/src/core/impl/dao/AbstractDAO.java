package core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.IDAO;
import core.util.Conexao;
import dominio.EntidadeDominio;

public abstract class AbstractDAO implements IDAO{

	protected Connection connection;
	protected String table;
	protected String idTable;
	protected boolean ctrlTransaction=true;
	
	public AbstractDAO( Connection connection, String table, String idTable) {
		// TODO Auto-generated constructor stub
		this.table = table;
		this.idTable = idTable;
		this.connection = connection;
	}
	
	protected AbstractDAO(String table, String idTable) {
		// TODO Auto-generated constructor stub
		this.table = table;
		this.idTable = idTable;
	}
	
	//----------------------override mathods-----------------------------
	
	@Override
	public void excluir(EntidadeDominio entidade) {
		openConnection();
		PreparedStatement pst=null;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM " + table + " WHERE " + idTable + "=?");
		
		/*sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idTable);
		sb.append("=");
		sb.append("?");*/
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			pst.setInt(1, entidade.getId());
			
			pst.executeUpdate();
			connection.commit();
		}catch (SQLException e) {
			try {
				connection.rollback();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			try {
				pst.close();
				if(ctrlTransaction)
					connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//metodo para abertura da conexão
	protected void 	openConnection(){
		try {
				if(connection == null || connection.isClosed()){
					connection = Conexao.getConnection();
				}
			
			}
		//exibe erro
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//exibe erro
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}





























