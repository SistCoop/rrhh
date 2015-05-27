package org.sistcoop.rrhh.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "SUCURSAL")
public class SucursalEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String denominacion;

	private Set<AgenciaEntity> agencias = new HashSet<AgenciaEntity>();

	private Timestamp optlk;

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 1, max = 60)
	@NotBlank
	@NaturalId(mutable = true)
	@Column(name = "DENOMINACION")
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sucursal")
	public Set<AgenciaEntity> getAgencias() {
		return agencias;
	}

	public void setAgencias(Set<AgenciaEntity> agencias) {
		this.agencias = agencias;
	}

	@Version
	public Timestamp getOptlk() {
		return optlk;
	}

	public void setOptlk(Timestamp optlk) {
		this.optlk = optlk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((denominacion == null) ? 0 : denominacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SucursalEntity other = (SucursalEntity) obj;
		if (denominacion == null) {
			if (other.denominacion != null)
				return false;
		} else if (!denominacion.equals(other.denominacion))
			return false;
		return true;
	}

}
