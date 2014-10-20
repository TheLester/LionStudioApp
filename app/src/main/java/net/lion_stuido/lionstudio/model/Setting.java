package net.lion_stuido.lionstudio.model;

/**
 * Created by lester on 11.10.14.
 */
public class Setting {
    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Android setting: pictures URL - " + url;
    }
}
