package bd.northwind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    private Connection connection;
    
    public boolean conectar(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/northwind_pentaho",leerInfo()[0],leerInfo()[1]);
            return true;
        }catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }
    public void desconectar(){
        try{
            connection.close();
        }catch (SQLException ex){
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE,null,ex);
            }
    }
    
    public Connection getConnection(){
        return connection;
    }
    
    private String[] leerInfo() {
        String rutaArchivo = "postgresql_login.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = br.readLine();
            String[] partes = linea.split(",");
            return partes;
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }
}