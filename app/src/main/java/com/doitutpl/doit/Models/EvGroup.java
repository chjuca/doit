package com.doitutpl.doit.Models;

public class EvGroup {

    private String keyGroup;
    private String name;

    public EvGroup() {
    }

    public EvGroup(String keyGroup, String name) {
        this.keyGroup = keyGroup;
        this.name = name;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
