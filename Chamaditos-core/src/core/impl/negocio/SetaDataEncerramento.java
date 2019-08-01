package core.impl.negocio;

import java.util.Date;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class SetaDataEncerramento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado)entidade;
		if(chamado.getStatus().equals("Fechado"))
		{
			Date date = new Date();
			chamado.setDataEncerramento(date);
		}
		return null;
	}

}
