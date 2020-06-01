package simulsynthese;

import java.awt.Point;
import java.util.Vector;
import java.awt.Color;

public class Poisson extends Animal
{
	private static final int FULL_ENERGIE = 1005;
	
	private static int count = 0;
	private static Object[] preds = {Requin.class, Tortue.class};
	private static Object[] proies = {Phytoplancton.class};
	private static int traitDomin; // 0, 1, 2 
    private static int rayonRadar = 60;    
    private static int[][] grandeurs = { {7, 15}, {10, 20}, {10, 25} };
    private static Color[] couleurs = new Color[] { new Color(109, 99, 184), new Color(12, 69, 255), new Color(123, 24, 237)};
    private static int ageAdulte = 300;
	private static int ageMax = 3000;
    private static int lent = 5;
	private static int rapide = 13;
	
	//https://www.fishipedia.fr/poissons/chromis-viridis
	private static float[] phs = {8.2f, 3.3f, 3.4f};
  	private static int[] tempers = {22, 25, 28};
  	private static int[] salins = {38, 34, 38};

    public Poisson (Point pt, int age, EnvirTerrain terrain)
    {
    	super(pt, Poisson.FULL_ENERGIE, grandeurs, new int[] {age, ageAdulte, ageMax}, couleurs, rayonRadar, new int[] {lent, rapide}, terrain, preds, proies );
    	Poisson.incrementer(1);
    }
    
	@Override
	protected boolean verifierTerrainCible()
	{
		System.out.println("Verifier terrain cible");
		Vector<EnvirTerrain> vec = new Vector<EnvirTerrain>();
		
		if (super.getRadar() != null)
		{
			vec = super.getRadar().getVecTerr();
		}		
		
		//if (vec != null)
		//{
			for (EnvirTerrain terr: vec)
			{
				if (terr instanceof Ile)
				{
					if (terr.getAire().contains(getPtCentre()) )
					{
						return false;
					}
				}
			}
		//}
		return true;
	}
	
	@Override
	protected void trouverCibleDodo(Vector<Vegetal> vegs)
	{
		//boolean cibleTrouvee = false;
		
		if (super.getCibleDodo() == null)
		{
			for (Vegetal veg : vegs)
			{
				if (veg instanceof Corail)
				{
					setCibleRand(null);
					//setCibleViv(null);
					setProie(null);
					setMate(null);
					//super.viderCibles();					
					setCibleDodo(veg);
					//cibleTrouvee = true;
					setHasReachedTarget(false);
				}
			}	
		}
		
		//return cibleTrouvee;
	}
	
	@Override
	protected void accoucher()
	{
		Poisson nouvBebe = null;
    	
		nouvBebe = new Poisson(new Point(super.getPtCentre()), 0, super.getTerrain() );		
		super.setHasBabyBorn(true);
		
		// Enregistrer la relation mere-enfant
		nouvBebe.setMere(this);		
		super.setGestationActu(super.getTempsGestation());
		super.addBebe(nouvBebe);
	}
	
    protected static int getAgeAdulte() {
		return ageAdulte;
	}

	protected static void setAgeAdulte(int ageAdulte) {
		Poisson.ageAdulte = ageAdulte;
	}
	
    protected static int getTraitDomin() {
		return traitDomin;
	}

	protected static void setTraitDomin(int traitDomin) {
		Poisson.traitDomin = traitDomin;
	}
	
    public int getFullEnergie() {
		return Poisson.FULL_ENERGIE;
	}

	public static void incrementer(int newIndiv)
    {
        count += newIndiv;
        Animal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Poisson.count;
    }

	@Override
	protected void manger()
	{	    
	    addNivEnergie(super.getProie().getNivEnergieMax());
	    
	    if ( getNivEnergie() > getFullEnergie())
	    {
	    	setNivEnergie(getFullEnergie());
	    }
	    
	    super.getProie().setNivEnergie(0);
	    super.getProie().setDead(true);
	    super.setProie(null);
	}


}