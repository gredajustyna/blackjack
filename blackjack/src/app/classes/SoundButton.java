package app.classes;

import app.ViewManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

public class SoundButton extends Button {
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('Sound.png'); ";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('SoundOFF.png')";
    AudioClip clickSound = new AudioClip("file:src/app/music/click.m4a");

    public SoundButton(){
        setPrefHeight(50);
        setPrefWidth(50);
        setStyle(BUTTON_PRESSED_STYLE);
        initializeButtonListeners();
    }

    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(ViewManager.isSoundOn == true){
                        clickSound.play();
                        ViewManager.isSoundOn = false;
                        setStyle(BUTTON_FREE_STYLE);
                    }else{
                        ViewManager.isSoundOn = true;
                        setStyle(BUTTON_PRESSED_STYLE);
                    }
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
