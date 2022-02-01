package dao;

import java.sql.SQLException;

import dto.complexquery3.TehnikalijeTransakcijeDTO;
import model.Skok;

public interface SkokDAO extends CRUDDao<Skok, String> {
	
	public Iterable<Skok> uDrzaviskaceSKizDrzave(String idDrzave) throws SQLException;

	public TehnikalijeTransakcijeDTO transakcija(TehnikalijeTransakcijeDTO dto)throws SQLException;
}
