package servlets;

import java.io.IOException;




import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelLogin;

//Ou ServletLoginController
@WebServlet(urlPatterns={"/principal/ServletLogin","/ServletLogin"})
public class ServletLogin extends HttpServlet {//classe java que extende HttpServlet

	private static final long serialVersionUID = 1L;

	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	private DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository();
	
	public ServletLogin() {
		super();
	}

	// Recebe os dados pela url em parametros
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao=request.getParameter("acao");//recebe pela url, logout.. href usa-se get
		if(acao!=null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();//invalida  seção do usuario
			RequestDispatcher redirecionar=request.getRequestDispatcher("index.jsp");//redireciona a página de login
			redirecionar.forward(request, response);
		}
		doPost(request, response);//pra caso chame o encaminhamento pela url, ai vai por get e o get chama o doPost
	}

	// Recebe os dados enviados por um formulario
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//recebe os parametros, requests
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");//<input name="url">, veio do input do jsp através do name

		try {//não ocorreu erro

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {//se o login e a senha são diferentes de vazio ou null
				ModelLogin modelLogin = new ModelLogin();//cria o objeto do repository
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				if (daoLoginRepository.validarAutenticacao(modelLogin)) {//se o usuario foi validado entra no if
					modelLogin=daoUsuarioRepository.consultaUsuarioLogado(login);
					request.getSession().setAttribute("usuario", modelLogin.getLogin());//seta uma seção ao usuário;seção:usuario, com o valor do login(String)
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					request.getSession().setAttribute("imageUser", modelLogin.getFotoUser()); //set a imagem do usuario no imageUser

					if (url == null || url.equals("null")) {//null ou vir escrita null
						url = "principal/principal.jsp";//pagina principal do sistema por login
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
				} else {//apos receber os dados do formulario, se o usuário nao for validado, redireciona a página de login novamente
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");//o '/' usa-se para voltar um caminho, pra localizar corretamente o encaminhamento
					request.setAttribute("msg", "Informe o login e a senha corretamente!");
					redirecionar.forward(request, response);
				}

			} else {//se o login ou senha sao vazios ou null
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e a senha corretamente!");
				redirecionar.forward(request, response);
			}
			
		} catch (Exception e) {//ocorreu erro
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());//a variavel msg é setada com a msg de erro
			redirecionar.forward(request, response);
		}
	}

}
