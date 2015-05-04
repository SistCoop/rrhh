package org.sistcoop.rrhh.admin.client.resource;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotBlank;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/trabajadorUsuarios")
public interface TrabajadorUsuarioResource {

	@GET
	@Path("/{id}")
	public TrabajadorUsuarioRepresentation findById(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);

	@GET
	@Path("/usuario/{usuario}")
	public TrabajadorUsuarioRepresentation findByUsuario(
			@PathParam("usuario") 
			@NotNull 
			@NotBlank String usuario);
	
}
