package Jeux;

import Geometry.Direction;
import Jetons.Symbole;

public class MorpionFormes extends MorpionSimple {
	private TypeAlign type;

	enum TypeAlign {
		PLUS(Direction.EST, Direction.NORD, Direction.OUEST, Direction.SUD),
		CROIX(Direction.NORDEST, Direction.SUDEST, Direction.SUDOUEST, Direction.NORDOUEST),
		T(Direction.SUD, Direction.NORD, Direction.NORDEST, Direction.NORDOUEST);

		private Direction[] dir;

		private TypeAlign(Direction... dir) {
			this.dir = dir;
		}

		private Direction[] getDir() {
			return dir;
		}
	}

	public MorpionFormes(int i, int j, TypeAlign t) {
		super(i, j, 5);
		type = t;
	}

	public boolean coupGagnant() {
		Direction[] dir = type.getDir();
		for (int i = 2; i < this.getHauteur(); ++i) {
			for (int j = 2; j < this.getLargeur(); ++j) {
				Symbole actuel = getSymbole(i, j);
				if (actuel == Symbole.VIDE || !estOuvert(i, j))
					continue;
				int cpt = 0;
				for (Direction d : dir) {
					cpt = getSymbole(i + d.getDx(), j + d.getDy()) == actuel && estOuvert(i + d.getDx(), j + d.getDy())
							? cpt + 1
							: cpt;
				}
				if (cpt == 4) {
					for (Direction d : dir)
						super.fermeSymbole(i + d.getDx(), j + d.getDy());
					fermeSymbole(i, j);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Vérifie s'il est encore possible de former un alignement en se concentrant
	 * sur les cases vides de la grille.
	 * 
	 * @return
	 */
	public boolean verifieGrille() {
		Direction[] dir = type.getDir();
		for (int i = 2; i < this.getHauteur(); ++i) {
			for (int j = 2; j < this.getLargeur(); ++j) {
				if (!estLibre(i, j))
					continue;
				int cpt = 0;
				Symbole otherSymbole = null;
				for (Direction d : dir) {
					if (!estOuvert(i + d.getDx(), j + d.getDy()))
						break;
					Symbole adjacent = getSymbole(i + d.getDx(), j + d.getDy());
					if (adjacent != Symbole.VIDE && otherSymbole == null)// S'il existe un symbole non vide, on le
																			// mémorise.
					{
						otherSymbole = adjacent;
					} else if (adjacent != Symbole.VIDE && adjacent != otherSymbole)// Alors deux symboles différents
																					// sont en concurrence pour cet
																					// alignement.
						break;
					++cpt;
				}
				if (cpt == 4) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	/**
	 * La partie s'arrête quand plus aucun alignement ne peut être formé.
	 */
	public boolean estFini() {
		return !verifieGrille();
	}

	public String règles() {
		String s;
		s = "Vous avez choisi de jouer au jeu du morpion avec la forme suivante : " + type;
		return s;
		// TODO Auto-generated method stub

	}
}
