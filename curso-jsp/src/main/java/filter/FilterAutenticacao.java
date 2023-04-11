package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DAOVersionadorBanco;

@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

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

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest req=(HttpServletRequest) request;
			HttpSession session=req.getSession();
			
			String usuarioLogado=(String) session.getAttribute("usuario"); //login user
			
			String urlParaAutenticar=req.getServletPath(); //url q esta sendo acessada
			
			//validar se ta logado, senao redireciona para login
			if(usuarioLogado == null  && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLoginController")) {
				
				RequestDispatcher redireciona=request.getRequestDispatcher("/index.jsp?url="+urlParaAutenticar);
				request.setAttribute("msg", "por favor realize o login");
				redireciona.forward(request, response);
				return;
				
			}else {
				chain.doFilter(request, response);
			}
			
			connection.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redireciona=request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redireciona.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		connection=SingleConnectionBanco.getConnection();
		
		DAOVersionadorBanco daoVersionadorBanco=new DAOVersionadorBanco();
		//File.separator independente se é Linux ou Windows
		String caminhoPastaSQL=fConfig.getServletContext().getRealPath("versionadorbancosql")+File.separator;
		
		File[] filesSQL=new File(caminhoPastaSQL).listFiles();
		try {
			for (File file : filesSQL) {
				boolean arquivoJaRodado=daoVersionadorBanco.arquivoSqlRodado(file.getName());
				if(!arquivoJaRodado) {
					FileInputStream entradaArquivo=new FileInputStream(file);
					Scanner lerArquivo=new Scanner(entradaArquivo, "UTF-8");
					StringBuilder sql=new StringBuilder();
					
					while(lerArquivo.hasNext()) {
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoSQLRodado(file.getName());
					
					connection.commit();
					lerArquivo.close();
				}
			}
		}catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
