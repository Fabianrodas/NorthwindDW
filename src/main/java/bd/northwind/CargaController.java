package bd.northwind;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CargaController implements Initializable {
    
    @FXML private ImageView imageView1, imageView2;
    @FXML private ProgressBar progressBar;
    @FXML private Button salir;
    @FXML private AnchorPane root;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        salir.setOnAction(event -> Platform.exit());
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage = (Stage) salir.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Image image2 = new Image(getClass().getResourceAsStream("/images/imagen1.png"));
        Image image1 = new Image(getClass().getResourceAsStream("/images/imagen2.png"));
        imageView1.setImage(image1);
        imageView2.setImage(image2);
        imageView1.setOpacity(1.0);
        imageView2.setOpacity(0.0);
        progressBar.setStyle("-fx-accent: blue;");
        progressBar.setProgress(0);
        startLoadingThread();
    }
    
    private void startLoadingThread() {
        Thread loadingThread = new Thread(() -> {
            try {
                Platform.runLater(this::startFadeTransition);
                Platform.runLater(this::startLoadingAnimation);
                Thread.sleep(2200);
                Platform.runLater(this::transitionToMainScreen);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        loadingThread.setDaemon(true);
        loadingThread.start();
    }
    
    private void startFadeTransition() {
        FadeTransition fadeOut1 = new FadeTransition(Duration.millis(1900), imageView1);
        fadeOut1.setFromValue(1.0);
        fadeOut1.setToValue(0.0);
        FadeTransition fadeIn2 = new FadeTransition(Duration.millis(2000), imageView2);
        fadeIn2.setFromValue(0.0);
        fadeIn2.setToValue(1.0);
        fadeOut1.play();
        fadeIn2.play();
    }

    private void transitionToMainScreen() {
        try {
            App.setRootWithTransition("main");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void startLoadingAnimation() {
        Timeline timeline = new Timeline();
        for (int i = 0; i < 2; i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(i * 0.8),
                event -> {
                    Timeline progressTimeline = new Timeline(
                        new KeyFrame(Duration.ZERO, 
                            new javafx.animation.KeyValue(progressBar.progressProperty(), progressBar.getProgress())),
                        new KeyFrame(Duration.seconds(1), 
                            new javafx.animation.KeyValue(progressBar.progressProperty(), (index + 1) / (double) 2))
                    );
                    progressTimeline.play();
                }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.play();
    }
}
