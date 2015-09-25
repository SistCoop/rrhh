package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
import org.sistcoop.rrhh.services.ErrorResponse;
import org.sistcoop.rrhh.services.managers.TrabajadorManager;

@Stateless
public class TrabajadorResourceImpl implements TrabajadorResource {

    @PathParam("trabajador")
    private String trabajador;

    @Inject
    private TrabajadorProvider trabajadorProvider;

    @Inject
    private TrabajadorManager trabajadorManager;

    private TrabajadorModel getTrabajadorModel() {
        return trabajadorProvider.findById(trabajador);
    }

    @Override
    public TrabajadorRepresentation toRepresentation() {
        TrabajadorRepresentation rep = ModelToRepresentation.toRepresentation(getTrabajadorModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException("Trabajador no encontrado");
        }
    }

    @Override
    public void update(TrabajadorRepresentation trabajadorRepresentation) {
        trabajadorManager.update(getTrabajadorModel(), trabajadorRepresentation);
    }

    @Override
    public void updateUsuario(TrabajadorRepresentation trabajadorRepresentation) {
        trabajadorManager.updateUsuario(getTrabajadorModel(), trabajadorRepresentation.getUsuario());
    }

    @Override
    public Response removeUsuario() {
        TrabajadorModel trabajadorModel = getTrabajadorModel();
        if (trabajadorModel == null) {
            throw new NotFoundException("Trabajador no encontrado");
        }
        boolean removed = trabajadorManager.removeUsuario(getTrabajadorModel());
        if (removed) {
            return Response.noContent().build();
        } else {
            return ErrorResponse
                    .error("TrabajadorUsuario no pudo ser eliminado", Response.Status.BAD_REQUEST);
        }
    }

    @Override
    public Response remove() {
        TrabajadorModel trabajadorModel = getTrabajadorModel();
        if (trabajadorModel == null) {
            throw new NotFoundException("Trabajador no encontrado");
        }
        boolean removed = trabajadorProvider.remove(getTrabajadorModel());
        if (removed) {
            return Response.noContent().build();
        } else {
            return ErrorResponse.error("Trabajador no pudo ser eliminado", Response.Status.BAD_REQUEST);
        }
    }

}
