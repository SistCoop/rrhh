package org.sistcoop.rrhh.models;

import javax.ejb.Local;

import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.provider.Provider;

@Local
public interface TrabajadorUsuarioProvider extends Provider {

    TrabajadorUsuarioModel create(TrabajadorModel trabajadorModel, String usuario);

    boolean remove(TrabajadorUsuarioModel model);

    TrabajadorUsuarioModel findById(String id);

    TrabajadorUsuarioModel findByUsuario(String usuario);

    SearchResultsModel<TrabajadorUsuarioModel> search(SearchCriteriaModel criteria);

}
