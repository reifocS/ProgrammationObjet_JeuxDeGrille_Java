package Jeux;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import Geometry.Direction;
import Jetons.Symbole;

public class MorpionRandom extends MorpionSimple {
	private int[] dernierCoupJou�;//On a besoin de cette information pour coupGagnant.

	protected void setDernierCoupJou�(int i, int j) {
		dernierCoupJou�[0] = i;
		dernierCoupJou�[1] = j;
	}
	public MorpionRandom() {
		this(6, 6, 3);
	}

	public MorpionRandom(int i, int j, int k) {
		super(i, j, k);
		remplieAl�atoirement();
		dernierCoupJou� = new int[] {0,0};

		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialise la grille donn�e avec autant d'un symbole que de l'autre. Si le
	 * total de case de la grille est impair, le symbole privil�gi� est choisi
	 * al�toirement. La fonction commence par remplir une liste d'entiers pour
	 * chaque case de la grille. Un indice est tir� al�atoirement dans cette liste.
	 * Un symbole est plac� dans la case correspondante et l'indice est retir� de la
	 * liste. On rep�te le processus jusqu'� ce que la liste soit vide.
	 */
	private void remplieAl�atoirement() {
		ArrayList<Integer> coordVides = new ArrayList<Integer>();
		Random r = new Random();
		boolean turn = true;
		if (this.getHauteur() * this.getLargeur() % 2 != 0) { // grille impair, choix du premier jeton al�atoire.
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
	 * La partie doit s'arr�ter quand il n'y plus assez de jetons d'un symbole ou de
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
	 * Intervertit deux symboles en mettant � jour la liste des coordonn�es ferm�es
	 * accord�ment.
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
	public boolean jouerCoup(int... coordonn�es) {
		/* R�cup�ration des coordon�es */
		int i = coordonn�es[0];
		int j = coordonn�es[1];
		int ii = coordonn�es[2];
		int jj = coordonn�es[3];
		/* V�rification */
		if (!estLegal(i, j, ii, jj))
			return false;
		Symbole s1 = getSymbole(i, j);
		Symbole s2 = getSymbole(ii, jj);
		intervertir(i, j, ii, jj, s1, s2);
		setDernierCoupJou�(i, j);
		if (coupGagnant()) {
			System.out.println("Un alignement a ete forme pour le joueur " + s2.getValue() + " en ligne : " + i
					+ ", colonne : " + j);
			incrementScore(s2);
		}
		setDernierCoupJou�(ii, jj);
		if (coupGagnant()) {
			System.out.println("Un alignement a ete forme pour le joueur " + s1.getValue() + " en ligne : " + ii
					+ ", colonne : " + jj);
			incrementScore(s1);
		}
		changeJoueurCourant();
		return true;
	}

	/**
	 * La particularit� de cette fonction est qu'elle ne v�rifie que les alentours
	 * du dernier symbole plac�. Elle met �galement � jour la liste des coordonn�es
	 * ferm�es si elle trouve un alignement. La fonction retourne vraie au premier
	 * alignement trouv�, car il est dit dans le sujet qu'un m�me symbole ne peut
	 * participer qu'� un seul alignement. L'ordre de priorit� est arbitraire.
	 */
	public boolean coupGagnant() {
		int x = dernierCoupJou�[0];
		int y = dernierCoupJou�[1];
		Symbole actuel = getSymbole(x, y);
		// Par pr�caution un symbole ferm� ou un symbole vide ne peut pas former
		// d'alignement.
		if (!estOuvert(x, y) || actuel == Symbole.VIDE)
			return false;
		Direction[] dir = new Direction[] { Direction.SUD, Direction.EST, Direction.SUDEST, Direction.SUDOUEST };
		// Pour chaque direction
		for (Direction d : dir) {
			/* r�initialisation des param�tres */
			boolean flag1, flag2;// deux indicateurs pour une direction et son inverse, ils indiquent le r�sultat
									// de la recherche.
			LinkedList<Integer[]> dFerme = new LinkedList<Integer[]>();// On y stocke les coordonn�es visit�es dans la
																	// direction donn�e.
			LinkedList<Integer[]> dInvFerme = new LinkedList<Integer[]>();// idem pour l'inverse de la direction donn�e.
			flag1 = flag2 = true;
			int cpt = getNbreAAligner() - 1;
			/* d�but de la recherche */
			// On va simultan�ment rechercher dans deux sens, une direction et son inverse.
			// On continue de chercher tant que le compteur n'est pas � 0 ou que les deux
			// flags ne sont pas � faux.
			for (int k = 1; cpt > 0 && (flag1 || flag2); ++k) {
				int xx = x + k * d.getDx(); // La nouvelle ligne � visiter.
				int yy = y + k * d.getDy(); // La nouvelle colonne.
				int xinv = x + k * d.inverser().getDx(); // Idem mais dans le sens oppos�.
				int yinv = y + k * d.inverser().getDy();
				// Si le symbole contigu est le bon, on d�cr�mente le compteur.
				if (flag1 && estDansGrille(xx, yy) && getSymbole(xx, yy) == actuel && estOuvert(xx, yy)) {
					dFerme.add(new Integer[] {xx, yy});
					--cpt;
				} else // Sinon il ne sert plus � rien de chercher dans cette direction.
					flag1 = false;
				if (flag2 && cpt > 0 && estDansGrille(xinv, yinv) && getSymbole(xinv, yinv) == actuel
						&& estOuvert(xinv, yinv)) {
					dInvFerme.add(new Integer[] {xinv, yinv});
					--cpt;
				} else
					flag2 = false;
			}
			if (cpt == 0) {// Si en sortant de la boucle, le compteur est � 0, le jeton plac� a contribu� �
							// former un alignement.
				super.fermeSymbole(x, y);// On met � jour la liste des coordonn�es ferm�es.
				for(Integer[] i : dFerme)
					fermeSymbole(i[0],i[1]);
				for(Integer[] i : dInvFerme)
					fermeSymbole(i[0],i[1]);					
				return true;
			}
		}
		return false;
	}
	public String r�gles() {
		String s;
		s = "Vous avez choisi de jouer au jeu du morpion al�atoire avec les param�tres suivants\nHauteur : "
				+ getHauteur() + "\nLargeur : " + getLargeur() + "\nSymboles � aligner : " + super.getNbreAAligner();
		s += "\nPour jouer vous devez saisir les coordonn�es de deux jetons de symboles diff�rents, vous avez donc deux saisies � faire.";
		return s;
		// TODO Auto-generated method stub

	}
}
