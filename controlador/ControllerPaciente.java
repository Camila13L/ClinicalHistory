package org.hospital.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hospital.modelo.Paciente;
import org.hospital.modelo.PacienteDAO;


/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */

public class ControllerPaciente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//private UsuarioDAO usuarioDAO;
	private PacienteDAO pacienteDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		pacienteDAO = new PacienteDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String action = request.getServletPath();
		String action = request.getParameter("accion");
		System.out.println ("Svlet Pacnt Action: "+action);
		try {
			switch (action) {
			case "new":
				showNewForm(request, response);
				break;
			case "insert":
				insertPaciente(request, response);
				break;
			case "delete":
				deletePaciente(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			case "update":
				updatePaciente(request, response);
				break;
			case "list":
				listPaciente(request, response);
				break;
			default:
				listPaciente(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	
	private void listPaciente(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		//System.out.println ("Hospital");
		List<Paciente> listPaciente = pacienteDAO.listAllPacientes();
		request.setAttribute("listPaciente", listPaciente);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-paciente/PacienteList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-paciente/PacienteForm.jsp");
		dispatcher.forward(request, response); 
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Paciente existePaciente = pacienteDAO.getPaciente(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-paciente/PacienteForm.jsp");
		request.setAttribute("paciente", existePaciente);
		dispatcher.forward(request, response);
	}

	private void insertPaciente(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String nombres = request.getParameter("nombres");
		String apellidos = request.getParameter("apellidos");
		String direccion = request.getParameter("direccion");
		String email = request.getParameter("email");
		String telefonos = request.getParameter("telefonos");
		String estado = request.getParameter("estado");
		
		Paciente newPaciente = new Paciente(nombres, apellidos, direccion, email, telefonos, estado);
		pacienteDAO.insertPaciente(newPaciente);
		response.sendRedirect("paciente?accion=list");
	}

	private void updatePaciente(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombres = request.getParameter("nombres");
		String apellidos = request.getParameter("apellidos");
		String direccion = request.getParameter("direccion");
		String email = request.getParameter("email");
		String telefonos = request.getParameter("telefonos");
		String estado = request.getParameter("estado");

		Paciente updPaciente = new Paciente(id, nombres, apellidos, direccion, email, telefonos, estado);
		pacienteDAO.updatePaciente(updPaciente);
		response.sendRedirect("paciente?accion=list");
	}

	private void deletePaciente(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Paciente paciente = new Paciente(id);
		pacienteDAO.deletePaciente(paciente);
		response.sendRedirect("paciente?accion=list");

	}

}
