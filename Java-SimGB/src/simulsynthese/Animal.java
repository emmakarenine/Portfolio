package simulsynthese;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Random;
import java.util.Vector;
import java.awt.Color;

public abstract class Animal extends Vivant // nos animaux sont tous cisgenres, heterosexuels et en relation libre!
{
    private static int count = 0;
    private static int nivEnergieMin = 300;
    private static int nivMinSatieteSex = 100;
    private static int nivMinSatiete = 60;
    private static int nivMinHydrat = 80;
    private static int tempsGestation = 50; // par def
    //private static int tempsDodo = 50; // par def
    
    private Vector<Animal> listePreds = new Vector<Animal>();
	private Vector<Vivant> listeProies = new Vector<Vivant>();; // comprend les vegetaux

    private int nivSatiete = 500; 
    private int nivSatieteSex = 400;
    private int vitMarche;
	private int vitCourse;
	
    private boolean isMale;    
    private Animal mere;
    private Animal bebe;	
    private int gestationActu = tempsGestation;
    //private int dureeDodoActu = tempsDodo;
    private boolean isPregnant = false;
    private boolean hasBabyBorn = false;
    private int nivSexAppeal;  // 0 a 10: va varier selon le trait dominant
   
	private boolean isThreatened = false;	
	private boolean isHungry = false;
	private boolean isTired = false;
	private boolean isSleeping = false;
	private boolean isHorny = false;
	private boolean isChasingMate = false;
	private boolean isConceivingBaby = false;    
    private boolean hasReachedTarget = false;

	private Radar radar;
    private Animal predateurProche = null;
	private Animal mate =  null;
	private Vivant proie = null;
	//private Vivant cibleViv = null; // a voir
	private Vivant cibleDodo = null;
	private Point cibleRand = null; // cible de promenade
	
	private Point lastPt = null;
	
	protected abstract boolean verifierTerrainCible();
    protected abstract void trouverCibleDodo(Vector<Vegetal> vegs);    
    protected abstract void manger();
    protected abstract void accoucher();
    	
    public Animal(Point pt, int fullEnergie, int[][] grandeurs, int[] ages, Color[] coul, int rayonRadar, int[] vitesses, EnvirTerrain terrain, Object[] preds, Object[] proies)
    {
    	super(pt, fullEnergie, grandeurs, ages, coul, terrain);
    	setLastPt();
    	setMale( new Random().nextInt(2) == 0 );
        setVitMarche(vitesses[0]);
        setVitCourse(vitesses[1]);
        changerCible();
        
        remplirVec(this.listePreds, preds);
        remplirVec(this.listeProies, proies);
        this.radar = new Radar(this, pt, rayonRadar, this.listePreds, this.listeProies);
    }
    
    protected void refreshBesoins()
    {
    	System.out.println("super.nivEnergie=="+super.getNivEnergie());
    	
    	if (isSleeping())
    	{
    		super.addNivEnergie(10);
    	}
    	else
    	{
    		super.addNivEnergie(-5);
    	}    	
    	
    	if ( !isConceivingBaby())
    	{
    		addNivSatieteSex(-1);
    	}
    	
    	addNivSatiete(-1);
    	
    	this.isTired = super.getNivEnergie() < Animal.getNivEnergieMin() ? true : false;
    	this.isHungry = this.nivSatiete < Animal.getNivMinSatiete() ? true : false;    	
    	
    	// Animal a l'age adulte
    	if (super.getAge() >= super.getAges()[1])
    	{
    		this.isHorny = getNivSatieteSex() < Animal.getNivMinSatieteSex() ? true : false;
    	}
    	
    	prioriserBesoin();
    	
    	System.out.println("Apres refreshBesoin, position est "+super.getPtCentre().x+", "+super.getPtCentre().y+"   lastPos est "+getLastPt().x+", "+getLastPt().y+
    			"\n                                         nivEnergie()=="+super.getNivEnergie()+"   nivSatiete=="+getNivSatiete()+"  nivSatieteSex=="+getNivSatieteSex());
    }
    
    // On s'assure qu'un seul besoin est repondu a chaque tour
    protected void prioriserBesoin()
    {
    	if (this.isConceivingBaby || this.isPregnant)
    	{
    		setSleeping(false);
    		setHungry(false);
    		setTired(false);
    		setHorny(false);
    	}
    	else if (this.isSleeping)
    	{
    		setHungry(false);
    		setTired(true);
    		setHorny(false);
    	}
    	if (this.isHungry)
    	{
    		setTired(false);
    		setHorny(false);
    	}
    	else if (this.isTired)
    	{
    		setHorny(false);
    	}
    }
	
