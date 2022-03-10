package market.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import market.model.entities.Product;

public class ProductDAO {
	
	private Connection conn;
	
	public ProductDAO(Connection conn) {
		this.conn = conn;
	}

	public void list() throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("select id, name, descricao from produto");
		
		ResultSet resultSet = statement.getResultSet();
		int count = 0;
		while (resultSet.next()) {
			count++;
			
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String descricao = resultSet.getString("descricao");
			System.out.println("Produto " + count + ": id={" + id +"},name={" + name +"},descricao={" + descricao +"}");
		}
	}
	
	public boolean create(String name, String descricao) {
		Statement statement;
		//String name = "milky way); DROP TABLE produto;--";
		//String descricao = "fake chocolate bar (dummy)";
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
