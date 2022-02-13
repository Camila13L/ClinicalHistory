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
public class UsuarioDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public UsuarioDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	public boolean insertUsuario(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO usuario (nombres, apellidos, usuario, email, password, estado, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, usuario.getNombres());
		statement.setString(2, usuario.getApellidos());
		statement.setString(3, usuario.getUsuario());
		statement.setString(4, usuario.getEmail());
		statement.setString(5, usuario.getPassword());
		statement.setString(6, usuario.getEstado());
		statement.setString(7, usuario.getTipo());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Usuario> listAllUsuarios() throws SQLException {
		List<Usuario> listUsuario = new ArrayList<>();
		
		String sql = "SELECT * FROM usuario";
		
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String nombres = resultSet.getString("nombres");
			String apellidos = resultSet.getString("apellidos");
			String usuario = resultSet.getString("usuario");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			String estado = resultSet.getString("estado");
			String tipo = resultSet.getString("tipo");
			
			Usuario usuarios = new Usuario(id, nombres, apellidos, usuario, email, password, estado, tipo);
			listUsuario.add(usuarios);
		}
		
		resultSet.close();
		statement.close();
		
		disconnect();
		
		return listUsuario;
	}
	
	public boolean deleteUsuario(Usuario usuario) throws SQLException {
		String sql = "DELETE FROM usuario where id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, usuario.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}
	
	public boolean updateUsuario(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuario SET nombres = ?, apellidos = ?, usuario = ?, email = ?, password = ?, estado = ?, tipo = ?";
		sql += " WHERE id = ?";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, usuario.getNombres());
		statement.setString(2, usuario.getApellidos());
		statement.setString(3, usuario.getUsuario());
		statement.setString(4, usuario.getEmail());
		statement.setString(5, usuario.getPassword());
		statement.setString(6, usuario.getEstado());
		statement.setString(7, usuario.getTipo());
		statement.setInt(8, usuario.getId());

		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;		
	}
	
	public Usuario getUsuario(int id) throws SQLException {
		Usuario usuarios = null;
		String sql = "SELECT * FROM usuario WHERE id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String nombres = resultSet.getString("nombres");
			String apellidos = resultSet.getString("apellidos");
			String usuario = resultSet.getString("usuario");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			String estado = resultSet.getString("estado");
			String tipo = resultSet.getString("tipo");
		
			usuarios = new Usuario(id, nombres, apellidos, usuario, email, password, estado, tipo);
		}
		
		resultSet.close();
		statement.close();
		
		return usuarios;
	}
	
	public Usuario getUsuario(String pUsuario, String pPassword) throws SQLException {
		Usuario usuarios = null;
		String sql = "SELECT * FROM usuario WHERE usuario = ? AND password = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, pUsuario);
		statement.setString(2, pPassword);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			Integer id = resultSet.getInt("id");
			String nombres = resultSet.getString("nombres");
			String apellidos = resultSet.getString("apellidos");
			String usuario = resultSet.getString("usuario");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			String estado = resultSet.getString("estado");
			String tipo = resultSet.getString("tipo");

			usuarios = new Usuario(id, nombres, apellidos, usuario, email, password, estado, tipo);
		}
		
		resultSet.close();
		statement.close();
		
		return usuarios;
	}
}
