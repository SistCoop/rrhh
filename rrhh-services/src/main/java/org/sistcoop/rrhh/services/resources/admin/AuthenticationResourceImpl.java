package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.sistcoop.rrhh.admin.client.resource.AuthenticationResource;

@Stateless
public class AuthenticationResourceImpl implements AuthenticationResource {

    @Context
    private UriInfo uriInfo;

    @Override
    public void login(String sucursal, String agencia) {
        /*
         * TrabajadorUsuarioModel trabajadorUsuarioModel =
         * trabajadorUsuarioProvider .getTrabajadorUsuarioByUsuario(username);
         * if (trabajadorUsuarioModel == null) { throw new
         * BadRequestException(); }
         * 
         * TrabajadorModel trabajadorModel =
         * trabajadorUsuarioModel.getTrabajador(); AgenciaModel agenciaModel =
         * trabajadorModel.getAgencia(); SucursalModel sucursalModel =
         * agenciaModel.getSucursal();
         * 
         * if (!sucursal.equals(sucursalModel.getDenominacion()) ||
         * !agencia.equals(agenciaModel.getDenominacion())) { throw new
         * BadRequestException(); }
         */
        throw new BadRequestException();
    }
}
