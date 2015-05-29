package org.sistcoop.rrhh.models.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorEntity;
import org.sistcoop.rrhh.models.jpa.entities.TrabajadorUsuarioEntity;

@Named
@Stateless
@Local(TrabajadorUsuarioProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTrabajadorUsuarioProvider implements TrabajadorUsuarioProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public TrabajadorUsuarioModel addTrabajadorUsuario(
			TrabajadorModel trabajadorModel, String usuario) {

		TrabajadorUsuarioEntity trabajadorUsuarioEntity = new TrabajadorUsuarioEntity();

		TrabajadorEntity trabajadorEntity = TrabajadorAdapter.toTrabajadorEntity(trabajadorModel, em);
		trabajadorUsuarioEntity.setTrabajador(trabajadorEntity);

		trabajadorUsuarioEntity.setUsuario(usuario);

		em.persist(trabajadorUsuarioEntity);
		return new TrabajadorUsuarioAdapter(em, trabajadorUsuarioEntity);
		
	}

	@Override
	public boolean removeTrabajadorUsuario(TrabajadorUsuarioModel model) {

		TrabajadorUsuarioEntity trabajadorUsuarioEntity = em.find(TrabajadorUsuarioEntity.class, model.getId());
		if (em.contains(trabajadorUsuarioEntity))
			em.remove(trabajadorUsuarioEntity);
		else
			em.remove(em.getReference(TrabajadorUsuarioEntity.class, trabajadorUsuarioEntity.getId()));
		return true;
		
	}

	@Override
	public TrabajadorUsuarioModel getTrabajadorUsuarioById(Integer id) {

		TrabajadorUsuarioEntity trabajadorUsuarioEntity = this.em.find(TrabajadorUsuarioEntity.class, id);
		return trabajadorUsuarioEntity != null ? new TrabajadorUsuarioAdapter(em, trabajadorUsuarioEntity) : null;
		
	}

	@Override
	public TrabajadorUsuarioModel getTrabajadorUsuarioByUsuario(String usuario) {

		TypedQuery<TrabajadorUsuarioEntity> query = em.createQuery("SELECT t FROM TrabajadorUsuarioEntity t WHERE t.usuario = :usuario", TrabajadorUsuarioEntity.class);
		query.setParameter("usuario", usuario);
		List<TrabajadorUsuarioEntity> list = query.getResultList();
		if (list.size() > 0) {
			for (TrabajadorUsuarioEntity trabajadorUsuarioEntity : list) {
				return new TrabajadorUsuarioAdapter(em, trabajadorUsuarioEntity);
			}	
		}
		
		return null;
		
	}

}