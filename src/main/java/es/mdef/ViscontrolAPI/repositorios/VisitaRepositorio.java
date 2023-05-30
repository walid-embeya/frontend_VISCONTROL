package es.mdef.ViscontrolAPI.repositorios;



import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.ViscontrolAPI.entidades.VisitaApiImp;


public interface VisitaRepositorio extends JpaRepository<VisitaApiImp, Long> {
	
	//List<PersonaApiImp> findInvitadosById(Long id);
	
	//List<Visita> findByAnfitrionId(Long id);
	
}
