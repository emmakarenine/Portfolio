package simulsynthese;

public interface VueUtil {
	
	// Affichage sur l'ecran
	public void afficher();	
	public void fermerFenetre();
	
	// Getters
	public int getRep();
	public boolean getRepSet();
	public boolean getParamsAssigned();
	
	// Setters
	public void setRep(int rep);
	public void setRepSet(boolean b);
	public void setParamsAssigned(boolean b);
	
	// Action de l'usager
	public void enregChoix(int rep);		
}