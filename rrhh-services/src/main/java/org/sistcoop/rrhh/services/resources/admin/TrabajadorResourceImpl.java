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
import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
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
	public TrabajadorRepresentation findById(Integer idSucursal, Integer idAgencia, Integer id) {		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(id);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
		
		return ModelToRepresentation.toRepresentation(trabajadorModel);
	}

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorRepresentation findByTipoNumeroDocumento(
			Integer idSucursal, Integer idAgencia,
			String tipoDocumento, String numeroDocumento) {
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByTipoNumeroDocumento(tipoDocumento, numeroDocumento);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
				
		return ModelToRepresentation.toRepresentation(trabajadorModel);
	}

	@RolesAllowed(Roles.administrar_trabajadores)
	@Override
	public void update(Integer idSucursal, Integer idAgencia,
			Integer idTrabajador, TrabajadorRepresentation rep) {
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
				
		
		AgenciaModel agenciaNuevaModel = null;
				
		AgenciaRepresentation agenciaRepresentation = rep.getAgencia();
		Integer idNuevaAgencia = agenciaRepresentation.getId();
		String codigoAgencia = agenciaRepresentation.getCodigo();
		if(idNuevaAgencia != null) {
			agenciaNuevaModel = agenciaProvider.getAgenciaById(idNuevaAgencia); 
		} else if (codigoAgencia != null) {
			agenciaNuevaModel = agenciaProvider.getAgenciaByCodigo(codigoAgencia);
		}
		
		trabajadorModel.setAgencia(agenciaNuevaModel);
		trabajadorModel.commit();				
	}

	@RolesAllowed(Roles.eliminar_trabajadores)
	@Override
	public void delete(Integer idSucursal, Integer idAgencia, Integer idTrabajador) {
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
		
		trabajadorProvider.removeTrabajador(trabajadorModel);
	}

	@RolesAllowed(Roles.eliminar_trabajadores)
	@Override
	public void desactivar(Integer idSucursal, Integer idAgencia, Integer idTrabajador) {
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
				
		trabajadorModel.desactivar();
		trabajadorModel.commit();
	}

	@Override
	public List<TrabajadorRepresentation> getTrabajadores(Integer idSucursal,
			Integer idAgencia, Boolean estado, String filterText,
			Integer firstResult, Integer maxResults) {
		
		if (filterText == null)
			filterText = "";
		if (firstResult == null)
			firstResult = -1;
		if (maxResults == null)
			maxResults = -1;

		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		
		List<TrabajadorRepresentation> results = new ArrayList<>();
		List<TrabajadorModel> trabajadorModels;
		if (estado == null) {
			trabajadorModels = agenciaModel.getTrabajadores(filterText, firstResult, maxResults);
		} else {
			trabajadorModels = agenciaModel.getTrabajadores(estado);
		}

		for (TrabajadorModel trabajadorModel : trabajadorModels) {
			results.add(ModelToRepresentation.toRepresentation(trabajadorModel));
		}

		return results;
	}

	@Override
	public Response addTrabajador(Integer idSucursal, Integer idAgencia,
			TrabajadorRepresentation rep) {

		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);
		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		
		TrabajadorModel trabajadorModel = trabajadorProvider.addTrabajador(agenciaModel, rep.getTipoDocumento(), rep.getNumeroDocumento());		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorModel.getId()).build();
		
	}
	
}