	protected void agir(Vector<Environnement> vecEnvirs, Vector<Vivant> vecVivants)
	{		
		this.isThreatened = this.radar.activer(vecEnvirs, vecVivants);
		
		System.out.println("Age=="+getAge()+
				"\n     isConceivingBaby=="+isConceivingBaby+
				"\n     isTheatened=="+isThreatened+
				"\n     hasReachedTarget=="+hasReachedTarget+
				"\n     isHorny=="+isHorny+"  isChasingMate=="+isChasingMate+
				"  isTired=="+isTired
				+"\n     isHungry=="+isHungry+
				"  isSleeping=="+isSleeping);

		// Deja occupe a copuler
		if (this.isConceivingBaby)
		{
			setChasingMate(false);
			seReproduire();	
		}
		else if (this.isSleeping)
		{
			dormir();		
		}
		// PAS DE MENACE ALENTOUR
		else if (!this.isThreatened)
		{
			if ( this.isTired)
			{
				System.out.println("Je suis fatigue....");
				trouverCibleDodo(this.radar.getVecVeg());
				//avancer(vecVivants, this.vitMarche);				
			}			
			// ANIMAL A FAIM
			else if (this.isHungry)
			{
				System.out.println("J'ai faim.");
				
				// La proie devient la cible				
				if (this.proie != null)
				{
					setProie(this.proie);
					setCibleDodo(null);
					setMate(null);
					setPredateurProche(null);
					//System.out.println(" Ma cible est ma proie !");
					setHasReachedTarget(false);
				}		
				
				//avancer(vecVivants, this.vitMarche);
			}
			// Un adulte en rut
			else if (this.getAge() >= this.getAges()[1] && this.isHorny && !this.isChasingMate)
			{
				trouverMate(vecVivants);
				avancer(vecVivants, this.vitMarche);
			}
//			else
//			{
//				avancer(vecVivants, this.vitMarche);
			//}
			
			avancer(vecVivants, this.vitMarche);			
		}
		else // MENACE A PROXIMITE !!!
		{
			// Arreter toute autre occupation
			setChasingMate(false);
			//viderCibles();
			setMate(null);
			setProie(null);
			setCibleDodo(null);
			setCibleRand(null);
			setPredateurProche(null);			
			
			changerCible();
			avancer(vecVivants, this.vitCourse);			
		}
	}
	


	protected void changerCible()
    {
		Rectangle shapeEnvir = super.getTerrain().getAire().getBounds(); // retourne le Rect qui circonscrit l'aire
		//System.out.println(" Mon terrain est celui-ci "+ super.getTerrain().getClass() +" et commence a "+shapeEnvir.x+", "+shapeEnvir.y);
		int xNext = shapeEnvir.x + new Random().nextInt(shapeEnvir.width);
		int yNext = shapeEnvir.y + new Random().nextInt(shapeEnvir.height);
		System.out.println(" Ma nouvelle cibleRand sera est ("+ xNext + ", "+yNext+")");
		
		// La nouvelle cible est sur un terrain acceptable pour l'espece
		if (verifierTerrainCible() )
		{
			setCibleRand(xNext, yNext);
			setHasReachedTarget(false);
		}
		else
		{
			changerCible();
		}
    }
	

	
	

    protected void seReproduire()
    {		
    	// Modifs du male
    	if (isMale() )
    	{
    		//setChasingMate(false); 
			setNivSatieteSex(100);
			setConceivingBaby(false);
			setMate(null);
			changerCible();
			avancer(null, this.vitMarche);
    	}
    	// Modifs de la femelle
    	else if ( !isMale() ) 
		{
    		//setChasingMate(false);
    		
			// Tomber enceinte
			if ( !isPregnant() )
			{
				setPregnant(true);
			}
			else
			{
				// Jours s'ecoulent avant fin de la gestation
				addGestationActu(-1);
				
				// Bebe va sortir
				if ( getGestationActu() == 0)
				{
					// Traiter la mere					
					setGestationActu(getTempsGestation());
					accoucher();					
					setPregnant(false);
					setConceivingBaby(false);
					setNivSatieteSex(1000);
					setMate(null);
					changerCible();
					avancer(null, this.vitMarche);
				}
			}
		}
    }
    
