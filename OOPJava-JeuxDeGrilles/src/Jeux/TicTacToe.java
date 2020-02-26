package Jeux;

import java.util.Arrays;

import Jetons.Symbole;

public class TicTacToe extends JeuxDeGrille {
	// Utile pour vérifier les alignements.
	// On met à jour la somme de chaque ligne, colonne, diagonale à
	// chaque fois qu'un jeton est placé.
	// Une croix vaut 1, un rond -1.
	private Integer[] totaux;

	public Integer[] getTotaux() {
		return totaux;
	}

	public TicTacToe() {
		super(3, 3, 3);
		totaux = new Integer[8];// 3 lignes, 3colonnes, 2 diagonales
		Arrays.fill(totaux, 0);
	}

	@Override
	public boolean estCoupLegal(int ligne, int colonne) {
		return estDansGrille(ligne, colonne) && estLibre(ligne, colonne);
	}

	@Override
	public boolean estFini() {
		return estPleine() || coupGagnant();
	}

	@Override
	public boolean coupGagnant() {
		return Arrays.asList(totaux).contains(3) || Arrays.asList(totaux).contains(-3);
	}

	@Override
	public boolean jouerCoup(int... coordonnées) {
		/* Récupération des coordonnées */
		int ligne = coordonnées[0];
		int col = coordonnées[1];
		if (!estCoupLegal(ligne, col))
			return false;
		placerSymbole(ligne, col, getJoueurCourant());
		/* Une croix vaut +1, un rond vaut -1 */
		int ajout = getJoueurCourant() == Symbole.SYMBOLEJ1 ? +1 : -1;
		totaux[ligne - 1] += ajout;// Somme de la ligne jouée, -1 car décalage des indices par rapport au tableau.
		totaux[col + 2] += ajout;// Somme colonne jouée.
		if (ligne == col)// Si on a joué dans la diago 1
			totaux[6] += ajout;
		if (ligne + col == 4)// Si on a joué dans la diago 2
			totaux[7] += ajout;
		changeJoueurCourant();
		return true;
	}

	@Override
	public Symbole getWinner() {
		if (Arrays.asList(totaux).contains(3))
			return Symbole.SYMBOLEJ1;
		if (Arrays.asList(totaux).contains(-3))
			return Symbole.SYMBOLEJ2;
		return Symbole.VIDE;
	}

	@Override
	public String règles() {
		return "Vous avez choisi de jouer au TicTacToe";
		// TODO Auto-generated method stub

	}

	@Override
	public int getNbreCoord() {
		// TODO Auto-generated method stub
		return 1;
	}
}
