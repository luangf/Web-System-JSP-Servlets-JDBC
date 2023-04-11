package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDTOGraficoSalarioUser;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import util.ReportUtil;

@MultipartConfig //upload
@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository=new DAOUsuarioRepository();
	
    public ServletUsuarioController() {
        super();
    }

    //consultar deletar
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao=request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String idUser=request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(super.getUserLogado(request)));
				request.setAttribute("msg", "Excluido com Sucesso!");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				String idUser=request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				response.getWriter().write("Excluido com Sucesso!"); //com ajax resposta assim
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				String nomeBusca=request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser=daoUsuarioRepository.consultarUsuarioList(nomeBusca, super.getUserLogado(request));
				
				//list to String json
				ObjectMapper mapper=new ObjectMapper();
				String json=mapper.writeValueAsString(dadosJsonUser);
				
				//Adicionar para o cabeçalho, pra n misturar com o json tbm
				//Concatenar com String, apenas ""+ , de int->string
				response.addHeader("totalPaginas", ""+daoUsuarioRepository.consultarUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				//converter JSON para String para passar como resposta aqui
				response.getWriter().write(json); //com ajax resposta assim
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				String nomeBusca=request.getParameter("nomeBusca");
				String pagina=request.getParameter("pagina");
				
				List<ModelLogin> dadosJsonUser=daoUsuarioRepository.consultarUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
				
				//list to String json
				ObjectMapper mapper=new ObjectMapper();
				String json=mapper.writeValueAsString(dadosJsonUser);
				
				//Adicionar para o cabeçalho, pra n misturar com o json tbm
				//Concatenar com String, apenas ""+ , de int->string
				response.addHeader("totalPaginas", ""+daoUsuarioRepository.consultarUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				//converter JSON para String para passar como resposta aqui
				response.getWriter().write(json); //com ajax resposta assim
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String id=request.getParameter("id");
				
				ModelLogin modelLogin=daoUsuarioRepository.consultarUsuarioId(id, super.getUserLogado(request));
				
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(super.getUserLogado(request)));
				request.setAttribute("msg", "Usuário em Edição");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsers")) {
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(super.getUserLogado(request)));
				request.setAttribute("msg", "Usuários carregados");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser=request.getParameter("id");
				
				ModelLogin modelLogin=daoUsuarioRepository.consultarUsuarioId(idUser, super.getUserLogado(request));
				
				if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					response.setHeader("Content-Disposition", "attachment;filename=arquivo."+modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				}
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset=Integer.parseInt(request.getParameter("pagina"));
				
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioListPaginada(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
				String dataInicial=request.getParameter("dataInicial");
				String dataFinal=request.getParameter("dataFinal");
				
				//caso quiser imprimir td, nao foi informado as datas
				if(dataInicial==null || dataInicial.isEmpty() && dataFinal==null || dataFinal.isEmpty()) {
					request.setAttribute("listaUser", daoUsuarioRepository.consultarUsuarioListRel(super.getUserLogado(request)));
				}else {
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
				}
				
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")
					|| acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
				String dataInicial=request.getParameter("dataInicial");
				String dataFinal=request.getParameter("dataFinal");
				
				List<ModelLogin> modelLogins=null;
				
				if(dataInicial==null || dataInicial.isEmpty() && dataFinal==null || dataFinal.isEmpty()) {
					modelLogins=daoUsuarioRepository.consultarUsuarioListRel(super.getUserLogado(request));
				}else {
					modelLogins=daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);
				}
				
				HashMap<String, Object> params=new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
				
				byte[] relatorio=null;
				String extensao="";
				
				if(acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
					relatorio=new ReportUtil().geraRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());
					extensao="pdf";
				}else if(acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
					relatorio=new ReportUtil().geraRelatorioExcel(modelLogins, "rel-user-jsp", params, request.getServletContext());
					extensao="xls";
				}
				
				response.setHeader("Content-Disposition", "attachment;filename=arquivo."+extensao);
				response.getOutputStream().write(relatorio);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")){
				String dataInicial=request.getParameter("dataInicial");
				String dataFinal=request.getParameter("dataFinal");
				
				if(dataInicial==null || dataInicial.isEmpty() && dataFinal==null || dataFinal.isEmpty()) {
					BeanDTOGraficoSalarioUser beanDTOGraficoSalarioUser=daoUsuarioRepository
							.montarGraficoMediaSalario(super.getUserLogado(request));
					
					//list to String json
					ObjectMapper mapper=new ObjectMapper();
					String json=mapper.writeValueAsString(beanDTOGraficoSalarioUser);
					
					//converter JSON para String para passar como resposta aqui
					response.getWriter().write(json); //com ajax resposta assim
					
				}else {
					BeanDTOGraficoSalarioUser beanDTOGraficoSalarioUser=daoUsuarioRepository
							.montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);
					
					ObjectMapper mapper=new ObjectMapper();
					String json=mapper.writeValueAsString(beanDTOGraficoSalarioUser);
					
					response.getWriter().write(json);
				}
				
			}else {
				List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(super.getUserLogado(request)));
				request.setAttribute("msg", "Usuários carregados");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar=request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	//salvar atualizar
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String msg="";
			
			String id=request.getParameter("id");
			String nome=request.getParameter("nome");
			String email=request.getParameter("email");
			String login=request.getParameter("login");
			String senha=request.getParameter("senha");
			String perfil=request.getParameter("perfil");
			String sexo=request.getParameter("sexo");
			//Web Service CEP
			String cep=request.getParameter("cep");
			String logradouro=request.getParameter("logradouro");
			String complemento=request.getParameter("complemento");
			String bairro=request.getParameter("bairro");
			String localidade=request.getParameter("localidade");
			String uf=request.getParameter("uf");
			String dataNascimento=request.getParameter("dataNascimento");
			String rendaMensal=request.getParameter("rendaMensal");
			rendaMensal=rendaMensal.split("\\ ")[1].replaceAll("\\.","").replaceAll("\\,", ".");
			
			ModelLogin modelLogin=new ModelLogin();
			
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			//Web Service CEP
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setComplemento(complemento);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
			modelLogin.setRendaMensal(Double.valueOf(rendaMensal));
			
			if(ServletFileUpload.isMultipartContent(request)) {
				Part part=request.getPart("fileFoto"); //pega foto da tela
				
				if(part.getSize() > 0) {
					byte[] foto=IOUtils.toByteArray(part.getInputStream()); //converte imagem para byte[]
					//padrao de formatação para as imagens...
					String imagemBase64="data:image/"+part.getContentType().split("\\/")[1]+";base64,"+new Base64().encodeBase64String(foto); //converte imagem em byte[] para String
					
					modelLogin.setFotouser(imagemBase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
				}
			}
			
			//Usuário com esse login já existe e a pessoa ta tentando gravar(Ñ editar, gravar)(novo)
			if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg="Usuário com esse Login já existe, informe outro Login...";
			}else { //gravar/editar
				if(modelLogin.isNovo()) {
					msg="Usuário cadastrado com sucesso!";
				}else {
					msg="Usuário atualizado com sucesso!";
				}
				modelLogin=daoUsuarioRepository.gravarUser(modelLogin, super.getUserLogado(request)); //grava ou atualiza
			}
			
			List<ModelLogin> modelLogins=daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
			
			request.setAttribute("modelLogins", modelLogins);
			request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(super.getUserLogado(request)));
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar=request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
	
}
