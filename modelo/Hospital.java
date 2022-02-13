package org.hospital.modelo;

/**
 * Book.java
 * This is a model class represents a book entity
 * @author www.codejava.net
 *
 */
public class Hospital {
	protected int id;
	protected String piso;
	protected String sala;
	protected String cama;
	protected Integer paciente_id;
	protected String estado;

	public Hospital() {
	}

	public Hospital(int id) {
		this.id = id;
	}

	public Hospital(int id, String piso, String sala, String cama, Integer paciente_id, String estado) {
		this(piso, sala, cama, paciente_id, estado);
		this.id = id;
	}
	
	public Hospital(String piso, String sala, String cama, Integer paciente_id, String estado) {
		this.piso = piso;
		this.sala = sala;
		this.cama = cama;
		this.paciente_id = paciente_id;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getCama() {
		return cama;
	}

	public void setCama(String cama) {
		this.cama = cama;
	}

	public Integer getPaciente_id() {
		return paciente_id;
	}

	public void setPaciente_id(Integer paciente_id) {
		this.paciente_id = paciente_id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	


}
