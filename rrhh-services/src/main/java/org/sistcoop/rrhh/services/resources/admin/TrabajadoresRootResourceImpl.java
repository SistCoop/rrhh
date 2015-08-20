package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.Jsend;
import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.admin.client.resource.TrabajadoresRootResource;
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
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;

@Stateless
public class TrabajadoresRootResourceImpl implements TrabajadoresRootResource {

    @Inject
    private TrabajadorProvider trabajadorProvider;

    @Inject
    private AgenciaProvider agenciaProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Context
    private UriInfo uriInfo;

    @Inject
    private TrabajadorFilterProvider trabajadorFilterProvider;

    @Inject
    private TrabajadorResource trabajadorResource;

    @Override
    public TrabajadorResource trabajador(String trabajador) {
        return trabajadorResource;
    }

    @Override
    public Response create(TrabajadorRepresentation trabajadorRepresentation) {
        AgenciaRepresentation agenciaRepresentation = trabajadorRepresentation.getAgencia();
        AgenciaModel agenciaModel = agenciaProvider.findById(agenciaRepresentation.getId());

        TrabajadorModel trabajadorModel = representationToModel.createTrabajador(agenciaModel,
                trabajadorRepresentation, trabajadorProvider);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId()).build())
                .header("Access-Control-Expose-Headers", "Location")
                .entity(Jsend.getSuccessJSend(trabajadorModel.getId())).build();
    }

    @Override
    public SearchResultsRepresentation<TrabajadorRepresentation> search(String documento, String numero,
            String sucursal, String agencia, String filterText, int page, int pageSize) {

        // add paging
        PagingModel paging = new PagingModel();
        paging.setPage(page);
        paging.setPageSize(pageSize);

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.setPaging(paging);

        // add ordery by
        searchCriteriaBean.addOrder(trabajadorFilterProvider.getTipoDocumentoFilter(), true);

        // add filter
        if (documento != null) {
            searchCriteriaBean.addFilter(trabajadorFilterProvider.getTipoDocumentoFilter(), documento,
                    SearchCriteriaFilterOperator.eq);
        }
        if (numero != null) {
            searchCriteriaBean.addFilter(trabajadorFilterProvider.getNumeroDocumentoFilter(), numero,
                    SearchCriteriaFilterOperator.eq);
        }
        if (sucursal != null) {
            searchCriteriaBean.addFilter(trabajadorFilterProvider.getIdSucursalFilter(), sucursal,
                    SearchCriteriaFilterOperator.eq);
        }
        if (agencia != null) {
            searchCriteriaBean.addFilter(trabajadorFilterProvider.getIdAgenciaFilter(), agencia,
                    SearchCriteriaFilterOperator.eq);
        }

        SearchResultsModel<TrabajadorModel> results = null;
        if (filterText != null) {
            results = trabajadorProvider.search(searchCriteriaBean, filterText);
        } else {
            results = trabajadorProvider.search(searchCriteriaBean);
        }

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
