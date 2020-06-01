
def lire(chemin, encodage):
	fichier = open(chemin, 'r', encoding = encodage)
	chaine = fichier.read().lower()
	fichier.close()
	
	return chaine
	

if __name__ == '__main__':
	chaine = lire('fichiersTexte/test.txt', 'utf-8')
	print(chaine)
	#print('ceci est lecture:', __name__)

	