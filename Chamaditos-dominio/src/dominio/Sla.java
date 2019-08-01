package dominio;

import java.util.Date;

public class Sla extends EntidadeDominio {

	private String Status;
	private Date DtResolucao;
	private Date DtPrevista;
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Date getDtResolucao() {
		return DtResolucao;
	}
	public void setDtResolucao(Date dtResolucao) {
		DtResolucao = dtResolucao;
	}
	public Date getDtPrevista() {
		return DtPrevista;
	}
	public void setDtPrevista(Date dtPrevista) {
		DtPrevista = dtPrevista;
	}	
}
