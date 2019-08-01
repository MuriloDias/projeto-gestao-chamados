package controle.web.command;

import core.aplicacao.Resultado;
import dominio.EntidadeDominio;

public interface ICommand {

	public Resultado execute(EntidadeDominio entidade);

}
