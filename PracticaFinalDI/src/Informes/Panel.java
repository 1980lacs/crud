package Informes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.chrono.MinguoChronology;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Panel extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Panel frame = new Panel();
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
	public Panel() {
		Connection conex = conexion();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(5, 5, 93, 61);
		contentPane.add(btnEjecutar);
		
		JButton btnPDF = new JButton("PDF");
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abstractReport.crearReporte(conex,"C:\\Users\\lacs8\\JaspersoftWorkspace\\MiPrimerReporte\\informe1.jasper");
				abstractReport.exportarPDF("C:\\Users\\lacs8\\Desktop\\informeGenerado.pdf");
			}
		});
		btnPDF.setBounds(108, 5, 93, 61);
		contentPane.add(btnPDF);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Madrid", "Barcelona", "Salamanca", "Toledo"}));
		comboBox.setBounds(82, 169, 66, 20);
		contentPane.add(comboBox);
		
		JButton btnEjecutarConFiltro = new JButton("Ejecutar con Filtro");
		btnEjecutarConFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEjecutarConFiltro.setBounds(158, 168, 172, 23);
		contentPane.add(btnEjecutarConFiltro);
		
		conexion();
		
		btnEjecutar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				abstractReport.crearReporte(conex,"C:\\Users\\lacs8\\JaspersoftWorkspace\\MiPrimerReporte\\informe1.jasper");
				abstractReport.mostrarReporte();
				//System.out.println("hola");
				
			}
		});
		
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
}
