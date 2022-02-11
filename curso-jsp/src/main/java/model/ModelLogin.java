package model;

import java.io.Serializable;
//Classe Model(classe de modelo)
//model implementa o serializable (padrao)
public class ModelLogin implements Serializable {

	//model tem essa variavel (padrao)
	private static final long serialVersionUID = 1L;

	private Long id;//padrao para localizar a linha da tabela...varias funcionalidades a variavel id..
	private String nome;
	private String email;
	private String login;
	private String senha;
	private boolean useradmin;
	private String perfil;
	private String sexo;
	private String fotoUser;
	private String extensaofotouser;
	
	public String getFotoUser() {
		return fotoUser;
	}

	public void setFotoUser(String fotoUser) {
		this.fotoUser = fotoUser;
	}

	public String getextensaofotouser() {
		return extensaofotouser;
	}

	public void setextensaofotouser(String extensaofotouser) {
		this.extensaofotouser = extensaofotouser;
	}

	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setUseradmin(boolean useradmin) {
		this.useradmin = useradmin;
	}
	
	public boolean getUseradmin() {
		return useradmin;
	}
	
	public boolean isNovo() {
		if(this.id == null) {
			return true; //inserir novo
		}else if(this.id != null && this.id > 0) {
			return false; //atualizar
		}
		return id == null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
