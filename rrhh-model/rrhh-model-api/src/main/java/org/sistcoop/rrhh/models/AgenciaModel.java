package org.sistcoop.rrhh.models;

import java.util.List;

public interface AgenciaModel extends Model {

	String getId();

	String getDenominacion();

	void setDenominacion(String denominacion);

	String getDireccion();

	void setDireccion(String direccion);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	SucursalModel getSucursal();

	List<TrabajadorModel> getTrabajadores();

}