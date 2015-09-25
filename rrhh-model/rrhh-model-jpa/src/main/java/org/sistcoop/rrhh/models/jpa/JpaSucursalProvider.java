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
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.SucursalEntity;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;

@Named
@Stateless
@Local(SucursalProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaSucursalProvider extends AbstractHibernateStorage implements SucursalProvider {

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
    public SucursalModel create(String denominacion) {
        if (findByDenominacion(denominacion) != null) {
            throw new ModelDuplicateException(
                    "SucursalEntity denominacion debe ser unico, se encontro otra entidad con denominacion="
                            + denominacion);
        }

        SucursalEntity sucursalEntity = new SucursalEntity();
        sucursalEntity.setDenominacion(denominacion);
        em.persist(sucursalEntity);
        return new SucursalAdapter(em, sucursalEntity);
    }

    @Override
    public boolean remove(SucursalModel sucursalModel) {
        TypedQuery<AgenciaEntity> query = em.createNamedQuery("AgenciaEntity.findByIdSucursal",
                AgenciaEntity.class);
        query.setParameter("idSucursal", sucursalModel.getId());
        query.setMaxResults(1);
        if (!query.getResultList().isEmpty()) {
            return false;
        }

        SucursalEntity sucursalEntity = em.find(SucursalEntity.class, sucursalModel.getId());
        em.remove(sucursalEntity);
        return true;
    }

    @Override
    public SucursalModel findById(String id) {
        SucursalEntity sucursalEntity = em.find(SucursalEntity.class, id);
        return sucursalEntity != null ? new SucursalAdapter(em, sucursalEntity) : null;
    }

    @Override
    public SucursalModel findByDenominacion(String denominacion) {
        TypedQuery<SucursalEntity> query = em.createNamedQuery("SucursalEntity.findByDenominacion",
                SucursalEntity.class);
        query.setParameter("denominacion", denominacion);
        List<SucursalEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de una SucursalEntity con denominacion=" + denominacion
                    + ", results=" + results);
        } else {
            return new SucursalAdapter(em, results.get(0));
        }
    }

    @Override
    public List<SucursalModel> getAll() {
        TypedQuery<SucursalEntity> query = em
                .createNamedQuery("SucursalEntity.findAll", SucursalEntity.class);

        List<SucursalEntity> entities = query.getResultList();
        List<SucursalModel> result = new ArrayList<SucursalModel>();
        for (SucursalEntity bovedaEntity : entities) {
            result.add(new SucursalAdapter(em, bovedaEntity));
        }
        return result;
    }

    @Override
    public SearchResultsModel<SucursalModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<SucursalEntity> entityResult = find(criteria, SucursalEntity.class);

        SearchResultsModel<SucursalModel> modelResult = new SearchResultsModel<>();
        List<SucursalModel> list = new ArrayList<>();
        for (SucursalEntity entity : entityResult.getModels()) {
            list.add(new SucursalAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<SucursalModel> search(SearchCriteriaModel criteria, String filterText) {
        SearchResultsModel<SucursalEntity> entityResult = findFullText(criteria, SucursalEntity.class,
                filterText, "denominacion");

        SearchResultsModel<SucursalModel> modelResult = new SearchResultsModel<>();
        List<SucursalModel> list = new ArrayList<>();
        for (SucursalEntity entity : entityResult.getModels()) {
            list.add(new SucursalAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}