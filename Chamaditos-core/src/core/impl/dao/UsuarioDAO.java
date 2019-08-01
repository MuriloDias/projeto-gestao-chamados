package core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.util.EmailSend;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Grupo;
import dominio.Usuario;


public class UsuarioDAO extends AbstractDAO{

	public UsuarioDAO() {
		super("tb_usuario", "id");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		openConnection();
		PreparedStatement pst=null;
		Usuario usuario = (Usuario)entidade;

		//chamada do metodo para salvar endereço e obter ID do endereço
		EnderecoDAO dao = new EnderecoDAO();
		dao.salvar(usuario.getEndereco());
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_usuario (nome, dtnascimento, genero, email, senha, grupo, endereco, sobrenome, desativado) VALUES (?,?,?,?,?,?,?,?, false)");

			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, usuario.getNome());
			pst.setDate(2, new java.sql.Date (usuario.getDtnascimento().getTime()));
			pst.setString(3, usuario.getGenero());
			pst.setString(4, usuario.getEmail());
			pst.setString(5, usuario.getSenha());
			pst.setInt(6, usuario.getGrupo().getId());
			pst.setInt(7, usuario.getEndereco().getId());
			pst.setString(8, usuario.getSobrenome());
			pst.executeUpdate();
			//Pegando o ID do enderco cadastrado para retornar ao DAO de usuário
			ResultSet rs = pst.getGeneratedKeys();
		    int id = 0;
		    while(rs.next())
		    {
			id = rs.getInt(1);
		    }
		    usuario.setId(id);
			
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
		PreparedStatement pst=null;
		Usuario usuario = (Usuario)entidade;

