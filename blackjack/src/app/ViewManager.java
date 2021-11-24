package app;

import app.classes.*;
import app.database.DbConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private AnchorPane mainPane;
    private AnchorPane startPane;
    private AnchorPane helpPane;
    private AnchorPane loginPane;
    private AnchorPane registerPane;
    private AnchorPane userPane;
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
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private final static int MENU_BUTTONS_START_X = 50;
    private final static int MENU_BUTTONS_START_Y = 250;
    private final String FONT_PATH = "src/app/fonts/Righteous.ttf";
    private boolean isLoggedIn = false;

    private String login;
    static FileInputStream fis; // do avatara
    static int length; // do avatara

    List<MainButton> menuButtons;
    List<SignButton> signButtons;
    MediaPlayer player;
    public static boolean isSoundOn = true;

    public ViewManager(){
        menuButtons = new ArrayList<>();
        signButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        startPane = new AnchorPane();
        helpPane = new AnchorPane();
        loginPane = new AnchorPane();
        registerPane = new AnchorPane();
        userPane = new AnchorPane();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        userAvatar = new ImageView();
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
                createStartMenu();
                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(true);
                }
                for (int i=0; i<signButtons.size(); i++){
                    signButtons.get(i).setDisable(true);
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
                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(true);
                }
                for (int i=0; i<signButtons.size(); i++){
                    signButtons.get(i).setDisable(true);
                }
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

    private void createLoginPanel(){
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
                String login = DbConnection.Login(usernameField.getText(), passwordField.getText());
                if (login != "0"){
                    isLoggedIn = true;
                }else{
                    isLoggedIn = false;
                }
                System.out.println(isLoggedIn);
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
            userPane.getChildren().add(userAvatar);


            mainLogOutButton.setVisible(true);
        }else{
            mainLoginButton.setVisible(true);
            mainRegisterButton.setVisible(true);
            noUserDetected.setVisible(true);
            userDetected.setVisible(false);

            mainLogOutButton.setVisible(false);
            userAvatar.setVisible(false);

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
                if(usernameField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false && confirmPasswordField.getText().isEmpty() == false && (confirmPasswordField.getText().equals(passwordField.getText()))) {
                    DbConnection.insert(usernameField.getText(), passwordField.getText(), fis, length);
                    login = usernameField.getText();
                    mainPane.getChildren().remove(registerPane);
                    for(int i=0; i<menuButtons.size(); i++){
                        menuButtons.get(i).setDisable(false);
                    }
                    for (int i=0; i<signButtons.size(); i++){
                        signButtons.get(i).setDisable(false);
                    }
                } else {
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

}
