package market.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import market.model.entities.Category;
import market.model.entities.Product;

public class ProductDAO {
	
	private Connection conn;
	
	public ProductDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Product> list() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		String sql = "select id, name, descricao from produto";
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.execute();
			
			try (ResultSet resultSet = preparedStatement.getResultSet()) {
				while (resultSet.next()) {
					Product product = new Product(resultSet.getString("name"), resultSet.getString("descricao"));
					product.setId(resultSet.getInt("id"));
					products.add(product);
				}
			}
		
		return products;
		}
	}
	
	public List<Product> listProductsAndCategory() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		String sql = "select p.*, c.id as cid, c.name as cname from produto as p INNER JOIN categoria as c on p.categoria_id = c.id";
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.execute();
			
			try (ResultSet resultSet = preparedStatement.getResultSet()) {
				while (resultSet.next()) {
					Product product = new Product(resultSet.getString("name"), resultSet.getString("descricao"));
					product.setId(resultSet.getInt("id"));
					product.setCategory(new Category(resultSet.getInt("cid"), resultSet.getString("cname")));
					products.add(product);
				}
			}
		
		return products;
		}
	}
	
	public List<Product> listByCategory(Category category) throws SQLException {
		List<Product> products = new ArrayList<Product>();
		String sql = "select id, name, descricao from produto where categoria.id = ?";
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setLong(1, category.getId());
			preparedStatement.execute();
			
			try (ResultSet resultSet = preparedStatement.getResultSet()) {
				while (resultSet.next()) {
					Product product = new Product(resultSet.getString("name"), resultSet.getString("descricao"));
					product.setId(resultSet.getInt("id"));
					products.add(product);
				}
			}
			return products;
		}
	}
	
	public boolean create(String name, String descricao) {
		try {
			String sql = "INSERT INTO produto(name, descricao) values(?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			Product product = new Product(name, descricao);
			add(preparedStatement, product);
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("Produto criado.");
		return true;
	}
	
	public boolean update(Product product, int id) {
		String sql = "UPDATE produto set name = ?, descricao = ? where id = ?";
		
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setLong(3, id);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("Produto criado.");
		return true;
	}
	
	public Product getById(int id) {
		String sql = "SELECT name, descricao FROM produto WHERE id = ?";
		Product product = new Product();
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
			
			try (ResultSet resultSet = preparedStatement.getResultSet()) {
				while (resultSet.next()) {
					product = new Product(resultSet.getString("name"), resultSet.getString("descricao"));
					product.setId(id);
				}
			}
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao capturar o produto. Causado por " + e.getMessage());
		}
		System.out.println("Produto localizado!");
		return product;
	}
	
	public List<Product> getByName(String name) {
		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT id, name, descricao FROM produto WHERE name = ?";
		Product product = new Product();
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			
			preparedStatement.setString(1, name);
			preparedStatement.execute();
			
			try (ResultSet resultSet = preparedStatement.getResultSet()) {
				while (resultSet.next()) {
					product = new Product(resultSet.getString("name"), resultSet.getString("descricao"));
					product.setId(resultSet.getLong("id"));
					products.add(product);
				}
			}
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao capturar o produto. Causado por " + e.getMessage());
		}
		System.out.println("Produto(s) localizado(s)!");
		return products;
	}

	private void add(PreparedStatement preparedStatement, Product product)
			throws SQLException {
		preparedStatement.setString(1, product.getName());
		preparedStatement.setString(2, product.getDescription());
		preparedStatement.execute();
	} 

	public boolean delete(int id) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.execute("delete from produto where id = " + id);
		
			int updateCount = statement.getUpdateCount();
			
			if (updateCount == 0) {
				System.out.println("Produto não encontrado no banco; não deletado.");
			} else {
				System.out.println("Produto deletado.");
			}
			
		} catch (SQLException e) {
			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	} 
}
