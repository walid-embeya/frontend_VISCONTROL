package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;
import es.mdef.ViscontrolAPI.entidades.VisitaApiImp;

@Component
public class VisitaAssembler implements RepresentationModelAssembler<VisitaApiImp, VisitaModel> {
	
	@Override
	public VisitaModel toModel(VisitaApiImp entity) {
		VisitaModel model = new VisitaModel();

		model.setId(entity.getId());
		model.setFechaInicio(entity.getFechaInicio());
		model.setFechaFin(entity.getFechaFin());
		model.setActividad(entity.getActividad());
		model.setActuaciones(entity.getActuaciones());

		model.add(linkTo(methodOn(PersonaController.class).one(((PersonaApiImp)entity.getAnfitrion()).getId())).withRel("anfitrion"));
		model.add(linkTo(methodOn(VisitaController.class).one(entity.getId())).withSelfRel());
		model.add(linkTo(methodOn(VisitaController.class).invitadosVisita(entity.getId())).withRel("lista_invitados"));
		
		return model;
	}
	
	public VisitaApiImp toEntity(VisitaModel model) {
		VisitaApiImp visita = new VisitaApiImp();
		
		visita.setId(model.getId());
		visita.setFechaInicio(model.getFechaInicio());
		visita.setFechaFin(model.getFechaFin());
		visita.setActividad(model.getActividad());
		visita.setActuaciones(model.getActuaciones());
		
		return visita;	
	}
}
