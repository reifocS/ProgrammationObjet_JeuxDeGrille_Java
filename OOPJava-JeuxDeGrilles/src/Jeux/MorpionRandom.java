package Jeux;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import Geometry.Direction;
import Jetons.Symbole;

public class MorpionRandom extends MorpionSimple {
	private int[] dernierCoupJoué;//On a besoin de cette information pour coupGagnant.

	protected void setDernierCoupJoué(int i, int j) {
		dernierCoupJoué[0] = i;
		dernierCoupJoué[1] = j;
	}
	public MorpionRandom() {
		this(6, 6, 3);
	}

	public MorpionRandom(int i, int j, int k) {
		super(i, j, k);
		remplieAléatoirement();
		dernierCoupJoué = new int[] {0,0};

		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialise la grille donnée avec autant d'un symbole que de l'autre. Si le
	 * total de case de la grille est impair, le symbole privilégié est choisi
	 * alétoirement. La fonction commence par remplir une liste d'entiers pour
	 * chaque case de la grille. Un indice est tiré aléatoirement dans cette liste.
	 * Un symbole est placé dans la case correspondante et l'indice est retiré de la
	 * liste. On repète le processus jusqu'à ce que la liste soit vide.
	 */
	private void remplieAléatoirement() {
		ArrayList<Integer> coordVides = new ArrayList<Integer>();
		Random r = new Random();
		boolean turn = true;
		if (this.getHauteur() * this.getLargeur() % 2 != 0) { // grille impair, choix du premier jeton aléatoire.
			int pair = r.nextInt(2);
			turn = pair == 0 ? true : false;
		}
		for (int i = 0; i < this.getHauteur() * this.getLargeur(); ++i)
			coordVides.add(i);
		while (!coordVides.isEmpty()) {
			Symbole s = (turn) ? Symbole.SYMBOLEJ1 : Symbole.SYMBOLEJ2;
			int c = r.nextInt(coordVides.size());
			int k = coordVides.remove(c);
			super.placerSymbole(k / getLargeur() + 1, k % getLargeur() + 1, s);
			turn = !turn;
		}

	}

	public boolean symbolesDifferents(int i, int j, int ii, int jj) {
		return getSymbole(i, j) != getSymbole(ii, jj);
	}

	@Override
	/**
	 * La partie doit s'arrêter quand il n'y plus assez de jetons d'un symbole ou de
	 * l'autre pour former un alignement.
	 */
	public boolean estFini() {
		int[] t = this.comptabiliserTotalJetons();
		return t[0] < getNbreAAligner() || t[1] < getNbreAAligner();
	}

	public int[] comptabiliserTotalJetons() {
		int countS1, countS2;
		countS1 = countS2 = 0;
		for (int i = 1; i <= getHauteur(); ++i) {
			for (int j = 1; j <= getLargeur(); ++j) {
				Symbole s = getSymbole(i, j);
				if (estOuvert(i, j)) {
					if (s == Symbole.SYMBOLEJ1)
						++countS1;
					else if (s == Symbole.SYMBOLEJ2)
						++countS2;
				}
			}
		}
		int[] totaux = new int[] {countS1,countS2};
		return totaux;
	}

	public boolean estLegal(int i, int j, int ii, int jj) {
		return estDansGrille(i, j) && estDansGrille(ii, jj) && symbolesDifferents(i, j, ii, jj);
	}

	/**
	 * Intervertit deux symboles en mettant à jour la liste des coordonnées fermées
	 * accordément.
	 * 
	 */
	private void intervertir(int i, int j, int ii, int jj, Symbole s1, Symbole s2) {
		placerSymbole(i, j, s2);
		placerSymbole(ii, jj, s1);
		if (!estOuvert(ii, jj) && estOuvert(i, j)) {
			super.fermeSymbole(i, j);
			super.ouvreSymbole(ii, jj);
		} else if (estOuvert(ii, jj) && !estOuvert(i, j)) {
			super.fermeSymbole(ii, jj);
			super.ouvreSymbole(i, j);
		}
	}
@Override
	public int getNbreCoord() {
		return 2;
	}
	@Override
	public boolean jouerCoup(int... coordonnées) {
		/* Récupération des coordonées */
		int i = coordonnées[0];
		int j = coordonnées[1];
		int ii = coordonnées[2];
		int jj = coordonnées[3];
		/* Vérification */
		if (!estLegal(i, j, ii, jj))
			return false;
		Symbole s1 = getSymbole(i, j);
		Symbole s2 = getSymbole(ii, jj);
		intervertir(i, j, ii, jj, s1, s2);
		setDernierCoupJoué(i, j);
		if (coupGagnant()) {
			System.out.println("Un alignement a ete forme pour le joueur " + s2.getValue() + " en ligne : " + i
					+ ", colonne : " + j);
			incrementScore(s2);
		}
		setDernierCoupJoué(ii, jj);
		if (coupGagnant()) {
			System.out.println("Un alignement a ete forme pour le joueur " + s1.getValue() + " en ligne : " + ii
					+ ", colonne : " + jj);
			incrementScore(s1);
		}
		changeJoueurCourant();
		return true;
	}

	/**
	 * La particularité de cette fonction est qu'elle ne vérifie que les alentours
	 * du dernier symbole placé. Elle met également à jour la liste des coordonnées
	 * fermées si elle trouve un alignement. La fonction retourne vraie au premier
	 * alignement trouvé, car il est dit dans le sujet qu'un même symbole ne peut
	 * participer qu'à un seul alignement. L'ordre de priorité est arbitraire.
	 */
	public boolean coupGagnant() {
		int x = dernierCoupJoué[0];
		int y = dernierCoupJoué[1];
		Symbole actuel = getSymbole(x, y);
		// Par précaution un symbole fermé ou un symbole vide ne peut pas former
		// d'alignement.
		if (!estOuvert(x, y) || actuel == Symbole.VIDE)
			return false;
		Direction[] dir = new Direction[] { Direction.SUD, Direction.EST, Direction.SUDEST, Direction.SUDOUEST };
		// Pour chaque direction
		for (Direction d : dir) {
			/* réinitialisation des paramètres */
			boolean flag1, flag2;// deux indicateurs pour une direction et son inverse, ils indiquent le résultat
									// de la recherche.
			LinkedList<Integer[]> dFerme = new LinkedList<Integer[]>();// On y stocke les coordonnées visitées dans la
																	// direction donnée.
			LinkedList<Integer[]> dInvFerme = new LinkedList<Integer[]>();// idem pour l'inverse de la direction donnée.
			flag1 = flag2 = true;
			int cpt = getNbreAAligner() - 1;
			/* début de la recherche */
			// On va simultanément rechercher dans deux sens, une direction et son inverse.
			// On continue de chercher tant que le compteur n'est pas à 0 ou que les deux
			// flags ne sont pas à faux.
			for (int k = 1; cpt > 0 && (flag1 || flag2); ++k) {
				int xx = x + k * d.getDx(); // La nouvelle ligne à visiter.
				int yy = y + k * d.getDy(); // La nouvelle colonne.
				int xinv = x + k * d.inverser().getDx(); // Idem mais dans le sens opposé.
				int yinv = y + k * d.inverser().getDy();
				// Si le symbole contigu est le bon, on décrémente le compteur.
				if (flag1 && estDansGrille(xx, yy) && getSymbole(xx, yy) == actuel && estOuvert(xx, yy)) {
					dFerme.add(new Integer[] {xx, yy});
					--cpt;
				} else // Sinon il ne sert plus à rien de chercher dans cette direction.
					flag1 = false;
				if (flag2 && cpt > 0 && estDansGrille(xinv, yinv) && getSymbole(xinv, yinv) == actuel
						&& estOuvert(xinv, yinv)) {
					dInvFerme.add(new Integer[] {xinv, yinv});
					--cpt;
				} else
					flag2 = false;
			}
			if (cpt == 0) {// Si en sortant de la boucle, le compteur est à 0, le jeton placé a contribué à
							// former un alignement.
				super.fermeSymbole(x, y);// On met à jour la liste des coordonnées fermées.
				for(Integer[] i : dFerme)
					fermeSymbole(i[0],i[1]);
				for(Integer[] i : dInvFerme)
					fermeSymbole(i[0],i[1]);					
				return true;
			}
		}
		return false;
	}
	public String règles() {
		String s;
		s = "Vous avez choisi de jouer au jeu du morpion aléatoire avec les paramètres suivants\nHauteur : "
				+ getHauteur() + "\nLargeur : " + getLargeur() + "\nSymboles à aligner : " + super.getNbreAAligner();
		s += "\nPour jouer vous devez saisir les coordonnées de deux jetons de symboles différents, vous avez donc deux saisies à faire.";
		return s;
		// TODO Auto-generated method stub

	}
}
