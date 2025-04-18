package modelo;

public class Grafico6 {
    private String country;
    private int total_proveedores;
    private int total_productos;

    public Grafico6(String country, int total_proveedores, int total_productos) {
        this.country = country;
        this.total_proveedores = total_proveedores;
        this.total_productos = total_productos;
    }

    public Grafico6() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotal_proveedores() {
        return total_proveedores;
    }

    public void setTotal_proveedores(int total_proveedores) {
        this.total_proveedores = total_proveedores;
    }

    public int getTotal_productos() {
        return total_productos;
    }

    public void setTotal_productos(int total_productos) {
        this.total_productos = total_productos;
    }
    
    
}
