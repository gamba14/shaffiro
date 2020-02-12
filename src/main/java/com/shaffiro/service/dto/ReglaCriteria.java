package com.shaffiro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.shaffiro.domain.enumeration.Unidad;
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
    /**
     * Class for filtering Unidad
     */
    public static class UnidadFilter extends Filter<Unidad> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private UnidadFilter unidad;

    private StringFilter valor;

    private StringFilter operador;

    private LongFilter dispositivoId;

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

    public UnidadFilter getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadFilter unidad) {
        this.unidad = unidad;
    }

    public StringFilter getValor() {
        return valor;
    }

    public void setValor(StringFilter valor) {
        this.valor = valor;
    }

    public StringFilter getOperador() {
        return operador;
    }

    public void setOperador(StringFilter operador) {
        this.operador = operador;
    }

    public LongFilter getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(LongFilter dispositivoId) {
        this.dispositivoId = dispositivoId;
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
            Objects.equals(unidad, that.unidad) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(operador, that.operador) &&
            Objects.equals(dispositivoId, that.dispositivoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        unidad,
        valor,
        operador,
        dispositivoId
        );
    }

    @Override
    public String toString() {
        return "ReglaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (unidad != null ? "unidad=" + unidad + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (operador != null ? "operador=" + operador + ", " : "") +
                (dispositivoId != null ? "dispositivoId=" + dispositivoId + ", " : "") +
            "}";
    }

}
