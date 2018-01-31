package modelo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel
{

	private ResultSet resultado;
    private ResultSetMetaData metaResultado;
    
    public ModeloTabla(ResultSet resultado){
        
        
        try {
            this.resultado = resultado;
            this.metaResultado = this.resultado.getMetaData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public ModeloTabla() {
		
	}
	@Override
    public int getRowCount() {
        try {
            resultado.last();
            return resultado.getRow();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //return metaResultado.getRow();
        return 0;
    }

    @Override
    public int getColumnCount() {
        try {
            
            return metaResultado.getColumnCount();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        try {
            resultado.absolute(rowIndex + 1);
            return resultado.getObject(columnIndex + 1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
        
    }
    
    public String getColumnName(int c){
        try {
            return metaResultado.getColumnName(c+1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return "";
    }
    
    public void removeRow() {
    	try {
			resultado.deleteRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String[] removeRowEsp() {
    	String datos[] = new String[2];
    	try {
			datos[0] = resultado.getString(1);
			datos[1] = String.valueOf(resultado.getInt(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return datos;
    }
    
	public String[] updateRowEmpleado() {
		String datos[] = new String[4];
		try {
			datos[0] = resultado.getString(2);
			datos[1] = resultado.getString(3);
			datos[2] = resultado.getString(1);
			datos[3] = String.valueOf(resultado.getDate(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	public String[] updateRowProyecto() {
		String datos[] = new String[4];
		try {
			datos[0] = resultado.getString(2);
			datos[1] = String.valueOf(resultado.getDate(3));
			datos[2] = String.valueOf(resultado.getDate(4));
			datos[3] = resultado.getString(5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	public int getProyecto() {
		try {
			int id = resultado.getInt(1);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public String getEmpleado() {
		try {
			String dni = resultado.getString(1);
			return dni;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
}