	protected void trouverMate(Vector<Vivant> vecVivants)
	{
		Point ptActu = super.getPtCentre();
				
		Vector<Animal> mates = getRadar().getVecMates();
		AFFICHER_VECTEUR("vecteur mates du radar", mates);
		
		// Le radar a detecte des mates potentiels
		if ( !mates.isEmpty() )
		{			
			this.mate = mates.elementAt(0);
			
			for (Animal maybe : mates)
			{
				// Le prochain mate est plus pres
				if ( ptActu.distance( maybe.getPtCentre() ) <
						ptActu.distance( mate.getPtCentre() ) )
				{
					this.mate = maybe;
				}
			}
			
			// Le mate devient la cible
			setMate(this.mate);
			setProie(null);
			setCibleDodo(null);
			setHasReachedTarget(false);
			this.setChasingMate(true);
		}
		else
		{
			avancer(null, this.vitMarche);
		}
	}
    
    protected void avancer(Vector<Vivant> vecVivants, int vitesse)
    {    	
    	int xPas = 0;
    	int yPas = 0;
    	
    	if ( !this.hasReachedTarget)
    	{
    		System.out.println("avancer---Je n'ai pas atteint ma cible.");   		
        	
        	// TRAITER UNE CIBLE RANDOM DE PROMENADE
        	if (this.cibleRand != null)
        	{
        		System.out.println("avancer---Ma cibleRand n'est pas null.");
        		
        		// cible est plus a gauche
        		if ( this.cibleRand.x < super.getAire().x-vitesse )
        		{System.out.println("cible est plus a gauche");
        			xPas = -vitesse;
        		}
        		// Cible est plus a droite
        		else if ( this.cibleRand.x > super.getAire().x+vitesse )
        		{System.out.println("cible est plus a droite");
        			xPas = vitesse;
        		}
        		// Cible est plus en haut
        		if ( this.cibleRand.y < super.getAire().y-vitesse )
        		{System.out.println("cible est plus en haut");
        			yPas = -vitesse;
        		}
        		// Cible est plus en bas
        		else if ( this.cibleRand.y > super.getAire().y+vitesse )
        		{System.out.println("cible est plus en bas");
        			yPas = vitesse;
        		}   
        		            	
            	// Deplacement de l'animal et de son radar
            	this.setLastPt();
        		super.getAire().translate(xPas, yPas);
        		super.setPtCentre();
        		//System.out.println("ptCentre est devenu "+getPtCentre().x+", "+getPtCentre().y);
        		this.radar.translate(xPas, yPas);
        		
        		if (this.cibleRand.x >= super.getAire().x-vitesse &&
        				this.cibleRand.x <= super.getAire().x+vitesse &&
        						this.cibleRand.y >= super.getAire().y-vitesse &&
        								this.cibleRand.y <= super.getAire().y+vitesse )
        		{System.out.println("cibleX et cibleY sont a moins d'un pas");
        			setHasReachedTarget(true);
        			this.cibleRand = null;
        		}
        	}
        	// TRAITER UNE CIBLE DODO        	
        	else if (this.cibleDodo != null)
        	{
        		// Cible est plus a gauche
        		if ( this.cibleDodo.getPtCentre().x < super.getPtCentre().x-vitesse )
        		{
        			xPas = -vitesse;
        		}
        		// Cible est plus a droite
        		else if ( this.cibleDodo.getPtCentre().x > super.getPtCentre().x+vitesse )
        		{
        			xPas = vitesse;
        		}
        		
        		// TRAITER LE Y CIBLE
        		// Cible est plus en haut
        		if ( this.cibleDodo.getPtCentre().y < super.getPtCentre().y-vitesse )
        		{
        			yPas = -vitesse;
        		}
        		// Cible est plus en bas
        		else if ( this.cibleDodo.getPtCentre().y > super.getPtCentre().y+vitesse )
        		{
        			yPas = vitesse;
        		}
        		
            	
            	// Deplacement de l'animal et de son radar
            	this.setLastPt();
        		super.getAire().translate(xPas, yPas);
        		super.setPtCentre();
        		//System.out.println("ptCentre est devenu "+getPtCentre().x+", "+getPtCentre().y);
        		this.radar.translate(xPas, yPas);
        		
        		
        		if (this.cibleDodo.getPtCentre().x >= super.getPtCentre().x-vitesse &&
        				this.cibleDodo.getPtCentre().x <= super.getPtCentre().x+vitesse &&
        					this.cibleDodo.getPtCentre().y >= super.getPtCentre().y-vitesse &&
        						this.cibleDodo.getPtCentre().y <= super.getPtCentre().y+vitesse )
        		{

        			setHasReachedTarget(true);
        			dormir();
        		}
        	}    	
    		// TRAITER UNE CIBLE VIVANTE        	
        	else if (this.proie != null)
        	{
        		// Cible est plus a gauche
        		if ( this.proie.getPtCentre().x < super.getPtCentre().x-vitesse )
        		{
        			xPas = -vitesse;
        		}
        		// Cible est plus a droite
        		else if ( this.proie.getPtCentre().x > super.getPtCentre().x+vitesse )
        		{
        			xPas = vitesse;
        		}
        		
        		// TRAITER LE Y CIBLE
        		// Cible est plus en haut
        		if ( this.proie.getPtCentre().y < super.getPtCentre().y-vitesse )
        		{
        			yPas = -vitesse;
        		}
        		// Cible est plus en bas
        		else if ( this.proie.getPtCentre().y > super.getPtCentre().y+vitesse )
        		{
        			yPas = vitesse;
        		}
        		
            	
            	// Deplacement de l'animal et de son radar
            	this.setLastPt();
        		super.getAire().translate(xPas, yPas);
        		super.setPtCentre();
        		//System.out.println("ptCentre est devenu "+getPtCentre().x+", "+getPtCentre().y);
        		this.radar.translate(xPas, yPas);
        		
        		
        		if (this.proie.getPtCentre().x >= super.getPtCentre().x-vitesse &&
        				this.proie.getPtCentre().x <= super.getPtCentre().x+vitesse &&
        					this.proie.getPtCentre().y >= super.getPtCentre().y-vitesse &&
        						this.proie.getPtCentre().y <= super.getPtCentre().y+vitesse )
        		{

        			setHasReachedTarget(true);
        			
        			if (this.isChasingMate)
        			{
        				setChasingMate(false);
        				setConceivingBaby(true);
        			}
        			else if (this.proie != null)
        			{
        				manger();			
        			}
        		}
        	}

    	}
    	else
    	{
    		changerCible();
    	}
    	
    	
    }
    
