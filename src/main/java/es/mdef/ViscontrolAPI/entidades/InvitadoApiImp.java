package es.mdef.ViscontrolAPI.entidades;

import java.util.Date;
import java.util.List;

import es.mdef.ViscontrolLib.Invitado;
import es.mdef.ViscontrolLib.Visita;


public class InvitadoApiImp extends PersonaApiImp implements Invitado {
	
	private String matricula;
	private String empresa;
	private Boolean autorizado;
	private Date inicioAut;
	private Date finAut;
   	private List<Visita> visitas;
   	
   	public List<Visita> getVisitas() {
		return visitas;
	}
	public void setVisitasInvitado(List<Visita> visitas) {
		this.visitas = visitas;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public boolean isAutorizado() {
		return autorizado;
	}
	public void setAutorizacion(boolean autorizacion) {
		this.autorizado = autorizacion;
	}
	
	public Date getInicioAut() {
		return inicioAut;
	}
	public void setInicioAut(Date inicioAut) {
		this.inicioAut = inicioAut;
	}
	public Date getFinAut() {
		return finAut;
	}
	
	public void setFinAut(Date finAut) {
		this.finAut = finAut;
	}
	
	public Tipo getTipo() {
		return Tipo.Invitado;
	}
	
	@Override
	public String toString() {
		return "InvitadoApiImpl [matricula=" + matricula + ", empresa=" + empresa + ", autorizacion=" + autorizado	+ "]";
	}

}