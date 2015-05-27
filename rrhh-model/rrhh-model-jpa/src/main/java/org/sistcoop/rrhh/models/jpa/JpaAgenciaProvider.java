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
import org.sistcoop.rrhh.models.AgenciaProvider;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.SucursalEntity;

@Named
@Stateless
@Local(AgenciaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAgenciaProvider implements AgenciaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public AgenciaModel addAgencia(SucursalModel sucursal, String denominacion, String ubigeo, String direccion) {
		AgenciaEntity agenciaEntity = new AgenciaEntity();

		SucursalEntity sucursalEntity = SucursalAdapter.toSucursalEntity(sucursal, em);
		agenciaEntity.setSucursal(sucursalEntity);

		agenciaEntity.setDenominacion(denominacion);
		agenciaEntity.setDireccion(direccion);
		agenciaEntity.setUbigeo(ubigeo);		
		em.persist(agenciaEntity);
		return new AgenciaAdapter(em, agenciaEntity);
	}

	@Override
	public boolean removeAgencia(AgenciaModel agenciaModel) {
		AgenciaEntity AgenciaEntity = em.find(AgenciaEntity.class, agenciaModel.getId());
		if (em.contains(AgenciaEntity))
			em.remove(AgenciaEntity);
		else
			em.remove(em.getReference(AgenciaEntity.class, AgenciaEntity.getId()));
		return true;
	}

	@Override
	public AgenciaModel getAgenciaByDenominacion(SucursalModel sucursal, String denominacion) {
		SucursalEntity sucursalEntity = SucursalAdapter.toSucursalEntity(sucursal, em);
		
		TypedQuery<AgenciaEntity> query = em.createQuery("SELECT a FROM AgenciaEntity a WHERE a.sucursal.denominacion=:sucursal AND a.denominacion = :agencia", AgenciaEntity.class);	
		query.setParameter("sucursal", sucursalEntity.getDenominacion());
		query.setParameter("agencia", denominacion);
		AgenciaEntity result = query.getSingleResult();
		if(result != null) {
			return new AgenciaAdapter(em, result);
		} else {
			return null;
		}
	}

	@Override
	public List<AgenciaModel> getAgencias(SucursalModel sucursal, String filterText, int firstResult, int maxResults) {						
		TypedQuery<AgenciaEntity> query = em.createQuery("SELECT a FROM AgenciaEntity a WHERE a.sucursal.denominacion=:sucursal AND a.denominacion LIKE :filterText", AgenciaEntity.class);	
		query.setParameter("sucursal", sucursal.getDenominacion());
		query.setParameter("filterText", "%" + filterText + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		
		List<AgenciaEntity> entities = query.getResultList();
		List<AgenciaModel> result = new ArrayList<>();
		for (AgenciaEntity agenciaEntity : entities) {
			result.add(new AgenciaAdapter(em, agenciaEntity));
		}
		return result;
	}

}
