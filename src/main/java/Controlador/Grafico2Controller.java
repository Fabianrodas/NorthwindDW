package Controlador;

import bd.northwind.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Grafico2;

public class Grafico2Controller {
    private DbConnection connection = new DbConnection();

    public ArrayList<Grafico2> Read() {
        ArrayList<Grafico2> grafico2 = new ArrayList<>();
        String req = "SELECT p.categoryname, ROUND((SUM(od.total_orders)::numeric / SUM(SUM(od.total_orders)) OVER ()) * 100, 2) AS porcentaje \n" +
        "FROM products p, orderdetails od WHERE p.productid = od.productid \n" +
        "GROUP BY p.categoryname ORDER BY porcentaje DESC;";
        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String categoria = resultSet.getString("categoryname");
                double porcentaje = resultSet.getDouble("porcentaje");
                grafico2.add(new Grafico2(categoria, porcentaje));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(this.connection != null){
                this.connection.desconectar();
            }
        }
        return grafico2;
    }
}
