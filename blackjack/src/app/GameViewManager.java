package app;

import app.classes.*;
import app.database.DbConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

public class GameViewManager {
    private AnchorPane retryPane;
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
    private Text currentPlayer;
    private int stopTime =0;
    private  Timer timer = new Timer();
    private Timer secondTimer = new Timer();
    private boolean isBot;
    private int dif1;
    private int dif2;
    private int dif3;
    private int dif4;

    StackPane playerCardsPane;
    StackPane dealerCardsPane;

    private Text player1PointsText = new Text("Points: " + player1Points);

    private Text player1Text;
    private Text player2Text;
    private Text player3Text;
    private Text player4Text;
    Text player2PointsText = new Text("Points: " + player2Points);
    Text player3PointsText = new Text("Points: " + player2Points);
    Text player4PointsText = new Text("Points: " + player4Points);
    Text dealerPointsText = new Text("Points: " + dealerPoints);


    private List<Card> player1 = new ArrayList<>();
    private List<Card> player2 = new ArrayList<>();
    private List<Card> player3 = new ArrayList<>();
    private List<Card> player4 = new ArrayList<>();

    private int[] timePlayed = new int[4];


    Text retryText = new Text("The winner is: ");
    private BotPlayer krupier;
    private ArrayList<Boolean> playersList =  new ArrayList<Boolean>();
    private ArrayList<Boolean> playingList =  new ArrayList<Boolean>();
    private ArrayList<BotPlayer> botList =  new ArrayList<BotPlayer>();

    private ArrayList<Boolean> playersTurn =  new ArrayList<Boolean>();
    int playerTurn = 1;
    MediaPlayer player;
    Timeline timeline;
    //Timer timer;

    TextArea log;

    int roundN = 1;

    static Deck deck;



    public Stage getGameStage() {
        return gameStage;
    }



    public GameViewManager(boolean isSoundOn, boolean isMusicOn, int decks, int players, boolean bot1, int diff1, int diff2, int diff3, int diff4){
        isBot = bot1;
        gamePane = new AnchorPane();
        timerPane = new AnchorPane();
        retryPane = new AnchorPane();
        gameScene = new Scene(gamePane,WIDTH,HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setTitle("Blackjack");
        repetitions = 0;
        secondsLeft = 15;
        secondsText = new Text(String.valueOf(secondsLeft));
        minutesText = new Text("0:");
        currentPlayer = new Text("Currently playing: ");
        hitButton = new GameButton("hit", "chip_green.png");
        standButton = new GameButton("stand", "chip_red.png");
        dealerCardsPane = new StackPane();
        dealerCardsPane.setPrefHeight(95);
        dealerCardsPane.setMinWidth(70);
        dealerCardsPane.setLayoutY(250);
        dealerCardsPane.setLayoutX(500);
        gamePane.getChildren().add(dealerCardsPane);
        playerCardsPane = new StackPane();
        playerCardsPane.setLayoutX(500);
        playerCardsPane.setLayoutY(400);
        playerCardsPane.setPrefHeight(95);
        playerCardsPane.setMinWidth(70);
        gamePane.getChildren().add(playerCardsPane);
        createBackground();
        numberOfPlayers = players;
        dif1 = diff1;
        dif2 = diff2;
        dif3 = diff3;
        dif4 = diff4;


        log = new TextArea();
        log.setLayoutX(0);
        log.setLayoutY(0);
        log.setMaxSize(250,250);


        log.appendText("ROUND 1\n ");


        log.setEditable(false);


        log.setVisible(true);
        try {
            log.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 10));
        }catch (FileNotFoundException e){
            log.setFont(Font.font("Verdana",10));
        }



        gamePane.getChildren().add(log);


        currentPlayer.setLayoutY(110);
        currentPlayer.setLayoutX(400);
        gamePane.getChildren().add(currentPlayer);
        try {
            currentPlayer.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            currentPlayer.setFont(Font.font("Verdana",30));
        }
        currentPlayer.setFill(Color.valueOf("FFFFFF"));

        if(decks==4){
            deck = new Deck(ThreadLocalRandom.current().nextInt(1, 3 + 1));

        }else{
            deck = new Deck(decks);
        }

        deck.shuffle();

