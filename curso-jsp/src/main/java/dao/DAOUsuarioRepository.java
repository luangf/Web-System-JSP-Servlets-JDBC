package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDTOGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public BeanDTOGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		String sql="select avg(renda_mensal) as media_salarial, perfil from model_login where usuario_id=? and data_nascimento>=? and data_nascimento<=? group by perfil";
		
		PreparedStatement prepareSQL = connection.prepareStatement(sql);
		prepareSQL.setLong(1, userLogado);
		prepareSQL.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		prepareSQL.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		List<Double> medias_salariais=new ArrayList<Double>();
		List<String> perfils=new ArrayList<String>();
		
		BeanDTOGraficoSalarioUser beanDTOGraficoSalarioUser=new BeanDTOGraficoSalarioUser();
		
		ResultSet resultSet=prepareSQL.executeQuery();
		
		while(resultSet.next()) {
			Double media_salarial=resultSet.getDouble("media_salarial");
			medias_salariais.add(media_salarial);
			String perfil=resultSet.getString("perfil");
			perfils.add(perfil);
		}
		
		beanDTOGraficoSalarioUser.setMedias_salariais(medias_salariais);
		beanDTOGraficoSalarioUser.setPerfils(perfils);
		
		return beanDTOGraficoSalarioUser;
	}
	
	public BeanDTOGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception {
		String sql="select avg(renda_mensal) as media_salarial, perfil from model_login where usuario_id=? group by perfil";
		
		PreparedStatement prepareSQL = connection.prepareStatement(sql);
		prepareSQL.setLong(1, userLogado);
		
		List<Double> medias_salariais=new ArrayList<Double>();
		List<String> perfils=new ArrayList<String>();
		
		BeanDTOGraficoSalarioUser beanDTOGraficoSalarioUser=new BeanDTOGraficoSalarioUser();
		
		ResultSet resultSet=prepareSQL.executeQuery();
		while(resultSet.next()) {
			Double media_salarial=resultSet.getDouble("media_salarial");
			medias_salariais.add(media_salarial);
			String perfil=resultSet.getString("perfil");
			perfils.add(perfil);
		}
		
		beanDTOGraficoSalarioUser.setMedias_salariais(medias_salariais);
		beanDTOGraficoSalarioUser.setPerfils(perfils);
		
		return beanDTOGraficoSalarioUser;
	}

	//param Long userLogado é para qual id(de usuário) esse usuário vai ser gravado, cada user pode ter vários users(cadastros de users)
	public ModelLogin gravarUser(ModelLogin modelLogin, Long userLogado) throws Exception {
		if(modelLogin.isNovo()) { //Gravar(Objeto novo)
			String sql = "insert into model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, complemento, bairro, localidade, uf, data_nascimento, renda_mensal) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
			PreparedStatement prepareSQL = connection.prepareStatement(sql);
	
			prepareSQL.setString(1, modelLogin.getLogin());
			prepareSQL.setString(2, modelLogin.getSenha());
			prepareSQL.setString(3, modelLogin.getNome());
			prepareSQL.setString(4, modelLogin.getEmail());
			prepareSQL.setLong(5, userLogado);
			prepareSQL.setString(6, modelLogin.getPerfil());
			prepareSQL.setString(7, modelLogin.getSexo());
			
			prepareSQL.setString(8, modelLogin.getCep());
			prepareSQL.setString(9, modelLogin.getLogradouro());
			prepareSQL.setString(10, modelLogin.getComplemento());
			prepareSQL.setString(11, modelLogin.getBairro());
			prepareSQL.setString(12, modelLogin.getLocalidade());
			prepareSQL.setString(13, modelLogin.getUf());
			prepareSQL.setDate(14, modelLogin.getDataNascimento());
			prepareSQL.setDouble(15, modelLogin.getRendaMensal());
	
			prepareSQL.execute();
			connection.commit();
			
			//se tiver foto atualiza o usuário recem gravado botando a foto e extensão dela
			if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
				sql="update model_login set fotouser=?, extensaofotouser=? where login=?";
				
				prepareSQL = connection.prepareStatement(sql);
				
				prepareSQL.setString(1, modelLogin.getFotouser());
				prepareSQL.setString(2, modelLogin.getExtensaofotouser());
				prepareSQL.setString(3, modelLogin.getLogin());
				
				prepareSQL.execute();
				connection.commit();
			}
			
		}else { //Atualizar
			String sql="update model_login set login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, complemento=?, bairro=?, localidade=?, uf=?, data_nascimento=? where id="+modelLogin.getId()+";";
			
			PreparedStatement prepareSQL = connection.prepareStatement(sql);
			prepareSQL.setString(1, modelLogin.getLogin());
			prepareSQL.setString(2, modelLogin.getSenha());
			prepareSQL.setString(3, modelLogin.getNome());
			prepareSQL.setString(4, modelLogin.getEmail());
			prepareSQL.setString(5, modelLogin.getPerfil());
			prepareSQL.setString(6, modelLogin.getSexo());
			
			prepareSQL.setString(7, modelLogin.getCep());
			prepareSQL.setString(8, modelLogin.getLogradouro());
			prepareSQL.setString(9, modelLogin.getComplemento());
			prepareSQL.setString(10, modelLogin.getBairro());
			prepareSQL.setString(11, modelLogin.getLocalidade());
			prepareSQL.setString(12, modelLogin.getUf());
			prepareSQL.setDate(13, modelLogin.getDataNascimento());
			prepareSQL.setDouble(14, modelLogin.getRendaMensal());
			
			prepareSQL.executeUpdate();
			connection.commit();
			
			if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
				sql="update model_login set fotouser=?, extensaofotouser=? where id=?";
				
				prepareSQL = connection.prepareStatement(sql);
				
				prepareSQL.setString(1, modelLogin.getFotouser());
				prepareSQL.setString(2, modelLogin.getExtensaofotouser());
				prepareSQL.setLong(3, modelLogin.getId());
				
				prepareSQL.execute();
				connection.commit();
			}
		}
		
		return this.consultarUsuario(modelLogin.getLogin(), userLogado);
	}
	
	public int totalPaginas(Long userLogado) throws Exception {
		String sql="select count(1) as totalUsers from model_login where usuario_id="+userLogado;
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		resultado.next();
		
		Double totalUsers = resultado.getDouble("totalUsers");
		Double porPagina = 5.0;
		Double quantPaginas = totalUsers / porPagina;
		
		return quantPaginas.intValue();
	}
	
	public List<ModelLogin> consultarUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		
		String sql="select * from model_login where useradmin is false and usuario_id="+userLogado+" order by nome offset "+offset+" limit 5";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin=new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	public List<ModelLogin> consultarUsuarioListRel(Long userLogado) throws Exception {
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		
		String sql="select * from model_login where useradmin is false and usuario_id="+userLogado;
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin=new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where useradmin is false and usuario_id="+userLogado+" and data_nascimento >= ? and data_nascimento <= ?"; // like+%%;contém
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) { // percorrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			modelLogin.setSexo(resultado.getString("sexo"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			retorno.add(modelLogin);
		}
		return retorno;
	}
	
	public List<ModelLogin> consultarUsuarioList(Long userLogado) throws Exception {
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		
		String sql="select * from model_login where useradmin is false and usuario_id="+userLogado+"limit 5;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin=new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	public int consultarUsuarioListTotalPaginaPaginacao(String nomeBusca, Long userLogado) throws Exception {
		String sql="select count(1) as totalUsers from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=?";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, "%"+nomeBusca+"%");
		preparedStatement.setLong(2, userLogado);
		
		ResultSet resultado=preparedStatement.executeQuery();
		resultado.next();
		
		Double totalUsers = resultado.getDouble("totalUsers");
		Double porPagina = 5.0;
		Double quantPaginas = totalUsers / porPagina;
		
		return quantPaginas.intValue();
	}
	
	public List<ModelLogin> consultarUsuarioListOffSet(String nomeBusca, Long userLogado, int offset) throws Exception {
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		
		String sql="select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=? offset "+offset+" limit 5;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, "%"+nomeBusca+"%");
		preparedStatement.setLong(2, userLogado);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin=new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	public List<ModelLogin> consultarUsuarioList(String nomeBusca, Long userLogado) throws Exception {
		List<ModelLogin> retorno=new ArrayList<ModelLogin>();
		
		String sql="select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=? limit 5;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, "%"+nomeBusca+"%");
		preparedStatement.setLong(2, userLogado);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		
		while(resultado.next()) {
			ModelLogin modelLogin=new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	public ModelLogin consultarUsuarioId(Long id) throws Exception {
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where id=? and useradmin is false;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("renda_mensal"));
		}
		
		return modelLogin;
	}
	
	public ModelLogin consultarUsuarioId(String id, Long userLogado) throws Exception {
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where id=? and useradmin is false and usuario_id=?;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setLong(1, Long.parseLong(id));
		preparedStatement.setLong(2, userLogado);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("renda_mensal"));
		}
		
		return modelLogin;
	}
	
	public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where upper(login)=upper('"+login+"')";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("renda_mensal"));
		}
		
		return modelLogin;
	}
	
	public ModelLogin consultarUsuario(String login) throws Exception {
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where upper(login)=upper('"+login+"') and useradmin is false;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("renda_mensal"));
		}
		
		return modelLogin;
	}
	
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {
		ModelLogin modelLogin=new ModelLogin();
		
		String sql="select * from model_login where upper(login)=upper('"+login+"') and useradmin is false and usuario_id="+userLogado+";";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			
			modelLogin.setDataNascimento(resultado.getDate("data_nascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("renda_mensal"));
		}
		
		return modelLogin;
	}
	
	public boolean validarLogin(String login) throws Exception {
		String sql="select count(1) > 0 as existe from model_login where upper(login)=upper('"+login+"');";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		ResultSet resultado=preparedStatement.executeQuery();
		
		resultado.next();
		return resultado.getBoolean("existe");
	}
	
	public void deletarUser(String idUser) throws Exception {
		String sql="DELETE FROM model_login WHERE id=? and useradmin is false;";
		
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setLong(1, Long.parseLong(idUser));
		
		preparedStatement.executeUpdate();
		connection.commit();
	}

	public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
		List<ModelTelefone> retorno=new ArrayList<ModelTelefone>();
		
		String sql="select * from telefone where usuario_pai_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUserPai);
		
		ResultSet rs=preparedStatement.executeQuery();
		
		while(rs.next()) {
			ModelTelefone modelTelefone=new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_pai_id(this.consultarUsuarioId(rs.getLong("usuario_pai_id")));
			modelTelefone.setUsuario_cad_id(this.consultarUsuarioId(rs.getLong("usuario_cad_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}
	
}