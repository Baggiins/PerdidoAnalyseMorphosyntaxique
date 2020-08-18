# Tokenizer Talismane

Permet de séparer les mots d'un texte passé en paramètre d'une requête POST. La valeur retourné est un texte _tokenisé_.

Le moteur de production utilisé est _Gradle_.
Les dépendances sont injectées dans le _build.gradle_.
La méthode de déploiement utilisé est _Docker_.

 
# Dépendence
Analyseur morpho-syntaxique Talismane.

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
 - POST (_ipmachine_:8080/tokenizerTalismane)
 - POST (_ipmachine_:8080/tokenizerTreeTaggerJson)

#### Corps des requêtes
**POST** _ipmachine_:8080/tokenizerTreeTagger : 
	
	{
    	"id":"1",
        "name":"test",
        "lang":"fr",
        "corpText":"Randonnée découverte d'un site d'abord, le Sanctuaire ..."
     }
    
**POST** _ipmachine_:8080/tokenizerTreeTaggerJson :

	{
    	"id":"1",
        "name":"test",
        "lang":"fr",
        "corpText":"Randonnée découverte d'un site d'abord, le Sanctuaire ..."
     }
     
 ### Réponses des requêtes
 
 **POST** _ipmachine_:8080/tokenizerTreeTagger : 
 	
    <request>
<id>1</id>
<name>test</name>
<lang>fr</lang>
<corpText>
1    Randonnée
2    découverte
3    d'
4    un
5    site
6    d'
7    abord
8    ,
9    le
10    Sanctuaire
11    ...

</corpText>
</request>
	
  **POST** _ipmachine_:8080/tokenizerTreeTaggerJson :
  
  	{
    "id": "1",
    "corpText": "1\tRandonnée\n2\tdécouverte\n3\td'\n4\tun\n5\tsite\n6\td'\n7\tabord\n8\t,\n9\tle\n10\tSanctuaire\n11\t...\n\n",
    "name": "test",
    "lang": "fr"
}
    
   # Déploiement
   
   ### Exporter le projet
    
Pour générer un .war avec _Gradle_, à la racine du projet il sera générer dans build/libs:
		
      gradle bootWar
      
      
### Docker
Arborescence du dossier 

 - DockerFile
 - TokenizeTreeTagger.war
 - /Talismane

Le _DockerFile_ est décrit dans le dossier Docker.

Créer une image Docker à la racine du dossier et la publier sur _DockerHub_ : 

	docker build -t name:tagname .
    docker tag name:tagname user/repository:tagname
    docker push user/repository:tagname

Exemple :
	
    docker build -t tokenize-tree-tagger:2.0 .
    docker tag tokenize-tree-tagger:2.0 dpham1/perdido:tokenize-tree-tagger
    docker push dpham1/perdido:tokenize-tree-tagger

Instancier un conteneur _Docker_ en le liant à un port de la machine. On souhaite rediriger les requêtes sur le conteneur :
 
 - Image locale

    	docker run -p portmachine:portconteneur name:tagname
    
    
	Exemple : 

		docker run -p 82:8080 tokenize-tree-tagger:2.0
    
    
 - Image distante

    	docker run -p portmachine:portconteneur user/repository:tagname
    
    
	Exemple : 

		docker run -p 82:8080 dpham1/perdido:tokenize-tree-tagger
    
 
	
Désormais les requêtes s'effectuent sur l'adresse de la machine qui a instancié le conteneur. La machine est en écoute sur le port 82 et redirige les requêtes sur le conteneur.
