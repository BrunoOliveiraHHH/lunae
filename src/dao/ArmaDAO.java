package dao;

import java.sql.SQLException;
import java.util.List;

import model.Arma;

public interface ArmaDAO {
	
	public boolean insertArma(Arma arma) throws SQLException;
	
	public boolean updateArma(Arma arma) throws SQLException;
	
	public boolean deleteArma(Arma arma) throws SQLException;
	
	public List<Arma> listAllArma() throws SQLException;
	
	public Arma getArma(int ID_Arma) throws SQLException;

}
