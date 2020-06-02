#include <iostream>
#include <conio.h>
#include <string>
#include <Windows.h>
#include <iomanip>
#include "Y:/console(v1.9).h"
#include <time.h>
using namespace std;

// CONSTANTES GLOBALES
// *******************

const int NB_COL_DAMIER= 12, NB_LIG_DAMIER= 8; // Nombre de colonnes et de lignes du damier

// DEFINITIONS DES TYPES GLOBAUX
// *****************************

enum Commandes // Code ascii des touches (flèches) de déplacement. 
//Vous devez utiliser ces valeurs symboliques
{
	KEY_DOWN		= 80,
	KEY_UP			= 72,	
	KEY_RIGHT		= 77,
	KEY_LEFT		= 75,
	KEY_UP_RIGHT	= 73,
	KEY_UP_LEFT		= 71,
	KEY_DOWN_LEFT	= 79,
	KEY_DOWN_RIGHT	= 81
};

struct Position // Pour mémoriser une position du curseur sur le damier du jeu
{
	int lig; // ligne du damier int x
	int col; // colonne du damier int y
};
Position caseJeu; // tableau de 2 coordonnées

struct Déplacement // Pour mémoriser un déplacement du curseur sur le damier du jeu
{
	Position dep; // case de départ du curseur
	Position arr; // case d'arrivée du curseur
};

enum État { BL, DC, DV, RO, MU }; // États possibles de chacune des cases du damier
/* 
	États possibles			Représentations graphiques

	BL = BLEUE				case bleue
	DC = DOLLARS CACHÉ		case bleue
	DV = DOLLARS VISIBLE	case $$$$ vert
	RO = ROSE				case rose
	MU = MUR				case noire
*/

struct Jeu // Pour mémoriser les différents aspects du jeu
{
	int ptsAccum = 0; // Points accumulés durant la partie. 0 au départ.

	// Damier du jeu avec les états initiaux de chacune des cases
	État damier [NB_LIG_DAMIER][NB_COL_DAMIER] =
	{
		{ BL , BL , BL , BL , BL , BL , BL , BL , MU , BL , BL , DC },
		{ BL , BL , MU , MU , BL , BL , BL , BL , BL , MU , BL , MU },
		{ BL , BL , MU , DC , MU , BL , BL , BL , BL , BL , MU , DC },
		{ BL , BL , MU , DC , MU , BL , BL , MU , MU , BL , MU , DC },
		{ DC , BL , MU , MU , MU , DC , MU , BL , MU , BL , MU , BL },
		{ DC , BL , DC , DC , BL , DC , MU , DC , MU , BL , MU , BL },
		{ DC , BL , DC , BL , BL , BL , MU , MU , MU , BL , MU , BL },
		{ DC , DC , BL , BL , BL , BL , BL , BL , BL , BL , BL , BL }
	};

	Déplacement depl; // Pour conserver le dernier déplacement du curseur dans le damier
};

// VARIABLES GLOBALES
// ******************
Jeu jeu; // Création du jeu en mémoire

void afficherPierre(string p, cvmColor c, int x, int y)
{
	cvmSetColor(c);
	gotoxy(x, y);
	cout << p ;
	gotoxy(x, y + 1);
	cout << p ;
}

void afficherCurseur(string c1, string c2, int x, int y)
{
	cvmSetColor(JAUNE);
	gotoxy(x, y);
	cout << c1;
	gotoxy(x, y + 1);
	cout << c2;
}

