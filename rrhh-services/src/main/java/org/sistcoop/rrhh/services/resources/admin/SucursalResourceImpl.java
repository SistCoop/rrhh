package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.SucursalResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

public class SucursalResourceImpl implements SucursalResource {

	@Inject
	private SucursalProvider sucursalProvider;
	
	@Inject
	private AgenciaProvider agenciaProvider;
	
	@Inject
	private RepresentationToModel representationToModel;
	
	@Context
	protected UriInfo uriInfo;

	@Override
	public List<SucursalRepresentation> findAll(Boolean estado,
			String filterText, Integer firstResult, Integer maxResults) {

		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		List<SucursalRepresentation> results = new ArrayList<SucursalRepresentation>();
		List<SucursalModel> sucursalModels;
		if (estado == null) {
			sucursalModels = sucursalProvider.getSucursales(filterText, firstResult, maxResults);
		} else {
			sucursalModels = sucursalProvider.getSucursales(estado);
		}
		
		for (SucursalModel sucursalModel : sucursalModels) {
			results.add(ModelToRepresentation.toRepresentation(sucursalModel));
		}
		
		return results;
	}

	@Override
	public SucursalRepresentation findById(Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		SucursalRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@Override
	public SucursalRepresentation findByDenominacion(String denominacion) {
		SucursalModel model = sucursalProvider.getSucursalByDenominacion(denominacion);
		SucursalRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@Override
	public Response create(SucursalRepresentation rep) {		
		SucursalModel model = representationToModel.createSucursal(rep, sucursalProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(model.getId()).build();
	}

	@Override
	public void update(Integer id, SucursalRepresentation rep) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		model.setAbreviatura(rep.getAbreviatura());
		model.setDenominacion(rep.getDenominacion());
		model.commit();	
	}

	@Override
	public void delete(Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		sucursalProvider.removeSucursal(model);
	}

	@Override
	public void desactivar(Integer id) {
		SucursalModel model = sucursalProvider.getSucursalById(id);
		model.desactivar();
		model.commit();
	}

	@Override
	public List<AgenciaRepresentation> getAgencias(Integer id, Boolean estado,
			String filterText, Integer firstResult, Integer maxResults) {

		if(filterText == null)
			filterText = "";
		if(firstResult == null)
			firstResult = -1;
		if(maxResults == null)
			maxResults = -1;
		
		SucursalModel model = sucursalProvider.getSucursalById(id);
		List<AgenciaModel> agenciaModels = model.getAgencias(filterText, firstResult, maxResults);
		List<AgenciaRepresentation> result = new ArrayList<AgenciaRepresentation>();
		for (AgenciaModel agenciaModel : agenciaModels) {
			result.add(ModelToRepresentation.toRepresentation(agenciaModel));
		}
		return result;
	}

	@Override
	public Response addAgencia(Integer id,
			AgenciaRepresentation agenciaRepresentation) {

		SucursalModel sucursalModel = sucursalProvider.getSucursalById(id);
		AgenciaModel model = agenciaProvider.addAgencia(
				sucursalModel, agenciaRepresentation.getCodigo(), 
				agenciaRepresentation.getAbreviatura(), 
				agenciaRepresentation.getDenominacion(), 
				agenciaRepresentation.getUbigeo(), 
				agenciaRepresentation.getDireccion());
		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(model.getId()).build();
	}
	
}
