<!-- ***************************
Makraphone Phouttama
TP Magix
Remise: Mardi, 28 avril 2020
**************************** -->

<?php

	class Connection
	{
		private static $connection;

		public static function getConnection()
		{
			// Si la connexion n'est pas initialisee...
			if (!isset(Connection::$connection))
			{
				//...initialise-en une
				// PDO: ~ interface entre la BD et nous (~protocole SJBC(?))
				Connection::$connection = new PDO("oci:dbname=" . DB_USER, DB_PASS, DB_CODE_PROF);
				// Pour faire afficher les erreurs (ceci permet de lancer des PDO_Exception utile avec
				// try/catch
				Connection::$connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				// L’endroit où les variables sont remplacées ( ? to var).
				Connection::$connection->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
			}

			return Connection::$connection;
		}

		/**
		 * L'utilisation de cette méthode n'est pas nécessaire, car
		 * lorsque la page a terminée, la classe n'est plus référenciée et
		 * la connexion est fermée. J'ai fait des tests pour le vérifier
		 **/
		public static function closeConnection() {
			if (isset(Connection::$connection)) {
				Connection::$connection = null;
			}
		}
	}
