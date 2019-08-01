package core.impl.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import core.util.ConverteDate;
import core.util.Email;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.FollowUpsChamados;
import dominio.Grupo;
import dominio.TipoDeServico;
import dominio.Usuario;

public class ChamadoDAO extends AbstractDAO {

	
	public ChamadoDAO() {
		super("tb_chamado", "id");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
		
		openConnection();
		PreparedStatement pst=null;
		Chamado chamado = (Chamado)entidade;
		Email email = new Email();
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_chamado (titulo, descricao, grupoatribuido, grupoautor, usuarioautor, tipodeservico, datacriacao, status) VALUES (?,?,?,?,?,?,?,?)");
			
			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, chamado.getTitulo());
			pst.setString(2, chamado.getDescricao());
			pst.setInt(3, chamado.getGrupoAtribuido().getId());
			pst.setInt(4, chamado.getGrupoAutor().getId());
			pst.setInt(5, chamado.getUsuarioAutor().getId());
			pst.setInt(6, chamado.getTipoDeServico().getId());
			pst.setTimestamp(7, new java.sql.Timestamp(chamado.getDataCriacao().getTime()));
			pst.setString(8, chamado.getStatus());
			pst.executeUpdate();
			//Pegando o ID do enderco cadastrado para retornar ao DAO de usuário
			ResultSet rs = pst.getGeneratedKeys();
		    int id = 0;
		    while(rs.next())
		    {
		    	id = rs.getInt(1);
		    }
			chamado.setId(id);
			connection.commit();
			
