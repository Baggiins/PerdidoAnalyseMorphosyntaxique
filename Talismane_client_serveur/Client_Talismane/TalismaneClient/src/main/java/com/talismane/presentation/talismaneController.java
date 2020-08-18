package com.talismane.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.talismane.talismaneService;
import com.talismane.model.Request;


@CrossOrigin
@RestController
public class talismaneController {
	
	/**
	 * le service permettant l'appel des méthodes de traitement
	 */
	@Autowired
	private talismaneService service;
	
	/**
	 * le GET
	 * @return la liste des fichiers traités
	 */
	@CrossOrigin
	@GetMapping
	public List<Request>  getAllText() {
		//affichage des fichier déjà traité
		return service.getRequest();
		}
		
	/**
	 * Permet la transformation de notre objet au format(visuel) XML
	 * @param t objet de type Request
	 * @return revoie une string 
	 */
	public String returnPostXml (Request t) {
		String postXml = "";
		postXml = postXml + 
				"<request>" + "\n" +
					"<id>" + t.getId() + "</id>" + "\n" +
					"<name>" + t.getName() + "</name>" + "\n" +
					"<lang>" + t.getLang() + "</lang>" + "\n" +
					"<ipAddress>" + t.getIpAddress() + "</ipAddress>" + "\n" +
					"<port>" + t.getPort() + "</port>" + "\n" +
					"<corpText>\n" + t.getCorpText() + "</corpText>" + "\n" +
				"</request>" + "\n";		
		return (postXml);
	}
	
	/**
	 * le POST, rajoute un fichier dans la hashmap et le traite
	 * @param text : le nom du fichier
	 */
	@CrossOrigin
	@PostMapping("/clientTalismane")
    public String lancementTraitement(@RequestBody Request text) {
		//Lancement du traitement.
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
	@PostMapping("/clientTalismaneJson")
	public Request lancementTraitementClient(@RequestBody Request text) {
		service.executeScript(text);
		return text;
	}
	
	/**
	 * le DELETE
	 * @param text : le nom du fichier à supprimer
	 */
	@DeleteMapping
	public void deletebyname(@RequestBody String text) {
		//Suprimme un fichier et le supprime du hashmap
		service.delete(text);
	}
}
