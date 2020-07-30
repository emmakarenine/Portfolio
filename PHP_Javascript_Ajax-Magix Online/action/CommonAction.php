<?php
	// Ce fichier contient la creation du COOKIE
	session_start();

	abstract class CommonAction
	{
		// Niveaux d'utilisateurs qui pourront etre identifies
		public static $VISIBILITY_PUBLIC = 0;
		public static $VISIBILITY_MEMBER = 1;

		private $visibility = null;

		public function __construct($visibility)
		{
			$this->visibility = $visibility;
		}

		// Definir dans les sous-classes
		protected abstract function executeAction();


		public function execute()
		{
			if ( !isset($_SESSION["visibility"]) )
			{
				$_SESSION["visibility"] = CommonAction::$VISIBILITY_PUBLIC; // niveau de base
			}

			$data = $this->executeAction();


			$data["key"] = empty($_SESSION["key"]) ? null : $_SESSION["key"];
			$data["username"] = empty($_SESSION["username"]) ? null : $_SESSION["username"];
			$data["visibility"] = empty($_SESSION["visibility"]) ? null : $_SESSION["visibility"];
			$data["isConnected"] = $_SESSION["visibility"] > CommonAction::$VISIBILITY_PUBLIC;
			$data["state"] = empty($_SESSION["state"]) ? null : $_SESSION["state"];

			if ( !$data["isConnected"])
			{
				session_unset();
				session_destroy();
				 session_start();
			}

			if ( !isset($_COOKIE) && isset( $_SESSION["username"]) )
			{
				setcookie($_SESSION["username"], $_SESSION["username"], time()+3600);
			}

			return $data;

		}


		// CONNEXION A L'API
		/**
		 * data = array('key1' => 'value1', 'key2' => 'value2');
		 */

		public function callAPI($service, array $data)
		{
			$apiURL = "https://magix.apps-de-cours.com/api/" . $service;

			$options = array(
				'http' => array(
					'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
					'method'  => 'POST',
					'content' => http_build_query($data)
				)
			);
			$context  = stream_context_create($options);
			$result = file_get_contents($apiURL, false, $context);

			if (strpos($result, "<br") !== false) {
				var_dump($result);
				exit;
			}

		return json_decode($result);
		}
	}



				// Deconnexion
			// if ( !empty($_GET["logout"]) ) // lie au nav dans header.php : ?logout=true
			// {
			// 	session_unset();
			// 	session_destroy();
			// 	session_start(); // repartir une nouvelle session tds
			// }