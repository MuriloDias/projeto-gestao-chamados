package core.impl.negocio;

import java.util.Date;
import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class ComplementaDataCriacao implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado)entidade;
		Date date = new Date();
		chamado.setDataCriacao(date);
		return null;
	}

}
