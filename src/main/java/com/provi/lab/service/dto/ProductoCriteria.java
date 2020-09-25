package com.provi.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.provi.lab.domain.Producto} entity. This class is used
 * in {@link com.provi.lab.web.rest.ProductoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter producto;

    private StringFilter clave;

    private StringFilter serieEAN;

    private LongFilter tipoId;

    public ProductoCriteria() {
    }

    public ProductoCriteria(ProductoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.producto = other.producto == null ? null : other.producto.copy();
        this.clave = other.clave == null ? null : other.clave.copy();
        this.serieEAN = other.serieEAN == null ? null : other.serieEAN.copy();
        this.tipoId = other.tipoId == null ? null : other.tipoId.copy();
    }

    @Override
    public ProductoCriteria copy() {
        return new ProductoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProducto() {
        return producto;
    }

    public void setProducto(StringFilter producto) {
        this.producto = producto;
    }

    public StringFilter getClave() {
        return clave;
    }

    public void setClave(StringFilter clave) {
        this.clave = clave;
    }

    public StringFilter getSerieEAN() {
        return serieEAN;
    }

    public void setSerieEAN(StringFilter serieEAN) {
        this.serieEAN = serieEAN;
    }

    public LongFilter getTipoId() {
        return tipoId;
    }

    public void setTipoId(LongFilter tipoId) {
        this.tipoId = tipoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductoCriteria that = (ProductoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(producto, that.producto) &&
            Objects.equals(clave, that.clave) &&
            Objects.equals(serieEAN, that.serieEAN) &&
            Objects.equals(tipoId, that.tipoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        producto,
        clave,
        serieEAN,
        tipoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (producto != null ? "producto=" + producto + ", " : "") +
                (clave != null ? "clave=" + clave + ", " : "") +
                (serieEAN != null ? "serieEAN=" + serieEAN + ", " : "") +
                (tipoId != null ? "tipoId=" + tipoId + ", " : "") +
            "}";
    }

}
