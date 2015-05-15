package org.sistcoop.rrhh.admin.client.resource;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Path("/sucursales/{sucursal}/agencias/{agencia}/trabajadores/{trabajador}/trabajadorUsuarios")
public interface TrabajadorUsuarioResource {

	@GET
	public TrabajadorUsuarioRepresentation getTrabajadorUsuario(
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
	public Response setTrabajadorUsuario(
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
			@Valid TrabajadorUsuarioRepresentation trabajadorUsuarioRepresentation);
	
	@GET
	@Path("/{trabajadorUsuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TrabajadorUsuarioRepresentation findById(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@PathParam("trabajador") 
			@NotNull
			@Min(value = 1) Integer idTrabajador,
			
			@PathParam("trabajadorUsuario") 
			@NotNull 
			@Min(value = 1) Integer idTrabajadorUsuario);

	@GET
	@Path("/usuario/{usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TrabajadorUsuarioRepresentation findByUsuario(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@PathParam("trabajador") 
			@NotNull
			@Min(value = 1) Integer idTrabajador,
			
			@PathParam("usuario") 
			@NotNull 
			@NotBlank String usuario);
	
	@DELETE
	@Path("/{id}")
	public void delete(
			@PathParam("sucursal") 
			@NotNull 
			@Min(value = 1) Integer idSucursal, 
			
			@PathParam("agencia") 
			@NotNull 
			@Min(value = 1) Integer idAgencia, 
			
			@PathParam("trabajador") 
			@NotNull
			@Min(value = 1) Integer idTrabajador,
			
			@PathParam("trabajadorUsuario") 
			@NotNull 
			@Min(value = 1) Integer idTrabajadorUsuario);
	
}
