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
@Consumes(MediaType.APPLICATION_JSON)
public interface TrabajadoresResource {

    @Path("/{trabajador}")
    public TrabajadorResource trabajador(@PathParam("trabajador") String trabajador);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TrabajadorRepresentation trabajadorRepresentation);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<TrabajadorRepresentation> search(
            @QueryParam("sucursal") String sucursal, @QueryParam("agencia") String agencia,
            @QueryParam("tipoDocumento") String tipoDocumento,
            @QueryParam("numeroDocumento") String numeroDocumento,
            @QueryParam("filterText") @DefaultValue(value = "") String filterText,
            @QueryParam("page") @DefaultValue(value = "") Integer page,
            @QueryParam("pageSize") @DefaultValue(value = "20") Integer pageSize);

}
