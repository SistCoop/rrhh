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
import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class AgenciaResourceImpl implements AgenciaResource {

	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Context
	protected UriInfo uriInfo;
	
	@RolesAllowed(Roles.ver_agencias)
	@Override
	public AgenciaRepresentation getAgenciaById(Integer idSucursal, Integer idAgencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);		
		
		if(sucursalModel.equals(agenciaModel.getSucursal())) {
			return ModelToRepresentation.toRepresentation(agenciaModel);	
		} else {
			throw new BadRequestException();
		}
	}

	@RolesAllowed(Roles.ver_agencias)
	@Override
	public List<AgenciaRepresentation> getAgencias(Integer idSucursal, String codigoAgencia, Boolean estado,
			String filterText, Integer firstResult, Integer maxResults) {

		//si codigo agencia existe
		if(codigoAgencia != null){			
						
			AgenciaModel agenciaModel = agenciaProvider.getAgenciaByCodigo(codigoAgencia);
			
			//no se encontro la agencia
			if(agenciaModel == null) {
				return new ArrayList<>(0);
			}
			
			//agencia encontrada
			List<AgenciaRepresentation> result = new ArrayList<>(1);
			if(agenciaModel != null) {					
				result.add( ModelToRepresentation.toRepresentation(agenciaModel));
			}
			return result;	
			
		}		
		
		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		SucursalModel model = sucursalProvider.getSucursalById(idSucursal);
		List<AgenciaModel> agenciaModels = model.getAgencias(filterText, firstResult, maxResults);
		List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
		for (AgenciaModel agenciaModel : agenciaModels) {
			result.add(ModelToRepresentation.toRepresentation(agenciaModel));
		}
		return result;
	}

	@RolesAllowed(Roles.administrar_agencias)
	@Override
	public Response addAgencia(Integer idSucursal, AgenciaRepresentation agenciaRepresentation) {

		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel model = agenciaProvider.addAgencia(
				sucursalModel, agenciaRepresentation.getCodigo(), 
				agenciaRepresentation.getAbreviatura(), 
				agenciaRepresentation.getDenominacion(), 
				agenciaRepresentation.getUbigeo(), 
				agenciaRepresentation.getDireccion());
		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(model.getId()).build();
	}
	
	@RolesAllowed(Roles.administrar_agencias)
	@Override
	public void updateAgencia(Integer idSucursal, Integer idAgencia, AgenciaRepresentation agenciaRepresentation) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();	
		}
		
		agenciaModel.setAbreviatura(agenciaRepresentation.getAbreviatura());
		agenciaModel.setDenominacion(agenciaRepresentation.getDenominacion());
		agenciaModel.setUbigeo(agenciaRepresentation.getUbigeo());
		agenciaModel.setDireccion(agenciaRepresentation.getDireccion());
		agenciaModel.commit();
		
	}

	@RolesAllowed(Roles.eliminar_agencias)
	@Override
	public void desactivar(Integer idSucursal, Integer idAgencia) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();	
		}
		
		agenciaModel.desactivar();
		agenciaModel.commit();
	}

}
