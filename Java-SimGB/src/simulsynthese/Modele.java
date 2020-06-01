package simulsynthese;

import java.util.*;
import java.awt.Point;
import java.awt.Dimension;

public class Modele
{	
	protected static Hashtable<String, Integer> paramsPersos = new Hashtable<String, Integer>();
	
    private static int[] factHumData = new int[] {3};
    private int[] humData = {0, 10, 2}; // apres 2 ans

	private Vector<Environnement> vecEnvirs = new Vector<Environnement>();
	private Vector<Vivant> vecVivants = new Vector<Vivant>();
	private Vector<Vivant> vecBebes = new Vector<Vivant>();
	private Vector<Vivant> vecMorts = new Vector<Vivant>();
	
	private EnvirHumain factHumain = null; // HARDCODE de classe EnvirHumain
	
    private final int O2_AIR = 21;
    private final int CO2_AIR = 4; // vraie valeur : 0.04 %
    private final int O2_EAU = 1; // vraies valeurs, dans l'ocean, sont tres petites
    private final int CO2_EAU = 1;
    
    private final int LARGEUR_TERR = 400;
    private final int MI_LARGEUR_TERRAIN = 200;
    private final int HAUTEUR_TERR = 1000;
    private final Dimension DIMS_TERRAIN_STD = new Dimension(LARGEUR_TERR, 1000);
    
    // Terrains (dans l'ordre)
    private final Point PT_HG_OCN_CONTN_1 = new Point(0, 0);
    private final Point PT_HG_OCN_LITT_1 = new Point(200, 0);
    private final Point PT_HG_ILE = new Point(600, 0);
    private final Point PT_HG_OCN_LITT_2 = new Point(1000, 0);    
    private final Point PT_HG_OCN_CONTN_2 = new Point(1400, 0);
    
    private boolean premObjetsCrees = false;
    private boolean factHumAdded = false;
	
	private Ile ile;
	private Ocean ocnLittoral1; //30 metres
	private Ocean ocnLittoral2;
    private Ocean ocnContinental1; // 30-200 metres
    private Ocean ocnContinental2;
	
	public Modele()
    {
			
	}
	
    protected void setParams(Hashtable<String, Integer> ht)
    {
        if (ht != null) // Params par defaut
        {
        	setParamsPersos(ht);
        }
        else
        {
        	// Initialiser la hashtable avec qtes par defaut
    		paramsPersos.put("corail", 7);
    		paramsPersos.put("mangrove", 0);
    		paramsPersos.put("phytoplancton", 10);
    		paramsPersos.put("etoile", 0);
    		paramsPersos.put("tortue", 0);
    		paramsPersos.put("poisson", 3);
    		paramsPersos.put("requin", 0);	
        }
    }

