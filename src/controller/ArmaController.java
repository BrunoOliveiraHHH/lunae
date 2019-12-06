package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ArmaDAO;
import dao.impl.ArmaDAOImpl;
import model.Arma;

public class ArmaController extends HttpServlet {

	private static final long serialVersionUID = -8158049943501608112L;
	private ArmaDAO armaDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		armaDAO = new ArmaDAOImpl(jdbcURL, jdbcUsername, jdbcPassword);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertArma(request, response);
				break;
			case "/delete":
				deleteArma(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateArma(request, response);
				break;
			default:
				listArma(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listArma(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Arma> listArma = armaDAO.listAllArma();
		request.setAttribute("listArma", listArma);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
		dispatcher.forward(request, response);
		
	}

	private void updateArma(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String tipo = request.getParameter("tipo");

		Arma arma = new Arma(id, nome, tipo);
		armaDAO.updateArma(arma);
		response.sendRedirect("list");
		
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Arma existingArma = armaDAO.getArma(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		request.setAttribute("arma", existingArma);
		dispatcher.forward(request, response);
		
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		dispatcher.forward(request, response);
	}

	private void insertArma(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		String nome = request.getParameter("nome");
		String tipo = request.getParameter("tipo");

		Arma newArma = new Arma(nome, tipo);
		armaDAO.insertArma(newArma);
		response.sendRedirect("list");

	}

	private void deleteArma(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		Arma arma = new Arma(id);
		armaDAO.deleteArma(arma);
		response.sendRedirect("list");

	}

}
