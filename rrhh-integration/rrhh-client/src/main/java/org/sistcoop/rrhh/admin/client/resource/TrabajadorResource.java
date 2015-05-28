package org.sistcoop.rrhh.admin.client.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

@Path("/trabajadores")
public interface TrabajadorResource {
	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/{id}")
	public TrabajadorRepresentation findById(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer idTrabajador);

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/buscar")
	public TrabajadorRepresentation findByTipoNumeroDocumento(
			@QueryParam("sucursal") 		
			@Size(min = 1, max = 60) String sucursal,
			
			@QueryParam("agencia") 		
			@Size(min = 1, max = 60) String agencia, 
			
			@QueryParam("tipoDocumento") 
			@NotNull 
			@Size(min = 1, max = 20) 
			String tipoDocumento, 
			
			@QueryParam("numeroDocumento")
			@NotNull 
			@Pattern(regexp = "[0-9]+")
			@Size(min = 1, max = 20) String numeroDocumento);

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@GET	
	public List<TrabajadorRepresentation> getTrabajadores(
			
			@QueryParam("sucursal") 		
			@Size(min = 1, max = 60) String sucursal,
			
			@QueryParam("agencia") 		
			@Size(min = 1, max = 60) String agencia, 
			
			@QueryParam("estado") Boolean estado, 
			
			@QueryParam("filterText")
			@Size(min = 0, max = 100) String filterText, 
			
			@QueryParam("firstResult") 
			@Min(value = 0) Integer firstResult, 
			
			@QueryParam("maxResults") 
			@Min(value = 1) Integer maxResults);
	
	@PUT
	@Path("/{id}")
	public void update(			
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer idTrabajador, 
			
			@NotNull
			@Valid TrabajadorRepresentation rep);

	@DELETE
	@Path("/{id}")
	public void delete(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer idTrabajador);

	@POST
	@Path("/{id}/desactivar")
	public void desactivar(
			@PathParam("id")
			@NotNull 
			@Min(value = 1) Integer idTrabajador);
	
}
