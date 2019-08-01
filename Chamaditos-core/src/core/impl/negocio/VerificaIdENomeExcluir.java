package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Grupo;

public class VerificaIdENomeExcluir implements IStrategy {
	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Grupo grupo = (Grupo)entidade;
		if(grupo.getId() == null)
		{
			if(grupo.getNomeGrupo() == null || grupo.getNomeGrupo().trim().equals(""))
			{
				return "Nome verificado é nulo, Insira um Nome..<br>";
			}
			return "Id verificado é nulo, inforrma um ID..<br>";
		}
		return null;
	}
}
