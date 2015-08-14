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

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.SucursalEntity;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.AgenciaFilterProvider;

@Named
@Stateless
@Local(AgenciaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAgenciaProvider extends AbstractHibernateStorage implements AgenciaProvider {

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private AgenciaFilterProvider filterProvider;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public AgenciaModel create(SucursalModel sucursal, String denominacion) {
        SucursalEntity sucursalEntity = em.find(SucursalEntity.class, sucursal.getId());

        AgenciaEntity agenciaEntity = new AgenciaEntity();
        agenciaEntity.setSucursal(sucursalEntity);
        agenciaEntity.setDenominacion(denominacion);
        em.persist(agenciaEntity);
        return new AgenciaAdapter(em, agenciaEntity);
    }

    @Override
    public boolean remove(AgenciaModel agenciaModel) {
        AgenciaEntity AgenciaEntity = em.find(AgenciaEntity.class, agenciaModel.getId());
        em.remove(AgenciaEntity);
        return true;
    }

    @Override
    public AgenciaModel findById(String id) {
        AgenciaEntity agenciaEntity = em.find(AgenciaEntity.class, id);
        return agenciaEntity != null ? new AgenciaAdapter(em, agenciaEntity) : null;
    }

    @Override
    public SearchResultsModel<AgenciaModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<AgenciaEntity> entityResult = find(criteria, AgenciaEntity.class);

        SearchResultsModel<AgenciaModel> modelResult = new SearchResultsModel<>();
        List<AgenciaModel> list = new ArrayList<>();
        for (AgenciaEntity entity : entityResult.getModels()) {
            list.add(new AgenciaAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<AgenciaModel> search(SearchCriteriaModel criteria, String filterText) {
        SearchResultsModel<AgenciaEntity> entityResult = findFullText(criteria, AgenciaEntity.class,
                filterText, filterProvider.getDenominacionFilter());

        SearchResultsModel<AgenciaModel> modelResult = new SearchResultsModel<>();
        List<AgenciaModel> list = new ArrayList<>();
        for (AgenciaEntity entity : entityResult.getModels()) {
            list.add(new AgenciaAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}
