import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;

public class Track {

    private String title;
    private String[] artists;
    private String album;
    private String duration;
    private String year;
    private String track;
    private String disc_no;
    private String[] composers;
    private byte[] cover_art;

    public Track(){

    }

    public Track(String title, String artists, String album, String duration, String year, String track, String disc_no, String composers){
        this.title = title;
        this.artists = artists.split(",");
        this.album = album;
        this.duration = duration;
        this.year = year;
        this.track = track;
        this.disc_no = disc_no;
        this.composers = composers.split(",");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtists(String[] artists) {
        this.artists = artists;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setComposers(String[] composers) {
        this.composers = composers;
    }

    public byte[] getCover_art() {
        return cover_art;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public void setDisc_no(String disc_no) {
        this.disc_no = disc_no;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public String[] getArtists() {
        return artists;
    }

    public String getDuration() {
        return duration;
    }

    public String getYear() {
        return year;
    }

    public String getTrack() {
        return track;
    }

    public String getDisc_no() {
        return disc_no;
    }

    public String[] getComposers() {
        return composers;
    }

    public void setCover_art(byte[] cover_art) {
        this.cover_art = cover_art;
    }

    public ImageView getTrackArtwork(){
        Image artwork = new Image(new ByteArrayInputStream(this.cover_art));
        return new ImageView(artwork);
    }

    public String trackToJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
