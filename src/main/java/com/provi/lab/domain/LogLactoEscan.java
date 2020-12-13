package com.provi.lab.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A LogLactoEscan.
 */
@Entity
@Table(name = "log_lacto_escan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogLactoEscan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Size(max = 300)
    @Column(name = "nombre_archivo", length = 300, nullable = false)
    private String nombreArchivo;

    @Column(name = "numero_fila")
    private Integer numeroFila;

    @NotNull
    @Size(max = 300)
    @Column(name = "mensaje_error", length = 300, nullable = false)
    private String mensajeError;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public LogLactoEscan tipo(Integer tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Instant getFecha() {
        return fecha;
    }

    public LogLactoEscan fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public LogLactoEscan nombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        return this;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getNumeroFila() {
        return numeroFila;
    }

    public LogLactoEscan numeroFila(Integer numeroFila) {
        this.numeroFila = numeroFila;
        return this;
    }

    public void setNumeroFila(Integer numeroFila) {
        this.numeroFila = numeroFila;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public LogLactoEscan mensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
        return this;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogLactoEscan)) {
            return false;
        }
        return id != null && id.equals(((LogLactoEscan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogLactoEscan{" +
            "id=" + getId() +
            ", tipo=" + getTipo() +
            ", fecha='" + getFecha() + "'" +
            ", nombreArchivo='" + getNombreArchivo() + "'" +
            ", numeroFila=" + getNumeroFila() +
            ", mensajeError='" + getMensajeError() + "'" +
            "}";
    }
}
