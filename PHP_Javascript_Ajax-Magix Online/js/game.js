//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/

let nodeAttacking = null;
let nodeAttacked = null;
let premierTour = true;
let partieTerminee = false;


window.addEventListener("load", () => {

	// Aller chercher les elements du board
	let nodeBoardJ = document.getElementById("joueur-battlefield");
	let nodeImgEnnemi = document.getElementById("img-ennemi");
	let nodeEndTurn = document.getElementById("btn-endturn");
	nodeImgEnnemi.uid = 0;

	// INSCRIRE LES ECOUTEURS
	// Ecouteur - Image de l'ennemi
	nodeImgEnnemi.addEventListener("click", function() {

		deplacerRole(false, nodeImgEnnemi); // mettre a jour la cible
		ecAttaquer(this);
	});

	// Ecouteur - Board du joueur
	nodeBoardJ.addEventListener("click", function() {
		// Un clic sur une carte du deck joueur envoie 2 events back-a-back.
		// On veut garder le 1er---> carte seule du deck (le 2e est le deck complet)
		if (nodeAttacking.parentNode.id !== "joueur-battlefield")
		{
			deplacerRole(false, nodeBoardJ); // mettre a jour la cible
			ecDeposerCarte(this);
		}

	})

	// Ecouteur - Bouton Fin de tour
	nodeEndTurn.addEventListener("click", function() {

		// Annuler attaquant et cible
		annulerRole(true);
		annulerRole(false);

		ecBoutons(this);
	});

	setTimeout(state, 1000);// Appel initial
})

const state = () => {

	let nodeHPower = document.getElementById("btn-hpower");

	// Demander etat de la partie au serveur
	$.ajax({
		url : 'ajaxState.php',
		type : 'POST'
	})

	// Reponse
	.done( function (msg)
	{
		let etatJeuActu = JSON.parse(msg);
		verifierFinJeu(etatJeuActu);

		// Le retour est un objet
		if ( typeof(etatJeuActu) === "object")
		{
			vieuxHp = etatJeuActu.hp;

			// Mettre a jour l'ecouteur du bouton Heros (si deja utilise a ce tour ou pas)
			if (!etatJeuActu.heroPowerAlreadyUsed)
			{
				nodeHPower.addEventListener("click", function() {

					// Annuler attaquant et cible
					annulerRole(true);
					annulerRole(false);
					ecBoutons(this);
				});
			}

			if (premierTour)
			{
				afficherCartes(etatJeuActu);
				afficherStats(etatJeuActu);
				premierTour = false;
			}

			if (!partieTerminee)
			{
				if (!etatJeuActu.yourTurn)
				{
					afficherCartes(etatJeuActu);
					afficherStats(etatJeuActu);
				}

				afficherTemps(etatJeuActu.yourTurn, etatJeuActu.remainingTurnTime);
			}
		}

		setTimeout(state, 1000);
	})
}


//#############################################################
// UTILITAIRES
//#############################################################
const afficherStats = (etatJeuActu) => {

	let nodeVies;
	let nodeArgent;
	let nodeCartesRest;

	//Stats ennemi
	nodeVies = document.querySelector("#espace-ennemi .nb-vies");
	nodeVies.innerHTML = etatJeuActu.opponent.hp;
	nodeArgent = document.querySelector("#espace-ennemi .argent-dispo p");
	nodeArgent.innerHTML = etatJeuActu.opponent.mp;
	nodeCartesRest = document.querySelector("#espace-ennemi .nb-cartes-restantes");
	nodeCartesRest.innerHTML = etatJeuActu.opponent.remainingCardsCount;

	//Stats joueur
	nodeVies = document.querySelector("#espace-joueur .nb-vies");
	nodeVies.innerHTML = etatJeuActu.hp;
	nodeArgent = document.querySelector("#espace-joueur .argent-dispo p");
	nodeArgent.innerHTML = etatJeuActu.mp;
	nodeCartesRest = document.querySelector("#espace-joueur .nb-cartes-restantes");
	nodeCartesRest.innerHTML = etatJeuActu.remainingCardsCount;
}

