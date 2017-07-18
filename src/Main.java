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
import java.awt.*;
import java.io.File;
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

    public ArrayList<String> readMusicFiles(String musicDirectory){
        ArrayList<String> songNames = new ArrayList<>();
        File musicFolder = new File(musicDirectory);
        File[] listOfSongs = musicFolder.listFiles();
        if(listOfSongs != null){
            int counter = 0;
            for (int i = 0; i < listOfSongs.length; i++) {
                if(isSong(listOfSongs[i].getName())){
                    songNames.add(listOfSongs[i].getName());
                    counter++;
                }
            }
            return songNames;
        }
        return new ArrayList<>();
    }

    public TableView constructTable(ArrayList<String> songs){
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

    public void initUserInterface(Stage primaryStage){
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

        String musicDirectory = "C:\\Users\\Public\\Music\\Sample Music";
        ArrayList<String> songs = readMusicFiles(musicDirectory);
        if(songs.size() > 0){
            TableView table = constructTable(songs);
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

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("images/jarvis.jpeg")));
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
