package org.sistcoop.rrhh.admin.client.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Path("/sucursales")
public interface SucursalResource {
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SucursalRepresentation findById(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SucursalRepresentation> findAll(
			@QueryParam("abreviatura") 			
			@Size(min = 1, max = 60) String abreviatura,
			
			@QueryParam("denominacion") 			
			@Size(min = 1, max = 60) String denominacion,
			
			@QueryParam("estado") Boolean estado,
			
			@QueryParam("filterText")
			@Size(min = 0, max = 100) String filterText, 
			
			@QueryParam("firstResult") 
			@Min(value = 0) Integer firstResult, 
			
			@QueryParam("maxResults") 
			@Min(value = 1) Integer maxResults);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(
			@NotNull 
			@Valid SucursalRepresentation rep);

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void update(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id, 
			
			@NotNull
			@Valid SucursalRepresentation rep);

	@POST
	@Path("/{id}/desactivar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void desactivar(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);
	
	@DELETE
	@Path("/{id}")
	public void delete(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);

}
