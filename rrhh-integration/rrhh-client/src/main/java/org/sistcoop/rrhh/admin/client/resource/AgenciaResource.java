package org.sistcoop.rrhh.admin.client.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/sucursales/{sucursal}/agencias")
public interface AgenciaResource {
	
	@GET
	@Path("/{agencia}")
	public AgenciaRepresentation getAgenciaById(
			@PathParam("sucursal") 
			@NotNull
			@Min(value = 1) Integer idSucursal,
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia);	
	
	@GET	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgenciaRepresentation> getAgencias(
			
			@PathParam("sucursal") 
			@NotNull
			@Min(value = 1) Integer idSucursal, 
			
			@QueryParam("codigo") 						
			@Size(min = 1, max = 20) String codigoAgencia,
			
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
	public Response addAgencia(
			@PathParam("sucursal") 
			@NotNull
			@Min(value = 1) Integer idSucursal, 
			
			@NotNull
			@Valid AgenciaRepresentation agenciaRepresentation);
	
	@PUT
	@Path("/{agencia}")
	public void updateAgencia(
			@PathParam("sucursal") 
			@NotNull
			@Min(value = 1) Integer idSucursal,
			
			@PathParam("agencia")
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@NotNull
			@Valid AgenciaRepresentation agenciaRepresentation);

	@POST
	@Path("/{agencia}/desactivar")
	public void desactivar(
			@PathParam("sucursal") 
			@NotNull
			@Min(value = 1) Integer idSucursal,
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia);	
	
}
