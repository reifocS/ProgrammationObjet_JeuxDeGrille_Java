package Jeux;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Jetons.Symbole;

class MorpionSimpleTest {

	@Test
	void test() {
		JeuxDeGrille m = new MorpionSimple(6, 6, 4);
		assertTrue(m.estVide());
		assertTrue(m.getJoueurCourant() == Symbole.SYMBOLEJ1);
		m.jouerCoup(1, 4);
		assertTrue(m.getJoueurCourant() == Symbole.SYMBOLEJ2);
		assertFalse(m.estCoupLegal(1, 1));
		assertEquals(((MorpionSimple) m).getScore()[0], 0);
		m.jouerCoup(2, 4);
		m.jouerCoup(1, 5);
		m.jouerCoup(1, 6);
		m.jouerCoup(3, 3);
		m.jouerCoup(2, 2);
		m.jouerCoup(3, 1);
		m.jouerCoup(2, 1);
		m.jouerCoup(3, 2);
		m.jouerCoup(2, 3);
		m.jouerCoup(3, 4);
		System.out.println(m);
		assertTrue(((MorpionSimple) m).getScore()[0] == 1);
		assertTrue(((MorpionSimple) m).getScore()[1] == 1);
	}
}
