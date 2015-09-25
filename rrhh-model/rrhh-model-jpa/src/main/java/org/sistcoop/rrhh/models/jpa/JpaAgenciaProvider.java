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

import org.jboss.logging.Logger;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.ModelDuplicateException;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.SucursalEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;
import org.sistcoop.rrhh.models.jpa.search.SearchCriteriaJoinModel;
import org.sistcoop.rrhh.models.jpa.search.SearchCriteriaJoinType;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;

@Named
@Stateless
@Local(AgenciaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAgenciaProvider extends AbstractHibernateStorage implements AgenciaProvider {

    private static final Logger logger = Logger.getLogger(JpaAgenciaProvider.class);

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
    public AgenciaModel create(SucursalModel sucursal, String denominacion) {
        if (findByDenominacion(sucursal, denominacion) != null) {
            throw new ModelDuplicateException(
                    "AgenciaEntity denominacion debe ser unico, se encontro otra entidad con SucursalEntity="
                            + sucursal + " y denominacion=" + denominacion);
        }

        SucursalEntity sucursalEntity = em.find(SucursalEntity.class, sucursal.getId());

        AgenciaEntity agenciaEntity = new AgenciaEntity();
        agenciaEntity.setSucursal(sucursalEntity);
        agenciaEntity.setDenominacion(denominacion);
        em.persist(agenciaEntity);
        return new AgenciaAdapter(em, agenciaEntity);
    }

    @Override
    public boolean remove(AgenciaModel agenciaModel) {
        TypedQuery<TrabajadorEntity> query = em.createNamedQuery("TrabajadorEntity.findByIdAgencia",
                TrabajadorEntity.class);
        query.setParameter("idAgencia", agenciaModel.getId());
        query.setMaxResults(1);
        if (!query.getResultList().isEmpty()) {
            logger.info("AgenciaEntity tiene TrabajadorEntity asociados, AgenciaEntity.remove() eliminara todas las entidades asociadas");
        }

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
    public AgenciaModel findByDenominacion(SucursalModel sucursal, String denominacion) {
        TypedQuery<AgenciaEntity> query = em.createNamedQuery("AgenciaEntity.findByIdSucursalDenominacion",
                AgenciaEntity.class);
        query.setParameter("idSucursal", sucursal.getId());
        query.setParameter("denominacion", denominacion);
        List<AgenciaEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de una AgenciaEntity con SucursalEntity=" + sucursal
                    + " y denominacion=" + denominacion + ", results=" + results);
        } else {
            return new AgenciaAdapter(em, results.get(0));
        }
    }

    @Override
    public List<AgenciaModel> getAll(SucursalModel sucursal) {
        TypedQuery<AgenciaEntity> query = em.createNamedQuery("AgenciaEntity.findByIdSucursal",
                AgenciaEntity.class);
        query.setParameter("idSucursal", sucursal.getId());
        List<AgenciaEntity> list = query.getResultList();

        List<AgenciaModel> result = new ArrayList<>();
        for (AgenciaEntity agenciaEntity : list) {
            result.add(new AgenciaAdapter(em, agenciaEntity));
        }
        return result;
    }

    @Override
    public SearchResultsModel<AgenciaModel> search(SucursalModel sucursal, SearchCriteriaModel criteria) {
        SearchCriteriaJoinModel criteriaJoin = new SearchCriteriaJoinModel("agencia");
        criteriaJoin.addJoin("agencia.sucursal", "sucursal", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addCondition("sucursal.id", sucursal.getId(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<AgenciaEntity> entityResult = find(criteriaJoin, criteria, AgenciaEntity.class);
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
    public SearchResultsModel<AgenciaModel> search(SucursalModel sucursal, SearchCriteriaModel criteria,
            String filterText) {
        SearchCriteriaJoinModel criteriaJoin = new SearchCriteriaJoinModel("agencia");
        criteriaJoin.addJoin("agencia.sucursal", "sucursal", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addCondition("sucursal.id", sucursal.getId(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<AgenciaEntity> entityResult = findFullText(criteriaJoin, criteria,
                AgenciaEntity.class, filterText, "denominacion");
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
