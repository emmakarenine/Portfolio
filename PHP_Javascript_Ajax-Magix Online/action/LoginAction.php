<?php
	require_once("CommonAction.php");

	class LoginAction extends CommonAction
	{
		public function __construct()
		{
			parent::__construct(CommonAction::$VISIBILITY_PUBLIC);
		}

		// executeAction() doit tjrs retourner un tableau
		protected function executeAction()
		{
			$connectionError = false;
			$data = [];

			// Si les 2 variables existent et sont remplies
			if ( isset($_POST["username"]) && isset($_POST["password"]) &&
					!empty($_POST["username"]) && !empty($_POST["password"])  )
			{
				$data["username"] = $_POST["username"];
				$data["password"] = $_POST["password"];

				// Valider dans la BD
				$result = parent::callAPI("signin", $data);

				// Infos de login invalides
				if ($result === "INVALID_USERNAME_PASSWORD")
				{
					$connectionError = true;
				}
				else // Infos de login valides
				{
					// Creer les variables session
					$_SESSION["username"] = $_POST["username"];
					$_SESSION["visibility"] = CommonAction::$VISIBILITY_MEMBER;
					$key = $result->key;
					$_SESSION["key"] = $key;
					//$_SESSION["type"] = null;

					// Diriger vers le lobby
					header("location:lobby.php");
					exit;
				}
			}

			return compact("connectionError");
		}
	}
