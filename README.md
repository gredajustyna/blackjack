# blackjack
Gra blackjack na potrzeby przedmiotu Inżynieria Oprogramowania, 21/22

##Jak uruchomić grę?
Do uruchomienia polecane jest środowisko Intellij. Wersja JDK użyta przy tworzeniu projektu to 15.
Należy otworzyć folder ``` blackjack -> blackjack ```
Następnie uruchomić ``` src/app/Main.java ``` i edytować konfigurację Main.
W polu ``` VM options ``` należy wkleić:
```
--module-path "src/openjfx-15.0.1_windows-x64_bin-sdk/javafx-sdk-15.0.1/lib" --add-modules=javafx.controls,javafx.fxml,javafx.media
```