        krupier = new BotPlayer(1,deck,0);
        createUsersTable();
        createTimer();
        buildHitButton();
        buildStandButton();
        //connecting to the deck class and creating game



        for(int i = 0;i<=playingList.size();i++){
            deck.draw(i);
            deck.draw(i);
        }


        player1Points = deck.score[1];
        player2Points = deck.score[2];
        player3Points = deck.score[3];
        player4Points = deck.score[4];
        updatePoints();
        showDealerCards();
        removeCurrentPlayerCards();
        addCards();
       // showCurrentPlayerCards();
        playRound();

        updateCurrentlyPlaying();
        botPlay(); //jeśli pierwszay gracz botem


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



       /*
       hitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(playersTurn.get(0) == true){
                    deck.draw(1);
                    playersTurn.set(0,false);
                    if (playersList.size() > 1){
                        playersTurn.set(1,true);
                    }
                    player1Points = deck.score[1];

                }
                else if(playersList.size() >= 2 && playersList.get(1) == true && playersTurn.get(1) == true){
                    deck.draw(2);
                    playersTurn.set(1,false);
                    if (playersList.size() > 2){
                        playersTurn.set(2,true);
                    }else playersTurn.set(0,true);
                    player2Points = deck.score[2];
                }
                else if(playersList.size() >= 3 && playersList.get(2) == true && playersTurn.get(2) == true){
                    deck.draw(3);
                    playersTurn.set(2,false);
                    if (playersList.size() > 3){
                        playersTurn.set(3,true);
                    } else playersTurn.set(0,true);
                    player3Points = deck.score[3];
                }
                else if(playersList.size() == 4 && playersList.get(3) == true && playersTurn.get(3) == true){
                    deck.draw(4);
                    playersTurn.set(3,false);
                    playersTurn.set(0,true);
                    player4Points = deck.score[4];
                }
            updatePoints();

            }
        });

        */

        hitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {
                secondsLeft =15;


                //showCurrentPlayerCards();

                switch (playerTurn){
                    case 1:
                        log.appendText("\n"+"Player: "+player1Text.getText()+ " have drawn "+ deck.getDeck().get(0).getName());
                        break;
                    case 2:
                        log.appendText("\n"+"Player: "+player2Text.getText()+ " have drawn "+ deck.getDeck().get(0).getName());
                        break;
                    case 3:
                        log.appendText("\n"+"Player: "+player3Text.getText()+ " have drawn "+ deck.getDeck().get(0).getName());
                        break;
                    case 4:
                        log.appendText("\n"+"Player: "+player4Text.getText()+ " have drawn "+ deck.getDeck().get(0).getName());
                        break;
                }
                deck.draw(playerTurn);
                addCards();

                player1Points = deck.score[1];
                player2Points = deck.score[2];
                player3Points = deck.score[3];
                player4Points = deck.score[4];
                updatePoints();



                if(deck.score[playerTurn]>20){

                    switch (playerTurn){
                        case 1:
                            log.appendText("\n"+"Player: "+player1Text.getText()+ " have passed");
                            break;
                        case 2:
                            log.appendText("\n"+"Player: "+player2Text.getText()+ " have passed");
                            break;
                        case 3:
                            log.appendText("\n"+"Player: "+player3Text.getText()+ " have passed");
                            break;
                        case 4:
                            log.appendText("\n"+"Player: "+player4Text.getText()+ " have passed");
                            break;
                    }

                    playingList.set(playerTurn-1, false);

                    if(playingList.contains(true)){

                        for(int i = playerTurn+1; i < playersList.size()+2; i++){
                            if(i == playersList.size()+1){
                                i=1;
                            }
                            if(playingList.get(i-1)){
                                playerTurn =i;

                                break;
                            }
                        }
                    }else{
                        while(krupier.play()){
                            log.appendText("\n"+"Dealer have drawn "+ deck.krupier.get(deck.krupier.size()-1).getName());
                        };
                        updatePoints();
                        //TODO niech się gra kończy gdy w playinglist nie ma już true


                        timeline.stop();

                        getUsers();
                        createRetryPanel();
                        return;
                    }
                    updateCurrentlyPlaying();
                    addCards();
                    botPlay();
                }


            }
        });

        standButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondsLeft =15;
                playingList.set(playerTurn-1, false);
                addCards();

                switch (playerTurn){
                    case 1:
                        log.appendText("\n"+"Player: "+player1Text.getText()+ " have passed");
                        break;
                    case 2:
                        log.appendText("\n"+"Player: "+player2Text.getText()+ " have passed");
                        break;
                    case 3:
                        log.appendText("\n"+"Player: "+player3Text.getText()+ " have passed");
                        break;
                    case 4:
                        log.appendText("\n"+"Player: "+player4Text.getText()+ " have passed");
                        break;
                }


                if(playingList.contains(true)){

                    for(int i = playerTurn+1; i < playersList.size()+2; i++){
                        if(i == playersList.size()+1){
                            i=1;
                        }
                        if(playingList.get(i-1)){
                            playerTurn =i;
                            break;
                        }
                    }
                }else{
                    while(krupier.play()){
                        log.appendText("\n"+"Dealer have drawn "+ deck.krupier.get(deck.krupier.size()-1).getName());
                    };
                    updatePoints();
                    timeline.stop();
                    getUsers();
                    createRetryPanel();
                    return;
                }
                updateCurrentlyPlaying();
                addCards();
                botPlay();
            }
        });

    }

    private void botPlay(){
        if(botList.get(playerTurn-1)!=null){
            timePlayed[playerTurn-1]++;

            if(botList.get(playerTurn-1).play())
                switch (playerTurn){
                    case 1:
                        log.appendText("\n"+"Player: "+player1Text.getText()+ " have drawn "+ deck.player1.get(0).getName());
                        break;
                    case 2:
                        log.appendText("\n"+"Player: "+player2Text.getText()+ " have drawn "+ deck.player2.get(0).getName());
                        break;
                    case 3:
                        log.appendText("\n"+"Player: "+player3Text.getText()+ " have drawn "+ deck.player3.get(0).getName());
                        break;
                    case 4:
                        log.appendText("\n"+"Player: "+player4Text.getText()+ " have drawn "+ deck.player4.get(0).getName());
                        break;
            }else{
                playingList.set(playerTurn-1,false);
            }


            player1Points = deck.score[1];
            player2Points = deck.score[2];
            player3Points = deck.score[3];
            player4Points = deck.score[4];

            if(deck.score[playerTurn]>20){
                playingList.set(playerTurn-1, false);
            }
            updatePoints();
            if(!playingList.get(playerTurn-1)) {
                switch (playerTurn){
                    case 1:
                        log.appendText("\n"+"Player: "+player1Text.getText()+ " have passed");
                        break;
                    case 2:
                        log.appendText("\n"+"Player: "+player2Text.getText()+ " have passed");
                        break;
                    case 3:
                        log.appendText("\n"+"Player: "+player3Text.getText()+ " have passed");
                        break;
                    case 4:
                        log.appendText("\n"+"Player: "+player4Text.getText()+ " have passed");
                        break;
                }
                if (playingList.contains(true)) {

                    for (int i = playerTurn + 1; i < (playersList.size() + 2); i++) {
                        if (i == playersList.size() + 1) {
                            i = 1;
                        }
                        if (playingList.get(i - 1)) {
                            playerTurn = i;

                            break;
                        }
                    }
                } else {
                    while (krupier.play()){
                        log.appendText("\n"+"Dealer have drawn "+ deck.krupier.get(deck.krupier.size()-1).getName());
                    } ;
                    updatePoints();

                    timeline.stop();
                    getUsers();
                    createRetryPanel();
                    return;


                }
            }
            updateCurrentlyPlaying();
            addCards();
            botPlay();


        }
    }

    private void updateCurrentlyPlaying(){
        switch (playerTurn){
            case 1:
                currentPlayer.setText("Currently playing: " + player1Text.getText());
                break;
            case 2:
                currentPlayer.setText("Currently playing: " + player2Text.getText());
                break;
            case 3:
                currentPlayer.setText("Currently playing: " + player3Text.getText());
                break;
            case 4:
                currentPlayer.setText("Currently playing: " + player4Text.getText());
                break;
        }
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


        try {
            dealerPointsText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        }catch (FileNotFoundException e){
            dealerPointsText.setFont(Font.font("Verdana",20));
        }
        dealerPane.getChildren().add(dealerPointsText);
        dealerPointsText.setFill(Color.valueOf("000000"));
        dealerPointsText.setLayoutX(10);
        dealerPointsText.setLayoutY(75);

        playersList.add(true);
        playersTurn.add(true);
        playingList.add(true);

        player1Text = new Text(MenuViewManager.user1.getLogin()); //DOMYŚLNIE JEGO LOGIN
        if(isBot) {
            player1Text = new Text("Bot1");
            playersList.set(0,false);
            botList.add(new BotPlayer(dif1,deck,1));
        }else {
            botList.add(null);
        }
        try {
            player1Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player1Text.setFont(Font.font("Verdana",30));
        }
        player1Pane.getChildren().add(player1Text);
        player1Text.setFill(Color.valueOf("000000"));
        player1Text.setLayoutX(10);
        player1Text.setLayoutY(35);

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
                //System.out.println("HERE");
                //System.out.println("this many players: " + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(525);
                player1Pane.setLayoutX(475);
                player1Pane.setEffect(new DropShadow());





                break;
            case 2:
                //System.out.println("this many players: " + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());
              //  playersList.add(true);
               // playersTurn.add(true);
               // playingList.add(true);
                playingList.add(true);
                //AnchorPane player2Pane = new AnchorPane();
                buildPlayer2Pane();

                break;
            case 3:
                //System.out.println("this many players" + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());
               // playersList.add(true);
               // playersTurn.add(true);

              //  playingList.add(true);
                playingList.add(true);
                playingList.add(true);
                buildPlayer2Pane();
                buildPlayer3Pane();

                break;
            case 4:
               // System.out.println("this many players" + numberOfPlayers);
                player1Pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px;-fx-border-width: 2px; -fx-border-radius: 10px;\n" +
                        "    -fx-border-color: black;");
                player1Pane.setPrefHeight(100);
                player1Pane.setPrefWidth(250);
                player1Pane.setLayoutY(225);
                player1Pane.setLayoutX(20);
                player1Pane.setEffect(new DropShadow());

               // playersList.add(true);
               // playersTurn.add(true);

               // playingList.add(true);
                playingList.add(true);
                playingList.add(true);
                playingList.add(true);
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

    /*
    private void playRound() {
        repetitions = 0;
        secondsLeft = 15;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repetitions += 1;
                secondsLeft = 15;
                if (repetitions == secondsLeft) {
                       //timer.cancel();
                }

                secondTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        secondsLeft -=1;
                        if (secondsLeft ==0) {
                            secondTimer.cancel();
                        }
                       // System.out.println(secondsLeft);
                        updateSeconds();
                    }
                }, 0, 1000);
                //System.out.println(repetitions + "repetitions");

            }

        }, 0, 15000);
        playingList.set(playerTurn-1, false);
        timer.cancel();


        if(playingList.contains(true)){

            for(int i = playerTurn+1; i < playersList.size()+2; i++){
                if(i == playersList.size()+1){
                    i=1;
                }
                if(playingList.get(i-1)){
                    playerTurn =i;
                    break;
                }
            }
        }else{
            while(krupier.play());
            updatePoints();
            createRetryPanel();
            return;
        }
        botPlay();
        playRound();

    }

     */
