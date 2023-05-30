package es.mdef.ViscontrolAPI.entidades;

import java.util.List;

import es.mdef.ViscontrolLib.Anfitrion;
import es.mdef.ViscontrolLib.Visita;

public class AnfitrionApiImp extends PersonaApiImp implements Anfitrion {
	
	private String nip;
	private String area;
	private String role;
	
	private List<Visita> visitas;
	
	public List<Visita> getVisitas() {
		return visitas;
	}
	public void setVisitasAnfitrion(List<Visita> visitas) {
		this.visitas = visitas;
	}
	
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Tipo getTipo() {
		return Tipo.Anfitrion;
	}
	
	@Override
	public String toString() {
		return "AnfitrionApiImpl [nip=" + nip + ", area=" + area + ", role=" + role + "]";
	}
	
}
