package modelo;

public class VecesFoto {
    private static VecesFoto instancia;

    private boolean ciclo;

    private VecesFoto() {}

    public static synchronized VecesFoto getInstancia() {
        if (instancia == null) {
            instancia = new VecesFoto();
        }
        return instancia;
    }

    public boolean isCiclo() {
        return ciclo;
    }

    public void setCiclo(boolean ciclo) {
        this.ciclo = ciclo;
    }
    
}
