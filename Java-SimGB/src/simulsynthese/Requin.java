package simulsynthese;

import java.awt.Point;
import java.util.Vector;
import java.awt.Color;

public class Requin extends Animal
{
	private static final int FULL_ENERGIE = 1500;
	public static int count = 0;	
	public static int traitDomin; // 0, 1, 2
	private static int rayonRadar = 160;
    
    private static int[][] grandeurs = { {40, 10}, {70, 25}, {80, 30} };
    private static Color[] couleurs = new Color[] { new Color(128, 228, 237), new Color(33, 208, 222), new Color(194, 235, 237)};
    private static int ageAdulte = 500;
	private static int ageMax = 3500;
	private static int lent = 10;
	private static int rapide = 15;

    public Requin (Point pt,  int age, EnvirTerrain terrain)
    {
    	super(pt, Requin.FULL_ENERGIE, grandeurs, new int[] {age, ageAdulte, ageMax}, couleurs, rayonRadar, new int[] {lent, rapide}, terrain, null, new Object[] {Tortue.class, Poisson.class, Etoile.class} );
    	Requin.incrementer(1);
    }    

	@Override
	protected boolean verifierTerrainCible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void accoucher()
	{
		Requin nouvBebe = null;
    	
    	int xMere = (int) this.getAire().getCenterX();
		int yMere = (int) this.getAire().getCenterY ();
		nouvBebe = new Requin(new Point(xMere, yMere), 0, super.getTerrain() );
		super.setHasBabyBorn(true);
		
		// Enregistrer la relation mere-enfant
		nouvBebe.setMere(this);		
		super.setGestationActu(super.getTempsGestation());
		super.addBebe(nouvBebe);
	}

    public static void incrementer(int newIndiv)
    {
        count += newIndiv;
        Animal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Requin.count;
    }
    
    protected static int getTraitDomin() {
		return Requin.traitDomin;
	}

	protected static void setTraitDomin(int traitDomin) {
		Requin.traitDomin = traitDomin;
	}

	public static int getAgeAdulte() {
		return ageAdulte;
	}


	public static void setAgeAdulte(int ageAdulte) {
		Requin.ageAdulte = ageAdulte;
	}

	@Override
	protected void manger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void trouverCibleDodo(Vector<Vegetal> vegs) {
		// TODO Auto-generated method stub
		
	}




}