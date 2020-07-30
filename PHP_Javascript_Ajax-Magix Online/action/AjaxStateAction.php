<?php
	require_once("CommonAction.php");

	// Appelle games/state
	class AjaxStateAction extends CommonAction
	{
		public function __construct()
		{
			parent::__construct(CommonAction::$VISIBILITY_MEMBER);
		}

		protected function executeAction()
		{
			$result = "";
			$data = [];

			$data["key"] = $_SESSION["key"];

			// Appeler le service
			$result = parent::callAPI("games/state", $data);

			if ($result == "WAITING")
			{
				// En attente
			}
			else if ($result === "LAST_GAME_WON")
			{
				// Garder le denouement de la partie en var session
				$_SESSION["state"] = $result;
				$data["key"] = $result;
				// Diriger vers le lobby
				header("location:lobby.php");
				exit;
			}
			else if ($result === "LAST_GAME_LOST")
			{
				// Garder le denouement de la partie en var session
				$_SESSION["state"] = $result;
				$data["key"] = $result;
				// Diriger vers le lobby
				header("location:lobby.php");
				exit;
			}
			else if ($result === "INVALID_KEY") // Cle invalide
			{
				$_SESSION["state"] = $result;
				// Diriger vers le login
				header("location:login.php");
				exit;
			}
			else if ($result === "") // Cle invalide
			{
				$_SESSION["state"] = "CHAINE VIDE";
				// Diriger vers le login
				header("location:login.php");
				exit;
			}

			$_SESSION["state"] = $result;
			return compact("result");
		}
	}