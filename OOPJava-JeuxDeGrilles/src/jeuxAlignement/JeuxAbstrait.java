package jeuxAlignement;

import Jetons.Symbole;

public interface JeuxAbstrait {

	boolean jouerCoup(int... coordonn�es);

	boolean estFini();

	Symbole getWinner();

	String r�gles();

	Symbole getJoueurCourant();
	
	int getNbreCoord();
}