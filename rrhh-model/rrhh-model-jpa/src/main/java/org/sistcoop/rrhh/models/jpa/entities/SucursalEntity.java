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
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(
		name = "SUCURSAL", 
		indexes = { @Index(columnList = "id") } )
@NamedQueries({ 
	@NamedQuery(name = SucursalEntity.findAll, query = "SELECT s FROM SucursalEntity s"), 
	@NamedQuery(name = SucursalEntity.findByAbreviatura, query = "SELECT s FROM SucursalEntity s WHERE s.abreviatura = :abreviatura"),
	@NamedQuery(name = SucursalEntity.findByDenominacion, query = "SELECT s FROM SucursalEntity s WHERE s.denominacion = :denominacion"),
	@NamedQuery(name = SucursalEntity.findByEstado, query = "SELECT s FROM SucursalEntity s WHERE s.estado = :estado"),
	@NamedQuery(name = SucursalEntity.findByFilterText, query = "SELECT s FROM SucursalEntity s WHERE (UPPER(s.denominacion) LIKE :filterText OR s.abreviatura LIKE :filterText) AND s.estado = TRUE")})
public class SucursalEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String base = "org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.SucursalEntity.";
	public static final String findAll = base + "findAll";
	public static final String findByAbreviatura = base + "findByAbreviatura";
	public static final String findByDenominacion = base + "findByDenominacion";
	public static final String findByEstado = base + "findByEstado";
	public static final String findByFilterText = base + "findByFilterText";//por defecto solo busca activos

	private Integer id;
	private String denominacion;
	private String abreviatura;
	private boolean estado;

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
	@NotEmpty	
	@Column(name = "DENOMINACION", unique = true)
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@NotNull
	@Size(min = 1, max = 30)
	@NotBlank
	@NotEmpty
	@Column(name = "ABREVIATURA", unique = true)
	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	@Column(name = "ESTADO")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@OneToMany(fetch= FetchType.LAZY, mappedBy = "sucursal")
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
				+ ((abreviatura == null) ? 0 : abreviatura.hashCode());
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
		if (abreviatura == null) {
			if (other.abreviatura != null)
				return false;
		} else if (!abreviatura.equals(other.abreviatura))
			return false;
		return true;
	}
	
}
