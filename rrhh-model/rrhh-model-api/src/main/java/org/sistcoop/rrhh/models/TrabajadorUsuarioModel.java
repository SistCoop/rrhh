package org.sistcoop.rrhh.models;

public interface TrabajadorUsuarioModel extends Model {

	Integer getId();

	String getUsuario();

	void setUsuario(String usuario);

	TrabajadorModel getTrabajador();

}