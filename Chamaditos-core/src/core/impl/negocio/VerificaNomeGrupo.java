package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Grupo;

public class VerificaNomeGrupo implements IStrategy {
	
	
	//Verifica��o do Nome no formGrupo - parea saber se ao realizar as opera��es ele n�o vem vazio;
	
	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Grupo grupo = (Grupo)entidade;
		//char[] splitedname = grupo.getNomeGrupo().toCharArray();
		
		if(grupo.getNomeGrupo() == null || grupo.getNomeGrupo().trim().equals(""))		//verificar se nome � nulo
		{
			return "Nome verificado � nulo, Insira um Nome..<br>";
		}
		else {
			char[] splitedname = grupo.getNomeGrupo().toCharArray();
			for(int number = 48; number < 57; number++)							//para cada numero de 0 at� 9 -> de acordo com a tabela AASCII
			{
				for(int letter = 0; letter < splitedname.length; letter++) 		//para cada letra desde o come�o da string at� o final
				{
					if(number == splitedname[letter])
					{
						return "Cont�m numeros em valor, n�o permitido!<br>";
					}
				}
			}
			if(grupo.getNomeGrupo().length() > 40)
			{
				return "Nome muito grande, tente menos que 40 caracteres<br>";
			}
			return null;														//retorna null significando que funcionou
		}
	}

}
