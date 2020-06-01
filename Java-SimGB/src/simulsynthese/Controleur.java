package simulsynthese;

import java.util.Hashtable;
import java.util.Vector;

public class Controleur implements Runnable
{
	private static int timeCount = 0;
	private static int moisCount = 0;
	private static int anneesCount = 0;
	
	private Modele modele;
	private Vue vue;
	private static boolean paramsAllSet = false;
	private Thread monThread;
	private int sleepTime = 500; //1 seconde entre ticks
	
	public Controleur (Modele modele, Vue vue)
	{		
		this.modele = modele;
		this.vue = vue;
	}
	
	protected void initProg()
	{
		afficherMenu();
	}
	
	@Override
	public void run()
	{
		while (true) // mauvaise condition?
        {
			if (! FrameSimul.getOnPause() )
			{
			
				// Mettre la bonne vitesse
				switch( FrameSimul.getVitesse() )
				{
				case 0: this.sleepTime = 500;
					break;
				case 1: this.sleepTime = 100;
					break;
				case 2: this.sleepTime = 50;
					break;			
				}	
				
				// Decompte des semaines
				Controleur.timeCount++;
				// Decompte des mois
				if  (Controleur.timeCount > 0 && Controleur.timeCount % 4 == 0)
				{
					Controleur.moisCount++;
				}
				// Decompte des annees
				if (Controleur.moisCount > 0 && Controleur.moisCount % 12 == 0)
				{
					Controleur.anneesCount++;
					Controleur.moisCount = 0;
					Controleur.timeCount = 0;
				}
				
				animer();
				this.vue.afficherSimul(this.modele.getVecEnvirs(), this.modele.getVecVivants(), this.modele.getHumData());
        	}
			
			// Independant a la PAUSE
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	private void animer()
	{	
		this.modele.animerElem();
	}
		
	private void afficherMenu()
	{	
		this.vue.initProg();
	}

	protected void initSimul(Hashtable<String, Integer> paramsPersos)
	{
		this.modele.setParams(paramsPersos);
		this.modele.creerElemSimul();
		this.vue.initEcranSimul(this.modele.getVecEnvirs(),this.modele.getVecVivants());

		monThread = new Thread(this);
		monThread.start(); // appelle la fonction run()
	}
		
	protected Thread getMonThread() {
		return monThread;
	}

	protected void end()
	{
		System.exit(0);
	}

	protected int getSleepTime() {
		return sleepTime;
	}

	protected void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	protected boolean isParamsAllSet() {
		return Controleur.paramsAllSet;
	}

	protected void setParamsAllSet(boolean paramsAllSet) {
		Controleur.paramsAllSet = paramsAllSet;
	}
	
	protected static int getTimeCount() {
		return Controleur.timeCount;
	}

	protected static void setTimeCount(int timeCount) {
		Controleur.timeCount = timeCount;
	}

	public static int getMoisCount() {
		return moisCount;
	}

	public static void setMoisCount(int moisCount) {
		Controleur.moisCount = moisCount;
	}

	public static int getAnneesCount() {
		return anneesCount;
	}

	public static void setAnneesCount(int anneesCount) {
		Controleur.anneesCount = anneesCount;
	}

}
