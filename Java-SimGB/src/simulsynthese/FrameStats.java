package simulsynthese;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class FrameStats extends JFrame
{
	private final int STATS_LARGEUR = 1600;
	private final int STATS_HAUTEUR = 900;

	public FrameStats()
	{
		setTitle("Simulation - Données statistiques");
		this.setSize(STATS_LARGEUR, STATS_HAUTEUR);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null); // centrer la fenetre
	}

}
