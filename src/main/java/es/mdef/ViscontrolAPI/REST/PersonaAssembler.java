package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.ViscontrolAPI.entidades.AnfitrionApiImp;
import es.mdef.ViscontrolAPI.entidades.InvitadoApiImp;
import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;
import es.mdef.ViscontrolAPI.entidades.PersonaApiImp.Tipo;


@Component
public class PersonaAssembler implements RepresentationModelAssembler<PersonaApiImp, PersonaModel> {

	@Override
	public PersonaModel toModel(PersonaApiImp entity) {
		PersonaModel model = new PersonaModel();
		
		switch (entity.getTipo()) {
		case Anfitrion: {
			AnfitrionApiImp anfitrion = (AnfitrionApiImp) entity;
			model.setNip(anfitrion.getNip());
			model.setArea(anfitrion.getArea());
			model.setRole(anfitrion.getRole());
			model.setTipo(Tipo.Anfitrion);
			break;
		}
		case Invitado: {
			InvitadoApiImp invitado = (InvitadoApiImp) entity;
			model.setMatricula(invitado.getMatricula());
			model.setEmpresa(invitado.getEmpresa());
			model.setAutorizacion(invitado.isAutorizado());
			model.setInicioAut(invitado.getInicioAut());
			model.setFinAut(invitado.getFinAut());
			model.setTipo(Tipo.Invitado);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + entity.getTipo());
		}

		model.setDni(entity.getDni());
		model.setNombre(entity.getNombre());
		model.setApellidos(entity.getApellidos());
		model.setTelefono(entity.getTelefono());
		model.setEmail(entity.getEmail());
		model.setId(entity.getId());
		
		model.add(linkTo(methodOn(PersonaController.class).one(entity.getId())).withSelfRel());
		
		switch (entity.getTipo()) {
		case Anfitrion: {
		    model.add(linkTo(methodOn(PersonaController.class).visitasDePersona(entity.getId())).withRel("Visitas_Anfitrion"));
		    break;
		}
	
		case Invitado: {
			model.add(linkTo(methodOn(PersonaController.class).visitasDePersona(entity.getId())).withRel("Visitas_Invitado"));
			break;
		}
		default:
			model.add(linkTo(methodOn(PersonaController.class).visitasDePersona(entity.getId())).withRel("visitas_Persona"));
			throw new IllegalArgumentException("Unexpected value: " + entity.getTipo());			
		}
	
		return model;
	}
	
	
	public PersonaApiImp toEntity(PersonaModel model) {
		PersonaApiImp persona;

		switch (model.getTipo()) {
		case Anfitrion: {
			AnfitrionApiImp anfitrion = new AnfitrionApiImp();
			anfitrion.setNip(model.getNip());
			anfitrion.setArea(model.getArea());
			anfitrion.setRole(model.getRole());
			persona = anfitrion;
			break;
		}
		case Invitado: {
			InvitadoApiImp invitado = new InvitadoApiImp();
			invitado.setMatricula(model.getMatricula());
			invitado.setEmpresa(model.getEmpresa());
			invitado.setAutorizacion(model.getAutorizacion());
			invitado.setInicioAut(model.getInicioAut());
			invitado.setFinAut(model.getFinAut());
			
			persona = invitado;
			break;
		}
		default:
			persona = new PersonaApiImp();
			throw new IllegalArgumentException("Unexpected value: " + model.getTipo());
		}

		persona.setId(model.getId());
		persona.setDni(model.getDni());
		persona.setNombre(model.getNombre());
		persona.setApellidos(model.getApellidos());
		persona.setTelefono(model.getTelefono());
		persona.setEmail(model.getEmail());		

		return persona;
	}

}
