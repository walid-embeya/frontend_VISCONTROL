package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import es.mdef.ViscontrolAPI.ViscontrolApiApplication;
import es.mdef.ViscontrolAPI.entidades.AnfitrionApiImp;
import es.mdef.ViscontrolAPI.entidades.InvitadoApiImp;
import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;
import es.mdef.ViscontrolAPI.repositorios.PersonaRepositorio;
import es.mdef.ViscontrolAPI.repositorios.VisitaRepositorio;
import es.mdef.ViscontrolLib.Visita;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/personas")
public class PersonaController {
	private final PersonaRepositorio repositorio;
	private final PersonaAssembler assembler;
    private final PersonaListaAssembler listaassembler;
    private final VisitaListaAssembler visitalistassembler;
    private final Logger log;
   
    
	public PersonaController(PersonaRepositorio repositorio, PersonaAssembler assembler, PersonaListaAssembler listaassembler, 
			VisitaRepositorio repositorioVisita,  VisitaListaAssembler visitalistassembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.visitalistassembler = visitalistassembler;
		this.listaassembler = listaassembler;
		log = (Logger) ViscontrolApiApplication.log;
	}
    
	@GetMapping("{id}")
	public PersonaModel one(@PathVariable Long id) {
		PersonaApiImp persona = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "persona"));
		log.info("Recuperado " + persona);
		
		return assembler.toModel(persona);
	}
	
	@GetMapping
	public CollectionModel<PersonaListaModel> all() {
		
		CollectionModel<PersonaListaModel> collection = listaassembler.toCollection(repositorio.findAll());
		
		collection.add(
				linkTo(methodOn(PersonaController.class).all()).withRel("Lista_Personas")
				);
		
		return collection;
	}
    
	@PostMapping
	public PersonaModel add(@RequestBody PersonaModel model) {
		PersonaApiImp persona = repositorio.save(assembler.toEntity(model));
		log.info("Añadido " + persona);
		return assembler.toModel(persona);
	}
	
	@PutMapping("{id}")
    public PersonaModel edit(@PathVariable Long id, @RequestBody PersonaModel model) {
		PersonaApiImp persona = repositorio.findById(id).map(pers -> {
			
			pers.setDni(model.getDni());
			pers.setNombre(model.getNombre());
			pers.setApellidos(model.getApellidos());			
			pers.setTelefono(model.getTelefono());
			pers.setEmail(model.getEmail());
					
			switch (model.getTipo()) {
			case Anfitrion: {
				AnfitrionApiImp anfitrion = (AnfitrionApiImp) pers;
				anfitrion.setNip(model.getNip());
				anfitrion.setArea(model.getArea());
				anfitrion.setRole(model.getRole());
				break;
			}
			case Invitado: {
				InvitadoApiImp invitado = (InvitadoApiImp) pers;
				invitado.setEmpresa(model.getEmpresa());
				invitado.setMatricula(model.getMatricula());
				invitado.setAutorizacion(model.getAutorizacion());
				invitado.setInicioAut(model.getInicioAut());
				invitado.setFinAut(model.getFinAut());

				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + model.getTipo());
			}
			
			return repositorio.save(pers);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "persona"));
		log.info("Actualizado " + persona);
		
		return assembler.toModel(persona);
	}
	
	@GetMapping("{id}/visitas")
    public CollectionModel<VisitaListaModel> visitasDePersona(@PathVariable Long id) {
	
		PersonaApiImp persona = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "persona"));
		
		List<Visita> lista = new ArrayList<>();
		
		switch (persona.getTipo()) {
		case Anfitrion: {
			AnfitrionApiImp anfitrion = (AnfitrionApiImp) persona;
			lista = anfitrion.getVisitas();
			break;
		}
		case Invitado: {
			InvitadoApiImp invitado = (InvitadoApiImp) persona;
			lista = invitado.getVisitas();

			break;
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + persona.getTipo());
		}
					
		CollectionModel<VisitaListaModel> collection = visitalistassembler.toCollection(lista); 
				
		switch (persona.getTipo()) {
		case Anfitrion: {
			collection.add(linkTo(methodOn(PersonaController.class).visitasDePersona(persona.getId())).withRel("Visitas_Anfitrion"));
		    break;
		}
	
		case Invitado: {
			collection.add(linkTo(methodOn(PersonaController.class).visitasDePersona(persona.getId())).withRel("Visitas_Invitado"));
			break;
		}
		default:
			collection.add(linkTo(methodOn(PersonaController.class).visitasDePersona(persona.getId())).withRel("Visitas_Persona"));
			throw new IllegalArgumentException("Unexpected value: " + persona.getTipo());			
		}
		
		return collection;
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado persona " + id);
		repositorio.deleteById(id);
	}
	
}
