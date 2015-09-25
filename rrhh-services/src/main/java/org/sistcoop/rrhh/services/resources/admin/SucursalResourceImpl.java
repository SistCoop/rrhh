package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.admin.client.resource.AgenciasResource;
import org.sistcoop.rrhh.admin.client.resource.SucursalResource;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.services.ErrorResponse;
import org.sistcoop.rrhh.services.managers.SucursalManager;

@Stateless
public class SucursalResourceImpl implements SucursalResource {

    @PathParam("sucursal")
    private String sucursal;

    @Inject
    private SucursalProvider sucursalProvider;

    @Inject
    private SucursalManager sucursalManager;

    @Inject
    private AgenciasResource agenciasResource;

    private SucursalModel getSucursalModel() {
        return sucursalProvider.findById(sucursal);
    }

    @Override
    public SucursalRepresentation toRepresentation() {
        SucursalRepresentation rep = ModelToRepresentation.toRepresentation(getSucursalModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException("Sucursal no encontrada");
        }
    }

    @Override
    public void update(SucursalRepresentation sucursalRepresentation) {
        sucursalManager.update(getSucursalModel(), sucursalRepresentation);
    }

    @Override
    public Response remove() {
        SucursalModel sucursalModel = getSucursalModel();
        if (sucursalModel == null) {
            throw new NotFoundException("Sucursal no encontrada");
        }
        boolean removed = sucursalProvider.remove(getSucursalModel());
        if (removed) {
            return Response.noContent().build();
        } else {
            return ErrorResponse.error("Sucursal no pudo ser eliminado", Response.Status.BAD_REQUEST);
        }
    }

    @Override
    public AgenciasResource agencias() {
        return agenciasResource;
    }

}
