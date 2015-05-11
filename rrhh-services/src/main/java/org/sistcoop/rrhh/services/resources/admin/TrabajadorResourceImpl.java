package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Stateless
public class TrabajadorResourceImpl implements TrabajadorResource {

	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Inject
	private TrabajadorUsuarioProvider trabajadorUsuarioProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Context
	protected UriInfo uriInfo;

	@Override
	public TrabajadorRepresentation findById(Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public TrabajadorRepresentation findByTipoNumeroDocumento(
			String tipoDocumento, String numeroDocumento) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorByTipoNumeroDocumento(tipoDocumento, numeroDocumento);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public void update(Integer id, TrabajadorRepresentation rep) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		
		AgenciaModel agenciaModel = null;
				
		AgenciaRepresentation agenciaRepresentation = rep.getAgencia();
		Integer idAgencia = agenciaRepresentation.getId();
		String codigoAgencia = agenciaRepresentation.getCodigo();
		if(idAgencia != null) {
			agenciaModel = agenciaProvider.getAgenciaById(idAgencia); 
		} else if (codigoAgencia != null) {
			agenciaModel = agenciaProvider.getAgenciaByCodigo(codigoAgencia);
		}
		
		model.setAgencia(agenciaModel);
		model.commit();				
	}

	@Override
	public void delete(Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		trabajadorProvider.removeTrabajador(model);
	}

	@Override
	public void desactivar(Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		model.desactivar();
		model.commit();
	}

	@Override
	public TrabajadorUsuarioRepresentation getTrabajadorUsuario(Integer id) {
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(id);
		TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorModel.getTrabajadorUsuarioModel();
		return ModelToRepresentation.toRepresentation(trabajadorUsuarioModel);
	}

	@Override
	public Response setTrabajadorUsuario(Integer id,
			TrabajadorUsuarioRepresentation trabajadorUsuarioRepresentation) {

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
		}								
		
	}

	@Override
	public AgenciaRepresentation getAgencia(Integer id) {
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorById(id);
		AgenciaModel agenciaModel = trabajadorModel.getAgencia();
		return ModelToRepresentation.toRepresentation(agenciaModel);
	}
	
}
