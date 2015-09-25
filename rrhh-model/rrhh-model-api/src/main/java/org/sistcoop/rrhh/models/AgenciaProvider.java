package org.sistcoop.rrhh.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.provider.Provider;

@Local
public interface AgenciaProvider extends Provider {

    AgenciaModel create(SucursalModel sucursal, String denominacion);

    boolean remove(AgenciaModel agencia);

    AgenciaModel findById(String id);

    AgenciaModel findByDenominacion(SucursalModel sucursal, String denominacion);

    List<AgenciaModel> getAll(SucursalModel sucursal);

    SearchResultsModel<AgenciaModel> search(SucursalModel sucursal, SearchCriteriaModel criteria);

    SearchResultsModel<AgenciaModel> search(SucursalModel sucursal, SearchCriteriaModel criteria,
            String filterText);

}
