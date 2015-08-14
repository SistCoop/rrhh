package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;

import org.sistcoop.rrhh.admin.client.resource.TrabajadorResource;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;
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
    public TrabajadorRepresentation trabajador() {
        TrabajadorRepresentation rep = ModelToRepresentation.toRepresentation(getTrabajadorModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException();
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
    public void removeUsuario() {
        trabajadorManager.removeUsuario(getTrabajadorModel());
    }

    @Override
    public void remove() {
        trabajadorProvider.remove(getTrabajadorModel());
    }

}
