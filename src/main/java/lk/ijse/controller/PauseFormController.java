package lk.ijse.controller;

import Sound.PlayMusic;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class PauseFormController {

    @FXML
    private AnchorPane pausePane;
    @FXML
    private Button btnExitp;

    @FXML
    private Button btnRestartp;

    @FXML
    private Button btnScorep;

    @FXML
    private ImageView buttonImage;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    ImageView imgScore;

    @FXML
    Label lblScore;

    String click = "D:\\GDSE68\\PROJECTS\\CourseWorks\\New folder\\Snake-Game\\src\\main\\resources\\sounds\\clickSound.wav";


    public void initialize(){

    }

    void setScore(String score){
        lblScore.setText(score);
    }
    @FXML
    void btnExitOnAction(ActionEvent event) {

        playAudio(click);
        System.exit(0);
    }

    @FXML
    void btnRestartOnAction(ActionEvent event) throws Exception {

        playAudio(click);

        PlayForm playForm = new PlayForm();
        Stage stage = (Stage) this.pausePane.getScene().getWindow();
        playForm.start(stage);

    }

    @FXML
    void btnScoreOnAction(ActionEvent event) throws IOException {

        playAudio(click);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ScoreListForm.fxml"));
        Parent root = loader.load();
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage stage = (Stage) this.pausePane.getScene().getWindow();
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


    private void playAudio(String clickSoundPath) {
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
