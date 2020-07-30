<?php
	require_once("CommonAction.php");

	// Service games/auto-match
	class LobbyAction extends CommonAction
	{
		public function __construct()
		{
			parent::__construct(CommonAction::$VISIBILITY_MEMBER);
		}

		protected function executeAction()
		{
			$connectionError = false;
			$result = [];
			$data = [];

			$data["key"] = $_SESSION["key"];

			// On a clique sur PRATIQUER
			if ( !empty($_POST["pratiquer"]) )
			{
				$data["type"] = "TRAINING";

				// Valider par le service
				$result = parent::callAPI("games/auto-match", $data);

				// Params valides
				if ($result == "JOINED_TRAINING")
				{
					// Ajouter une var session pour le type (optionnel)
					$_SESSION["type"] = $result;
					echo ("'<script>alert('.$result.')</script>");

					// Diriger vers le gameboard
					header("location:game.php");
					exit;
				}
				else // Erreurs diverses qui empechent de demarrer une pratique
				{
					if ($result == "DECK_INCOMPLETE")
					{
						echo ("'<script>alert('.$result.')</script>");
					}
					else if ($result == "INVALID_KEY")
					{
						echo ("'<script>alert('.$result.')</script>");
					}

					$connectionError = true;
				}
			}
			// On a clique sur JOUER
			else if ( !empty($_POST["jouer"]) )
			{
				$data["type"] = "PVP";

				// Valider par le service
				$result = parent::callAPI("games/auto-match", $data);

				// Params valides
				if ($result == "JOINED_PVP")
				{
					$_SESSION["type"] = $result;

					// Diriger vers le gameboard
					header("location:game.php");
					exit;
				}
				else if ($result == "CREATED_PVP")
				{
					$_SESSION["type"] = $result;

					echo ("'<script>alert('.$result.')</script>");

					// Diriger vers le gameboard
					header("location:game.php");
					exit;
				}
				else // Erreurs diverses
				{
					if ($result == "DECK_INCOMPLETE")
					{
						echo ("'<script>alert('.$result.')</script>");
					}
					else if ($result == "INVALID_KEY")
					{
						echo ("'<script>alert('.$result.')</script>");
					}

					$connectionError = true;
				}
			}
			// On a clique sur QUITTER
			else if ( !empty($_POST["quitter"]) )
			{
				// Valider la cle dans la BD
				$result = parent::callAPI("signout", $data);

				// Cle valide
				if ($result == "SIGNED_OUT")
				{
					// Detruire variables sesssion
					// session_unset();
					// session_destroy();
					// session_start();
					$_SESSION["visibility"] = CommonAction::$VISIBILITY_PUBLIC;

					// Diriger vers le lobby
					header("location:login.php");
					exit;
				}
				else
				{
					$connectionError = true;
				}
			}

		return compact("connectionError");
		}
	}


// ANCIENNE VERSION FONCTIONNELLE
// <?php
// 	require_once("CommonAction.php");

// 	class LobbyAction extends CommonAction
// 	{
// 		public function __construct()
// 		{
// 			parent::__construct(CommonAction::$VISIBILITY_MEMBER);
// 		}

// 		protected function executeAction()
// 		{
// 			$connectionError = false;
// 			$data = [];

// 			$_SESSION["type"] = null;

// 			// On a clique sur PRATIQUER
// 			if ( !empty($_POST["pratiquer"]) )
// 			{
// 				$data["key"] = $_SESSION["key"];
// 				$data["type"] = "TRAINING";

// 				// Valider par le service
// 				$result = parent::callAPI("games/auto-match", $data);

// 				// Params valides
// 				if ($result == "JOINED_TRAINING")
// 				{
// 					// Ajouter une var session pour le type
// 					$_SESSION["type"] = $result;
// 					//$data["type"] = $_SESSION["type"];

// 					// Diriger vers le gameboard
// 					header("location:game.php");
// 					exit;
// 				}
// 				else // Erreurs diverses qui empechent de demarrer une pratique
// 				{
// 					if ($result == "DECK_INCOMPLETE")
// 					{
// 						echo ("Votre deck doit contenir 30 cartes.");
// 					}
// 					else if ($result == "INVALID_KEY")
// 					{
// 						echo ("Nous n'avons pu valider votre identite.");
// 					}

// 					$connectionError = true;
// 				}
// 			}
// 			// On a clique sur JOUER
// 			if ( !empty($_POST["jouer"]) )
// 			{
// 				$data["key"] = $_SESSION["key"];
// 				$data["type"] = "PVP";

// 				// Valider par le service
// 				$result = parent::callAPI("games/auto-match", $data);

// 				// Params valides
// 				if ($result == "JOINED_PVP")
// 				{
// 					// Ajouter une var session pour le type
// 					$_SESSION["type"] = $result;
// 					//$data["type"] = $_SESSION["type"];

// 					// Diriger vers le gameboard
// 					header("location:game.php");
// 					exit;
// 				}
// 				else if  ($result == "CREATED_PVP")
// 				{
// 					// Ajouter une var session pour le type
// 					$_SESSION["type"] = $result;
// 					//$data["type"] = $_SESSION["type"];

// 					echo ("En attente d'un autre joueur.");

// 					// Diriger vers le gameboard
// 					header("location:game.php");
// 					exit;
// 				}
// 				else // Erreurs diverses qui empechent de demarrer une partie
// 				{
// 					if ($result == "DECK_INCOMPLETE")
// 					{
// 						echo ("Votre deck doit contenir 30 cartes.");
// 					}
// 					else if ($result == "INVALID_KEY")
// 					{
// 						echo ("Nous n'avons pu valider votre identite.");
// 					}

// 					$connectionError = true;
// 				}
// 			}
// 			// On a clique sur QUITTER
// 			else if ( !empty($_POST["logout"]) )
// 			{
// 				$data["key"] = $_SESSION["key"];

// 				// Valider la cle dans la BD
// 				$result = parent::callAPI("signout", $data);

// 				// Cle valide
// 				if ($result == "SIGNED_OUT")
// 				{
// 					// Detruire variables sesssion
// 					session_unset();
// 					session_destroy();

// 					// Preparer nouvelle session
// 					session_start();
// 					$_SESSION["visibility"] = CommonAction::$VISIBILITY_PUBLIC;

// 					// Diriger vers le lobby
// 					header("location:login.php");
// 					exit;
// 				}
// 				else
// 				{
// 					$connectionError = true;
// 				}
// 			}

// 			return compact("connectionError");
// 		}
// 	}