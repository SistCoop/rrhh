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
import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.admin.client.resource.TrabajadoresResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.search.PagingModel;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.TrabajadorFilterProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;

@Stateless
public class TrabajadoresResourceImpl implements TrabajadoresResource {

    @PathParam("agencia")
    private String agencia;

    @Inject
    private TrabajadorProvider trabajadorProvider;

    @Inject
    private AgenciaProvider agenciaProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Context
    protected UriInfo uriInfo;

    @Inject
    private TrabajadorResource trabajadorResource;

    @Inject
    private TrabajadorFilterProvider trabajadorFilterProvider;

    private AgenciaModel getAgenciaModel() {
        return agenciaProvider.findById(agencia);
    }

    @Override
    public TrabajadorResource trabajador(String trabajador) {
        return trabajadorResource;
    }

    @Override
    public Response create(TrabajadorRepresentation trabajadorRepresentation) {
        TrabajadorModel trabajadorModel = representationToModel.createTrabajador(getAgenciaModel(),
                trabajadorRepresentation, trabajadorProvider);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId()).build())
                .header("Access-Control-Expose-Headers", "Location")
                .entity(Jsend.getSuccessJSend(trabajadorModel.getId())).build();
    }

    @Override
    public SearchResultsRepresentation<TrabajadorRepresentation> search(String tipoDocumento,
            String numeroDocumento, String filterText, Integer page, Integer pageSize) {

        // add paging
        PagingModel paging = new PagingModel();
        paging.setPage(page);
        paging.setPageSize(pageSize);

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.setPaging(paging);

        // add ordery by
        searchCriteriaBean.addOrder(trabajadorFilterProvider.getTipoDocumentoFilter(), true);

        // add filter
        searchCriteriaBean.addFilter(trabajadorFilterProvider.getIdAgenciaFilter(),
                getAgenciaModel().getId(), SearchCriteriaFilterOperator.eq);
        if (tipoDocumento != null) {
            searchCriteriaBean.addFilter(trabajadorFilterProvider.getTipoDocumentoFilter(), tipoDocumento,
                    SearchCriteriaFilterOperator.eq);
        }
        if (numeroDocumento != null) {
            searchCriteriaBean.addFilter(trabajadorFilterProvider.getNumeroDocumentoFilter(),
                    numeroDocumento, SearchCriteriaFilterOperator.eq);
        }

        // search
        SearchResultsModel<TrabajadorModel> results = trabajadorProvider.search(searchCriteriaBean,
                filterText);
        SearchResultsRepresentation<TrabajadorRepresentation> rep = new SearchResultsRepresentation<>();
        List<TrabajadorRepresentation> representations = new ArrayList<>();
        for (TrabajadorModel model : results.getModels()) {
            representations.add(ModelToRepresentation.toRepresentation(model));
        }
        rep.setTotalSize(results.getTotalSize());
        rep.setItems(representations);
        return rep;
    }

}
