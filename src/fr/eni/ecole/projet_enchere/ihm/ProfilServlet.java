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
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/ProfilServlet")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtilisateurManager utilManager = BllFactory.getUniqueUtilisateurManager();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IHMException exception = new IHMException();
		ErreurModel errModel = new ErreurModel();
		LoginModel logModel = (LoginModel) request.getSession().getAttribute("logModel");
		ProfilModel profModel = null;
		try {
			profModel = new ProfilModel(utilManager.getUtilisateur(new Utilisateur(Integer.parseInt(request.getParameter("id")),"","","","","","","","","",0,false,true)));
		} catch (NumberFormatException e) {
			exception.ajoutMessage("id incorrecte");
		} catch (BLLException e) {
			errModel.setErrMessages("ErrGet", e.getMessages());
		}
		String nextPage = null;
		if (exception.estVide()) {
			nextPage = "/WEB-INF/profil.jsp";
		} else {
			errModel.setErrMessages("ErrId", exception.getMessages());
			nextPage = "AccueilServlet";
		}
		
		request.getSession().setAttribute("profModel", profModel);
		request.getSession().setAttribute("logModel", logModel);
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
