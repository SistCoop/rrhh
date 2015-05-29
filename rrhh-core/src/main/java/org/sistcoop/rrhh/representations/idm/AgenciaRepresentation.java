package org.sistcoop.rrhh.representations.idm;

import java.io.Serializable;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;

public class AgenciaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String denominacion;
	private String direccion;
	private String ubigeo;

	private SucursalRepresentation sucursal;

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

	@Size(min = 0, max = 150)
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Size(min = 6, max = 6)
	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	@XmlElement
	public SucursalRepresentation getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalRepresentation sucursal) {
		this.sucursal = sucursal;
	}

}
