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

    private StringFilter logica;

    private StringFilter dispositivosAsociados;

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

    public StringFilter getLogica() {
        return logica;
    }

    public void setLogica(StringFilter logica) {
        this.logica = logica;
    }

    public StringFilter getDispositivosAsociados() {
        return dispositivosAsociados;
    }

    public void setDispositivosAsociados(StringFilter dispositivosAsociados) {
        this.dispositivosAsociados = dispositivosAsociados;
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
            Objects.equals(logica, that.logica) &&
            Objects.equals(dispositivosAsociados, that.dispositivosAsociados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        logica,
        dispositivosAsociados
        );
    }

    @Override
    public String toString() {
        return "ReglaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (logica != null ? "logica=" + logica + ", " : "") +
                (dispositivosAsociados != null ? "dispositivosAsociados=" + dispositivosAsociados + ", " : "") +
            "}";
    }

}
