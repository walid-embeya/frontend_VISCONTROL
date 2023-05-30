package es.mdef.ViscontrolAPI.REST;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.ViscontrolAPI.entidades.PersonaApiImp.Tipo;

@Relation(collectionRelation = "personas")
public class PersonaListaModel extends RepresentationModel<PersonaListaModel> {
	private Long id;
	private String dni;
	private String nombre;
	private String apellidos;
	private Tipo tipo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	
	@Override
	public String toString() {
		return "PersonaListaModel [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", tipo=" + tipo
				+ "]";
	}

}
