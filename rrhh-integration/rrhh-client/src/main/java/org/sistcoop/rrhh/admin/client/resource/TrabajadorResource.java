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
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/sucursales/{sucursal}/agencias/{agencia}/trabajadores")
public interface TrabajadorResource {

	@GET	
	public List<TrabajadorRepresentation> getTrabajadores(
			
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@QueryParam("estado") Boolean estado, 
			
			@QueryParam("filterText")
			@Size(min = 0, max = 100) String filterText, 
			
			@QueryParam("firstResult") 
			@Min(value = 0) Integer firstResult, 
			
			@QueryParam("maxResults") 
			@Min(value = 1) Integer maxResults);
	
	@POST	
	public Response addTrabajador(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@NotNull
			@Valid TrabajadorRepresentation rep);
	
	@GET
	@Path("/{trabajador}")
	public TrabajadorRepresentation findById(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia,
			
			@PathParam("trabajador") 
			@NotNull 
			@Min(value = 1) Integer idTrabajador);

	@GET
	@Path("/buscar")
	public TrabajadorRepresentation findByTipoNumeroDocumento(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@QueryParam("tipoDocumento") 
			@NotNull 
			@Size(min = 1, max = 20) 
			String tipoDocumento, 
			
			@QueryParam("numeroDocumento")
			@NotNull 
			@Pattern(regexp = "[0-9]+")
			@Size(min = 1, max = 20) String numeroDocumento);

	@PUT
	@Path("/{trabajador}")
	public void update(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia,
			
			@PathParam("trabajador") 
			@NotNull 
			@Min(value = 1) Integer idTrabajador, 
			
			@NotNull
			@Valid TrabajadorRepresentation rep);

	@DELETE
	@Path("/{trabajador}")
	public void delete(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia,
			
			@PathParam("trabajador") 
			@NotNull 
			@Min(value = 1) Integer idTrabajador);

	@POST
	@Path("/{trabajador}/desactivar")
	public void desactivar(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia,
			
			@PathParam("trabajador")
			@NotNull 
			@Min(value = 1) Integer idTrabajador);
	
}
