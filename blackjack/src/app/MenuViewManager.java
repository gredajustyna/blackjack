package app;

import app.classes.*;
import app.database.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.CellSkinBase;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.*;

public class MenuViewManager {
    private AnchorPane mainPane;
    private AnchorPane startPane;
    private AnchorPane helpPane;
    private AnchorPane loginPane;
    private AnchorPane registerPane;
    private AnchorPane userPane;
    private AnchorPane statsPane;
    private AnchorPane scoreboardPane;
    private AnchorPane showLoginsPane2;
    private AnchorPane showLoginsPane3;
    private AnchorPane showLoginsPane4;
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
    private Text matchesText;
    private Text cardsUsedText;
    private Text timePlayedText;
    private Text winPercentage;
    public static Text userPanelWins;
    private Text matchesBotText;
    private Text cardsUsedBotText;
    private Text timePlayedBotText;
    private Text winPercentageBot;
    private Text botText;
    private Text twoCardsText;
    private Text threeCardsText;
    private Text fourCardsText;
    private Text fiveCardsText;
    private Text sixCardsText;
    private Text sevenCardsText;
    private Text eightCardsText;
    private Text nineCardsText;
    private Text tenCardsText;
    private Text waletCardsText;
    private Text queenCardsText;
    private Text kingCardsText;
    private Text aceCardsText;
    private Text cardsText;
    private Text player2log;
    private Text player3log;
    private Text player4log;
    private Scene mainScene;
    private Stage mainStage;
    private static Stage mainStage2;
    private ToggleGroup playerToggle;
    private ToggleGroup group;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private final static int MENU_BUTTONS_START_X = 50;
    private final static int MENU_BUTTONS_START_Y = 250;
    private final String FONT_PATH = "src/app/fonts/Righteous.ttf";
    private boolean isLoggedIn = false;
    private MainButton gameStartButton;
    private CloseButton closeLoginButton;

    //radiobuttons

    private RadioButton buttonPlayer1 = new RadioButton("");
    private RadioButton buttonPlayer2 = new RadioButton("");
    private RadioButton buttonPlayer3 = new RadioButton("");
    private RadioButton buttonPlayer4 = new RadioButton("");
    private  RadioButton buttonComputer1 = new RadioButton("");
    private RadioButton buttonComputer2 = new RadioButton("");
    private RadioButton buttonComputer3 = new RadioButton("");
    private RadioButton buttonComputer4 = new RadioButton("");

    private ComboBox levelComboBox1;
    private ComboBox levelComboBox2;
    private ComboBox levelComboBox3;
    private ComboBox levelComboBox4;

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
        scoreboardPane = new AnchorPane();
        statsPane = new AnchorPane();
        player1Pane = new AnchorPane();
        player2Pane = new AnchorPane();
        player3Pane = new AnchorPane();
        player4Pane = new AnchorPane();
        showLoginsPane2 = new AnchorPane();
        showLoginsPane3 = new AnchorPane();
        showLoginsPane4 = new AnchorPane();
        levelComboBox1 = new ComboBox();



        userPanelWins = new Text("Win percentage: " + 100 * 0 + "%");


        levelComboBox1.getItems().addAll(
                "Easy",
                "Medium",
                "Hard"
        );
        levelComboBox1.setValue("Easy");
        levelComboBox2 = new ComboBox();
        levelComboBox2.getItems().addAll(
                "Easy",
                "Medium",
                "Hard"
        );
        levelComboBox2.setValue("Easy");
        levelComboBox3 = new ComboBox();
        levelComboBox3.getItems().addAll(
                "Easy",
                "Medium",
                "Hard"
        );
        levelComboBox3.setValue("Easy");
        levelComboBox4 = new ComboBox();
        levelComboBox4.getItems().addAll(
                "Easy",
                "Medium",
                "Hard"
        );
        levelComboBox4.setValue("Easy");

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
        createLogo();


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


                    player1Pane.getChildren().clear();
                    player2Pane.getChildren().clear();
                    player3Pane.getChildren().clear();
                    player4Pane.getChildren().clear();
                    startPane.getChildren().clear();
                    createGameStartButton();
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

    private void createscoreboardPane() {
        scoreboardPane.setPrefHeight(600);
        scoreboardPane.setPrefWidth(500);
        scoreboardPane.setLayoutY(150);
        scoreboardPane.setLayoutX(350);
        scoreboardPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseScoreboardButton();
        mainPane.getChildren().add(scoreboardPane);


        Text statsGreetText = new Text("Top ten players!");
        try {
            statsGreetText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            statsGreetText.setFont(Font.font("Verdana", 30));
        }
        statsGreetText.setFill(Color.valueOf("FFFFFF"));
        statsGreetText.setLayoutX(50);
        statsGreetText.setLayoutY(50);
        scoreboardPane.getChildren().add(statsGreetText);


        List<String> users = new ArrayList<>();
        users = DbConnection.selectAllLogins();
        List<Integer> winData = new ArrayList<>();


        int[] data = new int[4];


        for (int i =0; i < users.size(); i++){
            data = DbConnection.getData(users.get(i));
            if(data[0] == 0) winData.add(0);
            else winData.add(100*data[1]/data[0]);
        }

        Map<String, Integer> map = new HashMap<>();

        for (int i =0; i < users.size(); i++){
            map.put(users.get(i) ,winData.get(i));

        }


        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());



        int size = users.size();




        Text player1 = new Text("1. ");
        if(size >= 1){
            String string = String.valueOf(list.get(size-1));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player1 = new Text("1. " + part1 + " - " + part2 + "% win");
        }
        try {
            player1.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player1.setFont(Font.font("Verdana", 30));
        }
        player1.setFill(Color.valueOf("FFFFFF"));
        player1.setLayoutX(50);
        player1.setLayoutY(100);
        scoreboardPane.getChildren().add(player1);

        Text player2 = new Text("2. ");
        if(size >= 2){
            String string = String.valueOf(list.get(size-2));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player2 = new Text("2. " + part1 + " - " + part2 + "% win");
        }
        try {
            player2.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player2.setFont(Font.font("Verdana", 30));
        }
        player2.setFill(Color.valueOf("FFFFFF"));
        player2.setLayoutX(50);
        player2.setLayoutY(150);
        scoreboardPane.getChildren().add(player2);

