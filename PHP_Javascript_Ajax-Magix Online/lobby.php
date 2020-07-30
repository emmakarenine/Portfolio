<?php
	require_once("action/LobbyAction.php");

	$action = new LobbyAction();
	$data = $action->execute();

?>

<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="css/style.css">
	<script src="js/lobby.js"></script>
	<script src="js/TiledImage.js"></script>
	<script src="js/sprite/Doctor.js"></script>
	<script src="js/sprite/Bacteriophage.js"></script>
	<title>Cell Attack! - Lobby</title>
</head>
<body id="lobby-container">

	<section class="lobby-gauche">
		<section id="titre">
			<h1>Cell Attack !</h1>
		</section>
	</section>

	<main class="lobby-main">
	<?php

if ($data["state"] !== null )
{
	if ( $data["state"] === "LAST_GAME_WON" )
	{
		echo("<div style= 'background-color:white;padding-top:20px;font-weight: bold;text-align:center'>VOUS AVEZ GAGNE VOTRE DERNIERE PARTIE... BRAVO ! </div>");

	}
	else if ( $data["state"] === "LAST_GAME_LOST" )
	{
		echo("<div style= 'background-color:white;padding-top:20px;font-weight: bold;text-align:center'>VOUS AVEZ PERDU VOTRE DERNIERE PARTIE... ON SE REPREND !</div>");
	}
}
	?>
		<canvas id="lobby-canvas"></canvas>
		<section class="lobby-bas">
			<form action="lobby.php" method="post" class="bas-gauche">
				<input type="submit" class="btn-pratiquer" value="Pratiquer" name="pratiquer">
				<input type="submit" class="btn-jouer" value="Jouer" name="jouer">
			</form>

			<div class="chatroom">
				<iframe style="width:100%;height:300px;font-size:0.8em;" onload="applyStyles(this)"
				src="<?="https://magix.apps-de-cours.com/server/#/chat/".$_SESSION["key"]; ?>">
				</iframe>
			</div>

			<form action="lobby.php" method="post" class="bas-droite" >
				<input type="submit" class="btn-quitter" value="Quitter" name="quitter">
			</form>
		</section>

	</main>

</body>
</html>