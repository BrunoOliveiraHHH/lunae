package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ArmaDAO;
import model.Arma;

public class ArmaDAOImpl implements ArmaDAO {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ArmaDAOImpl(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	@Override
	public boolean insertArma(Arma arma) throws SQLException {
		String sql = "INSERT INTO arma (Nome, Tipo) VALUES (?,?)";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, arma.getNome());
		statement.setString(2, arma.getTipo());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();

		return rowInserted;
	}

	@Override
	public boolean updateArma(Arma arma) throws SQLException {
		String sql = "UPDATE arma SET Nome = ?, Tipo = ?";
		sql += " WHERE ID_Arma = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, arma.getNome());
		statement.setString(2, arma.getTipo());
		statement.setInt(3, arma.getID_Arma());

		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();

		return rowUpdated;
	}

	@Override
	public boolean deleteArma(Arma arma) throws SQLException {
		String sql = "DELETE FROM arma WHERE ID_Arma = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, arma.getID_Arma());

		boolean rowDelete = statement.executeUpdate() > 0;
		statement.close();
		disconnect();

		return rowDelete;
	}

	@Override
	public List<Arma> listAllArma() throws SQLException {
		List<Arma> listArma = new ArrayList<>();

		String sql = "SELECT * FROM arma";

		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int id = resultSet.getInt("ID_Arma");
			String name = resultSet.getString("Nome");
			String type = resultSet.getString("Tipo");

			Arma arma = new Arma(id, name, type);

			listArma.add(arma);
		}
		
		resultSet.close();
		statement.close();

		return listArma;
	}

	@Override
	public Arma getArma(int ID_Arma) throws SQLException {
		Arma arma = null;
		String sql = "SELECT * FROM book WHERE book_id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, ID_Arma);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String name = resultSet.getString("Nome");
			String type = resultSet.getString("Tipo");
			
			arma = new Arma(ID_Arma, name, type);
		}
		
		resultSet.close();
		statement.close();
		
		return arma;
	}

}
