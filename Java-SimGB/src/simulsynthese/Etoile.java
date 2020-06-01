package simulsynthese;

import java.awt.Point;
import java.util.Vector;
import java.awt.Color;
import java.awt.Dimension;

public class Etoile extends Animal
{
	private static final int FULL_ENERGIE = 300;
	private static int count = 0;
    public static int traitDomin; // 0, 1, 2
    private static int rayonRadar = 100;
    private static int[][] grandeurs = { {20, 20}, {22, 22}, {25, 25}  };
    private static Color[] couleurs = new Color[] { new Color(235, 148, 187), new Color(255, 87, 126), new Color(237, 154, 176)};
    private static int ageAdulte = 500;

	private static int ageMax = 5000;
    private static int lent = 1;
	private static int rapide = 2;
    
//	public static double phIdeal = 8.2;
//  public static double phMin = 7.8;
//  public static int tempMin = 19;
//  public static int tempIdeale = 27;
//  public static int tempMax = 31; // vraie valeur 30.5
//  public static int salinMin = 28; // ppt "parts per thousand"
//  public static int salinIdeale = 34;
//  public static int salinMax = 38;

    public Etoile (Point pt, int age, EnvirTerrain terrain)
    {
    	super(pt, Etoile.FULL_ENERGIE, grandeurs, new int[] {age, ageAdulte, ageMax}, couleurs, rayonRadar, new int[] {lent, rapide},terrain, new Object[] {Tortue.class, Requin.class}, new Object[] {Phytoplancton.class} );
    	Etoile.incrementer(1);
    }
	
	@Override
	protected void accoucher()
	{
		Etoile nouvBebe = null;
    	
    	int xMere = (int) this.getAire().getCenterX();
		int yMere = (int) this.getAire().getCenterY ();
		nouvBebe = new Etoile(new Point(xMere, yMere), 0, super.getTerrain() );
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
		Etoile.ageAdulte = ageAdulte;
	}


    protected static int getTraitDomin() {
		return traitDomin;
	}

	protected static void setTraitDomin(int traitDomin) {
		Etoile.traitDomin = traitDomin;
	}


	
    public static void incrementer(int newIndiv)
    {
        Etoile.count += newIndiv;
        Animal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Etoile.count;
    }

	@Override
	protected boolean verifierTerrainCible() {
		// TODO Auto-generated method stub
		return false;
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