package simulsynthese;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Ile extends EnvirTerrain 
{
	//private static int decalageHaut = 300;
	private static Color[] couleurs = {new Color(247, 234, 156)};
	private static int[][] temperatures = { {5, 28, 40} }; // min, moyenne, max
	
	private int nbHrsSoleil;
		
	public Ile(Point topLeft, Dimension terrainStd, int stress, int bruit, int o2, int co2, int profondeur, int nbHrsSoleil)
	{
		super( new Ellipse2D.Float(topLeft.x, topLeft.y, terrainStd.width, terrainStd.height), Ile.couleurs, stress, bruit, o2, co2, Ile.temperatures, profondeur);
		this.setNbHrsSoleil(nbHrsSoleil);
	}

	public int getNbHrsSoleil() {
		return nbHrsSoleil;
	}

	public void setNbHrsSoleil(int nbHrsSoleil) {
		this.nbHrsSoleil = nbHrsSoleil;
	}

}
