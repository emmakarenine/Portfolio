package simulsynthese;

import java.util.Hashtable;
import java.util.Vector;

public class Vue implements Runnable
{	
	private Hashtable<String, Integer> paramsPersos = null;
	
	private Controleur contr;
	private MenuInit menuInit;
	private MenuParams menuParams;
	//private FrameStats frameStats;
	private FrameSimul frameSimul = null;

	private Canevas canevas;
		
	private boolean paramsEnRegle = false;
	private int repInit;
	private int repParams;
		
	public Vue()
	{		
			
	}
	
	protected void initProg()
	{		
		while (!this.paramsEnRegle)
		{
			afficherMenuInit();
			activerRepInit();
		}
		// Envoyer la reponse au Contr
		contr.initSimul(this.paramsPersos);
		
	}
	
	private int afficherMenuInit()
	{
		menuInit = new MenuInit();
		
		while ( !menuInit.getRepSet() )
		{
			menuInit.afficher();
		}
		
		this.repInit = menuInit.getRep();
		menuInit.setRepSet(false);
		
		return this.repInit;		
	}
	
	private void activerRepInit()
	{		
		switch(this.repInit)
		{
		case 0: // Demarrer
			setParamsEnRegle(true);
			break;
		case 1: // Personnaliser params
			afficherMenuParams();
			break;
		case 2: // Charger partie
			setParamsEnRegle(true);
			break;
		case 3: // Quitter
			contr.end();
			break;
		}
		
		if (this.repInit == 1 && this.repParams == 1)
		{
			setParamsEnRegle(true);
		}
	}
	
	private void afficherMenuParams()
	{
		menuParams = new MenuParams();
		
		while ( !menuParams.getRepSet() )
		{
			menuParams.afficher();
		}
		
		this.paramsPersos = menuParams.getParamsPersos();
		this.repParams = menuParams.getRep();
		menuInit.setParamsAssigned(false);
	}
	
	// Initier l'ecran, sans valeurs
	protected void initEcranSimul(Vector<Environnement> vecEnvirs, Vector<Vivant> vecVivants)
	{
		// Instancier la fenetre
		frameSimul = FrameSimul.getInstance();
		// lier les elements du Modele
		frameSimul.initVecsCanevas(vecEnvirs, vecVivants);		
		frameSimul.initNbParEspece(Modele.getParamsPersos());
		canevas = frameSimul.getCanevas();
		//canevas.setHT(htElems);
		canevas.setRunning(true);
		frameSimul.afficher();
		// afficher les elem
		
		//canevas.repaint();
	}
	
	protected void afficherSimul(Vector<Environnement> vecEnvirs, Vector<Vivant> vecVivants, int[] dateHumain)
	{
		//canevas.setHT(htElems);
		frameSimul.refreshTxtFields(dateHumain);
		canevas.setVecEnvirs(vecEnvirs);
		canevas.setVecVivants(vecVivants);
		canevas.repaint();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	// Getters
	protected void setControleur(Controleur c) 	{ contr = c; }	
	protected void setParamsEnRegle(boolean enRegle) 	{this.paramsEnRegle = enRegle; }
	
	// Setters
	protected boolean getParamsEnRegle() { return this.paramsEnRegle; }

	
	protected FrameSimul getFrameSimul() {
		return frameSimul;
	}

	protected void setFrameSimul(FrameSimul frameSimul) {
		this.frameSimul = frameSimul;
	}

}
