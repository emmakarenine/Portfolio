package simulsynthese;

import java.awt.Point;
import java.awt.Color;

public abstract class Vegetal extends Vivant
{
	private static int count = 0;
	
    private int tempsGermination;      
    
    private int tempsGerminActu;
	private Vegetal bebeSprout = null;
	private boolean hasNewSprout = false;

    public Vegetal(Point pt, int fullEnergie, int[][] grandeur, int[] ages, int tempsGermin, Color[] coul, EnvirTerrain terrain)
    {
        super(pt, fullEnergie, grandeur, ages, coul, terrain);
        this.tempsGermination = tempsGermin;
        this.tempsGerminActu = this.tempsGermination;
    }
    
    protected abstract void seRegenerer();
    
    
    protected void germer()
    {
    	addTempsGerminActu(-1);
		
		if ( this.getTempsGerminActu() == 0)
		{
			this.seRegenerer();
			// Recommencer le decompte
			this.tempsGerminActu = this.tempsGermination;
		}
	}
    
    protected int tempsGermination()
    {
		return tempsGermination;
	}

    protected int getTempsGerminActu() {
		return this.tempsGerminActu;
	}
    
    protected void setTempsGerminActu(int temps)
    {
		this.tempsGerminActu = temps;
	}
	
    protected void addTempsGerminActu(int increm)
    {
		this.tempsGerminActu += increm;
	}

    protected boolean getHasNewSprout()
	{
		return hasNewSprout;
	}

    protected void setHasNewSprout(boolean hasNewSprout)
	{
		this.hasNewSprout = hasNewSprout;
	}

    protected Vegetal getBebeSprout() {
		return bebeSprout;
	}

	protected void addBebeSprout(Vegetal bebeSprout) {
		this.bebeSprout = bebeSprout;
	}
	
	public static void incrementer(int newIndiv)
    {
        count += newIndiv;
        Vivant.incrementer(newIndiv);
    }
    
	public static int getCount()
    {
        return Vegetal.count;
    }
    
}
