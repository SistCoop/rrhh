package org.sistcoop.rrhh.services.resources.admin;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.admin.client.resource.SucursalResource;
import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

public class TrabajadorResourceImpl implements TrabajadorResource {

	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Context
	protected UriInfo uriInfo;

	@Override
	public TrabajadorRepresentation findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrabajadorRepresentation findByTipoNumeroDocumento(
			String tipoDocumento, String numeroDocumento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, TrabajadorRepresentation rep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desactivar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
	
}
