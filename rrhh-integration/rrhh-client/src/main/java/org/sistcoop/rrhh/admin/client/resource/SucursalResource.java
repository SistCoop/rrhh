package org.sistcoop.rrhh.admin.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

public interface SucursalResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SucursalRepresentation toRepresentation();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(SucursalRepresentation sucursalRepresentation);

    @DELETE
    public Response remove();

    @Path("/agencias")
    public AgenciasResource agencias();

}
