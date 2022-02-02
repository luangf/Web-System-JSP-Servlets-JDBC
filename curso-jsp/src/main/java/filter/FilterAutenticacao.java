package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) // intercepta todas as requisições do projeto ou mapeamento
public class FilterAutenticacao extends HttpFilter {

	private static Connection connection;

	public FilterAutenticacao() {
		super();
	}

	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			//Pegando usuario logado
			HttpServletRequest req = (HttpServletRequest) request;//ServletRequest -> HttpServletRequest
			HttpSession session = req.getSession();
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			String urlParaAutenticar = req.getServletPath();// url que esta sendo acessada
			
			//validar se esta logado senao redireciona para a tela de login
			//sendo que não está logado(a lógica desse if a pessoa tentando acessar uma url diferente não pode...), tentando acessar qualquer parte do sistema sem estar logado, não pode...
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { // nao logado
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url="+urlParaAutenticar);//seta o parametro url com o valor da url que o usuario queria acessar
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; // para a execução e redireciona para o login;quebrando o do filter que só acabava com o chain creio
			} else {// logado
				chain.doFilter(request, response);
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
