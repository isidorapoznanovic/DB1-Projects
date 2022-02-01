package dao;

import java.sql.SQLException;

import model.Skakaonica;

public interface SkakaonicaDAO extends CRUDDao<Skakaonica, String> {
	
	public Iterable<Skakaonica> nadjiUIntervalu(int a, int b) throws SQLException;
}
