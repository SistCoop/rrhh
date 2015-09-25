package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sistcoop.rrhh.admin.client.resource.AgenciaResource;
import org.sistcoop.rrhh.admin.client.resource.TrabajadoresResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.utils.ModelToRepresentation;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.services.ErrorResponse;
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
    public AgenciaRepresentation toRepresentation() {
        AgenciaRepresentation rep = ModelToRepresentation.toRepresentation(getAgenciaModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException("Agencia no encontrada");
        }
    }

    @Override
    public void update(AgenciaRepresentation agenciaRepresentation) {
        agenciaManager.update(getAgenciaModel(), agenciaRepresentation);
    }

    @Override
    public Response remove() {
        AgenciaModel agenciaModel = getAgenciaModel();
        if (agenciaModel == null) {
            throw new NotFoundException("Sucursal no encontrada");
        }
        boolean removed = agenciaProvider.remove(getAgenciaModel());
        if (removed) {
            return Response.noContent().build();
        } else {
            return ErrorResponse.error("Agencia no pudo ser eliminado", Response.Status.BAD_REQUEST);
        }
    }

    @Override
    public TrabajadoresResource trabajadores() {
        return trabajadoresResource;
    }

}
