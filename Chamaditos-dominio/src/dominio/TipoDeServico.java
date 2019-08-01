package dominio;

public class TipoDeServico extends EntidadeDominio {

	public String nome;
	public Grupo grupo;
	public int severidade;
	public int urgencia;
	public int tempoEstimado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public int getSeveridade() {
		return severidade;
	}
	public void setSeveridade(int severidade) {
		this.severidade = severidade;
	}
	public int getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(int urgencia) {
		this.urgencia = urgencia;
	}
	public int getTempoEstimado() {
		return tempoEstimado;
	}
	public void setTempoEstimado(int tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}

	
	
	
}
