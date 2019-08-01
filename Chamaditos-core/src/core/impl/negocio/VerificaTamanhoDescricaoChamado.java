package core.impl.negocio;

import core.IStrategy;
import dominio.Chamado;
import dominio.EntidadeDominio;

public class VerificaTamanhoDescricaoChamado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		
		Chamado chamado = (Chamado) entidade;
		if(chamado.getDescricao() != null)
		{
			if(chamado.getDescricao().length() < 20)
			{
			  return "Para melhor compreensão, Descrição Maior que 20 Caracteres por Favor<br>";
			}
		}
		return null;
	}
}
