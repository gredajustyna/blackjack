package app;

import app.classes.*;
import app.database.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MenuViewManager {
    private AnchorPane mainPane;
    private AnchorPane startPane;
    private AnchorPane helpPane;
    private AnchorPane loginPane;
    private AnchorPane registerPane;
    private AnchorPane userPane;
    private AnchorPane player1Pane;
    private AnchorPane player2Pane;
    private AnchorPane player3Pane;
    private AnchorPane player4Pane;
    private MainButton startButton;
    private SignButton mainLoginButton;
    private SignButton mainRegisterButton;
    private SignButton mainLogOutButton;
    private Text noUserDetected;
    private Text userDetected;
    private ImageView userAvatar;
    private Text username;
    private Text level;
    private Scene mainScene;
    private Stage mainStage;
    private ToggleGroup playerToggle;
    private ToggleGroup group;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private final static int MENU_BUTTONS_START_X = 50;
    private final static int MENU_BUTTONS_START_Y = 250;
    private final String FONT_PATH = "src/app/fonts/Righteous.ttf";
    private boolean isLoggedIn = false;

    private String login;
    public static String login_buff;
    private boolean isLoggedIn2 = false;
    private boolean isLoggedIn3 = false;
    private boolean isLoggedIn4 = false;
    public static User user1 = new User();
    public static User user2 = new User();
    public static User user3 = new User();
    public static User user4 = new User();
    static FileInputStream fis; // do avatara
    static int length; // do avatara

    List<MainButton> menuButtons;
    List<SignButton> signButtons;
    MediaPlayer player;
    public static boolean isSoundOn = true;

    public MenuViewManager(){
        menuButtons = new ArrayList<>();
        signButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        startPane = new AnchorPane();
        helpPane = new AnchorPane();
        loginPane = new AnchorPane();
        registerPane = new AnchorPane();
        userPane = new AnchorPane();
        player1Pane = new AnchorPane();
        player2Pane = new AnchorPane();
        player3Pane = new AnchorPane();
        player4Pane = new AnchorPane();
        playerToggle = new ToggleGroup();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        userAvatar = new ImageView();
        group = new ToggleGroup();
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
        createMusicButton();
        createSoundButton();


        DbConnection.connect();
    }

    private void addMenuButton(MainButton button){
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void addSignButton(SignButton button){
        signButtons.add(button);
    }

    private void createStartButton(){
        startButton = new MainButton("Start");
        addMenuButton(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isLoggedIn) {
                    user1.setLogin(login_buff);
                    System.out.println(user1.getLogin());
                    createStartMenu();
                    for (int i = 0; i < menuButtons.size(); i++) {
                        menuButtons.get(i).setDisable(true);
                    }
                    for (int i = 0; i < signButtons.size(); i++) {
                        signButtons.get(i).setDisable(true);
                    }
                }else{}
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
                for (int i=0; i<signButtons.size(); i++){
                    signButtons.get(i).setDisable(true);
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
        createGameStartButton();
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
        createLogOutButton();
        mainLogOutButton.setVisible(false);



        userDetected = new Text("Logged in.");
        userDetected.setFont(Font.font("src/app/fonts/Righteous-Regular", 13));
        userDetected.setFill(Color.valueOf("FFFFFF"));
        userDetected.setLayoutX(50);
        userDetected.setLayoutY(30);
        userPane.getChildren().add(userDetected);
        userDetected.setVisible(false);


        userPane.setLayoutX(900);
        userPane.setLayoutY(650);
        userPane.setPrefWidth(300);
        userPane.setPrefHeight(150);
        userPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");
        mainLoginButton = new SignButton("log in");
        mainLoginButton.setLayoutX(75);
        mainLoginButton.setLayoutY(50);
        userPane.getChildren().add(mainLoginButton);
        mainLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createLoginPanel();

            }
        });
        addSignButton(mainLoginButton);

        mainRegisterButton = new SignButton("register");
        mainRegisterButton.setLayoutX(75);
        mainRegisterButton.setLayoutY(95);
        userPane.getChildren().add(mainRegisterButton);
        mainRegisterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createRegisterPanel();
                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(true);
                }
                for (int i=0; i<signButtons.size(); i++){
                    signButtons.get(i).setDisable(true);
                }
            }
        });
        addSignButton(mainRegisterButton);

        noUserDetected = new Text("No user detected. Log in or register.");
        try {
            noUserDetected.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 13));
        }catch (FileNotFoundException e){
            noUserDetected.setFont(Font.font("Verdana", 13));
        }
        noUserDetected.setFill(Color.valueOf("FFFFFF"));
        noUserDetected.setLayoutX(40);
        noUserDetected.setLayoutY(30);
        userPane.getChildren().add(noUserDetected);


        mainPane.getChildren().add(userPane);
    }

    private void createStartMenu(){
        player4Pane.setVisible(false);
        player3Pane.setVisible(false);
        player2Pane.setVisible(false);
        startPane.setPrefHeight(600);
        startPane.setPrefWidth(500);
        startPane.setLayoutY(150);
        startPane.setLayoutX(350);
        startPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

        Text startText = new Text("Start");
        try {
            startText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            startText.setFont(Font.font("Verdana",30));
        }
        startText.setFill(Color.valueOf("FFFFFF"));
        startText.setLayoutX(200);
        startText.setLayoutY(50);
        startPane.getChildren().add(startText);

        Text selectCards = new Text("Decks:");
        try {
            selectCards.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
        }catch (FileNotFoundException e){
            selectCards.setFont(Font.font("Verdana",25));
        }
        selectCards.setFill(Color.valueOf("FFFFFF"));
        selectCards.setLayoutX(210);
        selectCards.setLayoutY(120);
        startPane.getChildren().add(selectCards);

        AnchorPane decksPane = new AnchorPane();

        InputStream is1 = getClass().getResourceAsStream("/deck1.png");
        Image img1= new Image(is1);
        ImageView button1img = new ImageView(img1);
        button1img.setFitHeight(50);
        button1img.setFitWidth(50);
        button1img.setPreserveRatio(true);
        RadioButton button1 = new RadioButton("");
        button1.setGraphic(button1img);
        button1.setToggleGroup(group);
        button1.setSelected(true);
        button1.setUserData(1);
        button1.setLayoutX(50);
        button1.setLayoutY(150);


        InputStream is2 = getClass().getResourceAsStream("/deck2.png");
        Image img2= new Image(is2);
        ImageView button2img = new ImageView(img2);
        button2img.setFitHeight(50);
        button2img.setFitWidth(50);
        button2img.setPreserveRatio(true);
        RadioButton button2 = new RadioButton("");
        button2.setGraphic(button2img);
        button2.setToggleGroup(group);
        button2.setUserData(2);
        button2.setSelected(false);
        button2.setLayoutX(150);
        button2.setLayoutY(150);

        InputStream is3 = getClass().getResourceAsStream("/deck3.png");
        Image img3= new Image(is3);
        ImageView button3img = new ImageView(img3);
        button3img.setFitHeight(50);
        button3img.setFitWidth(50);
        button3img.setPreserveRatio(true);
        RadioButton button3 = new RadioButton("");
        button3.setGraphic(button3img);
        button3.setToggleGroup(group);
        button3.setSelected(false);
        button3.setUserData(3);
        button3.setLayoutX(250);
        button3.setLayoutY(150);

        decksPane.getChildren().add(button1);
        decksPane.getChildren().add(button2);
        decksPane.getChildren().add(button3);
        decksPane.setLayoutX(60);
        startPane.getChildren().add(decksPane);

        Text players = new Text("Players:");
        try {
            players.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
        }catch (FileNotFoundException e){
            players.setFont(Font.font("Verdana",25));
        }
        players.setFill(Color.valueOf("FFFFFF"));
        players.setLayoutX(200);
        players.setLayoutY(250);
        startPane.getChildren().add(players);

        AnchorPane playersPane = new AnchorPane();
        startPane.getChildren().add(playersPane);
        playersPane.setLayoutX(25);
        playersPane.setLayoutY(120);

        InputStream is00 = getClass().getResourceAsStream("/player1.png");
        Image img00= new Image(is00);
        ImageView button00img = new ImageView(img00);
        button00img.setFitHeight(50);
        button00img.setFitWidth(50);
        button00img.setPreserveRatio(true);
        RadioButton button00 = new RadioButton("");
        button00.setGraphic(button00img);
        button00.setUserData(1);
        button00.setToggleGroup(playerToggle);
        button00.setSelected(true);
        button00.setLayoutX(50);
        button00.setLayoutY(150);
        button00.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updatePlayerVisibility(button00);
        });

        InputStream is11 = getClass().getResourceAsStream("/players2.png");
        Image img11= new Image(is11);
        ImageView button11img = new ImageView(img11);
        button11img.setFitHeight(50);
        button11img.setFitWidth(50);
        button11img.setPreserveRatio(true);
        RadioButton button11 = new RadioButton("");
        button11.setGraphic(button11img);
        button11.setUserData(2);
        button11.setToggleGroup(playerToggle);
        button11.setSelected(false);
        button11.setLayoutX(150);
        button11.setLayoutY(150);
        button11.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updatePlayerVisibility(button11);
        });



        InputStream is22 = getClass().getResourceAsStream("/players3.png");
        Image img22= new Image(is22);
        ImageView button22img = new ImageView(img22);
        button22img.setFitHeight(50);
        button22img.setFitWidth(50);
        button22img.setPreserveRatio(true);
        RadioButton button22 = new RadioButton("");
        button22.setGraphic(button22img);
        button22.setUserData(3);
        button22.setToggleGroup(playerToggle);
        button22.setSelected(false);
        button22.setLayoutX(250);
        button22.setLayoutY(150);
        button22.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updatePlayerVisibility(button22);
        });

        InputStream is33 = getClass().getResourceAsStream("/players4.png");
        Image img33= new Image(is33);
        ImageView button33img = new ImageView(img33);
        button33img.setFitHeight(50);
        button33img.setFitWidth(50);
        button33img.setPreserveRatio(true);
        RadioButton button33 = new RadioButton("");
        button33.setGraphic(button33img);
        button33.setUserData(4);
        button33.setToggleGroup(playerToggle);
        button33.setSelected(false);
        button33.setLayoutX(350);
        button33.setLayoutY(150);
        button33.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updatePlayerVisibility(button33);
        });

        playersPane.getChildren().add(button00);
        playersPane.getChildren().add(button11);
        playersPane.getChildren().add(button22);
        playersPane.getChildren().add(button33);

        Text player1 = new Text("Player 1:");
        try {
            player1.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player1.setFont(Font.font("Verdana",20));
        }
        player1.setFill(Color.valueOf("FFFFFF"));

        ToggleGroup player1group = new ToggleGroup();
        InputStream ispl1 = getClass().getResourceAsStream("/player1.png");
        Image imgpl1= new Image(ispl1);
        ImageView buttonpl1 = new ImageView(imgpl1);
        buttonpl1.setFitHeight(50);
        buttonpl1.setFitWidth(50);
        buttonpl1.setPreserveRatio(true);
        RadioButton buttonPlayer1 = new RadioButton("");
        buttonPlayer1.setGraphic(buttonpl1);
        buttonPlayer1.setUserData(3);
        buttonPlayer1.setToggleGroup(player1group);
        buttonPlayer1.setSelected(true);
        buttonPlayer1.setLayoutX(110);
        buttonPlayer1.setLayoutY(-30);


        InputStream iscom1 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom1= new Image(iscom1);
        ImageView buttoncom1 = new ImageView(imgcom1);
        buttoncom1.setFitHeight(40);
        buttoncom1.setFitWidth(40);
        buttoncom1.setPreserveRatio(true);
        RadioButton buttonComputer1 = new RadioButton("");
        buttonComputer1.setGraphic(buttoncom1);
        buttonComputer1.setUserData(3);
        buttonComputer1.setToggleGroup(player1group);
        buttonComputer1.setSelected(false);
        buttonComputer1.setLayoutX(200);
        buttonComputer1.setLayoutY(-24);


        player1Pane.getChildren().add(player1);
        player1Pane.setLayoutX(40);
        player1Pane.setLayoutY(350);
        player1Pane.getChildren().add(buttonPlayer1);
        player1Pane.getChildren().add(buttonComputer1);
        startPane.getChildren().add(player1Pane);


        Text player2 = new Text("Player 2:");
        try {
            player2.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player2.setFont(Font.font("Verdana",20));
        }
        player2.setFill(Color.valueOf("FFFFFF"));

        ToggleGroup player2group = new ToggleGroup();
        InputStream ispl2 = getClass().getResourceAsStream("/player1.png");
        Image imgpl2= new Image(ispl2);
        ImageView buttonpl2 = new ImageView(imgpl2);
        buttonpl2.setFitHeight(50);
        buttonpl2.setFitWidth(50);
        buttonpl2.setPreserveRatio(true);
        RadioButton buttonPlayer2 = new RadioButton("");
        buttonPlayer2.setGraphic(buttonpl2);
        buttonPlayer2.setUserData(3);
        buttonPlayer2.setToggleGroup(player2group);
        buttonPlayer2.setSelected(false);
        buttonPlayer2.setLayoutX(110);
        buttonPlayer2.setLayoutY(-30);

        InputStream iscom2 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom2= new Image(iscom2);
        ImageView buttoncom2 = new ImageView(imgcom2);
        buttoncom2.setFitHeight(40);
        buttoncom2.setFitWidth(40);
        buttoncom2.setPreserveRatio(true);
        RadioButton buttonComputer2 = new RadioButton("");
        buttonComputer2.setGraphic(buttoncom2);
        buttonComputer2.setUserData(3);
        buttonComputer2.setToggleGroup(player2group);
        buttonComputer2.setSelected(true);
        buttonComputer2.setLayoutX(200);
        buttonComputer2.setLayoutY(-24);

        buttonPlayer2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonPlayer2.isSelected()) {
                    isLoggedIn2 = true;
                    mainPane.getChildren().add(loginPane);
                }
            }
        });

        player2Pane.getChildren().add(player2);
        player2Pane.getChildren().add(buttonComputer2);
        player2Pane.getChildren().add(buttonPlayer2);
        player2Pane.setLayoutX(40);
        player2Pane.setLayoutY(400);
        startPane.getChildren().add(player2Pane);

        Text player3 = new Text("Player 3:");
        try {
            player3.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player3.setFont(Font.font("Verdana",20));
        }
        player3.setFill(Color.valueOf("FFFFFF"));

        ToggleGroup player3group = new ToggleGroup();
        InputStream ispl3 = getClass().getResourceAsStream("/player1.png");
        Image imgpl3= new Image(ispl3);
        ImageView buttonpl3 = new ImageView(imgpl3);
        buttonpl3.setFitHeight(50);
        buttonpl3.setFitWidth(50);
        buttonpl3.setPreserveRatio(true);
        RadioButton buttonPlayer3 = new RadioButton("");
        buttonPlayer3.setGraphic(buttonpl3);
        buttonPlayer3.setUserData(3);
        buttonPlayer3.setToggleGroup(player3group);
        buttonPlayer3.setSelected(false);
        buttonPlayer3.setLayoutX(110);
        buttonPlayer3.setLayoutY(-30);

        InputStream iscom3 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom3= new Image(iscom3);
        ImageView buttoncom3 = new ImageView(imgcom3);
        buttoncom3.setFitHeight(40);
        buttoncom3.setFitWidth(40);
        buttoncom3.setPreserveRatio(true);
        RadioButton buttonComputer3 = new RadioButton("");
        buttonComputer3.setGraphic(buttoncom3);
        buttonComputer3.setUserData(3);
        buttonComputer3.setToggleGroup(player3group);
        buttonComputer3.setSelected(true);
        buttonComputer3.setLayoutX(200);
        buttonComputer3.setLayoutY(-24);

        buttonPlayer3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonPlayer3.isSelected()) {
                    isLoggedIn3 = true;
                    mainPane.getChildren().add(loginPane);
                }
            }
        });

        player3Pane.getChildren().add(player3);
        player3Pane.getChildren().add(buttonPlayer3);
        player3Pane.getChildren().add(buttonComputer3);
        player3Pane.setLayoutX(40);
        player3Pane.setLayoutY(450);
        startPane.getChildren().add(player3Pane);

        Text player4 = new Text("Player 4:");
        try {
            player4.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            player4.setFont(Font.font("Verdana",20));
        }
        player4.setFill(Color.valueOf("FFFFFF"));

        ToggleGroup player4group = new ToggleGroup();
        InputStream ispl4 = getClass().getResourceAsStream("/player1.png");
        Image imgpl4= new Image(ispl4);
        ImageView buttonpl4 = new ImageView(imgpl4);
        buttonpl4.setFitHeight(50);
        buttonpl4.setFitWidth(50);
        buttonpl4.setPreserveRatio(true);
        RadioButton buttonPlayer4 = new RadioButton("");
        buttonPlayer4.setGraphic(buttonpl4);
        buttonPlayer4.setUserData(3);
        buttonPlayer4.setToggleGroup(player4group);
        buttonPlayer4.setSelected(false);
        buttonPlayer4.setLayoutX(110);
        buttonPlayer4.setLayoutY(-30);

        InputStream iscom4 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom4= new Image(iscom4);
        ImageView buttoncom4 = new ImageView(imgcom4);
        buttoncom4.setFitHeight(40);
        buttoncom4.setFitWidth(40);
        buttoncom4.setPreserveRatio(true);
        RadioButton buttonComputer4 = new RadioButton("");
        buttonComputer4.setGraphic(buttoncom4);
        buttonComputer4.setUserData(3);
        buttonComputer4.setToggleGroup(player4group);
        buttonComputer4.setSelected(true);
        buttonComputer4.setLayoutX(200);
        buttonComputer4.setLayoutY(-24);

        buttonPlayer4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonPlayer4.isSelected()) {
                    isLoggedIn4 = true;
                    mainPane.getChildren().add(loginPane);
                }
            }
        });

        player4Pane.getChildren().add(player4);
        player4Pane.getChildren().add(buttonComputer4);
        player4Pane.getChildren().add(buttonPlayer4);
        player4Pane.setLayoutX(40);
        player4Pane.setLayoutY(500);
        startPane.getChildren().add(player4Pane);


        createCloseStartButton();
        mainPane.getChildren().add(startPane);
    }

    private void updatePlayerVisibility(ToggleButton button){
        int result = (int) button.getUserData();
        switch (result){
            case 1:
                player1Pane.setVisible(true);
                player2Pane.setVisible(false);
                player3Pane.setVisible(false);
                player4Pane.setVisible(false);
            case 2:
                player1Pane.setVisible(true);
                player2Pane.setVisible(true);
                player3Pane.setVisible(false);
                player4Pane.setVisible(false);
                break;
            case 3:
                player1Pane.setVisible(true);
                player2Pane.setVisible(true);
                player3Pane.setVisible(true);
                player4Pane.setVisible(false);
                break;
            case 4:
                player1Pane.setVisible(true);
                player2Pane.setVisible(true);
                player3Pane.setVisible(true);
                player4Pane.setVisible(true);
                break;
        }
    }

    private void createHelpMenu(){
        helpPane.setPrefHeight(600);
        helpPane.setPrefWidth(500);
        helpPane.setLayoutY(150);
        helpPane.setLayoutX(350);
        helpPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseHelpButton();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(500);
        scrollPane.setPrefWidth(580);
        scrollPane.setLayoutX(10);
        scrollPane.setLayoutY(50);
        scrollPane.setStyle("-fx-background-color: transparent;");
        helpPane.getChildren().add(scrollPane);

        Text helpText = new Text(HelpText.getHelpText1());
        scrollPane.setContent(helpText);

        mainPane.getChildren().add(helpPane);
    }

    private void createCloseStartButton(){
        CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, startPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        startPane.getChildren().add(closeButton);
    }

    private void createCloseHelpButton(){
        CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, helpPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        helpPane.getChildren().add(closeButton);
    }

    private void createMusicButton(){
        MusicButton button = new MusicButton(player);
        button.setLayoutY(740);
        button.setLayoutX(10);
        mainPane.getChildren().add(button);
    }

    private void createSoundButton(){
        SoundButton button = new SoundButton();
        button.setLayoutY(740);
        button.setLayoutX(70);
        mainPane.getChildren().add(button);
    }

    public void createLoginPanel(){
        loginPane.setPrefHeight(300);
        loginPane.setPrefWidth(500);
        loginPane.setLayoutY(250);
        loginPane.setLayoutX(350);
        loginPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseLoginButton();

        Text loginText = new Text("Login");
        try {
            loginText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            loginText.setFont(Font.font("Verdana",30));
        }
        loginText.setFill(Color.valueOf("FFFFFF"));
        loginText.setLayoutX(200);
        loginText.setLayoutY(45);
        loginPane.getChildren().add(loginText);

        Text username = new Text("Username:");
        try {
            username.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            username.setFont(Font.font("Verdana",15));
        }
        username.setFill(Color.valueOf("FFFFFF"));
        username.setLayoutX(40);
        username.setLayoutY(125);
        loginPane.getChildren().add(username);

        TextField usernameField = new TextField();
        usernameField.setLayoutX(190);
        usernameField.setLayoutY(100);
        usernameField.setPrefHeight(40);
        usernameField.setPrefWidth(250);
        loginPane.getChildren().add(usernameField);

        Text password = new Text("Password:");
        try {
            password.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            password.setFont(Font.font("Verdana",15));
        }
        password.setFill(Color.valueOf("FFFFFF"));
        password.setLayoutX(40);
        password.setLayoutY(175);
        loginPane.getChildren().add(password);

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(190);
        passwordField.setLayoutY(150);
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(250);
        loginPane.getChildren().add(passwordField);

        MainButton loginButton = new MainButton("Log in");
        loginButton.setLayoutX(130);
        loginButton.setLayoutY(220);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login_buff = DbConnection.Login(usernameField.getText(), passwordField.getText());
                if (login_buff != "0"){
                    isLoggedIn = true;
                }else{
                    isLoggedIn = false;
                }
                if(login_buff != "0" && isLoggedIn2){
                    user2.setLogin(login_buff);
                    isLoggedIn2 = false;
                }
                if(login_buff != "0" && isLoggedIn3){
                    user3.setLogin(login_buff);
                    isLoggedIn3 = false;
                }
                if(login_buff != "0" && isLoggedIn4){
                    user4.setLogin(login_buff);
                    isLoggedIn4 = false;
                }
                mainPane.getChildren().remove(loginPane);
                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(false);
                }
                for (int i=0; i<signButtons.size(); i++){
                    signButtons.get(i).setDisable(false);
                }
                updateUserPanel();

            }
        });

        loginPane.getChildren().add(loginButton);

        mainPane.getChildren().add(loginPane);
    }

    private void updateUserPanel(){
        if(isLoggedIn){
            mainLoginButton.setVisible(false);
            mainRegisterButton.setVisible(false);
            noUserDetected.setVisible(false);
            userDetected.setVisible(true);

            Image image = DbConnection.setAvatar(login);
            userAvatar.setImage(image);
            userAvatar.setLayoutY(20);
            userAvatar.setLayoutX(20);
            userAvatar.setFitWidth(150);
            userAvatar.setFitHeight(150);
            userAvatar.setVisible(true);
            //userPane.getChildren().add(userAvatar);


            mainLogOutButton.setVisible(true);
        }else{
            mainLoginButton.setVisible(true);
            mainRegisterButton.setVisible(true);
            noUserDetected.setVisible(true);
            userDetected.setVisible(false);

            mainLogOutButton.setVisible(false);
            userAvatar.setVisible(false);
           // userPane.getChildren().remove(userAvatar);

        }
    }

    private void createRegisterPanel(){
        registerPane.setPrefHeight(600);
        registerPane.setPrefWidth(500);
        registerPane.setLayoutY(150);
        registerPane.setLayoutX(350);
        registerPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseRegisterButton();

        Text registerText = new Text("Register");
        try {
            registerText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            registerText.setFont(Font.font("Verdana",30));
        }
        registerText.setFill(Color.valueOf("FFFFFF"));
        registerText.setLayoutX(180);
        registerText.setLayoutY(50);
        registerPane.getChildren().add(registerText);

        Text username = new Text("Username:");
        try {
            username.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            username.setFont(Font.font("Verdana",15));
        }
        username.setFill(Color.valueOf("FFFFFF"));
        username.setLayoutX(40);
        username.setLayoutY(125);
        registerPane.getChildren().add(username);

        TextField usernameField = new TextField();
        usernameField.setLayoutX(190);
        usernameField.setLayoutY(100);
        usernameField.setPrefHeight(40);
        usernameField.setPrefWidth(250);
        registerPane.getChildren().add(usernameField);

        Text password = new Text("Password:");
        try {
            password.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            password.setFont(Font.font("Verdana",15));
        }
        password.setFill(Color.valueOf("FFFFFF"));
        password.setLayoutX(40);
        password.setLayoutY(175);
        registerPane.getChildren().add(password);

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(190);
        passwordField.setLayoutY(150);
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(250);
        registerPane.getChildren().add(passwordField);

        Text confirmPassword = new Text("Confirm password:");
        try {
            confirmPassword.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            confirmPassword.setFont(Font.font("Verdana",15));
        }
        confirmPassword.setFill(Color.valueOf("FFFFFF"));
        confirmPassword.setLayoutX(40);
        confirmPassword.setLayoutY(225);
        registerPane.getChildren().add(confirmPassword);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setLayoutX(190);
        confirmPasswordField.setLayoutY(200);
        confirmPasswordField.setPrefHeight(40);
        confirmPasswordField.setPrefWidth(250);
        registerPane.getChildren().add(confirmPasswordField);

        Text avatar = new Text("Avatar:");
        try {
            avatar.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            avatar.setFont(Font.font("Verdana",15));
        }
        avatar.setFill(Color.valueOf("FFFFFF"));
        avatar.setLayoutX(220);
        avatar.setLayoutY(280);
        registerPane.getChildren().add(avatar);

        SignButton openButton = new SignButton("Select");
        openButton.setLayoutY(460);
        openButton.setLayoutX(180);
        registerPane.getChildren().add(openButton);

        ImageView imageView = new ImageView();
        imageView.setLayoutY(298);
        imageView.setLayoutX(180);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setStyle("-fx-border-color: white");
        registerPane.getChildren().add(imageView);

        final FileChooser fileChooser = new FileChooser();

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) throws NullPointerException {
                try {
                    File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
                    length = Math.toIntExact(file.length());
                    fis = new FileInputStream(file);
                    if (file != null) {
                        Image image1 = new Image(file.toURI().toString());
                        ImageView ip = new ImageView(image1);
                        imageView.setImage(ip.getImage());
                    }
                } catch (NullPointerException | FileNotFoundException noFileSelected) {
                    File file = new File("Default.png");
                    length = Math.toIntExact(file.length());
                    try {
                        fis = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image image1 = new Image(file.toURI().toString());
                    ImageView ip = new ImageView(image1);
                    imageView.setImage(ip.getImage());
                }
            }
        });

        MainButton registerButton = new MainButton("Register");
        registerButton.setLayoutX(130);
        registerButton.setLayoutY(520);
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int sem = 0;
                if (DbConnection.selectAllLogins().contains(usernameField.getText())){
                    JOptionPane.showMessageDialog(null, "Login already taken :*");
                    sem = 1;
                }
                else if(sem == 0 && usernameField.getText().length() < 20 && usernameField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false && confirmPasswordField.getText().isEmpty() == false && (confirmPasswordField.getText().equals(passwordField.getText()))) {
                    DbConnection.insert(usernameField.getText(), passwordField.getText(), fis, length);
                    login = usernameField.getText();
                    mainPane.getChildren().remove(registerPane);
                    for(int i=0; i<menuButtons.size(); i++){
                        menuButtons.get(i).setDisable(false);
                    }
                    for (int i=0; i<signButtons.size(); i++){
                        signButtons.get(i).setDisable(false);
                    }
                } else if (usernameField.getText().length()  > 20){
                    JOptionPane.showMessageDialog(null, "Login length must be less than 20 characters");
                }else {
                    JOptionPane.showMessageDialog(null, "Fill all fields!!");
                }
            }
        });
        registerPane.getChildren().add(registerButton);

        mainPane.getChildren().add(registerPane);
    }

    private void createCloseLoginButton(){
        CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, loginPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        loginPane.getChildren().add(closeButton);
    }

    private void createCloseRegisterButton(){
        CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, registerPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        registerPane.getChildren().add(closeButton);
    }

    private void createLogOutButton(){
        mainLogOutButton = new SignButton("Log out");
        mainLogOutButton.setLayoutX(20);
        mainLogOutButton.setLayoutY(100);
        userPane.getChildren().add(mainLogOutButton);
        mainLogOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isLoggedIn = false;
                updateUserPanel();
            }
        });
    }

    private void createGameStartButton(){
        MainButton gameStartButton = new MainButton("Start");
        gameStartButton.setLayoutX(130);
        gameStartButton.setLayoutY(520);
        startPane.getChildren().add(gameStartButton);
        gameStartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int players = Integer.parseInt(playerToggle.getSelectedToggle().getUserData().toString());
                    int decks = Integer.parseInt(group.getSelectedToggle().getUserData().toString());
                    GameViewManager gameViewManager = new GameViewManager(isSoundOn, player.isMute(), decks,players);
                    Stage primaryStage = gameViewManager.getGameStage();
                    primaryStage.show();
                    mainStage.close();
                    player.dispose();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}