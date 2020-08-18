package com.tokenize;

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
import com.tokenize.model.Request;

@Service
public class TokenizeTreeTaggerService {
	/**
	 *  Déclaration des variables
	 */
	@Autowired
	private static HashMap<String, Request> MapText = new HashMap<>();


	/**
	 * Méthode permettant l'affichage de tous les textes stockés
	 * dans la hashmap
	 * @return tout les fichiers stockes
	 */
	public List<Request> getRequest(){
		return MapText.values().stream().sorted((s1, s2) -> {
			return s1.getName().compareTo(s2.getName());
		}).collect(Collectors.toList());
	}
	
	/**
	 * Supression du fichier
	 * @param text : le nom du fichier
	 */
	public void delete(String text) {
		File fichier = new File(text);
		fichier.delete();
		MapText.remove(text);
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
	 * Permet la transformation d'un string en fichier .txt 
	 *  
	 * @param t objet type Request contenant id, name, lang et corpText
	 */
	public void stringToText (Request t) {		
		String path =t.getName()+".txt";
		try {
 			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
 			
 			//On marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
 			writer.write(t.getCorpText());
 			
 			//On peut utiliser plusieurs fois methode write
 			writer.flush();
 			
 			//Ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter
 			writer.close();
 		}
 		catch(IOException ioe){System.out.println("erreur : " + ioe );}
	}
	
	
	
	/**
	 * file to string
	 * 
	 * Permet la transformation d'un fichier .txt en un string 
	 */
	public String textToString () {		
		InputStreamReader flog = null;
        LineNumberReader llog = null;
        String myLine = null;
        String textModified = "";
        try{ 
        	flog = new InputStreamReader(new FileInputStream("sortie.txt") );
        	llog = new LineNumberReader(flog);
        	while ((myLine = llog.readLine()) != null) {
        		textModified = textModified + myLine + "\n";
        		} 
        	}catch (Exception e){ 
        		// Erreur lecture du fichier (fichier non existant, illisible, etc.)
        		System.err.println("Error : "+e.getMessage());
        		} 	
		return (textModified);
	}
	
	/**
	 * Fonction avec switch qui permet de déterminer quelle "abbreviation" il faut uttiliser
	 * dans la commande pour le tokeniseur, en fonction de la langue qui
	 * a été déterminé
	 * 
	 * @param t objet type Request contenant id, name, lang et corpText
	 * @return le fichier abbreviations à utiliser 
	 */
	public String detectionAbbreviations (Request t) {
		String choix = "";
		switch(t.getLang()) {
		case "fr":
			choix = "french-abbreviations";
	    break;
		case "es":
			choix = "spanish-abbreviations";
	    break;
		case "pt":
			choix = "portuguese-abbreviations";
	    break;
		case "it":
			choix = "italian-abbreviations";
	    break;
		case "ro":
			choix = "romanian-abbreviations";
	    break;
		case "ca":
			choix = "catalan-abbreviations";
	    break;
		case "de":
			choix = "german-abbreviations";
	    break;
		case "en":
			choix = "english-abbreviations";
	    break;
	    //Non mis en place ici car problème lors de la détection de ces langues
		/*case "":
			choix = "dutch-abbreviations";
	    break;
		case "":
			choix = "latin-abbreviations";
	    break;*/
		default:
			choix = "french-abbreviations";
		}
		return (choix);
	}
		
	/**
	 * Traitement
	 */
	public void executeScript(Request t) {
		//Récuperation du fichier abbreviations à utiliser
		String langAbbreviations = detectionAbbreviations(t);
		
		//String to file txt
		stringToText(t);
		
		//Traitement fichier
		//Commande bash pour execution du .perl et tokenisation du texte
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "perl utf8-tokenize.perl -a "+langAbbreviations+" -f "+t.getName()+".txt");
		try {
			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//file to string			 
		String textModified = textToString();
		
		//Modifier le contenu du cortText de t, par le texte tokenisé passé via un string 
		t.setCorpText(textModified);
		ajouterTexte(t);
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
