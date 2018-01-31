package Informes;

import java.sql.Connection;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public abstract class abstractReport {

	private static JasperReport report;
	private static JasperPrint reportFilled;
	private static JasperViewer viewer;
	
	public static void crearReporte(Connection con, String path) {
		
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(path);
			reportFilled = JasperFillManager.fillReport(report, null, con);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void crearReporte(List<Proveedores> colection, String ruta) {
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile(ruta);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(colection);
			reportFilled = JasperFillManager.fillReport(report, null, dataSource);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void mostrarReporte() {
		
		viewer = new JasperViewer(reportFilled);
		viewer.setVisible(true);
	}
	
	public static void exportarPDF(String path) {
		try {
			JasperExportManager.exportReportToPdfFile(reportFilled,path);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
