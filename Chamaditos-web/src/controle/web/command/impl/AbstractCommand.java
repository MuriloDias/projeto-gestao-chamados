package controle.web.command.impl;

import controle.web.command.ICommand;
import core.IFachada;
import core.impl.controle.Fachada;

public abstract class AbstractCommand implements ICommand{
	
	protected IFachada fachada = new Fachada();

}
