package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.Jsend;
import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.admin.client.resource.AgenciasResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.search.PagingModel;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.AgenciaFilterProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;

@Stateless
public class AgenciasResourceImpl implements AgenciasResource {

    @PathParam("sucursal")
    private String sucursal;

    @Inject
    private SucursalProvider sucursalProvider;

    @Inject
    private AgenciaProvider agenciaProvider;

    @Inject
    private AgenciaFilterProvider agenciaFilterProvider;

    @Inject
    private AgenciaResource agenciaResource;

    @Inject
    private RepresentationToModel representationToModel;

    @Context
    private UriInfo uriInfo;

    private SucursalModel getSucursalModel() {
        return sucursalProvider.findById(sucursal);
    }

    @Override
    public AgenciaResource agencia(String agencia) {
        return agenciaResource;
    }

    @Override
    public Response create(AgenciaRepresentation agenciaRepresentation) {
        AgenciaModel agenciaModel = representationToModel.createAgencia(getSucursalModel(),
                agenciaRepresentation, agenciaProvider);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(agenciaModel.getId()).build())
                .header("Access-Control-Expose-Headers", "Location")
                .entity(Jsend.getSuccessJSend(agenciaModel.getId())).build();
    }

    @Override
    public SearchResultsRepresentation<AgenciaRepresentation> search(String denominacion, String filterText,
            Integer page, Integer pageSize) {

        // add paging
        PagingModel paging = new PagingModel();
        paging.setPage(page);
        paging.setPageSize(pageSize);

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.setPaging(paging);

        // add ordery by
        searchCriteriaBean.addOrder(agenciaFilterProvider.getDenominacionFilter(), true);

        // add filter
        searchCriteriaBean.addFilter(agenciaFilterProvider.getIdSucursalFilter(), getSucursalModel().getId(),
                SearchCriteriaFilterOperator.eq);
        if (denominacion != null) {
            searchCriteriaBean.addFilter(agenciaFilterProvider.getDenominacionFilter(), denominacion,
                    SearchCriteriaFilterOperator.eq);
        }

        // search
        SearchResultsModel<AgenciaModel> results = agenciaProvider.search(searchCriteriaBean, filterText);
        SearchResultsRepresentation<AgenciaRepresentation> rep = new SearchResultsRepresentation<>();
        List<AgenciaRepresentation> representations = new ArrayList<>();
        for (AgenciaModel model : results.getModels()) {
            representations.add(ModelToRepresentation.toRepresentation(model));
        }
        rep.setTotalSize(results.getTotalSize());
        rep.setItems(representations);
        return rep;
    }

}
