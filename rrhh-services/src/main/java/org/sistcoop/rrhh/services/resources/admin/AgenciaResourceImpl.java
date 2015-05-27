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

@Stateless
@SecurityDomain("keycloak")
public class AgenciaResourceImpl implements AgenciaResource {

	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private RepresentationToModel representationToModel;
	
	@Context
	protected UriInfo uriInfo;
	
	@RolesAllowed(Roles.ver_agencias)
	@Override
	public AgenciaRepresentation getAgenciaById(String sucursal, String agencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);		
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agencia);
		return ModelToRepresentation.toRepresentation(agenciaModel);
	}

	@RolesAllowed(Roles.ver_agencias)
	@Override
	public List<AgenciaRepresentation> getAgencias(String sucursal, String filterText, Integer firstResult, Integer maxResults) {	
		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);				
		
		List<AgenciaModel> agenciaModels = agenciaProvider.getAgencias(sucursalModel, filterText, firstResult, maxResults);		
		List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
		for (AgenciaModel agenciaModel : agenciaModels) {
			result.add(ModelToRepresentation.toRepresentation(agenciaModel));
		}
		return result;
	}

	@RolesAllowed(Roles.administrar_agencias)
	@Override
	public Response addAgencia(String sucursal, AgenciaRepresentation agenciaRepresentation) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);		
		AgenciaModel model = representationToModel.createAgencia(sucursalModel, agenciaRepresentation, agenciaProvider);
		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getDenominacion()).build()).header("Access-Control-Expose-Headers", "Location").build();
	}
	
	@RolesAllowed(Roles.administrar_agencias)
	@Override
	public void updateAgencia(String sucursal, String agencia, AgenciaRepresentation agenciaRepresentation) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agencia);
		
		agenciaModel.setDenominacion(agenciaRepresentation.getDenominacion());
		agenciaModel.setUbigeo(agenciaRepresentation.getUbigeo());
		agenciaModel.setDireccion(agenciaRepresentation.getDireccion());
		agenciaModel.commit();
		
	}

	@RolesAllowed(Roles.administrar_agencias)
	@Override
	public void remove(String sucursal, String agencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agencia);		
		agenciaProvider.removeAgencia(agenciaModel);
	}

}
