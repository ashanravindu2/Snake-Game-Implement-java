package lk.ijse.controller;

import Sound.PlayMusic;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class MainFormController {

    @FXML
    private Button btnClose;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnScoreList;

    @FXML
    private AnchorPane loginPane;

    PlayMusic playMusic = new PlayMusic();

    private String clickSoundPath = "D:\\GDSE68\\PROJECTS\\CourseWorks\\New folder\\Snake-Game\\src\\main\\resources\\sounds\\clickSound.wav";
    private String musicPath = "D:\\GDSE68\\PROJECTS\\CourseWorks\\New folder\\Snake-Game\\src\\main\\resources\\sounds\\AdhesiveWombat - Night Shade.wav";

    private Clip clip;

    public void initialize(){

        new Thread(()->{
            playBackgroundMusic();
        }).start();

    }

    private void playBackgroundMusic() {


        playMusic.playBackgroundMusic(musicPath);

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {

        playAudio();
        System.exit(0);
    }

    @FXML
    void btnMinimizeOnAction(ActionEvent event) {

        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void btnPlayOnAction(ActionEvent event) throws Exception {

        playMusic.stopAllBackgroundMusic();

        playAudio();

        PlayForm playForm = new PlayForm();
        Stage stage = (Stage) this.loginPane.getScene().getWindow();



        playForm.start(stage);

    }

    @FXML
    void btnScoreListOnAction(ActionEvent event) throws IOException {

        playAudio();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ScoreListForm.fxml"));
        Parent root = loader.load();
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage stage = (Stage) this.loginPane.getScene().getWindow();
            stage.setScene(subScene);


            TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
            tt.setFromX(-subScene.getWidth());
            tt.setToX(0);
            tt.play();
            stage.setScene(subScene);
            stage.setResizable(false);
            stage.show();
        }
    }

    private void playAudio() {
        try {
            File soundFile = new File(clickSoundPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
