package simulsynthese;

public class Main
{	
	
	public static Modele modele;
	public static Vue vue;
	public static Controleur contr;
	
	
	public static void main(String[] args)
	{
		modele = new Modele();
		vue = new Vue();
		contr = new Controleur(modele, vue);
		
		setControleur(contr);
		
		contr.initProg();
	}

	private static void setControleur(Controleur contr)
	{
		//modele.setControleur(contr);
		vue.setControleur(contr);
	}	
}