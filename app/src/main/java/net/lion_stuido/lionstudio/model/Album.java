package net.lion_stuido.lionstudio.model;

import java.util.Date;

/**
 * Created by lester on 11.10.14.
 */
public class Album {
    private int id;
    private String ava;
    private String name;
    private Date dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}
