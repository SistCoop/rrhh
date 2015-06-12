package org.sistcoop.rrhh.models.jpa;

import javax.persistence.EntityManager;

import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorUsuarioEntity;

public class TrabajadorUsuarioAdapter implements TrabajadorUsuarioModel {

	private static final long serialVersionUID = 1L;

	protected TrabajadorUsuarioEntity trabajadorUsuarioEntity;
	protected EntityManager em;

	public TrabajadorUsuarioAdapter(EntityManager em, TrabajadorUsuarioEntity trabajadorUsuarioEntity) {
		this.em = em;
		this.trabajadorUsuarioEntity = trabajadorUsuarioEntity;
	}

	public TrabajadorUsuarioEntity getTrabajadorUsuarioEntity() {
		return trabajadorUsuarioEntity;
	}

	public static TrabajadorUsuarioEntity toTrabajadorUsuarioEntity(TrabajadorUsuarioModel model, EntityManager em) {
		if (model instanceof TrabajadorUsuarioAdapter) {
			return ((TrabajadorUsuarioAdapter) model).getTrabajadorUsuarioEntity();
		}
		return em.getReference(TrabajadorUsuarioEntity.class, model.getId());
	}
	
	@Override
	public void commit() {
		em.merge(trabajadorUsuarioEntity);
	}

	@Override
	public String getId() {
		return trabajadorUsuarioEntity.getId();
	}

	@Override
	public String getUsuario() {
		return trabajadorUsuarioEntity.getUsuario();
	}

	@Override
	public void setUsuario(String usuario) {
		trabajadorUsuarioEntity.setUsuario(usuario);
	}

	@Override
	public TrabajadorModel getTrabajador() {
		return new TrabajadorAdapter(em, trabajadorUsuarioEntity.getTrabajador());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrabajadorUsuarioModel other = (TrabajadorUsuarioModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
}