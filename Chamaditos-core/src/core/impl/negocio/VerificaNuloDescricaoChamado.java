package core.impl.negocio;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class VerificaNuloDescricaoChamado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado) entidade;
		if(chamado.getDescricao() == null)
		{
		  return "Por favor, Descrição Necessária.<br>";
		}	
		return null;
	}

}
