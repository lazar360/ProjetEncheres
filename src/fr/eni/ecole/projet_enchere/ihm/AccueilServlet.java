package fr.eni.ecole.projet_enchere.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.projet_enchere.bll.ArticleVenduManager;
import fr.eni.ecole.projet_enchere.bll.BLLException;
import fr.eni.ecole.projet_enchere.bll.BllFactory;
import fr.eni.ecole.projet_enchere.bll.CategorieManager;
import fr.eni.ecole.projet_enchere.bo.Categorie;
import fr.eni.ecole.projet_enchere.bo.Utilisateur;

/**
 * Servlet implementation class AccueilServlet
 */
@WebServlet({ "/AccueilServlet" })
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleVenduManager artVendManager = BllFactory.getUniqueArticleVenduManager();
	private CategorieManager catManager = BllFactory.getUniqueCategorieManager();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccueilServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Initialisation
		String nextPage = "/WEB-INF/accueil.jsp";
		ErreurModel errModel = new ErreurModel();
		LoginModel logModel = (LoginModel) request.getSession().getAttribute("logModel");
		if (logModel == null) {
			logModel = new LoginModel(new Utilisateur("", "", "", "", "", "", "", "", "", 0, false, true));
		}
		AccueilModel accModel = null;
		try {
			accModel = new AccueilModel("", new Categorie(-1,"Toutes"), catManager.getAllCategorie());
		} catch (BLLException e) {
			errModel.setErrMessages("ErrAcc", e.getMessages());
		}

		if ("radioAchats".equals(request.getParameter("menuRadio")))
			accModel.setLstRadio("radioAchats", true);
		else
			accModel.setLstRadio("radioAchats", false);

		if ("on".equals(request.getParameter("enchOuv")))
			accModel.setLstCheckbox("enchOuv", true);
		else
			accModel.setLstCheckbox("enchOuv", false);

		if ("on".equals(request.getParameter("enchCour")))
			accModel.setLstCheckbox("enchCour", true);
		else
			accModel.setLstCheckbox("enchCour", false);

		if ("on".equals(request.getParameter("enchRemp")))
			accModel.setLstCheckbox("enchRemp", true);
		else
			accModel.setLstCheckbox("enchRemp", false);

		if ("radioVentes".equals(request.getParameter("menuRadio")))
			accModel.setLstRadio("radioVentes", true);
		else
			accModel.setLstRadio("radioVentes", false);

		if ("on".equals(request.getParameter("ventCour")))
			accModel.setLstCheckbox("ventCour", true);
		else
			accModel.setLstCheckbox("ventCour", false);

		if ("on".equals(request.getParameter("ventDeb")))
			accModel.setLstCheckbox("ventDeb", true);
		else
			accModel.setLstCheckbox("ventDeb", false);

		if ("on".equals(request.getParameter("ventTer")))
			accModel.setLstCheckbox("ventTer", true);
		else
			accModel.setLstCheckbox("ventTer", false);

		if (request.getParameter("menuRadio") == null && request.getParameter("enchOuv") == null) {
			accModel.setLstRadio("radioAchats", true);
			accModel.setLstCheckbox("enchOuv", true);
		}

		if (request.getParameter("filtre") != null) {
			accModel.setFiltre(request.getParameter("filtre"));
		}

		// Traitement
		try {
			Integer catSel = null;
			if (request.getParameter("categorieSelect") != null) {
				catSel = Integer.parseInt(request.getParameter("categorieSelect"));
			} else {
				catSel = -1;
			}
			
			if (logModel.getUtilisateur().getNoUtilisateur() == null) {

				Boolean catChoisie = false;
				for (Categorie categorie : accModel.getLstCategorie()) {
					if (categorie.getNoCategorie().equals(catSel)) {
						accModel.setLstArticleVendu(
								artVendManager.getArticleVenduCategorieFiltre(categorie, accModel.getFiltre()));
						accModel.setCategorie(categorie);
						catChoisie = true;
						break;
					}
				}
				if (!catChoisie)
					accModel.setLstArticleVendu(artVendManager.getArticleVenduFiltre(accModel.getFiltre()));

			} else {

				if (accModel.getLstRadio().get("radioAchats")) {
					Boolean catChoisie = false;
					for (Categorie categorie : accModel.getLstCategorie()) {
						if (categorie.getNoCategorie().equals(catSel)) {
							accModel.setLstArticleVendu(artVendManager.getArticleVenduCategorieFiltreAchats(categorie,
									accModel.getFiltre(), accModel.getLstCheckbox().get("enchOuv"),
									accModel.getLstCheckbox().get("enchCour"),
									accModel.getLstCheckbox().get("enchRemp"), logModel.getUtilisateur()));
							accModel.setCategorie(categorie);
							catChoisie = true;
							break;
						}
					}
					if (!catChoisie)
						accModel.setLstArticleVendu(artVendManager.getArticleVenduFiltreAchats(accModel.getFiltre(),
								accModel.getLstCheckbox().get("enchOuv"), accModel.getLstCheckbox().get("enchCour"),
								accModel.getLstCheckbox().get("enchRemp"), logModel.getUtilisateur()));
				}
				if (accModel.getLstRadio().get("radioVentes")) {
					Boolean catChoisie = false;
					for (Categorie categorie : accModel.getLstCategorie()) {
						if (categorie.getNoCategorie().equals(catSel)) {
							accModel.setLstArticleVendu(artVendManager.getArticleVenduCategorieFiltreVentes(categorie,
									accModel.getFiltre(), accModel.getLstCheckbox().get("ventCour"),
									accModel.getLstCheckbox().get("ventDeb"), accModel.getLstCheckbox().get("ventTer"),
									logModel.getUtilisateur()));
							accModel.setCategorie(categorie);
							catChoisie = true;
							break;
						}
					}
					if (!catChoisie)
						accModel.setLstArticleVendu(artVendManager.getArticleVenduFiltreVentes(accModel.getFiltre(),
								accModel.getLstCheckbox().get("ventCour"), accModel.getLstCheckbox().get("ventDeb"),
								accModel.getLstCheckbox().get("ventTer"), logModel.getUtilisateur()));
				}

			}
		} catch (BLLException e) {
			errModel.setErrMessages("ErrAcc", e.getMessages());
		}

		// Affichage
		request.setAttribute("accModel", accModel);
		request.setAttribute("errModel", errModel);
		request.getSession().setAttribute("previousPage", "/AccueilServlet");
		request.getRequestDispatcher(nextPage).forward(request, response);

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