			//-----------------------------------------------tratamento do email--------------------------------------------------
			//usuários do grupo atribuido
			Usuario usuario = new Usuario();
			usuario.setGrupo(chamado.getGrupoAtribuido());
			UsuarioDAO daoU = new UsuarioDAO();
			List<EntidadeDominio> listU = daoU.consultar(usuario);
			//obj do usuario logado
			Usuario sessionated = chamado.getUsuarioAutor();
			email.formular(listU, sessionated, chamado, "criou um chamado");
			
			
			
		}
		catch(SQLException e) {
			try {
				connection.rollback();
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}catch(Exception x) {
			System.out.println(x.getMessage());
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
		Chamado chamado = (Chamado)entidade;
			
		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_chamado set titulo=?, descricao=?, grupoatribuido=?, tipodeservico=?, status=?, dataencerramento=? WHERE id=?;");
			
			pst = connection.prepareStatement(sql.toString());
			pst.setString(1, chamado.getTitulo());
			pst.setString(2, chamado.getDescricao());
			pst.setInt(3, chamado.getGrupoAtribuido().getId());
			pst.setInt(4, chamado.getTipoDeServico().getId());
			pst.setString(5, chamado.getStatus());
			Timestamp timestamp;
			if(chamado.getDataEncerramento() != null)
			{
				timestamp = new java.sql.Timestamp(chamado.getDataEncerramento().getTime());	
			}
			else
			{
				timestamp = null;
			}
			pst.setTimestamp(6, timestamp);					
			pst.setInt(7, chamado.getId());
			
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
		PreparedStatement pst=null;
		Chamado chamado = (Chamado)entidade;
		
		//PARTE DAS CONSULTAS
		//preparação do SQL para os inserts
		String sql = "SELECT *FROM tb_chamado WHERE";
		if(chamado.getId() != null)
		{
			sql += " id = ? AND";
		}
		if(chamado.getTitulo() != null && !chamado.getTitulo().trim().equals("")) {
			sql += " titulo = ? AND";
		}
		if(chamado.getDescricao() != null && !chamado.getDescricao().trim().equals("")) {
			sql += " descricao = ? AND";
		}
		if(chamado.getGrupoAtribuido().getId() == chamado.getGrupoAutor().getId()) {
		//if(chamado.getGrupoAtribuido().getNomeGrupo() == chamado.getGrupoAutor().getNomeGrupo()) {
			sql += " (grupoautor = ? or grupoatribuido = ?) AND";
		}
		else {
			//grupo att
			if(chamado.getGrupoAtribuido() != null && chamado.getGrupoAtribuido().getId() != null) {
				sql += " grupoatribuido = ? AND";
			}
			//grupo autor
			if(chamado.getGrupoAutor() != null && chamado.getGrupoAutor().getId() != null) {
				sql += " grupoautor = ? AND";
			}
		}
		//if(chamado.getId() != null)
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
			if(chamado.getId() != null)
			{
				pst.setInt(i, chamado.getId());
				i++;
			}
			if(chamado.getTitulo() != null && !chamado.getTitulo().trim().equals("")) {
				pst.setString(i, chamado.getTitulo());
				i++;
			}
			if(chamado.getDescricao() != null && !chamado.getDescricao().trim().equals("")) {
				pst.setString(i, chamado.getDescricao());
				i++;
			}
			
			if(chamado.getGrupoAtribuido().getId() == chamado.getGrupoAutor().getId()) {
				pst.setInt(i, chamado.getGrupoAtribuido().getId());
				i++;
				pst.setInt(i, chamado.getGrupoAutor().getId());
				i++;
			}
			else {
				if(chamado.getGrupoAtribuido() != null && chamado.getGrupoAtribuido().getId() != null) {
					pst.setInt(i, chamado.getGrupoAtribuido().getId());
					i++;
				}
				if(chamado.getGrupoAutor() != null && chamado.getGrupoAutor().getId() != null) {
					pst.setInt(i, chamado.getGrupoAutor().getId());
					i++;
				}
			}
			
			

			//------------------------------tratamento do retorno do metodo------------------------
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

				
				//grupo Atribuido
				Grupo grupo = new Grupo();									//Cria variavel local endereco do tipo Endereco
				grupo.setId(rs.getInt("grupoatribuido"));					//seta no endereco o ID que trouxe do banco pelo RS
				GrupoDAO daoG = new GrupoDAO();								//Cria variavel local dao do tipo EnderecoDAO
				List<EntidadeDominio> listG = daoG.consultar(grupo);		//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				grupo = (Grupo) listG.get(0);								//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				c.setGrupoAtribuido(grupo);									//seta o endereco inteiro dentro do objeto de endereco no usuario
				
				//usuário autor
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("usuarioautor"));
				UsuarioDAO daoU = new UsuarioDAO();
				List<EntidadeDominio> listU = daoU.consultar(usuario);
				usuario = (Usuario) listU.get(0);
				c.setUsuarioAutor(usuario);
				
				//grupo Autor
				grupo = new Grupo();										//Cria variavel local endereco do tipo Endereco
				grupo.setId(rs.getInt("grupoautor"));						//seta no endereco o ID que trouxe do banco pelo RS
				daoG = new GrupoDAO();										//Cria variavel local dao do tipo EnderecoDAO
				listG = daoG.consultar(grupo);								//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				grupo = (Grupo) listG.get(0);								//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				c.setGrupoAutor(grupo);										//seta o endereco inteiro dentro do objeto de endereco no usuario
				
				//tipo de serviço
				TipoDeServico tipo = new TipoDeServico();					//Cria variavel local endereco do tipo Endereco
				tipo.setId(rs.getInt("tipodeservico"));						//seta no endereco o ID que trouxe do banco pelo RS
				TipoDeServicoDAO daoT = new TipoDeServicoDAO();				//Cria variavel local dao do tipo EnderecoDAO
				List<EntidadeDominio> listT = daoT.consultar(tipo);			//Invoca o metodo consultar do DAO de endereco e coloca na lista de entidade dominio
				tipo = (TipoDeServico) listT.get(0);						//coloca dentro de endereco o indice 0 da lista e faz o casting dele para o sistema entender que não é do tipo entidadeDominio e sim do tipo endereco
				c.setTipoDeServico(tipo);									//seta o endereco inteiro dentro do objeto de endereco no usuario
				
				//somar data de inicio com o tempo de resolução do chamado para obter a data estimada
				c.setDtEstimada(ConverteDate.somaMinutosDate(c.getTipoDeServico().getTempoEstimado(), c.getDataCriacao()));
				
				//follow up
				FollowUpsChamados follow = new FollowUpsChamados();			//Cria variavel local endereco do followup
				follow.setCodigoChamado(new Chamado());						//seta objeto de chamado dentro de código de chamado no followup
				follow.getCodigoChamado().setId(c.getId());					//seta no codigo de chamado o ID que trouxe do banco pelo Result set
				FollowUpsDAO daoF = new FollowUpsDAO();						//Cria variavel local dao do tipo FollowUpsDAO
				List<?> listF = daoF.consultar(follow);						//Invoca o metodo consultar do DAO de FollowUps e coloca na lista GENERICA<?>
				c.setFollowUps((List<FollowUpsChamados>)listF);				//seta a lista inteiro dentro do objeto de chamado
				
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
