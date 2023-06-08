package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.ViscontrolAPI.entidades.AnfitrionApiImp;
import es.mdef.ViscontrolAPI.entidades.VisitaApiImp;
import es.mdef.ViscontrolAPI.excepciones.PersonaNoEsAnfitrionExcpetion;


@Component
public class VisitaPostAssembler implements RepresentationModelAssembler<VisitaApiImp, VisitaPostModel> {
	
	@Override
	public VisitaPostModel toModel(VisitaApiImp entity) {
		
		VisitaPostModel model = new VisitaPostModel();
		
		model.setFechaInicio(entity.getFechaInicio());
		model.setFechaFin(entity.getFechaFin());
		model.setActividad(entity.getActividad());
		model.setActuaciones(entity.getActuaciones());
		model.setAnfitrion((AnfitrionApiImp)entity.getAnfitrion());
		
		model.add(
				linkTo(methodOn(VisitaController.class).one(entity.getId())).withSelfRel()
				);
		
		return model;
	}

	public VisitaApiImp toEntity(VisitaPostModel model) {
		
		VisitaApiImp visita = new VisitaApiImp();
		
		//System.out.println("anfitrion : " + model.getAnfitrion());
		
		if (model.getAnfitrion() instanceof AnfitrionApiImp) {
			visita.setFechaInicio(model.getFechaInicio());
			visita.setFechaFin(model.getFechaFin());
			visita.setActividad(model.getActividad());
			visita.setActuaciones(model.getActuaciones());
			visita.setAnfitrion((AnfitrionApiImp) model.getAnfitrion());
		} else {
			throw new PersonaNoEsAnfitrionExcpetion(model.getAnfitrion().getId());
		}
			
		return visita;
	}

}
