package app;

import app.classes.CloseButton;
import app.classes.MainButton;
import app.classes.SoundButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private AnchorPane mainPane;
    private AnchorPane startPane;
    private AnchorPane helpPane;
    private MainButton startButton;
    private Scene mainScene;
    private Stage mainStage;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private final static int MENU_BUTTONS_START_X = 50;
    private final static int MENU_BUTTONS_START_Y = 250;

    List<MainButton> menuButtons;
    MediaPlayer player;

    public ViewManager(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        startPane = new AnchorPane();
        helpPane = new AnchorPane();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("Blackjack");
        createButtons();
        createBackground();
        Media sound = new Media(new File("src/app/music/theme.mp3").toURI().toString());
        player = new MediaPlayer(sound);
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();
        createSoundButton();
    }

    private void addMenuButton(MainButton button){
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createStartButton(){
        startButton = new MainButton("Start");
        addMenuButton(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createStartMenu();
                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(true);
                }
            }
        });
    }

    private void createStatsButton(){
        MainButton statsButton = new MainButton("Stats");
        addMenuButton(statsButton);
    }

    private void createScoreboardButton(){
        MainButton scoreboardButton = new MainButton("Scoreboard");
        addMenuButton(scoreboardButton);
    }

    private void createHelpButton(){
        MainButton helpButton = new MainButton("Help");
        addMenuButton(helpButton);
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createHelpMenu();
                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(true);
                }
            }
        });
    }

    private void createButtons(){
        createStartButton();
        createStatsButton();
        createScoreboardButton();
        createHelpButton();
        createExitButton();
        createUserPanel();
    }

    private void createBackground(){
        Image backgroundImage = new Image("main_background_2.jpg",1200,800,false,false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));
    }

    public Stage getMainStage(){
        return mainStage;
    }


    private void createExitButton(){
        MainButton exitButton = new MainButton("exit");
        addMenuButton(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createLogo(){
        ImageView logo = new ImageView("logo.gif");
        logo.setLayoutX(320);
        logo.setLayoutY(50);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }

    private void createUserPanel(){
        AnchorPane userPane = new AnchorPane();
        userPane.setLayoutX(900);
        userPane.setLayoutY(650);
        userPane.setPrefWidth(300);
        userPane.setPrefHeight(150);
        userPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");
        mainPane.getChildren().add(userPane);
    }

    private void createStartMenu(){
        startPane.setPrefHeight(600);
        startPane.setPrefWidth(500);
        startPane.setLayoutY(150);
        startPane.setLayoutX(350);
        startPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseStartButton();
        mainPane.getChildren().add(startPane);
    }

    private void createHelpMenu(){
        helpPane.setPrefHeight(600);
        helpPane.setPrefWidth(500);
        helpPane.setLayoutY(150);
        helpPane.setLayoutX(350);
        helpPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseHelpButton();
        mainPane.getChildren().add(helpPane);
    }

    private void createCloseStartButton(){
        CloseButton closeButton = new CloseButton(menuButtons, mainPane, startPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        startPane.getChildren().add(closeButton);
    }

    private void createCloseHelpButton(){
        CloseButton closeButton = new CloseButton(menuButtons, mainPane, helpPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        helpPane.getChildren().add(closeButton);
    }

    private void createSoundButton(){
        SoundButton button = new SoundButton(player);
        button.setLayoutY(740);
        button.setLayoutX(10);
        mainPane.getChildren().add(button);
    }

}
