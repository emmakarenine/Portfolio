package simulsynthese;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class DeversmPetrolier extends EnvirHumain // Classe hardcodee
{
	private static Ellipse2D.Float aire = new Ellipse2D.Float(1375, 0, 100, 500);
	private static Color[] couleurs = { new Color(78, 78, 78), new Color(7, 7, 7) };
	private int vitDispersion;
	private int vitDispar;
	private Bateau bateau = null;

	public DeversmPetrolier(int stress, int bruit, int consoO2, int prodCO2, int vitDispers, int vitDispar)
	{
		super(DeversmPetrolier.aire, DeversmPetrolier.couleurs, stress, bruit, consoO2, prodCO2);
		this.bateau = new Bateau(this, (int)aire.getCenterX(), 1000); // bateau est hors-champ et va monter dans l'ecran
		this.vitDispersion = vitDispers;
		this.setVitDispar(vitDispar);
	}

	protected int getVitDispersion() {
		return this.vitDispersion;
	}

	protected Bateau getBateau() {
		return bateau;
	}

	protected void setBateau(Bateau bateau) {
		this.bateau = bateau;
	}

	protected int getVitDispar() {
		return vitDispar;
	}

	protected void setVitDispar(int vitDispar) {
		this.vitDispar = vitDispar;
	}
	
	protected void seRepandre()
	{		
		int xNew = super.getAire().getBounds().x-this.vitDispersion;
		int yNew = this.bateau.y-this.vitDispersion;
		int newLarg = super.getAire().getBounds().width+this.vitDispersion*2;
		int newHaut = super.getAire().getBounds().height+this.vitDispersion*2;
		
		setAire(xNew, yNew, newLarg, newHaut);
	}

}
