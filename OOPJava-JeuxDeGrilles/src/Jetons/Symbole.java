package Jetons;

public enum Symbole {
	VIDE(' '), SYMBOLEJ1('X'), SYMBOLEJ2('O');

	private char value;

	private Symbole(char value) {
		this.value = value;
	}

	public char getValue() {
		return value;
	}

}
