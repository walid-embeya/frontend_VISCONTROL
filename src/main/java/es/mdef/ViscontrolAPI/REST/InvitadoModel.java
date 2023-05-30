package es.mdef.ViscontrolAPI.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.ViscontrolAPI.entidades.PersonaApiImp;


@Relation(itemRelation = "persona")
public class InvitadoModel extends RepresentationModel<InvitadoModel> {
	
	
	private PersonaApiImp invitado;

	
	public PersonaApiImp getInvitado() {
		return invitado;
	}

	public void setInvitado(PersonaApiImp invitado) {
		this.invitado = invitado;
	}

}
