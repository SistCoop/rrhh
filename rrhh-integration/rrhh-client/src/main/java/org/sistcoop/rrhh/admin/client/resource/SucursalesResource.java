package org.sistcoop.rrhh.admin.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;

/**
 * @author carlosthe19916@gmail.com
 */
@Path("/sucursales")
@Consumes(MediaType.APPLICATION_JSON)
public interface SucursalesResource {

    @Path("/{sucursal}")
    public SucursalResource sucursal(@PathParam("sucursal") String sucursal);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(SucursalRepresentation sucursalRepresentation);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<SucursalRepresentation> search(
            @QueryParam("denominacion") String denominacion,
            @QueryParam("filterText") @DefaultValue(value = "") String filterText,
            @QueryParam("page") @DefaultValue(value = "") Integer page,
            @QueryParam("pageSize") @DefaultValue(value = "20") Integer pageSize);

}
