package org.sistcoop.rrhh.models;

import javax.ejb.Local;

import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.provider.Provider;

@Local
public interface AgenciaProvider extends Provider {

    AgenciaModel create(SucursalModel sucursal, String denominacion);

    boolean remove(AgenciaModel agenciaModel);

    AgenciaModel findById(String id);

    SearchResultsModel<AgenciaModel> search(SearchCriteriaModel criteria);

    SearchResultsModel<AgenciaModel> search(SearchCriteriaModel criteria, String filterText);

}
