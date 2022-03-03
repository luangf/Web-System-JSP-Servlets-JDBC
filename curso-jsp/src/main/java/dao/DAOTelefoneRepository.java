package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	//consulta; get
	public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
		List<ModelTelefone> retorno=new ArrayList<ModelTelefone>();
		
		String sql="select * from telefone where usuario_pai_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
		preparedStatement.setLong(1,idUserPai);
		
		ResultSet rs=preparedStatement.executeQuery();
		
		while(rs.next()) {
			ModelTelefone modelTelefone=new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id")); //pega a coluna id e seta no atributo do objeto
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioId(rs.getLong("usuario_pai_id")));
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultaUsuarioId(rs.getLong("usuario_cad_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}
	
	//normalmente gravar e atualizar é por post
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception{ //joga a exceção para ser tratada depois
		String sql="insert into telefone(numero, usuario_pai_id, usuario_cad_id) values (?, ?, ?)"; //nao precisa id; que tem a sequencia(seq) para itera-lo
		PreparedStatement preparedStatement=connection.prepareStatement(sql); //prepara o sql
		
		preparedStatement.setString(1, modelTelefone.getNumero()); //recebe os dados por String, msm sendo numero...//1(primeiro '?')
		preparedStatement.setLong(2, modelTelefone.getUsuario_pai_id().getId()); //user pai=usuario acessado no momento
		preparedStatement.setLong(3, modelTelefone.getUsuario_cad_id().getId()); //user cad=usuario da sessão
		
		preparedStatement.execute();
		connection.commit(); //salva as alterações no banco de dados
	}
	
	//normalmente deletar e consultar é por get
	public void deletarTelefone(Long id) throws Exception{
		String sql="delete from telefone where id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql); //prepara o sql
		
		preparedStatement.setLong(1, id); //primeiro e unico ?
		
		preparedStatement.executeUpdate(); //com o ...Update já que atualiza o bd existente
		connection.commit();
	}
}
