package org.sistcoop.rrhh.models.jpa.search.filter;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.sistcoop.rrhh.models.search.filters.AgenciaFilterProvider;

@Named
@Stateless
@Local(AgenciaFilterProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAgenciaFilterProvider implements AgenciaFilterProvider {

    private final String id = "id";
    private final String denominacion = "denominacion";
    private final String idSucursal = "sucursal.id";

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public String getIdFilter() {
        return id;
    }

    @Override
    public String getDenominacionFilter() {
        return denominacion;
    }

    @Override
    public String getIdSucursalFilter() {
        return idSucursal;
    }

}
