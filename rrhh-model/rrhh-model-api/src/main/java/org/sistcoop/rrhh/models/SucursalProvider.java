package org.sistcoop.rrhh.models;

import java.util.List;

import javax.ejb.Local;

import org.sistcoop.rrhh.provider.Provider;

@Local
public interface SucursalProvider extends Provider {

	SucursalModel addSucursal(String abreviatura);

	boolean removeSucursal(SucursalModel sucursalModel);

	SucursalModel getSucursalByDenominacion(String denominacion);

	List<SucursalModel> getSucursales();

	List<SucursalModel> getSucursales(String filterText, int firstResult, int maxResults);

}