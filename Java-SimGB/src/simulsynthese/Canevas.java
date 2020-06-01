package simulsynthese;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.JPanel;

//http://zetcode.com/tutorials/javagamestutorial/animation/
//https://www.javatpoint.com/java-thread-start-method
public class Canevas extends JPanel
{	
	//************************************
	private final float[] MOTIF_POINTILLE = { 10f, 0f, 0f };
	private final Stroke STROKE_RADAR = new BasicStroke(0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 50f, MOTIF_POINTILLE, 0f);
	private final int NB_SECTIONS_BARRE_VIE = 10;
	private final int LARGEUR_BARRE_VIE = 30; // divisee en 10 sections
	private final int HAUTEUR_BARRE_VIE = 5;
	
	private int largeurDivBarreVie = LARGEUR_BARRE_VIE/NB_SECTIONS_BARRE_VIE;
	
	private boolean isRunning = false;
	private float epaisseurPetrole = 0.5f;
	private int vitCoule = 0;
    private Vector<Environnement> vecEnvirs;
    private Vector<Vivant> vecVivants;
    
   
    public Canevas(int largeur, int hauteur)
    {  	
    	setPreferredSize(new Dimension(largeur, hauteur));
    }

    @Override
    public void paintComponent(Graphics g)
    {
    	//Toolkit.getDefaultToolkit().sync(); // pour compatibilite Linux(?)   	
    	if (this.isRunning)
    	{    		 		
    		// Choisir une couleur
    		Graphics2D g2 = (Graphics2D) g;
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		super.paintComponent(g2);
    		
    		// Colorier terrains
    		for (Environnement env : this.vecEnvirs)
    		{			
    			if (env instanceof EnvirTerrain)
    			{
    				// Peindre les terrains en premier
        			g2.setColor(env.getCouleurActu());
//        				//Toolkit.getDefaultToolkit().sync(); // pour compatibilite Linux(?)
//        			
//        			if ( !(env instanceof Ile) )
//        			{
        				g2.fillRect( (int)env.getAire().getBounds().x, 
            					(int)env.getAire().getBounds().y, 
        						(int)env.getAire().getBounds().width, 
        						(int)env.getAire().getBounds().height );
//        			}
//        			else
//        			{
//        				g2.fillOval( (int)env.getAire().getBounds().x, 
//            					(int)env.getAire().getBounds().y, 
//        						(int)env.getAire().getBounds().width, 
//        						(int)env.getAire().getBounds().height );
//        			}
    			}
    		}
    		
    		// Colorier les vivants
    		
    		// DESSINER ANIMAUX 		
    		for (Vivant viv : this.vecVivants)    			
    		{
    			int largeur = (int)viv.getAire().width;
    			int hauteur = (int)viv.getAire().height;    			

    			
    			if ( viv instanceof Animal )
    			{      
        			Radar radar = ((Animal)viv).getRadar();
        			int rayonRadar = radar.getRayon();
    				
    				int x = (int)viv.getPtCentre().x;
    				int y = (int)viv.getPtCentre().y;
    				
        			g2.setColor(viv.getCoulActu());
        			
        			// Dessiner l'animal
        			if ( !(viv instanceof Etoile) )
    		   		{
        				g2.fillOval( x-largeur/2, y-hauteur/2, largeur, hauteur); 				
        				   
    		   		}
        			else
        			{            			
        				g2.fillPolygon(new int[] {x, x+largeur/2, x-largeur/2},  new int[] {y-hauteur/3, y+hauteur/2, y+hauteur/2}, 3);
    		   			g2.fillPolygon(new int[] {x-largeur/2, x+largeur/2, x},  new int[] {y, y, y+hauteur-5}, 3);		   		
        			}
        			
        			//Dessiner sa barre de vie
					int percentEnergie = (int) Math.floor((double)viv.getNivEnergie()/1000*100);
					int percentVert = (int)Math.round(percentEnergie/10);
					int xVie = viv.getAire().x-LARGEUR_BARRE_VIE/3;
					int yVie = viv.getAire().y-HAUTEUR_BARRE_VIE*2;
							
					System.out.println(percentVert);
					
					g2.setColor(Color.BLACK);
    				g2.setStroke(new BasicStroke());
    				g2.drawRect(xVie, yVie, LARGEUR_BARRE_VIE, HAUTEUR_BARRE_VIE);
    				g2.fillRect(xVie, yVie, LARGEUR_BARRE_VIE, HAUTEUR_BARRE_VIE);   
        			
    				g2.setColor(Color.GREEN);
    				if (viv.getNivEnergie() >= viv.getNivEnergieMax()-100)
    				{
    					g2.fillRect(xVie, yVie, LARGEUR_BARRE_VIE, HAUTEUR_BARRE_VIE);  
    				}
    				else
    				{
    					g2.fillRect(xVie, yVie, largeurDivBarreVie*percentVert, HAUTEUR_BARRE_VIE);  
    				}
    				      			
        			
        			// Dessiner son radar
        			Color coulTrait;
        			if ( ((Animal) viv).isSleeping() )
        			{
        				coulTrait = new Color(23, 130, 252);
        			}
        			else if ( ((Animal) viv).isPregnant() )
        			{
        				coulTrait = Color.RED;
        			}
        			else
        			{
        				coulTrait = Color.BLACK;
        			}
        				
    				g2.setColor(coulTrait);
        			g2.setStroke(STROKE_RADAR);
        			g2.drawOval(x-rayonRadar, y-rayonRadar, rayonRadar*2, rayonRadar*2);
        			
        		}
    			else // Vegetaux
    			{    				
        			g2.setColor(viv.getCoulActu()); 
        			
    	    		if (viv instanceof Corail)
    		   		{
    		   			g2.fillRect(viv.getAire().x, viv.getAire().y, largeur, hauteur);
    		   		}
    		   		else// if (viv instanceof Mangrove || viv instanceof Phytoplancton)
    		   		{
    		   			g2.fillOval(viv.getAire().x, viv.getAire().y, largeur, hauteur);
    		   		}
    		   		
        		}
    		}		
    		
    		
    		// BRUIT HUMAIN AU DESSUS		
    		for (Environnement env : this.vecEnvirs)
    		{			
    			if (env instanceof EnvirHumain)
    			{
    				g2.setColor(((DeversmPetrolier)env).getBateau().getCouleur());    				
    				
    				// PETROLIER EN BON ETAT --- PAS ENCORE DE DEVERSEMENT
    				if ( !((DeversmPetrolier)env).getBateau().isWreck() )
    				{
    					g2.fillOval( (int)((DeversmPetrolier)env).getBateau().x, 
            					(int)((DeversmPetrolier)env).getBateau().y,
            					Bateau.getLargeur(), Bateau.getHauteur() );
    				}    					
    				// PETROLIER EN MAUVAIS ETAT --- DEVERSEMENT
    				else
					{    					
    					// DESSINER LE BATEAU TANT QU'IL N'A PAS COULE
    					// Debut : bateau repand du petrole
        				if (this.epaisseurPetrole < 500)
        				{
        					g2.fillOval( (int)((DeversmPetrolier)env).getBateau().x, 
                					(int)((DeversmPetrolier)env).getBateau().y,
                					Bateau.getLargeur(), Bateau.getHauteur() );
        				}
        				// Fin : bateau coule !!
        				else if ((int)Bateau.getLargeur()-vitCoule > 0 )// 
        				{
        					g2.fillOval( (int)((DeversmPetrolier)env).getBateau().x+this.vitCoule, 
                					(int)((DeversmPetrolier)env).getBateau().y+this.vitCoule,
                					(int)Bateau.getLargeur()-vitCoule, (int)Bateau.getHauteur()-3*vitCoule );
        					
        					this.vitCoule += 2;
        				}
        				
        				// Dessiner le petrole qui se repand       				
        				// Transparence:
        				//https://www.informit.com/articles/article.aspx?p=26349&seqNum=5
        				//AlphaComposite transparence = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        	            g2.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f) );
    					
            			Stroke traitPetrole = new BasicStroke(this.epaisseurPetrole);
            			g2.setColor(env.getCouleurActu());	
            			g2.setStroke(traitPetrole);
            			g2.drawOval( (int)((DeversmPetrolier)env).getBateau().x, 
            					(int)((DeversmPetrolier)env).getBateau().y,
            					(int)env.getAire().getBounds().width,
            					(int)env.getAire().getBounds().height );
            			
            			this.epaisseurPetrole += (float)((DeversmPetrolier)env).getVitDispersion();        				
					}
    			}
    		}
    	}
    }

    protected void setVecEnvirs(Vector<Environnement> vec)
    {
    	this.vecEnvirs = vec;
    }

	protected void setVecVivants(Vector<Vivant> vecVivants)
	{
		this.vecVivants = vecVivants;
	}
	    
    protected boolean isRunning()
    {
		return isRunning;
	}

	protected void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}
   
}
