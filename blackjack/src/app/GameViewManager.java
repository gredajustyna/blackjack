package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class GameViewManager {
    private AnchorPane gamePane;
    private AnchorPane timerPane;
    private final String FONT_PATH = "src/app/fonts/Righteous.ttf";
    private Scene gameScene;
    private Stage gameStage;
    private int numberOfPlayers;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private int repetitions;
    private int secondsLeft;
    private Text minutesText;
    private Text secondsText;
    MediaPlayer player;
    //Timer timer;



    public Stage getGameStage() {
        return gameStage;
    }



    public GameViewManager(boolean isSoundOn, boolean isMusicOn, int decks, int players){
        gamePane = new AnchorPane();
        timerPane = new AnchorPane();
        gameScene = new Scene(gamePane,WIDTH,HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setTitle("Blackjack");
        repetitions = 0;
        secondsLeft = 15;
        secondsText = new Text(String.valueOf(secondsLeft));
        minutesText = new Text("0:");
        createBackground();
        numberOfPlayers = players;
        //timer = new Timer();
        createUsersTable();
        createTimer();
        playRound();
        //TODO: muzyczka potem do dodania
//        if (isMusicOn){
//            Media sound = new Media(new File("src/app/music/theme.mp3").toURI().toString());
//            player = new MediaPlayer(sound);
//            player.setOnEndOfMedia(new Runnable() {
//                public void run() {
//                    player.seek(Duration.ZERO);
//                }
//            });
//            player.play();
//        }



    }

    private void createBackground(){
        Image backgroundImage = new Image("game_background.jpg",1200,800,false,false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        gamePane.setBackground(new Background(background));
    }

    private void createTimer(){
        HBox hBox = new HBox();
        hBox.setPrefWidth(100);
        hBox.setPrefHeight(50);
        hBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;");
        hBox.setAlignment(Pos.CENTER);
        //Text minutesText = new Text("0:");
        try {
            minutesText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            minutesText.setFont(Font.font("Verdana",30));
        }
        minutesText.setFill(Color.valueOf("000000"));


        //Text secondsText = new Text(String.valueOf(secondsLeft));
        try {
            secondsText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            secondsText.setFont(Font.font("Verdana",30));
        }
        secondsText.setFill(Color.valueOf("000000"));

        hBox.getChildren().add(minutesText);
        hBox.getChildren().add(secondsText);
        timerPane.getChildren().add(hBox);
        timerPane.setLayoutY(30);
        timerPane.setLayoutX(550);
        gamePane.getChildren().add(timerPane);
    }

    private void createUsersTable(){
        AnchorPane playersPane = new AnchorPane();
        gamePane.getChildren().add(playersPane);
        playersPane.setPrefWidth(1200);
        playersPane.setPrefHeight(650);
        playersPane.setLayoutY(150);

        switch (numberOfPlayers){
            case 2:
                AnchorPane player1Pane = new AnchorPane();
                gamePane.getChildren().add(player1Pane);


                break;
            case 3:
                break;
            case 4:
                break;
        }

    }
    private void updateSeconds(){
        if (secondsLeft<10){
            secondsText.setText("0"+ secondsLeft);
        }else{
            secondsText.setText(String.valueOf(secondsLeft));
        }
    }

    private void playRound() {
        repetitions = 0;
        secondsLeft = 15;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repetitions += 1;
                secondsLeft = 15;
                if (repetitions == numberOfPlayers) {
                    timer.cancel();
                }
                Timer secondTimer = new Timer();

                secondTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        secondsLeft -=1;
                        if (secondsLeft ==0) {
                            secondTimer.cancel();
                        }
                        System.out.println(secondsLeft);
                        updateSeconds();
                    }
                }, 0, 1000);
                System.out.println(repetitions + "repetitions");
            }
        }, 0, 15000);
    }




}
