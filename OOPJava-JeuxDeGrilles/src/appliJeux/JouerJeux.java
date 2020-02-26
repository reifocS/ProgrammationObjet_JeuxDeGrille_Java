package appliJeux;

import java.util.Scanner;

import Jetons.Symbole;
import Jeux.FabriqueJeu;
import jeuxAlignement.JeuxAbstrait;

public class JouerJeux {

	public static void main(String[] args) {
		/**
		 * Le format de args d�termine le type de partie lanc�. Le premier entier
		 * repr�sente l'application : 1 - tictactoe 2 - morpion 3 - morpion avec grille
		 * remplie al�atoirement 4 - morpion avec formes sp�ciales Les n entiers suivant
		 * doivent �tre s�par�s par un espace, ils repr�sentent respectivement : - la -
		 * la hauteur de la grille - la largeur de la grille - le nombre de jetons �
		 * aligner (devant �tre compris entre 3 et 5 comme indiqu� dans le sujet) OU la
		 * forme (si appli 4 choisie): 0 - Croix (x), 1 - Plus (+), 2 - t (T) Par
		 * d�fault les param�tres sont hauteur : 6, largeur : 6, alignement : 3. Si les
		 * arguments sont invalides c'est un tictactoe qui est lanc�. Si des arguments
		 * sont omis, ils sont compl�t�s par les valeurs par d�faut. Exemples : Les
		 * arguments : 2 Donneront lieu � une partie de morpion avec les valeurs par
		 * d�faut (6,6,3). Les arguments : 2 7 8 4 Donneront lieu � une partie de
		 * morpion sur une grille 7*8 avec 4 symboles � aligner. Les arguments : 4 11 5
		 * 2 Donneront lieu � une partie de morpion sur une grille 11*5 avec T comme
		 * forme d'alignement. Les arguments : 3 11 5 5 Donneront lieu � une partie de
		 * morpion al�atoire sur une grille 11-5 avec 5 jetons � aligner.
		 */
		System.out.println("+-----------------------------------------------+");
		System.out.println("|           Programmation Orient� Objet         |");
		System.out.println("|                                               |");
		System.out.println("| Auteurs : Nicolas Berthier, Vincent Escoffier |");
		System.out.println("+-----------------------------------------------+");
		Scanner sc = new Scanner(System.in);
		try {
			JeuxAbstrait j = new FabriqueJeu().fabriqueJeu(args);
			System.out.println(j.r�gles());
			/*---------------------------*/
			/* Debut de la boucle de jeu */
			/*---------------------------*/
			System.out.println(j);
			while (!j.estFini()) {
				int[] coord = { 0, 0, 0, 0 };
				int[] temp;
				do {
					for (int i = 0, k = 0; i < j.getNbreCoord(); ++i) {// Le nombre de saisies � r�cup�rer de
																		// l'utilisateur peut varier.
						System.out.println("Au tour du joueur " + j.getJoueurCourant().getValue() + " de jouer.");
						String str = sc.nextLine();
						if (validInput(str)) {
							temp = recupereEntiers(str);
							coord[k++] = temp[0];
							coord[k++] = temp[1];
						}
					}
				} while (!j.jouerCoup(coord) && messageErreur());// on ne sort que si jouerCoup renvoie vrai et donc
																	// qu'un
																	// jeton a �t� jou�.
																	// Auquel cas la seconde condition n'est
																	// m�me pas v�rifi� et le message d'erreur ne
																	// s'affiche
																	// pas.
				System.out.println(j);
			}
			String s = j.getWinner() == Symbole.VIDE ? "Partie nulle"
					: "Le joueur " + j.getWinner().getValue() + " gagne.";
			System.out.println(s);
			sc.close();
		} catch (NumberFormatException e) {
			System.out.println(
					"Format des param�tres invalide, 1 � 4 entiers naturels s�par�s par des espaces sont attendus (exemple : 2 7 11 5");
			System.exit(1);
		}
	}

	public static boolean validInput(String s) {
		return s.matches("\\d+\\s*-\\s*\\d+\\s*");
	}

	public static int[] recupereEntiers(String s) {
		assert (validInput(s));
		int[] c = new int[2];
		s = s.replaceAll("\\s+", "");
		Scanner sc = new Scanner(s);
		sc.useDelimiter("-");
		c[0] = sc.nextInt();
		c[1] = sc.nextInt();
		sc.close();
		return c;
	}

	public static boolean messageErreur() {
		System.out.println("Saisie invalide.");
		return true;
	}

}
