package simulsynthese;

public class SimulDao {
	
}


//Code de Fred
//package ca.qc.cvm.dba.magix.dao;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//
//import org.bson.Document;
//
//import com.mongodb.AggregationOutput;
//import com.mongodb.BasicDBList;
//import com.mongodb.BasicDBObject;
//import com.mongodb.Block;
//import com.mongodb.CommandResult;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//import com.mongodb.client.AggregateIterable;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//import ca.qc.cvm.dba.magix.entity.Card;
//
//public class GameDAO {
//	/**
//	 * Méthode retournant le nombre de parties jouées
//	 * @return nbre
//	 */
//	public static long getGameCount() {
//
//		//MongoDatabase connection = DBConnection.getConnection();
//		//return connection.getCollection("gameResults").count();
//
//		return DBConnection.getConnection().getCollection("gameResults").count();
//	}
//	
//	/**
//	 * Permet d'obtenir des informations sur les dernières parties jouées
//	 * 
//	 * @param numberOfResults Nombre de partie à retourner
//	 * @return Une liste contenant les informations des dernières parties jouées
//	 */
//	public static List<String> getLatestGamesResults(int numberOfResults) {
//		
//		final List<String> results = new ArrayList<String>();
//		
//		/*MongoDatabase connection = DBConnection.getConnection();
//		MongoCollection<Document> collection = connection.getCollection("gameResults");*/
//		
//		MondoCollection<Document> collection = 
//				DBConnection.getConnection().getCollection().("gameResults");
//
//		/*long time = System.currentTimeMillis();
//		Date date = new Date(time);
//		date.toString();*/
//		// Affichera un format similaire à : Web Mar 30 13:33:12 EDT 2016
//
//		//Document query = new Document(null);
//		Document orderBy  = new Document("date", -1); // -1 = DESC, 1 = ASC
//		FindIterable<Document> iterator = 
//					collection.find().sort(orderBy).limit(numberOfResults);
//		
//		try {
//
//			iterator.forEach (
//
//				new Block<Document>() {
//
//					@Override
//					public void apply(final Document document) {
//					
//						int outc = document.getInteger("outcome");
//						int nbRounds = document.getInteger("nbRounds");
//						
//						String date_str = new Date(document.getLong("date")).toString();
//						String nbRounds_str = String.valueOf(document.getInteger("nbRounds"));
//
//						int outc = document.getInteger("outcome");
//						String outc_stri = "";					
//						switch(outc)
//						{
//						case 0: outc_str = "--"; break;
//						case 1: outc_str = "Joueur"; break;
//						case 2: outc_str = "IA"; break;	
//						}
//
//						results.add
//						(String.format("%-35s Gagnee par : %-15s %-3s tours ",
//										date_str, outc_str, nbRounds_str) );
//		   			}
//		   		}
//		   	); // fin de iterable.forEach
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//						
//		return results;
//	}
//	
//	/**
//	 * Permet de savoir, pour le joueur, le nombre de parties 
//	 * qui ont été gagnées avec cette carte dans leur sélection.
//	 * 
//	 * Cette méthode ne doit pas vérifier pour l'IA, seulement le vrai joueur
//	 * 
//	 * @param c Carte à vérifier
//	 * @return Le nombre de parties
//	 */
//	private static long getNumberOfWonGames(Card c) {		
//		
//		MongoCollection<Document> collection =
//			DBConnection.getConnection().getCollection("gameResults");
//		
//		Document query = new Document("outcome", 1); // 1er query: cherche dans Gagnant(outcome) == joueur
//
//		query.append("liste_cartes_joueur", c.getName()); // 2e query: cherche dans Liste == carte en param)
//
//		return collection.count(query);
//	}	
//		
//		/**
//	 * Nombre de tours moyen avant que la partie se complète. 
//	 * 
//	 * @return
//	 */
//	public static double getAverageRounds() {
//		
//		MongoCollection collection =
//			DBConnection.getConnection().getCollection("gameResults");
//
//		// {$group : {_id : null, total : { $sum : temps }}}
//		// SQL : SELECT SUM(temps) FROM collExceptions GROUP BY null
//				
//		Document group = new Document(
//							"$group", new Document( "_id", null 	// SELECT * FROM collection
//									               ).append(   // ajout d’une new colonne a l’entree
//									               			"moyenne", new Document( // nom de cette colonne, et son type (****)
//															  			             "$avg", "$nbRounds" // fct AVG, sur la colonne "nbRounds"
//															  			             					 // donne un double == type(****)
//															                        )
//														   )
//						             );
//
//		List<Document> list = new ArrayList<Document>();
//		list.add(group);
//
//		final List<Document> results = new ArrayList<Document>();
//
//		AggregateIterable<Document> iterable = collection.aggregate(list);
//		
//		iterable.forEach (
//
//			new Block<Document>() {
//
//				@Override
//				public void apply(final Document document) {
//					results.add(document);
//				}
//			}
//		); // fin de iterable.forEach
//		
//		// prend l'item en indice 0, et la valeur(double) associee a la colonne "moyenne"
//		return results.get(0).getDouble("moyenne"); 
//	}
//	
//	/**
//	 * Cette méthode est appelée lorsqu'une partie se termine.
//	 * 
//	 * @param playerCards Les cartes choisies par l'usager
//	 * @param aiCards Les cartes choisies par l'IA (adversaire)
//	 * @param playerLife La vie restante du joueur à la fin de la partie
//	 * @param aiLife La vie restante de l'IA à la fin de la partie
//	 * @param totalRounds Le nombre de tours joués avant la fin de la partie
//	 */
//	public static void logGame(List<Card> playerCards, List<Card> aiCards, int playerLife, int aiLife, int totalRounds) {
//
//		try {
//
//			MongoCollection<Document> collection = 
//				DBConnection.getConnection()connection.getCollection("gameResults");
//			
//			//Calculer l'outcome
//			int outcome;
//			
//			if (playerLife > aiLife)
//			{
//				outcome = 1;
//			}
//			else if (playerLife < aiLife)
//			{
//				outcome = 2;
//			}
//			else
//			{
//				outcome = 0;
//			}
//
//			// Création d’une table
//			Document doc = new Document ();
//			 // Ajout de colonnes
//			doc.append("date", System.currentTimeMillis());
//			doc.append("vie_joueur", playerLife);			
//			doc.append("vie_AI", aiLife);
//			doc.append("nbRounds",totalRounds );
//			doc.append("outcome", outcome);			
//			
//			// Pour ajouter une liste de chaînes de caractères dans le document
//			List<String> playerCardsName = new ArrayList<String>();		// Création d’une liste de String
//			List<String> aiCardsName = new ArrayList<String>();		// Création d’une liste de String
//			
//			for (Card c: playerCards)
//			{
//				playerCardsName.add(c.getName());
//			}
//			
//			for (Card c: aiCards)
//			{
//				aiCardsName.add(c.getName());
//			}
//
//			doc.append("liste_cartes_joueur", playerCardsName);// Ajoute de colonne
//			doc.append("liste_ai", aiCardsName); // Ajout de colonne
//			
//			// Insertion dans la table enregistree, liee a notre db
//			collection.insertOne(doc);
//		}	
//		catch (Exception e) {
//			e.printStackTrace();
//		}	
//	}
//	
//	/**
//	 * Permet de savoir pour chaque carte combien de fois elle furent
//	 * présente lorsque l'usager a gagné.
//	 * 
//	 * @param collection de cartes du jeu
//	 * @return Une liste d'objets, où chaque object est similaire à : {"Wolf", 3}, 
//	 * ce qui signifie que la carte "Wolf" a été présente 3 fois dans les parties gagnées du joueur 
//	 */
//	public static List<Object[]> getCardRankings(List<Card> collection) {
//
//		List<Object[]> rankings = new ArrayList<Object[]>();
//		
//		for (Card c : collection) {
//			rankings.add(
//						new Object[]{c.getName(), getNumberOfWonGames(c)}
//					);
//		}
//		
//		return sortCardsByRanking(rankings);
//	}	
//	
//	/**
//	 * Méthode permettant de trier les objets en ordre décroissant
//	 * 
//	 * Exemple de liste valide:
//	 * rankings.get(0) contient : Object[]{"Wolf", 4}
//	 * rankings.get(1) contient : Object[]{"Dragon", 2}
//	 * rankings.get(2) contient : Object[]{"Swift", 5}
//	 * 
//	 * Ceci retournera
//	 * rankings.get(0) contient : Object[]{"Swift", 5}
//	 * rankings.get(1) contient : Object[]{"Wolf", 4}
//	 * rankings.get(2) contient : Object[]{"Dragon", 2}
//	 * 
//	 * @param rankings à trier
//	 * @return
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static List<Object[]> sortCardsByRanking(List<Object[]> rankings) {
//		Collections.sort(rankings, new Comparator() {
//
//			@Override
//			public int compare(Object o1, Object o2) {
//				int result = 0;
//				
//				if ((long)(((Object[])o1)[1]) < (long)(((Object[])o2)[1])) {
//					result = 1;
//				}
//				else if ((long)(((Object[])o1)[1]) > (long)(((Object[])o2)[1])) {
//					result = -1;
//				}
//				return result;
//			}
//			
//		});
//		
//		return rankings;
//	}
//}
