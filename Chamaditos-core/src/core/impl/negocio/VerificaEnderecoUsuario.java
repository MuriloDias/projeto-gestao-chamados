package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class VerificaEnderecoUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		Usuario user = (Usuario)entidade;
		
		if(user.getEndereco() != null)
		{
			if(user.getEndereco().getCep() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getBairro() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getCidade() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getComplemento() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getDtCadastro() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getEstado() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getNumero() == null)
			{
				return "Dado de Endereço Faltando..";
			}
			if(user.getEndereco().getRua() == null)
			{
				return "Dado de Endereço Faltando..";
			}
		}	

		return null;
	}

}
