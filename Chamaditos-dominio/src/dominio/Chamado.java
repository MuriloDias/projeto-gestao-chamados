package dominio;

import java.util.Date;
import java.util.List;

public class Chamado extends EntidadeDominio {

	
	public Date dataCriacao;
	public Date dataEncerramento;
	public String status;
	public Grupo grupoAutor;
	public Usuario usuarioAutor;
	public Grupo grupoAtribuido;
	public String titulo;
	public String descricao;
	public TipoDeServico tipoDeServico;
	public List <FollowUpsChamados> followUps;
	public Date DtEstimada;
	public Sla sla;
	
	
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataEncerramento() {
		return dataEncerramento;
	}
	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Grupo getGrupoAutor() {
		return grupoAutor;
	}
	public void setGrupoAutor(Grupo grupoAutor) {
		this.grupoAutor = grupoAutor;
	}

	public Usuario getUsuarioAutor() {
		return usuarioAutor;
	}
	public void setUsuarioAutor(Usuario usuarioAutor) {
		this.usuarioAutor = usuarioAutor;
	}
	public Grupo getGrupoAtribuido() {
		return grupoAtribuido;
	}
	public void setGrupoAtribuido(Grupo grupoAtribuido) {
		this.grupoAtribuido = grupoAtribuido;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public TipoDeServico getTipoDeServico() {
		return tipoDeServico;
	}
	public void setTipoDeServico(TipoDeServico tipoDeServico) {
		this.tipoDeServico = tipoDeServico;
	}
	public List<FollowUpsChamados> getFollowUps() {
		return followUps;
	}
	public void setFollowUps(List<FollowUpsChamados> followUps) {
		this.followUps = followUps;
	}
	public Date getDtEstimada() {
		return DtEstimada;
	}
	public void setDtEstimada(Date dtEstimada) {
		DtEstimada = dtEstimada;
	}
	public Sla getSla() {
		return sla;
	}
	public void setSla(Sla sla) {
		this.sla = sla;
	}
	
}
