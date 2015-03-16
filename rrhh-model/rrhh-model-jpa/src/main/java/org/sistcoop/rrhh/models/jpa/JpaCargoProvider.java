package org.sistcoop.rrhh.models.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sistcoop.rrhh.models.CargoModel;
import org.sistcoop.rrhh.models.CargoProvider;

@Named
@Stateless
@Local(CargoProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCargoProvider implements CargoProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public CargoModel addCargo(String denominacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeCargo(CargoModel cargoModel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CargoModel getCargoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CargoModel> getCargos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CargoModel> getCargos(boolean estado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CargoModel> getCargos(String filterText, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

}