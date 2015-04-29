package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

public class AgenciaResourceImpl implements AgenciaResource {

	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private TrabajadorProvider trabajadorProvider;
	
	@Context
	protected UriInfo uriInfo;
	
	@Override
	public AgenciaRepresentation getAgenciaById(Integer id) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public AgenciaRepresentation getAgenciaByCodigo(String codigo) {
		AgenciaModel model = agenciaProvider.getAgenciaByCodigo(codigo);
		return ModelToRepresentation.toRepresentation(model);
	}

	@Override
	public void updateAgencia(Integer id,
			AgenciaRepresentation agenciaRepresentation) {
		
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		model.setAbreviatura(agenciaRepresentation.getAbreviatura());
		model.setDenominacion(agenciaRepresentation.getDenominacion());
		model.setUbigeo(agenciaRepresentation.getUbigeo());
		model.setDireccion(agenciaRepresentation.getDireccion());
		model.commit();
		
	}

	@Override
	public void desactivar(Integer id) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		model.desactivar();
		model.commit();
	}

	@Override
	public List<TrabajadorRepresentation> getTrabajadores(Integer id,
			Boolean estado, String filterText, Integer firstResult,
			Integer maxResults) {

		/*if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		List<TrabajadorRepresentation> results = new ArrayList<TrabajadorRepresentation>();
		List<TrabajadorModel> trabajadorModels;
		if (estado == null) {
			
		} else {
			
		}
		
		for (TrabajadorModel trabajadorModel : trabajadorModels) {
			results.add(ModelToRepresentation.toRepresentation(trabajadorModel));
		}
		
		return results;*/
		return null;
		
	}
	
	@Override
	public Response addTrabajador(Integer id, TrabajadorRepresentation rep) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		TrabajadorModel trabajadorModel = trabajadorProvider.addTrabajador(model, rep.getTipoDocumento(), rep.getNumeroDocumento());		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(trabajadorModel.getId()).build();
	}

}
