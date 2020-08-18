package com.pack.Lang.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.pack.Lang.DetectionService;
import com.pack.Lang.modele.Request;

/**
 * Controller de l'application
 * @author baggins
 *
 */
@CrossOrigin
@RestController
public class LangController {
	
	/**
	 * le service permettant l'appel des méthode de traitement
	 */
	@Autowired
	private DetectionService service;
	
	
	/**
	 * le GET
	 * @return la liste des fichiers traité
	 */
	@CrossOrigin
	@GetMapping
	public List<Request> getAllText() {
		//affichage des fichier déjà traité
		return service.getRequest()
;	}

	/**
	 * Permet la transformation de notre objet au format(visuel) XML
	 * @param t objet de type TextTokenized
	 * @return revoie une string 
	 */
	public String returnPostXml (Request t) {
		String postXml = "";
		postXml = postXml + 
				"<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>" + "\n" +
				"<request>" + "\n" +
					"<id>" + t.getId() + "</id>" + "\n" +
					"<name>" + t.getName() + "</name>" + "\n" +
					"<lang>" + t.getLang() + "</lang>" + "\n" +
					"<corpText>" + t.getCorpText() + "</corpText>" + "\n" +
				"</request>" ;
		return (postXml);
	}	
	
	
	/**
	 * le Post, rajoute un fichier dans la hashmap et le traite
	 * @param text : un fichier à traité (structure en JSON)
	 * @return un message de confirmation
	 */
	@CrossOrigin
	@PostMapping("/LangAnalysis")
    public String lancementDetect(@RequestBody Request text) {
		//Lancement de la detection.
		service.detection(text);
		String visuelXml = returnPostXml(text);
		return(visuelXml);
    }

	/**
	 * Le POST pour le client
	 * @param text
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/LangAnalysisJson")
	public Request lancementDetectJson(@RequestBody Request text) {
		service.detection(text);
		return text;
	}
	
	/**
	 * le DELETE
	 * @param text : le nom du fichier à supprimer
	 */
	@CrossOrigin
	@DeleteMapping
	public void deletebyname(@RequestBody String text) {
		//Suprimme un fichier et le supprime du hashmap
		service.delete(text);
	}
}
