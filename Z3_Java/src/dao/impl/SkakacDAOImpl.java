package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionUtil_HikariCP;
import dao.SkakacDAO;
import model.Skakac;

public class SkakacDAOImpl implements SkakacDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Skakac entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Skakac> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Skakac> findAllById(Iterable<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Skakac findById(Integer id) throws SQLException {
		String query = "select idsc, imesc, przsc, idd, titule, pbsc from skakac where idsc = ?";
		Skakac skakac = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					skakac = new Skakac(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getInt(5), resultSet.getFloat(6));
				}
			}
		}

		return skakac;
	}

	@Override
	public boolean save(Skakac entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveAll(Iterable<Skakac> entities) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
