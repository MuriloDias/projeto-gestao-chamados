package core.impl.negocio;

import java.util.Date;

import core.IStrategy;
import core.util.ConverteDate;
import dominio.Chamado;
import dominio.EntidadeDominio;
import dominio.Sla;

public class CalculoStatusSla implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Chamado c = (Chamado)entidade;
		Date data = new Date();
		
		
		if(c.getSla() == null)
		{
			Sla sla = new Sla();
			c.setSla(sla);
			// c.setSla(new Sla());
		}
		if(c.getStatus().equals("Fechado"))
		{
			if(c.getDataEncerramento().after(c.getDtEstimada()))
			{
				//("Fechado fora do prazo!!");
				c.getSla().setStatus("Fechado fora do prazo!!");
			}
			else
			{
				//("Fechado dentro do prazo!!");
				c.getSla().setStatus("Fechado dentro do prazo!!");
			}
		}
		else if(data.after(c.getDtEstimada()))
		{
			//("Fora do Prazo!!");
			c.getSla().setStatus("Fora do Prazo!!");
		}
		else if(data.after(ConverteDate.somaMinutosDate(-90, c.getDtEstimada())))
		{
			//("Próximo ao Prazo!!!");
			c.getSla().setStatus("Próximo ao Prazo!!!");
		}
		else
		{
			//("Dentro do Prazo..");
			c.getSla().setStatus("Dentro do Prazo..");
		}	
		return null;
	}

}
