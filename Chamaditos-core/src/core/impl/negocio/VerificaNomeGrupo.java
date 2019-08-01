package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Grupo;

public class VerificaNomeGrupo implements IStrategy {
	
	
	//Verificação do Nome no formGrupo - parea saber se ao realizar as operações ele não vem vazio;
	
	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Grupo grupo = (Grupo)entidade;
		//char[] splitedname = grupo.getNomeGrupo().toCharArray();
		
		if(grupo.getNomeGrupo() == null || grupo.getNomeGrupo().trim().equals(""))		//verificar se nome é nulo
		{
			return "Nome verificado é nulo, Insira um Nome..<br>";
		}
		else {
			char[] splitedname = grupo.getNomeGrupo().toCharArray();
			for(int number = 48; number < 57; number++)							//para cada numero de 0 até 9 -> de acordo com a tabela AASCII
			{
				for(int letter = 0; letter < splitedname.length; letter++) 		//para cada letra desde o começo da string até o final
				{
					if(number == splitedname[letter])
					{
						return "Contém numeros em valor, não permitido!<br>";
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
