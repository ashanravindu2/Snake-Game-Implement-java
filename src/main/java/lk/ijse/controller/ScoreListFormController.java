package lk.ijse.controller;

import Sound.PlayMusic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.ScoreDto;
import lk.ijse.model.ScoreModel;
import lk.ijse.util.SQLUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoreListFormController {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblHighestScore;

    @FXML
    private Label lblhighestScoreName;

    @FXML
    private AnchorPane scorePnae;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vbox;

    @FXML
    ImageView backgroundImage;

    MainFormController mainFormController = new MainFormController();

    PlayMusic playMusic;

    ScoreModel scoreModel = new ScoreModel();

    private static final String[] FOODS_IMAGE = new String[]{

            "/assets/Foods/pineapple.png",
            "/assets/Foods/pngwing.png  ",
            "/assets/Foods/pngwing-1.png",
            "/assets/Foods/pngwing-2.png",
            "/assets/Foods/pngwing-3.png",
            "/assets/Foods/pngwing-4.png"

    };

    public void initialize(){

       new Thread(()->{

           backgroundAnimation();


       }).start();

       setScore();

    }


    private void setScore() {
        try {

            ArrayList<ScoreDto> scoreDtoList = scoreModel.getAll();


            for (ScoreDto scoreDto :  scoreDtoList){

                    AnchorPane pane = createScorePane(String.valueOf(scoreDto.getDate()), String.valueOf(scoreDto.getScore()));
                    vbox.getChildren().add(pane);
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    private AnchorPane createScorePane(String date, String score) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #F4F4F4; -fx-background-radius: 25;");
        anchorPane.setPrefHeight(69.0);
        anchorPane.setPrefWidth(641.0);

        // Selecting a random image path
        Random random = new Random();
        int index = random.nextInt(FOODS_IMAGE.length);
        String imagePath = FOODS_IMAGE[index];

        // Creating ImageView for the image
        ImageView imageView = new ImageView(new Image(getClass().getResource(imagePath).toString()));
        imageView.setFitWidth(35); // Adjust the width of the image
        imageView.setFitHeight(35); // Adjust the height of the image
        imageView.setLayoutX(6); // Adjust the x-coordinate of the image
        imageView.setLayoutY(6); // Adjust the y-coordinate of the image

        Label lblDate = new Label(date);
        lblDate.setLayoutX(196.0);
        lblDate.setLayoutY(14.0);
        lblDate.setFont(new Font("Comic Sans MS Bold", 20.0));

        Label lblScore = new Label(score);
        lblScore.setLayoutX(476.0);
        lblScore.setLayoutY(14.0);
        lblScore.setFont(new Font("Comic Sans MS Bold", 20.0));

        anchorPane.getChildren().addAll(imageView, lblDate, lblScore);

        return anchorPane;
    }



    private void backgroundAnimation() {

            String videoPath = new File("../src/main/resources/video/Jungle Forest - Free Cartoon Background Loop.mp4").getAbsolutePath();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

    }



    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {

        playMusic = new PlayMusic();
        playMusic.stopAllBackgroundMusic();
        navigateToMainForm();

    }

    void navigateToMainForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
        Parent root = loader.load();
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage stage = (Stage) this.scorePnae.getScene().getWindow();
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

}
