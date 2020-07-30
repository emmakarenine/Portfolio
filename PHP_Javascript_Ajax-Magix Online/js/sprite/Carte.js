//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/

class Carte
{
	constructor(nodeParent, hand)
	{
		this.nodeParent = nodeParent;
		this.id = hand.id;
		this.cost = hand.cost;
		this.hp = hand.hp;
		this.atk = hand.atk;
		this.mechs = hand.mechanics;
		this.uid = hand.uid;
		this.baseHP = hand.baseHP; // vie avec laquelle tu commences
		this.isSelected = false;

		this.classCost = 'c'+this.cost;
		this.classeFace =  "carte-face";

		// Creer le div de notre carte
		this.node = document.createElement("div");
		this.node.classList.add(this.classCost, this.classeFace);

		this.nodeParent.appendChild(this.node);
	}

	afficherCarte()
	{
		let newNode = null;

		// Creation des elements textes de la carte
		//Creer champ COST
		newNode = document.createElement("div");
		newNode.className = "cost";
		newNode.style.backgroundColor = "RGBA(255,255,255,0.5)";
		newNode.innerHTML = 'COUT: '+this.cost;
		this.node.appendChild(newNode);

		// Champ ATTACK
		newNode = document.createElement("div");
		newNode.className = "atk";
		newNode.style.backgroundColor = "RGBA(255,255,255,0.3)";
		newNode.innerHTML = 'ATTAQUE: '+this.atk;
		this.node.appendChild(newNode);

		// Champ VIE
		newNode = document.createElement("div");
		newNode.className = "hp";
		newNode.style.backgroundColor = "RGBA(255,255,255,0.3)";
		newNode.innerHTML = 'VIE: '+this.hp;
		this.node.appendChild(newNode);

		// Champ MECHANICS
		if ( this.mechs.length > 0 )
		{
			let mechsTxt = "PLUS:<br>"
			newNode = document.createElement("div");
			newNode.className = "mechs";
			newNode.style.backgroundColor = "RGBA(255,255,255,0.5)";

			// Parcourir et aller chercher chaque mechanic
			this.mechs.forEach( mech => {
				mechsTxt += mech+" ----- ";
			});

			newNode.innerHTML = mechsTxt;
			this.node.appendChild(newNode);
		}

		// Champ UID
		newNode = document.createElement("div");
		newNode.className = "uid";
		newNode.style.backgroundColor = "RGBA(255,255,255,0.3)";
		newNode.innerHTML = 'UID: '+this.uid;
		this.node.appendChild(newNode);

	}

}