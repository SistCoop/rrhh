package org.sistcoop.rrhh.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.rrhh.provider.Provider;

@Local
public interface AgenciaProvider extends Provider {

	AgenciaModel addAgencia(SucursalModel sucursal, String denominacion);

	boolean removeAgencia(AgenciaModel agenciaModel);

	AgenciaModel getAgenciaById(String id);
	
	AgenciaModel getAgenciaByDenominacion(SucursalModel sucursal, String denominacion);

	List<AgenciaModel> getAgencias(String filterText, int firstResult, int maxResults);
	
	List<AgenciaModel> getAgencias(SucursalModel sucursal, String filterText, int firstResult, int maxResults);
	
}
