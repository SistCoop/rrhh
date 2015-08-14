package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;

import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.admin.client.resource.TrabajadoresResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.services.managers.AgenciaManager;

@Stateless
public class AgenciaResourceImpl implements AgenciaResource {

    @PathParam("agencia")
    private String agencia;

    @Inject
    private AgenciaProvider agenciaProvider;

    @Inject
    private AgenciaManager agenciaManager;

    @Inject
    private TrabajadoresResource trabajadoresResource;

    private AgenciaModel getAgenciaModel() {
        return agenciaProvider.findById(agencia);
    }

    @Override
    public AgenciaRepresentation agencia() {
        AgenciaRepresentation rep = ModelToRepresentation.toRepresentation(getAgenciaModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void update(AgenciaRepresentation agenciaRepresentation) {
        agenciaManager.update(getAgenciaModel(), agenciaRepresentation);
    }

    @Override
    public void remove() {
        agenciaProvider.remove(getAgenciaModel());
    }

    @Override
    public TrabajadoresResource trabajadores() {
        return trabajadoresResource;
    }

}
