package com.example.blah;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class StartUp {
    private String owner;
    private String name;
    private String statusQuo;
    private boolean hasSolution;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusQuo() {
        return statusQuo;
    }

    public void setStatusQuo(String statusQuo) {
        this.statusQuo = statusQuo;
    }

    public boolean isHasSolution() {
        return hasSolution;
    }

    public void setHasSolution(boolean hasSolution) {
        this.hasSolution = hasSolution;
    }

    public StartUp(String name, String statusQuo, String owner, boolean hasSolution)
    {
        this.owner = owner;
        this.name = name;
        this.statusQuo = statusQuo;
        this.hasSolution = hasSolution;
    }

    public StartUp(){

    }

    @Override
    public String toString(){
        return "Startup{" +
                    "name='" + name + '\'' +
                    ", statusQuo='" + statusQuo + '\'' +
                    ", hasSolution='" + hasSolution + '\''
                + "}";
    }
}