    protected void creerElemSimul()
    {
        Random random = new Random();
        creerTerrains();
        creerVegetaux(random);
        creerAnimaux(random);
        
        setPremObjetsCrees(true);
    }
    
    
    protected void declencherFactHum()
    {  	
    	// Aller chercher la bonne fonction
        switch(this.humData[2])
        {
        case 0: if (Controleur.getTimeCount() == this.humData[1])  // bon nb de semaines
	        	{
	        		addHumainVecEnvirs();
	        		factHumAdded= true;
	        	};
//	        	break;
//        case 1: if (Controleur.getMoisCount() == this.humData[1])  // ... de mois
//		    	{
//		    		addHumainVecEnvirs();
//		    		factHumAdded= true;
//		    	};
//		    	break;
//        case 2: if (Controleur.getAnneesCount() == this.humData[1])  // ... d'annees
//		    	{
//		    		addHumainVecEnvirs();
//		    		factHumAdded= true;
//		    	}; 
//		    	break;        	
        }
    }
    
    
    protected void addHumainVecEnvirs()
    {    	
    	if (this.humData[0] == 0) // Choix de deversement petrolier
    	{
    		factHumain = new DeversmPetrolier(10, 0, 0, -CO2_EAU*15, 4, 3);
    		vecEnvirs.addElement(factHumain);
    	}
    	// Autres options non dispo
    }
    
    
	protected void animerElem()
    {
		System.out.println("*********ANIMER**********Nouveau tour de boucle**************");
		if ( !this.factHumAdded )
		{
			declencherFactHum();			
		}
		else if (this.factHumain instanceof DeversmPetrolier)
		{	
			// Bateau ne s'est pas encore arrete
			if ( !((DeversmPetrolier) this.factHumain).getBateau().isWreck() )
			{
				((DeversmPetrolier) this.factHumain).getBateau().avancer();
			}
			else
			{
				// Le petrole va se repandre
				((DeversmPetrolier) this.factHumain).seRepandre();
			}
			
		}
    	
    	// AFFICHER LES VIVANTS
    	for (Vivant viv: this.vecVivants )
    	{   
    		if (viv.isDead())
    		{
    			this.vecMorts.add(viv);
    		}
    		
    		viv.vieillir();    		
    		
    		if ( !viv.isDead() )
    		{
    			if (viv instanceof Animal)
    			{
    				((Animal) viv).refreshBesoins();
    				
    				// Enregistrer nouveaux bebes
    	    		if ( !((Animal) viv).isMale() && ((Animal) viv).isHasBabyBorn()) // n'enregistrer que par la mere
    	    		{
    	    			vecBebes.add(((Animal) viv).getBebe());
    	    			((Animal) viv).setHasBabyBorn(false);
    	    		}
    	    		else
    	    		{
    	    			((Animal) viv).agir(this.vecEnvirs, this.vecVivants);
    	    		}
    	    		
    			}
    			else if (viv instanceof Vegetal)
    			{		
    				//System.out.println("Verifier les sprouts.");
    				((Vegetal) viv).germer();
    				
    				// Enregistrer nouvelles pousses
    				if (((Vegetal) viv).getHasNewSprout())
    				{
    					vecBebes.add(((Vegetal) viv).getBebeSprout());
    					((Vegetal) viv).setHasNewSprout(false);
    				}
    				
    				//TODO: traiter un facteur humain
    			}
    		}
    	}
    	
    	// METTRE A JOUR LES VIVANTS
    	// Ajout des bebes
    	for (Vivant bebe: this.vecBebes)
    	{
    		vecVivants.add(bebe);
    	}
    	this.vecBebes.removeAllElements();
    	
    	// Enlever les morts    	
    	for (Vivant viv: this.vecMorts)
    	{
    		String classeStr = viv.getClass().getName();
    		
    		//System.out.println("La classe de ce mort s'appelle " + classeStr);
    		
    		// Enlever du count()
    		switch (classeStr)
    		{
    			case "simulsynthese.Tortue": Tortue.incrementer(-1); break;
    			case "simulsynthese.Poisson": Poisson.incrementer(-1); break;
    			case "simulsyntheseCorail": Corail.incrementer(-1); break;
    			case "simulsyntheseMangrove": Mangrove.incrementer(-1); break;
    			case "simulsynthese.Phytoplancton": Phytoplancton.incrementer(-1); break;
    			case "simulsynthese.Etoile": Etoile.incrementer(-1); break;
    			case "simulsyntheseRequin": Requin.incrementer(-1); break;
    		}
    		
    		vecVivants.remove(viv);    		
    	}
    	this.vecMorts.removeAllElements();
    }
    
    private void creerTerrains()
    {   	
    	ocnContinental1 = new Ocean(PT_HG_OCN_CONTN_1, new Dimension(MI_LARGEUR_TERRAIN, HAUTEUR_TERR), 5, 2, O2_EAU, CO2_EAU, 199);
        ocnContinental2 = new Ocean(PT_HG_OCN_CONTN_2, new Dimension(MI_LARGEUR_TERRAIN, HAUTEUR_TERR), 5, 2, O2_EAU, CO2_EAU, 199);
        ocnLittoral1 = new Ocean(PT_HG_OCN_LITT_1, DIMS_TERRAIN_STD, 4, 3, O2_EAU, CO2_EAU, 29);
        ocnLittoral2 = new Ocean(PT_HG_OCN_LITT_2, DIMS_TERRAIN_STD, 4, 3, O2_EAU, CO2_EAU, 29);        
        ile = new Ile(PT_HG_ILE, DIMS_TERRAIN_STD, 0, 2, O2_AIR, CO2_AIR, -5, 12);
        
        vecEnvirs.add(ocnContinental1);
        vecEnvirs.add(ocnContinental2);
        vecEnvirs.add(ocnLittoral1);
        vecEnvirs.add(ocnLittoral2);
        vecEnvirs.add(ile);
    }


