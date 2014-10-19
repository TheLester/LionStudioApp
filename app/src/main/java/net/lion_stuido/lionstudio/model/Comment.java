package net.lion_stuido.lionstudio.model;

/**
 * Created by lester on 14.10.14.
 */
public class Comment {
    private int id;
    private int photo_id;
    private String data;
    private String text;
    private String name;
    private boolean mod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMod() {
        return mod;
    }

    public void setMod(boolean mod) {
        this.mod = mod;
    }

    @Override
    public String toString() {
        return "Comment by "+name+",date "+data+",photo_id "+photo_id+":["+text+"]";
    }
}
