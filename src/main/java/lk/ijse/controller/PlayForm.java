package lk.ijse.controller;

import Sound.PlayMusic;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.dto.ScoreDto;
import lk.ijse.model.ScoreModel;

import javax.sound.sampled.*;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class PlayForm extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 533;
    private static final int ROWS = 32;
    private static final int COLUMNS = 32;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final String[] FOODS_IMAGE = new String[]{

          "/assets/Foods/pineapple.png",
          "/assets/Foods/pngwing.png  ",
          "/assets/Foods/pngwing-1.png",
          "/assets/Foods/pngwing-2.png",
          "/assets/Foods/pngwing-3.png",
          "/assets/Foods/pngwing-4.png"

    };

    private static final String food = "/assets/ScoreFruit.png";

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;

    private double headXC = 200; // X-coordinate of snake head
    private double headYC = 200; // Y-coordinate of snake head
    private double headSize = 20; // Size of snake head

    private static final int BUTTON_SIZE = 50;
    private static final Color BUTTON_COLOR = Color.TRANSPARENT;
    private static final Color BUTTON_BORDER_COLOR = Color.TRANSPARENT;
    private static final Image BUTTON_IMAGE = new Image("/assets/Pause Button.png");

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (currentDirection != LEFT) {
                        currentDirection = RIGHT;
                    }
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (currentDirection != RIGHT) {
                        currentDirection = LEFT;
                    }
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (currentDirection != DOWN) {
                        currentDirection = UP;
                    }
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (currentDirection != UP) {
                        currentDirection = DOWN;
                    }
                }
            }
        });

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> {
            try {
                run(gc);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) throws IOException {

//        if (gameOver) {
//           gameOverOnAction(true);
//            return;
//        }

        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        setScore();


        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }


        gameOver();
        eatFood();
    }


    private void drawBackground(GraphicsContext gc) {
     for (int i = 0; i < ROWS; i++) {
         for (int j = 0; j < COLUMNS; j++) {
             if ((i + j) % 2 == 0) {
                 gc.setFill(Color.web("#B0E0E6")); // Light blue color
             } else {
                 gc.setFill(Color.web("#ADD8E6")); // Lighter blue color
             }
             gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
         }
     }
 }

    private void generateFood() {
        start:
        while (true) {

           int range = (20 - 1) + 1;
           int foodXValue = (int)(Math.random() * range) + 1;
           int foodYValue = (int) (Math.random() * range)+1;

           if (foodXValue < 28 && foodYValue < 28){
               foodX = foodXValue;
               foodY = foodYValue;

           }else{
               generateFood();
           }

            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    continue start;
                }
            }
            foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
            break;
        }
    }

    private void drawFood(GraphicsContext gc) {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

//    private void drawSnake(GraphicsContext gc) {
//
//        gc.setFill(Color.web("4674E9"));
//        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);
//
//        for (int i = 1; i < snakeBody.size(); i++) {
//            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
//                    SQUARE_SIZE - 1, 20, 20);
//        }
//    }

    private void drawSnake(GraphicsContext gc) {

        gc.setFill(Color.web("#008000")); // Dark green


        double snakeSize = SQUARE_SIZE * 0.75;


        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2, snakeHead.getY() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2,
                snakeSize, snakeSize, 35, 35);


        double eyeSize = snakeSize * 0.1;
        gc.setFill(Color.BLACK);
        gc.fillOval((snakeHead.getX() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2) + snakeSize * 0.25, (snakeHead.getY() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2) + snakeSize * 0.25, eyeSize, eyeSize);
        gc.fillOval((snakeHead.getX() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2) + snakeSize * 0.65, (snakeHead.getY() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2) + snakeSize * 0.25, eyeSize, eyeSize);


        double mouthWidth = snakeSize * 0.4;
        double mouthHeight = snakeSize * 0.2;
        gc.setFill(Color.RED);
        gc.fillOval((snakeHead.getX() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2) + snakeSize * 0.3, (snakeHead.getY() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2) + snakeSize * 0.6, mouthWidth, mouthHeight);


        for (int i = 1; i < snakeBody.size(); i++) {
            gc.setFill(Color.web("#008000")); // Dark green
            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2, snakeBody.get(i).getY() * SQUARE_SIZE + (SQUARE_SIZE - snakeSize) / 2,
                    snakeSize, snakeSize, 20, 20);
        }
    }

    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

//    public void gameOver() {
//
//      if (!gameOver){
//        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
//            gameOver = true;
//        }
//
//        for (int i = 1; i < snakeBody.size(); i++) {
//            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
//                gameOver = true;
//                break;
//            }
//        }
//        }
//    }



    public void gameOver() {
        if (!gameOver) {
            if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
                gameOver = true;
            } else {
                for (int i = 1; i < snakeBody.size(); i++) {
                    if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {

                        gameOver = true;
                        break;
                    }
                }
            }

            if (gameOver) {
                try {
                    PlayMusic playMusic = new PlayMusic();
                    playMusic.stopAllBackgroundMusic();

                    gameOverOnAction(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


    private void gameOverOnAction(boolean b) throws IOException {

        PlayMusic playMusic = new PlayMusic();
        playMusic.stopAllBackgroundMusic();

        int playedScore = score;

        System.out.println("Score :"+playedScore);

        ScoreModel scoreModel = new ScoreModel();

        boolean saved;


        try{
           saved =  scoreModel.save(new ScoreDto(
                    LocalDate.now(),
                    playedScore
            ));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (saved){

        playAudio("D:\\GDSE68\\PROJECTS\\CourseWorks\\New folder\\Snake-Game\\src\\main\\resources\\sounds\\gameOver.wav");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PauseForm.fxml"));
        Parent root = loader.load();
        PauseFormController controller = loader.getController();
        controller.setScore(String.valueOf(score));


        if (root != null) {
            Scene subScene = new Scene(root);

            Stage currentStage = (Stage) gc.getCanvas().getScene().getWindow();
            currentStage.close();


            Stage stage = new Stage();
            stage.setScene(subScene);

            subScene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
            tt.setFromX(-subScene.getWidth());
            tt.setToX(0);
            tt.play();

            stage.setResizable(false);
            stage.show();

            }
        }
    }



    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            playAudio("D:\\GDSE68\\PROJECTS\\CourseWorks\\New folder\\Snake-Game\\src\\main\\resources\\sounds\\clickSound.wav");
            generateFood();
            score += 5;
        }
    }

    private void playAudio(String soundPath) {
        try {
            File soundFile = new File(soundPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            // Set looping to false after starting to play only once
            clip.loop(0);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setScore() {

        Image image = new Image("/assets/Foods/fruitBucket 1.png");


        gc.drawImage(image, 30, 20, 40, 40); // Adjust the coordinates and size as needed


        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35)); // Comic Sans, bold, size 45
        gc.setTextAlign(TextAlignment.LEFT);


        gc.fillText("   "+ score, 50, 50); // Adjust the coordinates as needed


    }



}