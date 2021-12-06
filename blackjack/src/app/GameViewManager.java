package app;

import app.classes.GameButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Timer;
import java.util.TimerTask;

public class GameViewManager {
    private AnchorPane gamePane;
    private AnchorPane timerPane;
    AnchorPane player1Pane = new AnchorPane();
    AnchorPane player2Pane = new AnchorPane();
    AnchorPane player3Pane = new AnchorPane();
    AnchorPane player4Pane = new AnchorPane();
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
    private int dealerPoints = 0;
    private int player1Points = 0;
    private int player2Points = 0;
    private int player3Points = 0;
    private int player4Points = 0;
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
        hitButton = new GameButton("hit", "chip_green.png");
        standButton = new GameButton("stand", "chip_red.png");
        createBackground();
        numberOfPlayers = players;
        createUsersTable();
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

    private void createUsersTable(){
        AnchorPane playersPane = new AnchorPane();
        gamePane.getChildren().add(playersPane);

        playersPane.getChildren().add(player1Pane);
        playersPane.getChildren().add(player2Pane);
        player2Pane.setVisible(false);

        playersPane.getChildren().add(player3Pane);
        player3Pane.setVisible(false);

        playersPane.getChildren().add(player4Pane);
        player4Pane.setVisible(false);

        AnchorPane dealerPane = new AnchorPane();
        playersPane.getChildren().add(dealerPane);
        dealerPane.setVisible( true);

        dealerPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                "    -fx-border-color: red;");
        dealerPane.setPrefHeight(100);
        dealerPane.setPrefWidth(250);
        dealerPane.setLayoutY(-20);
        dealerPane.setLayoutX(475);
        dealerPane.setEffect(new DropShadow());

        Text dealerText = new Text("Dealer");
        try {
            dealerText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            dealerText.setFont(Font.font("Verdana",30));
        }
        dealerPane.getChildren().add(dealerText);
        dealerText.setFill(Color.valueOf("000000"));
        dealerText.setLayoutX(80);
        dealerText.setLayoutY(35);

        Text dealerPointsText = new Text("Points: " + dealerPoints);
        try {
            dealerPointsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            dealerPointsText.setFont(Font.font("Verdana",20));
        }
        dealerPane.getChildren().add(dealerPointsText);
        dealerPointsText.setFill(Color.valueOf("000000"));
        dealerPointsText.setLayoutX(10);
        dealerPointsText.setLayoutY(75);

        Text player1Text = new Text("Player1"); //DOMYŚLNIE JEGO LOGIN
        try {
            player1Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player1Text.setFont(Font.font("Verdana",30));
        }
        player1Pane.getChildren().add(player1Text);
        player1Text.setFill(Color.valueOf("000000"));
        player1Text.setLayoutX(10);
        player1Text.setLayoutY(35);

        Text player1PointsText = new Text("Points: " + player1Points);
        try {
            player1PointsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player1PointsText.setFont(Font.font("Verdana",20));
        }
        player1Pane.getChildren().add(player1PointsText);
        player1PointsText.setFill(Color.valueOf("000000"));
        player1PointsText.setLayoutX(10);
        player1PointsText.setLayoutY(75);

        InputStream player1Ixs = getClass().getResourceAsStream("/player1.png");
        Image player1Image= new Image(player1Ixs);
        ImageView player1Avatar = new ImageView(player1Image);
        player1Avatar.setFitWidth(80);
        player1Avatar.setFitHeight(80);
        player1Avatar.setLayoutX(150);
        player1Avatar.setLayoutY(10);
        player1Pane.getChildren().add(player1Avatar);


        playersPane.setPrefWidth(1200);
        playersPane.setPrefHeight(650);
        playersPane.setLayoutY(150);

