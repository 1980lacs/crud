package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Consultas extends Conexion{
	
	ResultSet rs = null;
	boolean existe = false;
	String tabla ="";
	String tExist = "SHOW DATABASES";
	JasperReport report;
	JasperPrint reportFilled;
	JasperViewer viewer;
	
	public boolean comprobarDB() {
		Connection conex = getConexionVacia();
		Statement st = null;
		try {
			st = conex.createStatement();
			rs = st.executeQuery(tExist);
			while (rs.next()) {
				tabla = rs.getString(1);
				if (tabla.equals("empresa")) {
					existe = true;
					return existe;
				}
			}
		} catch (SQLException e) {
			System.out.println("No existe");
			e.printStackTrace();
		}
		
		existe = false;
		return existe;	
	}
	public boolean crearEjemplo() {
		
		Connection conex = getConexionVacia();
		Statement st = null;
		
		
		String bbdd = 	"CREATE DATABASE empresa;";
		String selec = 	"USE empresa;";				
		String templeados = "CREATE TABLE t_empleados (dni VARCHAR (10) PRIMARY KEY, nombre VARCHAR(30), apellidos VARCHAR (50), fecha_nac DATE);";
		String tproyectos = "CREATE TABLE t_proyectos (id INT(3) PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(150), fecha_ini DATE, fecha_fin DATE, observaciones LONGTEXT);";
		String trelaccion = "CREATE TABLE t_pro_emple (proyecto INT (3), empleado VARCHAR(10))";		
		
		try {
			st = conex.createStatement();
			
			/*rs = st.executeQuery(tExist);
			while (rs.next()) {
				tabla = rs.getString(1);
				if (tabla.equals("empresa")) {
					JOptionPane.showMessageDialog(null, "La BBDD \"" + tabla + "\" ya existe.");
					existe = true;
					
				}
			}*/
			if (!comprobarDB()) {
				st.executeUpdate(bbdd);
				st.executeUpdate(selec);
				st.executeUpdate(templeados);
				st.executeUpdate(tproyectos);
				st.executeUpdate(trelaccion);
				rellenarEmple();
				rellenarPro();
				JOptionPane.showMessageDialog(null, "La BBDD \"empresa\" ha sido creada.");
			} else {
				JOptionPane.showMessageDialog(null, "La BBDD \"" + tabla + "\" ya existe.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return true;
	}
	
	private void rellenarEmple() {
		try {
			Connection conex = getConexion();
			PreparedStatement ps = null;
			String sql = "INSERT INTO t_empleados VALUES (?,?,?,?);";
			FileReader fr = new FileReader("src//ArchivosEjemplo//Empleados.txt");
			BufferedReader br = new BufferedReader(fr);
			String linea ="";
			String texto[] = null;
			while ((linea = br.readLine()) != null) {
				texto = linea.split(",");
				ps = conex.prepareStatement(sql);
				ps.setString(1, texto[0]);
				ps.setString(2, texto[1]);
				ps.setString(3, texto[2]);
				ps.setString(4, texto[3]);
				ps.execute();
			}
			
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void rellenarPro() {
		try {
			Connection conex = getConexion();
			PreparedStatement ps = null;
			String sql = "INSERT INTO t_Proyectos (nombre, fecha_ini, fecha_fin, observaciones) VALUES (?,?,?,?);";
			FileReader fr = new FileReader("src//ArchivosEjemplo//Proyectos.txt");
			BufferedReader br = new BufferedReader(fr);
			String linea ="";
			String texto[] = null;
			while ((linea = br.readLine()) != null) {
				texto = linea.split(",");
				ps = conex.prepareStatement(sql);
				ps.setString(1, texto[0]);
				ps.setString(2, texto[1]);
				ps.setString(3, texto[2]);
				ps.setString(4, texto[3]);
				ps.execute();
			}
			
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean insertarPers(Persona pers){
		if (comprobarDB()) {
			Connection conex = getConexion();
			PreparedStatement ps = null;
			String sql = "INSERT INTO t_empleados (dni, nombre, apellidos, fecha_nac) VALUES(?,?,?,?);";
			
			try {
				ps = conex.prepareStatement(sql);
				ps.setString(1, pers.getDni());
				ps.setString(2, pers.getNombre());
				ps.setString(3, pers.getApellidos());
				ps.setString(4, pers.getFecha());
				ps.execute();
				JOptionPane.showMessageDialog(null, "La inserción se ha realizado satisfactoriamente.");
				return true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se ha podido realizar la inserción.");
				return false;
			} finally {
				try {
					conex.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "No hay seleccionada ninguna base de datos");
			return false;
		}

	}
	
	public boolean insertarProy(Proyecto pers){
		if (comprobarDB()) {
			Connection conex = getConexion();
			PreparedStatement ps = null;
			String sql = "INSERT INTO t_proyectos (nombre, fecha_ini, fecha_fin, observaciones) VALUES(?,?,?,?);";
			
			try {
				ps = conex.prepareStatement(sql);
				ps.setString(1, pers.getTitulo());
				ps.setString(2, pers.getfInicio());
				ps.setString(3, pers.getFin());
				ps.setString(4, pers.getDescripcion());
				ps.execute();
				JOptionPane.showMessageDialog(null, "La inserción se ha realizado satisfactoriamente.");
				return true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se ha podido realizar la inserción.");
				return false;
			} finally {
				try {
					conex.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		} else {
			JOptionPane.showMessageDialog(null, "No hay seleccionada ninguna base de datos");
			return false;
		}

	}
	
	public boolean modificarEmple(Persona pers, String aux) {
		
		Connection conex = getConexion();
		PreparedStatement ps = null;
		String where = aux;
		
		String sql = "UPDATE t_empleados SET dni=?,nombre=?,apellidos=?,fecha_nac=? WHERE dni=\"" + where + "\"";
		
		try {
			ps = conex.prepareStatement(sql);
			ps.setString(1, pers.getDni());
			ps.setString(2, pers.getNombre());
			ps.setString(3, pers.getApellidos());
			ps.setString(4, pers.getFecha());
			ps.execute();
			JOptionPane.showMessageDialog(null, "La modificación se ha realizado satisfactoriamente.");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "La modificación no se ha podido realizar.");
			return false;
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean modificarProy(Proyecto pers, String aux) {
		
		Connection conex = getConexion();
		PreparedStatement ps = null;
		String where = aux;
		
		String sql = "UPDATE t_proyectos SET nombre=?,fecha_ini=?,fecha_fin=?,observaciones=? WHERE nombre=\"" + where + "\"";
		
		try {
			ps = conex.prepareStatement(sql);
			ps.setString(1, pers.getTitulo());
			ps.setString(2, pers.getfInicio());
			ps.setString(3, pers.getFin());
			ps.setString(4, pers.getDescripcion());
			ps.execute();
			JOptionPane.showMessageDialog(null, "La modificación se ha realizado satisfactoriamente.");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "La modificación no se ha podido realizar.");
			return false;
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public ModeloTabla consultaEmp() {
		if(comprobarDB()) {
			Connection conex = getConexion();
			
			String consulta = "SELECT * FROM `t_empleados`;";
			try {
				
				Statement sentencia = conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				rs = sentencia.executeQuery(consulta);
				
				ModeloTabla modelo = new ModeloTabla(rs);			
				return modelo;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	public ModeloTabla consultaPro() {
		if (comprobarDB()) {
			Connection conex = getConexion();
			
			String consulta = "SELECT * FROM `t_proyectos`;";
			try {
				
				Statement sentencia = conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs = sentencia.executeQuery(consulta);
				
				ModeloTabla modelo = new ModeloTabla(rs);			
				return modelo;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return null;
	}
	
	public ModeloTabla consultaAsignaciones() {
		Connection conex = getConexion();
		
		/*SELECT T1.Col1, T1.Col2, T1.Col3, T2.Col7
		FROM Tabla1 T1 INNER JOIN Tabla2 T2 ON T1.Col1 = T2.Col1*/
		String consulta = "SELECT t_empleados.dni, t_empleados.nombre, t_empleados.apellidos, t_proyectos.id,t_proyectos.nombre "
				+ "FROM t_empleados INNER JOIN " + 
				"t_pro_emple ON t_empleados.dni = t_pro_emple.empleado INNER JOIN t_proyectos ON " + 
				"t_pro_emple.proyecto = t_proyectos.id;";
		try {
			
			Statement sentencia = conex.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = sentencia.executeQuery(consulta);
			
			ModeloTabla modelo = new ModeloTabla(rs);			
			return modelo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public DefaultListModel<String> listaEmpleados() {
		if (comprobarDB()) {
			DefaultListModel<String> empleados = new DefaultListModel<String>();
		
			Connection conex = getConexion();
			String consulta = "SELECT dni, nombre, apellidos FROM t_empleados";
			
			try {
				Statement st = conex.createStatement();
				ResultSet rs = st.executeQuery(consulta);
				
				while (rs.next()) {
					empleados.addElement(rs.getString(1) + " - " + rs.getString(2) + " " + rs.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					conex.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return empleados;
		} else {
			System.out.println("listaempleados (NO EXISTE)");
			return null;
		}
		
	}
	
	public DefaultListModel<String> listaProyectos() {
		if (comprobarDB()) {
			DefaultListModel<String> proyectos = new DefaultListModel<String>();
			Connection conex = getConexion();
			String consulta = "SELECT id,nombre FROM t_proyectos";
			
			try {
				Statement st = conex.createStatement();
				ResultSet rs = st.executeQuery(consulta);
				
				while (rs.next()) {
					proyectos.addElement(rs.getInt(1) + "  - " + rs.getString(2));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					conex.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return proyectos;
		}
		
		return null;
	}
	
	public DefaultListModel<String> listaResultado(String s) {
		DefaultListModel<String> resultado = new DefaultListModel<String>();
		Connection conex = getConexion();
		String consulta = "SELECT id, nombre FROM t_proyectos WHERE nombre LIKE \"%" + s + "%\"";
		String consulta2 = "SELECT dni, nombre,apellidos FROM t_empleados WHERE nombre LIKE \"%" + s + "%\" OR apellidos LIKE \"%" + s + "%\"";
		try {
			Statement st = conex.createStatement();
			ResultSet rs = st.executeQuery(consulta2);
			
			while (rs.next()) {
				//resultado.addElement(rs.getString(1) + " " + rs.getString(2) + " --- Empleado");
				resultado.addElement(rs.getString(1) + " - " + rs.getString(2) + " " + rs.getString(3) + " --- Empleado");
			}
			
			rs = st.executeQuery(consulta);
			
			while (rs.next()) {
				resultado.addElement(rs.getInt(1) + " - " + rs.getString(2) + " --- Proyecto");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

	public boolean asignarPro(int pro, String emp) {
		Connection conex = getConexion();
		PreparedStatement ps = null;
		String sql = "INSERT INTO t_pro_emple (proyecto, empleado) VALUES(?,?);";
		
		try {
			ps = conex.prepareStatement(sql);
			ps.setInt(1,pro);
			ps.setString(2, emp);

			ps.execute();
			JOptionPane.showMessageDialog(null, "La asignación se ha realizado satisfactoriamente.");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido realizar la asignación.");
			return false;
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void borrarAsig(String string, int parseInt) {
		Connection conex = getConexion();
		PreparedStatement ps = null;
		String sql = "DELETE FROM t_pro_emple WHERE empleado = \"" + string + "\" AND proyecto = " + parseInt + ";";
		
		try {
			ps = conex.prepareStatement(sql);

			ps.execute();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar el registro");
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void informeEmpleados(String path) {
		Connection conex = getConexion();
		try {	
			report = (JasperReport) JRLoader.loadObjectFromFile(path);
			reportFilled = JasperFillManager.fillReport(report, null, conex);
		} catch (JRException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void mostrarReporte() {
		
		viewer = new JasperViewer(reportFilled,false);
		viewer.setVisible(true);
	}
}