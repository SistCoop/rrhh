package org.sistcoop.rrhh.models.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorUsuarioEntity;

public class TrabajadorUsuarioAdapter implements TrabajadorUsuarioModel {

    private static final long serialVersionUID = 1L;

    private TrabajadorUsuarioEntity trabajadorUsuarioEntity;
    private EntityManager em;

    public TrabajadorUsuarioAdapter(EntityManager em, TrabajadorUsuarioEntity trabajadorUsuarioEntity) {
        this.em = em;
        this.trabajadorUsuarioEntity = trabajadorUsuarioEntity;
    }

    public TrabajadorUsuarioEntity getTrabajadorUsuarioEntity() {
        return trabajadorUsuarioEntity;
    }

    public static TrabajadorUsuarioEntity toTrabajadorUsuarioEntity(TrabajadorUsuarioModel model,
            EntityManager em) {
        if (model instanceof TrabajadorUsuarioAdapter) {
            return ((TrabajadorUsuarioAdapter) model).getTrabajadorUsuarioEntity();
        }
        return em.getReference(TrabajadorUsuarioEntity.class, model.getId());
    }

    @Override
    public void commit() {
        em.merge(trabajadorUsuarioEntity);
    }

    @Override
    public String getId() {
        return trabajadorUsuarioEntity.getId();
    }

    @Override
    public String getUsuario() {
        return trabajadorUsuarioEntity.getUsuario();
    }

    @Override
    public void setUsuario(String usuario) {
        trabajadorUsuarioEntity.setUsuario(usuario);
    }

    @Override
    public TrabajadorModel getTrabajador() {
        TypedQuery<TrabajadorEntity> query = em.createNamedQuery(
                "TrabajadorEntity.findByIdTrabajadorUsuario", TrabajadorEntity.class);
        query.setParameter("idTrabajadorUsuario", getId());
        List<TrabajadorEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de una TrabajadorEntity con TrabajadorUsuarioEntity.id="
                    + getId() + ", results=" + results);
        } else {
            return new TrabajadorAdapter(em, results.get(0));
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
        TrabajadorUsuarioModel other = (TrabajadorUsuarioModel) obj;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }

}