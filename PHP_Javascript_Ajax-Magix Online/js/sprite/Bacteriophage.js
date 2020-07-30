//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/


class Bacteriophage {
	constructor() {
		this.radius = 20;
		this.x = this.radius;
		this.y = this.radius;

		this.sens = Math.floor(Math.random()*4);
		this.angle = null; //debout par defaut

		// Apparait en haut
		if (this.sens <= 1)
		{
			this.x = Math.floor(Math.random() * (canvas.width-this.radius));
		}

		// Apparait a droite
		if (this.sens == 2)
		{
			this.x = canvas.width-this.radius;
			this.y = Math.random() * (canvas.height-this.radius*3.5); // toujours plus haut que la tete du docteur
		}

		// Apparait a gauche
		else if (this.sens == 3)
		{
			this.x = this.radius;
			this.y = Math.random() * (canvas.height-this.radius*3.5); // toujours plus haut que la tete du docteur
		}

		let columnCount = 2;
		let rowCount = 1;
		let refreshDelay = 1000; // 5 fois par sec --- 200ms, on va se deplacer de colonne---c'est lent
		let columnLoop = true;	//notre grille va aller d'une col a l'autre (avant de changer de ligne)
		let scale = 0.3; // 0.1 est minuscule, 10 est geant

		this.tiledImage = new TiledImage("images/bacteriophage_4.png", columnCount, rowCount, refreshDelay, columnLoop, scale);

		this.tiledImage.changeRow(0); // mets-toi sur la rangee 0 (1ere ligne)
		this.tiledImage.changeMinMaxInterval(0, 1); // boucle de la colonne 0 a la col 1

		switch(this.sens) {
			case 0: this.angle = 180; break; //haut
			case 1: this.angle = 180; break; //droite
			case 2: this.angle = 270; break; // gauche
			case 3: this.angle = 90; break; // gauche
		}

		this.tiledImage.setRotationAngle(this.angle); // marche si l'image est pas flipped
	}

	tick() {

		this.tiledImage.tick(this.x, this.y, ctx);

	}
}