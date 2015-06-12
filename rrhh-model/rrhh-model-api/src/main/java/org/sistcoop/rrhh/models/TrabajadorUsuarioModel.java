package org.sistcoop.rrhh.models;

public interface TrabajadorUsuarioModel extends Model {

	String getId();

	String getUsuario();

	void setUsuario(String usuario);

	TrabajadorModel getTrabajador();

}