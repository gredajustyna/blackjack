package app;

import app.classes.GameButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
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
    private GameButton hitButton;
    private GameButton standButton;
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
        hitButton = new GameButton("hit", "chip_green");
        standButton = new GameButton("stand", "chip_red");
        createBackground();
        numberOfPlayers = players;
        createUsersTable(players);
        createTimer();
        buildHitButton();
        buildStandButton();
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

    private void createUsersTable(int numberOfPlayers){
        AnchorPane playersPane = new AnchorPane();
        gamePane.getChildren().add(playersPane);
        AnchorPane player1Pane = new AnchorPane();
        AnchorPane player2Pane = new AnchorPane();
        AnchorPane player3Pane = new AnchorPane();
        AnchorPane player4Pane = new AnchorPane();
        playersPane.setPrefWidth(1200);
        playersPane.setPrefHeight(650);
        playersPane.setLayoutY(150);

        switch (numberOfPlayers){
            case 2:
                System.out.println("this many players");
                //AnchorPane player1Pane = new AnchorPane();
                playersPane.getChildren().add(player1Pane);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

                //AnchorPane player2Pane = new AnchorPane();
                playersPane.getChildren().add(player2Pane);
                player2Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                        "    -fx-border-color: black;-fx-border-radius: 10px;");
                player2Pane.setPrefHeight(100);
                player2Pane.setPrefWidth(250);
                player2Pane.setLayoutY(225);
                player2Pane.setLayoutX(930);
                player2Pane.setEffect(new DropShadow());

                break;
            case 3:
                System.out.println("this many players");
                //AnchorPane player1Pane = new AnchorPane();
                playersPane.getChildren().add(player1Pane);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

                //AnchorPane player2Pane = new AnchorPane();
                playersPane.getChildren().add(player2Pane);
                player2Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                        "    -fx-border-color: black;-fx-border-radius: 10px;");
                player2Pane.setPrefHeight(100);
                player2Pane.setPrefWidth(250);
                player2Pane.setLayoutY(225);
                player2Pane.setLayoutX(930);
                player2Pane.setEffect(new DropShadow());

                //AnchorPane player3Pane = new AnchorPane();
                playersPane.getChildren().add(player3Pane);
                player3Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                        "    -fx-border-color: black;-fx-border-radius: 10px;");
                player3Pane.setPrefHeight(100);
                player3Pane.setPrefWidth(250);
                player3Pane.setLayoutY(25);
                player3Pane.setLayoutX(950);
                player3Pane.setEffect(new DropShadow());
                break;
            case 4:
                System.out.println("this many players");
                //AnchorPane player1Pane = new AnchorPane();
                playersPane.getChildren().add(player1Pane);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

                //AnchorPane player2Pane = new AnchorPane();
                playersPane.getChildren().add(player2Pane);
                player2Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                        "    -fx-border-color: black;-fx-border-radius: 10px;");
                player2Pane.setPrefHeight(100);
                player2Pane.setPrefWidth(250);
                player2Pane.setLayoutY(225);
                player2Pane.setLayoutX(930);
                player2Pane.setEffect(new DropShadow());

                //AnchorPane player3Pane = new AnchorPane();
                playersPane.getChildren().add(player3Pane);
                player3Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                        "    -fx-border-color: black;-fx-border-radius: 10px;");
                player3Pane.setPrefHeight(100);
                player3Pane.setPrefWidth(250);
                player3Pane.setLayoutY(-20);
                player3Pane.setLayoutX(475);
                player3Pane.setEffect(new DropShadow());

                playersPane.getChildren().add(player4Pane);
                player4Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                        "    -fx-border-color: black;-fx-border-radius: 10px;");
                player4Pane.setPrefHeight(100);
                player4Pane.setPrefWidth(250);
                player4Pane.setLayoutY(525);
                player4Pane.setLayoutX(475);
                player4Pane.setEffect(new DropShadow());
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

    private void buildHitButton(){
        gamePane.getChildren().add(hitButton);
        hitButton.setVisible(false);
        hitButton.setLayoutX(400);
        hitButton.setLayoutY(300);
    }

    private void buildStandButton(){
        gamePane.getChildren().add(standButton);
        standButton.setVisible(false);
        standButton.setLayoutY(300);
        standButton.setLayoutX(700);
    }




}
