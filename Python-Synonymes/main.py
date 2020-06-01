# TP1 : Recherche de synonymes
# Auteure: Makraphone Phouttama
# Remise: 11 mars 2020

from sys import argv
import numpy as np
import re
import math
import time

class Lecteur:
    def __init__(self, encodage):
        self.encodage = encodage

    def extraire_mots(self, a_remplir, chemins):
        for i in range(len(chemins)):
            texte = self.__lire_texte(chemins[i])
            mots_texte = self.__diviser_mots(texte)
            a_remplir.append(mots_texte)

    def __lire_texte(self, chemin):
        fichier = open(chemin, 'r', encoding = self.encodage)
        chaine = fichier.read().lower()
        fichier.close()
        return chaine

    def __diviser_mots(self, texte):
        mots = []
        mots += re.findall('\w+', texte)
        return mots

class Engin_Cooccurences:
    def __init__(self, liste_chemins, taille_fenetre, lecteur):
        self.dico = {}
        self.matrice = None
        self.liste_chemins = liste_chemins
        self.taille_fenetre = int(taille_fenetre)        
        self.lecteur = lecteur
        self.idx_cible_min = math.floor(self.taille_fenetre/2)
        self.nb_mots_chaque_cote = self.idx_cible_min        
        self.liste_mots_par_texte = []

        self.__traiter_textes()  

    def __traiter_textes(self):
        self.__extraire_mots()
        self.__remplir_dico()
        self.__creer_matrice()
        self.__remplir_matrice()

    def __extraire_mots(self):
        self.lecteur.extraire_mots(self.liste_mots_par_texte, self.liste_chemins)

    def __remplir_dico(self):
        for texte in self.liste_mots_par_texte:
            for mot in texte:
                if mot not in self.dico:
                    self.dico[mot] = len(self.dico)

    def __creer_matrice(self):
        nb_mots = len(self.dico)
        self.matrice = np.zeros( (nb_mots,nb_mots) )
        
    def __remplir_matrice(self):
        # Parcourir chaque texte
        for t in range(len(self.liste_mots_par_texte)):
            # Ajuster la cible maximale selon la longueur du texte parcouru
            idx_cible_max = len(self.liste_mots_par_texte[t])-self.nb_mots_chaque_cote

            # Parcourir chaque cible
            for i in range(self.idx_cible_min, idx_cible_max):
                idx_cible_actu = self.dico[self.liste_mots_par_texte[t][i]]

                # Parcourir les voisins de fenetre d'une cible
                for j in range(1, self.nb_mots_chaque_cote+1):
                    ecart_gauche = i-j
                    ecart_droite = i+j

                    if ecart_gauche >= 0:
                        idx_cooc_gauche = self.dico[self.liste_mots_par_texte[t][ecart_gauche]]
                        #ne pas incrementer sur soi-meme
                        if idx_cible_actu != idx_cooc_gauche:
                            self.matrice[idx_cible_actu][idx_cooc_gauche] += 1
                    if ecart_droite < len(self.liste_mots_par_texte):
                        idx_cooc_droite = self.dico[self.liste_mots_par_texte[t][ecart_droite]]
                        #ne pas incrementer sur soi-meme
                        if idx_cible_actu != idx_cooc_droite:
                            self.matrice[idx_cible_actu][idx_cooc_droite] += 1

class Engin_Recherche():
    def __init__(self, engin_cooc, lecteur, stop_list_path=["stoplist.txt"]):
        self.resultat = []
        self.mot_rech = None
        self.nb_syn = None
        self.methode = None
        self.dico = engin_cooc.dico
        self.matrice = engin_cooc.matrice    
        self.lecteur = lecteur
        self.stop_list = []

        self.lecteur.extraire_mots(self.stop_list, stop_list_path)

    def set_params_recherche(self, rep):
        rep_div = rep.split()
        self.mot_rech = rep_div[0]
        self.nb_syn = rep_div[1]
        self.__set_methode(rep_div[2])

    def __set_methode(self, no_methode):
        if no_methode == '0':
            self.methode = self.__p_scal
        elif no_methode == '1':
            self.methode = self.__l_squares
        elif no_methode == '2':
            self.methode = self.__c_block

    def rechercher(self):
        idx_rech = self.dico[self.mot_rech]
        vec_rech = self.matrice[idx_rech]
        liste_scores = []

        for mot,idx in self.dico.items():
            if idx != idx_rech and mot not in self.stop_list[0]:
                score = self.methode(vec_rech, self.matrice[idx])
                liste_scores.append( (score,mot) )
        
        self.resultat = sorted(liste_scores, reverse = self.methode==self.__p_scal)

    def __p_scal(self, vec_rech, vec_temp):
        return np.dot(vec_rech, vec_temp)

    def __l_squares(self, vec_rech, vec_temp):
        return np.sum(np.square(vec_rech-vec_temp))

    def __c_block(self, vec_rech, vec_temp):
        return np.sum(np.absolute(vec_rech-vec_temp))

    def afficher_resultats(self, duree):
        print('''
            ''')
        for score,syn in self.resultat[0:int(self.nb_syn)]:
            print(syn, " ---> ", score)
        print(f'''
Cette recherche a dure {duree} secondes.
    ''')

def afficher(is_results, engin_rech=None, duree=None):
    if not is_results:
        print("""************************************************************************
************************************************************************

    VOTRE DICTIONNAIRE DE SYNONYMES.

    Entrez un mot, le nombre de synonymes et la méthode de calcul voulus :
    Produit scalaire -> 0. Least-squares -> 1. City-block -> 2.

    Tapez 'q' pour quitter.
    """)

        return input()
    else:
        engin_rech.afficher_resultats(duree)


def main():
    taille_fenetre = argv[1]
    encod = argv[2]
    liste_chemins = argv[3:]
    stop_list_path = ["stoplist.txt"]
    
    lecteur = Lecteur(encod)
    engin_cooc = Engin_Cooccurences(liste_chemins, taille_fenetre, lecteur)
    engin_rech = Engin_Recherche(engin_cooc, lecteur, stop_list_path)

    rep = afficher(False)

    while rep != 'q':
        mot_rech = rep.split()[0]

        if (mot_rech in engin_cooc.dico):            
            engin_rech.set_params_recherche(rep)

            t_debut = time.time()
            engin_rech.rechercher()                 
            duree = time.time()-t_debut
            afficher(True, engin_rech, duree)
        else:
            print('''
:( Ce mot n'apparait dans aucun des textes soumis. :(
''')        
        rep = afficher(False)

    print('''**** MERCI! AU REVOIR! *****''')
    return 0


if __name__ == '__main__':
    quit(main())