    protected void dormir()
    {
    	if (getCibleDodo() != null && hasReachedTarget() )    	
    	{
    		setSleeping(true);if ( super.getNivEnergie() >= getNivEnergieMax())
        	{
        		setNivEnergie(getNivEnergieMax());
        		setSleeping(false);
        		setTired(false);    		
        		System.out.println("super.nivEnergie=="+super.getNivEnergie());		
        		setCibleDodo(null);
        		//setHasReachedTarget(true); // pret pour une nouvelle destination
        	}
    		
    		
    	}

    	
    }    

//	protected Vivant getCibleViv() {
//		return this.cibleViv;
//	}
//	
//	protected void setCibleViv(Vivant vivant)
//	{
//		this.cibleViv = vivant;
//	}

	
	protected Point getCibleRand() {
		return this.cibleRand;
	}
    
	protected void setCibleRand(int x, int y) {
		setCibleRand(new Point(x, y));
	}

	protected void setCibleRand(Point cibleRand) {
		this.cibleRand = cibleRand;
	}
	
    protected int getVitMarche() {
		return vitMarche;
	}

	protected void setVitMarche(int vitMarche) {
		this.vitMarche = vitMarche;
	}

	protected int getVitCourse() {
		return vitCourse;
	}

	protected void setVitCourse(int vitCourse) {
		this.vitCourse = vitCourse;
	}
    
    protected boolean isHungry() {
		return this.isHungry;
	}

	protected void setHungry(boolean isHungry) {
		this.isHungry = isHungry;
	}

	protected boolean isConceivingBaby() {
		return this.isConceivingBaby;
	}

	protected void setConceivingBaby(boolean conceiving) {
		this.isConceivingBaby = conceiving;
	}

	protected boolean isMale() {
		return this.isMale;
	}

	protected void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	protected boolean isPregnant() {
		return isPregnant;
	}

