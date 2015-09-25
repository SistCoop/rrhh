package org.sistcoop.rrhh.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.provider.Provider;

@Local
public interface TrabajadorProvider extends Provider {

    TrabajadorModel create(AgenciaModel agencia, String tipoDocumento, String numeroDocumento);

    boolean remove(TrabajadorModel trabajador);

    TrabajadorModel findById(String id);

    TrabajadorModel findByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento);

    List<TrabajadorModel> getAll(AgenciaModel agencia);

    SearchResultsModel<TrabajadorModel> search(SearchCriteriaModel criteria);

    SearchResultsModel<TrabajadorModel> search(SearchCriteriaModel criteria, String filterText);

    SearchResultsModel<TrabajadorModel> search(SucursalModel sucursal, SearchCriteriaModel criteria);

    SearchResultsModel<TrabajadorModel> search(SucursalModel sucursal, SearchCriteriaModel criteria,
            String filterText);

    SearchResultsModel<TrabajadorModel> search(AgenciaModel agencia, SearchCriteriaModel criteria);

    SearchResultsModel<TrabajadorModel> search(AgenciaModel agencia, SearchCriteriaModel criteria,
            String filterText);

}
