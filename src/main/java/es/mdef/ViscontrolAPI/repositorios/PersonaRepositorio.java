package es.mdef.ViscontrolAPI.repositorios;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;
import es.mdef.ViscontrolLib.Persona;


public interface PersonaRepositorio extends JpaRepository<PersonaApiImp, Long> {
	Optional<PersonaApiImp> findByDni(String dni); 
	
	//List<PersonaApiImp> findPersonasByTipo(Tipo tipo_persona);
	//List<Visita> findVisitasByPersonaId(Long id);
}
