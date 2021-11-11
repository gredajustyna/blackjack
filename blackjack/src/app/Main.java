package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application {
    MediaPlayer player;
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();
            Media sound = new Media(new File("src/app/music/theme.mp3").toURI().toString());
            player = new MediaPlayer(sound);
            player.setOnEndOfMedia(new Runnable() {
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
            player.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
