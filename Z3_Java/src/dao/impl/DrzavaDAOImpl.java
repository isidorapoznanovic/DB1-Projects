package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionUtil_HikariCP;
import dao.DrzavaDAO;
import model.Drzava;

public class DrzavaDAOImpl implements DrzavaDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Drzava entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Drzava> findAll() throws SQLException {
		String query = "select idd, nazivd from drzava";
		List<Drzava> drzavaList = new ArrayList<Drzava>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Drzava skakaonica = new Drzava(resultSet.getString(1), resultSet.getString(2));
				drzavaList.add(skakaonica);
			}

		}
		return drzavaList;
	}

	@Override
	public Iterable<Drzava> findAllById(Iterable<String> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drzava findById(String id) throws SQLException {
		String query = "select idd, nazivd from drzava where idd = ?";
		Drzava drzava = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					drzava = new Drzava(resultSet.getString(1), resultSet.getString(2));
				}
			}
		}

		return drzava;
	}

	@Override
	public boolean save(Drzava entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveAll(Iterable<Drzava> entities) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
