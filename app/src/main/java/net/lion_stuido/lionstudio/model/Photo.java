package net.lion_stuido.lionstudio.model;

/**
 * Created by lester on 12.10.14.
 */
public class Photo {
    private int id;
    private int album_id;
    private String date;
    private String filename;
    private int like;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Photo: URL- "+filename+", album_id="+album_id+", date- "+date+", likes- "+like;
    }
}
