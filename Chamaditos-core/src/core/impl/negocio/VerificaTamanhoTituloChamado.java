package core.impl.negocio;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class VerificaTamanhoTituloChamado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado chamado = (Chamado) entidade;
		if(chamado.getTitulo() != null)
		{
			if(chamado.getTitulo().length() < 8)
			{
			  return "Para melhor compreensão, Titulo Maior que 8 Caracteres por Favor<br>";
			}	
		}
		return null;
	}

}
