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
 * Criteria class for the {@link com.provi.lab.domain.UserExtra} entity. This class is used
 * in {@link com.provi.lab.web.rest.UserExtraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-extras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserExtraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombreCompleto;

    private LongFilter areaId;

    private LongFilter personalId;

    private LongFilter userId;

    public UserExtraCriteria() {
    }

    public UserExtraCriteria(UserExtraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombreCompleto = other.nombreCompleto == null ? null : other.nombreCompleto.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.personalId = other.personalId == null ? null : other.personalId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public UserExtraCriteria copy() {
        return new UserExtraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(StringFilter nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getPersonalId() {
        return personalId;
    }

    public void setPersonalId(LongFilter personalId) {
        this.personalId = personalId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserExtraCriteria that = (UserExtraCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombreCompleto, that.nombreCompleto) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(personalId, that.personalId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombreCompleto,
        areaId,
        personalId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombreCompleto != null ? "nombreCompleto=" + nombreCompleto + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (personalId != null ? "personalId=" + personalId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
