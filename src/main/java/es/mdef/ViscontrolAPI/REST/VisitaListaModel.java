package es.mdef.ViscontrolAPI.REST;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "visitas")
public class VisitaListaModel extends RepresentationModel<VisitaListaModel> {
	
	private Date fechaInicio;
	private Date fechaFin;
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

	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	
	@Override
	public String toString() {
		return "VisitaListaModel [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", actividad=" + actividad + "]";
	}

}
