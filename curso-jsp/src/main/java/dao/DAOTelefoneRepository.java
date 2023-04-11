package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {
	private Connection connection;
	
	private DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository();
	
	public DAOTelefoneRepository(){
		connection=SingleConnectionBanco.getConnection();
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
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioId(rs.getLong("usuario_pai_id")));
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultarUsuarioId(rs.getLong("usuario_cad_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}
	
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception{
		String sql="insert into telefone(numero, usuario_pai_id, usuario_cad_id) values (?, ?, ?)";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		preparedStatement.setString(1, modelTelefone.getNumero());
		preparedStatement.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		preparedStatement.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		
		preparedStatement.execute();
		connection.commit();
	}
	
	public void deletarTelefone(Long id) throws Exception{
		String sql="delete from telefone where id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql); //prepara o sql
		
		preparedStatement.setLong(1, id); //primeiro e unico ?
		
		preparedStatement.executeUpdate(); //com o ...Update já que atualiza o bd existente
		connection.commit();
	}
	
	public boolean existeFone(String fone, Long idUser) throws Exception {
		String sql="select count(1)>0 as existe from telefone where usuario_pai_id=? and numero=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUser);
		preparedStatement.setString(2, fone);
		ResultSet resultSet=preparedStatement.executeQuery();
		resultSet.next();
		return resultSet.getBoolean("existe");
	}
	
}
