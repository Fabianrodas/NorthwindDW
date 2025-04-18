package modelo;

public class Grafico4 {
    private String companyname;
    private int num_ventas;

    public Grafico4(String companyname, int num_ventas) {
        this.companyname = companyname;
        this.num_ventas = num_ventas;
    }

    public Grafico4() {
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public int getNum_ventas() {
        return num_ventas;
    }

    public void setNum_ventas(int num_ventas) {
        this.num_ventas = num_ventas;
    }
    
    
}
