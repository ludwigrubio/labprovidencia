package com.provi.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Recepcion.
 */
@Entity
@Table(name = "recepcion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recepcion extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "litros", nullable = false)
    private Float litros;

    @NotNull
    @Column(name = "tiempo", nullable = false)
    private Instant tiempo;

    @NotNull
    @Size(max = 1)
    @Column(name = "turno", length = 1, nullable = false)
    private String turno;

    @Column(name = "incentivo_lt")
    private Double incentivoLT;

    @Column(name = "incentivo_t")
    private Double incentivoT;

    @Size(max = 80)
    @Column(name = "tipo_leche", length = 80)
    private String tipoLeche;

    @Size(max = 80)
    @Column(name = "flete", length = 80)
    private String flete;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "recepcions", allowSetters = true)
    private Personal proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLitros() {
        return litros;
    }

    public Recepcion litros(Float litros) {
        this.litros = litros;
        return this;
    }

    public void setLitros(Float litros) {
        this.litros = litros;
    }

    public Instant getTiempo() {
        return tiempo;
    }

    public Recepcion tiempo(Instant tiempo) {
        this.tiempo = tiempo;
        return this;
    }

    public void setTiempo(Instant tiempo) {
        this.tiempo = tiempo;
    }

    public String getTurno() {
        return turno;
    }

    public Recepcion turno(String turno) {
        this.turno = turno;
        return this;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Double getIncentivoLT() {
        return incentivoLT;
    }

    public Recepcion incentivoLT(Double incentivoLT) {
        this.incentivoLT = incentivoLT;
        return this;
    }

    public void setIncentivoLT(Double incentivoLT) {
        this.incentivoLT = incentivoLT;
    }

    public Double getIncentivoT() {
        return incentivoT;
    }

    public Recepcion incentivoT(Double incentivoT) {
        this.incentivoT = incentivoT;
        return this;
    }

    public void setIncentivoT(Double incentivoT) {
        this.incentivoT = incentivoT;
    }

    public String getTipoLeche() {
        return tipoLeche;
    }

    public Recepcion tipoLeche(String tipoLeche) {
        this.tipoLeche = tipoLeche;
        return this;
    }

    public void setTipoLeche(String tipoLeche) {
        this.tipoLeche = tipoLeche;
    }

    public String getFlete() {
        return flete;
    }

    public Recepcion flete(String flete) {
        this.flete = flete;
        return this;
    }

    public void setFlete(String flete) {
        this.flete = flete;
    }

    public Personal getProveedor() {
        return proveedor;
    }

    public Recepcion proveedor(Personal personal) {
        this.proveedor = personal;
        return this;
    }

    public void setProveedor(Personal personal) {
        this.proveedor = personal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recepcion)) {
            return false;
        }
        return id != null && id.equals(((Recepcion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recepcion{" +
            "id=" + getId() +
            ", litros=" + getLitros() +
            ", tiempo='" + getTiempo() + "'" +
            ", turno='" + getTurno() + "'" +
            ", incentivoLT=" + getIncentivoLT() +
            ", incentivoT=" + getIncentivoT() +
            ", tipoLeche='" + getTipoLeche() + "'" +
            ", flete='" + getFlete() + "'" +
            "}";
    }
}
