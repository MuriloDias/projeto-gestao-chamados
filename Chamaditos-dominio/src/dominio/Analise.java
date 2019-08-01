package dominio;

import java.util.List;
import java.util.Map;

public class Analise extends EntidadeDominio {

	public Chamado chamado; //pra consultar no banco
	//public String grupo;
	//public String tempo;
	//public String status;
	public List<String> linhas;
	private Map<String, Map<String, Integer>> meses; // = new HashMap<String, Map<String, Integer>>();
	
	public Chamado getChamado() {
		return chamado;
	}
	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}
	
	public List<String> getLinhas() {
		return linhas;
	}
	public void setLinhas(List<String> linhas) {
		this.linhas = linhas;
	}	
	/*public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}	

	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}*/

	public Map<String, Map<String, Integer>> getMeses() {
		return meses;
	}
	public void setMeses(Map<String, Map<String, Integer>> meses) {
		this.meses = meses;
	}
	
	
	
	
}
