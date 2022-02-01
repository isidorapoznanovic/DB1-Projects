package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionUtil_HikariCP;
import dao.SkokDAO;
import dto.complexquery3.TehnikalijeTransakcijeDTO;
import model.Skakac;
import model.Skok;

public class SkokDAOImpl implements SkokDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Skok entity) throws SQLException {
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
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return existsByIdTransactional(connection, id);
		}
	}

	// connection is a parameter because this method is used in a transaction (see
		// saveAll method)
		private boolean existsByIdTransactional(Connection connection, String id) throws SQLException {
			String query = "select idsk, idsc, idsa, bduzina, bstil, bvetar from skok where idsk = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
				
				preparedStatement.setString(1, id);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.isBeforeFirst();
				}
			}
		}
	
	@Override
	public Iterable<Skok> findAll() throws SQLException {
		String query = "select idsk, idsc, idsa, bduzina, bstil, bvetar from skok";
		List<Skok> skokList = new ArrayList<Skok>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Skok skok = new Skok(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4),
						resultSet.getInt(5), resultSet.getInt(6));
				skokList.add(skok);
			}

		}
		return skokList;
	}

	@Override
	public Iterable<Skok> findAllById(Iterable<String> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Skok findById(String id) throws SQLException {
		String query = "select idsk, idsc, idsa, bduzina, bstil, bvetar from skok where idsk = ?";
		Skok skok = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					skok = new Skok(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4),
							resultSet.getInt(5), resultSet.getInt(6));
				}
			}
		}

		return skok;
	}

	@Override
	public boolean save(Skok entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveAll(Iterable<Skok> entities) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Iterable<Skok> uDrzaviskaceSKizDrzave(String idDrzave) throws SQLException {
		String query = "select idsk, idsc, idsa, bduzina, bstil, bvetar "
						+ "from skok "
						+ "where idsc in (select idsc from skakac where idd = ?) "
						+ "and idsa in (select idsa from skakaonica where idd = ?)";
		List<Skok> skokList = new ArrayList<Skok>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);){
				
			preparedStatement.setString(1, idDrzave);
			preparedStatement.setString(2, idDrzave);
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				while (resultSet.next()) {
					skokList.add(new Skok(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4),
							resultSet.getInt(5), resultSet.getInt(6)));
				}
			}
		}
		return skokList;
	}

	@Override
	public TehnikalijeTransakcijeDTO transakcija(TehnikalijeTransakcijeDTO dto) throws SQLException{
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			connection.setAutoCommit(false); // transaction starts
			
			//izmena vetrovitih bodova
			String dmlopr = "update skok set bvetar = ? where idsk = ?"; 
			try (PreparedStatement izmeniBV = connection.prepareStatement(dmlopr)) {
				izmeniBV.setInt(1, dto.getBvetar());
				izmeniBV.setString(2, dto.getSkokStari().getId());
				izmeniBV.executeUpdate();
			}
			//pronalazak izmenjenog skoka
			String query2 = "select idsk, idsc, idsa, bduzina, bstil, bvetar from skok where idsk = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
				preparedStatement.setString(1, dto.getSkokStari().getId());
				try (ResultSet resultSet2 = preparedStatement.executeQuery()) {
					resultSet2.next();
						dto.setSkokIzmenjen(new Skok(resultSet2.getString(1), resultSet2.getInt(2), resultSet2.getString(3), resultSet2.getInt(4),
								resultSet2.getInt(5), resultSet2.getInt(6)));
					}
				}
			
			//provera da li je rekord oboren
			int mozdaNoviRekord = dto.getBvetar() + dto.getSkokStari().getBodoviDuzina() + dto.getSkokStari().getBodoviStil();
			if(dto.getSkakac().getRekord() < mozdaNoviRekord){
				//izmena rekorda
				String dmlopr1 = "update skakac set pbsc = ? where idsc = ?"; 
				try (PreparedStatement izmeniPB = connection.prepareStatement(dmlopr1)) {
					izmeniPB.setInt(1, mozdaNoviRekord);
					izmeniPB.setInt(2, dto.getSkakac().getId());
					izmeniPB.executeUpdate();
				}
				
				//pronalazak izmenjenog skakaca
				String query = "select idsc, imesc, przsc, idd, titule, pbsc from skakac where idsc = ?";
				try (PreparedStatement nadjiSkakaca = connection.prepareStatement(query)) {
					nadjiSkakaca.setInt(1, dto.getSkakac().getId());
					try(ResultSet resultSet = nadjiSkakaca.executeQuery();){
						resultSet.next();
						dto.setSkakacNovi(new Skakac(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getInt(5), resultSet.getFloat(6)));
					}
				}
			}else{
				dto.setPromenjenIgrac(false);
			}
			
			
			connection.commit(); // transaction end
		}
		return dto;
	}
}
