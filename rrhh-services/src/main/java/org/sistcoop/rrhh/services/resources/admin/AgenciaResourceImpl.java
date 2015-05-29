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
import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class AgenciaResourceImpl implements AgenciaResource {

	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private RepresentationToModel representationToModel;
	
	@Context
	protected UriInfo uriInfo;
	
	@RolesAllowed(Roles.ver_sucursales)
	@Override
	public AgenciaRepresentation findById(String id) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		return ModelToRepresentation.toRepresentation(agenciaModel);
	}

	@RolesAllowed(Roles.ver_sucursales)
	@Override
	public List<AgenciaRepresentation> findAll(String sucursal,
			String filterText, Integer firstResult, Integer maxResults) {
		
		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		if(sucursal != null) {
			SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);
			List<AgenciaModel> agenciaModels = agenciaProvider.getAgencias(sucursalModel, filterText, firstResult, maxResults);		
			List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
			for (AgenciaModel agenciaModel : agenciaModels) {
				result.add(ModelToRepresentation.toRepresentation(agenciaModel));
			}
			return result;
		} else {
			List<AgenciaModel> agenciaModels = agenciaProvider.getAgencias(filterText, firstResult, maxResults);		
			List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
			for (AgenciaModel agenciaModel : agenciaModels) {
				result.add(ModelToRepresentation.toRepresentation(agenciaModel));
			}
			return result;
		}
		
	}

	@RolesAllowed(Roles.administrar_sucursales)
	@Override
	public Response create(AgenciaRepresentation agenciaRepresentation) {
		SucursalRepresentation sucursalRepresentation = agenciaRepresentation.getSucursal();
		String sucursal = sucursalRepresentation.getDenominacion();
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);		
		AgenciaModel model = representationToModel.createAgencia(sucursalModel, agenciaRepresentation, agenciaProvider);
		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getDenominacion()).build()).header("Access-Control-Expose-Headers", "Location").build();
	}

	@RolesAllowed(Roles.administrar_sucursales)
	@Override
	public void update(String id,
			AgenciaRepresentation agenciaRepresentation) {
		
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);		
		agenciaModel.setDenominacion(agenciaRepresentation.getDenominacion());
		agenciaModel.setUbigeo(agenciaRepresentation.getUbigeo());
		agenciaModel.setDireccion(agenciaRepresentation.getDireccion());
		agenciaModel.commit();
		
	}

	@RolesAllowed(Roles.administrar_sucursales)
	@Override
	public void remove(String id) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);		
		agenciaProvider.removeAgencia(agenciaModel);
	}
	
}
