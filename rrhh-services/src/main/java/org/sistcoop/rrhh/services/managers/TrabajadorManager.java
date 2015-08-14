package org.sistcoop.rrhh.services.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrabajadorManager {

    @Inject
    private AgenciaProvider agenciaProvider;

    @Inject
    private TrabajadorUsuarioProvider trabajadorUsuarioProvider;

    public void update(TrabajadorModel model, TrabajadorRepresentation rep) {
        AgenciaRepresentation agenciaRepresentation = rep.getAgencia();
        AgenciaModel agenciaModel = agenciaProvider.findById(agenciaRepresentation.getId());

        model.setAgencia(agenciaModel);
        model.commit();
    }

    public void updateUsuario(TrabajadorModel trabajadorModel, String usuario) {
        TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorModel.getTrabajadorUsuarioModel();
        if (trabajadorUsuarioModel != null) {
            trabajadorUsuarioModel.setUsuario(usuario);
            trabajadorUsuarioModel.commit();
        } else {
            trabajadorUsuarioProvider.create(trabajadorModel, usuario);
        }
    }

    public void removeUsuario(TrabajadorModel trabajadorModel) {
        TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorModel.getTrabajadorUsuarioModel();
        if (trabajadorUsuarioModel != null) {
            trabajadorUsuarioProvider.remove(trabajadorUsuarioModel);
        }
    }

}
