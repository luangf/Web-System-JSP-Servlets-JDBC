package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;
//Varias classes DAO, no package(pacote) dao
//DAO (faz as operações no banco de dados)
//DAOXRepository (Repository->DAO)
public class DAOLoginRepository {
	private Connection connection;
	
	public DAOLoginRepository() {
		connection=SingleConnectionBanco.getConnection();
	}
	
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception{
		String sql="select * from model_login where upper(login)=upper(?) and upper(senha)=upper(?)";//nesse caso independe de case sensitivity
		PreparedStatement statement=connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2,modelLogin.getSenha());
		ResultSet resultSet=statement.executeQuery();
		if(resultSet.next()) {//if true/se tem resultado
			return true;//autenticado
		}
		return false;//nao autenticado
	}
}
