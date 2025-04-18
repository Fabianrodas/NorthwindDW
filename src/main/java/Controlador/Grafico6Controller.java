package Controlador;

import bd.northwind.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Grafico6;

public class Grafico6Controller {
    private DbConnection connection = new DbConnection();

    public ArrayList<Grafico6> Read() {
        ArrayList<Grafico6> grafico6 = new ArrayList<>();
        String req = "SELECT s.country, COUNT(DISTINCT s.supplierid) AS total_proveedores, \n" +
        "SUM(od.quantity) AS total_productos FROM suppliers s\n" +
        "LEFT JOIN orderdetails od ON od.supplierid = s.supplierid GROUP BY s.country\n" +
        "ORDER BY total_proveedores DESC;";
        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String country = resultSet.getString("country");
                int total_proveedores = resultSet.getInt("total_proveedores");
                int total_productos = resultSet.getInt("total_productos");
                grafico6.add(new Grafico6(country, total_proveedores, total_productos));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(this.connection != null){
                this.connection.desconectar();
            }
        }
        return grafico6;
    }
}
