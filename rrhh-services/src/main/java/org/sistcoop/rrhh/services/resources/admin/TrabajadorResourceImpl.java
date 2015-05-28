package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.sistcoop.rrhh.admin.client.Roles;
import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class TrabajadorResourceImpl implements TrabajadorResource {

	@Context
    private HttpRequest httpRequest;
	
	@Context 
	private SecurityContext securityContext;
	
	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private TrabajadorUsuarioProvider trabajadorUsuarioProvider;
	
	@Inject
	private RepresentationToModel representationToModel;	
	
	@Context
	protected UriInfo uriInfo;

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorRepresentation findById(Integer id) {				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(id);
		return ModelToRepresentation.toRepresentation(trabajadorModel);
	}

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorRepresentation findByTipoNumeroDocumento(String sucursal,
			String agencia, String tipoDocumento, String numeroDocumento) {
		
		SucursalModel sucursalModel =  null; 
		if(sucursal != null) {
			sucursalProvider.getSucursalByDenominacion(sucursal);
		}
		
		AgenciaModel agenciaModel = null;
		if(agencia != null) {
			agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agencia);
		}
		
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByTipoNumeroDocumento(tipoDocumento, numeroDocumento);
		if(trabajadorModel != null) {			
			if (agencia != null) {
				if (!agenciaModel.equals(trabajadorModel.getAgencia())) {
					return null;
				}
			}			
		} 
		
		return ModelToRepresentation.toRepresentation(trabajadorModel);	
		
	}

	@Override
	@RolesAllowed(Roles.ver_trabajadores)
	public List<TrabajadorRepresentation> getTrabajadores(String sucursal,
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
	public Response addTrabajador(TrabajadorRepresentation trabajadorRepresentation) {			
        
        //Agencia y sucursal enviada por el usuario
        AgenciaRepresentation agenciaRepresentation = trabajadorRepresentation.getAgencia();
        SucursalRepresentation sucursalRepresentation = agenciaRepresentation.getSucursal();        
        SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursalRepresentation.getDenominacion());
        AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agenciaRepresentation.getDenominacion());
                
        validarAdministrarTrabajadoresPorAgencia(sucursalModel, agenciaModel);                    
        
        //Creando trabajador
        TrabajadorModel trabajadorModel = representationToModel.createTrabajador(agenciaModel, trabajadorRepresentation, trabajadorProvider);        
        return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorModel.getId().toString()).build();
	}
	
	@RolesAllowed(Roles.administrar_trabajadores)
	@Override
	public void update(Integer idTrabajador, TrabajadorRepresentation trabajadorRepresentation) {	
		//Agencia y sucursal enviada por el usuario
        AgenciaRepresentation agenciaRepresentation = trabajadorRepresentation.getAgencia();
        SucursalRepresentation sucursalRepresentation = agenciaRepresentation.getSucursal();        
        SucursalModel sucursalModel = sucursalProvider.getSucursalByDenominacion(sucursalRepresentation.getDenominacion());
        AgenciaModel agenciaModel = agenciaProvider.getAgenciaByDenominacion(sucursalModel, agenciaRepresentation.getDenominacion());                        
        
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);	
		trabajadorModel.setAgencia(agenciaModel);		
		trabajadorModel.commit();				
	}

	@RolesAllowed(Roles.administrar_trabajadores)
	@Override
	public void delete(Integer idTrabajador) {			
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		trabajadorProvider.removeTrabajador(trabajadorModel);
	}

	@RolesAllowed(Roles.administrar_trabajadores)
	@Override
	public void desactivar(Integer idTrabajador) {			
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);	
		trabajadorModel.desactivar();
		trabajadorModel.commit();
	}
	
	private void validarAdministrarTrabajadoresPorAgencia(SucursalModel sucursalModel, AgenciaModel agenciaModel){
		//keycloak username
        KeycloakSecurityContext securityContextKeycloak = (KeycloakSecurityContext) httpRequest.getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken accessToken = securityContextKeycloak.getToken();       
        
		if(securityContext.isUserInRole(Roles.administrar_trabajadores)){
        	//crear trababajador
        } else if(securityContext.isUserInRole(Roles.administrar_trabajadores_agencia)){
        	//verificar que el usuario tenga la agencia y sucursal correcta
        	String username = accessToken.getPreferredUsername();
        	TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioByUsuario(username);
        	TrabajadorModel trabajadorModel = trabajadorUsuarioModel.getTrabajador();
        	AgenciaModel agenciaDelUsuarioModel = trabajadorModel.getAgencia();
        	SucursalModel sucursalDelUsuarioModel = agenciaDelUsuarioModel.getSucursal();
        	if(!agenciaDelUsuarioModel.equals(agenciaModel)) {
        		throw new InternalServerErrorException("El usuario no puede crear trabajadores en esta agencia");
        	}
        	if(!sucursalDelUsuarioModel.equals(sucursalModel)) {
        		throw new InternalServerErrorException("El usuario no puede crear trabajadores en esta sucursal");
        	}
        } else {        	
        	throw new InternalServerErrorException();
        }   
	}
	
}
