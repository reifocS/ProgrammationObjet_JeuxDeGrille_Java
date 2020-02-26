package Jeux;

import Jetons.Symbole;
import jeuxAlignement.JeuxAbstrait;

public abstract class JeuxDeGrille implements JeuxAbstrait {
	private Symbole[][] grille;
	private Symbole joueurCourant;
	private int nbreAAligner;

	/*---------------------------------*/
	/* Méthodes abstraites */
	/*---------------------------------*/
	public abstract boolean estCoupLegal(int ligne, int colonne);

	public abstract boolean coupGagnant();

	@Override
	public abstract boolean jouerCoup(int... coordonnées);

	@Override
	public abstract boolean estFini();

	@Override
	public abstract Symbole getWinner();

	@Override
	public abstract String règles();

	public abstract int getNbreCoord();// Le nombre de coordonnées à récupérer de l'utilisateur.

	/*---------------------------------------------*/
	/* Méthodes hérités */
	/*---------------------------------------------*/
	

	public int getHauteur() {
		return grille.length;
	}

	public int getLargeur() {
		return grille[0].length;
	}
	
	public JeuxDeGrille(int nbreLignes, int nbreColonnes, int nbreAAligner) {
		assert (nbreLignes > 0 && nbreColonnes > 0);
		grille = new Symbole[nbreLignes][nbreColonnes];
		joueurCourant = Symbole.values()[1];
		this.nbreAAligner = nbreAAligner;
		initialiseGrille();
	}

	private void initialiseGrille() {
		for (int i = 0; i < grille.length; ++i) {
			for (int j = 0; j < grille[0].length; ++j) {
				grille[i][j] = Symbole.VIDE;
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%6s", ""));
		for (int i = 1; i <= grille.length; ++i)
			sb.append(String.format("%2d%2s", i, ""));
		sb.append("\n");
		int cpt = 1;
		for (Symbole[] ligne : grille) {
			sb.append(String.format("%5d ", cpt++));
			for (Symbole sym : ligne) {
				sb.append("[" + sym.getValue() + "] ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	protected void changeJoueurCourant() {
		joueurCourant = (joueurCourant == Symbole.SYMBOLEJ1) ? Symbole.SYMBOLEJ2 : Symbole.SYMBOLEJ1;
	}

	public Symbole getSymbole(int ligne, int colonne) {
		assert (estDansGrille(ligne, colonne));
		return grille[ligne - 1][colonne - 1];
	}

	protected void placerSymbole(int ligne, int colonne, Symbole s) {
		assert (estDansGrille(ligne, colonne));
		grille[ligne - 1][colonne - 1] = s;
	}

	public boolean estPleine() {
		for (Symbole[] ligne : grille) {
			for (Symbole s : ligne)
				if (s == Symbole.VIDE)
					return false;
		}
		return true;
	}

	public boolean estVide() {
		for (Symbole[] ligne : grille) {
			for (Symbole s : ligne)
				if (s != Symbole.VIDE)
					return false;
		}
		return true;
	}

	public boolean estLibre(int i, int j) {
		return getSymbole(i, j) == Symbole.VIDE;
	}

	public boolean estDansGrille(int ligne, int colonne) {
		return ligne <= grille.length && ligne > 0 && colonne > 0 && colonne <= grille[0].length;
	}

	@Override
	public Symbole getJoueurCourant() {
		return joueurCourant;
	}

	public int getNbreAAligner() {
		return nbreAAligner;
	}

}
