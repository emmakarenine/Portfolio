package simulsynthese;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

public class Radar extends Rectangle {
	// private Hashtable<String, Vector> htRadar = new Hashtable<String, Vector>();

	private Animal cetAnimal;
	private int rayon;

	private boolean enDanger = false;

	private Vector<Animal> listeClassPreds = new Vector<Animal>();
	private Vector<Vivant> listeClassProies = new Vector<Vivant>();

	private Vector<EnvirTerrain> vecTerr = new Vector<EnvirTerrain>();
	private Vector<EnvirHumain> vecHum = new Vector<EnvirHumain>();

	private Vector<Vegetal> vecVeg = new Vector<Vegetal>();
	private Vector<Animal> vecAnim = new Vector<Animal>();
	private Vector<Animal> vecMates = new Vector<Animal>();
	private Vector<Animal> vecPreds = new Vector<Animal>();
	private Vector<Vivant> vecProies = new Vector<Vivant>(); // comprend les vegetaux
	private Vector<Animal> vecRivaux = new Vector<Animal>();

	private Animal predateurProche;
	private Vivant proie;

	public Radar() {
		super();
	}

	public Radar(Animal cetAnimal, Point pt, int rayon, Vector<Animal> preds, Vector<Vivant> proies) {
		super((int) pt.x - rayon, (int) pt.y - rayon, rayon * 2, rayon * 2);
		this.setRayon(rayon);
		this.cetAnimal = cetAnimal;
		// this.htRadar = new Hashtable<String, Vector>();
//    	insertDsHashtable("vegetaux", vecVeg);
//    	insertDsHashtable("animaux", vecAnim);
//    	insertDsHashtable("mates", vecMates);
//    	insertDsHashtable("rivaux", vecMates);
//    	insertDsHashtable("terrains", vecTerr);
//    	insertDsHashtable("humains", vecHum);
//    	insertDsHashtable("preds", vecPreds);
//    	insertDsHashtable("proies", vecProies);
		this.listeClassPreds = preds;
		this.listeClassProies = proies;
	}

//	protected void insertDsHashtable(String nom, Vector vec)
//	{
//		htRadar.put(nom, vec);
//	}

	protected boolean activer(Vector<Environnement> vecEnvirs, Vector<Vivant> vecVivants) {
		updatePosition(vecEnvirs, vecVivants);
		return this.enDanger;
	}

	protected void updatePosition(Vector<Environnement> vecEnvirs, Vector<Vivant> vecVivants) {
		super.getBounds().setLocation(cetAnimal.getAire().getLocation()); // aire se deplace
		updateContenu(vecEnvirs, vecVivants);
	}

