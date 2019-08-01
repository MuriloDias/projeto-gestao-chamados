package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class VerificaGrupoSalvarUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		
		Usuario usuario =  (Usuario)entidade;
		
		if(usuario.getGrupo() == null)
		{
			return "Selecione o Grupo que o Usuário Pertence.<br>";
		}
		
		return null;
	}

}
