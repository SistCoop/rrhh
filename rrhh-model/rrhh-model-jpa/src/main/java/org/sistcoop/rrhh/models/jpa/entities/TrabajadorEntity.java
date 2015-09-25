package org.sistcoop.rrhh.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TRABAJADOR")
@NamedQueries(value = {
        @NamedQuery(name = "TrabajadorEntity.findAll", query = "SELECT t FROM TrabajadorEntity t"),
        @NamedQuery(name = "TrabajadorEntity.findByTipoNumeroDocumento", query = "SELECT t FROM TrabajadorEntity t WHERE t.tipoDocumento = :tipoDocumento AND t.numeroDocumento = :numeroDocumento"),
        @NamedQuery(name = "TrabajadorEntity.findByIdAgencia", query = "SELECT t FROM TrabajadorEntity t INNER JOIN t.agencia a WHERE a.id = :idAgencia"),
        @NamedQuery(name = "TrabajadorEntity.findByIdTrabajadorUsuario", query = "SELECT t FROM TrabajadorEntity t INNER JOIN t.trabajadorUsuario tu WHERE tu.id = :idTrabajadorUsuario") })
public class TrabajadorEntity implements Serializable {

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
    @Size(min = 1, max = 20)
    @NotBlank
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    @NotNull
    @Size(min = 1, max = 20)
    @NotBlank
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "AGENCIA_ID")
    private AgenciaEntity agencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey)
    private AreaEntity area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey)
    private CargoEntity cargo;

    @OneToOne(orphanRemoval = true, cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "TRABAJADOR_USUARIO_ID", foreignKey = @ForeignKey)
    private TrabajadorUsuarioEntity trabajadorUsuario;

    @Version
    private Timestamp optlk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public AgenciaEntity getAgencia() {
        return agencia;
    }

    public void setAgencia(AgenciaEntity agencia) {
        this.agencia = agencia;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public CargoEntity getCargo() {
        return cargo;
    }

    public void setCargo(CargoEntity cargo) {
        this.cargo = cargo;
    }

    public TrabajadorUsuarioEntity getTrabajadorUsuario() {
        return trabajadorUsuario;
    }

    public void setTrabajadorUsuario(TrabajadorUsuarioEntity trabajadorUsuario) {
        this.trabajadorUsuario = trabajadorUsuario;
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
