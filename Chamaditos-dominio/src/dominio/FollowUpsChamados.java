package dominio;

import java.util.Date;

public class FollowUpsChamados extends EntidadeDominio {

	public String followUps;
	public Usuario proprietario;
	public Chamado codigoChamado;
	public Date dataCriacao;
	
	public String getFollowUps() {
		return followUps;
	}
	public void setFollowUps(String followUps) {
		this.followUps = followUps;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Chamado getCodigoChamado() {
		return codigoChamado;
	}
	public void setCodigoChamado(Chamado codigoChamado) {
		this.codigoChamado = codigoChamado;
	}
	public Usuario getProprietario() {
		return proprietario;
	}
	public void setProprietario(Usuario proprietario) {
		this.proprietario = proprietario;
	}	
	
}
