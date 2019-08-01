package core.impl.negocio;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class VerificaNuloTituloChamado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado) entidade;
		if(chamado.getTitulo() == null)
		{
		  return "Por favor, Insira um Titulo.<br>";
		}	
		return null;
	}

}
