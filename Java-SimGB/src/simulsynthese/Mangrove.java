package simulsynthese;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;

public class Mangrove extends Vegetal
{
	private static final int FULL_ENERGIE = 5;
    private static int count = 0;
    private static int rayonDispersion = 80;
    
    private static Color[] couleurs = new Color[] {new Color(204, 248, 143), new Color(125, 212, 3)};
    private static int[][] grandeurs = { {30, 30}, {50, 50} }; // enfant, adulte
	private static int tempsGermin = 2000;
    private static int ageAdulte = 300;
	private static int ageMax = 5000;

    public Mangrove(Point pt, int age, EnvirTerrain terrain)
    {
        super(pt, Mangrove.FULL_ENERGIE, grandeurs, new int[]{age, ageAdulte, ageMax}, tempsGermin, couleurs, terrain);
        Mangrove.incrementer(1);
    }

	@Override
	protected void seRegenerer()
	{
		//System.out.println("mangrove---Dans seRegenerer");
		Mangrove nouvPousse = null;
    	
		// Genere un nouvel endroit de pousse a proximite
    	int x = super.getAire().x-rayonDispersion+new Random().nextInt( (int)(super.getAire().width+2*rayonDispersion) );
    	int y = super.getAire().y-rayonDispersion+new Random().nextInt( (int)(super.getAire().height+2*rayonDispersion) );
    	
		nouvPousse = new Mangrove(new Point(x, y), 0, super.getTerrain() );
		
		super.setHasNewSprout(true);		
		super.addBebeSprout(nouvPousse);		
	}
	
    protected static int getAgeAdulte() {
		return Mangrove.ageAdulte;
	}

    public static void incrementer(int newIndiv)
    {
        Mangrove.count += newIndiv;
        Vegetal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Mangrove.count;
    }

}