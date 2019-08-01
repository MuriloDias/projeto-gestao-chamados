package core.impl.negocio;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class VerificaGrupoIgualGrupoAtt implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado) entidade;
			if(chamado.getGrupoAtribuido() == null)
			{
				return "O Grupo Atribuido � nulo, Insira Um!<br>";
			}
			else if(chamado.getTipoDeServico() == null)
			{
				return "O Tipo de Servi�o Prestado � nulo, Insira Um!<br>";
			}
			else if(chamado.getGrupoAtribuido().getId() != chamado.getTipoDeServico().getGrupo().getId())
			{
				return "O Grupo Atribuido � diferente do Grupo de Tipo de Servi�o relacionado!<br>";
			}
			else
			{
				return null;
			}
	}
}


