package org.sistcoop.rrhh.services.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgenciaManager {

    public void update(AgenciaModel model, AgenciaRepresentation rep) {
        model.setDenominacion(rep.getDenominacion());
        model.setDireccion(rep.getDireccion());
        model.setUbigeo(rep.getUbigeo());
        model.commit();
    }

}
