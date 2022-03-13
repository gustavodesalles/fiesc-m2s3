package market.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	public boolean create(String name) {
		try {
			String sql = "INSERT INTO categoria(name) values(?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			Category category = new Category(name);
			add(preparedStatement, category);
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("Categoria criada.");
		return true;
	}
	
	private void add(PreparedStatement preparedStatement, Category category)
			throws SQLException {
		preparedStatement.setString(1, category.getName());
		preparedStatement.execute();
	} 
	
	public boolean update(Category category, int id) {
		String sql = "UPDATE categoria set name = ? where id = ?";
		
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			
			preparedStatement.setString(1, category.getName());
			preparedStatement.setLong(2, id);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("Categoria atualizada.");
		return true;
	}
	
	public boolean delete(int id) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.execute("delete from categoria where id = " + id);
		
			int updateCount = statement.getUpdateCount();
			
			if (updateCount == 0) {
				System.out.println("Categoria não encontrada no banco; não deletada.");
			} else {
				System.out.println("Categoria deletada.");
			}
			
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	} 
}
