package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.ViscontrolAPI.entidades.AnfitrionApiImp;
import es.mdef.ViscontrolAPI.entidades.InvitadoApiImp;
import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;
import es.mdef.ViscontrolLib.Persona;

@Component
public class PersonaListaAssembler <T extends Persona> implements RepresentationModelAssembler<T, PersonaListaModel> {

	@Override
	public PersonaListaModel toModel(T entity) {
		PersonaListaModel model = new PersonaListaModel();
		model.setId(((PersonaApiImp) entity).getId());
		model.setDni(entity.getDni());
		model.setNombre(entity.getNombre());
		model.setApellidos(entity.getApellidos());		
		
		switch (((PersonaApiImp) entity).getTipo()) {
		case Anfitrion: {
			AnfitrionApiImp anfitrion = (AnfitrionApiImp) entity;
			model.setTipo(anfitrion.getTipo());
			break;
		}
		case Invitado: {
			InvitadoApiImp invitado = (InvitadoApiImp) entity;
			model.setTipo(invitado.getTipo());
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + ((PersonaApiImp) entity).getTipo());
		}
		
		model.add(
				linkTo(methodOn(PersonaController.class).one(((PersonaApiImp) entity).getId())).withSelfRel()
				);
		
		return model;
	} 
	
	public CollectionModel<PersonaListaModel> toCollection(List<T> lista) {
		CollectionModel<PersonaListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
//		collection.add(
//				linkTo(methodOn(PersonaController.class).all()).withRel("personas")
//				);
		
		return collection;
	}	
}
