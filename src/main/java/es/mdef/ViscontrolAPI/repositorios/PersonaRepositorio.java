package es.mdef.ViscontrolAPI.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;

@RepositoryRestResource(path="personas", collectionResourceRel="personas")
public interface PersonaRepositorio extends JpaRepository<PersonaApiImp, Long> {
	@Query(value = "SELECT invitado_id, COUNT(*) AS count FROM public.visita_invitado " +
            "WHERE visita_id IN (SELECT id FROM visitas WHERE anfitrion_id = :idAnfitrion) " +
            "GROUP BY invitado_id " +
            "ORDER BY count DESC " +
            "LIMIT 1", nativeQuery = true)

	Long findFrecuentGuestByHost(@Param("idAnfitrion") Long idAnfitrion);	
}
