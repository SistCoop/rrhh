package org.sistcoop.rrhh.models;

import java.util.List;

public interface SucursalModel extends Model {

	String getId();

	String getDenominacion();

	void setDenominacion(String denominacion);

	List<AgenciaModel> getAgencias();

}