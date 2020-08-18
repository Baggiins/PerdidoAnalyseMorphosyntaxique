package com.talismane.model;

//Objet servant aux transformations et aux envois 
public class Request {
	private String id;
	private String corpText;
	private String name;
	private String lang;
	private String port;
	private String ipAddress;

	public Request() {};
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorpText() {
		return corpText;
	}
	public void setCorpText(String corpText) {
		this.corpText = corpText;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
		
	public Request (String name) {
		setName(name);
		
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
