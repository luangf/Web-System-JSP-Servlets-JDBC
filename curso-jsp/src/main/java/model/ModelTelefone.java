package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelTelefone implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String numero;
	private ModelLogin usuario_pai_id; //usuario sendo acessado pelo "ver" no momento //tipo ModelLogin para poder acessar outras infos
	private ModelLogin usuario_cad_id; //usuario da sessão
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public ModelLogin getUsuario_pai_id() {
		return usuario_pai_id;
	}
	public void setUsuario_pai_id(ModelLogin usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}
	public ModelLogin getUsuario_cad_id() {
		return usuario_cad_id;
	}
	public void setUsuario_cad_id(ModelLogin usuario_cad_id) {
		this.usuario_cad_id = usuario_cad_id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelTelefone other = (ModelTelefone) obj;
		return Objects.equals(id, other.id);
	}
	
}