	protected void setPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
	}

	protected boolean hasReachedTarget() {
		return this.hasReachedTarget;
	}

	protected void setHasReachedTarget(boolean hasReachedTarget) {
		this.hasReachedTarget = hasReachedTarget;
	}

	protected int getNivSatiete() {
		return nivSatiete;
	}

	protected void addNivSatiete(int increm)
	{
		this.nivSatiete += increm;
	}

	protected int getNivSexAppeal()
	{
		return nivSexAppeal;
	}

	protected void setNivSexAppeal(int nivSexAppeal)
	{
		this.nivSexAppeal = nivSexAppeal;
	}
	
	protected int getNivSatieteSex() {
		return this.nivSatieteSex;
	}

	protected void setNivSatieteSex(int nivApp)
	{
		this.nivSatieteSex = nivApp;
	}
	
	protected void addNivSatieteSex(int increm)
	{
		this.nivSatieteSex += increm;
	}
	
    protected Vector<Animal> getListePreds() {
		return listePreds;
	}


	protected void setListePreds(Vector<Animal> listePreds) {
		this.listePreds = listePreds;
	}

	protected Vector<Vivant> getListeProies() {
		return listeProies;
	}
	
	protected void setListeProies(Vector<Vivant> listeProies) {
		this.listeProies = listeProies;
	}	
	
	protected Animal getMere()
	{
		return this.mere;
	}

	protected void setMere(Animal mere)
	{
		this.mere = mere;
	}
	
	protected Radar getRadar() {
		return this.radar;
	}

	protected void setRadar(Radar radar) {
		this.radar = radar;
	}
	

	protected Animal getPredateurProche() {
		return this.predateurProche;
	}


	protected void setPredateurProche(Animal pred) {
		this.predateurProche = pred;
	}
	
    protected boolean isChasingMate() {
		return isChasingMate;
	}


    protected void setChasingMate(boolean isChasingMate) {
		this.isChasingMate = isChasingMate;
	}


    protected int getGestationActu() {
		return gestationActu;
	}


    protected void setGestationActu(int gestationActu) {
		this.gestationActu = gestationActu;
	}
	
    protected void addGestationActu(int increm) {
		this.gestationActu += increm;
	}

    protected boolean isHasBabyBorn() {
		return hasBabyBorn;
	}

    protected void setHasBabyBorn(boolean hasBabyBorn) {
		this.hasBabyBorn = hasBabyBorn;
	}
	
	protected Animal getMate() {
		return mate;
	}

	protected void setMate(Animal mate) {
		this.mate = mate;
	}

	protected Animal getBebe()
	{
		return this.bebe;
	}

	protected void addBebe(Animal bebe) {
		this.bebe = bebe;
	}



	public boolean isTired() {
		return isTired;
	}

	protected void setTired(boolean isTired) {
		this.isTired = isTired;
	}

	protected boolean isHorny() {
		return isHorny;
	}

	protected boolean setHorny(boolean isHorny) {
		this.isHorny = isHorny;
		return isHorny;
	}
	
	protected Vivant getProie() {
		return proie;
	}

	protected void setProie(Vivant proie) {
		this.proie = proie;
	}
	
   protected void AFFICHER_VECTEUR(String entete, Vector vec)
   {
	   	System.out.println(entete);
	   	for (Object o: vec)
	   	{
	   		System.out.println(o);
	   	}
   }
   
    protected Vivant getCibleDodo() {
		return this.cibleDodo;
	}

    protected void setCibleDodo(Vivant cibleDodo) {
		this.cibleDodo = cibleDodo;
	}

//	protected int getDureeDodoActu() {
//		return dureeDodoActu;
//	}
//
//	protected void setDureeDodoActu(int dureeDodoActu) {
//		this.dureeDodoActu = dureeDodoActu;
//	}
//	
//	protected void addDureeDodoActu(int increm) {
//		this.dureeDodoActu += increm;
//	}
	
	protected boolean isSleeping() {
		return isSleeping;
	}

	protected void setSleeping(boolean isSleeping) {
		this.isSleeping = isSleeping;
	}

	protected static int getNivEnergieMin() {
		return nivEnergieMin;
	}

	protected static int getNivMinSatieteSex() {
		return Animal.nivMinSatieteSex;
	}

	protected static int getNivMinSatiete() {
		return Animal.nivMinSatiete;
	}

	protected static int getNivMinHydrat() {
		return nivMinHydrat;
	}
	
    protected static int getTempsGestation() {
		return tempsGestation;
	}
	
//	protected static int getTempsDodo() {
//		return Animal.tempsDodo;
//	}
	
	protected Point getLastPt() {
		return lastPt;
	}
	
	public void setLastPt(Point lastPt) {
		this.lastPt = new Point(lastPt.x, lastPt.y);
		System.out.println("LastPt est rendu "+this.lastPt.x+", "+this.lastPt.y);
	}
	
	protected void setLastPt() {
		this.lastPt = super.getPtCentre();
	}	
	
	public static void incrementer(int newIndiv) {
        Vivant.count += newIndiv;
        Vivant.incrementer(newIndiv);
    }
    
    public static int getCount() {
        return Animal.count;
    }
    
    protected void remplirVec(Vector vec, Object[] tabClasses)
    {
    	if ( tabClasses != null)
    	{
    		for (Object classe: tabClasses)
        	{
        		vec.add(classe);
        	}
    	}    	
    }
}