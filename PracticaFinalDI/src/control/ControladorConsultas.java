package control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.regex.Pattern;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modelo.AbstractReportV2;
import modelo.Consultas;
import modelo.ModeloTabla;
import modelo.Persona;
import modelo.Proyecto;
import modelo.Conexion;
import vista.Formulario;
import vista.FrmAlta;

/**
 * Clase controladora de consultas
 * 
 * @author lacs
 * @version 1.0
 *
 */

public class ControladorConsultas implements ActionListener{

	private Formulario form;
	private Consultas cons;
	private Persona pers;
	private Proyecto proy;
	private FrmAlta frmA;
	private JScrollPane scp;
	private JScrollPane scpListaEmp;
	private JScrollPane scpListaPro;
	private JScrollPane scpRes;
	private ModeloTabla modelo;
	private String aux;
	
	/**
	 * Contructor de la clase
	 * 
	 * @param form
	 * @param frmA
	 * @param cons
	 * @param pers
	 * @param proy
	 */
	public ControladorConsultas(Formulario form, FrmAlta frmA, Consultas cons, Persona pers, Proyecto proy) {
		
		this.form = form;
		this.pers = pers;
		this.cons = cons;
		this.frmA = frmA;
		this.proy = proy;
		this.scp = new JScrollPane();
		this.scpListaEmp = new JScrollPane();
		this.scpListaPro = new JScrollPane();
		this.scpRes = new JScrollPane();
		this.modelo = new ModeloTabla();
		this.form.btnCrearEjemplo.addActionListener(this);
		this.form.btnAlta.addActionListener(this);
		this.form.btnModificar.addActionListener(this);
		this.form.btnVerAsignaciones.addActionListener(this);
		this.form.btnBorrar.addActionListener(this);
		this.form.btnBuscar.addActionListener(this);
		this.form.btnAsignarEmp.addActionListener(this);
		this.form.btnAsignarProy.addActionListener(this);
		this.form.mntmCrearbbdd.addActionListener(this);
		this.form.mntmNuevo.addActionListener(this);
		this.form.mntmSalir.addActionListener(this);
		this.form.mntmEmpleados.addActionListener(this);
		this.form.mntmProyectos.addActionListener(this);
		if (cons.comprobarDB()) {
			iniciarTabla();
			mostrarListaEmpleados();
			mostrarListaProyectos();
		}
	}
	
