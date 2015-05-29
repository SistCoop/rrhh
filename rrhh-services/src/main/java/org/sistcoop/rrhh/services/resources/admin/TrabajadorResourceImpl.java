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
import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
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
public class TrabajadorResourceImpl implements TrabajadorResource {
	
	@Inject
	private KeycloakSession keycloakSession;
	
	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private RepresentationToModel representationToModel;	
	
	@Context
	protected UriInfo uriInfo;

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorRepresentation findById(String id) {				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(id);
		return ModelToRepresentation.toRepresentation(trabajadorModel);
	}

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorRepresentation findByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento) {				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByTipoNumeroDocumento(tipoDocumento, numeroDocumento);
		return ModelToRepresentation.toRepresentation(trabajadorModel);			
	}

	@Override
	@RolesAllowed(Roles.ver_trabajadores)
	public List<TrabajadorRepresentation> findAll(String sucursal,
			String agencia, Boolean estado, String filterText,
			Integer firstResult, Integer maxResults) {
		
		if (filterText == null)
			filterText = "";
		if (firstResult == null)
			firstResult = -1;
		if (maxResults == null)
			maxResults = -1;

		SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agencia);
		
		List<TrabajadorRepresentation> results = new ArrayList<>();
		List<TrabajadorModel> trabajadorModels;
		trabajadorModels = agenciaModel.getTrabajadores();

		for (TrabajadorModel trabajadorModel : trabajadorModels) {
			results.add(ModelToRepresentation.toRepresentation(trabajadorModel));
		}

		return results;
	}
	
	@RolesAllowed({ Roles.administrar_trabajadores, Roles.administrar_trabajadores_agencia })
	@Override
	public Response create(TrabajadorRepresentation trabajadorRepresentation) {			
        
        //Agencia y sucursal enviada por el usuario
        AgenciaRepresentation agenciaRepresentation = trabajadorRepresentation.getAgencia();
        SucursalRepresentation sucursalRepresentation = agenciaRepresentation.getSucursal();        
        SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursalRepresentation.getDenominacion());
        AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agenciaRepresentation.getDenominacion());
                
        //validar permisos de usuario
        keycloakSession.validarAdministrarTrabajadoresPorAgencia(agenciaModel);                   
        
        //Creando trabajador
        TrabajadorModel trabajadorModel = representationToModel.createTrabajador(agenciaModel, trabajadorRepresentation, trabajadorProvider);        
        return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorModel.getId().toString()).build();
	}
	
	@RolesAllowed({ Roles.administrar_trabajadores, Roles.administrar_trabajadores_agencia })
	@Override
	public void update(String idTrabajador, TrabajadorRepresentation trabajadorRepresentation) {	
		//Agencia y sucursal enviada por el usuario
        AgenciaRepresentation agenciaRepresentation = trabajadorRepresentation.getAgencia();
        SucursalRepresentation sucursalRepresentation = agenciaRepresentation.getSucursal();        
        SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursalRepresentation.getDenominacion());
        AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agenciaRepresentation.getDenominacion());                        
        
        //validar permisos de usuario
        keycloakSession.validarAdministrarTrabajadoresPorAgencia(agenciaModel);   
        
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);	
		trabajadorModel.setAgencia(agenciaModel);		
		trabajadorModel.commit();				
	}

	@RolesAllowed({ Roles.administrar_trabajadores, Roles.administrar_trabajadores_agencia })
	@Override
	public void remove(String idTrabajador) {			
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		AgenciaModel agenciaModel = trabajadorModel.getAgencia();
		
		//validar permisos de usuario
        keycloakSession.validarAdministrarTrabajadoresPorAgencia(agenciaModel);   
        
		trabajadorProvider.removeTrabajador(trabajadorModel);
	}
	
}
