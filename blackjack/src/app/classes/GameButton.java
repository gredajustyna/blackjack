package app.classes;

import app.MenuViewManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameButton extends Button {
    private final String FONT_PATH = "src/app/fonts/Casino.ttf";
    AudioClip clickSound = new AudioClip("file:src/app/music/click.m4a");


    public GameButton(String text, String buttonUrl){
        setText(text);
        setButtonFont();
        setPrefWidth(120);
        setPrefHeight(120);
        setGameButton(buttonUrl);
        initializeButtonListeners();
    }

    private void setGameButton(String buttonUrl){
        final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('"+ buttonUrl +"'); -fx-text-fill: black; -fx-background-repeat: no-repeat; -fx-background-size: 120px 120px";
        System.out.println(buttonUrl);
        setStyle(BUTTON_FREE_STYLE);
    }

    private void setButtonFont(){
        try {
            setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",25));
        }
    }

    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(MenuViewManager.isSoundOn){
                        clickSound.play();
                    }
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }
}
