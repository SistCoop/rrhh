package org.sistcoop.rrhh.models.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.SucursalEntity;

public class SucursalAdapter implements SucursalModel {

    private static final long serialVersionUID = 1L;

    private SucursalEntity sucursalEntity;
    private EntityManager em;

    public SucursalAdapter(EntityManager em, SucursalEntity sucursalEntity) {
        this.em = em;
        this.sucursalEntity = sucursalEntity;
    }

    public SucursalEntity getSucursalEntity() {
        return sucursalEntity;
    }

    public static SucursalEntity toSucursalEntity(SucursalModel model, EntityManager em) {
        if (model instanceof SucursalAdapter) {
            return ((SucursalAdapter) model).getSucursalEntity();
        }
        return em.getReference(SucursalEntity.class, model.getId());
    }

    @Override
    public void commit() {
        em.merge(sucursalEntity);
    }

    @Override
    public String getId() {
        return sucursalEntity.getId();
    }

    @Override
    public String getDenominacion() {
        return sucursalEntity.getDenominacion();
    }

    @Override
    public void setDenominacion(String denominacion) {
        sucursalEntity.setDenominacion(denominacion);
    }

    @Override
    public List<AgenciaModel> getAgencias() {
        Set<AgenciaEntity> list = sucursalEntity.getAgencias();
        List<AgenciaModel> result = new ArrayList<AgenciaModel>();
        for (AgenciaEntity entity : list) {
            result.add(new AgenciaAdapter(em, entity));
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDenominacion() == null) ? 0 : getDenominacion().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SucursalModel other = (SucursalModel) obj;
        if (getDenominacion() == null) {
            if (other.getDenominacion() != null)
                return false;
        } else if (!getDenominacion().equals(other.getDenominacion()))
            return false;
        return true;
    }

}
