package simulsynthese;

import java.awt.Point;
import java.util.Random;
import java.awt.Color;

public class Corail extends Vegetal
{
	private static final int FULL_ENERGIE = 200;
	
    private static int count = 0;
    private static int rayonDispersion = 30;
    
    private static Color[] couleurs = new Color[] {new Color(252, 198, 194), new Color(248, 131, 121)};
    private static int[][] grandeurs = { {20, 80}, {30, 200} }; // enfant, adulte
    private static int tempsGermin = 1000;
    private static int ageAdulte = 500;
    private static int ageMax = 3000;

    public Corail(Point pt, int age, EnvirTerrain terrain)
    {
    	super(pt, Corail.FULL_ENERGIE, grandeurs, new int[]{age, ageAdulte, ageMax}, tempsGermin, couleurs, terrain);
    	Corail.incrementer(1);
    }
    
	@Override
	protected void seRegenerer()
	{
		//System.out.println("corail---Dans seRegenerer");
		Corail nouvPousse = null;
    	
		// Genere un nouvel endroit de pousse a proximite
    	int x = super.getAire().x-rayonDispersion+new Random().nextInt( (int)(super.getAire().width+2*rayonDispersion) );
    	int y = super.getAire().y-rayonDispersion+new Random().nextInt( (int)(super.getAire().height+2*rayonDispersion) );
    	
		nouvPousse = new Corail(new Point(x, y), 0, super.getTerrain() );
		
		super.setHasNewSprout(true);		
		super.addBebeSprout(nouvPousse);		
	}

    protected static int getAgeAdulte() {
		return Corail.ageAdulte;
	}

	public static void incrementer(int newIndiv)
    {
        Corail.count += newIndiv;
        Vegetal.incrementer(newIndiv);
    }
    
    public static int getCount()
    {
        return Corail.count;
    }
}