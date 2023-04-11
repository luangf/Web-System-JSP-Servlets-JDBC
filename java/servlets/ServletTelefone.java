package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import model.ModelTelefone;

@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {

	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private DAOTelefoneRepository daoTelefoneRepository=new DAOTelefoneRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao=request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equals("excluir")) {
				String idFone=request.getParameter("id");

				daoTelefoneRepository.deletarTelefone(Long.parseLong(idFone));
				
				String userPai=request.getParameter("userpai");
				
				ModelLogin modelLogin=daoUsuarioRepository.consultarUsuarioId(Long.parseLong(userPai));
				
				List<ModelTelefone> modelTelefones=daoTelefoneRepository.listFone(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones);
				
				request.setAttribute("msg", "Telefone excluido com sucesso");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
				return;
			}
			
			String idUser=request.getParameter("iduser"); // id usuário cad (especifico, ñ o logado)
			
			if(idUser != null && !idUser.isEmpty()) { //se tiver usuario no formulario
				ModelLogin modelLogin=daoUsuarioRepository.consultarUsuarioId(Long.parseLong(idUser));
					
				List<ModelTelefone> modelTelefones=daoTelefoneRepository.listFone(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones);
				
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			}else { //se n tiver user volta pra pagina de user
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(super.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				request.getRequestDispatcher("principal/principal.jsp").forward(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String usuario_pai_id=request.getParameter("id");
			String numero=request.getParameter("numero");
			
			if(!daoTelefoneRepository.existeFone(numero, Long.valueOf(usuario_pai_id))) {
				ModelTelefone modelTelefone=new ModelTelefone();
				
				modelTelefone.setNumero(numero);
				modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioId(Long.parseLong(usuario_pai_id)));
				modelTelefone.setUsuario_cad_id(super.getUserLogadoObj(request));
				
				daoTelefoneRepository.gravarTelefone(modelTelefone);
				
				request.setAttribute("msg", "Telefone salvo com sucesso!");
				
			}else {
				request.setAttribute("msg", "Telefone já existe");
			}
			
			List<ModelTelefone> modelTelefones=daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id));
			
			ModelLogin modelLogin=daoUsuarioRepository.consultarUsuarioId(Long.parseLong(usuario_pai_id));
			
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefones", modelTelefones);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
