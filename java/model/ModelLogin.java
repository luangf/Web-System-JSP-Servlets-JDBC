package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private boolean useradmin;
	private String perfil;
	private String sexo;

	private String fotouser; // do tipo text no banco de dados, gigante
	private String extensaofotouser;// png, jpg...

	private String cep;
	private String logradouro; // rua
	private String complemento; // número
	private String bairro;
	private String localidade; // cidade
	private String uf; // estado

	private Date dataNascimento;

	private Double rendaMensal;

	private List<ModelTelefone> telefones = new ArrayList<ModelTelefone>();

	public void setTelefones(List<ModelTelefone> telefones) {
		this.telefones = telefones;
	}

	public List<ModelTelefone> getTelefones() {
		return telefones;
	}

	public void setRendaMensal(Double rendaMensal) {
		this.rendaMensal = rendaMensal;
	}

	public Double getRendaMensal() {
		return rendaMensal;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public boolean isNovo() {
		if (this.id == null) {
			return true; // gravar/inserir
		} else if (this.id != null && this.id > 0) {
			return false; // atualizar
		}
		return id == null;
	}

	public String getFotouser() {
		return fotouser;
	}

	public void setFotouser(String fotouser) {
		this.fotouser = fotouser;
	}

	public String getExtensaofotouser() {
		return extensaofotouser;
	}

	public void setExtensaofotouser(String extensaofotouser) {
		this.extensaofotouser = extensaofotouser;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
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
