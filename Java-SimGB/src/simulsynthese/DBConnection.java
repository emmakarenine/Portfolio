package simulsynthese;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DBConnection
{
	public static final String DB_NAME = "simulation";
	public static final String DB_HOST = "localhost";
	
	// Creer le client qui se connecte
	private static MongoClient mongoClient;
	private static MongoDatabase connection;

	// Acceder a la DB
    MongoDatabase db;;
    
    // Acceder a une collection
    MongoCollection<Document> coll1 = db.getCollection("ile");
    
    
	public static MongoDatabase getConnection()
	{
		mongoClient = new MongoClient(DB_HOST, 27017);
		connection = mongoClient.getDatabase(DB_NAME);
		return connection;
	}
	
	/**
	 * Méthode permettant de tester la connexion
	 * 
	 * @return si la connexion est ouverte ou pas
	 */
	public static boolean connectionCredentialsValid()
	{
		MongoDatabase c = getConnection();
		boolean valid = c != null;
		releaseConnection();
		
		return valid;
	}
	
	public static void releaseConnection()
	{
		if (connection != null)
		{
				mongoClient.close();
				connection = null;
		}
	}
	
	
}

//Code de Fred
//package ca.qc.cvm.dba.magix.dao;
//
//import java.io.File;
//import java.net.UnknownHostException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import com.mongodb.*;
//import com.mongodb.client.MongoDatabase;
//
//public class DBConnection {
//	private static MongoClient mongoClient; 
//	private static MongoDatabase connection;
//	private static final String DB_NAME = "magix_db";
//	private static final String DB_HOST = "localhost";
//	
//	/**
//	 * Méthode qui permet de retourner une connexion à la base de données
//	 * 
//	 * @return
//	 */
//	public static MongoDatabase getConnection() {
//		if (connection == null) {
//			
//			try {
//				Logger mongoLogger = Logger.getLogger( "org.mongodb" );
//				mongoLogger.setLevel(Level.WARNING);
//				
//				mongoClient = new MongoClient(DB_HOST);
//				connection = mongoClient.getDatabase(DB_NAME);
//				
//			} 
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return connection;
//	}
//	
//	/**
//	 * Méthode permettant de tester la connexion
//	 * 
//	 * @return si la connexion est ouverte ou pas
//	 */
//	public static boolean connectionCredentialsValid() {
//		MongoDatabase c = getConnection();
//		boolean valid = c != null;
//		releaseConnection();
//		
//		return valid;
//	}
//	
//	public static void releaseConnection() {
//		if (connection != null) {
//			try {
//				mongoClient.close();
//				connection = null;
//			} 
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}