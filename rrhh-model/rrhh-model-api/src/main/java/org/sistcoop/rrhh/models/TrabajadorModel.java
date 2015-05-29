package org.sistcoop.rrhh.models;

public interface TrabajadorModel extends Model {

	String getId();

	String getTipoDocumento();

	void setTipoDocumento(String tipoDocumento);

	String getNumeroDocumento();

	void setNumeroDocumento(String numeroDocumento);

	AgenciaModel getAgencia();

	void setAgencia(AgenciaModel agenciaModel);

	AreaModel getArea();

	void setArea(AreaModel areaModel);

	CargoModel getCargo();

	void setCargo(CargoModel cargoModel);
	
	TrabajadorUsuarioModel getTrabajadorUsuarioModel();

}