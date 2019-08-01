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
				return "O Grupo Atribuido é nulo, Insira Um!<br>";
			}
			else if(chamado.getTipoDeServico() == null)
			{
				return "O Tipo de Serviço Prestado é nulo, Insira Um!<br>";
			}
			else if(chamado.getGrupoAtribuido().getId() != chamado.getTipoDeServico().getGrupo().getId())
			{
				return "O Grupo Atribuido é diferente do Grupo de Tipo de Serviço relacionado!<br>";
			}
			else
			{
				return null;
			}
	}
}


