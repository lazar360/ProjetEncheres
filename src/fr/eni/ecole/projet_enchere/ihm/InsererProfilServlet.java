package fr.eni.ecole.projet_enchere.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.projet_enchere.bll.BLLException;
import fr.eni.ecole.projet_enchere.bll.BllFactory;
import fr.eni.ecole.projet_enchere.bll.UtilisateurManager;
import fr.eni.ecole.projet_enchere.bo.Utilisateur;

/**
 * Servlet implementation class InsererUtilisateurServlet
 */
@WebServlet("/InsererProfilServlet")
public class InsererProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager utilManager = BllFactory.getUniqueUtilisateurManager();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsererProfilServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErreurModel errModel = new ErreurModel();
		LoginModel logModel = (LoginModel) request.getSession().getAttribute("logModel");
		if (logModel == null) {
			logModel = new LoginModel(new Utilisateur("", "", "", "", "", "", "", "", "", 0, false,true));
		}
		InsererProfilModel insModel = null;
		String nextPage = "/WEB-INF/insertion.jsp";
		Boolean retourAccueil = false;
		
		insModel = new InsererProfilModel(new Utilisateur("", "", "", "", "", "", "", "", "", 0, false,true));

		if ("creer".equals(request.getParameter("formulaireInscription"))) {
			insModel.getUtilisateur().setPseudo(request.getParameter("pseudo"));
			insModel.getUtilisateur().setPrenom(request.getParameter("prenom"));
			insModel.getUtilisateur().setTelephone(request.getParameter("telephone"));
			insModel.getUtilisateur().setCodePostal(request.getParameter("cp"));
			insModel.getUtilisateur().setMotDePasse(request.getParameter("newPassword"));
			insModel.getUtilisateur().setNom(request.getParameter("nom"));
			insModel.getUtilisateur().setEmail(request.getParameter("email"));
			insModel.getUtilisateur().setRue(request.getParameter("rue"));
			insModel.getUtilisateur().setVille(request.getParameter("ville"));
	
			try {
				if (utilManager.newPassChecked(request.getParameter("newPassword"),
						request.getParameter("confPassword"))) {
					utilManager.addUtilisateur(insModel.getUtilisateur());
					logModel.setUtilisateur(insModel.getUtilisateur());
					request.getSession().setAttribute("logModel", logModel);
					retourAccueil = true;
				}
				
			} catch (BLLException e) {
				errModel.setErrMessages("ErrIns", e.getMessages());

			}
		}
		
		if ("annuler".equals(request.getParameter("formulaireAnnulation"))) {
			insModel = null;
			nextPage = "/AccueilServlet";
		}

		request.setAttribute("errModel", errModel);
		request.setAttribute("insModel", insModel);

		if (retourAccueil) {
			response.sendRedirect("AccueilServlet");
		} else {
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
