package simulsynthese;

import java.awt.Shape;
import java.awt.Color;

public class EnvirHumain extends Environnement
{   
	private int consoO2;
	private int prodCO2;
	
	public EnvirHumain(Shape aire, Color[] couleurs, int stress, int bruit, int o2, int co2)
	{
		super(aire, couleurs, stress, bruit);
		this.consoO2 = o2;
		this.prodCO2 = co2;
	}	

	protected int getConsoO2() {
		return consoO2;
	}

	protected void setConsoO2(int consoO2) {
		this.consoO2 = consoO2;
	}

	protected int getProdCO2() {
		return prodCO2;
	}

	protected void setProdCO2(int prodCO2) {
		this.prodCO2 = prodCO2;
	}

}
