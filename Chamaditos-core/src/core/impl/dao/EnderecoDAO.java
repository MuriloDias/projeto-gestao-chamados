package core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class EnderecoDAO extends AbstractDAO {

	protected EnderecoDAO() {
		super("tb_endereco", "id");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		Endereco endereco = (Endereco)entidade;
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_endereco (rua, cidade, bairro, estado, cep, complemento, numero) VALUES (?,?,?,?,?,?,?)");

			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, endereco.getRua());
			pst.setString(2, endereco.getCidade());
			pst.setString(3, endereco.getBairro());
			pst.setString(4, endereco.getEstado());
			pst.setString(5, endereco.getEstado());
			pst.setString(6, endereco.getComplemento());
			pst.setString(7, endereco.getNumero());
			
			pst.executeUpdate();
			//Pegando o ID do enderco cadastrado para retornar ao DAO de usuário
			ResultSet rs = pst.getGeneratedKeys();
		    int id = 0;
		    while(rs.next())
		    {
			id = rs.getInt(1);
		    }
		    endereco.setId(id);

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

		openConnection();
		PreparedStatement pst = null;
		Endereco endereco = (Endereco)entidade;
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_endereco set rua=?, cidade=?, bairro=?, estado=?, cep=?, complemento=?, numero=? WHERE id=?;");

			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, endereco.getRua());
			pst.setString(2, endereco.getCidade());
			pst.setString(3, endereco.getBairro());
			pst.setString(4, endereco.getEstado());
			pst.setString(5, endereco.getEstado());
			pst.setString(6, endereco.getComplemento());
			pst.setString(7, endereco.getNumero());
			pst.setInt(8, endereco.getId());
			
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

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst = null;
		Endereco endereco = (Endereco)entidade;
		//------------------------------SQL---------------------------------------
		String sql = "SELECT *FROM tb_endereco WHERE";
		if(endereco.getId() != null)
		{
			sql += " id = ? AND";
		}
		if(endereco.getRua() != null && !endereco.getRua().trim().equals("")) {
			sql += " rua = ? AND";
		}
		if(endereco.getCidade() != null && !endereco.getCidade().trim().equals("")) {
			sql += " cidade = ? AND";
		}
		if(endereco.getBairro() != null && !endereco.getBairro().trim().equals("")) {
			sql += " bairro = ? AND";
		}
		if(endereco.getEstado() != null && !endereco.getEstado().trim().equals("")) {
			sql += " estado = ? AND";
		}
		if(endereco.getCep() != null && !endereco.getCep().trim().equals("")) {
			sql += " cep = ? AND";
		}
		if(endereco.getComplemento() != null && !endereco.getComplemento().trim().equals("")) {
			sql += " complemento = ? AND";
		}
		if(endereco.getNumero() != null && !endereco.getNumero().trim().equals("")) {
			sql += " numero = ? AND";
		}
		if(sql.endsWith(" AND"))								//se o final do sql houve um "AND"
		{
			sql = sql.substring(0, sql.length() - 4) + ";";		//pega o mapa de char da string e remove as 4 ultimas letras, concatena com o ";" e salva no sql
		}
		else													//se não houver alteração no sql pelos IFs
		{
			sql = sql.substring(0, sql.length() - 6) + ";";		//pega o mapa de char da string e remove as 6 ultimas letras, concatena com o ";" e salva no sql
		}
		//--------------------------------PST---------------------------------------
		try {
			pst = connection.prepareStatement(sql);
			int i = 1;
			if(endereco.getId() != null)
			{
				pst.setInt(i, endereco.getId());
				i++;
			}
			if(endereco.getRua() != null && !endereco.getRua().trim().equals("")) {
				pst.setString(i, endereco.getRua());
				i++;
			}
			if(endereco.getCidade() != null && !endereco.getCidade().trim().equals("")) {
				pst.setString(i, endereco.getCidade());
				i++;
			}
			if(endereco.getBairro() != null && !endereco.getBairro().trim().equals("")) {
				pst.setString(i, endereco.getBairro());
				i++;
			}
			if(endereco.getEstado() != null && !endereco.getEstado().trim().equals("")) {
				pst.setString(i, endereco.getEstado());
				i++;
			}
			if(endereco.getCep() != null && !endereco.getCep().trim().equals("")) {
				pst.setString(i, endereco.getCep());
				i++;
			}
			if(endereco.getComplemento() != null && !endereco.getComplemento().trim().equals("")) {
				pst.setString(i, endereco.getComplemento());
				i++;
			}
			if(endereco.getNumero() != null && !endereco.getNumero().trim().equals("")) {
				pst.setString(i, endereco.getNumero());
				i++;
			}
			//-------------------------Tratamento de retorno de metodo-----------------------------------------
			ResultSet rs = pst.executeQuery(); 					//classe do java que trata os resultados de uma query slq
			List<EntidadeDominio> enderecos = new ArrayList<EntidadeDominio>();
			while(rs.next())
			{
				Endereco e = new Endereco();
				e.setId(rs.getInt("id"));
				e.setRua(rs.getString("rua"));
				e.setCidade(rs.getString("cidade"));
				e.setBairro(rs.getString("bairro"));
				e.setEstado(rs.getString("estado"));
				e.setCep(rs.getString("cep"));
				e.setComplemento(rs.getString("complemento"));
				e.setNumero(rs.getString("numero"));
				enderecos.add(e);
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
			return enderecos;			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}		
		return null;
	}

}
