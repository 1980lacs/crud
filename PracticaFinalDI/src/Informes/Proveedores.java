package Informes;

public class Proveedores {

	private int id;
	private String apellidos;
	private String nombre;
	private String dni;
	private String ciudad;
	private String login;
	
	public Proveedores(int id, String apellidos, String nombre, String dni, String ciudad) {
		super();
		this.id = id;
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.dni = dni;
		this.ciudad = ciudad;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
