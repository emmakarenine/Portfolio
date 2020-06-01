package simulsynthese;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;

public class FrameSimul extends JFrame implements VueUtil
{
	//private Hashtable<String, Vector> htFrameSimul = null;
	private static FrameSimul frameSimul = null;
	private final int CANVAS_LARGEUR = 1600;
	private final int CANVAS_HAUTEUR = 1000;
	
	private static boolean onPause = false;
	private FrameStats frameStats = null;
	
	private static int vitesse = 1; // vitesse normale. Choix: 0, 1, 2
	//***************************************************
	
	//private int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width; // donne ~1920
	//private int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height; // donne ~1080
	private int largeurEcran = 1900;
	private int hauteurEcran = 1000;
			
	private JPanel mainRightPanel;
	private static Canevas canevas;

	private JLabel lblTitre;
	private JPanel pnlNbParEspece;
	
	private JPanel pnlVitesse;
	private JSlider slidVitesse;
	private JLabel lblVitesse;
	private JLabel lblLent;
	private JLabel lblRapide;
	
	private JPanel pnlTemps;
	private JLabel lblTemps;
	private JLabel lblSem;
	private JLabel lblMois;
	private JLabel lblAnnees;
	private JTextField tfSem;
	private JTextField tfMois;
	private JTextField tfAnnees;
	private JTextField tfPrevuPr;
	
	private JPanel pnlHumain;	
	private JLabel lblFacteur;
	private JLabel lblPrevu;
	private JRadioButton radPetrol;
	private JRadioButton radTourisme;
	private JRadioButton radPlast;
	private JButton btnPrecipiter;
	
	private JPanel pnlBoutons;
	private JButton btnStats;
	private JButton btnPause;
	private JButton btnEnreg;
	private JButton btnQuitter;
	
	private Ecouteur ec;
	
	private boolean repSet = false;
	private boolean paramsAssigned = false;
	
	private JLabel lblIndiv;
	private JLabel lblPlancton;
	private JLabel lblRequin;
	private JLabel lblEtoile;
	private JLabel lblMangrove;
	private JLabel lblCorail;
	private JLabel lblTortue;
	private JLabel lblPoisson;
	private JTextField tfNbCoraux;
	private JTextField tfNbMangroves;
	private JTextField tfNbPlanctons;
	private JTextField tfNbPoissons;
	private JTextField tfNbEtoiles;
	private JTextField tfNbTortues;
	private JTextField tfNbRequins;
	//private JScrollPane myScrollPane;
	
	/**
	 * Create the frame.
	 */
		
