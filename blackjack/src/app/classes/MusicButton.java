package app.classes;

import app.MenuViewManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

public class MusicButton extends Button {
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('Music.png'); ";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('MusicOFF.png')";
    AudioClip clickSound = new AudioClip("file:src/app/music/click.m4a");

    public MusicButton(MediaPlayer player){
        setPrefHeight(50);
        setPrefWidth(50);
        setStyle(BUTTON_PRESSED_STYLE);
        initializeButtonListeners(player);
    }

    private void initializeButtonListeners(MediaPlayer player){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if (MenuViewManager.isSoundOn){
                        clickSound.play();
                    }
                    if(player.isMute()){
                        player.setMute(false);
                        setStyle(BUTTON_PRESSED_STYLE);
                    }else{
                        player.setMute(true);
                        setStyle(BUTTON_FREE_STYLE);
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
    public void setX(){
        setStyle(BUTTON_FREE_STYLE);
    }
    public void setFree(){
        setStyle(BUTTON_PRESSED_STYLE);
    }

}
