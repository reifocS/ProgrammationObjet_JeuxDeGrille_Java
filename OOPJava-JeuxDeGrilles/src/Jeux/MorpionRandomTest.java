package Jeux;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MorpionRandomTest {

	@Test
	void test() {
		JeuxDeGrille m = new MorpionRandom();
		int[] t = ((MorpionRandom) m).comptabiliserTotalJetons();
		assertTrue(t[0] == t[1] && t[0] == (m.getHauteur() * m.getLargeur()) / 2);
		assertTrue(m.estPleine());
		m = new MorpionRandom(7,7,3);
		t = ((MorpionRandom) m).comptabiliserTotalJetons();
		assertTrue(t[0] == t[1] + 1 || t[1] == t[0] + 1);
		assertTrue(m.estPleine());
	}

}
