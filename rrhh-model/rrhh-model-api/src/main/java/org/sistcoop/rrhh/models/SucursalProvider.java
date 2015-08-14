package org.sistcoop.rrhh.models;

import javax.ejb.Local;

import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.provider.Provider;

@Local
public interface SucursalProvider extends Provider {

    SucursalModel create(String denominacion);

    boolean remove(SucursalModel sucursalModel);

    SucursalModel findById(String id);

    SearchResultsModel<SucursalModel> search();

    SearchResultsModel<SucursalModel> search(SearchCriteriaModel criteria);

    SearchResultsModel<SucursalModel> search(SearchCriteriaModel criteria, String filterText);

}