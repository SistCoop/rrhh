package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.sistcoop.rrhh.admin.client.Roles;
import org.sistcoop.rrhh.admin.client.resource.AgenciaTrabajadorResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.services.util.KeycloakSession;

@Stateless
@SecurityDomain("keycloak")
public class AgenciaTrabajadorResourceImpl implements AgenciaTrabajadorResource {

	@Inject
	private KeycloakSession keycloakSession;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Inject
	private RepresentationToModel representationToModel;
	
	@Context
	protected UriInfo uriInfo;
	
	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public List<TrabajadorRepresentation> getTrabajadores(String idAgencia,
			String filterText, Integer firstResult, Integer maxResults) {
		
		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		List<TrabajadorModel> trabajadorModels = trabajadorProvider.getTrabajadores(agenciaModel, filterText, firstResult, maxResults);
		
		List<TrabajadorRepresentation> result = new ArrayList<>();
		for (TrabajadorModel trabajadorModel : trabajadorModels) {
			result.add(ModelToRepresentation.toRepresentation(trabajadorModel));
		}
		return result;
	}

	@RolesAllowed({Roles.administrar_trabajadores, Roles.administrar_trabajadores_agencia})
	@Override
	public Response addTrabajador(String idAgencia,
			TrabajadorRepresentation trabajadorRepresentation) {

		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		TrabajadorModel trabajadorModel = representationToModel.createTrabajador(agenciaModel, trabajadorRepresentation, trabajadorProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorModel.getId()).build();
	}

	@RolesAllowed({Roles.administrar_trabajadores, Roles.administrar_trabajadores_agencia})
	@Override
	public void updateTrabajador(String idAgencia, String idTrabajador,
			TrabajadorRepresentation trabajadorRepresentation) {
		
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		if(!agenciaModel.equals(trabajadorModel.getAgencia())){
			throw new BadRequestException("Trabajador no pertenece a la agencia indicada");
		}
		
		//validar permisos
		keycloakSession.validarAdministrarTrabajadoresPorAgencia(agenciaModel);
		
		//Agencia y sucursal enviada por el usuario
        AgenciaRepresentation agenciaRepresentation = trabajadorRepresentation.getAgencia();
        SucursalRepresentation sucursalRepresentation = agenciaRepresentation.getSucursal();        
        SucursalModel newSucursalModel = sucursalProvider.getSucursalByDenominacion(sucursalRepresentation.getDenominacion());
        AgenciaModel newAgenciaModel = agenciaProvider.getAgenciaByDenominacion(newSucursalModel, agenciaRepresentation.getDenominacion());                        
        		
		trabajadorModel.setAgencia(newAgenciaModel);		
		trabajadorModel.commit();	
	}

	@RolesAllowed({Roles.administrar_trabajadores, Roles.administrar_trabajadores_agencia})
	@Override
	public void removeTrabajador(String idAgencia, String idTrabajador) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		if(!agenciaModel.equals(trabajadorModel.getAgencia())){
			throw new BadRequestException("Trabajador no pertenece a la agencia indicada");
		}
		
		//validar permisos
		keycloakSession.validarAdministrarTrabajadoresPorAgencia(agenciaModel);
				
		trabajadorProvider.removeTrabajador(trabajadorModel);
	}

}
