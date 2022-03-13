package market.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FactoryConnection {
	
	private String jdbcUrl;
	
	private String user;
	
	private String password;
	
	private DataSource dataSource;
	
	public FactoryConnection() {
		this.jdbcUrl = "jdbc:postgresql://localhost/market?useTimezone=true&serverTimezone=UTC";
		this.user = "postgres";
		this.password = "2501";
		
		ComboPooledDataSource cSource = new ComboPooledDataSource();
		cSource.setJdbcUrl(jdbcUrl);
		cSource.setUser(user);
		cSource.setPassword(password);
		
		cSource.setMaxPoolSize(18);
		
		this.dataSource = cSource;
	}
	
	public Connection openConnection() throws SQLException {
		System.out.println("Preparando para abrir comunicação com o banco de dados...");
		return this.dataSource.getConnection();
	}
	
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
		System.out.println("Conexão encerrada.");
	}
}
