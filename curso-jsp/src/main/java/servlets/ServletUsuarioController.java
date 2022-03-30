package servlets;

import java.io.File;
import java.io.IOException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoSalarioUser;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;//bug
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import util.ReportUtil;

//Mapeamento, parte url que vem no navegador...
@WebServlet(urlPatterns={"/ServletUsuarioController","/usuario.jsp"})//bug
//@WebServlet("/ServletUsuarioController")//bug
@MultipartConfig
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// normalmente para deletar e consultar usa get
		try {//sem erro
			String acao = request.getParameter("acao");
			
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {//?acao=deletar
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);

				List<ModelLogin> modelLogins=daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluído com sucesso!");//resposta usada trabalhando com servlet
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {//?acao=deletarajax
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);
				response.getWriter().write("Excluído com sucesso!");//resposta usada trabalhando com ajax
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {//?acao=buscarUserAjax
				String nomeBusca = request.getParameter("nomeBusca");
				List<ModelLogin> dadosJsonUser=daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				ObjectMapper mapper=new ObjectMapper();
				String json=mapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPaginas", ""+daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {//?acao=buscarEditar
				String id=request.getParameter("id");
				ModelLogin modelLogin=daoUsuarioRepository.consultaUsuarioId(id, super.getUserLogado(request));
				
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {//?acao=listarUser
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuários carregados");
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser=request.getParameter("id");
				ModelLogin modelLogin=daoUsuarioRepository.consultaUsuarioId(idUser, super.getUserLogado(request));
				if(modelLogin.getFotoUser() != null && !modelLogin.getFotoUser().isEmpty()) {
					response.setHeader("Content-Disposition", "attachment;filename=arquivo."+modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotoUser().split("\\,")[1]));
				}
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset=Integer.parseInt(request.getParameter("pagina"));
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultaUsuarioListPaginado(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
				String dataInicial=request.getParameter("dataInicial");				String dataFinal=request.getParameter("dataFinal");
				
				//caso quiser imprimir td, nao foi informado as datas
				if(dataInicial==null || dataInicial.isEmpty() && dataFinal==null || dataFinal.isEmpty()) {
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				}else {
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
				}
				
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")) { //acao=imprimirRelatorioPDF
				String dataInicial=request.getParameter("dataInicial");
				String dataFinal=request.getParameter("dataFinal");
				List<ModelLogin> modelLogins=null;
				if(dataInicial==null || dataInicial.isEmpty() && dataFinal==null || dataFinal.isEmpty()) {//nao foi informado data
					modelLogins=daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));
				}else {//foi informado data
					modelLogins=daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);
				}
				HashMap<String, Object> params=new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
				//se foi informado data ou nao;
				byte[] relatorio=new ReportUtil().geraRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());
				
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) { //acao=graficoSalario
				String dataInicial=request.getParameter("dataInicial");
				String dataFinal=request.getParameter("dataFinal");
				
				if(dataInicial==null || dataInicial.isEmpty() && dataFinal==null || dataFinal.isEmpty()) {//nao foi informado data
					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser=daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request));
					ObjectMapper mapper=new ObjectMapper();
					String json=mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					response.getWriter().write(json);
				}else {//foi informado data
					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser=daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);
					ObjectMapper mapper=new ObjectMapper();
					String json=mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					response.getWriter().write(json);
				}
				
			}else { //se a acao n for nenhum desses
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
		} catch (Exception e) {//erro
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// normalmente para atualizar e gravar usa post
		try {
			String msg = "Operação realizada com sucesso!";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil= request.getParameter("perfil");
			String sexo= request.getParameter("sexo");
			String cep= request.getParameter("cep");
			String logradouro= request.getParameter("logradouro");
			String bairro= request.getParameter("bairro");
			String localidade= request.getParameter("localidade");
			String uf= request.getParameter("uf");
			String numero= request.getParameter("numero");
			String dataNascimento= request.getParameter("dataNascimento");
			String rendaMensal= request.getParameter("rendaMensal"); //vem string como ex.: R$ 122.122,23
			rendaMensal=rendaMensal.split("\\ ")[1].replaceAll("\\.","").replaceAll("\\,",".");
			
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);//parseLong(tava String), se existe um número, se sim, se nao...a servlet recebe os parametros por String
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento)))); //transforma String e Date
			modelLogin.setRendaMensal(Double.valueOf(rendaMensal));

			//Foto
			if(ServletFileUpload.isMultipartContent(request)) {
				Part part=request.getPart("fileFoto"); //pega foto da tela
				if(part.getSize()>0) {
					byte[] foto=IOUtils.toByteArray(part.getInputStream()); //converte a imagem para bytes
					String imagemBase64="data:image/"+part.getContentType().split("\\/")[1]+";base64,"+new Base64().encodeBase64String(foto); //String gigante)
					
					modelLogin.setFotoUser(imagemBase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);//
				}
			}
			
			if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe usuário com o mesmo login, informe outro login";
			} else {
				if (modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				} else {
					msg = "Atualizado com sucesso!";
				}
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}

			List<ModelLogin> modelLogins=daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
