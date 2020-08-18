# Détecteur de langue

Permet de détecter la langue d'un texte passé en paramètre d'une requête POST. La valeur retourné est une chaîne de 2 caractères.

Le moteur de production utilisé est _Gradle_.
Les dépendances sont injectées dans le _build.gradle_.
La méthode de déploiement utilisé est _Docker_.

 
# Dépendence
package apache.tika

# Usage
En ligne de commande à la racine du projet : 
 
 	 gradle bootRun
     
Le port d'écoute par défaut est le 8080.

Le port est spécifié dans :
	
    src/main/resources/application.properties
    server.port=8080
    
Le serveur est en écoute de requête sur le port 8080.

### Requête
 - GET (_ipmachine_:8080/)
 - POST (_ipmachine_:8080/langAnalysis)
 - POST (_ipmachine_:8080/langAnalysisJson)

#### Corps des requêtes
**POST** _ipmachine_:8080/langAnalysis : 
	
	<request>
      <id>1</id>
      <name>test</name>
      <corpText>
          Randonnée découverte d'un site d'abord, le Sanctuaire ...
      </corpText>
	</request>
    
**POST** _ipmachine_:8080/langAnalysisJson :

	{
    	"id":"1",
        "name":"test",
        "corpText":"Randonnée découverte d'un site d'abord, le Sanctuaire ..."
     }
     
 ### Réponses des requêtes
 
 	fr
	
    
   # Déploiement
   
   ### Exporter le projet
    
Pour générer un .war avec _Gradle_, à la racine du projet:
		
      gradle bootWar
      
      
### Docker
Arborescence du dossier 

 - DockerFile
 - lang_detector.war

Le _DockerFile_ est décrit dans le dossier Docker.

Créer une image Docker à la racine du dossier et la publier sur _DockerHub_ : 

	docker build -t name:tagname .
    docker tag name:tagname user/repository:tagname
    docker push user/repository:tagname

Exemple :
	
    docker build -t lang-detector:2.0 .
    docker tag lang-detector:2.0 dpham1/perdido:lang-detector
    docker push dpham1/perdido:lang-detector

Instancier un conteneur _Docker_ en le liant à un port de la machine. On souhaite rediriger les requêtes sur le conteneur :
 
 - Image locale

    	docker run -p portmachine:portconteneur name:tagname
    
    
	Exemple : 

		docker run -p 81:8080 lang-detector:2.0
    
    
 - Image distante

    	docker run -p portmachine:portconteneur user/repository:tagname
    
    
	Exemple : 

		docker run -p 81:8080 dpham1/perdido:lang-detector
    
 
	
Désormais les requêtes s'effectuent sur l'adresse de la machine qui a instancié le conteneur. La machine est en écoute sur le port 81 et redirige les requêtes sur le conteneur.
