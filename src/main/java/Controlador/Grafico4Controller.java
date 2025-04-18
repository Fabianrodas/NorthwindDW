package Controlador;

import bd.northwind.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Grafico4;

public class Grafico4Controller {
    private DbConnection connection = new DbConnection();

    public ArrayList<Grafico4> Read() {
        ArrayList<Grafico4> grafico4 = new ArrayList<>();
        String req = "SELECT c.companyname, COUNT(od.total_orders) AS num_ventas FROM customers c, orderdetails od WHERE c.customerid = od.customerid\n" +
        "GROUP BY c.companyname ORDER BY num_ventas DESC LIMIT 10;";
        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String companyname = resultSet.getString("companyname");
                int num_ventas = resultSet.getInt("num_ventas");
                grafico4.add(new Grafico4(companyname, num_ventas));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(this.connection != null){
                this.connection.desconectar();
            }
        }
        return grafico4;
    }
}
