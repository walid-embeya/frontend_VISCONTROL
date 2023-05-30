package es.mdef.ViscontrolAPI.REST;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.ViscontrolAPI.entidades.InvitadoApiImp;
import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;


@Relation(itemRelation = "listapersona")
public class InvitadoListaPostModel extends RepresentationModel<InvitadoListaPostModel> {
	
	
	private List<PersonaApiImp> listaInvitados;

	
	public List<PersonaApiImp> getListaInvitados() {
		return listaInvitados;
	}

	public void setListaInvitados(List<PersonaApiImp> listaInvitados) {
		this.listaInvitados = listaInvitados;
	}

	
	
	
	

}
