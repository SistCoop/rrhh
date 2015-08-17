package org.sistcoop.rrhh.admin.client.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Path("/session/account")
public interface SessionResource {

    @GET
    @Path("/sucursal")
    @Produces(MediaType.APPLICATION_JSON)
    public SucursalRepresentation getSucursal();

    @GET
    @Path("/agencia")
    @Produces(MediaType.APPLICATION_JSON)
    public AgenciaRepresentation getAgencia();

}
