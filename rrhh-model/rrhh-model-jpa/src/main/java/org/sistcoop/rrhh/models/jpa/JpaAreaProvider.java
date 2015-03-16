package org.sistcoop.rrhh.models.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sistcoop.rrhh.models.AreaModel;
import org.sistcoop.rrhh.models.AreaProvider;

@Named
@Stateless
@Local(AreaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAreaProvider implements AreaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public AreaModel addArea(String denominacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeArea(AreaModel areaModel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AreaModel getAreaById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AreaModel> getAreas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AreaModel> getAreas(boolean estado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AreaModel> getAreas(String filterText, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

}