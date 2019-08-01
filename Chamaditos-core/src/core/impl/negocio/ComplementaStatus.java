package core.impl.negocio;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class ComplementaStatus implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado) entidade;
		chamado.setStatus("Processando");
		return null;
	}

}
