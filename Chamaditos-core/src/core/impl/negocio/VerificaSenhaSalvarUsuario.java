package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class VerificaSenhaSalvarUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Usuario usuario =  (Usuario)entidade;
		
		if(usuario.getSenha().length() < 6)
		{
			return "A senha tem que ter mais de 6 caracteres<br>";
		}
		return null;
	}
}
