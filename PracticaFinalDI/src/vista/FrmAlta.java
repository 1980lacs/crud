package vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import javax.swing.JTextArea;
import java.awt.Color;

public class FrmAlta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JPanel cardProyecto;
	public JButton btnPLimpiar;
	public JButton btnProyecto;
	public JButton btnVolver;
	public JButton btnPInsertar;
	public JPanel cardEmpleado;
	public JButton btnEInsertar;
	public JButton btnELimpiar;
	public JTextField tfNombreEmpl;
	public JTextField tfApellidos;
	public JTextField tfDni;
	public JLabel lblAltaEmpleado;
	public JPanel pnlPrincipal;
	public CardLayout loutCard;
	public JSpinner spEmpDia;
	public JSpinner spEmpMes;
	public JSpinner spEmpAno;
	public JLabel lblAltaProyecto;
	public JLabel lblTitulo;
	public JLabel lblFechaDeInicio;
	public JLabel lblFechaDeFinalizacin;
	public JLabel lblDescripcin;
	public JTextField tfPTitulo;
	public JSpinner spIniDia;
	public JSpinner spIniMes;
	public JSpinner spIniAno;
	public JSpinner spFinDia;
	public JSpinner spFinMes;
	public JSpinner spFinAno;
	public JScrollPane scp;
	public JTextArea txaDescripcion;
	public JButton btnModificar;
	public JButton btnModificarP;
	public JLabel lblAVacio;
	public JLabel lblDniMal;
	public JLabel lblNVacio;
	public JLabel lblTituloVacio;

	/**
	 * Create the dialog.
	 */
	public FrmAlta() {
		setTitle("Alta Empleado/Proyecto");
		setBounds(100, 100, 547, 412);
		loutCard = new CardLayout();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		pnlPrincipal = new JPanel();
		contentPanel.add(pnlPrincipal, BorderLayout.CENTER);
		pnlPrincipal.setLayout(loutCard);

		cardEmpleado = new JPanel();
		cardEmpleado.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlPrincipal.add(cardEmpleado, "emple");
		cardEmpleado.setLayout(null);

		btnEInsertar = new JButton("Insertar");
		btnEInsertar.setBounds(154, 296, 100, 23);
		cardEmpleado.add(btnEInsertar);


		btnELimpiar = new JButton("Limpiar");
		btnELimpiar.setBounds(264, 296, 100, 23);
		cardEmpleado.add(btnELimpiar);


		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 61, 118, 14);
		cardEmpleado.add(lblNombre);


		tfNombreEmpl = new JTextField();
		tfNombreEmpl.setBounds(131, 58, 189, 20);
		cardEmpleado.add(tfNombreEmpl);
		tfNombreEmpl.setColumns(10);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(10, 88, 118, 14);
		cardEmpleado.add(lblApellidos);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(10, 113, 118, 14);
		cardEmpleado.add(lblDni);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento");
		lblFechaDeNacimiento.setBounds(10, 138, 118, 14);
		cardEmpleado.add(lblFechaDeNacimiento);

		tfApellidos = new JTextField();
		tfApellidos.setBounds(131, 83, 189, 20);
		cardEmpleado.add(tfApellidos);
		tfApellidos.setColumns(10);

		tfDni = new JTextField();
		tfDni.setBounds(131, 110, 189, 20);
		cardEmpleado.add(tfDni);
		tfDni.setColumns(10);

		lblAltaEmpleado = new JLabel("ALTA EMPLEADO");
		lblAltaEmpleado.setFont(new Font("Calibri", Font.BOLD, 20));
		lblAltaEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltaEmpleado.setBounds(10, 11, 501, 36);					
		cardEmpleado.add(lblAltaEmpleado);
		
		spEmpDia = new JSpinner();
		spEmpDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spEmpDia.setBounds(131, 135, 39, 20);
		cardEmpleado.add(spEmpDia);
		
		spEmpMes = new JSpinner();
		spEmpMes.setModel(new SpinnerListModel(new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"}));
		spEmpMes.setBounds(180, 135, 74, 20);
		cardEmpleado.add(spEmpMes);
		
		spEmpAno = new JSpinner();
		spEmpAno.setModel(new SpinnerNumberModel(1980, 1900, 2050, 1));
		spEmpAno.setBounds(264, 135, 56, 20);
		cardEmpleado.add(spEmpAno);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(55, 296, 89, 23);
		cardEmpleado.add(btnModificar);
		
		lblNVacio = new JLabel("", SwingConstants.CENTER);
		lblNVacio.setForeground(Color.RED);
		lblNVacio.setBounds(330, 61, 181, 14);
		cardEmpleado.add(lblNVacio);
		
		lblAVacio = new JLabel("", SwingConstants.CENTER);
		lblAVacio.setForeground(Color.RED);
		lblAVacio.setBounds(330, 88, 181, 14);
		cardEmpleado.add(lblAVacio);
		
		lblDniMal = new JLabel("");
		lblDniMal.setForeground(Color.RED);
		lblDniMal.setBounds(330, 113, 181, 39);
		cardEmpleado.add(lblDniMal);

		cardProyecto = new JPanel();
		cardProyecto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlPrincipal.add(cardProyecto, "pro");
		cardProyecto.setLayout(null);

		btnPInsertar = new JButton("Insertar");
		btnPInsertar.setBounds(146, 296, 100, 23);
		cardProyecto.add(btnPInsertar);


		btnPLimpiar = new JButton("Limpiar");
		btnPLimpiar.setBounds(256, 296, 100, 23);
		cardProyecto.add(btnPLimpiar);
		
		lblAltaProyecto = new JLabel("ALTA PROYECTO");
		lblAltaProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltaProyecto.setFont(new Font("Calibri", Font.BOLD, 20));
		lblAltaProyecto.setBounds(10, 11, 501, 34);
		cardProyecto.add(lblAltaProyecto);
		
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(10, 56, 126, 14);
		cardProyecto.add(lblTitulo);
		
		lblFechaDeInicio = new JLabel("Fecha de inicio");
		lblFechaDeInicio.setBounds(10, 81, 126, 14);
		cardProyecto.add(lblFechaDeInicio);
		
		lblFechaDeFinalizacin = new JLabel("Fecha de finalizaci\u00F3n");
		lblFechaDeFinalizacin.setBounds(10, 106, 126, 14);
		cardProyecto.add(lblFechaDeFinalizacin);
		
		lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(10, 131, 126, 14);
		cardProyecto.add(lblDescripcin);
		
		tfPTitulo = new JTextField();
		tfPTitulo.setBounds(146, 53, 192, 20);
		cardProyecto.add(tfPTitulo);
		tfPTitulo.setColumns(10);
		
		spIniDia = new JSpinner();
		spIniDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spIniDia.setBounds(146, 78, 40, 20);
		cardProyecto.add(spIniDia);
		
		spIniMes = new JSpinner();
		spIniMes.setModel(new SpinnerListModel(new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"}));
		spIniMes.setBounds(196, 78, 82, 20);
		cardProyecto.add(spIniMes);
		
		spIniAno = new JSpinner();
		spIniAno.setModel(new SpinnerNumberModel(1980, 1900, 2100, 1));
		spIniAno.setBounds(288, 78, 50, 20);
		cardProyecto.add(spIniAno);
		
		spFinDia = new JSpinner();
		spFinDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spFinDia.setBounds(146, 103, 39, 20);
		cardProyecto.add(spFinDia);
		
		spFinMes = new JSpinner();
		spFinMes.setModel(new SpinnerListModel(new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"}));
		spFinMes.setBounds(196, 103, 82, 20);
		cardProyecto.add(spFinMes);
		
		spFinAno = new JSpinner();
		spFinAno.setModel(new SpinnerNumberModel(1980, 1900, 2100, 1));
		spFinAno.setBounds(288, 103, 50, 20);
		cardProyecto.add(spFinAno);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(146, 126, 192, 159);
		cardProyecto.add(scrollPane);
		
		txaDescripcion = new JTextArea();
		scrollPane.setViewportView(txaDescripcion);
		txaDescripcion.setLineWrap(true);
		
		btnModificarP = new JButton("Modificar");
		btnModificarP.setBounds(47, 296, 89, 23);
		cardProyecto.add(btnModificarP);
		
		lblTituloVacio = new JLabel("");
		lblTituloVacio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloVacio.setForeground(Color.RED);
		lblTituloVacio.setBounds(348, 56, 163, 14);
		cardProyecto.add(lblTituloVacio);

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.SOUTH);

		btnProyecto = new JButton("Proyecto");
		panel.add(btnProyecto);


		btnVolver = new JButton("Volver");
		panel.add(btnVolver);


	}
}
