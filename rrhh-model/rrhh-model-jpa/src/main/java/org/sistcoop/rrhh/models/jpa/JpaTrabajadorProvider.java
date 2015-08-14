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

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.TrabajadorFilterProvider;

@Named
@Stateless
@Local(TrabajadorProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorProvider extends AbstractHibernateStorage implements TrabajadorProvider {

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private TrabajadorFilterProvider filterProvider;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public TrabajadorModel create(AgenciaModel agenciaModel, String tipoDocumento, String numeroDocumento) {
        AgenciaEntity agenciaEntity = em.find(AgenciaEntity.class, agenciaModel.getId());

        TrabajadorEntity trabajadorEntity = new TrabajadorEntity();
        trabajadorEntity.setAgencia(agenciaEntity);
        trabajadorEntity.setTipoDocumento(tipoDocumento);
        trabajadorEntity.setNumeroDocumento(numeroDocumento);

        em.persist(trabajadorEntity);
        return new TrabajadorAdapter(em, trabajadorEntity);
    }

    @Override
    public boolean remove(TrabajadorModel TrabajadorModel) {
        TrabajadorEntity TrabajadorEntity = em.find(TrabajadorEntity.class, TrabajadorModel.getId());
        em.remove(TrabajadorEntity);
        return true;
    }

    @Override
    public TrabajadorModel findById(String id) {
        TrabajadorEntity trabajadorEntity = this.em.find(TrabajadorEntity.class, id);
        return trabajadorEntity != null ? new TrabajadorAdapter(em, trabajadorEntity) : null;
    }

    @Override
    public SearchResultsModel<TrabajadorModel> search() {
        TypedQuery<TrabajadorEntity> query = em.createNamedQuery("TrabajadorEntity.findAll",
                TrabajadorEntity.class);

        List<TrabajadorEntity> entities = query.getResultList();
        List<TrabajadorModel> models = new ArrayList<TrabajadorModel>();
        for (TrabajadorEntity bovedaEntity : entities) {
            models.add(new TrabajadorAdapter(em, bovedaEntity));
        }

        SearchResultsModel<TrabajadorModel> result = new SearchResultsModel<>();
        result.setModels(models);
        result.setTotalSize(models.size());
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
                filterText, filterProvider.getNumeroDocumentoFilter());

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