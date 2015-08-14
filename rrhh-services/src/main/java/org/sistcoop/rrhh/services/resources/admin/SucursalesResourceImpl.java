package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.Jsend;
import org.sistcoop.rrhh.admin.client.resource.SucursalResource;
import org.sistcoop.rrhh.admin.client.resource.SucursalesResource;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.search.PagingModel;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.SucursalFilterProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;

@Stateless
public class SucursalesResourceImpl implements SucursalesResource {

    @Inject
    private SucursalProvider sucursalProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Context
    private UriInfo uriInfo;

    @Inject
    private SucursalResource sucursalResource;

    @Inject
    private SucursalFilterProvider sucursalFilterProvider;

    @Override
    public SucursalResource sucursal(String sucursal) {
        return sucursalResource;
    }

    @Override
    public Response create(SucursalRepresentation sucursalRepresentation) {
        SucursalModel sucursalModel = representationToModel.createSucursal(sucursalRepresentation,
                sucursalProvider);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(sucursalModel.getId()).build())
                .header("Access-Control-Expose-Headers", "Location")
                .entity(Jsend.getSuccessJSend(sucursalModel.getId())).build();
    }

    @Override
    public SearchResultsRepresentation<SucursalRepresentation> search(String denominacion, String filterText,
            Integer page, Integer pageSize) {

        // add paging
        PagingModel paging = new PagingModel();
        paging.setPage(page);
        paging.setPageSize(pageSize);

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.setPaging(paging);

        // add ordery by
        searchCriteriaBean.addOrder(sucursalFilterProvider.getDenominacionFilter(), true);

        // add filter
        if (denominacion != null) {
            searchCriteriaBean.addFilter(sucursalFilterProvider.getDenominacionFilter(), denominacion,
                    SearchCriteriaFilterOperator.eq);
        }

        // search
        SearchResultsModel<SucursalModel> results = sucursalProvider.search(searchCriteriaBean, filterText);
        SearchResultsRepresentation<SucursalRepresentation> rep = new SearchResultsRepresentation<>();
        List<SucursalRepresentation> representations = new ArrayList<>();
        for (SucursalModel model : results.getModels()) {
            representations.add(ModelToRepresentation.toRepresentation(model));
        }
        rep.setTotalSize(results.getTotalSize());
        rep.setItems(representations);
        return rep;
    }

}
