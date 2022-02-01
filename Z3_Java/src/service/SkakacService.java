package service;

import java.sql.SQLException;

import dao.SkakacDAO;
import dao.impl.SkakacDAOImpl;
import model.Skakac;

public class SkakacService {
	private static final SkakacDAO skakacDAO = new SkakacDAOImpl();
	public Skakac findById(Integer id) throws SQLException {
		return skakacDAO.findById(id);
	}
}
