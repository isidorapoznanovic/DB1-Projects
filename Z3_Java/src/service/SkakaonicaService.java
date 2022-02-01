package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.SkakaonicaDAO;
import dao.impl.SkakaonicaDAOImpl;
import model.Skakaonica;

public class SkakaonicaService {
	private static final SkakaonicaDAO skakaonicaDAO = new SkakaonicaDAOImpl();
	
	public int count() throws SQLException {
		return skakaonicaDAO.count();
	}

	public boolean delete(Skakaonica entity) throws SQLException {
		return skakaonicaDAO.delete(entity);
	}
	
	public int deleteAll() throws SQLException {
		return skakaonicaDAO.deleteAll();
	}

	public boolean deleteById(String id) throws SQLException {
		return skakaonicaDAO.deleteById(id);
	}
	
	public boolean existsById(String id) throws SQLException {
		return skakaonicaDAO.existsById(id);
	}
	
	public ArrayList<Skakaonica> findAll() throws SQLException {
		return (ArrayList<Skakaonica>) skakaonicaDAO.findAll();
	}
	
	public ArrayList<Skakaonica> findAllById(Iterable<String> ids) throws SQLException {
		return (ArrayList<Skakaonica>) skakaonicaDAO.findAllById(ids);
	}

	public Skakaonica findById(String id) throws SQLException {
		return skakaonicaDAO.findById(id);
	}

	public boolean save(Skakaonica entity) throws SQLException {
		return skakaonicaDAO.save(entity);
	}
	
	public int saveAll(Iterable<Skakaonica> entities) throws SQLException {
		return skakaonicaDAO.saveAll(entities);
	}
	
	public ArrayList<Skakaonica> nadjiUIntervalu(int a, int b) throws SQLException{
		return (ArrayList<Skakaonica>) skakaonicaDAO.nadjiUIntervalu(a, b);
	}
}
