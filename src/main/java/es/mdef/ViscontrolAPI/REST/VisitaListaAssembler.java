package es.mdef.ViscontrolAPI.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.ViscontrolAPI.entidades.VisitaApiImp;
import es.mdef.ViscontrolLib.Visita;

@Component
public class VisitaListaAssembler <T extends Visita> implements RepresentationModelAssembler<T, VisitaListaModel> {
	
	@Override
	public VisitaListaModel toModel(T entity) {
		
		VisitaListaModel model = new VisitaListaModel();
		
		model.setFechaInicio(((VisitaApiImp) entity).getFechaInicio());
		model.setFechaFin(((VisitaApiImp) entity).getFechaFin());
		model.setActividad(((VisitaApiImp) entity).getActividad());
			
		model.add(
				linkTo(methodOn(VisitaController.class).one(((VisitaApiImp) entity).getId())).withSelfRel()
				);
		return model;
	} 
	
	public CollectionModel<VisitaListaModel> toCollection(List<T> lista) {
		CollectionModel<VisitaListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
//		collection.add(
//				linkTo(methodOn(VisitaController.class).all()).withRel("lista_visitas")
//				);
		
		return collection;
	}

}
