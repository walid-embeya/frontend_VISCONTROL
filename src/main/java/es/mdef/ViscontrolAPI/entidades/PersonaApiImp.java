package es.mdef.ViscontrolAPI.entidades;

import es.mdef.ViscontrolLib.PersonaImp;

public class PersonaApiImp extends PersonaImp {
	
	public enum Tipo {
		Anfitrion,
		Invitado
	}
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Tipo getTipo() {
		return null;
	}
	
	@Override
	public String toString() {
		return "PersonaApiImpl [getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getApellidos()="
				+ getApellidos() + ", getTelefono()=" + getTelefono() + ", getEmail()=" + getEmail() + "]";
	}
		
}
