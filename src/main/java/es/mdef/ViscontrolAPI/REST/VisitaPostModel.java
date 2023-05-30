package es.mdef.ViscontrolAPI.REST;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;


@Relation(itemRelation = "visita")
public class VisitaPostModel extends RepresentationModel<VisitaPostModel> {

	private Date fechaInicio;
	private Date fechaFin;
	private String actuaciones;
	private String actividad;
	private PersonaApiImp anfitrion;
	
	public PersonaApiImp getAnfitrion() {
		return anfitrion;
	}
	public void setAnfitrion(PersonaApiImp anfitrion) {
		this.anfitrion = anfitrion;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getActuaciones() {
		return actuaciones;
	}
	public void setActuaciones(String actuaciones) {
		this.actuaciones = actuaciones;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	
	@Override
	public String toString() {
		return "VisitaPostModel [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", actuaciones=" + actuaciones
				+ ", actividad=" + actividad + "]";
	}
	
}
