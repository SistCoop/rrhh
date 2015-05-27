package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.sistcoop.rrhh.admin.client.Roles;
import org.sistcoop.rrhh.admin.client.resource.SucursalResource;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class SucursalResourceImpl implements SucursalResource {

	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private RepresentationToModel representationToModel;
	
	@Context
	protected UriInfo uriInfo;

	@RolesAllowed(Roles.ver_sucursales)
	@Override
	public SucursalRepresentation findById(String sucursal) {
		SucursalModel model = sucursalProvider.getSucursalByDenominacion(sucursal);
		SucursalRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@RolesAllowed(Roles.ver_sucursales)
	@Override
	public List<SucursalRepresentation> findAll(String filterText, Integer firstResult, Integer maxResults) {
		
		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;		
		
		List<SucursalRepresentation> results = new ArrayList<SucursalRepresentation>();
		List<SucursalModel> sucursalModels = sucursalProvider.getSucursales(filterText, firstResult, maxResults);
				
		for (SucursalModel sucursalModel : sucursalModels) {			
			results.add(ModelToRepresentation.toRepresentation(sucursalModel));			
		}
				
		return results;
	}

	@RolesAllowed(Roles.administrar_sucursales)
	@Override
	public Response create(SucursalRepresentation rep) {		
		SucursalModel model = representationToModel.createSucursal(rep, sucursalProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getDenominacion()).build()).header("Access-Control-Expose-Headers", "Location").build();
	}

	@RolesAllowed(Roles.administrar_sucursales)
	@Override
	public void update(String sucursal, SucursalRepresentation rep) {
		SucursalModel model = sucursalProvider.getSucursalByDenominacion(sucursal);		
		model.setDenominacion(rep.getDenominacion());
		model.commit();	
	}

	@RolesAllowed(Roles.administrar_sucursales)
	@Override
	public void delete(String sucursal) {
		SucursalModel model = sucursalProvider.getSucursalByDenominacion(sucursal);
		sucursalProvider.removeSucursal(model);
	}
	
}
