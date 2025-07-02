package org.example.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class PlayerController {

    private static final String MUSIC_DIR = "/home/rama/Music/";

    @FXML
    private ListView<String> trackListView;

    @FXML
    private Button playBtn, pauseBtn, prevBtn, nextBtn;

    private ObservableList<String> trackNames;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        loadTrackNames();
        setupButtonActions();
    }

    private void loadTrackNames() {
        File musicFolder = new File(MUSIC_DIR);
        File[] files = musicFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        trackNames = FXCollections.observableArrayList();
        if (files != null) {
            for (File f : files) {
                trackNames.add(f.getName());
            }
        }

        trackListView.setItems(trackNames);
    }

    private void setupButtonActions() {
        playBtn.setOnAction(e -> playSelectedTrack());
        pauseBtn.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.pause();
        });
        prevBtn.setOnAction(e -> playPreviousTrack());
        nextBtn.setOnAction(e -> playNextTrack());
    }

    private void playSelectedTrack() {
        String selectedTrack = trackListView.getSelectionModel().getSelectedItem();
        if (selectedTrack == null) {
            System.out.println("Выберите трек для воспроизведения");
            return;
        }
        playTrack(selectedTrack);
    }

    private void playTrack(String trackName) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            Media media = new Media(new File(MUSIC_DIR + trackName).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

            // Выделяем текущий трек в списке
            trackListView.getSelectionModel().select(trackName);

        } catch (Exception e) {
            System.err.println("Ошибка при воспроизведении: " + e.getMessage());
        }
    }

    private void playNextTrack() {
        int currentIndex = trackListView.getSelectionModel().getSelectedIndex();
        if (currentIndex < 0) return;

        int nextIndex = (currentIndex + 1) % trackNames.size();
        String nextTrack = trackNames.get(nextIndex);
        playTrack(nextTrack);
    }

    private void playPreviousTrack() {
        int currentIndex = trackListView.getSelectionModel().getSelectedIndex();
        if (currentIndex < 0) return;

        int prevIndex = (currentIndex - 1 + trackNames.size()) % trackNames.size();
        String prevTrack = trackNames.get(prevIndex);
        playTrack(prevTrack);
    }
}