        switch (numberOfPlayers){
            case 1:
                System.out.println("HERE");
                System.out.println("this many players: " + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(525);
                player1Pane.setLayoutX(475);
                player1Pane.setEffect(new DropShadow());

                break;
            case 2:
                System.out.println("this many players: " + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

                //AnchorPane player2Pane = new AnchorPane();
                buildPlayer2Pane();

                break;
            case 3:
                System.out.println("this many players" + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

                buildPlayer2Pane();
                buildPlayer3Pane();

                break;
            case 4:
                System.out.println("this many players" + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

                buildPlayer2Pane();
                buildPlayer3Pane();
                buildPlayer4Pane();
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
        //hitButton.setVisible(false);
        hitButton.setLayoutX(350);
        hitButton.setLayoutY(500);
    }

    private void buildStandButton(){
        gamePane.getChildren().add(standButton);
        //standButton.setVisible(false);
        standButton.setLayoutY(500);
        standButton.setLayoutX(700);
    }

    private void buildPlayer2Pane(){
        player2Pane.setVisible(true);
        player2Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                "    -fx-border-color: black;-fx-border-radius: 10px;");
        player2Pane.setPrefHeight(100);
        player2Pane.setPrefWidth(250);
        player2Pane.setLayoutY(225);
        player2Pane.setLayoutX(930);
        player2Pane.setEffect(new DropShadow());

        Text player2Text = new Text("Player2"); //DOMYŚLNIE JEGO LOGIN
        try {
            player2Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player2Text.setFont(Font.font("Verdana",30));
        }
        player2Pane.getChildren().add(player2Text);
        player2Text.setFill(Color.valueOf("000000"));
        player2Text.setLayoutX(10);
        player2Text.setLayoutY(35);

        Text player2PointsText = new Text("Points: " + player2Points);
        try {
            player2PointsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player2PointsText.setFont(Font.font("Verdana",20));
        }
        player2Pane.getChildren().add(player2PointsText);
        player2PointsText.setFill(Color.valueOf("000000"));
        player2PointsText.setLayoutX(10);
        player2PointsText.setLayoutY(75);

        InputStream player2Ixs = getClass().getResourceAsStream("/player1.png");
        Image player2Image= new Image(player2Ixs);
        ImageView player2Avatar = new ImageView(player2Image);
        player2Avatar.setFitWidth(80);
        player2Avatar.setFitHeight(80);
        player2Avatar.setLayoutX(150);
        player2Avatar.setLayoutY(10);
        player2Pane.getChildren().add(player2Avatar);
    }

    private void buildPlayer3Pane(){
        player3Pane.setVisible(true);
        player3Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                "    -fx-border-color: black;-fx-border-radius: 10px;");
        player3Pane.setPrefHeight(100);
        player3Pane.setPrefWidth(250);
        player3Pane.setLayoutY(525);
        player3Pane.setLayoutX(475);
        player3Pane.setEffect(new DropShadow());

        Text player3Text = new Text("Player3"); //DOMYŚLNIE JEGO LOGIN
        try {
            player3Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player3Text.setFont(Font.font("Verdana",30));
        }
        player3Pane.getChildren().add(player3Text);
        player3Text.setFill(Color.valueOf("000000"));
        player3Text.setLayoutX(10);
        player3Text.setLayoutY(35);

        Text player3PointsText = new Text("Points: " + player2Points);
        try {
            player3PointsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player3PointsText.setFont(Font.font("Verdana",20));
        }
        player3Pane.getChildren().add(player3PointsText);
        player3PointsText.setFill(Color.valueOf("000000"));
        player3PointsText.setLayoutX(10);
        player3PointsText.setLayoutY(75);

        InputStream player3Ixs = getClass().getResourceAsStream("/player1.png");
        Image player3Image= new Image(player3Ixs);
        ImageView player3Avatar = new ImageView(player3Image);
        player3Avatar.setFitWidth(80);
        player3Avatar.setFitHeight(80);
        player3Avatar.setLayoutX(150);
        player3Avatar.setLayoutY(10);
        player3Pane.getChildren().add(player3Avatar);



    }

    private void buildPlayer4Pane(){
        player4Pane.setVisible(true);
        player4Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px;\n" +
                "    -fx-border-color: black;-fx-border-radius: 10px;");
        player4Pane.setPrefHeight(100);
        player4Pane.setPrefWidth(250);
        player4Pane.setLayoutY(525);
        player4Pane.setLayoutX(650);
        player4Pane.setEffect(new DropShadow());

        Text player4Text = new Text("Player4"); //DOMYŚLNIE JEGO LOGIN
        try {
            player4Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player4Text.setFont(Font.font("Verdana",30));
        }
        player4Pane.getChildren().add(player4Text);
        player4Text.setFill(Color.valueOf("000000"));
        player4Text.setLayoutX(10);
        player4Text.setLayoutY(35);

        Text player4PointsText = new Text("Points: " + player4Points);
        try {
            player4PointsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player4PointsText.setFont(Font.font("Verdana",20));
        }
        player4Pane.getChildren().add(player4PointsText);
        player4PointsText.setFill(Color.valueOf("000000"));
        player4PointsText.setLayoutX(10);
        player4PointsText.setLayoutY(75);

        InputStream player4Ixs = getClass().getResourceAsStream("/player1.png");
        Image player4Image= new Image(player4Ixs);
        ImageView player4Avatar = new ImageView(player4Image);
        player4Avatar.setFitWidth(80);
        player4Avatar.setFitHeight(80);
        player4Avatar.setLayoutX(150);
        player4Avatar.setLayoutY(10);
        player4Pane.getChildren().add(player4Avatar);
    }



}
