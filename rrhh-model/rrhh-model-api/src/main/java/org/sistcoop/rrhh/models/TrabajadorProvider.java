package org.sistcoop.rrhh.models;

import javax.ejb.Local;

import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.provider.Provider;

@Local
public interface TrabajadorProvider extends Provider {

    TrabajadorModel create(AgenciaModel agenciaModel, String tipoDocumento, String numeroDocumento);

    boolean remove(TrabajadorModel trabajadorModel);

    TrabajadorModel findById(String id);

    SearchResultsModel<TrabajadorModel> search();

    SearchResultsModel<TrabajadorModel> search(SearchCriteriaModel criteria);

    SearchResultsModel<TrabajadorModel> search(SearchCriteriaModel criteria, String filterText);

}
