package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import es.mdef.ViscontrolAPI.entidades.VisitaApiImp;
import es.mdef.ViscontrolAPI.repositorios.VisitaRepositorio;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/visitas")
public class VisitaController {
	
	private final VisitaRepositorio repositorio;
	private final VisitaAssembler assembler;
    private final VisitaListaAssembler listaAssembler;
    private final VisitaPostAssembler postAssembler;
    private final PersonaAssembler personaassembler;
    private final PersonaListaAssembler personalistassembler;
    private final Logger log;
	
    public VisitaController(VisitaRepositorio repositorio, VisitaAssembler assembler, VisitaListaAssembler listaAssembler,
    		 VisitaPostAssembler postAssembler, PersonaListaAssembler personalistassembler, PersonaAssembler personaassembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.postAssembler = postAssembler;
		this.personalistassembler = personalistassembler;
		this.personaassembler = personaassembler;
		log = (Logger) ViscontrolApiApplication.log;
	}
    
    
    // Endpoint para recuperar la lista global de visitas   
    @GetMapping
	public CollectionModel<VisitaListaModel> all() {
    	
    	CollectionModel<VisitaListaModel> collection = listaAssembler.toCollection(repositorio.findAll());
		 
		collection.add(
					linkTo(methodOn(VisitaController.class).all()).withRel("lista_global_visitas")					
					);
		
		return collection;
	}
    
    // Endpoint para recuperar una visita por id
    @GetMapping("{id}")
	public VisitaModel one(@PathVariable Long id) {
		VisitaApiImp visita = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "visita"));
		
		log.info("Recuperado " + visita);
		
		return assembler.toModel(visita);
	}
    
    // Endpoint para añadir una nueva visita 
    @PostMapping
	public VisitaModel add(@RequestBody VisitaPostModel model) {
    	VisitaApiImp visita = repositorio.save(postAssembler.toEntity(model));
    	
		log.info("Añadido " + visita);
		
		return assembler.toModel(visita);
	}
    
    // Endpoint para modificar una visita ya existente
    @PutMapping("{id}")
    public VisitaModel edit(@PathVariable Long id, @RequestBody VisitaPostModel model) {
		VisitaApiImp visita = repositorio.findById(id).map(vis -> {			
			vis.setFechaInicio(model.getFechaInicio());
			vis.setFechaFin(model.getFechaFin());
			vis.setActividad(model.getActividad());		
			vis.setActuaciones(model.getActuaciones());	
			vis.setAnfitrion((AnfitrionApiImp) model.getAnfitrion());					
			return repositorio.save(vis);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "visita"));
		
		log.info("Actualizado " + visita);
		
		return assembler.toModel(visita);
	}
    
    // Endpoint para recuperar la lista de invitados de una visita
    @GetMapping("{id}/invitados")
	public CollectionModel<PersonaListaModel> invitadosVisita(@PathVariable Long id) {

    	VisitaApiImp visita = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "visita"));
    	
    	CollectionModel<PersonaListaModel> collection =	personalistassembler.toCollection(visita.getInvitados());
    	log.info("Recuperado " + collection);
    	
    	collection.add(
				linkTo(methodOn(VisitaController.class).invitadosVisita(id)).withRel("invitados_visita"));
    	
    	return collection;    	
	}
        
    // Endpoint para añadir un invitado a una visita  
    @PostMapping("{id}/addinvitado")
    public VisitaModel addInvitadoToVisita(@PathVariable Long id, @RequestBody InvitadoModel model) {
        
    	VisitaApiImp visita = repositorio.findById(id)
        		.orElseThrow(() -> new RegisterNotFoundException(id, "visita"));        		
   
    	log.info("Añadido invitado visita " + visita);
    	
    	visita.getInvitados().add((InvitadoApiImp) model.getInvitado());    	       
        
        repositorio.save(visita);

        return assembler.toModel(visita);
    }
    
    // Endpoint para añadir un lista de invitados a una visita   
    @PostMapping("{id}/invitados")
    public VisitaModel addListaInvitadosToVisita(@PathVariable Long id, @RequestBody InvitadoListaPostModel listapostmodel) {
        VisitaApiImp visita = repositorio.findById(id)
        		.orElseThrow(() -> new RegisterNotFoundException(id, "visita"));        		
   
        log.info("Añadido lista invitados en bloc ");
        
        visita.getInvitados().clear();                 
        listapostmodel.getListaInvitados().forEach(inv -> 
        	 { visita.getInvitados().add((InvitadoApiImp) inv);}
        );                           
        
        //// Otro metodo para anadir la lista y guardar la visita
		//visita.getInvitados().addAll(listapostmodel.getListaInvitados());
		//return assembler.toModel(repositorio.save(visita));
        
        repositorio.save(visita);        
        
        return assembler.toModel(visita);            	
    }

    // Endpoint para recuperar el infitrion de una visita (el que ha planificado esta visita)
    @GetMapping("{id}/anfitrion")
	public PersonaModel anfitrion(@PathVariable Long id) {
    	
    	VisitaApiImp visita = repositorio.findById(id)
        		.orElseThrow(() -> new RegisterNotFoundException(id, "visita"));     
        
    	PersonaApiImp persona =	(AnfitrionApiImp) visita.getAnfitrion();
    	
    	log.info("Recuperado anfitrion " + visita);
      		
    	return personaassembler.toModel(persona);		
	}
   
    
    // Endpoint para delete una visita (con la lista de invitados)
    @DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado visita " + id);
		repositorio.deleteById(id);
	}
    
}
    










