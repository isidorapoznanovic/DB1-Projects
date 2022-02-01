package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DrzavaDAO;
import dao.impl.DrzavaDAOImpl;
import model.Drzava;

public class DrzavaService {
	private static final DrzavaDAO drzavaDAO = new DrzavaDAOImpl();
	
	public ArrayList<Drzava> findAll() throws SQLException {
		return (ArrayList<Drzava>) drzavaDAO.findAll();
	}
	
	public Drzava findById(String id) throws SQLException {
		return drzavaDAO.findById(id);
	}
}
