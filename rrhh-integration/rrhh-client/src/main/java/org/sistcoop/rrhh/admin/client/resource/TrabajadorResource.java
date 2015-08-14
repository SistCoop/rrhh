package org.sistcoop.rrhh.admin.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

public interface TrabajadorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TrabajadorRepresentation trabajador();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(TrabajadorRepresentation trabajadorRepresentation);

    @PUT
    @Path("/usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUsuario(TrabajadorRepresentation trabajadorRepresentation);

    @DELETE
    @Path("/usuario")
    public void removeUsuario();

    @DELETE
    public void remove();

}
