package org.sistcoop.rrhh.models;

import java.util.List;

public interface SucursalModel extends Model {

	Integer getId();

	String getDenominacion();

	void setDenominacion(String denominacion);

	List<AgenciaModel> getAgencias();

}