package market.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import market.model.entities.Category;

public class CategoryDAO {
	
	private Connection conn;
	
	public CategoryDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Category> list() throws SQLException {
		List<Category> categories = new ArrayList<Category>();
		
		try (Statement statement = conn.createStatement()) {
			statement.execute("select id, name from categoria");
			
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Category category = new Category(resultSet.getString("name"));
					category.setId(resultSet.getInt("id"));
					categories.add(category);
				}
			}
			return categories;
		}
	}
	
}
