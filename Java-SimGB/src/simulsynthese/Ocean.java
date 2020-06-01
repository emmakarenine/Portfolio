package simulsynthese;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Ocean extends EnvirTerrain
{
	private static Color[] couleurs = { new Color(102, 255, 255), new Color(4, 146, 194) }; // peu prof, prof
	private static int[][] temperPossibles = { {10, 21, 30}, {12, 26, 35} }; // min, moyenne, max
	private static float[] phs = {7.0f, 8.2f, 9.5f}; // min, moy, max
	private static int[] salinites = {30, 35, 39}; // min, moy, max
	
	private double phActu = Ocean.phs[1];
	private int salinActu = Ocean.salinites[1];

	public Ocean(Point topLeft, Dimension miTerrain, int stress, int bruit, int o2, int co2, int profondeur)
	{
		super(new Rectangle(topLeft.x, topLeft.y, miTerrain.width, miTerrain.height), Ocean.couleurs, stress, bruit,  o2, co2, Ocean.temperPossibles, profondeur);
	}

	public double getPhActu() {
		return phActu;
	}

	public void setPhActu(double phActu) {
		this.phActu = phActu;
	}	

    public int getSalinite() {
		return this.salinActu;
	}
	
	public void setSalinite(int salinite) {
		this.salinActu = salinite;
	}

	protected static float[] getPhs() {
		return phs;
	}

	protected static void setPhs(float[] phs) {
		Ocean.phs = phs;
	}

	protected static int[] getSalinites() {
		return salinites;
	}

	protected static void setSalinites(int[] salinites) {
		Ocean.salinites = salinites;
	}

	protected int getSalinActu() {
		return this.salinActu;
	}

	protected void setSalinActu(int salinActu) {
		this.salinActu = salinActu;
	}

}
