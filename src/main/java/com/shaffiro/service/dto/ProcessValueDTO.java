package com.shaffiro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Agustin Gambirassi
 **/
public class ProcessValueDTO implements Serializable {
    private String action;
    private String id;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProcessValueDTO{" +
            "action='" + action + '\'' +
            ", id='" + id + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessValueDTO that = (ProcessValueDTO) o;
        return Objects.equals(action, that.action) &&
            Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, id);
    }
}
