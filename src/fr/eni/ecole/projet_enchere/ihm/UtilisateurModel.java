package fr.eni.ecole.projet_enchere.ihm;

import java.util.ArrayList;
import java.util.List;


import fr.eni.ecole.projet_enchere.bo.Utilisateur;


public class UtilisateurModel {

	private Utilisateur utilisateur;
	private List<Utilisateur> lstUtilisateur = new ArrayList<Utilisateur>();
	
	public UtilisateurModel (Utilisateur utilisateur, List<Utilisateur> lstutilisateur) {
		super();
		this.utilisateur = utilisateur;
		this.lstUtilisateur = lstutilisateur;
	}
		
	public UtilisateurModel() {
	super();
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	public void setUtilisateur(Utilisateur utilisateur) {
	this.utilisateur = utilisateur;
		}
	public List<Utilisateur> getLstUtilisateur() {
		return lstUtilisateur;
	}
	
	public void setLstUtilisateur(List<Utilisateur> lstutilisateur) {
	this.lstUtilisateur = lstutilisateur;
	}


@Override
public String toString() {
	return "UtilisateurModel [utilisateur=" + utilisateur + ", lstUtilisateur=" + lstUtilisateur + "]";
}

}