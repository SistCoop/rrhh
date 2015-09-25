package org.sistcoop.rrhh.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sistcoop.rrhh.models.ModelDuplicateException;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorUsuarioEntity;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;

@Named
@Stateless
@Local(TrabajadorUsuarioProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorUsuarioProvider extends AbstractHibernateStorage implements
        TrabajadorUsuarioProvider {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public TrabajadorUsuarioModel create(TrabajadorModel trabajadorModel, String usuario) {
        if (findByUsuario(usuario) != null) {
            throw new ModelDuplicateException(
                    "TrabajadorUsuarioEntity usuario debe ser unico, se encontro otra entidad con usuario="
                            + usuario);
        }

        TrabajadorEntity trabajadorEntity = em.find(TrabajadorEntity.class, trabajadorModel.getId());
        if (trabajadorEntity.getTrabajadorUsuario() != null) {
            throw new ModelDuplicateException(
                    "TrabajadorEntity solo puede tener un usuario, el trabajador ya tiene un usuario asignado");
        }

        TrabajadorUsuarioEntity trabajadorUsuarioEntity = new TrabajadorUsuarioEntity();
        trabajadorUsuarioEntity.setUsuario(usuario);
        em.persist(trabajadorUsuarioEntity);

        trabajadorEntity.setTrabajadorUsuario(trabajadorUsuarioEntity);
        em.merge(trabajadorEntity);

        return new TrabajadorUsuarioAdapter(em, trabajadorUsuarioEntity);

    }

    @Override
    public boolean remove(TrabajadorUsuarioModel model) {
        TrabajadorUsuarioEntity entity = em.find(TrabajadorUsuarioEntity.class, model.getId());
        em.remove(entity);
        return true;
    }

    @Override
    public TrabajadorUsuarioModel findById(String id) {
        TrabajadorUsuarioEntity entity = this.em.find(TrabajadorUsuarioEntity.class, id);
        return entity != null ? new TrabajadorUsuarioAdapter(em, entity) : null;
    }

    @Override
    public TrabajadorUsuarioModel findByUsuario(String usuario) {
        TypedQuery<TrabajadorUsuarioEntity> query = em.createNamedQuery(
                "TrabajadorUsuarioEntity.findByUsuario", TrabajadorUsuarioEntity.class);
        query.setParameter("usuario", usuario);
        List<TrabajadorUsuarioEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de un TrabajadorUsuarioEntity con usuario=" + usuario
                    + ", results=" + results);
        } else {
            return new TrabajadorUsuarioAdapter(em, results.get(0));
        }
    }

    @Override
    public SearchResultsModel<TrabajadorUsuarioModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<TrabajadorUsuarioEntity> entityResult = find(criteria,
                TrabajadorUsuarioEntity.class);

        SearchResultsModel<TrabajadorUsuarioModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorUsuarioModel> list = new ArrayList<>();
        for (TrabajadorUsuarioEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorUsuarioAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}