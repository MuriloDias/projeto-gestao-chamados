package core.impl.negocio;

import java.util.Date;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class VerificaDtNascSalvarUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Usuario usuario =  (Usuario)entidade;
		
		if(usuario.getDtnascimento() == null)
		{
			return "A Data de Nascimento n�o � valida<br>";
		}
		Date data = new Date(System.currentTimeMillis());
		if(usuario.getDtnascimento().after(data) == true) 
		{
			return "A Data de Nascimento � Depois da Data Atual<br>";
		}
		return null;
	}

}
