package org.hospital.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hospital.modelo.Hospital;
import org.hospital.modelo.HospitalDAO;


/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */

public class ControllerHospital extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//private UsuarioDAO usuarioDAO;
	private HospitalDAO hospitalDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		hospitalDAO = new HospitalDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String action = request.getServletPath();
		String action = request.getParameter("accion");
		System.out.println ("Svlet Hosp Action: "+action);
		try {
			switch (action) {
			case "new":
				showNewForm(request, response);
				break;
			case "insert":
				insertHospital(request, response);
				break;
			case "delete":
				deleteHospital(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			case "update":
				updateHospital(request, response);
				break;
			case "list":
				listHospital(request, response);
				break;
			default:
				listHospital(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	
	private void listHospital(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		//System.out.println ("Hospital");
		List<Hospital> listHospital = hospitalDAO.listAllHospitales();
		request.setAttribute("listHospital", listHospital);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-hospital/HospitalList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-hospital/HospitalForm.jsp");
		dispatcher.forward(request, response); 
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Hospital existeHospital = hospitalDAO.getHospital(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-hospital/HospitalForm.jsp");
		request.setAttribute("hospital", existeHospital);
		dispatcher.forward(request, response);
	}

	private void insertHospital(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String piso = request.getParameter("piso");
		String sala = request.getParameter("sala");
		String cama = request.getParameter("cama");
		Integer paciente_id = 0; //Inicializa en 0 al paciente
		String estado = request.getParameter("estado");
		
		Hospital newHospital = new Hospital(piso, sala, cama, paciente_id, estado);
		hospitalDAO.insertHospital(newHospital);
		response.sendRedirect("hospital?accion=list");
	}

	private void updateHospital(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String piso = request.getParameter("piso");
		String sala = request.getParameter("sala");
		String cama = request.getParameter("cama");
		Integer paciente_id = Integer.parseInt(request.getParameter("email"));
		String estado = request.getParameter("estado");

		Hospital updHospital = new Hospital(id, piso, sala, cama, paciente_id, estado);
		hospitalDAO.updateHospital(updHospital);
		response.sendRedirect("hospital?accion=list");
	}

	private void deleteHospital(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Hospital hospital = new Hospital(id);
		hospitalDAO.deleteHospital(hospital);
		response.sendRedirect("hospital?accion=list");

	}

}
