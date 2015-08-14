package org.sistcoop.rrhh.models.search.filters;

import javax.ejb.Local;

import org.sistcoop.rrhh.provider.Provider;

@Local
public interface AgenciaFilterProvider extends Provider {

    String getIdFilter();

    String getDenominacionFilter();

    String getIdSucursalFilter();

}
