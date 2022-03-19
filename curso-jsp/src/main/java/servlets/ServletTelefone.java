package servlets;

import java.io.IOException;


import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

//salvar e excluir no telefone
@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {

	private static final long serialVersionUID = 1L;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	//foi criado um objeto da classe DAOTelefoneRepository
	private DAOTelefoneRepository daoTelefoneRepository=new DAOTelefoneRepository();
	
	public ServletTelefone() {
		super();
	}

	// consultar/deletar
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try { //try doGet
			String acao=request.getParameter("acao");//Front->Back (os dados que recebe da pagina jsp)
			
			if(acao != null && !acao.isEmpty() && acao.equals("excluir")) {
				String idFone=request.getParameter("id");//pega dado da tela pra servlet "tratar"
				
				//metodo de acesso/interação com banco de dados(deletarFone), no caso metodo "DAORepository" de deletar no banco de dados
				daoTelefoneRepository.deletarTelefone(Long.parseLong(idFone)); //converte o atributo "idFone" para Long com o "Long.parseLong()", pq o metodo deletarTelefone requisita um parametro long, pq o id no banco de dados (relação) com o eclipse é Long
				
				String userPai=request.getParameter("userpai");//front->back pega o parametro com nome "userpai"
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(Long.parseLong(userPai));
				
				List<ModelTelefone> modelTelefones=daoTelefoneRepository.listFone(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones); //back->end
				
				request.setAttribute("modelLogin", modelLogin); //dado do back-end que pode ser acessado no front(pagina jsp), seta o atributo que pode ser acessado com ${nomeAtributo} ou request.getAttribute("nomeAtributo")
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response); //redireciona para determinada tela(combo das 2 funções)
				
				return; //pra n bagunçar a tela...
			}
			
			String idUser = request.getParameter("idUser");//parametro(dado) que vem da tela(pagina jsp)
			if (idUser != null && !idUser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(Long.parseLong(idUser));
				
				List<ModelTelefone> modelTelefones=daoTelefoneRepository.listFone(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones);
				
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) { //catch doGet
			e.printStackTrace(); //imprime no console do Eclipse o erro
		}
	}

	// gravar/atualizar
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try { //try doPost
			String usuario_pai_id = request.getParameter("id"); // usuario que ta sendo analisado no momento
			String numero = request.getParameter("numero"); //vem do form do front

			if(!daoTelefoneRepository.existeFone(numero, Long.valueOf(usuario_pai_id))) { //se nao existe entra no if
				ModelTelefone modelTelefone = new ModelTelefone(); // objeto dos dados recebidos do front
				
				modelTelefone.setNumero(numero);
				modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioId(Long.parseLong(usuario_pai_id)));//CRUD(R)-read/consultar;sem commit 
				modelTelefone.setUsuario_cad_id(super.getUserLogadoObj(request)); // usuario logado
				
				daoTelefoneRepository.gravarTelefone(modelTelefone);
				
				request.setAttribute("msg", "Salvo com sucesso!");
				
			}else { //se já existe
				request.setAttribute("msg", "Telefone já existe!");
			}
			List<ModelTelefone> modelTelefones=daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id));
			
			ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(Long.parseLong(usuario_pai_id));//pega a classe modelo da tela passada
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefones", modelTelefones);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
		} catch (Exception e) { //catch doPost
			e.printStackTrace(); //imprime no console do Eclipse o erro
		}
	}
}
