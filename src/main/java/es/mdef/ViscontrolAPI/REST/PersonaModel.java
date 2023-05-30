package es.mdef.ViscontrolAPI.REST;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.ViscontrolAPI.entidades.PersonaApiImp.Tipo;

@Relation(itemRelation = "persona")
public class PersonaModel extends RepresentationModel<PersonaModel> {
	private Long id;
	private String dni;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	
	private String nip;
	private String area;
	private String role;
	
	private String matricula;
	private String empresa;
	private Boolean autorizacion;
	private Date inicioAut;
	private Date finAut;
	
	private Tipo tipo;

	public String getDni() {
		return dni;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Boolean getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(Boolean autorizacion) {
		this.autorizacion = autorizacion;
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
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "PersonaModel [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", tipo=" + tipo + "]";
	}
	
	
	

}
