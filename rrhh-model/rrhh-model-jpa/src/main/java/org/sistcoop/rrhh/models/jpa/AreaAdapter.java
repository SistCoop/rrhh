package org.sistcoop.rrhh.models.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.jpa.entities.AreaEntity;

public class AreaAdapter implements SucursalModel {

	private static final long serialVersionUID = 1L;

	protected AreaEntity areaEntity;
	protected EntityManager em;

	public AreaAdapter(EntityManager em, AreaEntity areaEntity) {
		this.em = em;
		this.areaEntity = areaEntity;
	}

	public AreaEntity getSucursalEntity() {
		return areaEntity;
	}

	@Override
	public void commit() {
		em.merge(areaEntity);
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
	public String getAbreviatura() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAbreviatura(String abreviatura) {
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

	@Override
	public List<AgenciaModel> getAgencias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AgenciaModel> getAgencias(boolean estado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AgenciaModel> getAgencias(String filterText, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

}
