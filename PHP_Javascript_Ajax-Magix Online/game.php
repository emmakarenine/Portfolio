<?php
	require_once("action/GameAction.php");
	$action = new GameAction();
	$data = $action->execute();
	//L'affichage de la victoire ou defaite de la partie la plus recente
	// se fait dans le lobby, et il faut cliquer sur END TURN pour y avoir acces
?>

<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="css/style.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/game.js"></script>
	<script src="js/sprite/Carte.js"></script>
	<title>Cell Attack! - Au jeu</title>
</head>
<body id="gameboard-container">

		<section id="espace-ennemi">

			<div id="ennemi-hand" class="gauche"></div>

			<div class="centre">
				<div class="nb-vies"></div>
				<div id="img-ennemi"></div>
				<div class="argent-dispo">
					<p></p>
				</div>
			</div>

			<div class="droite">
				<div class="nb-cartes-restantes"></div>
			</div>

		</section>
		<section id="battlefield">
			<div id="ennemi-battlefield"></div>
			<div id="joueur-battlefield"></div>
		</section>

		<section id="espace-joueur">

			<div class="gauche">
				<div class="nb-vies"></div>
				<div class="argent-dispo">
					<p></p>
				</div>
				<div class="nb-cartes-restantes" class="carte-dos">
				</div>
			</div>

			<div class="centre">
				<div id="img-joueur"></div>
				<div id="joueur-hand"></div>
			</div>

			<form action = "game.php" method = "post" class="droite">
				<input type="submit" id="btn-hpower" value="Heros" name="hpower">
				<input type="submit" id="btn-endturn" value="Finir tour" name="endturn">
				<div class="temps-restant"></div>
			</form>
		</section>
</body>
</html>