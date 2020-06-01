package simulsynthese;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;

public class Vivant
{
	protected static int count = 0;	
	
	private boolean isDead = false;
	private Rectangle aire = new Rectangle();
	private Point ptCentre;
	private EnvirTerrain terrain;
    private Color[] couleurs;
    private Color coulActu;
    private int[][] grandeurs; // grandeur enfant et adulte
    private int[] ages; // age actuel, age adulte et age maximal
    private int nivEnergie;
    private int nivEnergieMax;
    
    // Valeurs du corail, pour l'instant
//    public static double phIdeal = 8.2;
//    public static double phMin = 7.8;
//    public static int tempMin = 19;
//    public static int tempIdeale = 27;
//    public static int tempMax = 31; // vraie valeur 30.5
//    public static int salinMin = 28; // ppt "parts per thousand"
//    public static int salinIdeale = 34;
//    public static int salinMax = 38;
    
    public Vivant()
    {
		;
    }
    
	public Vivant(Point pt, int fullEnergie, int[][] grandeurs, int[] ages, Color[] coul, EnvirTerrain terrain)
    {
		this.grandeurs = grandeurs;
		this.terrain = terrain;
		this.nivEnergieMax = fullEnergie;
		this.nivEnergie = fullEnergie;
		setAges(ages);
		setAire(pt);
		setPtCentre();
        setCouleurs(coul);
        setCoulActu();
    }
	
	protected void vieillir()
	{
		// L'age actuel est plus petit que l'age maximal		
		if (this.ages[0] <= this.ages[2])
		{
			this.ages[0]++;;
			setCoulActu();					
		}
		else
		{
			setDead(true);
		}
	}
	
	protected Point getPtCentre()
	{
		return this.ptCentre;
	}
	
	protected void setPtCentre() {
		this.ptCentre = new Point((int)getAire().getCenterX(), (int)getAire().getCenterY() );
	} 
	
	protected Rectangle getAire() {
		return aire;
	}

	protected void setAire(Point pt, int largeur, int hauteur)
	{
		this.aire.setBounds(pt.x-(int)(largeur/2), pt.y-(int)(hauteur/2), largeur, hauteur);		
	}	
	
	protected void setAire(Point pt)
	{
		int idx;
		int largeur;
		int hauteur;
		
		// L'age est-il celui d'un enfant ?
		idx =  this.ages[0] < this.ages[1] ? 0 : 1;
		
		largeur = this.grandeurs[idx][0];
		hauteur = this.grandeurs[idx][1];
		
		setAire(pt, largeur, hauteur);
	}	    

     protected int getAge()
	{
		return this.ages[0];
	}

     protected void setAge(int age) {
		this.ages[0] = age;
	}

     protected EnvirTerrain getTerrain() {
		return terrain;
	}

	protected void setTerrain(EnvirTerrain terrain) {
		this.terrain = terrain;
	}
	
	protected boolean isDead() {
		return isDead;
	}

	protected void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	protected int[] getAges() {
		return ages;
	}

	protected void setAges(int[] ages) {
		this.ages = ages;
	}

	protected Color[] getCouleurs() {
		return couleurs;
	}

	protected void setCouleurs(Color[] couleurs) {
		this.couleurs = couleurs;
	}

	protected Color getCoulActu() {
		return coulActu;
	}

	protected void setCoulActu()
	{
		// L'age actuel est plus petit que l'age maximal
		// Le vegetal a une couleur d'enfant
		if (this.ages[0] <= this.ages[1])
		{
			this.coulActu = this.couleurs[0];
		}
		else
		{
			// Animal a une couleur selon le sexe
			if (this instanceof Animal)
			{
				this.coulActu = ((Animal)this).isMale() ? this.couleurs[1] : this.couleurs[2]; 								
			}
			else // Vegetal n'a qu'une couleur adulte
			{
				this.coulActu = this.couleurs[1];
			}			
		}		
	}

	

	protected int getNivEnergie()
	{
		return nivEnergie;
	}

	protected void setNivEnergie(int nivEnergie) {
		this.nivEnergie = nivEnergie;
	}

	protected void addNivEnergie(int increm)
	{
		this.nivEnergie += increm;
	}
	
	public int getNivEnergieMax() {
		return nivEnergieMax;
	}

	public void setNivEnergieMax(int nivEnergieMax) {
		this.nivEnergieMax = nivEnergieMax;
	}

	public static void incrementer(int newIndiv)
    {
    	Vivant.count += newIndiv;
    }
     
     public static int getCount()
     {
         return Vivant.count;
     }


}