package simulsynthese;

import java.awt.Point;
import java.util.Vector;
import java.awt.Color;
public class Tortue extends Animal
{
	//private static Vector<Color> couleurs = new Vector<Color>();
	private static final int FULL_ENERGIE = 1005;
	
	private static int count = 0;
	private static Object[] preds = {Requin.class};
	private static Object[] proies = {Poisson.class, Phytoplancton.class};
 	private static int traitDomin = 0; //0, 1, 2
	private static int rayonRadar = 80;
	
	private static int[][] grandeurs = { {10, 20}, {20, 30}, {30, 45} };
    private static Color[] couleurs = new Color[]{new Color(151, 199, 156), new Color(8, 160, 21), new Color(107, 176, 114)};
    private static int ageAdulte = 750;
	private static int ageMax = 3000;
    private static int lent = 5;
	private static int rapide = 7;
//    public static double phIdeal = 8.2;
//    public static double phMin = 7.8;
//    public static int tempMin = 19;
//    public static int tempIdeale = 27;
//    public static int tempMax = 31; // vraie valeur 30.5
//    public static int salinMin = 28; // ppt "parts per thousand"
//    public static int salinIdeale = 34;
//    public static int salinMax = 38;
    
    public Tortue (Point pt, int age, EnvirTerrain terrain)
    {
    	super(pt, Tortue.FULL_ENERGIE, grandeurs, new int[] {age, ageAdulte, ageMax}, couleurs, rayonRadar, new int[] {lent, rapide}, terrain, preds, proies );
    	Tortue.incrementer(1);
    }
    
	@Override
	protected boolean verifierTerrainCible() {
		// TODO Auto-generated method stub
		return true;
	}
    
   
	@Override
    protected void accoucher()
    {
    	Tortue nouvBebe = null;
    	
    	int xMere = (int) this.getAire().getCenterX();
		int yMere = (int) this.getAire().getCenterY ();
		nouvBebe = new Tortue(new Point(xMere, yMere), 0, super.getTerrain() );
		super.setHasBabyBorn(true);
		
		// Enregistrer la relation mere-enfant
		nouvBebe.setMere(this);		
		super.setGestationActu(super.getTempsGestation());
		super.addBebe(nouvBebe);		
    }

	public static int getAgeAdulte() {
		return ageAdulte;
	}

	public static void setAgeAdulte(int ageAdulte) {
		Tortue.ageAdulte = ageAdulte;
	}

    protected static int getTraitDomin() {
		return Tortue.traitDomin;
	}

	protected static void setTraitDomin(int traitDomin) {
		Tortue.traitDomin = traitDomin;
	}

    public static void incrementer(int newIndiv)
    {
        count += newIndiv;
        Animal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Tortue.count;
    }

	@Override
	protected void manger()
	{
		addNivEnergie(super.getProie().getNivEnergieMax());
	    
	    if ( getNivEnergie() > getNivEnergieMax())
	    {
	    	setNivEnergie(getNivEnergieMax());
	    }
	    
	    super.getProie().setNivEnergie(0);
	    super.getProie().setDead(true);
	    super.setProie(null);
		
	}

	@Override
	protected void trouverCibleDodo(Vector<Vegetal> vegs) {
		// TODO Auto-generated method stub
		
	}



}