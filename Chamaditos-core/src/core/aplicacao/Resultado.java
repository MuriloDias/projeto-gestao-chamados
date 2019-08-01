package core.aplicacao;

import java.util.List;

import dominio.EntidadeDominio;

public class Resultado extends EntidadeAplicacao{
	
	private String msg;
	private List<EntidadeDominio> entidades;
	
	//metodo para recuperação do campo msg
	//return valor do campo msg
	
	public String getMsg() {
		return msg;
	}
	
	//Valor de msg atribuido a msg
	//param msg Atributo da classe
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//Metodo de recuperacao do campo entidades
	//return valor do campo entidades

	public List<EntidadeDominio> getEntidades(){
		return entidades;
	}
	//valor de entidades atribuido a entidades
	//param entidades Atributo da classe
	
	public void setEntidades(List<EntidadeDominio> entidades) {
		this.entidades = entidades;
	}
}
