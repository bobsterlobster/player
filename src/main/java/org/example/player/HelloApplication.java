package org.example.player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Добавление CSS-стилей
        scene.getStylesheets().add(getClass().getResource("player-style.css").toExternalForm());

        stage.setTitle("Зеленый Музыкальный Плеер");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
