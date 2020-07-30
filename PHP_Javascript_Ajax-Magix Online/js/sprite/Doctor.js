//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/

class Doctor
{
	constructor()
	{
		this.radius = 16;
		this.x = 0;
		this.y = canvas.height-this.radius*2;
		this.vitesse = 1.2;
		this.versDroite = true;
		this.away = false;

		// Appel a TiledImage.js :
		// Besoin du nb de lignes/colonnes dans l'img a loader
		let columnCount = 9;
		let rowCount = 1;
		let refreshDelay = 500; // 5 fois par sec --- 200ms, on va se deplacer de colonne---c'est lent
		let columnLoop = true;	//notre grille va aller d'une col a l'autre (avant de changer de ligne)
		let scale = 0.5; // 0.1 est minuscule, 10 est geant

		this.tiledImage = new TiledImage("images/doctor_transp4.png", columnCount, rowCount, refreshDelay, columnLoop, scale);

		this.tiledImage.changeRow(0); // mets-toi sur la rangee 0
		this.tiledImage.changeMinMaxInterval(0, 8); // boucle de la colonne 0 a la col 8
	}

	isAway()
	{
		return this.away;
	}

	//Gerer les sorties du docteur
	tick()
	{

		if (this.versDroite)
		{
			if (this.x < canvas.width+this.radius*2)
			{
				this.x += this.vitesse;
				this.away = false;
			}
			else // Docteur sort du cadre vvers la droite
			{
				this.versDroite = !this.versDroite;
				this.away = true;
			}
		}
		else
		{
			if (this.x > -this.radius*2)
			{
				this.x -= this.vitesse;
				this.away = false;
			}
			else // Docteur sort du cadre vers la gauche
			{

				this.versDroite = !this.versDroite;
				this.away = true;
			}
		}

		this.tiledImage.tick(this.x, this.y, ctx);
	}
}