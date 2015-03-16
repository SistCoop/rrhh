package org.sistcoop.rrhh.models.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "TRABAJADOR")
public class UbicacionEntity {

	private String direccion;
	private String codigoPostal;
	private String codigoPais;
	

}
