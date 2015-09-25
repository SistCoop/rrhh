package org.sistcoop.rrhh.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "AGENCIA")
@NamedQueries(value = {
        @NamedQuery(name = "AgenciaEntity.findAll", query = "SELECT a FROM AgenciaEntity a"),
        @NamedQuery(name = "AgenciaEntity.findByIdSucursal", query = "SELECT a FROM AgenciaEntity a INNER JOIN a.sucursal s WHERE s.id = :idSucursal"),
        @NamedQuery(name = "AgenciaEntity.findByIdSucursalDenominacion", query = "SELECT a FROM AgenciaEntity a INNER JOIN a.sucursal s WHERE s.id = :idSucursal AND a.denominacion = :denominacion") })
public class AgenciaEntity implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @NotNull
    @Size(min = 1, max = 60)
    @NotBlank
    @NaturalId(mutable = true)
    @Column(name = "DENOMINACION")
    private String denominacion;

    @Size(min = 0, max = 150)
    @Column(name = "DIRECCION")
    private String direccion;

    @Size(min = 6, max = 6)
    @Column(name = "UBIGEO")
    private String ubigeo;

    @NotNull
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "SUCURSAL_ID")
    private SucursalEntity sucursal;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "agencia")
    private Set<TrabajadorEntity> trabajadores = new HashSet<TrabajadorEntity>();

    @Version
    private Timestamp optlk;

    public AgenciaEntity() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public SucursalEntity getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalEntity sucursal) {
        this.sucursal = sucursal;
    }

    public Set<TrabajadorEntity> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(Set<TrabajadorEntity> trabajadores) {
        this.trabajadores = trabajadores;
    }

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
        result = prime * result + ((denominacion == null) ? 0 : denominacion.hashCode());
        result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
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
        AgenciaEntity other = (AgenciaEntity) obj;
        if (denominacion == null) {
            if (other.denominacion != null)
                return false;
        } else if (!denominacion.equals(other.denominacion))
            return false;
        if (sucursal == null) {
            if (other.sucursal != null)
                return false;
        } else if (!sucursal.equals(other.sucursal))
            return false;
        return true;
    }

}
