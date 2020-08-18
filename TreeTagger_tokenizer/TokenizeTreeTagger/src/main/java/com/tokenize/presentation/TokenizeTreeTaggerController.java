package com.tokenize.presentation;
import com.tokenize.TokenizeTreeTaggerService;
import com.tokenize.model.Request;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Autorise les requêtes 
@CrossOrigin
@RestController
public class TokenizeTreeTaggerController {
	
	/**
	 * Le service permettant l'appel des méthodes de traitement
	 */
	@Autowired
	private TokenizeTreeTaggerService service;
	
	/**
	 * le GET
	 * @return la liste des fichiers traités
	 */
	@CrossOrigin
	@GetMapping
	public List<Request>  getAllText() {
		//Affichage des fichier déjà traité
		return service.getRequest();
		}
		
	/**
	 * Permet la transformation de notre objet au format(visuel) XML
	 * @param t objet de type Request
	 * @return revoie une String 
	 */
	public String returnPostXml (Request t) {
		String postXml = "";
		postXml = postXml + 
				"<request>" + "\n" +
					"<id>" + t.getId() + "</id>" + "\n" +
					"<name>" + t.getName() + "</name>" + "\n" +
					"<lang>" + t.getLang() + "</lang>" + "\n" +
					"<corpText>\n" + t.getCorpText() + "</corpText>" + "\n" +
				"</request>" + "\n";		
		return (postXml);
	}
	
	/**
	 * le POST, rajoute un fichier dans la hashmap et le traite
	 * @param text : le nom du fichier
	 */
	@CrossOrigin
	@PostMapping("/tokenizerTreeTagger")
    public String lancementTokenize(@RequestBody Request text) {
		//Lancement de la tokenisation.
		service.executeScript(text);	
		String visuelXml = returnPostXml(text);
		return(visuelXml);
    }
	
	/**
	 * Le POST pour le client
	 * @param text
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/tokenizerTreeTaggerJson")
	public Request lancementTokenizeClient(@RequestBody Request text) {
		service.executeScript(text);
		return text;
	}
	
	/**
	 * le DELETE
	 * @param text : le nom du fichier à supprimer
	 */
	@DeleteMapping
	public void deletebyname(@RequestBody String text) {
		//Suprimme un fichier et le supprime de la hashmap
		service.delete(text);
	}
}
