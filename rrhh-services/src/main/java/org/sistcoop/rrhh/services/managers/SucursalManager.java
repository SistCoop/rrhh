package org.sistcoop.rrhh.services.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SucursalManager {

    public void update(SucursalModel model, SucursalRepresentation rep) {
        model.setDenominacion(rep.getDenominacion());
        model.commit();
    }

}
