package org.sistcoop.rrhh.models.jpa.search.filter;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.sistcoop.rrhh.models.search.filters.TrabajadorFilterProvider;

@Named
@Stateless
@Local(TrabajadorFilterProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorFilterProvider implements TrabajadorFilterProvider {

    private final String id = "id";
    private final String tipoDocumento = "tipoDocumento";
    private final String numeroDocumento = "numeroDocumento";
    private final String idSucursal = "agencia.sucursal.id";
    private final String idAgencia = "agencia.id";

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public String getIdFilter() {
        return id;
    }

    @Override
    public String getTipoDocumentoFilter() {
        return tipoDocumento;
    }

    @Override
    public String getNumeroDocumentoFilter() {
        return numeroDocumento;
    }

    @Override
    public String getIdSucursalFilter() {
        return idSucursal;
    }

    @Override
    public String getIdAgenciaFilter() {
        return idAgencia;
    }

}
