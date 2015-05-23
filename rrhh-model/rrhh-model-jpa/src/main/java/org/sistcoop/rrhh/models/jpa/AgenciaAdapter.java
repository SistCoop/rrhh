package org.sistcoop.rrhh.models.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.jpa.entities.AgenciaEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;

public class AgenciaAdapter implements AgenciaModel {

	private static final long serialVersionUID = 1L;

	protected AgenciaEntity agenciaEntity;
	protected EntityManager em;

	public AgenciaAdapter(EntityManager em, AgenciaEntity agenciaEntity) {
		this.em = em;
		this.agenciaEntity = agenciaEntity;
	}

	public static AgenciaEntity toAgenciaEntity(AgenciaModel model,
			EntityManager em) {
		if (model instanceof AgenciaAdapter) {
			return ((AgenciaAdapter) model).getAgenciaEntity();
		}
		return em.getReference(AgenciaEntity.class, model.getId());
	}

	public AgenciaEntity getAgenciaEntity() {
		return agenciaEntity;
	}

	@Override
	public void commit() {
		em.merge(agenciaEntity);
	}

	@Override
	public Integer getId() {
		return agenciaEntity.getId();
	}

	@Override
	public String getDenominacion() {
		return agenciaEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		agenciaEntity.setDenominacion(denominacion);
	}

	@Override
	public String getDireccion() {
		return agenciaEntity.getDireccion();
	}

	@Override
	public void setDireccion(String direccion) {
		agenciaEntity.setDireccion(direccion);
	}

	@Override
	public String getUbigeo() {
		return agenciaEntity.getUbigeo();
	}

	@Override
	public void setUbigeo(String ubigeo) {
		agenciaEntity.setUbigeo(ubigeo);
	}

	@Override
	public SucursalModel getSucursal() {
		return new SucursalAdapter(em, agenciaEntity.getSucursal());
	}

	@Override
	public List<TrabajadorModel> getTrabajadores() {
		Set<TrabajadorEntity> list = agenciaEntity.getTrabajadores();
		List<TrabajadorModel> result = new ArrayList<TrabajadorModel>();
		for (TrabajadorEntity entity : list) {
			if (entity.isEstado() == true)
				result.add(new TrabajadorAdapter(em, entity));
		}
		return result;
	}

}
