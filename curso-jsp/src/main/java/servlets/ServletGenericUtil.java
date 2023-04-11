package servlets;

import java.io.Serializable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.DAOUsuarioRepository;
import model.ModelLogin;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository(); //connection é inicial nesse obj
	
	//retorna id/código do usuário(tabela model_login)
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession(); //pega sessão do request do HttpServletRequest
		String usuarioLogado=(String) session.getAttribute("usuario"); //pega login
		//agora com o login vamos achar o id do usuário
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
	}
	
	//Retorna obg do user logado
	public ModelLogin getUserLogadoObj(HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession(); //pega sessão do request do HttpServletRequest
		String usuarioLogado=(String) session.getAttribute("usuario"); //pega login
		//agora com o login vamos achar o id do usuário
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado);
	}
	
}
