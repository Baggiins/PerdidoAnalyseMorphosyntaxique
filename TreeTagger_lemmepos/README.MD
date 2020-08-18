# Lemme/PosTagging TreeTagger

Permet d'étiqueté les mots d'un texte tokénisé passé en paramètre d'une requête POST avec TreeTagger. La valeur retourné est un texte étiqueté.

Le moteur de production utilisé est _Gradle_.
Les dépendances sont injectées dans le _build.gradle_.
La méthode de déploiement utilisé est _Docker_.

 
# Dépendence
Analyseur morpho-syntaxique _TreeTagger_

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
 - POST (_ipmachine_:8080/lemmePosTreeTagger)
 - POST (_ipmachine_:8080/lemmePosTreeTaggerJson)

#### Corps des requêtes
**POST** _ipmachine_:8080/lemmePosTreeTagger : 
	
	{
      "id": "1",
      "corpText": "Randonnée\ndécouverte\nd'\nun\nsite\nd'abord\n,\nle\nSanctuaire\n...\n",
      "name": "test",
      "lang": "fr"
	}
    
**POST** _ipmachine_:8080/lemmePosTreeTaggerJson :

	{
      "id": "1",
      "corpText": "Randonnée\ndécouverte\nd'\nun\nsite\nd'abord\n,\nle\nSanctuaire\n...\n",
      "name": "test",
      "lang": "fr"
	}
     
 ### Réponses des requêtes
 
 **POST** _ipmachine_:8080/tokenizerTreeTagger : 
 	
    <request>
    <id>1</id>
    <name>test</name>
    <lang>fr</lang>
    <corpText>
      Randonnée    NOM    randonnée
      découverte    VER:pper    découvrir
      d'    PRP    de
      un    DET:ART    un
      site    NOM    site
      d'abord    ADV    d'abord
      ,    PUN    ,
      le    DET:ART    le
      Sanctuaire    NAM    <unknown>
      ...    PUN    ...
    </corpText>
    </request>
	
  **POST** _ipmachine_:8080/tokenizerTreeTaggerJson :
  
  	{
    "id": "1",
    "corpText": "Randonnée\tNOM\trandonnée\ndécouverte\tVER:pper\tdécouvrir\nd'\tPRP\tde\nun\tDET:ART\tun\nsite\tNOM\tsite\nd'abord\tADV\td'abord\n,\tPUN\t,\nle\tDET:ART\tle\nSanctuaire\tNAM\t<unknown>\n...\tPUN\t...\n",
    "name": "test",
    "lang": "fr"
}
    
   # Déploiement
   
   ### Exporter le projet
    
Pour générer un .war avec _Gradle_, à la racine du projet il sera générer dans build/libs :
		
      gradle bootWar
      
      
### Docker
Arborescence du dossier 

 - DockerFile
 - LemmePosTreeTagger.war
 - tree-tagger
 - /parameter_Files

Le _DockerFile_ est décrit dans le dossier Docker.

Créer une image Docker à la racine du dossier et la publier sur _DockerHub_ : 

	docker build -t name:tagname .
    docker tag name:tagname user/repository:tagname
    docker push user/repository:tagname

Exemple :
	
    docker build -t lemmepos-tree-tagger:2.0 .
    docker tag lemmepos-tree-tagger:2.0 dpham1/perdido:lemmepos-tree-tagger
    docker push dpham1/perdido:lemmepos-tree-tagger

Instancier un conteneur _Docker_ en le liant à un port de la machine. On souhaite rediriger les requêtes sur le conteneur :
 
 - Image locale

    	docker run -p portmachine:portconteneur name:tagname
    
    
	Exemple : 

		docker run -p 83:8080 lemmepos-tree-tagger:2.0
    
    
 - Image distante

    	docker run -p portmachine:portconteneur user/repository:tagname
    
    
	Exemple : 

		docker run -p 83:8080 dpham1/perdido:lemmepos-tree-tagger
    
 
	
Désormais les requêtes s'effectuent sur l'adresse de la machine qui a instancié le conteneur. La machine est en écoute sur le port 83 et redirige les requêtes sur le conteneur.
