package core.impl.negocio;

import java.util.Date;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.FollowUpsChamados;

public class ComplementaDataCriacaoFollowUps implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		FollowUpsChamados follow = (FollowUpsChamados)entidade;
		Date date = new Date();
		follow.setDataCriacao(date);	
		return null;
	}

}
