package market.model.dao;

import java.sql.Connection;
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
}
