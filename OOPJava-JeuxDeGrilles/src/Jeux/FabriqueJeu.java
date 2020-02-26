package Jeux;

import Jeux.MorpionFormes.TypeAlign;
import jeuxAlignement.JeuxAbstrait;

public class FabriqueJeu {
	//valeurs par défaut. 
	static int jeu = 1;
	static int nbLignes = 6;
	static int nbColonnes = 6;
	static int align = 3;
	static TypeAlign forme = TypeAlign.CROIX;
	
	public JeuxAbstrait fabriqueJeu(String[] args) throws NumberFormatException{
		if (args.length == 0)
			return new TicTacToe();
		int [] config = new int [] { jeu, nbLignes,nbColonnes,align };
		//On remplace par les paramètres donnés.
		for (int i = 0; i < args.length;++i) {
			config[i] = Integer.parseInt(args[i]);
		}
		switch (config[0]) {
		case 1:
			return new TicTacToe();
		case 2:
			return new MorpionSimple(config[1], config[2], config[3]);
		case 3:
			return new MorpionRandom(config[1], config[2], config[3]);
		case 4:
			int index = config[3];
			if (index >= 0 && index < TypeAlign.values().length)
				return new MorpionFormes(config[1], config[2], TypeAlign.values()[index]);
			return new MorpionFormes(nbLignes, nbColonnes, forme);	
		default:
			return new TicTacToe();
		}
	}
}
