package simulsynthese;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuParams extends JFrame implements VueUtil
{
	private Hashtable<String, Integer> paramsPersos = null;
	
	
	// Variables d'interface graphique
	private JPanel pnlNbPopul;
	private JTextField tfNbRequins;
	private JTextField tfNbTortues;
	private JTextField tfNbPoissons;
	private JTextField tfNbEtoiles;
	private JTextField tfNbCoraux;
	private JTextField tfNbPlanctons;
	private JTextField tfNbMangroves;	
	
	private JPanel pnlTrait;
	private JComboBox cboxTraitRequin;
	private JComboBox cboxTraitTortue;
	private JComboBox cboxTraitPoisson;
	private JComboBox cboxTraitEtoile;	
	
	private JPanel pnlFacteur;
	private JComboBox cboxFactHum;
	private JTextField tfNbPeriodes;
	private JComboBox cboxPeriode;
	
	private JButton btnRetour;
	private JButton btnDemarrer;	
	private Ecouteur ec;	
	
	// Setup des combobox
	Vector<String> choixTrait = new Vector<String>();
	Vector<String> choixFreq = new Vector<String>();
	Vector<String> choixFacteur = new Vector<String>();
	Vector<String> choixDuree = new Vector<String>();
	DefaultComboBoxModel dfTrait = new DefaultComboBoxModel(choixTrait);
	DefaultComboBoxModel dfFreq = new DefaultComboBoxModel(choixFreq);
	DefaultComboBoxModel dfFacteur = new DefaultComboBoxModel(choixFacteur);
	DefaultComboBoxModel dfTemps = new DefaultComboBoxModel(choixDuree);
	
	
	// Variables du logiciel
	private int rep;
	//private int repParams;
	private boolean repSet = false;
	private boolean paramsSet = false;
	
	private void creerChoixComboBox()	
	{
		String[] traits = {"Vitesse","Grande taille","Couleur flamboyante"};
		String[] frequences = {"Rare","Suffisant","Luxuriant"};
		String[] facteurs = {"Déversement pétrolier", "Tourisme", "Accumulation plastique"};
		String[] durees = {"semaines","mois","années"};
		
		for (String trait: traits)
		{
			dfTrait.addElement(trait);
		}
		
		for (String freq: frequences)
		{
			dfFreq.addElement(freq);
		}
		
		for (String facteur: facteurs)
		{
			dfFacteur.addElement(facteur);
		}
		
		for (String duree: durees)
		{
			dfTemps.addElement(duree);
		}
	}
	

	/**
	 * Create the frame.
	 */
	public MenuParams()
	{				
		//this.parent = vue;
		
		setTitle("Grande Barrière VS Humain : Personnalisation des paramètres");
		setBounds(100, 100, 1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		//*****************
		// Text labels		
		JLabel lblTitre = new JLabel("Grande Barri\u00E8re VS l'Humain");
		lblTitre.setVerticalAlignment(SwingConstants.CENTER);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Dialog", Font.PLAIN, 32));
		lblTitre.setBounds(200, 11, 800, 75);
		getContentPane().add(lblTitre);
		
		JLabel lblPopuls = new JLabel("Populations");
		lblPopuls.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPopuls.setBounds(28, 118, 129, 42);
		getContentPane().add(lblPopuls);
		
		JLabel lblSuggest = new JLabel("(Quantit\u00E9 sugg\u00E9r\u00E9e: 5)");
		lblSuggest.setBounds(155, 138, 148, 14);
		getContentPane().add(lblSuggest);
		
		JLabel lblRequins = new JLabel("1. Requins blancs");
		lblRequins.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRequins.setBounds(28, 171, 173, 30);
		getContentPane().add(lblRequins);
		
		JLabel lblTortues = new JLabel("2. Tortues vertes");
		lblTortues.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTortues.setBounds(28, 216, 173, 30);
		getContentPane().add(lblTortues);
		
		JLabel lblPoissons = new JLabel("3. Poissons-demoiselles");
		lblPoissons.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPoissons.setBounds(28, 263, 173, 30);
		getContentPane().add(lblPoissons);
		
		JLabel lblEtoiles = new JLabel("4. \u00C9toiles de mer pourpres");
		lblEtoiles.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEtoiles.setBounds(28, 312, 216, 30);
		getContentPane().add(lblEtoiles);
		
		JLabel lblPlancton = new JLabel("5. Phytoplancton");
		lblPlancton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlancton.setBounds(28, 353, 216, 30);
		getContentPane().add(lblPlancton);
		
		JLabel lblCorail = new JLabel("6. R\u00E9cifs de corail");
		lblCorail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCorail.setBounds(28, 394, 216, 30);
		getContentPane().add(lblCorail);
		
		JLabel lblMangroves = new JLabel("7. Mangroves");
		lblMangroves.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMangroves.setBounds(28, 440, 216, 30);
		getContentPane().add(lblMangroves);		
		
		JLabel lblTrait = new JLabel("Trait \u00E9volutif initial :");
		lblTrait.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTrait.setBounds(350, 137, 159, 42);
		getContentPane().add(lblTrait);
		
		JLabel lblFacteur = new JLabel("Facteur humain");
		lblFacteur.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblFacteur.setBounds(794, 118, 173, 42);
		getContentPane().add(lblFacteur);
		
		
		
		//****************************************
		// Panel nb d'individus par population		
		pnlNbPopul = new JPanel();
		pnlNbPopul.setBounds(264, 171, 58, 313);
		getContentPane().add(pnlNbPopul);
		pnlNbPopul.setLayout(null);
		
		tfNbRequins = new JTextField("5");
		tfNbRequins.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbRequins.setColumns(10);
		tfNbRequins.setBounds(0, 11, 54, 20);
		pnlNbPopul.add(tfNbRequins);
		
		tfNbTortues = new JTextField("5");
		tfNbTortues.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbTortues.setColumns(10);
		tfNbTortues.setBounds(0, 55, 54, 20);
		pnlNbPopul.add(tfNbTortues);
		
		tfNbPoissons = new JTextField("5");
		tfNbPoissons.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbPoissons.setColumns(10);
		tfNbPoissons.setBounds(0, 100, 54, 20);
		pnlNbPopul.add(tfNbPoissons);
		
		tfNbPlanctons = new JTextField("5");
		tfNbPlanctons.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbPlanctons.setColumns(10);
		tfNbPlanctons.setBounds(0, 191, 54, 20);
		pnlNbPopul.add(tfNbPlanctons);
		
		tfNbEtoiles = new JTextField("5");
		tfNbEtoiles.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbEtoiles.setColumns(10);
		tfNbEtoiles.setBounds(0, 148, 54, 20);
		pnlNbPopul.add(tfNbEtoiles);
		
		tfNbMangroves = new JTextField("5");
		tfNbMangroves.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbMangroves.setColumns(10);
		tfNbMangroves.setBounds(0, 273, 54, 20);
		pnlNbPopul.add(tfNbMangroves);
		
		tfNbCoraux = new JTextField("5");
		tfNbCoraux.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbCoraux.setColumns(10);
		tfNbCoraux.setBounds(0, 230, 54, 20);
		pnlNbPopul.add(tfNbCoraux);
		
		//********************************
		// Panel trait par population
		pnlTrait = new JPanel();
		pnlTrait.setBounds(341, 171, 173, 179);
		getContentPane().add(pnlTrait);
		pnlTrait.setLayout(null);		

		dfTrait = new DefaultComboBoxModel(choixTrait);		
		cboxTraitRequin = new JComboBox(dfTrait);
		cboxTraitRequin.setBounds(0, 11, 168, 20);
		pnlTrait.add(cboxTraitRequin);
		dfTrait.setSelectedItem("Vitesse");
		
		dfTrait = new DefaultComboBoxModel(choixTrait);
		cboxTraitTortue = new JComboBox(dfTrait);
		cboxTraitTortue.setBounds(0, 55, 168, 20);
		pnlTrait.add(cboxTraitTortue);
		dfTrait.setSelectedItem("Vitesse");
		
		dfTrait = new DefaultComboBoxModel(choixTrait);
		cboxTraitPoisson = new JComboBox(dfTrait);
		cboxTraitPoisson.setBounds(0, 106, 168, 20);
		pnlTrait.add(cboxTraitPoisson);
		dfTrait.setSelectedItem("Vitesse");
		
		dfTrait = new DefaultComboBoxModel(choixTrait);
		cboxTraitEtoile = new JComboBox(dfTrait);
		cboxTraitEtoile.setBounds(0, 148, 168, 20);
		pnlTrait.add(cboxTraitEtoile);
		dfTrait.setSelectedItem("Vitesse");		
		
		creerChoixComboBox();
		
		dfFreq = new DefaultComboBoxModel(choixFreq);
		dfFreq.setSelectedItem("Suffisant");		
		
		dfFreq = new DefaultComboBoxModel(choixFreq);
		dfFreq.setSelectedItem("Suffisant");
		
		dfFreq = new DefaultComboBoxModel(choixFreq);
		dfFreq.setSelectedItem("Suffisant");
		
		cboxFactHum = new JComboBox(dfFacteur);
		cboxFactHum.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cboxFactHum.setBounds(794, 171, 262, 42);
		getContentPane().add(cboxFactHum);
		dfFacteur.setSelectedItem("Déversement pétrolier");
		
		//********************************************
		// Panel facteur humain et son apparition		
		pnlFacteur = new JPanel();
		pnlFacteur.setBounds(794, 171, 262, 122);
		getContentPane().add(pnlFacteur);
		pnlFacteur.setLayout(null);
		
		JLabel lblTemps = new JLabel("Survient \u00E0 :");
		lblTemps.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTemps.setBounds(0, 70, 92, 42);
		pnlFacteur.add(lblTemps);
		
		cboxFactHum = new JComboBox(dfFacteur);
		cboxFactHum.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cboxFactHum.setBounds(0, 0, 262, 42);
		pnlFacteur.add(cboxFactHum);
		
		tfNbPeriodes = new JTextField("6");
		tfNbPeriodes.setHorizontalAlignment(SwingConstants.CENTER);
		tfNbPeriodes.setColumns(10);
		tfNbPeriodes.setBounds(102, 84, 45, 20);
		pnlFacteur.add(tfNbPeriodes);
		
		cboxPeriode = new JComboBox(dfTemps);
		cboxPeriode.setBounds(152, 84, 110, 20);
		pnlFacteur.add(cboxPeriode);
		
		//************************
		// Boutons des choix		
		btnRetour = new JButton("<html>Retour au menu initial<br/>sans sauvegarde</html>");
		btnRetour.setBounds(987, 11, 187, 60);
		getContentPane().add(btnRetour);
		
		btnDemarrer = new JButton("D\u00E9marrer la simulation");
		btnDemarrer.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnDemarrer.setBounds(417, 628, 392, 66);
		getContentPane().add(btnDemarrer);
		
		ec = new Ecouteur();
		btnRetour.addActionListener(ec);
		btnDemarrer.addActionListener(ec);		

	}
	
	private class Ecouteur implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{				
			if (e.getSource() == btnRetour)
			{
				enregChoix(0);
			}
			else if (e.getSource() == btnDemarrer)
			{
				enregChoix(1);
			}	
		}
	}
	
	private void enregParams()
	{	
		paramsPersos = new Hashtable<String, Integer>();
		
		// Enregistrer le nb par espece
		paramsPersos.put("requin", Integer.valueOf(tfNbRequins.getText()) );
		paramsPersos.put("tortue", Integer.valueOf(tfNbTortues.getText()) );
		paramsPersos.put("poisson", Integer.valueOf(tfNbPoissons.getText()) );
		paramsPersos.put("etoile", Integer.valueOf(tfNbEtoiles.getText()) );
		paramsPersos.put("phytoplancton", Integer.valueOf(tfNbPlanctons.getText()) );
		paramsPersos.put("corail", Integer.valueOf(tfNbCoraux.getText()) );
		paramsPersos.put("mangrove", Integer.valueOf(tfNbMangroves.getText()) );
		
		// Enregistrer le trait evolutif prefere
		paramsPersos.put("traitRequin", cboxTraitRequin.getSelectedIndex());
		paramsPersos.put("traitTortue", cboxTraitTortue.getSelectedIndex());
		paramsPersos.put("traitPoisson", cboxTraitTortue.getSelectedIndex());
		paramsPersos.put("traitEtoile", cboxTraitTortue.getSelectedIndex());
		
		// Enregistrer le facteur humain et son moment d'apparition
		paramsPersos.put("factHum", cboxFactHum.getSelectedIndex());
		paramsPersos.put("nbPeriodes", Integer.valueOf(tfNbPeriodes.getText()) );
		paramsPersos.put("periode", cboxPeriode.getSelectedIndex());
	}
	
	@Override
	public void enregChoix(int rep)
	{
		setRep(rep);
		
		if (rep == 1)
		{
			enregParams();
			setParamsAssigned(true);		
		}
		
		setRepSet(true);
		fermerFenetre();
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
	public boolean getRepSet() {
		return this.repSet;
	}

	@Override
	public void setRepSet(boolean b) {
		this.repSet = b;		
	}
	
	@Override
	public void setRep(int rep)
	{
		this.rep = rep;
	}

	@Override
	public int getRep()
	{
		return this.rep;
	}
	
	@Override
	public boolean getParamsAssigned() {
		// TODO Auto-generated method stub
		return this.paramsSet;
	}

	@Override
	public void setParamsAssigned(boolean b) {
		this.paramsSet = b;
		
	}


	protected Hashtable<String, Integer> getParamsPersos() {
		return paramsPersos;
	}


}
