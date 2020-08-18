package com.lemmepos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemmepos.model.Request;


@Service
public class LemmePosTreeTaggerService {
	/**
	 *  DECLARATION DES VARIABLES
	 */
	@Autowired
	private static HashMap<String, Request> MapText = new HashMap<>();

	/**
	 * Methode permettant l'affichage de tous les textes stockes
	 * dans la hashmap
	 * @return tout les fichiers stockes
	 */
	public List<Request> getRequest(){
		return MapText.values().stream().sorted((s1, s2) -> {
			return s1.getName().compareTo(s2.getName());
		}).collect(Collectors.toList());
	}
	
	/**
	 * supression du fichier
	 * @param text : le nom du fichier
	 */
	public void delete(String text) {
		File fichier = new File(text);
		fichier.delete();
		MapText.remove(text);
	}
	
	/**
	 * méthode permettant de connaitre le nombre de fichier traité
	 * @return la taille du hashmap
	 */
	public int Nbtexte() {
		return MapText.size();
		}
	
	public static void ajouterTexte(Request text) {
		MapText.put(text.getId(), text);
	}
	
	/**
	 * Méthode permattant d'obtenir un fichier stocké dans le hashmap 
	 * par rapport à son nom
	 * @param id : le nom du fichier
	 * @return
	 */
	public Request getTextById(String id) {
		return MapText.get(id);
	}

	/**
	 * String to file txt
	 * 
	 * Permet la transformation d'une string en fichier .txt 
	 *  
	 * @param t objet type Request contenant id, name, lang et corpText
	 */
	public void stringToText (Request t) {		
		String path =t.getName()+".txt";
		try {
 			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
 			//on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
 			writer.write(t.getCorpText());
 			//on peut utiliser plusieurs fois methode write
 			writer.flush();
 			//ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter
 			writer.close();
 		}
 		catch(IOException ioe){System.out.println("erreur : " + ioe );}
 		//on "catch" l exception ici si il y en a une, et on l affiche sur la console 	
	}
	
	
	/**
	 * file to string
	 * 
	 * Permet la transformation d'un fichier .txt en une string
	 */
	public String textToString (Request t) {		
		InputStreamReader flogToken = null;
		InputStreamReader flogLemma = null;
        LineNumberReader llogToken = null;
        LineNumberReader llogLemma = null;
        String myLineToken = null;
        String myLineLemma = null;
        String textModified = "";
        try{ 
        	flogToken = new InputStreamReader(new FileInputStream(t.getName() +".txt"));
        	flogLemma = new InputStreamReader(new FileInputStream("sortie.txt") );
        	llogToken = new LineNumberReader(flogToken);
        	llogLemma = new LineNumberReader(flogLemma);

        	while (((myLineToken = llogToken.readLine()) != null) && ((myLineLemma = llogLemma.readLine()) != null) ) {
        		textModified = textModified + myLineToken + "\t" + myLineLemma + "\n";
        		} 
        	}catch (Exception e){ 
        		// --- Gestion erreur lecture du fichier (fichier non existant, illisible, etc.)
        		System.err.println("Error : "+e.getMessage());
        		} 	
		return (textModified);
	}
	
	
	/**
	 * Fonction avec switch qui permet de déterminer quel "Parameter Files" il faut uttiliser
	 * dans la commande pour le lemme et POS, en fonction de la langue qui
	 * a été déterminé
	 * 
	 * @param t objet type Request contenant id, name, lang et corpText
	 * @return le fichier Parameter File à utiliser 
	 */
	public String detectionParameterFile (Request t) {
		String choix = "";
		switch(t.getLang()) {
		case "fr":
			choix = "french.par";
	    break;
		case "es":
			choix = "spanish.par";
	    break;
		case "pt":
			choix = "portuguese.par";
	    break;
		case "it":
			choix = "italian.par";
	    break;
		case "ro":
			choix = "romanian.par";
	    break;
		case "ca":
			choix = "catalan.par";
	    break;
		case "de":
			choix = "german.par";
	    break;
		case "en":
			choix = "english.par";
			//autre version de parameter File possible
			//langParameterFile = "english-bnc.par";
	    break;
	    //Non mis en place ici car problème lors de la détection de ces langues
		/*case "":
			choix = "dutch.par";
	    break;
		case "":
			choix = "latin.par";
	    break;*/
		default:
		  choix = "french.par";
		}	
		return (choix);
	}

	/**
	 * Traitement
	 */
	public void executeScript(Request t) {
		//Recuperation du fichier Parameter File à utiliser
		String langParameterFile = detectionParameterFile(t);
		
		//String to file txt
		stringToText(t);
		
		//traitement fichier
		ProcessBuilder processBuilder = new ProcessBuilder();
		
		//commande bash pour execution du .perl et lemme/POS du texte
		processBuilder.command("bash", "-c", "./tree-tagger -lemma -sgml "+langParameterFile+" "+t.getName()+".txt" + " > sortie.txt");
		try {
			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			
			//file to string			 
			String textModified = textToString(t);
			
			//Modifier le contenue du cortText de t, par le texte avec lemme et POS passé via une string 
			t.setCorpText(textModified);
			ajouterTexte(t);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
 	 
	/**
	 * GET SET
	 */
	public static HashMap<String, Request> getMapText() {
		return MapText;
	}

	public static void setMapText(HashMap<String, Request> mapText) {
		MapText = mapText;
	}
}
