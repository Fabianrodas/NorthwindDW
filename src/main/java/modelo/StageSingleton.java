package modelo;

import javafx.stage.Stage;

public class StageSingleton {
    private static StageSingleton instancia;

    private Stage stage;

    private StageSingleton() {}

    public static synchronized StageSingleton getInstancia() {
        if (instancia == null) {
            instancia = new StageSingleton();
        }
        return instancia;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
