package org.sistcoop.rrhh.admin.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;

public interface AgenciaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AgenciaRepresentation agencia();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(AgenciaRepresentation agenciaRepresentation);

    @DELETE
    public void remove();

    @Path("/trabajadores")
    public TrabajadoresResource trabajadores();

}
