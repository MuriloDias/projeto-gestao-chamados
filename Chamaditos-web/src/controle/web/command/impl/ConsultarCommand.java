package controle.web.command.impl;

import core.aplicacao.Resultado;
import dominio.EntidadeDominio;

public class ConsultarCommand extends AbstractCommand {

	@Override
	public Resultado execute(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return fachada.consultar(entidade);
	}

}
