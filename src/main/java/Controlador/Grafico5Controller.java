
package Controlador;

import bd.northwind.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Grafico5;

public class Grafico5Controller {
   private DbConnection connection = new DbConnection();

    public ArrayList<Grafico5> Read() {
        ArrayList<Grafico5> grafico5 = new ArrayList<>();
        String[] queries = {

            "SELECT e.firstname, e.lastname, SUM(od.total_orders) AS total_ventas, t.year " +
            "FROM employees e, dim_time t, orderdetails od " +
            "WHERE e.employeeid = od.employeeid AND t.timeid = od.timeid " +
            "AND e.firstname = 'Margaret' AND e.lastname = 'Peacock' " +
            "GROUP BY e.lastname, e.firstname, t.year ORDER BY t.year ASC;",

            "SELECT e.firstname, e.lastname, SUM(od.total_orders) AS total_ventas, t.year " +
            "FROM employees e, dim_time t, orderdetails od " +
            "WHERE e.employeeid = od.employeeid AND t.timeid = od.timeid " +
            "AND e.firstname = 'Janet' AND e.lastname = 'Leverling' " +
            "GROUP BY e.lastname, e.firstname, t.year ORDER BY t.year ASC;",

            "SELECT e.firstname, e.lastname, SUM(od.total_orders) AS total_ventas, t.year " +
            "FROM employees e, dim_time t, orderdetails od " +
            "WHERE e.employeeid = od.employeeid AND t.timeid = od.timeid " +
            "AND e.firstname = 'Nancy' AND e.lastname = 'Davolio' " +
            "GROUP BY e.lastname, e.firstname, t.year ORDER BY t.year ASC;",

            "SELECT e.firstname, e.lastname, SUM(od.total_orders) AS total_ventas, t.year " +
            "FROM employees e, dim_time t, orderdetails od " +
            "WHERE e.employeeid = od.employeeid AND t.timeid = od.timeid " +
            "AND e.firstname = 'Andrew' AND e.lastname = 'Fuller' " +
            "GROUP BY e.lastname, e.firstname, t.year ORDER BY t.year ASC;",

            "SELECT e.firstname, e.lastname, SUM(od.total_orders) AS total_ventas, t.year " +
            "FROM employees e, dim_time t, orderdetails od " +
            "WHERE e.employeeid = od.employeeid AND t.timeid = od.timeid " +
            "AND e.firstname = 'Robert' AND e.lastname = 'King' " +
            "GROUP BY e.lastname, e.firstname, t.year ORDER BY t.year ASC;"
        };

        try {
            this.connection.conectar();

            // Recorrer todas las consultas
            for (String req : queries) {
                PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
                ResultSet resultSet = statement.executeQuery();

                // Procesar los resultados de cada consulta
                while (resultSet.next()) {
                    int ventas = resultSet.getInt("total_ventas");
                    int anio = resultSet.getInt("year");
                    String empleado = resultSet.getString("firstname") + " " + resultSet.getString("lastname");

                    grafico5.add(new Grafico5(ventas, anio, empleado));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (this.connection != null) {
                this.connection.desconectar();
            }
        }

        return grafico5;
    } 
}
