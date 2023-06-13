package es.mdef.ViscontrolAPI.entidades;

import es.mdef.ViscontrolLib.VisitaImp;

public class VisitaApiImp extends VisitaImp {
	
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VisitaApiImp [id=" + id + ", getFechaInicio()=" + getFechaInicio() + ", getFechaFin()=" + getFechaFin()
				+ ", getActividad()=" + getActividad() + "]";
	}
	
}
