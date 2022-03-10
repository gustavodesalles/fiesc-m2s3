package market.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnection {
	
	public FactoryConnection() {}
	
	public Connection openConnection(String usuario, String senha) throws SQLException {
		System.out.println("Preparando para abrir comunicação com o banco de dados...");
		return DriverManager.getConnection("jdbc:postgresql://localhost/market?useTimezone=true&serverTimezone=UTC",  usuario, senha);
	}
	
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
		System.out.println("Conexão encerrada.");
	}
}
