package modelo;

public class Grafico2 {
    private String categoria;
    private double porcentaje;

    public Grafico2(String categoria, double porcentaje) {
        this.categoria = categoria;
        this.porcentaje = porcentaje;
    }

    public Grafico2() {
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    
}