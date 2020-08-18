package com.talismane;

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

import com.talismane.model.Request;

@Service
public class talismaneService {
	/**
	 *  Déclaration des variables
	 */
	@Autowired
	private static HashMap<String, Request> MapText = new HashMap<>();


	/**
	 * Méthode permettant l'affichage de tous les textes stockés
	 * dans la hashmap
	 * @return tout les fichiers stockés
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
	 * Méthode permattant d'obtenir un fichier stocké dans la hashmap 
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
			//On marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
 			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
 			
 			//On peut utiliser plusieurs fois méthode write
 			writer.write(t.getCorpText());
 			
 			//Ensuite flush envoie dans le fichier, ne pas oublier cette méthode pour le BufferedWriter
 			writer.flush();
 			
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
        		// Gestion erreur lecture du fichier (fichier non existant, illisible, etc.)
        		System.err.println("Error : "+e.getMessage());
        		} 	
		return (textModified);
	}
	
	/**
	 * Fonction avec switch qui permet de déterminer quel "Dconfig.file" il faut uttiliser
	 * dans la commande pour le tokeniseur, en fonction de la langue qui
	 * a été déterminé
	 * 
	 * @param t objet type Request contenant id, name, lang et corpText
	 * @return le fichier Dconfig.file à utiliser 
	 */
	public String detectionDconfigFile (Request t) {
		String choix = "";
		switch(t.getLang()) {
		case "fr":
			choix = "talismane-client.jar";
	    break;
	    //Non mis en place ici mais possibilité de rajouter l'anglais au besoin
		/*case "en":
			choix = "";
	    break;*/
		default:
			choix = "talismane-client.jar";
		}
		return (choix);
	}
	
	
	/**
	 * Traitement
	 */
	public void executeScript(Request t) {
		//Recuperation du fichier Dconfig.file à utiliser
		String langDconfigFile = detectionDconfigFile(t);

		//Traitement fichier
		//Commande bash pour execution du tokenizer du texte
		//L'addresse IP et le numero de port sont envoyer par l'aplication anglobante
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "echo \""+t.getCorpText()+"\" | java -jar "+langDconfigFile+" TalismaneClient "+t.getIpAddress()+" "+t.getPort()+" > sortie.txt");
		try {
			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			
			//file to string
			String textModified = textToString();

			//Modifier le contenue du cortText de t, par le texte tokenizer passer via une string 	
			t.setCorpText(textModified);
			ajouterTexte(t);
		} catch (IOException e) {e.printStackTrace();}		
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
