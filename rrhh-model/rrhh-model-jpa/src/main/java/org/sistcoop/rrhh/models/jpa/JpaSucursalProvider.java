package org.sistcoop.rrhh.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.SucursalProvider;
import org.sistcoop.rrhh.models.jpa.entities.SucursalEntity;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.SucursalFilterProvider;

@Named
@Stateless
@Local(SucursalProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaSucursalProvider extends AbstractHibernateStorage implements SucursalProvider {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private SucursalFilterProvider filterProvider;

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
        SucursalEntity sucursalEntity = new SucursalEntity();
        sucursalEntity.setDenominacion(denominacion);
        em.persist(sucursalEntity);
        return new SucursalAdapter(em, sucursalEntity);
    }

    @Override
    public boolean remove(SucursalModel sucursalModel) {
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
    public SearchResultsModel<SucursalModel> search() {
        TypedQuery<SucursalEntity> query = em
                .createNamedQuery("SucursalEntity.findAll", SucursalEntity.class);

        List<SucursalEntity> entities = query.getResultList();
        List<SucursalModel> models = new ArrayList<SucursalModel>();
        for (SucursalEntity bovedaEntity : entities) {
            models.add(new SucursalAdapter(em, bovedaEntity));
        }

        SearchResultsModel<SucursalModel> result = new SearchResultsModel<>();
        result.setModels(models);
        result.setTotalSize(models.size());
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
                filterText, filterProvider.getDenominacionFilter());

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