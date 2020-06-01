package simulsynthese;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuInit extends JFrame implements VueUtil
{	
	private JLabel titreSimul;
	private JLabel lblUneSimulationDe;
	private JButton btnDemarrer;
	private JButton btnParams;
	private JButton btnCharger;
	private JButton btnQuitter;
	
	private int repInit;
	private boolean repSet = false;
	private boolean paramsSet = false;
	

	private Ecouteur ec;

	/**
	 * Create the frame.
	 */
	public MenuInit()
	{		
		//this.parent = vue;
		setTitle("Grande Barrière VS Humain : Menu initial");
		setBounds(100, 100, 1200, 800); // VERIFIER SI 1200 et 800 SONT ADEQUATS POUR TOUT ORDI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null); // centrer
		
		titreSimul = new JLabel("Grande Barri\u00E8re VS l'Humain");
		titreSimul.setHorizontalAlignment(SwingConstants.CENTER);
		titreSimul.setVerticalAlignment(SwingConstants.CENTER);
		titreSimul.setFont(new Font("Dialog", Font.PLAIN, 32));
		titreSimul.setBounds(160, 28, 800, 75);
		getContentPane().add(titreSimul);
		
		lblUneSimulationDe = new JLabel("Une simulation de la Grande Barri\u00E8re de Corail");
		lblUneSimulationDe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUneSimulationDe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUneSimulationDe.setBounds(658, 87, 334, 38);
		getContentPane().add(lblUneSimulationDe);
		
		btnDemarrer = new JButton("D\u00E9marrer");
		btnDemarrer.setBounds(319, 264, 235, 32);
		getContentPane().add(btnDemarrer);
		
		btnParams = new JButton("Personnaliser les param\u00E8tres");
		btnParams.setBounds(588, 264, 235, 32);
		getContentPane().add(btnParams);
		
		btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds(588, 317, 235, 32);
		getContentPane().add(btnQuitter);
		
		btnCharger = new JButton("Charger une partie");
		btnCharger.setBounds(319, 317, 235, 32);
		getContentPane().add(btnCharger);
		
		ec = new Ecouteur();
		btnDemarrer.addActionListener(ec);
		btnParams.addActionListener(ec);
		btnCharger.addActionListener(ec);
		btnQuitter.addActionListener(ec);
	}

	
	
	private class Ecouteur implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			JButton src = (JButton) e.getSource();			
			
			if (src == btnDemarrer)
			{
				enregChoix(0);				
			}
			else if (src == btnParams)
			{
				enregChoix(1);
			}
			else if (src == btnCharger)
			{
				enregChoix(2);
			}
			else if (src == btnQuitter)
			{
				int rep = JOptionPane.showConfirmDialog(MenuInit.this, 
						"Vous avez choisi de quitter. Le programme va s'arreter.", "Quitter", JOptionPane.WARNING_MESSAGE, 
						JOptionPane.YES_NO_OPTION);
				
				if (rep == JOptionPane.YES_OPTION )
				{
					enregChoix(3);
				}
			}
		}
	}
	
	@Override
	public void enregChoix(int rep)
	{
		setRep(rep);
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
	public void setRep(int rep)
	{
		this.repInit = rep;
	}

	@Override
	public int getRep()
	{
		return this.repInit;
	}
	
	@Override
	public boolean getRepSet()
	{
		return this.repSet;
	}
	
	@Override
	public void setRepSet(boolean b)
	{
		this.repSet = b;
	}

	@Override
	public boolean getParamsAssigned() {
		// TODO Auto-generated method stub
		return this.paramsSet;
	}

	@Override
	public void setParamsAssigned(boolean b) {
		// TODO Auto-generated method stub
		this.paramsSet = b;
		
	}
}