        Text player3 = new Text("3. ");
        if(size >= 3){
            String string = String.valueOf(list.get(size-3));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player3 = new Text("3. " + part1 + " - " + part2 + "% win");
        }
        try {
            player3.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player3.setFont(Font.font("Verdana", 30));
        }
        player3.setFill(Color.valueOf("FFFFFF"));
        player3.setLayoutX(50);
        player3.setLayoutY(200);
        scoreboardPane.getChildren().add(player3);

        Text player4 = new Text("4. ");
        if(size >= 4){
            String string = String.valueOf(list.get(size-4));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player4 = new Text("4. " + part1 + " - " + part2 + "% win");
        }
        try {
            player4.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player4.setFont(Font.font("Verdana", 30));
        }
        player4.setFill(Color.valueOf("FFFFFF"));
        player4.setLayoutX(50);
        player4.setLayoutY(250);
        scoreboardPane.getChildren().add(player4);

        Text player5 = new Text("5. ");
        if(size >= 5){
            String string = String.valueOf(list.get(size-5));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player5 = new Text("5. " + part1 + " - " + part2 + "% win");
        }
        try {
            player5.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player5.setFont(Font.font("Verdana", 30));
        }
        player5.setFill(Color.valueOf("FFFFFF"));
        player5.setLayoutX(50);
        player5.setLayoutY(300);
        scoreboardPane.getChildren().add(player5);

        Text player6 = new Text("6. ");
        if(size >= 6 ){
            String string = String.valueOf(list.get(size-6));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player6 = new Text("6. " + part1 + " - " + part2 + "% win");
        }
        try {
            player6.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player6.setFont(Font.font("Verdana", 30));
        }
        player6.setFill(Color.valueOf("FFFFFF"));
        player6.setLayoutX(50);
        player6.setLayoutY(350);
        scoreboardPane.getChildren().add(player6);

        Text player7 = new Text("7. ");
        if(size >= 7 ){
            String string = String.valueOf(list.get(size-7));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player7 = new Text("7. " + part1 + " - " + part2 + "% win");
        }
        try {
            player7.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player7.setFont(Font.font("Verdana", 30));
        }
        player7.setFill(Color.valueOf("FFFFFF"));
        player7.setLayoutX(50);
        player7.setLayoutY(400);
        scoreboardPane.getChildren().add(player7);

        Text player8 = new Text("8. ");
        if(size >= 8 ){
            String string = String.valueOf(list.get(size-8));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player8 = new Text("8. " + part1 + " - " + part2 + "% win");
        }
        try {
            player8.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player8.setFont(Font.font("Verdana", 30));
        }
        player8.setFill(Color.valueOf("FFFFFF"));
        player8.setLayoutX(50);
        player8.setLayoutY(450);
        scoreboardPane.getChildren().add(player8);

        Text player9 = new Text("9. ");
        if(size >= 9 ){
            String string = String.valueOf(list.get(size-9));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player9 = new Text("9. " + part1 + " - " + part2 + "% win");
        }
        try {
            player9.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player9.setFont(Font.font("Verdana", 30));
        }
        player9.setFill(Color.valueOf("FFFFFF"));
        player9.setLayoutX(50);
        player9.setLayoutY(500);
        scoreboardPane.getChildren().add(player9);

