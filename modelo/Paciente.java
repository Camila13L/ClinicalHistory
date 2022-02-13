package org.hospital.modelo;

/**
 * Book.java
 * This is a model class represents a book entity
 * @author www.codejava.net
 *
 */
public class Paciente {
	protected int id;
	protected String nombres;
	protected String apellidos;
	protected String direccion;
	protected String email;
	protected String telefonos;
	protected String estado;

	public Paciente() {
	}

	public Paciente(int id) {
		this.id = id;
	}

	public Paciente(int id, String nombres, String apellidos, String direccion, String email, String telefonos, String estado) {
		this(nombres, apellidos, direccion, email, telefonos, estado);
		this.id = id;
	}
	
	public Paciente(String nombres, String apellidos, String direccion, String email, String telefonos, String estado) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.email = email;
		this.telefonos = telefonos;
		this.estado = estado;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
