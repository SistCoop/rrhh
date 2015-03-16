package org.sistcoop.rrhh.models.jpa;

import javax.persistence.EntityManager;

import org.sistcoop.rrhh.models.CargoModel;
import org.sistcoop.rrhh.models.jpa.entities.CargoEntity;

public class CargoAdapter implements CargoModel {

	private static final long serialVersionUID = 1L;

	protected CargoEntity cargoEntity;
	protected EntityManager em;

	public CargoAdapter(EntityManager em, CargoEntity cargoEntity) {
		this.em = em;
		this.cargoEntity = cargoEntity;
	}

	public CargoEntity getCargoEntity() {
		return cargoEntity;
	}

	@Override
	public void commit() {
		em.merge(cargoEntity);
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDenominacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDenominacion(String denominacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getEstado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void desactivar() {
		// TODO Auto-generated method stub

	}

}
