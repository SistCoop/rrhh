package org.sistcoop.rrhh.admin.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/authentication")
public interface AuthenticationResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void login(@FormParam("sucursal") String sucursal, @FormParam("agencia") String agencia);

}
