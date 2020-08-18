package com.pack.Lang;


import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tika.language.LanguageIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.Lang.modele.Request;


/**
 * Classe de service
 * @author baggins
 *
 */
@SuppressWarnings("deprecation")
@Service
public class DetectionService {
	
	//  DECLARATION DES VARIABLES
	
	//la HashMap contient la liste des fichier passé en traitement
	@Autowired
	private static HashMap<String, Request> MapText = new HashMap<>();
	
	//      TRAITEMENT SUR LA HASHMAP
	
	/**
	 * Méthode permettant l'affichage de tout les textes stoqué
	 * dans la hashmap
	 * @return tout les fichier stoqué
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
		try {
			File fichier = new File(text);
			fichier.delete();
			MapText.remove(text);
		} catch (Exception e) {
			System.out.println("echec de la supression , exception :"
					+ e);
		}
	}
	
	/**
	 * méthode permettant de connaitre le nombre de fichier traité
	 * @return la taille du hashmap
	 */
	public int Nbtexte() {
		return MapText.size();
		}
	
	/**
	 * Méthode permattant d'obtenir un fichier stoqué dans le hashmap 
	 * par rapport à son nom
	 * @param id : le nom du fichier
	 * @return
	 */
	public Request getFichierbyid(String id) {
		return MapText.get(id);
	}
	
	public static void ajouterTexte(Request text) {
		MapText.put(text.getId(), text);
	}
	
	//  GUETTERS & SETTERS
	
	
	public static HashMap<String, Request> getMapfichier() {
		return MapText;
	}

	public static void setMapfichier(HashMap<String, Request> maptext) {
		MapText = maptext;
	}
	
	//   METHODE DE TRAITEMENT
	
	/**
	 * detection du language
	 * @param LangAnalysis nom du fichier texte
	 */
	@SuppressWarnings("static-access")
	public void detection(Request text) {
		//Fichiertxt fichier;
		try {					
			//enregistrement et affichage de la langue dans une variable lang
			text.setLang(identifyLanguage(text.getCorpText()));
			//Ajoute le fichier traité dans la Base
			ajouterTexte(text);
		} catch (Exception e) {
			System.out.println("fichier texte non trouvé");
	        e.printStackTrace();
		}
	}
	
	/**
	 * méthode de détection de tika
	 * @param text le contenue du fichier texte à traité
	 * @return le language du texte
	 */
	public static String identifyLanguage(String text) {
	    LanguageIdentifier identifier = new LanguageIdentifier(text);
	    return identifier.getLanguage();
	}
}
