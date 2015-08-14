package org.sistcoop.rrhh.models.search.filters;

import javax.ejb.Local;

import org.sistcoop.rrhh.provider.Provider;

@Local
public interface TrabajadorFilterProvider extends Provider {

    String getIdFilter();

    String getTipoDocumentoFilter();

    String getNumeroDocumentoFilter();

    String getIdSucursalFilter();

    String getIdAgenciaFilter();

}
