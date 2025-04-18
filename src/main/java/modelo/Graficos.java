package modelo;

import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class Graficos {
    private static Graficos instancia;

    private ArrayList<ImageView> graficos;

    private Graficos() {}

    public static synchronized Graficos getInstancia() {
        if (instancia == null) {
            instancia = new Graficos();
        }
        return instancia;
    }

    public ArrayList<ImageView> getGraficos() {
        return graficos;
    }

    public void setGraficos(ArrayList<ImageView> graficos) {
        this.graficos = graficos;
    }
    
    
}
