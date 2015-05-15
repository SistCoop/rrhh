package org.sistcoop.rrhh.services.resources.admin;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.sistcoop.rrhh.admin.client.Roles;
import org.sistcoop.rrhh.admin.client.resource.TrabajadorUsuarioResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class TrabajadorUsuarioResourceImpl implements TrabajadorUsuarioResource {

	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private TrabajadorUsuarioProvider trabajadorUsuarioProvider;
	
	@Context
	protected UriInfo uriInfo;

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorUsuarioRepresentation findById(
			Integer idSucursal, Integer idAgencia, 
			Integer idTrabajador, Integer idTrabajadorUsuario) {
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioById(idTrabajadorUsuario);
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
		if(!trabajadorModel.equals(trabajadorUsuarioModel.getTrabajador())) {
			throw new BadRequestException();
		}
				
		return ModelToRepresentation.toRepresentation(trabajadorUsuarioModel);
	}

	@RolesAllowed(Roles.ver_trabajadores)
	@Override
	public TrabajadorUsuarioRepresentation findByUsuario(
			Integer idSucursal, Integer idAgencia, 
			Integer idTrabajador, String usuario) {
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioByUsuario(usuario);
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
		if(!trabajadorModel.equals(trabajadorUsuarioModel.getTrabajador())) {
			throw new BadRequestException();
		}
		
		return ModelToRepresentation.toRepresentation(trabajadorUsuarioModel);
	}

	@RolesAllowed(Roles.eliminar_trabajadores)
	@Override
	public void delete(Integer idSucursal, Integer idAgencia, 
			Integer idTrabajador, Integer idTrabajadorUsuario) {
		
		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioById(idTrabajadorUsuario);
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
		if(!trabajadorModel.equals(trabajadorUsuarioModel.getTrabajador())) {
			throw new BadRequestException();
		}
		
		trabajadorUsuarioProvider.removeTrabajadorUsuario(trabajadorUsuarioModel);
	}

	@Override
	public TrabajadorUsuarioRepresentation getTrabajadorUsuario(
			Integer idSucursal, Integer idAgencia, Integer idTrabajador) {

		SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);		
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}		
				
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorModel.getTrabajadorUsuarioModel();
		return ModelToRepresentation.toRepresentation(trabajadorUsuarioModel);
	}

	@Override
	public Response setTrabajadorUsuario(Integer idSucursal, Integer idAgencia,
			Integer idTrabajador,
			TrabajadorUsuarioRepresentation trabajadorUsuarioRepresentation) {

		/*SucursalModel sucursalModel = sucursalProvider.getSucursalById(idSucursal);
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(idAgencia);				
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(idTrabajador);
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioById(idTrabajadorUsuario);
		if(!sucursalModel.equals(agenciaModel.getSucursal())) {
			throw new BadRequestException();
		}
		if(!agenciaModel.equals(trabajadorModel.getAgencia())) {
			throw new BadRequestException();
		}
		if(!trabajadorModel.equals(trabajadorUsuarioModel.getTrabajador())) {
			throw new BadRequestException();
		}*/
		/*
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(id);
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorModel.getTrabajadorUsuarioModel();
		
		String usuario = trabajadorUsuarioRepresentation.getUsuario();	
		//Usuario ya existente
		if(trabajadorUsuarioModel != null) 
		{
			trabajadorUsuarioModel.setUsuario(usuario);
			trabajadorUsuarioModel.commit();
			return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorUsuarioModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorUsuarioModel.getId()).build();
		} 
		//usuario asignado por primera vez
		else 
		{
			TrabajadorUsuarioModel trabajadorUsuarioNewModel = trabajadorUsuarioProvider.addTrabajadorUsuario(trabajadorModel, usuario);
			return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorUsuarioNewModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorUsuarioNewModel.getId()).build();
		}*/
		return null;
	}
	
}