/*
    private void playRound() {
        repetitions = 0;
        secondsLeft = 15;

                secondTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        secondsLeft -=1;
                        if (secondsLeft ==0) {
                            secondTimer.cancel();
                            playingList.set(playerTurn - 1, false);
                            timer.cancel();


                            if (playingList.contains(true)) {

                                for (int i = playerTurn + 1; i < playersList.size() + 2; i++) {
                                    if (i == playersList.size() + 1) {
                                        i = 1;
                                    }
                                    if (playingList.get(i - 1)) {
                                        playerTurn = i;
                                        break;
                                    }
                                }
                            } else {
                                while (krupier.play()) ;
                                updatePoints();
                                createRetryPanel();
                                return;
                            }
                            botPlay();
                            playRound();
                        }
                        // System.out.println(secondsLeft);
                        updateSeconds();
                    }
                }, 0, 1000);
                //System.out.println(repetitions + "repetitions");

    }

 */

    private void playRound() {
        secondsLeft = 15;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft -= 1;
            timePlayed[playerTurn-1]++;
            if (secondsLeft == 0) {
                secondTimer.cancel();
                playingList.set(playerTurn - 1, false);
                timer.cancel();


                if (playingList.contains(true)) {

                    for (int i = playerTurn + 1; i < playersList.size() + 2; i++) {
                        if (i == playersList.size() + 1) {

                            i = 1;
                            updateCurrentlyPlaying();
                        }
                        if (playingList.get(i - 1)) {
                            playerTurn = i;
                            updateCurrentlyPlaying();
                            break;
                        }
                    }
                } else {
                    while (krupier.play()){log.appendText("\n"+"Dealer have drawn "+ deck.krupier.get(deck.krupier.size()-1).getName());}
                    updatePoints();
                    timeline.stop();
                    getUsers();
                    createRetryPanel();
                    return;
                }

                botPlay();
                addCards();
                secondsLeft = 15;
            }
            updateSeconds();
        }
        ));
        timeline.setCycleCount(-1);
        timeline.play();

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
        player2Text = new Text(MenuViewManager.user2.getLogin());//DOMYŚLNIE JEGO LOGIN
        playersList.add(true);
        playersTurn.add(false);
        if(MenuViewManager.user2.getLogin() == null) {
            player2Text = new Text("Bot2");
            playersList.set(1,false);
            botList.add(new BotPlayer(dif2,deck,2));
        }else {
            botList.add(null);
        }
        try {
            player2Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player2Text.setFont(Font.font("Verdana",30));
        }
        player2Pane.getChildren().add(player2Text);
        player2Text.setFill(Color.valueOf("000000"));
        player2Text.setLayoutX(10);
        player2Text.setLayoutY(35);


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

        player3Text = new Text(MenuViewManager.user3.getLogin()); //DOMYŚLNIE JEGO LOGIN
        playersList.add(true);
        playersTurn.add(false);
        if(MenuViewManager.user3.getLogin() == null) {
            player3Text = new Text("Bot3");
            playersList.set(2,false);
            botList.add(new BotPlayer(dif3,deck,3));
        }else {
            botList.add(null);
        }
        try {
            player3Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player3Text.setFont(Font.font("Verdana",30));
        }
        player3Pane.getChildren().add(player3Text);
        player3Text.setFill(Color.valueOf("000000"));
        player3Text.setLayoutX(10);
        player3Text.setLayoutY(35);


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
        player3Pane.setLayoutY(525);
        player4Pane.setLayoutX(650);
        player3Pane.setLayoutX(250);
        player4Pane.setEffect(new DropShadow());

        player4Text = new Text(MenuViewManager.user4.getLogin()); //DOMYŚLNIE JEGO LOGIN
        playersList.add(true);
        playersTurn.add(false);
        if(MenuViewManager.user4.getLogin() == null) {
            player4Text = new Text("Bot4");
            playersList.set(3,false);
            botList.add(new BotPlayer(dif4,deck,4));
        }else {
            botList.add(null);
        }
        try {
            player4Text.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            player4Text.setFont(Font.font("Verdana",30));
        }
        player4Pane.getChildren().add(player4Text);
        player4Text.setFill(Color.valueOf("000000"));
        player4Text.setLayoutX(10);
        player4Text.setLayoutY(35);


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

    private void updatePoints(){
        dealerPointsText.setText("Points: " + (deck.score[0] - deck.krupier.get(0).getValue()));
        player1PointsText.setText("Points: " + deck.score[1]);
        player2PointsText.setText("Points: " + deck.score[2]);
        player3PointsText.setText("Points: " + deck.score[3]);
        player4PointsText.setText("Points: " + deck.score[4]);
    }

    public void createRetryPanel(){
        retryPane.setPrefHeight(200);
        retryPane.setPrefWidth(500);
        retryPane.setLayoutY(250);
        retryPane.setLayoutX(350);
        retryPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

        dealerPointsText.setText("Points: " + deck.score[0] );

        try {
            retryText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 30));
        }catch (FileNotFoundException e){
            retryText.setFont(Font.font("Verdana",30));
        }
        retryText.setFill(Color.valueOf("FFFFFF"));
        retryText.setLayoutX(20);
        retryText.setLayoutY(45);
        retryPane.getChildren().add(retryText);

        showDealerCardsFinal();
        MainButton retryButton = new MainButton("Retry");
        retryButton.setLayoutX(130);
        retryButton.setLayoutY(80);
        //retryButton.setMaxSize(200, 200);
        retryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                roundN++;
                log.appendText("\n\nROUND"+ roundN +"\n ");
                removeDealerCards();
                hitButton.setDisable(false);
                standButton.setDisable(false);
                deck.discard();
                removeCurrentPlayerCards();
                removeCurrentPlayerCards();
                playerTurn = 1;
                for(int i = 0;i<=playingList.size();i++){
                    deck.draw(i);
                    deck.draw(i);
                }

                for(int i = 0; i < playingList.size();i++){
                    playingList.set(i,true);
                }
                gamePane.getChildren().remove(retryPane);
                retryPane.getChildren().remove(retryText);


                updatePoints();
                timeline.play();
                secondsLeft=15;
                playerTurn =1;
               // showCurrentPlayerCards();
                showDealerCards();
                updateCurrentlyPlaying();
                addCards();
            }
        });

        retryPane.getChildren().add(retryButton);

        MainButton quitButton = new MainButton("Quit");
        quitButton.setLayoutX(130);
        quitButton.setLayoutY(160);
       // quitButton.setMaxSize(200, 200);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCurrentPlayerCards();
                removeDealerCards();
                gameStage.close();
                MenuViewManager.getMainStage2().show();
                int[] data = new int[4];
                data = DbConnection.getData(MenuViewManager.user1.getLogin());

                if(data[0]==0){
                    MenuViewManager.userPanelWins.setText("Win percentage: " + 100 * 0 + "%");

                }else{
                    MenuViewManager.userPanelWins.setText("Win percentage: " + 100 * data[1]/data[0] + "%");

                }

            }
        });

        retryPane.getChildren().add(quitButton);

        gamePane.getChildren().add(retryPane);
        hitButton.setDisable(true);
        standButton.setDisable(true);

        //update bazy
        if(botList.get(0) != null) {
            DbConnection.updateUser(returnBotDiffName(botList.get(0).getDifficulty()),deck.player1.size(),timePlayed[0]);
        }else DbConnection.updateUser(MenuViewManager.user1.getLogin(),deck.player1.size(),timePlayed[0]);

        if(playingList.size() >= 2 && botList.get(1) != null) {
            DbConnection.updateUser(returnBotDiffName(botList.get(1).getDifficulty()),deck.player2.size(),timePlayed[1]);
        }else if (playingList.size() >= 2) DbConnection.updateUser(MenuViewManager.user2.getLogin(),deck.player2.size(),timePlayed[1]);

        if(playingList.size() >= 3 && botList.get(2) != null) {
            DbConnection.updateUser(returnBotDiffName(botList.get(2).getDifficulty()),deck.player3.size(),timePlayed[2]);
        }else if (playingList.size() >= 3)  DbConnection.updateUser(MenuViewManager.user3.getLogin(),deck.player3.size(),timePlayed[2]);

        if(playingList.size() == 4 && botList.get(3) != null) {
            DbConnection.updateUser(returnBotDiffName(botList.get(3).getDifficulty()),deck.player4.size(),timePlayed[3]);
        }else if (playingList.size() == 4) DbConnection.updateUser(MenuViewManager.user4.getLogin(),deck.player4.size(),timePlayed[3]);

    }

    private boolean[] getWinners(){
        int max =0;
        boolean winner[] = {false,false,false,false,false};
        for(int i =0; i<= playingList.size(); i++){
            if(deck.getScore(i)<=21){
                if(deck.getScore(i)>max){
                    max=deck.getScore(i);
                }
            }
        }
        for(int i =0; i<= playingList.size(); i++){
            if(deck.getScore(i)==max){
                winner[i]=true;
            }

        }
        return winner;
    }

    private void getUsers(){

        retryText.setText("The winner is ");
        int licznik = 0, sem =1, noWinner = 0;
        String winners ="";
        for (int i =0; i<=playingList.size();i++){
            if(getWinners()[i]){
                licznik++;
            }
        }

        for (int i =0; i<= playingList.size();i++){
            if(licznik == 1 && getWinners()[i]){
                noWinner = 1;
                switch (i) {
                    case 0:
                        retryText.setText("The winner is the Dealer");

                        break;
                    case 1:
                        retryText.setText("The winner is " + player1Text.getText());
                        if(botList.get(0) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(0).getDifficulty()));
                        } else DbConnection.updateWin(player1Text.getText());

                        break;
                    case 2:
                        retryText.setText("The winner is " + player2Text.getText());
                        if(botList.get(1) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(1).getDifficulty()));
                        }else DbConnection.updateWin(player2Text.getText());

                        break;
                    case 3:
                        retryText.setText("The winner is " + player3Text.getText());
                        if(botList.get(2) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(2).getDifficulty()));
                        }else DbConnection.updateWin(player3Text.getText());

                        break;
                    case 4:
                        retryText.setText("The winner is " + player4Text.getText());
                        if(botList.get(3) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(3).getDifficulty()));
                        }else DbConnection.updateWin(player4Text.getText());

                        break;
                }
            }

            if(sem == 1) {
                winners = "The winners are ";
                sem=0;
            }
            if(licznik > 1  && getWinners()[i]){
                noWinner = 1;
                switch (i) {
                    case 0:
                        winners = winners + "Dealer, ";
                        break;
                    case 1:
                        winners = winners + player1Text.getText() + ", ";
                        if(botList.get(0) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(0).getDifficulty()));
                        } else DbConnection.updateWin(player1Text.getText());
                        break;
                    case 2:
                        winners = winners + player2Text.getText() + ", ";
                        if(botList.get(1) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(1).getDifficulty()));
                        }else DbConnection.updateWin(player2Text.getText());
                        break;
                    case 3:
                        winners = winners + player3Text.getText() + ", ";
                        if(botList.get(2) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(2).getDifficulty()));
                        }else DbConnection.updateWin(player3Text.getText());
                        break;
                    case 4:
                        winners = winners + player4Text.getText() + ", ";
                        if(botList.get(3) != null) {
                            DbConnection.updateWin(returnBotDiffName(botList.get(3).getDifficulty()));
                        }else DbConnection.updateWin(player4Text.getText());
                        break;
                }
                retryText.setText(winners.substring(0, winners.length() - 2));

            }
            if(noWinner == 0){
                retryText.setText("No winners!");
            }

        }
    }

    private void showDealerCards(){
        InputStream card1 = getClass().getResourceAsStream(Card.getCardImage("default"));
        Image card1img= new Image(card1);
        ImageView dealer1img = new ImageView(card1img);
        InputStream card2 = getClass().getResourceAsStream(Card.getCardImage(deck.krupier.get(1).getName()));
        Image card2img= new Image(card2);
        ImageView dealer2img = new ImageView(card2img);

        dealer1img.setFitWidth(70);
        dealer1img.setFitHeight(95);
        dealerCardsPane.getChildren().add(dealer1img);

        dealer2img.setFitWidth(70);
        dealer2img.setFitHeight(95);
        dealer2img.setTranslateX(20);
        dealerCardsPane.getChildren().add(dealer2img);

    }

    private void showDealerCardsFinal(){
        removeDealerCards();
        dealerCardsPane.getChildren().clear();
        String card1string = "";
        for(int i =0; i<deck.krupier.size(); i++) {
                    card1string = Card.getCardImage(deck.krupier.get(i).getName());
                    InputStream card11 = getClass().getResourceAsStream(card1string);
                    Image card11img= new Image(card11);
                    ImageView player1img = new ImageView(card11img);


                    player1img.setFitWidth(70);
                    player1img.setFitHeight(95);
                    dealerCardsPane.getChildren().add(player1img);
                    player1img.setTranslateX( 20*i);
                }


    }

   /* private void showCurrentPlayerCards(){
        String card1string = "";
        String card2string = "";
        switch (playerTurn){
            case 1:
                card1string = Card.getCardImage(deck.player1.get(0).getName());
                card2string = Card.getCardImage(deck.player1.get(1).getName());
                break;
            case 2:
                card1string = Card.getCardImage(deck.player2.get(0).getName());
                card2string = Card.getCardImage(deck.player2.get(1).getName());
                break;
            case 3:
                card1string = Card.getCardImage(deck.player3.get(0).getName());
                card2string = Card.getCardImage(deck.player3.get(1).getName());
                break;
            case 4:
                card1string = Card.getCardImage(deck.player4.get(0).getName());
                card2string = Card.getCardImage(deck.player4.get(1).getName());
                break;
        }


        System.out.println("hehe");

        InputStream card11 = getClass().getResourceAsStream(card1string);
        Image card11img= new Image(card11);
        ImageView player1img = new ImageView(card11img);
        InputStream card22 = getClass().getResourceAsStream(card2string);
        Image card22img= new Image(card22);
        ImageView player2img = new ImageView(card22img);


        player1img.setFitWidth(70);
        player1img.setFitHeight(95);
        playerCardsPane.getChildren().add(player1img);

        player2img.setFitWidth(70);
        player2img.setFitHeight(95);
        playerCardsPane.getChildren().add(player2img);
        player2img.setTranslateX(playerCardsPane.getWidth()+20);
    } */

    private void removeCurrentPlayerCards(){
        playerCardsPane.getChildren().clear();
    }

    private void removeDealerCards(){
        dealerCardsPane.getChildren().clear();
    }

    /*private void addCard(){
        String card1string = "";
        switch (playerTurn){
            case 1:
                card1string = Card.getCardImage(deck.player1.get(deck.player1.size()-1).getName());
                break;
            case 2:
                card1string = Card.getCardImage(deck.player2.get(deck.player2.size()-1).getName());
                break;
            case 3:
                card1string = Card.getCardImage(deck.player3.get(deck.player3.size()-1).getName());
                break;
            case 4:
                card1string = Card.getCardImage(deck.player4.get(deck.player4.size()-1).getName());
                break;
        }

        InputStream card11 = getClass().getResourceAsStream(card1string);
        Image card11img= new Image(card11);
        ImageView player1img = new ImageView(card11img);


        player1img.setFitWidth(70);
        player1img.setFitHeight(95);
        playerCardsPane.getChildren().add(player1img);
        player1img.setTranslateX(playerCardsPane.getWidth()+100);
    }

    */
    public void addCards(){
        removeCurrentPlayerCards();
        playerCardsPane.getChildren().clear();
        String card1string = "";
        switch (playerTurn){
            case 1:
                for(int i =0; i<deck.player1.size(); i++) {
                    card1string = Card.getCardImage(deck.player1.get(i).getName());
                    InputStream card11 = getClass().getResourceAsStream(card1string);
                    Image card11img= new Image(card11);
                    ImageView player1img = new ImageView(card11img);


                    player1img.setFitWidth(70);
                    player1img.setFitHeight(95);
                    playerCardsPane.getChildren().add(player1img);
                    player1img.setTranslateX(-10*deck.player1.size() + 20*i + 50);
                }
                break;
            case 2:
                for(int i =0; i<deck.player2.size(); i++) {
                    card1string = Card.getCardImage(deck.player2.get(i).getName());
                    InputStream card11 = getClass().getResourceAsStream(card1string);
                    Image card11img = new Image(card11);
                    ImageView player1img = new ImageView(card11img);


                    player1img.setFitWidth(70);
                    player1img.setFitHeight(95);
                    playerCardsPane.getChildren().add(player1img);
                    player1img.setTranslateX(-10*deck.player2.size() + 20*i + 50);
                }
                break;
            case 3:
                for(int i =0; i<deck.player3.size(); i++) {
                    card1string = Card.getCardImage(deck.player3.get(i).getName());
                    InputStream card11 = getClass().getResourceAsStream(card1string);
                    Image card11img = new Image(card11);
                    ImageView player1img = new ImageView(card11img);


                    player1img.setFitWidth(70);
                    player1img.setFitHeight(95);
                    playerCardsPane.getChildren().add(player1img);
                    player1img.setTranslateX(-10*deck.player3.size() + 20*i + 50);
                }
                break;
            case 4:
                for(int i =0; i<deck.player4.size(); i++) {
                card1string = Card.getCardImage(deck.player4.get(i).getName());
                    InputStream card11 = getClass().getResourceAsStream(card1string);
                    Image card11img = new Image(card11);
                    ImageView player1img = new ImageView(card11img);


                    player1img.setFitWidth(70);
                    player1img.setFitHeight(95);
                    playerCardsPane.getChildren().add(player1img);
                    player1img.setTranslateX(-10*deck.player4.size() + 20*i + 50);
                }
                break;
        }

    }

    public String returnBotDiffName(int diff){
        if (diff == 0) return "easybot";
        if (diff == 1) return "mediumbot";
        if (diff == 2) return "hardbot";
        return "";
    }



    //private void

}
