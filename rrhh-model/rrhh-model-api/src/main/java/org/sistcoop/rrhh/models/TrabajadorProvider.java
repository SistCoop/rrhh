package org.sistcoop.rrhh.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.rrhh.provider.Provider;

@Local
public interface TrabajadorProvider extends Provider {

	TrabajadorModel addTrabajador(
			AgenciaModel agenciaModel, 
			String tipoDocumento, 
			String numeroDocumento);

	boolean removeTrabajador(TrabajadorModel trabajadorModel);	

	TrabajadorModel getTrabajadorById(String id);

	TrabajadorModel getTrabajadorByTipoNumeroDocumento(String tipoDocumento, String numeroDocumento);
	
	List<TrabajadorModel> getTrabajadores(String filterText, int firstResult, int maxResults);
	
	List<TrabajadorModel> getTrabajadores(AgenciaModel agencia, String filterText, int firstResult, int maxResults);

}
