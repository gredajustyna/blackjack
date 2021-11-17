package app.classes;

import app.ViewManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;

import java.util.List;

public class CloseButton extends Button {
    private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('quitX.png')";
    AudioClip clickSound = new AudioClip("file:src/app/music/click.m4a");

    public CloseButton(List<MainButton> menuButtons, List<SignButton> signButtons, AnchorPane mainPane, AnchorPane childPane){
        setPrefHeight(50);
        setPrefWidth(50);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners(menuButtons, signButtons, mainPane, childPane);
    }

    private void initializeButtonListeners(List<MainButton> menuButtons, List<SignButton> signButtons, AnchorPane mainPane, AnchorPane childPane){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(ViewManager.isSoundOn){
                        clickSound.play();
                    }
                    for(int i=0; i<menuButtons.size(); i++){
                        menuButtons.get(i).setDisable(false);
                    }
                    for (int i=0; i<signButtons.size(); i++){
                        signButtons.get(i).setDisable(false);
                    }
                    mainPane.getChildren().remove(childPane);
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
