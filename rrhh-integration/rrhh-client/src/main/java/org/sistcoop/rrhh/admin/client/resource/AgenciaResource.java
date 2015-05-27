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

import org.hibernate.validator.constraints.NotBlank;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;

@Path("/sucursales/{sucursal}/agencias")
public interface AgenciaResource {
	
	@GET
	@Path("/{agencia}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AgenciaRepresentation getAgenciaById(
			@PathParam("sucursal") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String sucursal,
			
			@PathParam("agencia") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String agencia);	
	
	@GET	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgenciaRepresentation> getAgencias(
			@PathParam("sucursal") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String sucursal, 
			
			@QueryParam("filterText")
			@Size(min = 0, max = 100) String filterText, 
			
			@QueryParam("firstResult") 
			@Min(value = 0) Integer firstResult, 
			
			@QueryParam("maxResults") 
			@Min(value = 1) Integer maxResults);
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAgencia(
			@PathParam("sucursal") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String sucursal, 
			
			@NotNull
			@Valid AgenciaRepresentation agenciaRepresentation);
	
	@PUT
	@Path("/{agencia}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateAgencia(
			@PathParam("sucursal") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String sucursal,
			
			@PathParam("agencia") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String agencia, 
			
			@NotNull
			@Valid AgenciaRepresentation agenciaRepresentation);

	@DELETE
	@Path("/{agencia}")
	public void remove(
			@PathParam("sucursal") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String sucursal,
			
			@PathParam("agencia") 
			@NotNull
			@Size(min = 1, max = 60)
			@NotBlank String agencia);	
	
}
