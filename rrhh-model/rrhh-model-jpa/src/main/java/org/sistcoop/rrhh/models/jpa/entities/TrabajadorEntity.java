package org.sistcoop.rrhh.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TRABAJADOR")
public class TrabajadorEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String tipoDocumento;
	private String numeroDocumento;	
	private AgenciaEntity agencia;
	private AreaEntity area;
	private CargoEntity cargo;

	private Set<TrabajadorUsuarioEntity> trabajadorUsuarios = new HashSet<TrabajadorUsuarioEntity>();
	
	private Timestamp optlk;	

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 1, max = 20)
	@NotEmpty
	@NotBlank
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@NotNull
	@Size(min = 1, max = 20)
	@NotEmpty
	@NotBlank
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public AgenciaEntity getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEntity agencia) {
		this.agencia = agencia;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public AreaEntity getArea() {
		return area;
	}

	public void setArea(AreaEntity area) {
		this.area = area;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public CargoEntity getCargo() {
		return cargo;
	}

	public void setCargo(CargoEntity cargo) {
		this.cargo = cargo;
	}
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy = "trabajador")	
	public Set<TrabajadorUsuarioEntity> getTrabajadorUsuarios() {
		return trabajadorUsuarios;
	}

	public void setTrabajadorUsuarios(
			Set<TrabajadorUsuarioEntity> trabajadorUsuarios) {
		this.trabajadorUsuarios = trabajadorUsuarios;
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
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TrabajadorEntity))
			return false;
		TrabajadorEntity other = (TrabajadorEntity) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

}
