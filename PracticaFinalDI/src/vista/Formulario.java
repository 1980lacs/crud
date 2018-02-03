package vista;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.FlowLayout;

import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.awt.event.InputEvent;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Formulario extends JFrame {

	private JPanel contentPane;
	public JButton btnCrearEjemplo;
	public JButton btnAlta;
	private JPanel pnlPrincipal;
	private JPanel pnlBotones;
	private JMenuBar menuBar;
	public JPanel pnlTabla;
	public JPanel pnlAux;
	public JButton btnModificar;
	public JButton btnBorrar;
	public JTable table;
	public JButton btnVerAsignaciones;
	public JTabbedPane tabPane;
	public JPanel pnlProyectos;
	public JPanel pnlAsignar;
	public JTextField tfBuscar;
	public JList<String> listaEmpleados;
	public JButton btnBuscar;
	public JButton btnAsignarEmp;
	public JPanel pnlEmpleados;
	public JPanel panelResultados;
	public JList<String> listaResultados;
	public JButton btnAsignarProy;
	public JPanel pnlAsignarP;
	public JList<String> listaProyectos;
	public JMenuItem mntmSalir;
	public JMenuItem mntmNuevo;
	public JMenuItem mntmCrearbbdd;
	public JMenu mnInformes;
	public JMenuItem mntmEmpleados;
	public JMenuItem mntmProyectos;
	public JMenuItem mntmAyuda;
	public JMenu mnAyuda;

	/**
	 * Create the frame.
	 */
	public Formulario() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNuevo = new JMenu("Nuevo");
		menuBar.add(mnNuevo);
		
		mntmCrearbbdd = new JMenuItem("CrearBBDD");
		mntmCrearbbdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_MASK));
		mnNuevo.add(mntmCrearbbdd);
		
		mntmNuevo = new JMenuItem("Nuevo");
		mntmNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		mnNuevo.add(mntmNuevo);
		
		JSeparator separator = new JSeparator();
		mnNuevo.add(separator);
		
		mntmSalir = new JMenuItem("Salir");
		mnNuevo.add(mntmSalir);
		
		mnInformes = new JMenu("Informes");
		menuBar.add(mnInformes);
		
		mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		mntmEmpleados = new JMenuItem("Empleados");
		mntmEmpleados.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
		
		mntmProyectos = new JMenuItem("Proyectos");
		mntmProyectos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK));
		
		mntmAyuda = new JMenuItem("Ayuda");
		mntmAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.ALT_MASK));
		
		mnInformes.add(mntmEmpleados);
		mnInformes.add(mntmProyectos);
		
		mnAyuda.add(mntmAyuda);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		pnlPrincipal = new JPanel();
		contentPane.add(pnlPrincipal, BorderLayout.CENTER);
		pnlPrincipal.setLayout(null);
		
		pnlTabla = new JPanel();
		pnlTabla.setBounds(0, 0, 473, 507);
		pnlPrincipal.add(pnlTabla);
		pnlTabla.setLayout(new BorderLayout(0, 0));
		
		table = new JTable() {
			//Implement table cell tool tips.           
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    //comment row, exclude heading
                    if(rowIndex != -1){
                      tip = getValueAt(rowIndex, colIndex).toString();
                    }
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }
		};
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlTabla.add(table, BorderLayout.CENTER);
		
		pnlAux = new JPanel();
		pnlAux.setBounds(483, 0, 301, 507);
		pnlPrincipal.add(pnlAux);
		pnlAux.setLayout(null);
		
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		tabPane.setBounds(0, 0, 301, 465);
		pnlAux.add(tabPane);
		
		pnlProyectos = new JPanel();
		tabPane.addTab("Proyectos", null, pnlProyectos, null);
		pnlProyectos.setLayout(new BorderLayout());
		
		listaEmpleados = new JList<String>();
		listaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEmpleados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Empleados disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		listaEmpleados.setVisibleRowCount(20);
		listaEmpleados.setVisible(true);
		pnlProyectos.add(listaEmpleados, BorderLayout.CENTER);
		
		pnlAsignar = new JPanel();
		tfBuscar = new JTextField();
		tfBuscar.setBounds(0, 476, 207, 20);
		pnlAux.add(tfBuscar);
		tfBuscar.setColumns(10);
		
		btnBuscar = new JButton("");
		btnBuscar.setIcon(new ImageIcon(("src/Imagenes/buscar.png")));
		btnBuscar.setBounds(217, 475, 74, 23);
		pnlAux.add(btnBuscar);
		
		pnlProyectos.add(pnlAsignar,BorderLayout.SOUTH);
		pnlAsignar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAsignarEmp = new JButton("Asignar");
		pnlAsignar.add(btnAsignarEmp);
		
		pnlEmpleados = new JPanel();
		tabPane.addTab("Empleados", null, pnlEmpleados, null);
		pnlEmpleados.setLayout(new BorderLayout(0, 0));
		
		listaProyectos = new JList<String>();
		listaProyectos.setBorder(new TitledBorder(null, "Proyectos disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		listaProyectos.setVisibleRowCount(20);
		listaProyectos.setVisible(true);
		listaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pnlEmpleados.add(listaProyectos, BorderLayout.CENTER);
		
		pnlAsignarP = new JPanel();
		pnlEmpleados.add(pnlAsignarP, BorderLayout.SOUTH);
		pnlAsignarP.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAsignarProy = new JButton("Asignar");
		pnlAsignarP.add(btnAsignarProy);
		
		panelResultados = new JPanel();
		tabPane.addTab("Resultado busqueda", null, panelResultados, null);
		panelResultados.setLayout(new BorderLayout(0, 0));
		
		listaResultados = new JList<String>();
		listaResultados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Coincidencias encontradas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelResultados.add(listaResultados, BorderLayout.CENTER);
		
		pnlBotones = new JPanel();
		FlowLayout layBotones = new FlowLayout(FlowLayout.LEFT);
		pnlBotones.setLayout(layBotones);
		contentPane.add(pnlBotones, BorderLayout.SOUTH);
		
		btnAlta = new JButton("Alta");
		pnlBotones.add(btnAlta);
		
		btnModificar = new JButton("Modificar");
		pnlBotones.add(btnModificar);
		
		btnBorrar = new JButton("Borrar");
		pnlBotones.add(btnBorrar);
		
		btnVerAsignaciones = new JButton("Ver Asignaciones");
		pnlBotones.add(btnVerAsignaciones);
		
		btnCrearEjemplo = new JButton("Crear Ejemplo");
		pnlBotones.add(btnCrearEjemplo);
	}
}
