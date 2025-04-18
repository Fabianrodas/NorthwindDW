package modelo;


public class Grafico5 {
   private int ventas;
   private int anio;
   private String empleado;

    public Grafico5(int ventas, int anio, String empleado) {
        this.ventas = ventas;
        this.anio = anio;
        this.empleado = empleado;
    }

    public Grafico5() {
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

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
   
   
}
