<?php
	require_once("action/LoginAction.php");

	// Retour sur la page login efface toutes les variables session
	session_unset();
	session_destroy();
	session_start();

	$action = new LoginAction();
	$data = $action->execute();
?>

<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<link rel="stylesheet" href="css/style.css">
		<script src="js/login.js"></script>
		<script src="js/sprite/VirusVert.js"></script>
		<title>Cell Attack! - Login</title>
	</head>
	<body>

		<div id="login-container">

			<section id="titre">
				<h1>Cell Attack !</h1>
				<h3>Que le plus contagieux gagne !</h3>
			</section>

			<section id="login-connexion">
					<div>
					<h2>Connectez-vous pour jouer !</h2>
					<form action="login.php" method="post">
						<h3>Nom d'utilisateur</h3>
						<input type="text" name="username" placeholder="Nom d'utilisateur" required>
						<h3>Mot de passe</h3>
						<input type="password" name="password" placeholder="Mot de passe">
						<input type="submit" id="btn-login" value="Se connecter">
					</form>
				</div>
			</section>


		</div>

	</body>
</html>