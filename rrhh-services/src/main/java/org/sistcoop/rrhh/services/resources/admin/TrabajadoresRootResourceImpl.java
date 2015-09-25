package org.sistcoop.rrhh.services.resources.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.admin.client.resource.TrabajadoresRootResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.ModelDuplicateException;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.search.PagingModel;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.models.utils.RepresentationToModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.representations.idm.search.SearchResultsRepresentation;
import org.sistcoop.rrhh.services.ErrorResponse;

@Stateless
public class TrabajadoresRootResourceImpl implements TrabajadoresRootResource {

    @Inject
    private TrabajadorProvider trabajadorProvider;

    @Inject
    private TrabajadorUsuarioProvider trabajadorUsuarioProvider;

    @Inject
    private SucursalProvider sucursalProvider;

    @Inject
    private AgenciaProvider agenciaProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Context
    private UriInfo uriInfo;

    @Inject
    private TrabajadorResource trabajadorResource;

    @Override
    public TrabajadorResource trabajador(String trabajador) {
        return trabajadorResource;
    }

    @Override
    public Response create(TrabajadorRepresentation rep) {
        // Check duplicated tipoDocumento y numeroDocumento
        if (trabajadorProvider.findByTipoNumeroDocumento(rep.getTipoDocumento(), rep.getNumeroDocumento()) != null) {
            return ErrorResponse.exists("Sucursal existe con la misma denominacion");
        }

        try {
            AgenciaRepresentation agenciaRepresentation = rep.getAgencia();
            AgenciaModel agenciaModel = agenciaProvider.findById(agenciaRepresentation.getId());

            TrabajadorModel trabajadorModel = representationToModel.createTrabajador(agenciaModel, rep,
                    trabajadorProvider);
            return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorModel.getId()).build())
                    .header("Access-Control-Expose-Headers", "Location")
                    .entity(ModelToRepresentation.toRepresentation(trabajadorModel)).build();
        } catch (ModelDuplicateException e) {
            return ErrorResponse.exists("Trabajador existe con el mismo tipoDocumento y numeroDocumento");
        }
    }

    @Override
    public SearchResultsRepresentation<TrabajadorRepresentation> search(String usuario, String tipoDocumento,
            String numeroDocumento, String idSucursal, String idAgencia, String filterText, int page,
            int pageSize) {
        SearchResultsModel<TrabajadorModel> results = null;

        // find by usuario
        if (usuario != null) {
            TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.findByUsuario(usuario);
            TrabajadorModel trabajadorModel = null;
            if (trabajadorUsuarioModel != null) {
                trabajadorModel = trabajadorUsuarioModel.getTrabajador();
            }

            results = new SearchResultsModel<>();
            List<TrabajadorModel> list = new ArrayList<>();
            if (trabajadorModel != null) {
                list.add(trabajadorModel);
            }
            results.setModels(list);
            results.setTotalSize(list.size());

            SearchResultsRepresentation<TrabajadorRepresentation> rep = new SearchResultsRepresentation<>();
            List<TrabajadorRepresentation> representations = new ArrayList<>();
            for (TrabajadorModel model : results.getModels()) {
                representations.add(ModelToRepresentation.toRepresentation(model));
            }
            rep.setTotalSize(results.getTotalSize());
            rep.setItems(representations);

            return rep;
        }

        // add paging
        PagingModel paging = new PagingModel();
        paging.setPage(page);
        paging.setPageSize(pageSize);

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        searchCriteriaBean.setPaging(paging);

        if (tipoDocumento != null) {
            searchCriteriaBean.addFilter("tipoDocumento", tipoDocumento, SearchCriteriaFilterOperator.eq);
        }
        if (numeroDocumento != null) {
            searchCriteriaBean.addFilter("numeroDocumento", numeroDocumento, SearchCriteriaFilterOperator.eq);
        }

        if (idAgencia != null) {
            AgenciaModel agenciaModel = agenciaProvider.findById(idAgencia);
            if (filterText != null) {
                results = trabajadorProvider.search(agenciaModel, searchCriteriaBean, filterText);
            } else {
                results = trabajadorProvider.search(agenciaModel, searchCriteriaBean);
            }
        } else if (idSucursal != null) {
            SucursalModel sucursalModel = sucursalProvider.findById(idSucursal);
            if (filterText != null) {
                results = trabajadorProvider.search(sucursalModel, searchCriteriaBean, filterText);
            } else {
                results = trabajadorProvider.search(sucursalModel, searchCriteriaBean);
            }
        } else {
            if (filterText != null) {
                results = trabajadorProvider.search(searchCriteriaBean, filterText);
            } else {
                results = trabajadorProvider.search(searchCriteriaBean);
            }
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
