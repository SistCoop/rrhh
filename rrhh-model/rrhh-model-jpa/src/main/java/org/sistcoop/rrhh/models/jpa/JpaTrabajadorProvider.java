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
import org.sistcoop.rrhh.models.ModelDuplicateException;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;
import org.sistcoop.rrhh.models.jpa.search.SearchCriteriaJoinModel;
import org.sistcoop.rrhh.models.jpa.search.SearchCriteriaJoinType;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;

@Named
@Stateless
@Local(TrabajadorProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorProvider extends AbstractHibernateStorage implements TrabajadorProvider {

    private static final Logger logger = Logger.getLogger(JpaTrabajadorProvider.class);

    @PersistenceContext
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public TrabajadorModel create(AgenciaModel agencia, String tipoDocumento, String numeroDocumento) {
        if (findByTipoNumeroDocumento(tipoDocumento, numeroDocumento) != null) {
            throw new ModelDuplicateException(
                    "TrabajadorEntity tipoDocumento y numeroDocumento debe ser unico, se encontro otra entidad con tipoDocumento="
                            + tipoDocumento + " y numeroDocumento=" + numeroDocumento);
        }

        AgenciaEntity agenciaEntity = em.find(AgenciaEntity.class, agencia.getId());

        TrabajadorEntity trabajadorEntity = new TrabajadorEntity();
        trabajadorEntity.setAgencia(agenciaEntity);
        trabajadorEntity.setTipoDocumento(tipoDocumento);
        trabajadorEntity.setNumeroDocumento(numeroDocumento);

        em.persist(trabajadorEntity);
        return new TrabajadorAdapter(em, trabajadorEntity);
    }

    @Override
    public boolean remove(TrabajadorModel TrabajadorModel) {
        TrabajadorEntity trabajadorEntity = em.find(TrabajadorEntity.class, TrabajadorModel.getId());
        if (trabajadorEntity != null) {
            logger.info("TrabajadorEntity tiene TrabajadorUsuarioEntity asociados, TrabajadorEntity.remove() eliminara todas las entidades asociadas");
        }

        em.remove(trabajadorEntity);
        return true;
    }

    @Override
    public TrabajadorModel findById(String id) {
        TrabajadorEntity trabajadorEntity = this.em.find(TrabajadorEntity.class, id);
        return trabajadorEntity != null ? new TrabajadorAdapter(em, trabajadorEntity) : null;
    }

    @Override
    public TrabajadorModel findByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        TypedQuery<TrabajadorEntity> query = em.createNamedQuery(
                "TrabajadorEntity.findByTipoNumeroDocumento", TrabajadorEntity.class);
        query.setParameter("tipoDocumento", tipoDocumento);
        query.setParameter("numeroDocumento", numeroDocumento);
        List<TrabajadorEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de un TrabajadorEntity con tipoDocumento=" + tipoDocumento
                    + " y numeroDocumento=" + numeroDocumento + ", results=" + results);
        } else {
            return new TrabajadorAdapter(em, results.get(0));
        }
    }

    @Override
    public List<TrabajadorModel> getAll(AgenciaModel agencia) {
        TypedQuery<TrabajadorEntity> query = em.createNamedQuery("TrabajadorEntity.findByIdAgencia",
                TrabajadorEntity.class);
        query.setParameter("idAgencia", agencia.getId());
        List<TrabajadorEntity> entities = query.getResultList();
        List<TrabajadorModel> result = new ArrayList<TrabajadorModel>();
        for (TrabajadorEntity bovedaEntity : entities) {
            result.add(new TrabajadorAdapter(em, bovedaEntity));
        }
        return result;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<TrabajadorEntity> entityResult = find(criteria, TrabajadorEntity.class);
        SearchResultsModel<TrabajadorModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorModel> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search(SearchCriteriaModel criteria, String filterText) {
        SearchResultsModel<TrabajadorEntity> entityResult = findFullText(criteria, TrabajadorEntity.class,
                filterText, "numeroDocumento");
        SearchResultsModel<TrabajadorModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorModel> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search(SucursalModel sucursal, SearchCriteriaModel criteria) {
        SearchCriteriaJoinModel criteriaJoin = new SearchCriteriaJoinModel("trabajador");
        criteriaJoin.addJoin("trabajador.agencia", "agencia", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addJoin("agencia.sucursal", "sucursal", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addCondition("sucursal.id", sucursal.getId(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<TrabajadorEntity> entityResult = find(criteriaJoin, criteria,
                TrabajadorEntity.class);
        SearchResultsModel<TrabajadorModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorModel> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search(SucursalModel sucursal, SearchCriteriaModel criteria,
            String filterText) {
        SearchCriteriaJoinModel criteriaJoin = new SearchCriteriaJoinModel("trabajador");
        criteriaJoin.addJoin("trabajador.agencia", "agencia", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addJoin("agencia.sucursal", "sucursal", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addCondition("sucursal.id", sucursal.getId(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<TrabajadorEntity> entityResult = findFullText(criteriaJoin, criteria,
                TrabajadorEntity.class, filterText, "numeroDocumento");
        SearchResultsModel<TrabajadorModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorModel> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search(AgenciaModel agencia, SearchCriteriaModel criteria) {
        SearchCriteriaJoinModel criteriaJoin = new SearchCriteriaJoinModel("trabajador");
        criteriaJoin.addJoin("trabajador.agencia", "agencia", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addCondition("agencia.id", agencia.getId(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<TrabajadorEntity> entityResult = find(criteriaJoin, criteria,
                TrabajadorEntity.class);
        SearchResultsModel<TrabajadorModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorModel> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search(AgenciaModel agencia, SearchCriteriaModel criteria,
            String filterText) {
        SearchCriteriaJoinModel criteriaJoin = new SearchCriteriaJoinModel("trabajador");
        criteriaJoin.addJoin("trabajador.agencia", "agencia", SearchCriteriaJoinType.INNER_JOIN);
        criteriaJoin.addCondition("agencia.id", agencia.getId(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<TrabajadorEntity> entityResult = findFullText(criteriaJoin, criteria,
                TrabajadorEntity.class, filterText, "numeroDocumento");
        SearchResultsModel<TrabajadorModel> modelResult = new SearchResultsModel<>();
        List<TrabajadorModel> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityResult.getModels()) {
            list.add(new TrabajadorAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}