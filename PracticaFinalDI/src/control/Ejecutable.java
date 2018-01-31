package control;

import javax.swing.JOptionPane;

import modelo.Consultas;
import modelo.Persona;
import modelo.Proyecto;
import vista.Formulario;
import vista.FrmAlta;

public class Ejecutable {

	public static void main(String[] args) {

		try {
		Formulario form = new Formulario();
		Persona pers = new Persona();
		Consultas cons = new Consultas();
		FrmAlta frmA = new FrmAlta();
		Proyecto proy = new Proyecto();
			
		ControladorConsultas control = new ControladorConsultas(form, frmA, cons, pers, proy);
		control.iniciar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Comprueba que tienes MySQL iniciado.");
			System.exit(0);
		}

	}

}
