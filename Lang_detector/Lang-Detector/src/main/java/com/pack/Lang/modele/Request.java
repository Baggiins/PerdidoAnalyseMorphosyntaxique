package com.pack.Lang.modele;



import javax.xml.bind.annotation.XmlRootElement;

/**
 * Cette classe représente un fichier
 * @author baggins
 *
 */
@XmlRootElement
//public class LangAnalysis {
public class Request{
	
	//   DECLARATION VARIABLE 
	
	private String id;
	private String name;
	private String corpText;
	private String lang;

	//  GETTERS & SETTERS
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorpText() {
		return corpText;
	}

	public void setCorpText(String cont) {
		this.corpText = cont;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	//  METHODE DE TRAITEMENT
	
	/**
	 * Constructeur par défault, en principe, ne doit jamais être
	 * utilisé, car pas d'ouverture du fichier !!!
	 */
	public Request() {
	}
	
	public Request (String name) {
		setName(name);
	}
}