void main(void)
{
	const int NB_LIG_PIERRE = 2;
	const int NB_COL_PIERRE = 4;
	const int MAX_CASH = 15;
	const int MAX_MU = 8;
	const int MAX_MU_COIN = 3;

	const Position CASE_DEP = { 0, 0 };
	const Position XY_DEP = { 2, 10 };
		
	Déplacement caseJouee, pointXY;
	Commandes commandes; 
	
	bool valide, victoire, gameOver;
	int char1, char2, nbDepl, nbMU, nbMuCoin; 
	
	string bl, dv, ro, mu, curseurH, curseurB; 
	time_t tempsEcoule, tempsDep, tempsFin;
	
	bl = "\xb2\xb2\xb2\xb2";
	dv = "\x24\x24\x24\x24"; 
	ro = "\xb0\xb0\xb0\xb0";
	mu = "####";
	curseurH = "\xc9\xcb\xcb\xbb";
	curseurB = "\xc8\xca\xca\xbc";

	cout << "Decouvrez et amassez " << MAX_CASH << " cases $$$$";
	gotoxy(64, 0);
	cout << "Points: " << jeu.ptsAccum;

	pointXY = { XY_DEP, XY_DEP };
	
	for (int i = 0; i < NB_COL_DAMIER; i++)       // Affichage du damier
	{
		for (int j = 0; j < NB_LIG_DAMIER; j++)
		{
			switch (jeu.damier[j][i])
			{
			case BL:
			case DC:
				afficherPierre(bl, BLEU, pointXY.dep.col, pointXY.dep.lig);
				break;
			case MU:
				afficherPierre(mu, NOIR, pointXY.dep.col, pointXY.dep.lig);
			}
			pointXY.dep.lig += (NB_LIG_PIERRE + 1);
		}
		pointXY.dep.lig = XY_DEP.lig;
		pointXY.dep.col += NB_COL_PIERRE + 1;
	}

	pointXY = { XY_DEP, XY_DEP };     // Renitialisation des coordo x/y d'origine 
	caseJouee = { CASE_DEP, CASE_DEP };   // Initialisation de la case damier d'origine
	
	nbDepl = 0;
	gameOver = false;
	victoire = false;
	tempsDep = time(NULL);
	
	while (!_kbhit() && !gameOver )
	{
		afficherCurseur(curseurH, curseurB, pointXY.dep.col, pointXY.dep.lig);

		valide = false;
			
		// Adapté du fichier ToucheClavier.cpp
		char1 = _getch();

		if (char1 == 0 || char1 == 224)
		{
			char2 = _getch();
			char1 = char2;
		}
		else
		{
			char2 = char1;
		}
			
		switch (char2)          // Valider le deplacement et definir la case d'arrivee
		{
		case KEY_DOWN:                            
			if ( caseJouee.dep.lig < NB_LIG_DAMIER - 1
				&& jeu.damier[caseJouee.dep.lig + 1][caseJouee.dep.col] != MU )
			{
				caseJouee.arr.lig++;
				pointXY.arr.lig += (NB_LIG_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_UP:
			if ( caseJouee.dep.lig > 0
				&& jeu.damier[caseJouee.dep.lig - 1][caseJouee.dep.col] != MU )
			{
				caseJouee.arr.lig--;
				pointXY.arr.lig -= (NB_LIG_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_RIGHT:
			if ( caseJouee.dep.col < NB_COL_DAMIER - 1
				&& jeu.damier[caseJouee.dep.lig][caseJouee.dep.col + 1] != MU )
			{
				caseJouee.arr.col++;
				pointXY.arr.col += (NB_COL_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_LEFT:
			if (caseJouee.dep.col > 0
				&& jeu.damier[caseJouee.dep.lig][caseJouee.dep.col - 1] != MU )
			{
				caseJouee.arr.col--;
				pointXY.arr.col -= (NB_COL_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_UP_RIGHT:
			if ( (caseJouee.dep.lig > 0) && (caseJouee.dep.col < NB_COL_DAMIER - 1) // limites damier
				&& jeu.damier[caseJouee.dep.lig-1][caseJouee.dep.col+1] != MU) // murs
			{
				caseJouee.arr.lig--;
				caseJouee.arr.col++;
				pointXY.arr.lig -= (NB_LIG_PIERRE + 1);
				pointXY.arr.col += (NB_COL_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_UP_LEFT:
			if ( (caseJouee.dep.lig > 0) && (caseJouee.dep.col > 0)
				&& jeu.damier[caseJouee.dep.lig-1][caseJouee.dep.col - 1] != MU )
			{
				caseJouee.arr.lig--;
				caseJouee.arr.col--;
				pointXY.arr.lig -= (NB_LIG_PIERRE + 1);
				pointXY.arr.col -= (NB_COL_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_DOWN_LEFT:
			if ((caseJouee.dep.lig < NB_LIG_DAMIER - 1) && (caseJouee.dep.col > 0)
				&& jeu.damier[caseJouee.dep.lig + 1][caseJouee.dep.col - 1] != MU)
			{
				caseJouee.arr.lig++;
				caseJouee.arr.col--;
				pointXY.arr.lig += (NB_LIG_PIERRE + 1);
				pointXY.arr.col -= (NB_COL_PIERRE + 1);

				valide = true;
			}	break;
		case KEY_DOWN_RIGHT:
			if ((caseJouee.dep.lig < NB_LIG_DAMIER - 1) && (caseJouee.dep.col < NB_COL_DAMIER - 1)
				&& jeu.damier[caseJouee.dep.lig + 1][caseJouee.dep.col+1] != MU)
			{
				caseJouee.arr.lig++;
				caseJouee.arr.col++;
				pointXY.arr.lig += (NB_LIG_PIERRE + 1);
				pointXY.arr.col += (NB_COL_PIERRE + 1);

				valide = true;
			}	break;
		}
		// AFFICHER LE DAMIER SELON LE DÉPART
		if (valide)
		{
			nbDepl++;

			switch (jeu.damier[caseJouee.dep.lig][caseJouee.dep.col])
			{
			case DC:
			case BL:
				afficherPierre(bl, BLEU, pointXY.dep.col, pointXY.dep.lig);
				break;
			case RO:
				afficherPierre(ro, ROSE, pointXY.dep.col, pointXY.dep.lig);
				break;
			case DV:
				afficherPierre(dv, VERT, pointXY.dep.col, pointXY.dep.lig);
				break;
			case MU:
				afficherPierre(mu, NOIR, pointXY.dep.col, pointXY.dep.lig);

				// VERIFIER SI BLOQUÉ
														// Cas ligne du haut ou du bas
				if ((caseJouee.arr.lig == 0 | caseJouee.arr.lig == NB_LIG_DAMIER - 1)
					&& jeu.damier[caseJouee.arr.lig][caseJouee.arr.col - 1] == MU
					&& jeu.damier[caseJouee.arr.lig][caseJouee.arr.col + 1] == MU)
				{
					if (caseJouee.arr.lig == 0
						&& jeu.damier[caseJouee.arr.lig + 1][caseJouee.arr.col - 1] == MU
						&& jeu.damier[caseJouee.arr.lig + 1][caseJouee.arr.col] == MU
						&& jeu.damier[caseJouee.arr.lig + 1][caseJouee.arr.col + 1] == MU)
					{
						gameOver = true;
					}
					if (caseJouee.arr.lig == NB_LIG_DAMIER - 1
						&& jeu.damier[caseJouee.arr.lig - 1][caseJouee.arr.col - 1] == MU
						&& jeu.damier[caseJouee.arr.lig - 1][caseJouee.arr.col] == MU
						&& jeu.damier[caseJouee.arr.lig - 1][caseJouee.arr.col + 1] == MU)
					{
						gameOver = true;
					}
				}											// Cas des coins
				else if ( (caseJouee.arr.lig == 0 || caseJouee.arr.lig == NB_LIG_DAMIER - 1)
					&& (caseJouee.arr.col == 0 || caseJouee.arr.col == NB_COL_DAMIER - 1) )
				{
					nbMuCoin = 0;

					for (int a = caseJouee.arr.col - 1; a <= caseJouee.arr.col + 1; a++)
					{
						for (int b = caseJouee.arr.lig - 1; b <= caseJouee.arr.lig + 1; b++)
						{
							if (jeu.damier[b][a] != jeu.damier[caseJouee.arr.lig][caseJouee.arr.col]
								&& jeu.damier[b][a] == MU)
							{
								nbMuCoin++;
							}
						}
					}

					if (nbMuCoin == MAX_MU_COIN)
					{
						gameOver = true;
					}
				}									  // Cas colonne gauche ou droite
				else if ( (caseJouee.arr.col == 0 || caseJouee.arr.col == NB_COL_DAMIER-1)
					&& (jeu.damier[caseJouee.arr.lig - 1][caseJouee.arr.col] == MU
						&& jeu.damier[caseJouee.arr.lig + 1][caseJouee.arr.col] == MU) )
				{
					if (caseJouee.arr.col == 0
						&& jeu.damier[caseJouee.arr.lig - 1][caseJouee.arr.col + 1] == MU
						&& jeu.damier[caseJouee.arr.lig][caseJouee.arr.col + 1] == MU
						&& jeu.damier[caseJouee.arr.lig + 1][caseJouee.arr.col + 1] == MU)
					{
							gameOver = true;
					}
					if ( caseJouee.arr.col == NB_COL_DAMIER-1
						&& jeu.damier[caseJouee.arr.lig - 1][caseJouee.arr.col - 1] == MU
						&& jeu.damier[caseJouee.arr.lig][caseJouee.arr.col - 1] == MU
						&& jeu.damier[caseJouee.arr.lig + 1][caseJouee.arr.col - 1] == MU)
					{
							gameOver = true;
					}
				}
				else  // CASES NON LIMITROPHES
				{
					nbMU = 0; 
					
					for (int a = caseJouee.arr.col - 1; a <= caseJouee.arr.col + 1; a++)
					{
						for (int b = caseJouee.arr.lig - 1; b <= caseJouee.arr.lig + 1; b++)
						{
							if (jeu.damier[b][a] != jeu.damier[caseJouee.arr.lig][caseJouee.arr.col]
								&& jeu.damier[b][a]== MU)
							{
								nbMU++;
							}
						}
					}
					if (nbMU == MAX_MU)
					{
						gameOver = true;
					}
				}
			}

			if (!gameOver)    // MODIFIER ÉTATS DES CASES DU DAMIER
			{
				// Cases de départ
				switch (jeu.damier[caseJouee.dep.lig][caseJouee.dep.col])
				{
				case BL:
					if (nbDepl > 2)  // excluant le 1er déplacement
						jeu.damier[caseJouee.dep.lig][caseJouee.dep.col] = RO; break;
				case DC:          //si était $$$$
					jeu.damier[caseJouee.dep.lig][caseJouee.dep.col] = DV; break;
				}

				// Cases d'arrivée
				switch (jeu.damier[caseJouee.arr.lig][caseJouee.arr.col])
				{
				case DV:
					jeu.ptsAccum++;        // Accumuler et afficher les points
					cvmResetColor();
					gotoxy(64, 0);
					cout << "Points: " << jeu.ptsAccum;

					if (jeu.ptsAccum == MAX_CASH)
					{
						gotoxy(64, 0);
						cout << "Points: " << jeu.ptsAccum;
						gameOver = true;
						victoire = true;
					}
					jeu.damier[caseJouee.arr.lig][caseJouee.arr.col] = RO; break;
				case BL:
					jeu.damier[caseJouee.arr.lig][caseJouee.arr.col] = RO; break;
				case DC:
					jeu.damier[caseJouee.arr.lig][caseJouee.arr.col] = DV; break;
				case RO:
					jeu.damier[caseJouee.arr.lig][caseJouee.arr.col] = MU; break;
				}
				caseJouee.dep = caseJouee.arr;		// Réinitialisation des cases départ/arrivée
				pointXY.dep = pointXY.arr;
			}
			else
			{
				cout << "\a";
			}
		}
	} 
		tempsFin = time(NULL);
		tempsEcoule = tempsFin - tempsDep;
		
		// AFFICHAGE DE L'ECRAN FINAL
		clrscr();
		cvmResetColor();

		 if (victoire)
		 {
			cout << "\nVICTOIRE !\n\n"
				<< "  Total des points\t\t:  " << setw(2) << jeu.ptsAccum << " sur un objectif de 15"
				<< "\n\n  Total des d\x82placements\t:  " << setw(2) << nbDepl
				<< "\n\n  Temps \x82""coul\x82\t\t\t:  " << setw(2) << tempsEcoule << " sec";
		 }
		 else if (gameOver)
		 {
			 cout << "\n\x90""CHEC !\n\n"
				 << "  Total des points\t\t:  " << setw(2) << jeu.ptsAccum << " sur un objectif de 15"
				 << "\n\n  Total des d\x82placements\t:  " << setw(2) << nbDepl
				 << "\n\n  Temps \x82""coul\x82\t\t\t:  " << setw(2) << tempsEcoule << " sec";
		 }

		_getch();
}