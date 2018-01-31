package Informes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VariasQuerys extends JFrame {

	private JPanel contentPane;
	private JTextField tf_buscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VariasQuerys frame = new VariasQuerys();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VariasQuerys() {
		Connection conex = conexion();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGeneral = new JButton("Consulta general");
		btnGeneral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Proveedores> query = consulta("SELECT * FROM proveedores");
				
				abstractReport.crearReporte(query, "C:\\Users\\lacs8\\JaspersoftWorkspace\\MiPrimerReporte\\parametros.jasper");
				abstractReport.mostrarReporte();
			}
		});
		btnGeneral.setBounds(5, 5, 424, 23);
		contentPane.add(btnGeneral);
		
		JButton btnporNombre = new JButton("Por Nombre");
		btnporNombre.setBounds(5, 39, 424, 23);
		contentPane.add(btnporNombre);
		
		JButton btnporApellido = new JButton("Por Apellido");
		btnporApellido.setBounds(5, 73, 424, 23);
		contentPane.add(btnporApellido);
		
		tf_buscar = new JTextField();
		tf_buscar.setBounds(10, 199, 276, 20);
		contentPane.add(tf_buscar);
		tf_buscar.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(335, 198, 89, 23);
		contentPane.add(btnBuscar);
	}
	
	private Connection conexion() {
		try {
			Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost/jasper","root","");
			return miConexion;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Proveedores> consulta (String query){
		
		List<Proveedores> listaProveedores = new ArrayList<>();
		
		try {
			Statement sentencia = (Statement) conexion().createStatement();
			ResultSet rs = sentencia.executeQuery(query);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String dni = rs.getString("dni");
				String ciudad = rs.getString("ciudad");
				
				listaProveedores.add(new Proveedores(id, apellidos, nombre, dni, ciudad));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaProveedores;
		
	}
}