		//chamada do metodo para salvar endereço e obter ID do endereço
		EnderecoDAO dao = new EnderecoDAO();
		dao.alterar(usuario.getEndereco());
		
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_usuario set nome=?,sobrenome=?, dtnascimento=?, genero=?, email=?, senha=?, grupo=?, endereco=? WHERE id=?;");

			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getSobrenome());
			pst.setDate(3, new java.sql.Date (usuario.getDtnascimento().getTime()));
			pst.setString(4, usuario.getGenero());
			pst.setString(5, usuario.getEmail());
			pst.setString(6, usuario.getSenha());
			pst.setInt(7, usuario.getGrupo().getId());
			pst.setInt(8, usuario.getEndereco().getId());
			pst.setInt(9, usuario.getId());
			
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
		Usuario usuario = (Usuario)entidade;
		
	
		//------------------------------SQL---------------------------------------
		String sql = "SELECT *FROM tb_usuario WHERE";
		if(usuario.getId() != null)
		{
			sql += " id = ? AND";
		}
		if(usuario.getNome() != null && !usuario.getNome().trim().equals("")) {
			sql += " nome = ? AND";
		}
		if(usuario.getSobrenome() != null && !usuario.getSobrenome().trim().equals("")) {
			sql += " sobrenome = ? AND";
		}
		if(usuario.getDtnascimento() != null) {
			sql += " dtnascimento = ? AND";
		}
		if(usuario.getGenero() != null && !usuario.getGenero().trim().equals("")) {
			sql += " genero = ? AND";
		}
		if(usuario.getEmail() != null && !usuario.getEmail().trim().equals("")) {
			sql += " email = ? AND";
		}
		if(usuario.getSenha() != null && !usuario.getSenha().trim().equals("")) {
			sql += " senha = ? AND";
		}
		//grupo
		if(usuario.getGrupo() != null && usuario.getGrupo().getId() != null) {
			sql += " grupo = ? AND";
		}
		//endereço
		if(usuario.getEndereco() != null && usuario.getEndereco().getId() != null) {
			sql += " endereco = ? AND";
		}
		if(sql.endsWith(" AND"))								//se o final do sql houve um "AND"
		{
			sql = sql.substring(0, sql.length() - 4) + ";";		//pega o mapa de char da string e remove as 4 ultimas letras, concatena com o ";" e salva no sql
		}
		else													//se não houver alteração no sql pelos IFs
		{
			sql = sql.substring(0, sql.length() - 6) + ";";		//pega o mapa de char da string e remove as 6 ultimas letras, concatena com o ";" e salva no sql
		}
		
		//------------------------------PST-----------------------------------------
		try {
			pst = connection.prepareStatement(sql);
			int i = 1;
			if(usuario.getId() != null)
			{
				pst.setInt(i, usuario.getId());
				i++;
			}
			if(usuario.getNome() != null && !usuario.getNome().trim().equals("")) {
				pst.setString(i, usuario.getNome());
				i++;
			}
			if(usuario.getSobrenome() != null && !usuario.getSobrenome().trim().equals("")) {
				pst.setString(i, usuario.getSobrenome());
				i++;
			}
			if(usuario.getDtnascimento() != null) {
				pst.setDate(i, new java.sql.Date (usuario.getDtnascimento().getTime()));
				i++;
			}
			if(usuario.getGenero() != null && !usuario.getGenero().trim().equals("")) {
				pst.setString(i, usuario.getGenero());
				i++;
			}
			if(usuario.getEmail() != null && !usuario.getEmail().trim().equals("")) {
				pst.setString(i, usuario.getEmail());
				i++;
			}
			if(usuario.getSenha() != null && !usuario.getSenha().trim().equals("")) {
				pst.setString(i, usuario.getSenha());
				i++;
			}
			//grupo
			if(usuario.getGrupo() != null && usuario.getGrupo().getId() != null) {
				pst.setInt(i, usuario.getGrupo().getId());
				i++;
			}
			//endereço
			if(usuario.getEndereco() != null && usuario.getEndereco().getId() != null) {
				pst.setInt(i, usuario.getEndereco().getId());
				i++;
			}
			//------------------------------tratamento do retorno do metodo------------------------
			ResultSet rs = pst.executeQuery(); 					//classe do java que trata os resultados de uma query slq
			List<EntidadeDominio> usuarios = new ArrayList<EntidadeDominio>();
			while(rs.next())
			{
				Usuario u = new Usuario();
				u.setId(rs.getInt("id"));
				u.setNome(rs.getString("nome"));
				u.setSobrenome(rs.getString("sobrenome"));
				u.setDesativado(rs.getBoolean("desativado"));
				
				//u.setDtnascimento(new Date (rs.getDate("dtnascimento").getTime()));				
				java.sql.Date sDate = rs.getDate("dtnascimento");
				long tempo = sDate.getTime();
				java.util.Date uDate = new Date(tempo);
				u.setDtnascimento(uDate);
				
				u.setGenero(rs.getString("genero"));
				u.setEmail(rs.getString("email"));
				u.setSenha(rs.getString("senha"));
				
				//---------------endereco
				Endereco endereco = new Endereco();					//Cria variavel local endereco do tipo Endereco
				endereco.setId(rs.getInt("endereco"));				//seta no endereco o ID que trouxe do banco pelo RS
				EnderecoDAO daoE = new EnderecoDAO();				//Cria variavel local dao do tipo EnderecoDAO
				List<EntidadeDominio> listE = daoE.consultar(endereco);//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				endereco = (Endereco) listE.get(0);					//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				u.setEndereco(endereco);							//seta o endereco inteiro dentro do objeto de endereco no usuario
				//------------grupo
				Grupo grupo = new Grupo();							//Cria variavel local endereco do tipo Endereco
				grupo.setId(rs.getInt("grupo"));					//seta no endereco o ID que trouxe do banco pelo RS
				GrupoDAO daoG = new GrupoDAO();						//Cria variavel local dao do tipo EnderecoDAO
				List<EntidadeDominio> listG = daoG.consultar(grupo);//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				grupo = (Grupo) listG.get(0);						//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				u.setGrupo(grupo);									//seta o endereco inteiro dentro do objeto de endereco no usuario
				
				
				
				usuarios.add(u);
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
			return usuarios;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	public void excluir(EntidadeDominio entidade) {
		
	    Usuario usuario = (Usuario) entidade;	    
	    openConnection();
		PreparedStatement pst=null;
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_usuario set desativado=true WHERE id=?;");

			pst = connection.prepareStatement(sql.toString());
			pst.setInt(1, usuario.getId());
			
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
