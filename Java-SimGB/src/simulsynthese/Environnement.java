package simulsynthese;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;

public abstract class Environnement
{
	private Area aire;
	private Color[] couleurs;
	
	private Color couleurActu;
	

	private int nivStress;
    private int nivBruit;

	public Environnement (Shape aire, Color[] couleurs, int stress, int bruit)
	{
		this.setAire(aire);
		this.couleurs = couleurs;
        this.nivStress = stress;
        this.nivBruit = bruit;
	};


	protected int getNivStress() {
		return nivStress;
	}

	protected void setNivStress(int nivStress) {
		this.nivStress = nivStress;
	}

	protected int getNivBruit() {
		return nivBruit;
	}

	protected void setNivBruit(int nivBruit) {
		this.nivBruit = nivBruit;
	}

	public Area getAire() {
		return this.aire;
	}

	public void setAire(Shape shape) {
		this.aire = new Area(shape);
	}
	
	public void setAire(int x, int y, int largeur, int hauteur)
	{
		((Rectangle) this.aire.getBounds()).setBounds(x, y , largeur, hauteur);
	}

	public Color[] getCouleurs() {
		return couleurs;
	}

	public Color getCouleurActu() {
		return couleurActu;
	}


	public void setCouleurActu()
	{
		int idx = 0;
		
		if (this instanceof Ocean)
		{
			if ( ((Ocean)this).getProfondeur() >= 30)
			{
				idx = 1;
			}
							
		}
		
		this.couleurActu = couleurs[idx];
	}

}
