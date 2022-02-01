package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionUtil_HikariCP;
import dao.SkakaonicaDAO;
import model.Skakaonica;

public class SkakaonicaDAOImpl implements SkakaonicaDAO {

	@Override
	public int count() throws SQLException {
		String query = "select count(*) from skakaonica";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return -1;
			}
		}
	}

	@Override
	public boolean delete(Skakaonica entity) throws SQLException {
		return deleteById(entity.getId());
	}

	@Override
	public int deleteAll() throws SQLException {
		String query = "delete from skakaonica";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			int rowsAfffected = preparedStatement.executeUpdate();
			return rowsAfffected;
		}
	}
	
	//samo se ne skace sa rsg2 
	@Override
	public boolean deleteById(String id) throws SQLException {
		String query = "delete from skakaonica where idsa = ?";
		for(int i = id.length(); i < 6; i++){
			id += " ";
		}
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}
//TODO ULEPSAJ OVO
	@Override
	public boolean existsById(String id) throws SQLException {
//		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
//			return existsByIdTransactional(connection, id);
//		}
		String query = "select idsa, nazivsa, duzinasa, tipsa, idd from skakaonica";
		List<Skakaonica> skakaonicaList = new ArrayList<Skakaonica>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Skakaonica skakaonica = new Skakaonica(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getString(4), resultSet.getString(5));
				skakaonicaList.add(skakaonica);
			}

		}

		for(Skakaonica s: skakaonicaList){
			//System.out.println(id + "|" + id.getClass() + " " + s.getId() + "|" + s.getId().getClass());
			if(id.equals(s.getId().trim())){
				return true;
			}
		}
		return false;
	}

	// connection is a parameter because this method is used in a transaction (see
	// saveAll method)
	private boolean existsByIdTransactional(Connection connection, String id) throws SQLException {
		String query = "select idsa, nazivsa, duzinasa, tipsa, idd from skakaonica where idsa = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			for(int i = id.length(); i < 6; i++){
				id += " "; 
			}
			preparedStatement.setString(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}
	
	@Override
	public Iterable<Skakaonica> findAll() throws SQLException {
		String query = "select idsa, nazivsa, duzinasa, tipsa, idd from skakaonica";
		List<Skakaonica> skakaonicaList = new ArrayList<Skakaonica>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Skakaonica skakaonica = new Skakaonica(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getString(4), resultSet.getString(5));
				skakaonicaList.add(skakaonica);
			}

		}
		return skakaonicaList;
	}

	@Override
	public Iterable<Skakaonica> findAllById(Iterable<String> ids) throws SQLException {
		List<Skakaonica> skakaonicaList = new ArrayList<>();

		StringBuilder stringBuilder = new StringBuilder();

		String queryBegin = "select idsa, nazivsa, duzinasa, tipsa, idd from skakaonica where idsa in(";
		stringBuilder.append(queryBegin);

		for (@SuppressWarnings("unused")
		String id : ids) {
			stringBuilder.append("?,");
		}

		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // delete last ','
		stringBuilder.append(")");

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());) {

			int i = 0;
			for (String id : ids) {
				preparedStatement.setString(++i, id);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					skakaonicaList.add(new Skakaonica(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
							resultSet.getString(4), resultSet.getString(5)));
				}
			}
		}

		return skakaonicaList;
	}

	@Override
	public Skakaonica findById(String id) throws SQLException {
		String query = "select idsa, nazivsa, duzinasa, tipsa, idd from skakaonica where idsa = ?";
		Skakaonica skakaonica = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			for(int i = id.length(); i < 6; i++){
				id += " ";
			}
			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					skakaonica = new Skakaonica(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
							resultSet.getString(4), resultSet.getString(5));
				}
			}
		}

		return skakaonica;
	}

	@Override
	public boolean save(Skakaonica entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<Skakaonica> entities) throws SQLException {
		int rowsSaved = 0;
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			connection.setAutoCommit(false); // transaction start

			// insert or update every skakaonica
			for (Skakaonica entity : entities) {
				boolean success = saveTransactional(connection, entity); // changes are visible only to current connection
				if (success) rowsSaved++;
			}

			connection.commit(); // transaction ends successfully, changes are now visible to other connections as well
		}
		
		return rowsSaved;
	}

	// used by save and saveAll
	private boolean saveTransactional(Connection connection, Skakaonica entity) throws SQLException {
		String insertCommand = "insert into skakaonica (nazivsa, duzinasa, tipsa, idd, idsa) values (?, ?, ?, ?, ?)";
		String updateCommand = "update skakaonica set nazivsa = ?, duzinasa = ?, tipsa = ?, idd = ? where idsa = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
			existsByIdTransactional(connection, entity.getId()) ? updateCommand : insertCommand)) {
			String id = entity.getId();
			for(int i = id.length(); i < 6; i++){
				id += " "; 
			}
			preparedStatement.setString(1, entity.getNaziv());
			preparedStatement.setInt(2, entity.getDuzina());
			preparedStatement.setString(3, entity.getTip());
			preparedStatement.setString(4, entity.getIdDrzave());
			preparedStatement.setString(5, id);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}
	
	@Override
	public Iterable<Skakaonica> nadjiUIntervalu(int a, int b) throws SQLException{
		List<Skakaonica> skakaonicaList = new ArrayList<>();
		String query = "select * from skakaonica where duzinasa >= ? and duzinasa <= ? order by duzinasa";
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			if(a>b){
				int t = b;
				b = a;
				a = t;
			}
			preparedStatement.setInt(1, a);
			preparedStatement.setInt(2, b);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					while(resultSet.next()){

						skakaonicaList.add(new Skakaonica(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
															resultSet.getString(4), resultSet.getString(5)));
					}
				}
			}
		}
		
		return skakaonicaList;
	}
}