    private void creerVegetaux(Random random)
    {
    	EnvirTerrain terrain;    	
    	int x;
    	int y;
    	
    	// Creer coraux
        for (int i = 0; i < getParamsPersos().get("corail"); i++)
        {
        	if ( random.nextInt(2) == 0)
        	{
        		x = ((int)PT_HG_OCN_LITT_1.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie gauche
        		terrain = ocnLittoral1;
        	}
        	else
        	{
        		x = ((int)PT_HG_OCN_LITT_2.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie droite
        		terrain = ocnLittoral2;
        	}	
    		y = random.nextInt(HAUTEUR_TERR-100);
    		vecVivants.add( new Corail(new Point(x,y), Corail.getAgeAdulte(), terrain) );
        }
                
        // Creer mangroves
        for (int i = 0; i < getParamsPersos().get("mangrove"); i++)
        {          	
    		x = ((int)PT_HG_ILE.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer sur l'ile
    		y = random.nextInt(HAUTEUR_TERR-100);
    		
    		vecVivants.add( new Mangrove(new Point(x,y), Mangrove.getAgeAdulte(), ile) );
        }
        
    	// Creer planctons
		for (int i = 0; i < getParamsPersos().get("phytoplancton"); i++)
        {			
			//if ( random.nextInt(2) == 0)
        	//{
        		x = ((int)PT_HG_OCN_LITT_1.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie gauche
        		terrain = ocnLittoral1;
        	//}
        	//else
        	//{
        	//	x = ((int)PT_HG_OCN_LITT_2.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie droite
        	//	terrain = ocnLittoral2;
        	//} 
			y = random.nextInt(HAUTEUR_TERR-100);
			
			vecVivants.add( new Phytoplancton(new Point(x,y), Phytoplancton.getAgeAdulte(), terrain) );
        }
    }
    
    private void creerAnimaux(Random random)
    {
    	EnvirTerrain terrain;
    	int x;
    	int y;
    	
    	// Creer etoiles de mer
//		for (int i = 0; i < getParamsPersos().get("etoile"); i++)
//        {
//			if ( random.nextInt(2) == 0)
//        	{
//        		x = ((int)PT_HG_OCN_LITT_1.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie gauche
//        		terrain = ocnLittoral1;
//        	}
//        	else
//        	{
//        		x = ((int)PT_HG_OCN_LITT_2.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie droite
//        		terrain = ocnLittoral2;
//        	}
//			y = random.nextInt(HAUTEUR_TERR-100);
//			
//			vecVivants.add( new Etoile(new Point(x,y), Etoile.getAgeAdulte(), terrain) );
//        }
//		
		// Creer poissons
		for (int i = 0; i < getParamsPersos().get("poisson"); i++)
        {
			//if ( random.nextInt(2) == 0)
        	//{
        		x = ((int)PT_HG_OCN_LITT_1.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie gauche
        		terrain = ocnLittoral1;
        	//}
        	//else
        	//{
        	//	x = ((int)PT_HG_OCN_LITT_2.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie droite
        	//	terrain = ocnLittoral2;
        	//}
			y = random.nextInt(HAUTEUR_TERR-100);
			
			vecVivants.add( new Poisson(new Point(x,y), Poisson.getAgeAdulte(), terrain) );
        }
		
		// Creer tortues
		for (int i = 0; i < getParamsPersos().get("tortue"); i++)
        {
			x = (int)PT_HG_ILE.x + (random.nextInt(LARGEUR_TERR)); // Creer sur l'ile
    		y = random.nextInt(HAUTEUR_TERR-30);
    		
    		vecVivants.add( new Tortue(new Point(x,y), Tortue.getAgeAdulte(), ile) );
        }
		
		// Creer requins
//		for (int i = 0; i < getParamsPersos().get("requin"); i++)
//        {
//			if ( random.nextInt(2) == 0)
//        	{
//        		x = ((int)PT_HG_OCN_CONTN_1.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie gauche
//        		terrain = ocnContinental1;
//        	}
//        	else
//        	{
//        		x = ((int)PT_HG_OCN_CONTN_2.x+25) + (random.nextInt(LARGEUR_TERR-25)); // Creer ds la partie droite
//        		terrain = ocnContinental2;
//        	}
//			y = random.nextInt(HAUTEUR_TERR-100);
//			
//			vecVivants.add( new Requin(new Point(x,y), Requin.getAgeAdulte(), terrain) );
//        }
    }    

    protected boolean getPremObjetsCrees()
    {       
        return this.premObjetsCrees;
    }
    
    protected boolean setPremObjetsCrees(boolean crees)
    {       
        return this.premObjetsCrees = crees;
    }
    
    protected Ile getIle() {
		return this.ile;
	}
	
	protected static Hashtable<String, Integer> getParamsPersos() {
		return paramsPersos;
	}

	protected void setParamsPersos(Hashtable<String, Integer> paramsPersos) {
		Modele.paramsPersos = paramsPersos;
	}

	protected Vector<Environnement> getVecEnvirs() {
		return vecEnvirs;
	}

	protected void setVecEnvirs(Vector<Environnement> vecEnvirs) {
		this.vecEnvirs = vecEnvirs;
	}


	protected Vector<Vivant> getVecVivants() {
		return vecVivants;
	}

	protected void setVecVivants(Vector<Vivant> vecVivants) {
		this.vecVivants = vecVivants;
	}
	
    protected void AFFICHER_VECTEUR (Vector vec)
    {
    	for (Object o : vec)
    	{
    		System.out.println("    "+o);
    	}
    }

	public static int[] getFactHumData() {
		return factHumData;
	}

	public static void setFactHumData(int[] factHumData) {
		Modele.factHumData = factHumData;
	}

	public int[] getHumData() {
		return humData;
	}

	public void setHumData(int[] humData) {
		this.humData = humData;
	}
}


//
//private EnvirTerrain genererTerrain(Point pt)
//{
// Utiliser le CONTAINS
//	if (pt.getX() > 200 && pt.getX() < 600)
//	{
//		return ocnLittoral1;
//	}
//	if (pt.getX() > 1000 && pt.getX() < 1400)
//	{
//		return ocnLittoral2;
//	}
//	if (pt.getX() > 0 && pt.getX() < 200)
//	{
//		return ocnContinental1;
//	}
//	if (pt.getX() > 1400 && pt.getX() < 1600)
//	{
//		return ocnContinental2;
//	}
//	
//	return ile;
//}

//
//private Point genererPoint(Vivant viv)
//{
//	Random random = new Random();		
//	int x = 0;
//	int y = 0;
//	//EnvirTerrain terrain;
//	
//	if (viv instanceof Poisson || viv instanceof Corail || viv instanceof Phytoplancton)
//	{
//		if ( random.nextInt(2) == 0)
//    	{
//    		x = ((int)PT_HG_OCN_LITT_1.getX()) + (random.nextInt(LARGEUR_TERR-10)); // Creer ds la partie gauche
//    		//terrain = ocnLittoral1;
//    	}
//    	else
//    	{
//    		x = ((int)PT_HG_OCN_LITT_2.getX()) + (random.nextInt(LARGEUR_TERR-10)); // Creer ds la partie droite
//    		//terrain = ocnLittoral2;
//    	}	
//		
//	}
//	else if (viv instanceof Tortue || viv instanceof Mangrove)
//	{
//		x = (int)PT_HG_ILE.getX() + (random.nextInt(LARGEUR_TERR-10)); // Creer sur l'ile
//		
//		//System.out.println("xMangrove ==" + x + " yMangrove == " + y);
//		//vecVivants.add( new Mangrove(new Point(x,y), new Dimension(50, 50), 55, ile) );
//	}
//	
//	y = new Random().nextInt(HAUTEUR_TERR-100);
//	
//	return new Point(x,y);
//}
