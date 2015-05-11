package org.sistcoop.rrhh.admin.client.resource;

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

import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorUsuarioRepresentation;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/trabajadores")
public interface TrabajadorResource {

	@GET
	@Path("/{id}")
	public TrabajadorRepresentation findById(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);

	@GET
	@Path("/buscar")
	public TrabajadorRepresentation findByTipoNumeroDocumento(
			@QueryParam("tipoDocumento") 
			@NotNull 
			@Size(min = 1, max = 20) 
			String tipoDocumento, 
			
			@QueryParam("numeroDocumento")
			@NotNull 
			@Pattern(regexp = "[0-9]+")
			@Size(min = 1, max = 20) String numeroDocumento);

	@PUT
	@Path("/{id}")
	public void update(
			@PathParam("id") 
			@NotNull
			@Min(value = 1) Integer id, 
			
			@NotNull
			@Valid TrabajadorRepresentation rep);

	@DELETE
	@Path("/{id}")
	public void delete(
			@PathParam("id")
			@NotNull
			@Min(value = 1) Integer id);

	@POST
	@Path("/{id}/desactivar")
	public void desactivar(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);	
	
	/**
	 * Agencia*/
	
	@GET
	@Path("/{id}/agencia")
	public AgenciaRepresentation getAgencia(
			@PathParam("id") 
			@NotNull 
			@Min(value = 1) Integer id);
	
	/**
	 * Trabajador usuarios
	 * */
	
	@GET
	@Path("/{id}/trabajadorUsuarios")
	public TrabajadorUsuarioRepresentation getTrabajadorUsuario(
			@PathParam("id") 
			@NotNull
			@Min(value = 1) Integer id);
	

	@POST
	@Path("/{id}/trabajadorUsuarios")
	public Response setTrabajadorUsuario(
			@PathParam("id") 
			@NotNull
			@Min(value = 1) Integer id, 
			
			@NotNull
			@Valid TrabajadorUsuarioRepresentation trabajadorUsuarioRepresentation);
	
}
