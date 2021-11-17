package app.classes;

import app.ViewManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SignButton extends Button {
    private final String FONT_PATH = "src/app/fonts/Casino.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('button_p_small.png'); -fx-text-fill: FFFFFF";
    private final String BUTTON_HOVER_STYLE = "-fx-background-color: transparent; -fx-background-image: url('button_np_small.png'); -fx-text-fill: FFFFFF";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('button_np_small.png')";
    AudioClip clickSound = new AudioClip("file:src/app/music/click.m4a");

    public SignButton(String text){
        setText(text);
        setButtonFont();
        setPrefWidth(155);
        setPrefHeight(38);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(40);
        setLayoutY(getLayoutY()+4);
    }

    private void setButtonReleasedStyle(){
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(38);
        setLayoutY(getLayoutY()-4);
    }

    private void setButtonFont(){
        try {
            setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",20));
        }
    }

    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setButtonPressedStyle();
                    if(ViewManager.isSoundOn){
                        clickSound.play();
                    }
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
                setStyle(BUTTON_HOVER_STYLE);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
                setStyle(BUTTON_FREE_STYLE);
            }
        });
    }

}
