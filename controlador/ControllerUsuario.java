package org.hospital.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hospital.modelo.Usuario;
import org.hospital.modelo.UsuarioDAO;
import org.hospital.modelo.Md5;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControllerUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;
	int intentos = 0; 
	String sessionId;

	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		usuarioDAO = new UsuarioDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String action = request.getServletPath();
		String action = request.getParameter("accion");
		System.out.println ("Svlet Usr Action: "+action);
		try {
			switch (action) {
			case "login":
				login(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			case "verificaUsuario":
				verificaUsuario(request, response);
				break;
			case "new":
				showNewForm(request, response);
				break;
			case "insert":
				insertUsuario(request, response);
				break;
			case "delete":
				deleteUsuario(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			case "update":
				updateUsuario(request, response);
				break;
			case "list":
				listUsuario(request, response);
				break;
			default:
				login(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println ("Login");
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession sesion = request.getSession();
		System.out.println ("Logout");
		sesion.invalidate();
		intentos = 0;
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}
	
	private void verificaUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession sesion = request.getSession();
System.out.println ("Intentos: "+intentos);
		String usuario = request.getParameter("usuario");
		String password = Md5.Encriptar(request.getParameter("password"));
		//System.out.println ("Hola2 password: "+password);
		intentos = intentos + 1;
		sesion.setAttribute("intentos", intentos);
		if (sessionId == null){
			System.out.println ("Session es NULO");
			sessionId=request.getSession().getId();
		}
	
		Usuario existeUsuario = usuarioDAO.getUsuario(usuario,password);
		if (existeUsuario != null){
			sesion.setAttribute("session", usuario);
			System.out.println ("Session: "+sesion.getAttribute("session"));
			//response.sendRedirect("list");
			if (existeUsuario.getTipo().equals("ADMINISTRADOR")){
				RequestDispatcher dispatcher = request.getRequestDispatcher("MenuAdministrador.jsp");
				dispatcher.forward(request, response);
			}
			else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("MenuHospital.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			//validacion numero de intentos
System.out.println ("Valida if Intentos: "+intentos);
System.out.println ("SESSION ID: "+sessionId);
			if(!sessionId.equals(request.getSession().getId())){
				System.out.println ("cambia SESSION ID");
				intentos = 1;
				sesion.setAttribute("intentos", intentos);
				sessionId=null;
System.out.println ("Valida if Intentos: "+intentos);
System.out.println ("SESSION ID: "+sessionId);
			}
			if(intentos < 3){
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginError.jsp");
				dispatcher.forward(request, response);
			}
			else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginBloqueado.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
	
	private void listUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Usuario> listUsuario = usuarioDAO.listAllUsuarios();
		request.setAttribute("listUsuario", listUsuario);
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-usuario/UsuarioList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-usuario/UsuarioForm.jsp");
		dispatcher.forward(request, response); 
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Usuario existeUsuario = usuarioDAO.getUsuario(id);
		//Desencriptar pwd
		existeUsuario.setPassword(Md5.Desencriptar(existeUsuario.getPassword()));
		RequestDispatcher dispatcher = request.getRequestDispatcher("vista-usuario/UsuarioForm.jsp");
		request.setAttribute("usuario", existeUsuario);
		dispatcher.forward(request, response);
	}

	private void insertUsuario(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String nombres = request.getParameter("nombres");
		String apellidos = request.getParameter("apellidos");
		String usuario = request.getParameter("usuario");
		String email = request.getParameter("email");
		String password = Md5.Encriptar(request.getParameter("password"));
		String estado = request.getParameter("estado");
		String tipo = request.getParameter("tipo");

		Usuario newUsuario = new Usuario(nombres, apellidos, usuario, email, password, estado, tipo);
		usuarioDAO.insertUsuario(newUsuario);
		response.sendRedirect("usuario?accion=list");
	}

	private void updateUsuario(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombres = request.getParameter("nombres");
		String apellidos = request.getParameter("apellidos");
		String usuario = request.getParameter("usuario");
		String email = request.getParameter("email");
		String password = Md5.Encriptar(request.getParameter("password"));
		String estado = request.getParameter("estado");
		String tipo = request.getParameter("tipo");

		Usuario updUsuario = new Usuario(id, nombres, apellidos, usuario, email, password, estado, tipo);
		usuarioDAO.updateUsuario(updUsuario);
		response.sendRedirect("usuario?accion=list");
	}

	private void deleteUsuario(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Usuario usuario = new Usuario(id);
		usuarioDAO.deleteUsuario(usuario);
		response.sendRedirect("usuario?accion=list");

	}

}