const afficherTemps = (yourTurn, secondes) => {

	document.querySelector("#espace-joueur .temps-restant").innerHTML = yourTurn ? secondes : "";
}

const verifierFinJeu = (etat) => {
	if ( typeof(etat) !== "object"  && etat === "GAME_NOT_FOUND" )
	{
		window.location.href = "lobby.php";
	}
}


const viderConteneur = (nodeContnr) => {

	let nodeEnfant = nodeContnr.lastElementChild;

	if (nodeEnfant != null)
	{
		while (nodeEnfant)
		{
			nodeContnr.removeChild(nodeEnfant);
			nodeEnfant = nodeContnr.lastElementChild;
		}
	}
}



//#############################################################
// ECOUTEURS : boutons, deposer, attaquer
//#############################################################
const ecBoutons = (nodeBouton) => {

	let typeStr = "";

	if (nodeBouton.value === "Heros" )
	{
		typeStr = "HERO_POWER";
	}
	else if (nodeBouton.value === "Finir tour")
	{
		typeStr = "END_TURN";
	}

	// Requete au serveur
	$.ajax({
		url : 'ajaxJeu.php',
		type : 'POST',
		data : {
			type: typeStr
		}
	})

	// Reponse Ajax du serveur
	.done( function (msg)
	{
		let etatJeuActu = JSON.parse(msg);

		if ( typeof(etatJeuActu) !== "object" && etatJeuActu === "GAME_NOT_FOUND" )
		{
			window.location.href = "lobby.php";
		}

		afficherStats(etatJeuActu);
		annulerRole(true);
		annulerRole(false);
	})
}

const ecDeposerCarte = () => {

	// joueur a deja choisi une carte et clique sur son board
	if (nodeAttacking !== null & nodeAttacked.id === "joueur-battlefield" )
	{
		// Requete au serveur
		$.ajax({
			url : 'ajaxJeu.php',
			type : 'POST',
			data : {
				type: "PLAY",
				uid: nodeAttacking.uid
			}
		})

		// Reponse Ajax du serveur
		.done( function (msg)
		{
			let etatJeuActu = JSON.parse(msg);
			verifierFinJeu(etatJeuActu);
			afficherCartes(etatJeuActu);
			afficherStats(etatJeuActu);
		})

		annulerRole(true);
		annulerRole(false);
	}
}

const ecAttaquer = () => {

	if (nodeAttacking !== null && // carte d'attaque est choisie
		 nodeAttacked !== null && // cible est choisie
		 	nodeAttacked !== document.getElementById("joueur-battlefield") // et cette cible n'est pas le board du joueur
		 )
	{
		// Envoyer la requete au serveur
		$.ajax({
			url : 'ajaxJeu.php',
			type : 'POST',
			data : {
				type: "ATTACK",
				uid: nodeAttacking.uid,
				targetuid: nodeAttacked.uid
			}
		})

		// Recevoir l'etat du jeu
		.done( function (msg)
		{
			let etatJeuActu = JSON.parse(msg);
			verifierFinJeu(etatJeuActu);
			afficherCartes(etatJeuActu);
			afficherStats(etatJeuActu);

		})

		annulerRole(true); // attaquant
		annulerRole(false); // cible
	}
}


//#############################################################
// CREATION / AFFICHAGE DES CARTES
//#############################################################