	public void iniciar() {
		form.setVisible(true);
		form.setLocationRelativeTo(null);
		form.setTitle("Proyecto Final de Trimestre Desarrollo de Interfaces --- 2º DAM");
		form.tabPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane tb = (JTabbedPane) e.getSource();
				if (tb.getSelectedIndex() == 1 ) {
					mostrarEmpleados();
					mostrarListaProyectos();
				} else {
					mostrarProyectos();
					mostrarListaEmpleados();
				}
				
			}
		});
		
		try {
			
			File fichero = new File("src/help" + File.separator + "help_set.hs");
			URL hsURL = fichero.toURI().toURL();
			
			//Crea el HelpSet y el HelpBroker
			
			HelpSet helpset = new HelpSet(getClass().getClassLoader(),hsURL);
			HelpBroker hb = helpset.createHelpBroker();
			
			//Poner ayuda a los items y teclas
			
			hb.enableHelpOnButton(form.mntmAyuda, "main", helpset);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == form.btnCrearEjemplo || e.getSource() == form.mntmCrearbbdd) {
			cons.crearEjemplo();
			iniciarTabla();
			mostrarListaEmpleados();
			mostrarListaProyectos();
		}
		
		if (e.getSource() == form.btnAlta || e.getSource() == form.mntmNuevo) {
			abrirFormAlta();
		} 
		
		if (e.getSource() == form.mntmSalir) {
			System.exit(0);
		}
		
		if (e.getSource() == frmA.btnEInsertar) {
				pers = new Persona();
				pers.setNombre(frmA.tfNombreEmpl.getText());
				pers.setApellidos(frmA.tfApellidos.getText());
				pers.setDni(frmA.tfDni.getText().toUpperCase().replaceAll(" ",""));
				String fecha = frmA.spEmpAno.getValue() + "-" + traducirFecha(frmA.spEmpMes.getValue()) + "-" + frmA.spEmpDia.getValue();
				pers.setFecha(fecha);
				if (vacio(frmA.tfNombreEmpl, frmA.lblNVacio) && vacio(frmA.tfApellidos, frmA.lblAVacio) && validaDNI(frmA.tfDni.getText().replaceAll(" ","").toUpperCase())) {
					cons.insertarPers(pers);
					limpiar();
					mostrarEmpleados();
					form.tabPane.setSelectedIndex(1);
				} else
					JOptionPane.showMessageDialog(null, "Revisa los campos en rojo");
				
		}
		
		if (e.getSource() == frmA.btnELimpiar) {
			limpiar();
		}
		
		if (e.getSource() == frmA.btnPInsertar) {
			proy = new Proyecto();
			proy.setTitulo(frmA.tfPTitulo.getText());
			String fecha = frmA.spIniAno.getValue() + "-" + traducirFecha(frmA.spIniMes.getValue()) + "-" + frmA.spIniDia.getValue();
			proy.setfInicio(fecha);
			fecha = frmA.spFinAno.getValue() + "-" + traducirFecha(frmA.spFinMes.getValue()) + "-" + frmA.spFinDia.getValue();
			proy.setFin(fecha);
			proy.setDescripcion(frmA.txaDescripcion.getText());
			if (vacio(frmA.tfPTitulo, frmA.lblTituloVacio)) {
				cons.insertarProy(proy);
				limpiar();
				mostrarProyectos();
				form.tabPane.setSelectedIndex(0);
			} else {
				JOptionPane.showMessageDialog(null, "Revisa los campos en rojo");
			}
		}
		
		if (e.getSource() == frmA.btnPLimpiar) {
			limpiar();
		}
		
		if (e.getSource() == frmA.btnVolver) {
			frmA.dispose();
		}
		
		if (e.getSource() == frmA.btnProyecto) {
			if (frmA.btnProyecto.getText() == "Proyecto") {
				frmA.loutCard.show(frmA.pnlPrincipal, "pro");
				frmA.btnProyecto.setText("Empleado");
			} else {
				frmA.loutCard.show(frmA.pnlPrincipal, "emple");
				frmA.btnProyecto.setText("Proyecto");
			}
		}
		
		
		if (e.getSource() == form.btnVerAsignaciones) {
			mostrarAsignaciones();
		}
		
		if (e.getSource() == form.btnBuscar) {
			if (cons.comprobarDB()) {
				form.listaResultados.setModel(cons.listaResultado(form.tfBuscar.getText()));
				scpRes.setViewportView(form.listaResultados);
				form.panelResultados.add(scpRes);
				form.tabPane.setSelectedComponent(form.panelResultados);
			}
			
		}
		
		if (e.getSource() == form.btnBorrar) {
			try {
				if (form.table.getSelectedRow() != -1) {
					modelo = (ModeloTabla)form.table.getModel();
					if (modelo.getColumnName(3).equals("id")) {
						cons.borrarAsig(modelo.removeRowEsp()[0], Integer.parseInt(modelo.removeRowEsp()[1]));
						mostrarAsignaciones();
					} else {
						modelo.removeRow();
						if (modelo.getColumnName(0).equals("dni")) {
							mostrarEmpleados();
						} else {
							mostrarProyectos();
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un registro de la tabla");
				}
			}catch(ClassCastException ex) {
				JOptionPane.showMessageDialog(null, "Para borrar un registro, primero\n"
						+ "tienes que seleccionarlo de la tabla.");
			}
		}
		
		if (e.getSource() == form.btnAsignarEmp) {
			try {
				if (form.listaEmpleados.getSelectedValue() != null) {
					if (form.table.getSelectedRow() != -1) {
						modelo = (ModeloTabla)form.table.getModel();
						int pro = modelo.getProyecto();
						String emp = (String)form.listaEmpleados.getSelectedValue();
						emp = emp.substring(0,9);
						cons.asignarPro(pro,emp);
					} else {
						JOptionPane.showMessageDialog(null, "Tienes que seleccionar un proyecto de la tabla");
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un empleado de la lista");
				}
			} catch (ClassCastException ex) {
				JOptionPane.showMessageDialog(null, "No hay ninguna tabla seleccionada");
			}
		}
		
		if (e.getSource() == form.btnAsignarProy) {
			try {
				if (form.listaProyectos.getSelectedValue() != null) {
					if (form.table.getSelectedRow() != -1) {
						modelo = (ModeloTabla)form.table.getModel();
						String emp = modelo.getEmpleado();
						String pro = (String)form.listaProyectos.getSelectedValue();
						int num = Integer.parseInt(pro.substring(0, 3).replaceAll("[^1-999]", ""));
						cons.asignarPro(num,emp);
					} else {
						JOptionPane.showMessageDialog(null, "Tienes que seleccionar un empleado de la tabla");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un proyecto de la lista");
				}
			} catch (ClassCastException ex) {
				JOptionPane.showMessageDialog(null, "No hay ninguna tabla seleccionada");
			}
		}
		
		if (e.getSource() == form.btnModificar) {
			try{
				if (form.table.getSelectedRow() != -1) {
					modelo = (ModeloTabla)form.table.getModel();
				
					if (modelo.getColumnName(0).equals("dni")) {
						abrirFormAlta();
						frmA.tfNombreEmpl.setText(modelo.updateRowEmpleado()[0]);
						frmA.tfApellidos.setText(modelo.updateRowEmpleado()[1]);
						frmA.tfDni.setText(modelo.updateRowEmpleado()[2]);
						int ano = Integer.parseInt(modelo.updateRowEmpleado()[3].substring(0, 4));
						int mes = Integer.parseInt(modelo.updateRowEmpleado()[3].substring(5, 7));
						int dia = Integer.parseInt(modelo.updateRowEmpleado()[3].substring(8, 10));
						frmA.spEmpDia.setValue(dia);
						frmA.spEmpAno.setValue(ano);
						frmA.spEmpMes.setValue(mesLiteral(mes));
						aux = modelo.updateRowEmpleado()[2];
						frmA.lblAltaEmpleado.setText("MODIFICAR EMPLEADO");
						frmA.setTitle("Modificacion de empleado");
						frmA.btnELimpiar.setEnabled(false);
						frmA.btnProyecto.setEnabled(false);
						frmA.btnEInsertar.setEnabled(false);
					} else {
						abrirFormAlta();
						frmA.loutCard.show(frmA.pnlPrincipal, "pro");
						frmA.tfPTitulo.setText(modelo.updateRowProyecto()[0]);
						int ano = Integer.parseInt(modelo.updateRowProyecto()[1].substring(0, 4));
						int mes = Integer.parseInt(modelo.updateRowProyecto()[1].substring(5, 7));
						int dia = Integer.parseInt(modelo.updateRowProyecto()[1].substring(8, 10));
						frmA.spIniDia.setValue(dia);
						frmA.spIniAno.setValue(ano);
						frmA.spIniMes.setValue(mesLiteral(mes));
						ano = Integer.parseInt(modelo.updateRowProyecto()[2].substring(0, 4));
						mes = Integer.parseInt(modelo.updateRowProyecto()[2].substring(5, 7));
						dia = Integer.parseInt(modelo.updateRowProyecto()[2].substring(8, 10));
						frmA.spFinDia.setValue(dia);
						frmA.spFinAno.setValue(ano);
						frmA.spFinMes.setValue(mesLiteral(mes));
						frmA.txaDescripcion.setText(modelo.updateRowProyecto()[3]);
						aux = modelo.updateRowEmpleado()[0];
						frmA.lblAltaProyecto.setText("MODIFICAR PROYECTO");
						frmA.setTitle("Modificacion de proyecto");
						frmA.btnPLimpiar.setEnabled(false);
						frmA.btnProyecto.setEnabled(false);
						frmA.btnPInsertar.setEnabled(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Para modificar un registro, primero\n"
							+ "tienes que seleccionarlo de la tabla.");
				}
			} catch(ClassCastException ex) {
				JOptionPane.showMessageDialog(null, "Para modificar un registro, primero\n"
						+ "tienes que seleccionarlo de la tabla.");
			}
		}
		
		if (e.getSource() == frmA.btnModificar) {
			pers = new Persona();
			pers.setDni(frmA.tfDni.getText());
			pers.setNombre(frmA.tfNombreEmpl.getText());
			pers.setApellidos(frmA.tfApellidos.getText());
			String fecha = frmA.spEmpAno.getValue() + "-" + traducirFecha(frmA.spEmpMes.getValue()) + "-" + frmA.spEmpDia.getValue();
			pers.setFecha(fecha);
			if (vacio(frmA.tfNombreEmpl, frmA.lblNVacio) && vacio(frmA.tfApellidos, frmA.lblAVacio) && validaDNI(frmA.tfDni.getText().replaceAll(" ","").toUpperCase())) {
				cons.modificarEmple(pers, aux);
				limpiar();
			} else
				JOptionPane.showMessageDialog(null, "Revisa los campos en rojo");
			
		}
		
		if (e.getSource() == frmA.btnModificarP) {
			proy = new Proyecto();
			proy.setTitulo(frmA.tfPTitulo.getText());
			String fecha = frmA.spIniAno.getValue() + "-" + traducirFecha(frmA.spIniMes.getValue()) + "-" + frmA.spIniDia.getValue();
			proy.setfInicio(fecha);
			fecha = frmA.spFinAno.getValue() + "-" + traducirFecha(frmA.spFinMes.getValue()) + "-" + frmA.spFinDia.getValue();
			proy.setFin(fecha);
			proy.setDescripcion(frmA.txaDescripcion.getText());
			if (vacio(frmA.tfPTitulo, frmA.lblTituloVacio)) {
				cons.modificarProy(proy, aux);
				limpiar();
			} else {
				JOptionPane.showMessageDialog(null, "Revisa los campos en rojo");
			}
		}
		
		if (e.getSource() == form.mntmEmpleados) {
			Conexion con = new Conexion();
			Connection conex = con.getConexion();
			cons.informeEmpleados("src\\Informes\\informe_empleados.jasper");
			cons.mostrarReporte();
		}
		
		if (e.getSource() == form.mntmProyectos) {
			Conexion con = new Conexion();
			Connection conex = con.getConexion();
			cons.informeEmpleados("src\\Informes\\informe_proyectos.jasper");
			cons.mostrarReporte();
		}
		
	}
	
	private void iniciarTabla() {
		if (cons.comprobarDB()) {
			mostrarProyectos();
			form.table.repaint();
		}
	}
	
	private void mostrarProyectos() {
		if (cons.comprobarDB()) {
			form.table.setModel(cons.consultaPro());
			scp.setViewportView(form.table);
			form.pnlTabla.add(scp,BorderLayout.CENTER);
		}
		
	}

	private void mostrarEmpleados() {
		if (cons.comprobarDB()) {
			form.table.setModel(cons.consultaEmp());
			scp.setViewportView(form.table);
			form.pnlTabla.add(scp,BorderLayout.CENTER);
		}
	}
	
	private void mostrarAsignaciones() {
		if (cons.comprobarDB()) {
			form.table.setModel(cons.consultaAsignaciones());
			scp.setViewportView(form.table);
			form.pnlTabla.add(scp,BorderLayout.CENTER);
		}
	}
	
	private void mostrarListaEmpleados() {
		if (cons.comprobarDB()) {
			form.listaEmpleados.setModel(cons.listaEmpleados());
			scpListaEmp.setViewportView(form.listaEmpleados);
			form.pnlProyectos.add(scpListaEmp);
		}
	}
	
	private void mostrarListaProyectos() {
		if (cons.comprobarDB()) {
			form.listaProyectos.setModel(cons.listaProyectos());
			scpListaPro.setViewportView(form.listaProyectos);
			this.form.pnlEmpleados.add(scpListaPro);
		}
	}

	private String mesLiteral(int mes) {
		String mesL = "";
		switch (mes) {
		case 1:
			mesL ="Enero";
			break;
		case 2:
			mesL ="Febrero";
			break;
		case 3:
			mesL ="Marzo";
			break;
		case 4:
			mesL ="Abril";
			break;
		case 5:
			mesL ="Mayo";
			break;
		case 6:
			mesL ="Junio";
			break;
		case 7:
			mesL ="Julio";
			break;
		case 8:
			mesL ="Agosto";
			break;
		case 9:
			mesL ="Septiembre";
			break;
		case 10:
			mesL ="Octubre";
			break;
		case 11:
			mesL ="Noviembre";
			break;
		case 12:
			mesL ="Diciembre";
			break;
		
		default:
			break;
		}
		return mesL;
	}

	private void abrirFormAlta() {
		try {
			
			frmA = new FrmAlta();
			frmA.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			frmA.setVisible(true);
			frmA.btnEInsertar.addActionListener(this);
			frmA.btnELimpiar.addActionListener(this);
			frmA.btnPInsertar.addActionListener(this);
			frmA.btnPLimpiar.addActionListener(this);
			frmA.btnProyecto.addActionListener(this);
			frmA.btnVolver.addActionListener(this);
			frmA.btnModificar.addActionListener(this);
			frmA.btnModificarP.addActionListener(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public void limpiar() {
		frmA.tfApellidos.setText(null);
		frmA.tfDni.setText(null);
		frmA.tfNombreEmpl.setText(null);
		frmA.tfPTitulo.setText(null);
		frmA.txaDescripcion.setText(null);
		frmA.spEmpAno.setValue(1980);
		frmA.spEmpDia.setValue(1);
		frmA.spEmpMes.setValue("Enero");
		frmA.spIniAno.setValue(1980);
		frmA.spIniDia.setValue(1);
		frmA.spIniMes.setValue("Enero");
		frmA.spFinAno.setValue(1980);
		frmA.spFinDia.setValue(1);
		frmA.spFinMes.setValue("Enero");
		
	}
	
	public int traducirFecha(Object object) {
		if (object == "Enero") return 1;
		else if (object == "Febrero") return 2;
		else if (object == "Marzo") return 3;
		else if (object == "Abril") return 4;
		else if (object == "Mayo") return 5;
		else if (object == "Junio") return 6;
		else if (object == "Julio") return 7;
		else if (object == "Agosto") return 8;
		else if (object == "Septiembre") return 9;
		else if (object == "Octubre") return 10;
		else if (object == "Noviembre") return 11;
		else if (object == "Diciembre") return 12;
		return -1;
	}
	
	public boolean validaDNI(String dni) {
		boolean valida = false;
		Border normal = frmA.tfApellidos.getBorder();
		
		if (!(Pattern.matches("\\d{8}[A-HJ-NP-TV-Z]", dni))){
			frmA.tfDni.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			frmA.lblDniMal.setText("<html>El DNI debe de contener<br>8 digitos y una letra</html>");
			valida = false;
		} else {
			frmA.tfDni.setBorder(normal);
			frmA.lblDniMal.setText("");
			valida = true;
		}	
		
		return valida;
	}
	
	public boolean vacio(JTextField tf, JLabel lbl) {
		Border normal = frmA.spEmpAno.getBorder();
		if (!tf.getText().equals("")) {
			tf.setBorder(normal);
			lbl.setText("");
			return true;
		} else {
			tf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			lbl.setText("No puede estar vacio.");
			return false;
		}
	}

}
