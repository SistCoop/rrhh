package org.sistcoop.rrhh.models;

import javax.ejb.Local;

import org.sistcoop.rrhh.provider.Provider;

@Local
public interface TrabajadorUsuarioProvider extends Provider {

	TrabajadorUsuarioModel addTrabajadorUsuario(
			TrabajadorModel trabajadorModel, 
			String usuario);

	boolean removeTrabajadorUsuario(TrabajadorUsuarioModel model);

	TrabajadorUsuarioModel getTrabajadorUsuarioById(Integer id);
	
	TrabajadorUsuarioModel getTrabajadorUsuarioByUsuario(String usuario);	

}
