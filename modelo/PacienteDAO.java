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
public class PacienteDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public PacienteDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	public boolean insertPaciente(Paciente paciente) throws SQLException {
		String sql = "INSERT INTO paciente (nombres, apellidos, direccion, email, telefonos, estado) VALUES (?, ?, ?, ?, ?, ?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, paciente.getNombres());
		statement.setString(2, paciente.getApellidos());
		statement.setString(3, paciente.getDireccion());
		statement.setString(4, paciente.getEmail());
		statement.setString(5, paciente.getTelefonos());
		statement.setString(6, paciente.getEstado());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Paciente> listAllPacientes() throws SQLException {
		List<Paciente> listPaciente = new ArrayList<>();
		
		String sql = "SELECT * FROM paciente";
		
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String nombres = resultSet.getString("nombres");
			String apellidos = resultSet.getString("apellidos");
			String direccion = resultSet.getString("direccion");
			String email = resultSet.getString("email");
			String telefonos = resultSet.getString("telefonos");
			String estado = resultSet.getString("estado");
			
			Paciente paciente = new Paciente(id, nombres, apellidos, direccion, email, telefonos, estado);
			listPaciente.add(paciente);
		}
		
		resultSet.close();
		statement.close();
		
		disconnect();
		
		return listPaciente;
	}
	
	public boolean deletePaciente(Paciente paciente) throws SQLException {
		String sql = "DELETE FROM paciente where id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, paciente.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}
	
	public boolean updatePaciente(Paciente paciente) throws SQLException {
		String sql = "UPDATE paciente SET nombres = ?, apellidos = ?, direccion = ?, email = ?, telefonos = ?, estado = ?";
		sql += " WHERE id = ?";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, paciente.getNombres());
		statement.setString(2, paciente.getApellidos());
		statement.setString(3, paciente.getDireccion());
		statement.setString(4, paciente.getEmail());
		statement.setString(5, paciente.getTelefonos());
		statement.setString(6, paciente.getEstado());
		statement.setInt(7, paciente.getId());
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;		
	}
	
	public Paciente getPaciente(int id) throws SQLException {
		Paciente paciente = null;
		String sql = "SELECT * FROM paciente WHERE id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String nombres = resultSet.getString("nombres");
			String apellidos = resultSet.getString("apellidos");
			String direccion = resultSet.getString("direccion");
			String email = resultSet.getString("email");
			String telefonos = resultSet.getString("telefonos");
			String estado = resultSet.getString("estado");
		
			paciente = new Paciente(id, nombres, apellidos, direccion, email, telefonos, estado);
		}
		
		resultSet.close();
		statement.close();
		
		return paciente;
	}
	
}
