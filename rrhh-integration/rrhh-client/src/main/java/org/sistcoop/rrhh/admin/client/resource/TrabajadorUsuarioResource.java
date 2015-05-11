package org.sistcoop.rrhh.admin.client.resource;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotBlank;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Path("/trabajadorUsuarios")
public interface TrabajadorUsuarioResource {

	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TrabajadorUsuarioRepresentation findById(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);

	@GET
	@Path("/usuario/{usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TrabajadorUsuarioRepresentation findByUsuario(
			@PathParam("usuario") 
			@NotNull 
			@NotBlank String usuario);
	
	@DELETE
	@Path("/{id}")
	public void delete(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);
	
}
