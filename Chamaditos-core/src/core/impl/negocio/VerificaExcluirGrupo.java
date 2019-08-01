package core.impl.negocio;

import java.sql.SQLException;
import java.util.List;

import core.IStrategy;
import core.impl.dao.UsuarioDAO;
import dominio.EntidadeDominio;
import dominio.Grupo;
import dominio.Usuario;

public class VerificaExcluirGrupo implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Grupo grupo = (Grupo)entidade;
		Usuario usuario = new Usuario();
		UsuarioDAO dao = new UsuarioDAO();
		
		usuario.setGrupo(grupo);
		try {
			List<EntidadeDominio> list = dao.consultar(usuario);
			if(list != null && !list.isEmpty())
			{
				return "Não foi possivel Excluir, existe um usuário vinculado a este Grupo..";
			}
			else
			{
				return null;
			}	
		}catch(SQLException e)
		{
			return "Problema na exclusão, contate o ADM do sistema..";
		}
	}
}
