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

import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;

/**
 * @author carlosthe19916@gmail.com
 */
@Path("/trabajadores")
@Consumes(MediaType.APPLICATION_JSON)
public interface TrabajadoresRootResource {

    @Path("/{trabajador}")
    public TrabajadorResource trabajador(@PathParam("trabajador") String trabajador);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TrabajadorRepresentation trabajadorRepresentation);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<TrabajadorRepresentation> search(
            @QueryParam("usuario") String usuario, 
            @QueryParam("documento") String documento, @QueryParam("numero") String numero,
            @QueryParam("sucursal") String sucursal, @QueryParam("agencia") String agencia,
            @QueryParam("filterText") String filterText,
            @QueryParam("page") @DefaultValue(value = "1") int page,
            @QueryParam("pageSize") @DefaultValue(value = "20") int pageSize);

}
