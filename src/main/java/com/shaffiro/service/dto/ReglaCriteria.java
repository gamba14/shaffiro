package com.shaffiro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Regla entity. This class is used in ReglaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /reglas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReglaCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter antecedente;

    private StringFilter concecuente;

    private StringFilter operador;

    private LongFilter dispositivoAsociadoId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(StringFilter antecedente) {
        this.antecedente = antecedente;
    }

    public StringFilter getConcecuente() {
        return concecuente;
    }

    public void setConcecuente(StringFilter concecuente) {
        this.concecuente = concecuente;
    }

    public StringFilter getOperador() {
        return operador;
    }

    public void setOperador(StringFilter operador) {
        this.operador = operador;
    }

    public LongFilter getDispositivoAsociadoId() {
        return dispositivoAsociadoId;
    }

    public void setDispositivoAsociadoId(LongFilter dispositivoAsociadoId) {
        this.dispositivoAsociadoId = dispositivoAsociadoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReglaCriteria that = (ReglaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(antecedente, that.antecedente) &&
            Objects.equals(concecuente, that.concecuente) &&
            Objects.equals(operador, that.operador) &&
            Objects.equals(dispositivoAsociadoId, that.dispositivoAsociadoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        antecedente,
        concecuente,
        operador,
        dispositivoAsociadoId
        );
    }

    @Override
    public String toString() {
        return "ReglaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (antecedente != null ? "antecedente=" + antecedente + ", " : "") +
                (concecuente != null ? "concecuente=" + concecuente + ", " : "") +
                (operador != null ? "operador=" + operador + ", " : "") +
                (dispositivoAsociadoId != null ? "dispositivoAsociadoId=" + dispositivoAsociadoId + ", " : "") +
            "}";
    }

}
