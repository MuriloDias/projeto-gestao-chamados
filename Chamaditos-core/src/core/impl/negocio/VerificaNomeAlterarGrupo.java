package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Grupo;

public class VerificaNomeAlterarGrupo implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Grupo grupo = (Grupo)entidade;
		if(grupo.getId() == null)		//verificar se nome � nulo
		{
			return "Id verificado � nulo, Insira um ID..<br>";
		}
		return null;
	}
}
