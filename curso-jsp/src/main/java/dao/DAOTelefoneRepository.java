package dao;

import java.sql.Connection;

import connection.SingleConnectionBanco;

public class DAOTelefoneRepository {
	private Connection connection;
	
	public DAOTelefoneRepository(){
		connection=SingleConnectionBanco.getConnection();
	}
}
