package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		if(objeto.isNovo()) { //grava novo usuario
		
		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement preparedSql = connection.prepareStatement(sql);

		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		preparedSql.setLong(5, userLogado);

		preparedSql.execute();
		connection.commit();
		
		}else { //atualiza usuario
			String sql = "update model_login set login=?, senha=?, nome=?, email=? where id="+objeto.getId()+";";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			
			preparedSql.executeUpdate();
			connection.commit();
		}
		
		return this.consultaUsuario(objeto.getLogin());
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception{
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		String sql="select * from model_login where useradmin is false and usuario_id="+userLogado; //like + %%; contém
		PreparedStatement statement=connection.prepareStatement(sql);
		
		ResultSet resultado=statement.executeQuery();
		while(resultado.next()) { //percorrer as linhas de resultado do SQL
			ModelLogin modelLogin=new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception{
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		String sql="select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=?"; //like + %%; contém
		PreparedStatement statement=connection.prepareStatement(sql);
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado=statement.executeQuery();
		while(resultado.next()) { //percorrer as linhas de resultado do SQL
			ModelLogin modelLogin=new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	//login sempre único
	public ModelLogin consultaUsuarioLogado(String login) throws Exception{
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where upper(login)=upper('"+login+"')";
		
		PreparedStatement statement=connection.prepareStatement(sql);
		ResultSet resultado=statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
		return modelLogin;
	}
	
	//login sempre único
	public ModelLogin consultaUsuario(String login) throws Exception{
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where upper(login)=upper('"+login+"') and useradmin is false";
		
		PreparedStatement statement=connection.prepareStatement(sql);
		ResultSet resultado=statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
		return modelLogin;
	}
	
	//login sempre único
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception{
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where upper(login)=upper('"+login+"') and useradmin is false and useradmin is false and usuario_id="+userLogado;
		
		PreparedStatement statement=connection.prepareStatement(sql);
		ResultSet resultado=statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
		return modelLogin;
	}
	
	public ModelLogin consultaUsuarioId(String id, Long userLogado) throws Exception{
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where id=? and useradmin is false and usuario_id=?";
		
		PreparedStatement statement=connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		
		ResultSet resultado=statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
		return modelLogin;
	}
	
	public boolean validarLogin(String login) throws Exception{
		String sql="select count(1)>0 as existe from model_login where upper(login)=upper('"+login+"');";
		PreparedStatement statement=connection.prepareStatement(sql);
		ResultSet resultado=statement.executeQuery();
		resultado.next();
		return resultado.getBoolean("existe");
	}
	
	public void deletarUser(String idUser) throws Exception{
		String sql="delete from model_login where id=? and useradmin is false";
		PreparedStatement prepareSql=connection.prepareStatement(sql);
		
		prepareSql.setLong(1,Long.parseLong(idUser));
		
		prepareSql.executeUpdate();
		connection.commit();
	}
	
}
