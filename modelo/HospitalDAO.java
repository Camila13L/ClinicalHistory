package org.hospital.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractDAO.java
 * This DAO class provides CRUD database operations for the table book
 * in the database.
 * @author www.codejava.net
 *
 */
public class HospitalDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public HospitalDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(
										jdbcURL, jdbcUsername, jdbcPassword);
		}
	}
	
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public boolean insertHospital(Hospital hospital) throws SQLException {
		String sql = "INSERT INTO hospital (piso, sala, cama, paciente_id, estado) VALUES (?, ?, ?, ?, ?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, hospital.getPiso());
		statement.setString(2, hospital.getSala());
		statement.setString(3, hospital.getCama());
		statement.setInt(4, hospital.getPaciente_id());
		statement.setString(5, hospital.getEstado());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Hospital> listAllHospitales() throws SQLException {
		List<Hospital> listHospital = new ArrayList<>();
		
		String sql = "SELECT * FROM hospital";
		
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String piso = resultSet.getString("piso");
			String sala = resultSet.getString("sala");
			String cama = resultSet.getString("cama");
			Integer paciente_id = resultSet.getInt("paciente_id");
			String estado = resultSet.getString("estado");
			
			Hospital hospital = new Hospital(id, piso, sala, cama, paciente_id, estado);
			listHospital.add(hospital);
		}
		
		resultSet.close();
		statement.close();
		
		disconnect();
		
		return listHospital;
	}
	
	public boolean deleteHospital(Hospital hospital) throws SQLException {
		String sql = "DELETE FROM hospital where id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, hospital.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}
	
	public boolean updateHospital(Hospital hospital) throws SQLException {
		String sql = "UPDATE hospital SET piso = ?, sala = ?, cama = ?, paciente_id = ?, estado = ?";
		sql += " WHERE id = ?";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, hospital.getPiso());
		statement.setString(2, hospital.getSala());
		statement.setString(3, hospital.getCama());
		statement.setInt(4, hospital.getPaciente_id());
		statement.setString(5, hospital.getEstado());
		statement.setInt(6, hospital.getId());
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;		
	}
	
	public Hospital getHospital(int id) throws SQLException {
		Hospital hospital = null;
		String sql = "SELECT * FROM hospital WHERE id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String piso = resultSet.getString("piso");
			String sala = resultSet.getString("sala");
			String cama = resultSet.getString("cama");
			Integer paciente_id = resultSet.getInt("paciente_id");
			String estado = resultSet.getString("estado");
		
			hospital = new Hospital(id, piso, sala, cama, paciente_id, estado);
		}
		
		resultSet.close();
		statement.close();
		
		return hospital;
	}
	
	public Paciente getPaciente(int pacienteId) throws SQLException {
		Paciente pacientes = null;
		String sql = "SELECT * FROM paciente WHERE id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, pacienteId);

		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			Integer id = resultSet.getInt("id");
			String nombres = resultSet.getString("nombres");
			String apellidos = resultSet.getString("apellidos");
			String direccion = resultSet.getString("direccion");
			String email = resultSet.getString("email");
			String telefonos = resultSet.getString("telefonos");
			String estado = resultSet.getString("estado");

			pacientes = new Paciente(id, nombres, apellidos, direccion, email, telefonos, estado);
		}
		
		resultSet.close();
		statement.close();
		
		return pacientes;
	}
}
