package Controlador;

import bd.northwind.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Grafico1;

public class Grafico1Controller {
    private DbConnection connection = new DbConnection();

    public ArrayList<Grafico1> Read() {
        ArrayList<Grafico1> grafico1 = new ArrayList<>();
        String req = "SELECT SUM(od.total_orders) AS ventas, t.year, t.quarter FROM orderdetails od, dim_time t WHERE od.timeid = t.timeid GROUP BY t.year, t.quarter;";
        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ventas = resultSet.getInt("ventas");
                int anio = resultSet.getInt("year");
                String trimestre = resultSet.getString("quarter");
                grafico1.add(new Grafico1(ventas, anio, trimestre));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(this.connection != null){
                this.connection.desconectar();
            }
        }
        return grafico1;
    }

}
