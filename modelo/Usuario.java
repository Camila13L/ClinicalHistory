package org.hospital.modelo;

/**
 * Book.java
 * This is a model class represents a book entity
 * @author www.codejava.net
 *
 */
public class Usuario {
	protected int id;
	protected String nombres;
	protected String apellidos;
	protected String usuario;
	protected String email;
	protected String password;
	protected String estado;
	protected String tipo;

	public Usuario() {
	}

	public Usuario(int id) {
		this.id = id;
	}

	public Usuario(int id, String nombres, String apellidos, String usuario, String email, String password, String estado, String tipo) {
		this(nombres, apellidos, usuario, email, password, estado, tipo);
		this.id = id;
	}
	
	public Usuario(String nombres, String apellidos, String usuario, String email, String password, String estado, String tipo) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.email = email;
		this.password = password;
		this.estado = estado;
		this.tipo = tipo;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
