package es.mdef.ViscontrolAPI.REST;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "visita")
public class VisitaModel extends RepresentationModel<VisitaModel> {

	private Date fechaInicio;
	private Date fechaFin;
	private String actuaciones;
	private String actividad;
	
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
		return "VisitaModel [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", actuaciones=" + actuaciones
				+ ", actividad=" + actividad + "]";
	}
	 	
}
