package org.sistcoop.rrhh.representations.idm;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class SucursalRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String denominacion;

	@Size(min = 1)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Size(min = 1, max = 60)
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

}