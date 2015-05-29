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

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorProvider;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;

@Named
@Stateless
@Local(TrabajadorProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorProvider implements TrabajadorProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public TrabajadorModel addTrabajador(AgenciaModel agenciaModel, String tipoDocumento, String numeroDocumento) {
		TrabajadorEntity trabajadorEntity = new TrabajadorEntity();

		AgenciaEntity agenciaEntity = AgenciaAdapter.toAgenciaEntity(agenciaModel, em);
		trabajadorEntity.setAgencia(agenciaEntity);

		trabajadorEntity.setTipoDocumento(tipoDocumento);
		trabajadorEntity.setNumeroDocumento(numeroDocumento);		

		em.persist(trabajadorEntity);
		return new TrabajadorAdapter(em, trabajadorEntity);
	}

	@Override
	public boolean removeTrabajador(TrabajadorModel TrabajadorModel) {
		TrabajadorEntity TrabajadorEntity = em.find(TrabajadorEntity.class, TrabajadorModel.getId());
		if (em.contains(TrabajadorEntity))
			em.remove(TrabajadorEntity);
		else
			em.remove(em.getReference(TrabajadorEntity.class, TrabajadorEntity.getId()));
		return true;
	}

	@Override
	public TrabajadorModel getTrabajadorById(String id) {
		TrabajadorEntity trabajadorEntity = this.em.find(TrabajadorEntity.class, id);
		return trabajadorEntity != null ? new TrabajadorAdapter(em, trabajadorEntity) : null;
	}

	@Override
	public TrabajadorModel getTrabajadorByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento) {
		TypedQuery<TrabajadorEntity> query = em.createQuery("SELECT t FROM TrabajadorEntity t WHERE t.tipoDocumento = :tipoDocumento AND t.numeroDocumento = :numeroDocumento", TrabajadorEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento);
		query.setParameter("numeroDocumento", numeroDocumento);
		List<TrabajadorEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new TrabajadorAdapter(em, results.get(0));
	}

	@Override
	public List<TrabajadorModel> getTrabajadores(String filterText,
			int firstResult, int maxResults) {
		TypedQuery<TrabajadorEntity> query = em.createQuery("SELECT a FROM TrabajadorEntity a WHERE a.numeroDocumento LIKE :filterText", TrabajadorEntity.class);			
		query.setParameter("filterText", "%" + filterText + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		
		List<TrabajadorEntity> entities = query.getResultList();
		List<TrabajadorModel> result = new ArrayList<>();
		for (TrabajadorEntity trabajadorEntity : entities) {
			result.add(new TrabajadorAdapter(em, trabajadorEntity));
		}
		return result;
	}

	@Override
	public List<TrabajadorModel> getTrabajadores(AgenciaModel agencia,
			String filterText, int firstResult, int maxResults) {
		TypedQuery<TrabajadorEntity> query = em.createQuery("SELECT a FROM TrabajadorEntity a WHERE a.agencia.denominacion = :agencia AND a.agencia.sucursal.denominacion = :sucursal AND a.numeroDocumento LIKE :filterText", TrabajadorEntity.class);			
		query.setParameter("sucursal", agencia.getSucursal().getDenominacion());
		query.setParameter("agencia", agencia.getDenominacion());
		query.setParameter("filterText", "%" + filterText + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		
		List<TrabajadorEntity> entities = query.getResultList();
		List<TrabajadorModel> result = new ArrayList<>();
		for (TrabajadorEntity trabajadorEntity : entities) {
			result.add(new TrabajadorAdapter(em, trabajadorEntity));
		}
		return result;
	}

}