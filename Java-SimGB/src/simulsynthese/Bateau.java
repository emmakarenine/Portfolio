package simulsynthese;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class Bateau extends Rectangle
{
	private EnvirHumain envir;
	private Point pt = null;
	private static int largeur = 100;
	private static int hauteur = 500;
	
	private boolean isWreck = false;
	private int bruit = 10;	
	private Color couleur = new Color(158, 25, 8);	
	private int xPas = 0;
	private int yPas = -80;

	
	public Bateau(EnvirHumain envir, int x, int y)
	{
		super(x-largeur/2, y-hauteur/2, largeur, hauteur);
		this.envir = envir;
		setPt(new Point(x-largeur/2, y-hauteur/2));
	}

	protected Color getCouleur() {
		return this.couleur;
	}

	protected void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	protected int getBruit() {
		return this.bruit;
	}

	protected void setBruit(int bruit) {
		this.bruit = bruit;
	}
	
	protected void avancer()
	{
		//System.out.println("bateau----dans Avancer");
		
		if (!this.isWreck && super.getCenterY() > 276)
		{
			super.translate(xPas, yPas);			
		}
		else
		{
			this.setWreck(true);
		}
	}

	public Point getPt() {
		return pt;
	}

	public void setPt(Point pt) {
		this.pt = pt;
	}
	
	protected static int getLargeur() {
		return largeur;
	}

	protected static void setLargeur(int largeur) {
		Bateau.largeur = largeur;
	}

	protected static int getHauteur() {
		return hauteur;
	}

	protected static void setHauteur(int hauteur) {
		Bateau.hauteur = hauteur;
	}

	public boolean isWreck() {
		return isWreck;
	}

	public void setWreck(boolean isWreck) {
		this.isWreck = isWreck;
	}

}