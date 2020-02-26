package jeuxAlignement;

import Jetons.Symbole;

public interface JeuxAbstrait {

	boolean jouerCoup(int... coordonnées);

	boolean estFini();

	Symbole getWinner();

	String règles();

	Symbole getJoueurCourant();
	
	int getNbreCoord();
}