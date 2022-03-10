package market.connection;

import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import market.model.dao.ProductDAO;
import market.model.entities.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionBd {
	
	public static void main(String[] args) {
		boolean repeat = true;
		Scanner input = new Scanner(System.in);
		int opcao = 0;
		
		do {
			try {
				System.out.println("Digite uma opção: ");
				System.out.println("1 - Criar um produto");
				System.out.println("2 - Deletar um produto");
				System.out.println("Digite qualquer outro número para sair.");
				opcao = input.nextInt();
				repeat = false;
			} catch (InputMismatchException e) {
				System.out.println("Opção inválida. Digite somente números.");
				input.nextLine();
			}
		} while (repeat);
		
		switch (opcao) {
		case 1:
			try {
				FactoryConnection factoryConnection = new FactoryConnection();
				
				input.nextLine();
				System.out.println("Digite o nome do usuário: ");
				String usuario = input.nextLine();
				System.out.println("Digite a senha: ");
				String senha = input.nextLine();
				Connection conn = factoryConnection.openConnection(usuario, senha);
				
				conn.setAutoCommit(false);
				
				System.out.println("Digite o nome do produto: ");
				String name = input.nextLine();
				System.out.println("Digite a descrição do produto: ");
				String descricao = input.nextLine();
				
				ProductDAO productDAO = new ProductDAO(conn);

				productDAO.create(name, descricao);
				productDAO.list();
				
				factoryConnection.closeConnection(conn);
				
			} catch (SQLException e) {
				System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			}
			main(null);
			break;
			
		case 2:
			repeat = true;
			try {
				FactoryConnection factoryConnection = new FactoryConnection();
				
				input.nextLine();
				System.out.println("Digite o nome do usuário: ");
				String usuario = input.nextLine();
				System.out.println("Digite a senha: ");
				String senha = input.nextLine();
				Connection conn = factoryConnection.openConnection(usuario, senha);
				
				ProductDAO productDAO = new ProductDAO(conn);
				productDAO.list();
				
				do {
					try {
						System.out.println("Digite o código de identificação do item que deseja deletar: ");
						int id = input.nextInt();
						repeat = false;
						productDAO.delete(id);
					} catch (InputMismatchException im) {
						System.out.println("Entrada inválida. Favor digitar um número inteiro.");
					} catch (IndexOutOfBoundsException ob) {
						System.out.println("Entrada inválida. Não há um produto com este código.");
					}
				} while (repeat);
				
				factoryConnection.closeConnection(conn);
				
			} catch (SQLException e) {
				System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
			}
			main(null);
			break;
			
		default:
			System.out.println("Encerrando...");
			System.exit(0);
			break;
		}
		
//		try {
//			FactoryConnection factoryConnection = new FactoryConnection();
//			Connection conn = factoryConnection.openConnection();
//			conn.setAutoCommit(false);
//		
//			productDAO.list();
//			productDAO.create("Notebook", "Dell");
//			productDAO.list();
//			//productDAO.delete(14);
//			productDAO.list();
//			factoryConnection.closeConnection(conn);
//			
//		} catch (SQLException e) {
//			System.out.println("[ERROR]: Erro ao se conectar ao banco de dados. Causado por " + e.getMessage());
//			conn.rollback();
//		}
		
	}

	
}
