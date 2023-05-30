package es.mdef.ViscontrolAPI.excepciones;

public class PersonaNoEsAnfitrionExcpetion extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PersonaNoEsAnfitrionExcpetion(Long id) {
		super("La persona con ID " + id + " no  es un anfitrión");
	}

}
