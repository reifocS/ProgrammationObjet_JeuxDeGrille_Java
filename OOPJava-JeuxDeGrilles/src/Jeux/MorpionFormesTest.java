package Jeux;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Jeux.MorpionFormes.TypeAlign;

class MorpionFormesTest {

	@Test
	void test() {
		MorpionFormes m = new MorpionFormes(6, 6, TypeAlign.T);
		// test du T
		m.jouerCoup(2, 2);
		m.jouerCoup(2, 1);
		m.jouerCoup(2, 3);
		m.jouerCoup(3, 1);
		m.jouerCoup(3, 2);
		m.jouerCoup(4, 1);
		m.jouerCoup(1, 1);
		m.jouerCoup(5, 1);
		m.jouerCoup(1, 2);
		m.jouerCoup(6, 1);
		m.jouerCoup(1, 3);
		assertEquals(1, m.getScore()[0]);
		assertTrue(!m.estOuvert(3, 2));
		assertTrue(!m.estOuvert(2, 2));
		assertTrue(!m.estOuvert(1, 2));
		assertTrue(!m.estOuvert(1, 1));
		assertTrue(!m.estOuvert(1, 3));
		m = new MorpionFormes(6, 6, TypeAlign.CROIX);
		m.jouerCoup(2, 2);
		m.jouerCoup(2, 1);
		m.jouerCoup(1, 1);
		m.jouerCoup(3, 2);
		m.jouerCoup(3, 3);
		m.jouerCoup(1, 2);
		m.jouerCoup(1, 3);
		m.jouerCoup(2, 3);
		m.jouerCoup(3, 1);
		assertEquals(1, m.getScore()[0]);
		assertTrue(!m.estOuvert(1, 1));
		assertTrue(!m.estOuvert(1, 3));
		assertTrue(!m.estOuvert(2, 2));
		assertTrue(!m.estOuvert(3, 1));
		assertTrue(!m.estOuvert(3, 3));
	}
}
