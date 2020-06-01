package simulsynthese;

import java.awt.Shape;
import java.awt.Color;

public class EnvirTerrain extends Environnement
{
	private int o2Dispo;
	private int prodCO2;
	private static int[] temperatures;
	
	private int temperActu;
	private int profondeur;
	
	public EnvirTerrain(Shape aire, Color[] couleurs, int stress, int bruit, int o2, int co2, int[][] tempers, int profondeur)
	{
		super(aire, couleurs, stress, bruit);				
		this.o2Dispo = o2;
		this.prodCO2 = co2;
		this.profondeur = profondeur;
		super.setCouleurActu();
		setTemperatures(tempers);
		setTemperActu();
	}
	
	protected int getO2Dispo() {
		return this.o2Dispo;
	}

	protected void setO2Dispo(int o2Dispo) {
		this.o2Dispo = o2Dispo;
	}

	protected int getProdCO2() {
		return this.prodCO2;
	}

	protected void setProdCO2(int prodCO2) {
		this.prodCO2 = prodCO2;
	}

	protected int getProfondeur() {
		return this.profondeur;
	}

	protected void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}

	protected int[] getTemperatures() {
		return EnvirTerrain.temperatures;
	}

	protected void setTemperatures(int[][] temperatures)
	{
		// Ocean a des temperatures differentes selon profondeur
		if (this instanceof Ocean && this.getProfondeur() >= 30)
		{
			EnvirTerrain.temperatures = temperatures[1];
			
		}
		else
		{
			EnvirTerrain.temperatures = temperatures[0];
		}
	}

	protected int getTemperActu() {
		return this.temperActu;
	}

	protected void setTemperActu() {
		this.temperActu = EnvirTerrain.temperatures[1];
	}
	
	protected void setTemperActu(int temper) {
		this.temperActu  = temper;
	}
	
	protected void addTemperActu(int increm) {
		this.temperActu += increm;
	}

}
