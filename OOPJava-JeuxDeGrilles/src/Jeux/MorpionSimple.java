package Jeux;

import Geometry.Direction;
import Jetons.Symbole;

public class MorpionSimple extends JeuxDeGrille{
	private boolean[][] ouvert;
	private static final Direction[] dir = new Direction[] {Direction.NORD,Direction.OUEST,Direction.NORDEST,Direction.NORDOUEST};
	private int scores[];

	public MorpionSimple(int nbreLignes, int nbreColonnes, int nbreAAligner) {
		super(nbreLignes, nbreColonnes, nbreAAligner);
		scores = new int[2];
		ouvert = new boolean[nbreLignes+1][nbreColonnes+1];
		for (int i = 1; i < ouvert.length; ++i) {
			for (int j = 1; j < ouvert[0].length; ++j)
				ouvert[i][j] = true;
		}
		// TODO Auto-generated constructor stub
	}
	protected void incrementScore(Symbole s) {
		assert (s != Symbole.VIDE);
		++scores[s.ordinal() - 1];// -1 Car symbole VIDE en première place de l'enum.
	}

	public int[] getScore() {
		return scores;
	}
	@Override
	public boolean estCoupLegal(int ligne, int colonne) {
		if (!estDansGrille(ligne, colonne))
			return false;
		if (estVide())
			return true;
		return estAdjacent(ligne, colonne) && estLibre(ligne, colonne);
	}

	public boolean estAdjacent(int ligne, int colonne) {
		for (Direction dir : Direction.values()) {
			if (estDansGrille(ligne + dir.getDx(), colonne + dir.getDy())
					&& !estLibre(ligne + dir.getDx(), colonne + dir.getDy())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean coupGagnant() {
		//Pour chaque case,
		for (int i = 1; i <= super.getHauteur(); ++i) {
			//Pour chaque colonne
			for (int j = 1; j <= super.getLargeur(); ++j) {
				//On ne vérifie pas les cases vides ou les symboles fermés
				if (getSymbole(i,j) == Symbole.VIDE || !ouvert[i][j])
					continue;
				for (Direction d : dir) {
					int dx = d.getDx();
					int dy = d.getDy();
					int k = 1;
					for (; k < super.getNbreAAligner() && super.estDansGrille(i+k*dx, j+k*dy)
							&& getSymbole(i+k*dx,j+k*dy) == getSymbole(i,j)
							&& ouvert[i + k *dx][j + k * dy]; ++k)
						;
					if (k == getNbreAAligner()) {//alors il y a un alignement
						for (int kk = 0; kk < getNbreAAligner(); ++kk)
							fermeSymbole(i + kk * dx,j+kk*dy);
						return true;
					}
				}
			}
		}
		return false;
	}
	protected void fermeSymbole(int i, int j) {
		ouvert[i][j] = false;
	}
	public boolean estOuvert(int i, int j) {
		return ouvert[i][j];
	}
	@Override
	public boolean jouerCoup(int... coordonnées) {
		int ligne = coordonnées[0];
		int col = coordonnées[1];
		if (!estCoupLegal(ligne, col))
			return false;
		placerSymbole(ligne, col, getJoueurCourant());
		if (coupGagnant())
			incrementScore(getJoueurCourant());
		changeJoueurCourant();
		return true;
	}

	@Override
	public boolean estFini() {
		// TODO Auto-generated method stub
		return estPleine();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%6s", ""));
		for (int i = 1; i <= getLargeur(); ++i)
			sb.append(String.format("%2d%2s", i, ""));
		sb.append("\n");
		for (int i = 1; i <= getHauteur(); ++i) {
			sb.append(String.format("%5d ", i));
			for (int j = 1; j <= getLargeur(); ++j) {
				char c = ouvert[i][j] ? getSymbole(i, j).getValue()
						: Character.toLowerCase(getSymbole(i, j).getValue());
				sb.append("[" + c + "] ");
			}
			sb.append("\n");
		}
		int s[] = getScore();
		sb.append(Symbole.SYMBOLEJ1.getValue() + ": " + s[0] + ", " + Symbole.SYMBOLEJ2.getValue() + " : " + s[1]);
		return sb.toString();
	}

	@Override
	public Symbole getWinner() {
		int s[] = getScore();
		if (s[0] > s[1])
			return Symbole.SYMBOLEJ1;
		if (s[0] < s[1])
			return Symbole.SYMBOLEJ2;
		return Symbole.VIDE;
	}

	@Override
	public String règles() {
		String s;
		s = "Vous avez choisi de jouer au jeu du morpion avec les paramètres suivants\nHauteur : " + getHauteur()
				+ "\nLargeur : " + getLargeur() + "\nSymboles à aligner : " + super.getNbreAAligner();
		return s;
		// TODO Auto-generated method stub

	}

	@Override
	public int getNbreCoord() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void ouvreSymbole(int ii, int jj) {
		// TODO Auto-generated method stub
		ouvert[ii][jj] = true;
		
	}
	
}
