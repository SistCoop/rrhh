package org.sistcoop.rrhh.representations.idm;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("unused")
@XmlRootElement(name = "trabajadorUsuario")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TrabajadorUsuarioRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String usuario;
	private TrabajadorRepresentation trabajador;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@NotBlank
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public TrabajadorRepresentation getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(TrabajadorRepresentation trabajador) {
		this.trabajador = trabajador;
	}

}