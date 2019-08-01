package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class VerificaEmailSalvarUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Usuario usuario =  (Usuario)entidade;		
		char[] splitedname = usuario.getEmail().toCharArray();
		if(!usuario.getEmail().endsWith(".com"))
		{
			return "Email Inválido, Insira um Final de Email Válido.<br>";
		}
		boolean arroba = false;
		for(int letter = 0; letter < splitedname.length; letter++) 		//para cada letra desde o começo da string até o final
		{
			
			if(splitedname[letter] != '@') 
			{
				arroba = false;
			}
			else		// tem arroba!
			{
				arroba = true;
				break;
			}
		}
		if(!arroba)
		{
			return "Email Inválido, Insira um email com @ (Arroba).<br>";
		}
		return null;
	}
}