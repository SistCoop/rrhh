package org.sistcoop.rrhh.representations.idm;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class SucursalRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String denominacion;

	@Min(value = 1)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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