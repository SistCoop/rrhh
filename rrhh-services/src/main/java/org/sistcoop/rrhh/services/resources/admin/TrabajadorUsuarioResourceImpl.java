package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.TrabajadorUsuarioResource;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Stateless
public class TrabajadorUsuarioResourceImpl implements TrabajadorUsuarioResource {

	@Inject
	private TrabajadorUsuarioProvider trabajadorUsuarioProvider;
	
	@Context
	protected UriInfo uriInfo;

	@Override
	public TrabajadorUsuarioRepresentation findById(Integer id) {
		TrabajadorUsuarioModel model = trabajadorUsuarioProvider.getTrabajadorUsuarioById(id);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public TrabajadorUsuarioRepresentation findByUsuario(String usuario) {
		TrabajadorUsuarioModel model = trabajadorUsuarioProvider.getTrabajadorUsuarioByUsuario(usuario);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public void delete(Integer id) {
		TrabajadorUsuarioModel model = trabajadorUsuarioProvider.getTrabajadorUsuarioById(id);
		trabajadorUsuarioProvider.removeTrabajadorUsuario(model);
	}
	
}
