import com.google.gson.Gson;
import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.FieldKey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUserInterface(primaryStage);

        primaryStage.show();
    }

    public String readMusicFiles(String musicDirectory) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        ArrayList<Track> tracks = new ArrayList<>();
        File musicFolder = new File(musicDirectory);
        File[] listOfSongs = musicFolder.listFiles();
        if(listOfSongs != null){
            int counter = 0;
            Gson gson = new Gson();
            for (int i = 0; i < listOfSongs.length; i++) {
                if(isSong(listOfSongs[i].getName())){
                    MP3File f = new MP3File(listOfSongs[i]);
                    Tag tag = f.getTag();
                    Track track = new Track(tag.getFirst(FieldKey.TITLE), tag.getFirst(FieldKey.ARTIST), tag.getFirst(FieldKey.ALBUM), "0", tag.getFirst(FieldKey.YEAR), tag.getFirst(FieldKey.TRACK), tag.getFirst(FieldKey.DISC_NO), tag.getFirst(FieldKey.COMPOSER));
                    track.setCover_art(tag.getFirstArtwork().getBinaryData());
                    tracks.add(track);
                    counter++;
                }
            }
            return gson.toJson(tracks);
        }
        return "";
    }

    public TableView constructTable(Track[] songs){
        TableView table = new TableView();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(500);

        TableColumn Title = new TableColumn("Title");
        TableColumn Artist = new TableColumn("Artist");
        TableColumn Duration = new TableColumn("Duration");

        table.getColumns().add(Title);
        table.getColumns().add(Artist);
        table.getColumns().add(Duration);

        return table;
    }

    public boolean isSong(String songName){

        int index = songName.lastIndexOf('.');
        String ext = songName.substring(index+1);

        return (ext.equals("wav") || ext.equals("mp3") || ext.equals("mp4") || ext.equals("mid"));
    }

    public void initUserInterface(Stage primaryStage) throws IOException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        primaryStage.setTitle("C.Y.T.U.N.E.S");

        Image logo = new Image(Main.class.getResourceAsStream("images/jarvislogo.png"));
        Image black = new Image(Main.class.getResourceAsStream("images/black.jpg"));
        ImageView blackImage = new ImageView(black);
        ImageView logoview = new ImageView(logo);
        logoview.setOpacity(0);
        blackImage.setFitHeight(10000);
        blackImage.setFitWidth(10000);
        showLogoImage(logoview, blackImage);

        StackPane layout = new StackPane();

        layout.getChildren().add(blackImage);
        layout.getChildren().add(logoview);

        BackgroundImage bi = new BackgroundImage(new Image("images/ironman.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        layout.setBackground(new Background(bi));

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());



        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("images/jarvis.jpeg")));
    }

    public void displayTracks() throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        String musicDirectory = "C:\\Users\\Public\\Music\\Sample Music";
        String tracks = readMusicFiles(musicDirectory);

//        This code will get the tracks image and display it in the middle of the screen.
        Gson gson = new Gson();
        Track[] listOfTracks = gson.fromJson(tracks, Track[].class);
//        layout.getChildren().add(listOfTracks[0].getTrackArtwork());

        if(listOfTracks.length > 0){
            TableView table = constructTable(listOfTracks);
            VBox box = new VBox();
            box.setSpacing(5);
            box.setPadding(new Insets(10, 0, 0, 10));
            box.getChildren().add(table);
            box.maxWidth(500);
            box.setAlignment(Pos.CENTER);
            table.setOpacity(0);
            setFade(table);
            layout.getChildren().add(box);
        }
    }

    public void displayAlbums(){

    }

    public void displayArtists(){

    }

    public void showLogoImage(ImageView image, ImageView blackImage){
        FadeTransition ft = new FadeTransition();
        ft.setDelay(Duration.millis(1000));
        ft.setNode(image);
        ft.setDuration(new Duration(1000));
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setAutoReverse(false);
        ft.play();

        ScaleTransition st = new ScaleTransition(Duration.millis(1000), image);
        st.setDelay(Duration.millis(2000));
        st.setByX(10f);
        st.setByY(10f);
        st.setAutoReverse(false);
        st.play();

        FadeTransition f2 = new FadeTransition();
        f2.setDelay(Duration.millis(2000));
        f2.setNode(image);
        f2.setDuration(new Duration(250));
        f2.setFromValue(1.0);
        f2.setToValue(0.0);
        f2.setAutoReverse(false);
        f2.play();

        FadeTransition fBlack = new FadeTransition();
        fBlack.setDelay(Duration.millis(2000));
        fBlack.setNode(blackImage);
        fBlack.setDuration(new Duration(1000));
        fBlack.setFromValue(1.0);
        fBlack.setToValue(0.0);
        fBlack.setAutoReverse(false);
        fBlack.play();
    }

    public void setFade(Node node){
        FadeTransition ft = new FadeTransition();
        ft.setDelay(Duration.millis(2000));
        ft.setNode(node);
        ft.setDuration(new Duration(1000));
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setAutoReverse(false);
        ft.play();
    }


}
