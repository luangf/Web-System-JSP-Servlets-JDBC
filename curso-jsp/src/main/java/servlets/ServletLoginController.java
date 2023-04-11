package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;

@WebServlet(urlPatterns = {"/principal/ServletLoginController", "/ServletLoginController"})
public class ServletLoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private DAOLoginRepository daoLoginRepository=new DAOLoginRepository();
	
	private DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository();
	
    public ServletLoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao=request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			
			RequestDispatcher redirecionar=request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login=request.getParameter("login");
		String senha=request.getParameter("senha");
		String url=request.getParameter("url");
		
		try {
			if(login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) { //se vem algo nos campos
				ModelLogin modelLogin=new ModelLogin();
				
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				if(daoLoginRepository.validarAutenticacao(modelLogin)) { //se tem o login e senha no db
					
					modelLogin=daoUsuarioRepository.consultarUsuarioLogado(login);
					
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());
					
					if(url == null || url.equals("null")) { //se n tiverem querendo acessar pagina especifica vai a padrao do sistema
						url="principal/principal.jsp";
					}
					
					RequestDispatcher redirecionar=request.getRequestDispatcher(url);
					redirecionar.forward(request, response);	
				}else {//login ou senha errados
					RequestDispatcher redirecionar=request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "informe o login e senha corretamente");
					redirecionar.forward(request, response);	
				}
			}else{ //vazio
				RequestDispatcher redirecionar=request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "informe o login e senha corretamente");
				redirecionar.forward(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar=request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}
