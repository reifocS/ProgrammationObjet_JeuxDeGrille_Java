package Jeux;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Jetons.Symbole;

class TicTacToeTest {

	@Test
	void test() {
		JeuxDeGrille j = new TicTacToe();
		assertTrue(j.estVide());
		assertFalse(j.estFini());
		assertTrue(j.getJoueurCourant() == Symbole.SYMBOLEJ1);
		assertEquals(Symbole.SYMBOLEJ1, j.getSymbole(1, 1));
		assertTrue(j.getJoueurCourant() == Symbole.SYMBOLEJ2);
		assertFalse(j.estCoupLegal(1, 1));
		j.jouerCoup(2, 1);
		assertEquals(Symbole.SYMBOLEJ2, j.getSymbole(2, 1));
		j.jouerCoup(1, 2);
		j.jouerCoup(3, 1);
		j.jouerCoup(1, 3);
		assertTrue(j.estFini());
		assertEquals(j.getWinner(), Symbole.SYMBOLEJ1);
	}

}
