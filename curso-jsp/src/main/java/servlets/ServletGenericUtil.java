package servlets;

import java.io.Serializable;



import dao.DAOUsuarioRepository;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.ModelLogin;

public class ServletGenericUtil extends HttpServlet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository();//ja efetua a conexão com o banco de dados ai n precisa do connection
	
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado).getId();
	}
	
	public ModelLogin getUserLogadoObj(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(); //objeto da sessão
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado);
	}
}