	private FrameSimul() 
	{
		//******************************
		// Creer ecran principal
		this.setSize(largeurEcran, hauteurEcran);
		setTitle("Grande Barriee VS Humain : Ecran de simulation");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		//**********************************************
		// Creer panneau de gauche (canevas de dessin)
		canevas = new Canevas(CANVAS_LARGEUR, CANVAS_HAUTEUR);
		canevas.setBackground(Color.WHITE);
		canevas.setAutoscrolls(true);
		getContentPane().add(canevas, BorderLayout.WEST);
		canevas.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		canevas.setLayout(null);		
		
		//*****************************
		// Creer panneau de droite
		mainRightPanel = new JPanel();
		mainRightPanel.setBackground(Color.LIGHT_GRAY);
		mainRightPanel.setBorder(new LineBorder(Color.RED, 2));
		getContentPane().add(mainRightPanel, BorderLayout.CENTER);
		mainRightPanel.setLayout(null);
		
		lblTitre = new JLabel("Grande Barriere VS Humain");
		lblTitre.setBounds(10, 0, 237, 33);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		mainRightPanel.add(lblTitre);
		
		//*************************
		// Panel Temps ecoule
		pnlTemps = new JPanel();
		pnlTemps.setBounds(22, 180, 257, 136);
		mainRightPanel.add(pnlTemps);
		pnlTemps.setLayout(null);
		
		lblTemps = new JLabel("Temps \u00E9coul\u00E9");
		lblTemps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTemps.setBounds(10, 0, 149, 24);
		pnlTemps.add(lblTemps);
		
		lblSem = new JLabel("semaines");
		lblSem.setHorizontalAlignment(SwingConstants.LEFT);
		lblSem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSem.setBounds(106, 38, 83, 14);
		pnlTemps.add(lblSem);
		
		lblMois = new JLabel("mois");
		lblMois.setHorizontalAlignment(SwingConstants.LEFT);
		lblMois.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMois.setBounds(106, 69, 83, 14);
		pnlTemps.add(lblMois);
		
		lblAnnees = new JLabel("ans");
		lblAnnees.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnnees.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAnnees.setBounds(106, 100, 83, 14);
		pnlTemps.add(lblAnnees);
		
		tfSem = new JTextField("0");
		tfSem.setEditable(false);
		tfSem.setDisabledTextColor(Color.BLACK);
		tfSem.setHorizontalAlignment(SwingConstants.RIGHT);
		tfSem.setColumns(10);
		tfSem.setBounds(10, 35, 86, 20);
		pnlTemps.add(tfSem);		
		
		tfMois = new JTextField("0");
		tfMois.setDisabledTextColor(Color.BLACK);
		tfMois.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMois.setEditable(false);
		tfMois.setColumns(10);
		tfMois.setBounds(10, 66, 86, 20);
		pnlTemps.add(tfMois);
		
		tfAnnees = new JTextField("0");
		tfAnnees.setDisabledTextColor(Color.BLACK);
		tfAnnees.setHorizontalAlignment(SwingConstants.RIGHT);
		tfAnnees.setEditable(false);
		tfAnnees.setColumns(10);
		tfAnnees.setBounds(10, 97, 86, 20);
		pnlTemps.add(tfAnnees);
		
		//********************
		// Panel vitesse
		pnlVitesse = new JPanel();
		pnlVitesse.setBounds(22, 85, 257, 71);
		mainRightPanel.add(pnlVitesse);
		pnlVitesse.setLayout(null);
		
		lblVitesse = new JLabel("Vitesse de d\u00E9roulement");
		lblVitesse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVitesse.setBounds(10, 0, 149, 24);
		pnlVitesse.add(lblVitesse);
		
		lblLent = new JLabel("Lent");
		lblLent.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblLent.setBounds(10, 35, 27, 14);
		pnlVitesse.add(lblLent);
		
		lblRapide = new JLabel("Rapide");
		lblRapide.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRapide.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblRapide.setBounds(187, 35, 46, 14);
		pnlVitesse.add(lblRapide);
		
		slidVitesse = new JSlider();
		slidVitesse.setValue(1);
		slidVitesse.setPaintTicks(true);
		slidVitesse.setMaximum(2);
		slidVitesse.setBounds(20, 46, 213, 26);
		pnlVitesse.add(slidVitesse);		
		
		//***************************
		// Panel Facteur humain
		pnlHumain = new JPanel();
		pnlHumain.setBounds(22, 340, 257, 173);
		mainRightPanel.add(pnlHumain);
		pnlHumain.setLayout(null);
		
		lblFacteur = new JLabel("Facteur humain");
		lblFacteur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFacteur.setBounds(10, 0, 149, 24);
		pnlHumain.add(lblFacteur);
		
		lblPrevu = new JLabel("Prevu apres");
		lblPrevu.setBounds(31, 113, 75, 14);
		pnlHumain.add(lblPrevu);
		
		tfPrevuPr = new JTextField();
		tfPrevuPr.setDisabledTextColor(new Color(0, 0, 0));
		tfPrevuPr.setEditable(false);
		tfPrevuPr.setColumns(10);
		tfPrevuPr.setBounds(106, 110, 127, 20);
		pnlHumain.add(tfPrevuPr);
		
		btnPrecipiter = new JButton("Pr\u00E9cipiter le d\u00E9clenchement");
		btnPrecipiter.setHorizontalAlignment(SwingConstants.RIGHT);
		btnPrecipiter.setBounds(41, 141, 206, 23);
		pnlHumain.add(btnPrecipiter);
		
		//*********************
		// Panel boutons
		pnlBoutons = new JPanel();
		pnlBoutons.setBounds(22, 848, 257, 102);
		mainRightPanel.add(pnlBoutons);
		pnlBoutons.setLayout(null);
		
		btnStats = new JButton("Statistiques");
		btnStats.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnStats.setBounds(10, 11, 118, 37);
		pnlBoutons.add(btnStats);
		
		btnPause = new JButton("Pause");
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnPause.setBounds(10, 52, 118, 37);
		pnlBoutons.add(btnPause);
		
		btnEnreg = new JButton("Enregistrer");
		btnEnreg.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEnreg.setBounds(129, 11, 118, 37);
		pnlBoutons.add(btnEnreg);
		
		btnQuitter = new JButton("Quitter");
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnQuitter.setBounds(129, 52, 118, 37);
		pnlBoutons.add(btnQuitter);
		
		pnlNbParEspece = new JPanel();
		pnlNbParEspece.setBounds(27, 540, 252, 281);
		mainRightPanel.add(pnlNbParEspece);
		pnlNbParEspece.setLayout(null);
		
		lblIndiv = new JLabel("Individus par esp\u00E8ce");
		lblIndiv.setBounds(10, 5, 221, 17);
		lblIndiv.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlNbParEspece.add(lblIndiv);
		
		lblCorail = new JLabel("Corail");
		lblCorail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCorail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCorail.setBounds(10, 53, 89, 26);
		pnlNbParEspece.add(lblCorail);
		
		lblMangrove = new JLabel("Mangrove");
		lblMangrove.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMangrove.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMangrove.setBounds(10, 76, 89, 26);
		pnlNbParEspece.add(lblMangrove);
		
		lblPoisson = new JLabel("Poisson");
		lblPoisson.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPoisson.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPoisson.setBounds(10, 151, 89, 26);
		pnlNbParEspece.add(lblPoisson);
		
		lblTortue = new JLabel("Tortue");
		lblTortue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTortue.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTortue.setBounds(10, 177, 89, 26);
		pnlNbParEspece.add(lblTortue);
		
		lblPlancton = new JLabel("Phytoplancton");
		lblPlancton.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlancton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlancton.setBounds(10, 102, 89, 26);
		pnlNbParEspece.add(lblPlancton);
		
		lblRequin = new JLabel("Requin");
		lblRequin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRequin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRequin.setBounds(10, 205, 89, 26);
		pnlNbParEspece.add(lblRequin);
		
		lblEtoile = new JLabel("Etoile de mer");
		lblEtoile.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEtoile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEtoile.setBounds(10, 127, 89, 26);
		pnlNbParEspece.add(lblEtoile);
		
		tfNbCoraux = new JTextField("0");
		tfNbCoraux.setEditable(false);
		tfNbCoraux.setDisabledTextColor(new Color(199, 21, 133));
		tfNbCoraux.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbCoraux.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbCoraux.setBounds(109, 57, 86, 20);
		pnlNbParEspece.add(tfNbCoraux);
		tfNbCoraux.setColumns(10);
		
		tfNbMangroves = new JTextField("0");
		tfNbMangroves.setEditable(false);
		tfNbMangroves.setDisabledTextColor(new Color(50, 205, 50));
		tfNbMangroves.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbMangroves.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbMangroves.setColumns(10);
		tfNbMangroves.setBounds(109, 80, 86, 20);
		pnlNbParEspece.add(tfNbMangroves);
		
		tfNbPlanctons = new JTextField("0");
		tfNbPlanctons.setEditable(false);
		tfNbPlanctons.setDisabledTextColor(new Color(30, 144, 255));
		tfNbPlanctons.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbPlanctons.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbPlanctons.setColumns(10);
		tfNbPlanctons.setBounds(109, 106, 86, 20);
		pnlNbParEspece.add(tfNbPlanctons);
		
		tfNbPoissons = new JTextField("0");
		tfNbPoissons.setEditable(false);
		tfNbPoissons.setDisabledTextColor(new Color(0, 0, 255));
		tfNbPoissons.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbPoissons.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbPoissons.setColumns(10);
		tfNbPoissons.setBounds(109, 154, 86, 20);
		pnlNbParEspece.add(tfNbPoissons);
		
		tfNbEtoiles = new JTextField("0");
		tfNbEtoiles.setEditable(false);
		tfNbEtoiles.setDisabledTextColor(new Color(220, 20, 60));
		tfNbEtoiles.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbEtoiles.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbEtoiles.setColumns(10);
		tfNbEtoiles.setBounds(109, 131, 86, 20);
		pnlNbParEspece.add(tfNbEtoiles);
		
		tfNbTortues = new JTextField("0");
		tfNbTortues.setEditable(false);
		tfNbTortues.setDisabledTextColor(new Color(34, 139, 34));
		tfNbTortues.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbTortues.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbTortues.setColumns(10);
		tfNbTortues.setBounds(109, 181, 86, 20);
		pnlNbParEspece.add(tfNbTortues);
		
		tfNbRequins = new JTextField("0");
		tfNbRequins.setEditable(false);
		tfNbRequins.setDisabledTextColor(new Color(105, 105, 105));
		tfNbRequins.setFocusable(false);
		tfNbRequins.setHorizontalAlignment(SwingConstants.LEFT);
		tfNbRequins.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNbRequins.setColumns(10);
		tfNbRequins.setBounds(109, 209, 86, 20);
		pnlNbParEspece.add(tfNbRequins);		
		
		//*****************************
		// Traitement radio buttons
		radPetrol = new JRadioButton("D\u00E9versement p\u00E9trolier");
		radPetrol.setSelected(true);
		radPetrol.setBounds(10, 31, 213, 23);
		pnlHumain.add(radPetrol);
		
		radTourisme = new JRadioButton("Industrie touristique");
		radTourisme.setBounds(10, 57, 223, 23);
		pnlHumain.add(radTourisme);
		
		radPlast = new JRadioButton("Accumulation plastique");
		radPlast.setBounds(10, 83, 223, 23);
		pnlHumain.add(radPlast);
		
		//***************************************
		// Exclusion de boutons && Ecouteur
		forcerExclusionEntreBoutons(pnlHumain);
		ec = new Ecouteur();
		slidVitesse.addChangeListener(ec);
		attacherEcouteurBoutons(ec, pnlBoutons);		
	}
	