        Text player10 = new Text("10. ");
        if(size == 10 ){
            String string = String.valueOf(list.get(size-10));
            String[] parts = string.split("=");
            String part1 = parts[0];
            String part2 = parts[1];
            player10 = new Text("10. " + part1 + " - " + part2 + "% win");
        }
        try {
            player10.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            player10.setFont(Font.font("Verdana", 30));
        }
        player10.setFill(Color.valueOf("FFFFFF"));
        player10.setLayoutX(50);
        player10.setLayoutY(550);
        scoreboardPane.getChildren().add(player10);


    }

    private void createStatsPane(){
        statsPane.setPrefHeight(600);
        statsPane.setPrefWidth(500);
        statsPane.setLayoutY(150);
        statsPane.setLayoutX(350);
        statsPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        createCloseStatsButton();
        mainPane.getChildren().add(statsPane);

        Text statsGreetText = new Text("Stats");
        try {
            statsGreetText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            statsGreetText.setFont(Font.font("Verdana",30));
        }
        statsGreetText.setFill(Color.valueOf("FFFFFF"));
        statsGreetText.setLayoutX(200);
        statsGreetText.setLayoutY(50);
        statsPane.getChildren().add(statsGreetText);

        ImageView imageView = new ImageView();
        imageView.setLayoutY(100);
        imageView.setLayoutX(180);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setStyle("-fx-border-color: white");
        registerPane.getChildren().add(imageView);
        imageView.setImage(userAvatar.getImage());
        statsPane.getChildren().add(imageView);

        Text loginText = new Text(user1.getLogin());
        try {
            loginText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            loginText.setFont(Font.font("Verdana",30));
        }
        loginText.setFill(Color.valueOf("FFFFFF"));
        loginText.setLayoutX(200);
        loginText.setLayoutY(300);

        statsPane.getChildren().add(loginText);



        int[] data = new int[4];
        data = DbConnection.getData(user1.getLogin());
        matchesText = new Text("Matches played: " + data[0]);
        try {
            matchesText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            matchesText.setFont(Font.font("Verdana",30));
        }
        matchesText.setFill(Color.valueOf("FFFFFF"));
        matchesText.setLayoutX(50);
        matchesText.setLayoutY(350);

        statsPane.getChildren().add(matchesText);

        cardsUsedText = new Text("Cards used: " + data[2]);
        try {
            cardsUsedText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            cardsUsedText.setFont(Font.font("Verdana",30));
        }
        cardsUsedText.setFill(Color.valueOf("FFFFFF"));
        cardsUsedText.setLayoutX(50);
        cardsUsedText.setLayoutY(400);

        statsPane.getChildren().add(cardsUsedText);

        timePlayedText = new Text("Time played: " + data[3]/60 + " min");
        try {
            timePlayedText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            timePlayedText.setFont(Font.font("Verdana",30));
        }
        timePlayedText.setFill(Color.valueOf("FFFFFF"));
        timePlayedText.setLayoutX(50);
        timePlayedText.setLayoutY(450);

        statsPane.getChildren().add(timePlayedText);

        if(data[0]==0){
            winPercentage = new Text("Win percentage: " + 100 * 0 + "%");
        }else{
            winPercentage = new Text("Win percentage: " + 100 * data[1]/data[0] + "%");
        }

        try {
            winPercentage.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            winPercentage.setFont(Font.font("Verdana",30));
        }
        winPercentage.setFill(Color.valueOf("FFFFFF"));
        winPercentage.setLayoutX(50);
        winPercentage.setLayoutY(500);

        statsPane.getChildren().add(winPercentage);


        botText = new Text("Bot Stats : easy/medium/hard");
        try {
            botText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            botText.setFont(Font.font("Verdana",30));
        }
        botText.setFill(Color.valueOf("FFFFFF"));
        botText.setLayoutX(20);
        botText.setLayoutY(100);



        int[] easyBotData = new int[4];
        easyBotData = DbConnection.getData("easybot");
        int[] mediumBotData = new int[4];
        mediumBotData = DbConnection.getData("mediumbot");
        int[] hardBotData = new int[4];
        hardBotData = DbConnection.getData("hardbot");
        matchesBotText = new Text("Matches played: " + easyBotData[0] + "/" + mediumBotData[0] + "/" + hardBotData[0]);
        try {
            matchesBotText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            matchesBotText.setFont(Font.font("Verdana",30));
        }
        matchesBotText.setFill(Color.valueOf("FFFFFF"));
        matchesBotText.setLayoutX(50);
        matchesBotText.setLayoutY(200);



        cardsUsedBotText = new Text("Cards used: " + easyBotData[2] + "/" + mediumBotData[2] + "/" + hardBotData[2]);
        try {
            cardsUsedBotText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            cardsUsedBotText.setFont(Font.font("Verdana",30));
        }
        cardsUsedBotText.setFill(Color.valueOf("FFFFFF"));
        cardsUsedBotText.setLayoutX(50);
        cardsUsedBotText.setLayoutY(300);



        timePlayedBotText = new Text("Time played: " + easyBotData[3]/60 + "/" + mediumBotData[3]/60 + "/" + hardBotData[3]/60 +" min");
        try {
            timePlayedBotText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            timePlayedBotText.setFont(Font.font("Verdana",30));
        }
        timePlayedBotText.setFill(Color.valueOf("FFFFFF"));
        timePlayedBotText.setLayoutX(50);
        timePlayedBotText.setLayoutY(400);



        if(easyBotData[0]==0 && mediumBotData[0] ==0 && hardBotData[0] == 0){
            winPercentageBot = new Text("Win percentage: 0/0/0 %");
        }else if (easyBotData[0]==0 && mediumBotData[0] ==0){
            winPercentageBot = new Text("Win percentage: 0/0/" + 100* hardBotData[1]/hardBotData[0] + " %");
        }
        else if (easyBotData[0]==0 && hardBotData[0] == 0){
            winPercentageBot = new Text("Win percentage: 0/" + 100* mediumBotData[1]/mediumBotData[0] + "/0 %");
        }else if (mediumBotData[0] ==0 && hardBotData[0] == 0){
            winPercentageBot = new Text("Win percentage: " + 100* easyBotData[1]/easyBotData[0] + "0/0 %");
        }else winPercentageBot = new Text("Win percentage: " + 100* easyBotData[1]/easyBotData[0] + "/" + 100* mediumBotData[1]/mediumBotData[0] +"/" +100* hardBotData[1]/hardBotData[0] + " %");

        try {
            winPercentageBot.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            winPercentageBot.setFont(Font.font("Verdana",30));
        }
        winPercentageBot.setFill(Color.valueOf("FFFFFF"));
        winPercentageBot.setLayoutX(50);
        winPercentageBot.setLayoutY(500);



        cardsText = new Text("Cards Stats");
        try {
            cardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            cardsText.setFont(Font.font("Verdana",30));
        }
        cardsText.setFill(Color.valueOf("FFFFFF"));
        cardsText.setLayoutX(40);
        cardsText.setLayoutY(50);

        int[] cardsData = new int[13];
        cardsData = DbConnection.getCards();


        twoCardsText = new Text("Cards two: " + cardsData[1]);
        try {
            twoCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            twoCardsText.setFont(Font.font("Verdana",24));
        }
        twoCardsText.setFill(Color.valueOf("FFFFFF"));
        twoCardsText.setLayoutX(50);
        twoCardsText.setLayoutY(87);

        threeCardsText = new Text("Cards three: " + cardsData[2]);
        try {
            threeCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            threeCardsText.setFont(Font.font("Verdana",24));
        }
        threeCardsText.setFill(Color.valueOf("FFFFFF"));
        threeCardsText.setLayoutX(50);
        threeCardsText.setLayoutY(125);

        fourCardsText = new Text("Cards four: " + cardsData[3]);
        try {
            fourCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            fourCardsText.setFont(Font.font("Verdana",24));
        }
        fourCardsText.setFill(Color.valueOf("FFFFFF"));
        fourCardsText.setLayoutX(50);
        fourCardsText.setLayoutY(162);

        fiveCardsText = new Text("Cards five: " + cardsData[4]);
        try {
            fiveCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            fiveCardsText.setFont(Font.font("Verdana",24));
        }
        fiveCardsText.setFill(Color.valueOf("FFFFFF"));
        fiveCardsText.setLayoutX(50);
        fiveCardsText.setLayoutY(200);

        sixCardsText = new Text("Cards six: " + cardsData[5]);
        try {
            sixCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            sixCardsText.setFont(Font.font("Verdana",24));
        }
        sixCardsText.setFill(Color.valueOf("FFFFFF"));
        sixCardsText.setLayoutX(50);
        sixCardsText.setLayoutY(237);

        sevenCardsText = new Text("Cards seven: " + cardsData[6]);
        try {
            sevenCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            sevenCardsText.setFont(Font.font("Verdana",24));
        }
        sevenCardsText.setFill(Color.valueOf("FFFFFF"));
        sevenCardsText.setLayoutX(50);
        sevenCardsText.setLayoutY(275);

        eightCardsText = new Text("Cards eight: " + cardsData[7]);
        try {
            eightCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            eightCardsText.setFont(Font.font("Verdana",24));
        }
        eightCardsText.setFill(Color.valueOf("FFFFFF"));
        eightCardsText.setLayoutX(50);
        eightCardsText.setLayoutY(312);

        nineCardsText = new Text("Cards nine: " + cardsData[8]);
        try {
            nineCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            nineCardsText.setFont(Font.font("Verdana",24));
        }
        nineCardsText.setFill(Color.valueOf("FFFFFF"));
        nineCardsText.setLayoutX(50);
        nineCardsText.setLayoutY(350);

        tenCardsText = new Text("Cards ten: " + cardsData[9]);
        try {
            tenCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            tenCardsText.setFont(Font.font("Verdana",24));
        }
        tenCardsText.setFill(Color.valueOf("FFFFFF"));
        tenCardsText.setLayoutX(50);
        tenCardsText.setLayoutY(387);

        waletCardsText = new Text("Cards jack: " + cardsData[10]);
        try {
            waletCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            waletCardsText.setFont(Font.font("Verdana",24));
        }
        waletCardsText.setFill(Color.valueOf("FFFFFF"));
        waletCardsText.setLayoutX(50);
        waletCardsText.setLayoutY(425);


        queenCardsText = new Text("Cards queen: " + cardsData[11]);
        try {
            queenCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            queenCardsText.setFont(Font.font("Verdana",24));
        }
        queenCardsText.setFill(Color.valueOf("FFFFFF"));
        queenCardsText.setLayoutX(50);
        queenCardsText.setLayoutY(462);

        kingCardsText = new Text("Cards king: " + cardsData[12]);
        try {
            kingCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            kingCardsText.setFont(Font.font("Verdana",24));
        }
        kingCardsText.setFill(Color.valueOf("FFFFFF"));
        kingCardsText.setLayoutX(50);
        kingCardsText.setLayoutY(495);

        aceCardsText = new Text("Cards ace: " + cardsData[0]);
        try {
            aceCardsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 24));
        }catch (FileNotFoundException e){
            aceCardsText.setFont(Font.font("Verdana",24));
        }
        aceCardsText.setFill(Color.valueOf("FFFFFF"));
        aceCardsText.setLayoutX(50);
        aceCardsText.setLayoutY(528);




        MainButton botStats = new MainButton("Bot Stats");
        botStats.setLayoutX(120);
        botStats.setLayoutY(530);
        MainButton cardStats = new MainButton("Card Stats");
        cardStats.setLayoutX(120);
        cardStats.setLayoutY(530);
        MainButton userStats = new MainButton("User Stats");
        userStats.setLayoutX(120);
        userStats.setLayoutY(530);
        botStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsPane.getChildren().remove(statsGreetText);
                statsPane.getChildren().remove(imageView);
                statsPane.getChildren().remove(loginText);
                statsPane.getChildren().remove(matchesText);
                statsPane.getChildren().remove(cardsUsedText);
                statsPane.getChildren().remove(timePlayedText);
                statsPane.getChildren().remove(winPercentage);
                statsPane.getChildren().add(botText);
                statsPane.getChildren().add(matchesBotText);
                statsPane.getChildren().add(cardsUsedBotText);
                statsPane.getChildren().add(timePlayedBotText);
                statsPane.getChildren().add(winPercentageBot);
                statsPane.getChildren().add(cardStats);
                statsPane.getChildren().remove(botStats);
            }
        });

        statsPane.getChildren().add(botStats);


        cardStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsPane.getChildren().remove(botText);
                statsPane.getChildren().remove(matchesBotText);
                statsPane.getChildren().remove(cardsUsedBotText);
                statsPane.getChildren().remove(timePlayedBotText);
                statsPane.getChildren().remove(winPercentageBot);
                statsPane.getChildren().add(cardsText);
                statsPane.getChildren().add(twoCardsText);
                statsPane.getChildren().add(threeCardsText);
                statsPane.getChildren().add(fourCardsText);
                statsPane.getChildren().add(fiveCardsText);
                statsPane.getChildren().add(sixCardsText);
                statsPane.getChildren().add(sevenCardsText);
                statsPane.getChildren().add(eightCardsText);
                statsPane.getChildren().add(nineCardsText);
                statsPane.getChildren().add(tenCardsText);
                statsPane.getChildren().add(waletCardsText);
                statsPane.getChildren().add(queenCardsText);
                statsPane.getChildren().add(kingCardsText);
                statsPane.getChildren().add(aceCardsText);
                statsPane.getChildren().add(userStats);
                statsPane.getChildren().remove(cardStats);

            }
        });


        userStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsPane.getChildren().remove(cardsText);
                statsPane.getChildren().remove(twoCardsText);
                statsPane.getChildren().remove(threeCardsText);
                statsPane.getChildren().remove(fourCardsText);
                statsPane.getChildren().remove(fiveCardsText);
                statsPane.getChildren().remove(sixCardsText);
                statsPane.getChildren().remove(sevenCardsText);
                statsPane.getChildren().remove(eightCardsText);
                statsPane.getChildren().remove(nineCardsText);
                statsPane.getChildren().remove(tenCardsText);
                statsPane.getChildren().remove(waletCardsText);
                statsPane.getChildren().remove(queenCardsText);
                statsPane.getChildren().remove(kingCardsText);
                statsPane.getChildren().remove(aceCardsText);
                statsPane.getChildren().add(statsGreetText);
                statsPane.getChildren().add(imageView);
                statsPane.getChildren().add(loginText);
                statsPane.getChildren().add(matchesText);
                statsPane.getChildren().add(cardsUsedText);
                statsPane.getChildren().add(timePlayedText);
                statsPane.getChildren().add(winPercentage);
                statsPane.getChildren().add(botStats);
                statsPane.getChildren().remove(userStats);

            }
        });







    }

    private void createStatsButton(){
        MainButton statsButton = new MainButton("Stats");
        addMenuButton(statsButton);
        statsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isLoggedIn){
                    createStatsPane();
                    for(int i=0; i<menuButtons.size(); i++){
                        menuButtons.get(i).setDisable(true);
                    }
                    for (int i=0; i<signButtons.size(); i++){
                        signButtons.get(i).setDisable(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Log in to view stats!");
                }

            }
        });
    }

    private void createScoreboardButton(){
        MainButton scoreboardButton = new MainButton("Scoreboard");
        addMenuButton(scoreboardButton);
        scoreboardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isLoggedIn){
                    createscoreboardPane();
                    for(int i=0; i<menuButtons.size(); i++){
                        menuButtons.get(i).setDisable(true);
                    }
                    for (int i=0; i<signButtons.size(); i++){
                        signButtons.get(i).setDisable(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Log in to view scoreboard!");
                }

            }
        });
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

    public static Stage getMainStage2(){
        return mainStage2;
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
        Text logo = new Text("Blackjack");
        try {
            logo.setFont(javafx.scene.text.Font.loadFont(new FileInputStream("src/app/fonts/Casino.ttf"), 120));
        } catch (FileNotFoundException e) {
            logo.setFont(Font.font("Verdana",30));
        }
        logo.setFill(Color.valueOf("FFFFFF"));
        logo.setLayoutX(160);
        logo.setLayoutY(130);
        logo.setEffect(new DropShadow());
        mainPane.getChildren().add(logo);
    }

    private void createUserPanel(){
        createLogOutButton();
        signButtons.add(mainLogOutButton);
        mainLogOutButton.setVisible(false);
        userPanelWins.setVisible(false);
        userPane.getChildren().add(userPanelWins);


        try {
            userPanelWins.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            userPanelWins.setFont(Font.font("Verdana",15));
        }
        userPanelWins.setFill(Color.valueOf("FFFFFF"));
        userPanelWins.setLayoutX(40);
        userPanelWins.setLayoutY(75);

        userDetected = new Text();
        try {
            userDetected.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            userDetected.setFont(Font.font("Verdana", 20));
        }
        userDetected.setFill(Color.valueOf("FFFFFF"));
        userDetected.setLayoutX(70);
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
        player4Pane.setVisible(false);
        player3Pane.setVisible(false);
        player2Pane.setVisible(false);
        levelComboBox1.setVisible(false);
        levelComboBox2.setVisible(true);
        levelComboBox3.setVisible(true);
        levelComboBox4.setVisible(true);
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
        selectCards.setLayoutY(110);
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
        button1.setLayoutY(0);


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
        button2.setLayoutY(0);

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
        button3.setLayoutY(0);


        RadioButton button4 = new RadioButton("random");
        button4.setToggleGroup(group);
        button4.setSelected(false);
        button4.setUserData(4);
        button4.setLayoutX(350);
        button4.setLayoutY(15);

        decksPane.getChildren().add(button1);
        decksPane.getChildren().add(button2);
        decksPane.getChildren().add(button3);
        decksPane.getChildren().add(button4);
        decksPane.setLayoutX(30);
        decksPane.setLayoutY(150);
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
        playersPane.setLayoutY(270);

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
        button00.setLayoutY(0);
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
        button11.setLayoutY(0);
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
        button22.setLayoutY(0);
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
        button33.setLayoutY(0);
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

        levelComboBox1.setLayoutX(300);
        levelComboBox1.setLayoutY(-25);
        player1Pane.getChildren().addAll(levelComboBox1);

        buttonPlayer1.setGraphic(buttonpl1);
        buttonPlayer1.setUserData(0);
        buttonPlayer1.setToggleGroup(player1group);
        buttonPlayer1.setSelected(true);
        buttonPlayer1.setLayoutX(110);
        buttonPlayer1.setLayoutY(-30);
        buttonPlayer1.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox1, (int)buttonPlayer1.getUserData());
        });


        InputStream iscom1 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom1= new Image(iscom1);
        ImageView buttoncom1 = new ImageView(imgcom1);
        buttoncom1.setFitHeight(40);
        buttoncom1.setFitWidth(40);
        buttoncom1.setPreserveRatio(true);
        buttonComputer1.setGraphic(buttoncom1);
        buttonComputer1.setUserData(1);
        buttonComputer1.setToggleGroup(player1group);
        buttonComputer1.setSelected(false);
        buttonComputer1.setLayoutX(200);
        buttonComputer1.setLayoutY(-24);
        buttonComputer1.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox1, (int)buttonComputer1.getUserData());
        });

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

        levelComboBox2.setLayoutX(300);
        levelComboBox2.setLayoutY(-25);
        player2Pane.getChildren().addAll(levelComboBox2);

        ToggleGroup player2group = new ToggleGroup();
        InputStream ispl2 = getClass().getResourceAsStream("/player1.png");
        Image imgpl2= new Image(ispl2);
        ImageView buttonpl2 = new ImageView(imgpl2);
        buttonpl2.setFitHeight(50);
        buttonpl2.setFitWidth(50);
        buttonpl2.setPreserveRatio(true);
        buttonPlayer2.setGraphic(buttonpl2);
        buttonPlayer2.setUserData(0);
        buttonPlayer2.setToggleGroup(player2group);
        buttonPlayer2.setSelected(false);
        buttonPlayer2.setLayoutX(110);
        buttonPlayer2.setLayoutY(-30);
        buttonPlayer2.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox2, (int)buttonPlayer2.getUserData());
        });

        InputStream iscom2 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom2= new Image(iscom2);
        ImageView buttoncom2 = new ImageView(imgcom2);
        buttoncom2.setFitHeight(40);
        buttoncom2.setFitWidth(40);
        buttoncom2.setPreserveRatio(true);
        buttonComputer2.setGraphic(buttoncom2);
        buttonComputer2.setUserData(1);
        buttonComputer2.setToggleGroup(player2group);
        buttonComputer2.setSelected(true);
        buttonComputer2.setLayoutX(200);
        buttonComputer2.setLayoutY(-24);
        buttonComputer2.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox2, (int)buttonComputer2.getUserData());
        });

        buttonPlayer2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonPlayer2.isSelected()) {
                    gameStartButton.setDisable(true);
                    isLoggedIn2 = true;
                    mainPane.getChildren().add(loginPane);
                    loginPane.getChildren().remove(closeLoginButton);
                }

            }
        });

        buttonComputer2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonComputer2.isSelected()) {
                    user2.setLogin(null);
                    showLoginsPane2.getChildren().clear();
                    mainPane.getChildren().remove(showLoginsPane2);
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

        levelComboBox3.setLayoutX(300);
        levelComboBox3.setLayoutY(-25);
        player3Pane.getChildren().addAll(levelComboBox3);

        ToggleGroup player3group = new ToggleGroup();
        InputStream ispl3 = getClass().getResourceAsStream("/player1.png");
        Image imgpl3= new Image(ispl3);
        ImageView buttonpl3 = new ImageView(imgpl3);
        buttonpl3.setFitHeight(50);
        buttonpl3.setFitWidth(50);
        buttonpl3.setPreserveRatio(true);
        buttonPlayer3.setGraphic(buttonpl3);
        buttonPlayer3.setUserData(0);
        buttonPlayer3.setToggleGroup(player3group);
        buttonPlayer3.setSelected(false);
        buttonPlayer3.setLayoutX(110);
        buttonPlayer3.setLayoutY(-30);
        buttonPlayer3.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox3, (int)buttonPlayer3.getUserData());
        });

        InputStream iscom3 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom3= new Image(iscom3);
        ImageView buttoncom3 = new ImageView(imgcom3);
        buttoncom3.setFitHeight(40);
        buttoncom3.setFitWidth(40);
        buttoncom3.setPreserveRatio(true);
        buttonComputer3.setGraphic(buttoncom3);
        buttonComputer3.setUserData(1);
        buttonComputer3.setToggleGroup(player3group);
        buttonComputer3.setSelected(true);
        buttonComputer3.setLayoutX(200);
        buttonComputer3.setLayoutY(-24);
        buttonComputer3.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox3, (int)buttonComputer3.getUserData());
        });

        buttonPlayer3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonPlayer3.isSelected()) {
                    gameStartButton.setDisable(true);
                    isLoggedIn3 = true;
                    mainPane.getChildren().add(loginPane);
                    loginPane.getChildren().remove(closeLoginButton);
                }
            }
        });

        buttonComputer3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonComputer3.isSelected()) {
                    user3.setLogin(null);
                    showLoginsPane3.getChildren().clear();
                    mainPane.getChildren().remove(showLoginsPane3);
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

        levelComboBox4.setLayoutX(300);
        levelComboBox4.setLayoutY(-25);
        player4Pane.getChildren().addAll(levelComboBox4);

        ToggleGroup player4group = new ToggleGroup();
        InputStream ispl4 = getClass().getResourceAsStream("/player1.png");
        Image imgpl4= new Image(ispl4);
        ImageView buttonpl4 = new ImageView(imgpl4);
        buttonpl4.setFitHeight(50);
        buttonpl4.setFitWidth(50);
        buttonpl4.setPreserveRatio(true);
        buttonPlayer4.setGraphic(buttonpl4);
        buttonPlayer4.setUserData(0);
        buttonPlayer4.setToggleGroup(player4group);
        buttonPlayer4.setSelected(false);
        buttonPlayer4.setLayoutX(110);
        buttonPlayer4.setLayoutY(-30);
        buttonPlayer4.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox4, (int)buttonPlayer4.getUserData());
        });

        InputStream iscom4 = getClass().getResourceAsStream("/computer1.png");
        Image imgcom4= new Image(iscom4);
        ImageView buttoncom4 = new ImageView(imgcom4);
        buttoncom4.setFitHeight(40);
        buttoncom4.setFitWidth(40);
        buttoncom4.setPreserveRatio(true);
        buttonComputer4.setGraphic(buttoncom4);
        buttonComputer4.setUserData(1);
        buttonComputer4.setToggleGroup(player4group);
        buttonComputer4.setSelected(true);
        buttonComputer4.setLayoutX(200);
        buttonComputer4.setLayoutY(-24);
        buttonComputer4.setOnAction(e ->{
            ToggleButton toggle = (ToggleButton) e.getSource();
            updateLevelVisibility(levelComboBox4, (int)buttonComputer4.getUserData());
        });

        buttonPlayer4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonPlayer4.isSelected()) {
                    gameStartButton.setDisable(true);
                    isLoggedIn4 = true;
                    mainPane.getChildren().add(loginPane);
                    loginPane.getChildren().remove(closeLoginButton);
                }
            }
        });

        buttonComputer4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (buttonComputer4.isSelected()) {
                    user4.setLogin(null);
                    showLoginsPane4.getChildren().clear();
                    mainPane.getChildren().remove(showLoginsPane4);
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

    private void updateLevelVisibility(ComboBox comboBox, int value){
        switch (value){
            case 0:
                comboBox.setVisible(false);
                break;
            case 1:
                comboBox.setVisible(true);
                break;
        }
    }

    private void updatePlayerVisibility(ToggleButton button){
        int result = (int) button.getUserData();
        switch (result){
            case 1:
                player1Pane.setVisible(true);
                player2Pane.setVisible(false);
                player3Pane.setVisible(false);
                player4Pane.setVisible(false);
                break;
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

        Text helpGreetText = new Text("Help");
        try {
            helpGreetText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            helpGreetText.setFont(Font.font("Verdana",30));
        }
        helpGreetText.setFill(Color.valueOf("FFFFFF"));
        helpGreetText.setLayoutX(200);
        helpGreetText.setLayoutY(50);
        helpPane.getChildren().add(helpGreetText);

        Label label1 = new Label(HelpText.getHelpText1());
        try {
            label1.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 12));
        }catch (FileNotFoundException e){
            label1.setFont(Font.font("Verdana",12));
        }
        label1.setTextFill(Color.valueOf("FFFFFF"));
        label1.setMaxWidth(470);
        label1.setLayoutX(20);
        label1.setLayoutY(80);
        label1.setWrapText(true);
        helpPane.getChildren().add(label1);

        Text cardText = new Text("Cards");
        try {
            cardText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            cardText.setFont(Font.font("Verdana",20));
        }
        cardText.setFill(Color.valueOf("FFFFFF"));
        cardText.setLayoutX(20);
        cardText.setLayoutY(170);
        helpPane.getChildren().add(cardText);

        Label label2 = new Label(HelpText.getThePack());
        try {
            label2.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 12));
        }catch (FileNotFoundException e){
            label2.setFont(Font.font("Verdana",12));
        }
        label2.setTextFill(Color.valueOf("FFFFFF"));
        label2.setMaxWidth(470);
        label2.setLayoutX(20);
        label2.setLayoutY(183);
        label2.setWrapText(true);
        helpPane.getChildren().add(label2);

        Text gameObjective = new Text("Objective");
        try {
            gameObjective.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            gameObjective.setFont(Font.font("Verdana",20));
        }
        gameObjective.setFill(Color.valueOf("FFFFFF"));
        gameObjective.setLayoutX(20);
        gameObjective.setLayoutY(240);
        helpPane.getChildren().add(gameObjective);

        Label label3 = new Label(HelpText.getObjectOfTheGame());
        try {
            label3.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 12));
        }catch (FileNotFoundException e){
            label3.setFont(Font.font("Verdana",12));
        }
        label3.setTextFill(Color.valueOf("FFFFFF"));
        label3.setMaxWidth(470);
        label3.setLayoutX(20);
        label3.setLayoutY(253);
        label3.setWrapText(true);
        helpPane.getChildren().add(label3);

        Text cardScoring = new Text("Card Values");
        try {
            cardScoring.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            cardScoring.setFont(Font.font("Verdana",20));
        }
        cardScoring.setFill(Color.valueOf("FFFFFF"));
        cardScoring.setLayoutX(20);
        cardScoring.setLayoutY(310);
        helpPane.getChildren().add(cardScoring);

        Label label4 = new Label(HelpText.getCardValuesScoring());
        try {
            label4.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 12));
        }catch (FileNotFoundException e){
            label4.setFont(Font.font("Verdana",12));
        }
        label4.setTextFill(Color.valueOf("FFFFFF"));
        label4.setMaxWidth(470);
        label4.setLayoutX(20);
        label4.setLayoutY(323);
        label4.setWrapText(true);
        helpPane.getChildren().add(label4);

        Text thePlay = new Text("The Play");
        try {
            thePlay.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            thePlay.setFont(Font.font("Verdana",20));
        }
        thePlay.setFill(Color.valueOf("FFFFFF"));
        thePlay.setLayoutX(20);
        thePlay.setLayoutY(395);
        helpPane.getChildren().add(thePlay);

        Label label5 = new Label(HelpText.getThePlay());
        try {
            label5.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 12));
        }catch (FileNotFoundException e){
            label5.setFont(Font.font("Verdana",12));
        }
        label5.setTextFill(Color.valueOf("FFFFFF"));
        label5.setMaxWidth(470);
        label5.setLayoutX(20);
        label5.setLayoutY(412);
        label5.setWrapText(true);
        helpPane.getChildren().add(label5);

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



                for(int i=0; i<menuButtons.size(); i++){
                    menuButtons.get(i).setDisable(false);
                }


                gameStartButton.setDisable(false);
                login_buff = DbConnection.Login(usernameField.getText(), passwordField.getText());


                if (login_buff != "0"){
                    isLoggedIn = true;
                    mainLogOutButton.setDisable(false);


                }else if (login_buff == "0" && buttonPlayer2.isSelected() && user2.getLogin() == null){
                    turnButtonsOff();
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    buttonPlayer2.setSelected(false);
                    buttonComputer2.setSelected(true);
                }else if (login_buff == "0" && buttonPlayer3.isSelected() && user3.getLogin() == null){
                    turnButtonsOff();
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    buttonPlayer3.setSelected(false);
                    buttonComputer3.setSelected(true);
                }else if (login_buff == "0" && buttonPlayer4.isSelected() && user4.getLogin() == null){
                    turnButtonsOff();
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    buttonPlayer4.setSelected(false);
                    buttonComputer4.setSelected(true);
                } else{
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    isLoggedIn = false;

                }

                if(login_buff != "0" && isLoggedIn2){
                    turnButtonsOff();
                    if(login_buff.equals(user1.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer2.setSelected(false);
                        buttonComputer2.setSelected(true);
                    } else if(login_buff.equals(user3.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer2.setSelected(false);
                        buttonComputer2.setSelected(true);
                    }else if(login_buff.equals(user4.getLogin())) {
                            JOptionPane.showMessageDialog(null, "Account already in use!");
                            buttonPlayer2.setSelected(false);
                            buttonComputer2.setSelected(true);
                    }else {
                        user2.setLogin(login_buff);
                        createShowLoginsPane2();
                        player2log.setText(login_buff);

                    }
                    isLoggedIn2 = false;
                }
                if(login_buff != "0" && isLoggedIn3){
                    turnButtonsOff();
                    if(login_buff.equals(user1.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer3.setSelected(false);
                        buttonComputer3.setSelected(true);
                    } else if(login_buff.equals(user2.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer3.setSelected(false);
                        buttonComputer3.setSelected(true);
                    }else if(login_buff.equals(user4.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer3.setSelected(false);
                        buttonComputer3.setSelected(true);
                    } else {
                        user3.setLogin(login_buff);
                        createShowLoginsPane3();
                        player3log.setText(login_buff);
                    }
                    isLoggedIn3 = false;
                }
                if(login_buff != "0" && isLoggedIn4){
                    turnButtonsOff();
                    if(login_buff.equals(user1.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer4.setSelected(false);
                        buttonComputer4.setSelected(true);
                    } else if(login_buff.equals(user2.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer4.setSelected(false);
                        buttonComputer4.setSelected(true);
                    }else if(login_buff.equals(user3.getLogin())) {
                        JOptionPane.showMessageDialog(null, "Account already in use!");
                        buttonPlayer4.setSelected(false);
                        buttonComputer4.setSelected(true);
                    } else {
                        user4.setLogin(login_buff);

                        createShowLoginsPane4();
                        player4log.setText(login_buff);
                    }
                    isLoggedIn4 = false;
                }
                mainPane.getChildren().remove(loginPane);


                if(login_buff != "0" && user1.getLogin() == null){
                    updateUserPanel();
                }
                usernameField.setText("");
                passwordField.setText("");
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
            userPanelWins.setVisible(true);


            Image image = DbConnection.setAvatar(login_buff);
            userAvatar.setImage(image);
            userAvatar.setLayoutY(30);
            userAvatar.setLayoutX(200);
            userAvatar.setFitWidth(80);
            userAvatar.setFitHeight(80);
            userAvatar.setVisible(true);
            userPane.getChildren().add(userAvatar);
            user1.setLogin(login_buff);



            int[] data = new int[4];
            data = DbConnection.getData(user1.getLogin());

            if(data[0]==0){
                userPanelWins.setText("Win percentage: " + 100 * 0 + "%");

            }else{
                userPanelWins.setText("Win percentage: " + 100 * data[1]/data[0] + "%");

            }
            userDetected.setText(user1.getLogin());



            mainLogOutButton.setVisible(true);
        }else{
            mainLoginButton.setVisible(true);
            mainRegisterButton.setVisible(true);
            noUserDetected.setVisible(true);
            userDetected.setVisible(false);
            userPanelWins.setVisible(false);

            mainLogOutButton.setVisible(false);
            userAvatar.setVisible(false);
            userPane.getChildren().remove(userAvatar);

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
                    JOptionPane.showMessageDialog(null, "Login already taken");
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
        closeLoginButton = new CloseButton(menuButtons, signButtons, mainPane, loginPane);
        closeLoginButton.setLayoutY(10);
        closeLoginButton.setLayoutX(440);
        loginPane.getChildren().add(closeLoginButton);
    }

    private void createCloseRegisterButton(){
        CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, registerPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        registerPane.getChildren().add(closeButton);
    }

    private void createCloseStatsButton(){
        CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, statsPane);
        closeButton.setLayoutY(10);
        closeButton.setLayoutX(440);
        statsPane.getChildren().add(closeButton);
    }

        private void createCloseScoreboardButton(){
            CloseButton closeButton = new CloseButton(menuButtons, signButtons, mainPane, scoreboardPane);
            closeButton.setLayoutY(10);
            closeButton.setLayoutX(440);
            scoreboardPane.getChildren().add(closeButton);
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
                user1.setLogin(null);
                user2.setLogin(null);
                user3.setLogin(null);
                user4.setLogin(null);
                updateUserPanel();
            }
        });
    }

    private void createGameStartButton(){
        gameStartButton = new MainButton("Start");
        gameStartButton.setLayoutX(130);
        gameStartButton.setLayoutY(520);
        startPane.getChildren().add(gameStartButton);
        gameStartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    boolean bot1;
                    int diff1 = 0, diff2 = 0, diff3 = 0, diff4 = 0;
                    int players = Integer.parseInt(playerToggle.getSelectedToggle().getUserData().toString());
                    int decks = Integer.parseInt(group.getSelectedToggle().getUserData().toString());
                    if(buttonComputer1.isSelected()) {
                        bot1 = true;
                        diff1 = returnDiff(levelComboBox1.getSelectionModel().getSelectedItem().toString());

                    } else bot1 = false;
                    if(buttonComputer2.isSelected()) {
                        diff2 = returnDiff(levelComboBox2.getSelectionModel().getSelectedItem().toString());

                    }
                    if(buttonComputer3.isSelected()) {
                        diff3 = returnDiff(levelComboBox3.getSelectionModel().getSelectedItem().toString());

                    }
                    if(buttonComputer4.isSelected()) {
                        diff4 = returnDiff(levelComboBox4.getSelectionModel().getSelectedItem().toString());

                    }

                    GameViewManager gameViewManager = new GameViewManager(isSoundOn, player.isMute(), decks,players,bot1,diff1,diff2,diff3,diff4);
                    Stage primaryStage = gameViewManager.getGameStage();
                    primaryStage.show();
                    mainStage.close();
                    mainStage2= mainStage;
                    player.dispose();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public int returnDiff(String name){
        if (name.equals("Easy")) return 0;
        if (name.equals("Medium")) return 1;
        if (name.equals("Hard")) return 2;
        return 1;
    }

    public void turnButtonsOff(){
        for(int i=0; i<menuButtons.size(); i++){
            menuButtons.get(i).setDisable(true);
        }
        for (int i=0; i<signButtons.size(); i++){
            signButtons.get(i).setDisable(true);
        }
    }

    private void createShowLoginsPane2() {
        showLoginsPane2.setPrefHeight(25);
        showLoginsPane2.setPrefWidth(100);
        showLoginsPane2.setLayoutY(0);
        showLoginsPane2.setLayoutX(1100);
        showLoginsPane2.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

        player2log = new Text("");
        try {
            player2log.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            player2log.setFont(Font.font("Verdana",15));
        }
        player2log.setFill(Color.valueOf("FFFFFF"));
        player2log.setLayoutX(0);
        player2log.setLayoutY(10);
        showLoginsPane2.getChildren().add(player2log);

        mainPane.getChildren().add(showLoginsPane2);

    }


    private void createShowLoginsPane3() {
        showLoginsPane3.setPrefHeight(25);
        showLoginsPane3.setPrefWidth(100);
        showLoginsPane3.setLayoutY(25);
        showLoginsPane3.setLayoutX(1100);
        showLoginsPane3.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

        player3log = new Text("");
        try {
            player3log.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            player3log.setFont(Font.font("Verdana",15));
        }
        player3log.setFill(Color.valueOf("FFFFFF"));
        player3log.setLayoutX(0);
        player3log.setLayoutY(10);
        showLoginsPane3.getChildren().add(player3log);

        mainPane.getChildren().add(showLoginsPane3);

    }

    private void createShowLoginsPane4() {
        showLoginsPane4.setPrefHeight(25);
        showLoginsPane4.setPrefWidth(100);
        showLoginsPane4.setLayoutY(50);
        showLoginsPane4.setLayoutX(1100);
        showLoginsPane4.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

        player4log = new Text("");
        try {
            player4log.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 15));
        }catch (FileNotFoundException e){
            player4log.setFont(Font.font("Verdana",15));
        }
        player4log.setFill(Color.valueOf("FFFFFF"));
        player4log.setLayoutX(0);
        player4log.setLayoutY(10);
        showLoginsPane4.getChildren().add(player4log);

        mainPane.getChildren().add(showLoginsPane4);

    }
}
