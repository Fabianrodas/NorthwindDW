package modelo;

public class GraficoSingleton {
    private static GraficoSingleton instancia;

    private int grafico;

    private GraficoSingleton() {}

    public static synchronized GraficoSingleton getInstancia() {
        if (instancia == null) {
            instancia = new GraficoSingleton();
        }
        return instancia;
    }

    public int getGrafico() {
        return grafico;
    }

    public void setGrafico(int grafico) {
        this.grafico = grafico;
    }
}