	public static FrameSimul getInstance()
	{
		if (frameSimul == null)
		{
			frameSimul = new FrameSimul();
		}		
		
		return frameSimul;
	}
	
	
	protected void afficherStats()
	{
		frameStats = new FrameStats();
		frameStats.setVisible(true);
	}
	
	private class Ecouteur implements ActionListener, ChangeListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComponent srce = (JComponent) e.getSource();
			
			
			if (srce == btnStats)
			{
				afficherStats();
			}
			else if (srce == btnPause)
			{
				onPause = !onPause;
			}
			else if (srce == btnQuitter)
			{
				int rep = JOptionPane.showConfirmDialog(FrameSimul.this, 
						"Vous avez choisi de quitter. Le programme va s'arreter.", "Quitter", JOptionPane.WARNING_MESSAGE, 
						JOptionPane.YES_NO_OPTION);
				
				if (rep == JOptionPane.YES_OPTION )
				{
					System.exit(0);
				}
			}
		}

		@Override
		public void stateChanged(ChangeEvent e)
		{
			
			if (e.getSource() == slidVitesse)
			{
				FrameSimul.setVitesse(slidVitesse.getValue());
			}
		}		
	}
	
	// Affichage des espece 
	public void initNbParEspece(Hashtable<String, Integer> nbParEspece)
	{
		tfNbCoraux.setText(String.valueOf(nbParEspece.get("corail")) );
		tfNbMangroves.setText(String.valueOf(nbParEspece.get("mangrove")) );
		tfNbPlanctons.setText(String.valueOf(nbParEspece.get("phytoplancton")) );
		tfNbEtoiles.setText(String.valueOf(nbParEspece.get("etoile")) );
		tfNbPoissons.setText(String.valueOf(nbParEspece.get("poisson")) );
		tfNbTortues.setText(String.valueOf(nbParEspece.get("tortue")) );
		tfNbRequins.setText(String.valueOf(nbParEspece.get("requin")) );
	}

	protected void refreshTxtFields(int[] dateHumain)
	{
		String periode = "";
		tfNbCoraux.setText(String.valueOf(Corail.getCount()) );
		tfNbMangroves.setText(String.valueOf(Mangrove.getCount()) );
		tfNbPlanctons.setText(String.valueOf(Phytoplancton.getCount()) );
		tfNbEtoiles.setText(String.valueOf(Etoile.getCount()) );
		tfNbPoissons.setText(String.valueOf(Poisson.getCount()) );
		tfNbTortues.setText(String.valueOf(Tortue.getCount()) );
		tfNbRequins.setText(String.valueOf(Requin.getCount()) );		
		tfSem.setText(String.valueOf(Controleur.getTimeCount()) );
		tfMois.setText(String.valueOf(Controleur.getMoisCount()) );
		tfAnnees.setText(String.valueOf(Controleur.getAnneesCount()) );
		
		switch(dateHumain[2])
		{
		case 0: periode = " semaines"; break;
		case 1: periode = " mois"; break;
		case 2: periode = " ans"; break;
		}
		
		tfPrevuPr.setText("  "+String.valueOf(dateHumain[1])+periode );
	}

	@Override
	public void enregChoix(int rep)
	{
		// TODO Auto-generated method stub		
	}
	
	public void afficherCanevas()
	{
		afficher();		
		canevas.repaint();		
	}	

	protected void initVecsCanevas(Vector<Environnement> vecEnvirs, Vector<Vivant> vecViv)
	{
		canevas.setVecEnvirs(vecEnvirs);
		canevas.setVecVivants(vecViv);
	}

	// Code de B43 (TP1)
	protected void forcerExclusionEntreBoutons(JPanel pnlBoutons)
	{
	    ButtonGroup grBtns = new ButtonGroup();
		Component[] tabComps = pnlBoutons.getComponents();
		
		for (Component c: tabComps)
		{			
			if (c instanceof JRadioButton)
			{
				grBtns.add((JRadioButton) c);
			}
		}	
	}
	
	protected void attacherEcouteurBoutons(Ecouteur ec, JPanel panel)
	{
		Component[] tabComps = panel.getComponents();
		
 		for (Component comp: tabComps)
 		{
 			if (comp instanceof JButton)
			{
 				((JButton) comp).addActionListener(ec);
			}
 		}
	}
	
	protected int getLargeurCanvas()
	{
		return this.CANVAS_LARGEUR;
	}

	protected int getHauteurCanvas()
	{
		return this.CANVAS_HAUTEUR;
	}

	@Override
	public boolean getParamsAssigned() {
		return this.paramsAssigned;
	}

	@Override
	public void setParamsAssigned(boolean b) {
		this.paramsAssigned = b;		
	}

	@Override
	public boolean getRepSet() {
		return this.repSet;
	}

	@Override
	public void setRepSet(boolean b) {
		this.repSet = true;		
	}

	@Override
	public void afficher()
	{
		this.setVisible(true);
		
	}
	
	@Override
	public void fermerFenetre()
	{
		dispose();		
	}


	@Override
	public void setRep(int rep)
	{
		;
	}

	@Override
	public int getRep() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getVitesse() {
		return FrameSimul.vitesse;
	}

	public static void setVitesse(int vitesse) {
		FrameSimul.vitesse = vitesse;
	}
	
	protected Canevas getCanevas() {
		return canevas;
	}

	protected static void setCanevas(Canevas canevas) {
		FrameSimul.canevas = canevas;
	}

	public static boolean getOnPause() {
		return FrameSimul.onPause;		
	}
	
	public static void setOnPause(boolean b) {
		FrameSimul.onPause = b;		
	}

}
