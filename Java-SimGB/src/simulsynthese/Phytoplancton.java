package simulsynthese;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;

public class Phytoplancton extends Vegetal // se nourrit par photosynthese
{
	private static final int FULL_ENERGIE = 100;
	
	public static int count = 0;
	private static int rayonDispersion = 15;
	 
	private static int[][] grandeurs = { {5, 5}, {10, 10} };
    private static Color[] couleurs = new Color[] {new Color(220, 254, 223), new Color(154, 230, 11)};
    private static int tempsGermin = 500;
    private static int ageAdulte = 100;
    private static int ageMax = 5000;
    
    public Phytoplancton (Point pt, int age, EnvirTerrain terrain)
    {
    	super(pt, Phytoplancton.FULL_ENERGIE, grandeurs, new int[]{age, ageAdulte, ageMax}, tempsGermin, couleurs, terrain); 
    	Phytoplancton.incrementer(1);
    }
    
	@Override
	protected void seRegenerer()
	{	
		//System.out.println("phytoplancton---Dans seRegenerer");
		Phytoplancton nouvPousse = null;
    	
		// Genere un nouvel endroit de pousse a proximite
    	int x = super.getAire().x-rayonDispersion+new Random().nextInt( (int)(super.getAire().width+2*rayonDispersion) );
    	int y = super.getAire().y-rayonDispersion+new Random().nextInt( (int)(super.getAire().height+2*rayonDispersion) );
    	
		nouvPousse = new Phytoplancton(new Point(x, y), 0, super.getTerrain() );
		
		super.setHasNewSprout(true);
		super.addBebeSprout(nouvPousse);		
	}
	
    protected static int getAgeAdulte() {
		return Phytoplancton.ageAdulte;
	}
	
    public int getFullEnergie() {
		return Phytoplancton.FULL_ENERGIE;
	}

	public static void incrementer(int newIndiv)
    {
        Phytoplancton.count += newIndiv;
        Vegetal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Phytoplancton.count;
    }
}