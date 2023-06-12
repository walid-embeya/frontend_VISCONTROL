package es.mdef.ViscontrolAPI.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.ViscontrolAPI.entidades.VisitaApiImp;

@RepositoryRestResource(path="visitas", collectionResourceRel="visitas")
public interface VisitaRepositorio extends JpaRepository<VisitaApiImp, Long> {	
	
}
