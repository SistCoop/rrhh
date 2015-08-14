package org.sistcoop.rrhh.models.jpa.search.filter;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.sistcoop.rrhh.models.search.filters.SucursalFilterProvider;

@Named
@Stateless
@Local(SucursalFilterProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaSucursalFilterProvider implements SucursalFilterProvider {

    private final String id = "id";
    private final String denominacion = "denominacion";

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

}
