package servlets;

import java.io.Serializable;
import java.sql.Connection;

import connection.SingleConnectionBanco;
import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ServletGenericUtil extends HttpServlet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository();//ja efetua a conexão com o banco de dados ai n precisa do connection
	
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado).getId();
	}
}
