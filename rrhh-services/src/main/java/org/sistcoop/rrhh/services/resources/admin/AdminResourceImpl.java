package org.sistcoop.rrhh.services.resources.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.sistcoop.rrhh.admin.client.resource.AdminResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Stateless
@SecurityDomain("keycloak")
public class AdminResourceImpl implements AdminResource {
	
	@Inject
	private TrabajadorUsuarioProvider trabajadorUsuarioProvider;
	
	@Context
	protected UriInfo uriInfo;

	@Override
	public void login(String username, String sucursal, String agencia) {
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioByUsuario(username);
		if(trabajadorUsuarioModel == null) {
			throw new BadRequestException();
		}
		
		TrabajadorModel trabajadorModel = trabajadorUsuarioModel.getTrabajador();
		AgenciaModel agenciaModel = trabajadorModel.getAgencia();
		SucursalModel sucursalModel = agenciaModel.getSucursal();
		
		if(!sucursal.equals(sucursalModel.getDenominacion()) || !agencia.equals(agenciaModel.getDenominacion())) {
			throw new BadRequestException();
		}
	}

	@Override
	public List<SucursalRepresentation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