	protected void updateContenu(Vector<Environnement> vecEnvirs, Vector<Vivant> vecVivants) {
//    	htRadar.forEach ( (cle, vecteur) -> {
//    		
//    		vecteur.removeAllElements();
//    	});

		// Tout remettre a neuf
		setEnDanger(false);
		viderVecs();

		for (Vivant viv : vecVivants) {

			if (viv != this.cetAnimal) // ne pas se compter soi-meme
			{
				// ENREGISTRER ET CATEGORISER LES VIVANTS A PROXIMITE
				if (super.contains(viv.getAire().getCenterX(), viv.getAire().getCenterY())) {
					// PROIE (ANIMALE OU VEGETALE)
					if (this.listeClassProies.contains(viv.getClass())) {
						this.vecProies.addElement(viv);
						// AFFICHER_VECTEUR(this.vecProies);
						setProie(getVecProies().lastElement());
					}
					// ANIMAL
					else if (viv instanceof Animal) {
						// DANGER
						if (this.listeClassPreds.contains(viv.getClass())) {
							// Premier predateur est automatiquement le plus proche
							if (this.vecPreds.size() == 0) {
								setPredateurProche((Animal) viv);
							} else {
								// Sinon, mettre a jour le plus proche predateur
								if (this.cetAnimal.getPtCentre().distance(viv.getPtCentre()) < // le nouveau predateur
																								// est moins loin que
																								// l'ancien
										this.cetAnimal.getPtCentre()
												.distance(this.vecPreds.lastElement().getPtCentre())) {
									setPredateurProche((Animal) viv);
								}
							}

							this.vecPreds.addElement((Animal) viv);
							setEnDanger(true);
						}
						// MEME ESPECE: MATE OU RIVAL
						else if (viv.getClass() == this.cetAnimal.getClass()) {
							// Potentiel mate
							if (((Animal) viv).isMale() != this.cetAnimal.isMale()) {
								// Femelle non enceinte
								if (!((Animal) viv).isPregnant() && ((Animal) viv).isHorny()) {
									this.vecMates.addElement((Animal) viv);
								}
							} else // Potentiel rival
							{
								this.vecRivaux.addElement((Animal) viv);
							}
						} else // AUTRE ANIMAL
						{
							vecAnim.add((Animal) viv);
						}
					} else // AUTRE VEGETAL
					{
						vecVeg.add((Vegetal) viv);
					}
				}
			}
		}

		for (Environnement env : vecEnvirs) {
			if (env instanceof EnvirTerrain) {
				// Le champ de l'animal comprend cet environnement
				if (env.getAire().intersects(this.getBounds())) {
					vecTerr.add((EnvirTerrain) env);
				}
			} else // Environnement humain
			{
				vecHum.add((EnvirHumain) env);
			}
		}
	}

	protected boolean getEnDanger() {
		return this.enDanger;
	}

	protected void setEnDanger(boolean enDanger) {
		this.enDanger = enDanger;
	}

	protected void AFFICHER_VECTEUR(Vector vec) {
		// Contenu des vecs
		System.out.println("radar----CONTENU du vecteur");
		for (Object o : vec) {
			System.out.println(o);
		}
	}

	protected Vector<EnvirTerrain> getVecTerr() {
		return vecTerr;
	}

	protected void setVecTerr(Vector<EnvirTerrain> vecTerr) {
		this.vecTerr = vecTerr;
	}

	public Vector<Vegetal> getVecVeg() {
		return vecVeg;
	}

	public void setVecVeg(Vector<Vegetal> vecVeg) {
		this.vecVeg = vecVeg;
	}

	protected Vector<Animal> getVecMates() {
		return this.vecMates;
	}

	protected void setVecMates(Vector<Animal> vecMates) {
		this.vecMates = vecMates;
	}

	protected Vector<Animal> getVecRivaux() {
		return this.vecRivaux;
	}

	protected void setVecRivaux(Vector<Animal> vecRivaux) {
		this.vecRivaux = vecRivaux;
	}

	protected Vector<Animal> getVecPreds() {
		return this.vecPreds;
	}

	protected void setVecPreds(Vector<Animal> vecPreds) {
		this.vecPreds = vecPreds;
	}

	public Animal getPredateurProche() {
		return this.predateurProche;
	}

	public void setPredateurProche(Animal pred) {
		this.predateurProche = pred;
		this.cetAnimal.setPredateurProche(pred);
	}

	protected Vector<Vivant> getVecProies() {
		return this.vecProies;
	}

	protected void setVecProies(Vector<Vivant> vecProies) {
		this.vecProies = vecProies;
	}

	public Vivant getProie() {
		return proie;
	}

	public void setProie(Vivant proie) {
		this.proie = proie;
		this.cetAnimal.setProie(proie);
	}

	public int getRayon() {
		return rayon;
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	private void viderVecs() {
		vecVeg.removeAllElements();
		this.vecAnim.removeAllElements();
		this.vecMates.removeAllElements();
		this.vecPreds.removeAllElements();
		this.vecProies.removeAllElements();
		this.vecTerr.removeAllElements();
		this.vecHum.removeAllElements();
	}

}