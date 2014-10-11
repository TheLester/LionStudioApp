package net.lion_stuido.lionstudio.model;

/**
 * Created by lester on 11.10.14.
 */
public class Album {
    private int id;
    private String ava;
    private String Name;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Album: ID-"+id+"URL thumb-"+ava+",name-"+Name +",date- " +data;
    }
}
