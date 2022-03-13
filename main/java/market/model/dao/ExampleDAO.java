package market.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import market.model.entities.Example;

public class ExampleDAO {
	
	private Connection conn;

	public ExampleDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Example> list() throws SQLException {
		List<Example> examples = new ArrayList<Example>();
		
		try (Statement statement = conn.createStatement()) {
			statement.execute("select id, fav_color, fav_number from example");
			
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Example example = new Example(resultSet.getLong("id"), resultSet.getString("fav_color"), resultSet.getInt("fav_number"));
					examples.add(example);
				}
			}	
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
		}
		return examples;
	}
	
	public boolean create(String fav_color, int fav_number) {
		try {
			String sql = "INSERT INTO example(fav_color, fav_number) values(?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			Example example = new Example(fav_color, fav_number);
			add(preparedStatement, example);
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("Example criado.");
		return true;
	}
	
	private void add(PreparedStatement preparedStatement, Example example)
			throws SQLException {
		preparedStatement.setString(1, example.getFav_color());
		preparedStatement.setInt(2, example.getFav_number());
		preparedStatement.execute();
	} 
	
	public boolean update(Example example, int id) {
		String sql = "UPDATE example set fav_color = ?, fav_number = ? where id = ?";
		
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			
			preparedStatement.setString(1, example.getFav_color());
			preparedStatement.setInt(2, example.getFav_number());
			preparedStatement.setLong(3, id);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("Example atualizado.");
		return true;
	}
	
	public boolean delete(int id) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.execute("delete from example where id = " + id);
		
			int updateCount = statement.getUpdateCount();
			
			if (updateCount == 0) {
				System.out.println("Example não encontrado no banco; não deletado.");
			} else {
				System.out.println("Example deletado.");
			}
			
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	} 
}
