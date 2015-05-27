package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
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
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class TrabajadorResourceImpl implements TrabajadorResource {

	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
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
	
	@RolesAllowed(Roles.administrar_trabajadores)
	@Override
	public void update(Integer idTrabajador, TrabajadorRepresentation rep) {						
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);			
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
	
}