const afficherCartes = (etatJeuActu) => {

	//******************************/
	// HAND CACHEE DE L'ENNEMI
	//******************************/

	// Vider le conteneur de cartes
	let nbCartesEH = etatJeuActu.opponent.handSize;
	let nodeParentEH = document.getElementById("ennemi-hand");
	viderConteneur(nodeParentEH);

	// Creation des cartes-dos
	for (let i = 0; i < nbCartesEH; i++)
	{
		let carteDosNode = document.createElement("div");
		carteDosNode.className = "carte-dos";
		nodeParentEH.appendChild(carteDosNode);
	}

	//******************************/
	// HAND DU JOUEUR
	//******************************/

	// Vider le conteneur de cartes
	let nodeParentJH = document.getElementById("joueur-hand");
	viderConteneur(nodeParentJH);

	// Creation des cartes
	etatJeuActu.hand.forEach( carte => {

		//-- Instancier
		let carteJH = new Carte(nodeParentJH, carte);
		//-- Aller chercher son element HTML
		let nodeCarteJH = carteJH.node;
		//-- Y associer son uid
		nodeCarteJH.uid = carteJH.uid;

		//-- Inscrire l'ecouteur
		nodeCarteJH.addEventListener("click", function() {

			annulerRole(false); // remettre la cible a zero
			deplacerRole(true, this); // mettre a jour l'attaquant
		});

		carteJH.afficherCarte();
	})

	//******************************/
	// CARTES DU BOARD DE L'ENNEMI
	//******************************/

	// Vider le conteneur de cartes
	let nodeParentEGB = document.getElementById("ennemi-battlefield");
	viderConteneur(nodeParentEGB);

	// Creation des cartes
	etatJeuActu.opponent.board.forEach( carte => {

		//-- Instancier
		let carteEGB = new Carte(nodeParentEGB, carte);
		//-- Aller chercher son element HTML
		let nodeCarteEGB = carteEGB.node;
		//-- Y associer son uid
		nodeCarteEGB.uid = carteEGB.uid;
		//-- Inscrire l'ecouteur
		nodeCarteEGB.addEventListener("click", function() {

			deplacerRole(false, this); // mettre a jour la cible
			ecAttaquer(this);
		});

		carteEGB.afficherCarte();
	})

	//******************************/
	// CARTES DU BOARD DU JOUEUR
	//******************************/

	// Vider le conteneur de cartes
	let nodeParentJGB = document.getElementById("joueur-battlefield");
	viderConteneur(nodeParentJGB);

	// Instancier chaque carte et l'afficher
	etatJeuActu.board.forEach( carte => {
		//-- Instancier
		let carteJGB = new Carte(nodeParentJGB, carte);
		//-- Aller chercher son element HTML
		let nodeCarteJBG = carteJGB.node;
		//-- Y associer son uid
		nodeCarteJBG.uid = carteJGB.uid;

		//-- Inscrire l'ecouteur
		nodeCarteJBG.addEventListener("click", function() {

			annulerRole(false); // remettre la cible a zero
			deplacerRole(true, this); // mettre a jour l'attaquant
		});

		carteJGB.afficherCarte();
	})
}


//#############################################################
// GESTION DES ROLES (deplacer, annuler, vider)
//#############################################################

const deplacerRole = (isAttacking, newNode) => {

	// carte d'attaque choisie
	if (isAttacking)
	{
		// Un clic sur une carte du deck joueur envoie 2 events back-a-back.
		// On veut garder le 1er---> carte seule du deck (le 2e est le deck complet)
		if ( nodeAttacking !== null &&
				nodeAttacking.parentNode === document.getElementById("joueur-battlefield") &&
					newNode === document.getElementById("joueur-battlefield") )
		{
			//Cas special
		}
		else
		{
			viderNode(nodeAttacking);
			nodeAttacking = newNode; // nouvel attaquant
			nodeAttacking.style.boxShadow = "0 0 50px yellow"; // hightlight
		}
	}
	else // cible choisie
	{
		viderNode(nodeAttacked);
		nodeAttacked = newNode; // nouvelle cible
		nodeAttacked.style.boxShadow = "0 0 50px red"; // highlight
	}
}

const viderNode = (node) => {

	if (node !== null)
	{
		node.style.boxShadow = "";
	}

	node = null;
}

const annulerRole = (isAttacking) => {
	// carte d'attaque
	if (isAttacking)
	{
		viderNode(nodeAttacking);
	}
	else // cible
	{
		viderNode(nodeAttacked);
	}
}
