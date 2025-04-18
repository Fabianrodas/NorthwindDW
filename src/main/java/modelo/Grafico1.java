package modelo;

public class Grafico1 {
    private int ventas;
    private int anio;
    private String trimestre;

    public Grafico1(int ventas, int anio, String trimestre) {
        this.ventas = ventas;
        this.anio = anio;
        this.trimestre = trimestre;
    }

    public Grafico1() {
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }
    
    
}
