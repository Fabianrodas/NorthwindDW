package Controlador;

import bd.northwind.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Grafico3;

public class Grafico3Controller {
    private DbConnection connection = new DbConnection();

    public ArrayList<Grafico3> Read() {
        ArrayList<Grafico3> grafico3 = new ArrayList<>();
        String req = "SELECT c.country, SUM(od.total_orders) AS total FROM customers c, orderdetails od WHERE c.customerid = od.customerid GROUP BY c.country\n" +
"ORDER BY total DESC LIMIT 10;";
        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String pais = resultSet.getString("country");
                double total = resultSet.getDouble("total");
                grafico3.add(new Grafico3(pais, total));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(this.connection != null){
                this.connection.desconectar();
            }
        }
        return grafico3;
    }

}
