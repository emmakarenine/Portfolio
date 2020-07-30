<?php
	require_once("CommonAction.php");

	// Appelle le service games/action
	class AjaxJeuAction extends CommonAction
	{
		public function __construct()
		{
			parent::__construct(CommonAction::$VISIBILITY_MEMBER);
		}

		// Cette fonction appelle le service games/action et retourne son resultat
		protected function executeAction()
		{
			$result = "";
			$data = [];

			// Si on a clique sur sur END_TURN ou HERO_POWER
			$data["key"] = $_SESSION["key"];
			$data["type"] = $_POST["type"];

			// On va DEPOSER UNE CARTE
			if ( $data["type"] == "PLAY" )
			{
				$data["uid"] = $_POST["uid"];
			}
			// On va ATTAQUER l'ennemi
			else if ( $data["type"] == "ATTACK" )
			{
				$data["uid"] = $_POST["uid"];
				$data["targetuid"] = $_POST["targetuid"];
			}

			// Appeler le service
			$result = parent::callAPI("games/action", $data);

			if ($result == "GAME_NOT_FOUND")
			{
				$result = parent::callAPI("games/action", $data);
				// Diriger vers le login
				//header("location:lobby.php");
				//exit;
			}

			return compact("result");
		}
	}