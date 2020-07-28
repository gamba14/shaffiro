package com.shaffiro.service.dto;

import java.io.Serializable;

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
}
