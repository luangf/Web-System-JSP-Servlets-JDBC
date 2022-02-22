package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		if (objeto.isNovo()) { // grava novo usuario

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);

			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setLong(5, userLogado);
			preparedSql.setString(6, objeto.getPerfil());
			preparedSql.setString(7, objeto.getSexo());

			preparedSql.setString(8, objeto.getCep());
			preparedSql.setString(9, objeto.getLogradouro());
			preparedSql.setString(10, objeto.getBairro());
			preparedSql.setString(11, objeto.getLocalidade());
			preparedSql.setString(12, objeto.getUf());
			preparedSql.setString(13, objeto.getNumero());

			preparedSql.execute(); // executa o preparedSql com os sets
			connection.commit(); //

			// Foto
			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "update model_login set fotouser=?, extensaofotouser=? where login=?";
				preparedSql = connection.prepareStatement(sql);

				preparedSql.setString(1, objeto.getFotoUser());
				preparedSql.setString(2, objeto.getextensaofotouser());
				preparedSql.setString(3, objeto.getLogin());

				preparedSql.execute();
				connection.commit();
			}

		} else { // atualiza usuario
			String sql = "update model_login set login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? where id="
					+ objeto.getId() + ";";
			PreparedStatement preparedSql = connection.prepareStatement(sql);

			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setString(5, objeto.getPerfil());
			preparedSql.setString(6, objeto.getSexo());

			preparedSql.setString(7, objeto.getCep());
			preparedSql.setString(8, objeto.getLogradouro());
			preparedSql.setString(9, objeto.getBairro());
			preparedSql.setString(10, objeto.getLocalidade());
			preparedSql.setString(11, objeto.getUf());
			preparedSql.setString(12, objeto.getNumero());

			preparedSql.executeUpdate();
			connection.commit();

			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "update model_login set fotouser=?, extensaofotouser=? where id=?"; // para att ja temos o id,
																							// digamos que é mais
																							// correto
				preparedSql = connection.prepareStatement(sql);

				preparedSql.setString(1, objeto.getFotoUser());
				preparedSql.setString(2, objeto.getextensaofotouser());
				preparedSql.setLong(3, objeto.getId());

				preparedSql.execute();
				connection.commit();
			}
		}

		return this.consultaUsuario(objeto.getLogin());
	}

	public List<ModelLogin> consultaUsuarioListPaginado(Long userLogado, Integer offset) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where useradmin is false and usuario_id=" + userLogado
				+ " order by nome offset " + offset + " limit 5"; // like + %%;
		// contém
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) { // percorrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);
		}
		return retorno;
	}

	public int totalPaginas(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select count(1) as total from model_login where usuario_id=" + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		resultado.next();

		Double cadastros = resultado.getDouble("total");
		Double porPagina = 5.0;
		Double quantPaginas = cadastros / porPagina;
		Double resto = quantPaginas % 2;
		if (resto > 0) {
			quantPaginas++;
		}

		return quantPaginas.intValue();
	}

	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where useradmin is false and usuario_id=" + userLogado + " limit 5"; // like
																														// +
																														// %%;
		// contém
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) { // percorrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);
		}
		return retorno;
	}

	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=? limit 5";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		resultado.next();

		Double cadastros = resultado.getDouble("total");
		Double porPagina = 5.0;
		Double quantPaginas = cadastros / porPagina;
		Double resto = quantPaginas % 2;
		if (resto > 0) {
			quantPaginas++;
		}

		return quantPaginas.intValue();
	}

	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=? limit 5"; // like
		// +
		// %%;
		// contém
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) { // percorrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}
		return retorno;
	}

	// login sempre único
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login)=upper('" + login + "')";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			modelLogin.setFotoUser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}
		return modelLogin;
	}

	// login sempre único
	public ModelLogin consultaUsuario(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login)=upper('" + login + "') and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			modelLogin.setFotoUser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}
		return modelLogin;
	}

	// login sempre único
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login)=upper('" + login
				+ "') and useradmin is false and useradmin is false and usuario_id=" + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			modelLogin.setFotoUser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}
		return modelLogin;
	}

	public ModelLogin consultaUsuarioId(String id, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id=? and useradmin is false and usuario_id=?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setextensaofotouser(resultado.getString("extensaofotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}
		return modelLogin;
	}

	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1)>0 as existe from model_login where upper(login)=upper('" + login + "');";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		return resultado.getBoolean("existe");
	}

	public void deletarUser(String idUser) throws Exception {
		String sql = "delete from model_login where id=? and useradmin is false";
		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idUser));

		prepareSql.executeUpdate();
		connection.commit();
	}

}
