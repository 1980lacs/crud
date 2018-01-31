package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Conexion {

	String driver = "com.mysql.jdbc.Driver";
	String db2 = "jdbc:mysql://localhost";
	String db = db2 + "/empresa";
	String usuario = "root";
	String pass = "";
	Connection conex = null;
	
	public Connection getConexionVacia() {
		try {
			// CARGAR EL DRIVER
			Class.forName(driver);
			
			// CONECTARNOS
			conex = DriverManager.getConnection(db2, usuario, pass);
			
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver.");
		} catch (SQLException e) {
			
		}
		
		return conex;
	}
	
	public Connection getConexion() {
		try {
			// CARGAR EL DRIVER
			Class.forName(driver);
			
			// CONECTARNOS
			conex = DriverManager.getConnection(db, usuario, pass);
			
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver.");
		} catch (SQLException e) {
		}
		
		return conex;
	}
}
