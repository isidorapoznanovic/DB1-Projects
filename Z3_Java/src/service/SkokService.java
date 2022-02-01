package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.SkokDAO;
import dao.impl.SkokDAOImpl;
import dto.complexquery3.TehnikalijeTransakcijeDTO;
import model.Skok;

public class SkokService {
	private static final SkokDAO skokDAO = new SkokDAOImpl();
	
	public ArrayList<Skok> findAll() throws SQLException {
		return (ArrayList<Skok>) skokDAO.findAll();
	}
	
	public ArrayList<Skok> uDrzaviskaceSKizDrzave(String idDrzave) throws SQLException {
		return  (ArrayList<Skok>) skokDAO.uDrzaviskaceSKizDrzave(idDrzave);
	}
	
	public TehnikalijeTransakcijeDTO transakcija(TehnikalijeTransakcijeDTO dto) throws SQLException{
		return skokDAO.transakcija(dto);
	}
		
}
