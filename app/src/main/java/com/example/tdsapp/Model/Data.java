package com.example.tdsapp.Model;

public class Data
{
    private String name;
    private String descritption;
    private String id;
    private String date;

    public Data(String name, String descritption, String id, String date) {
        this.name = name;
        this.descritption = descritption;
        this.id = id;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
