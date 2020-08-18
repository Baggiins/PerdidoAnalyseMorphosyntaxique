# Lemme/PosTagging Talismane

Permet d'étiqueté les mots d'un texte tokénisé passé en paramètre d'une requête POST avec _Talismane_. La valeur retourné est un texte étiqueté.

Le moteur de production utilisé est _Gradle_.
Les dépendances sont injectées dans le _build.gradle_.
La méthode de déploiement utilisé est _Docker_.

 
# Dépendence
Analyseur morpho-syntaxique _Talismane_

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
 - POST (_ipmachine_:8080/lemmePosTalismane)
 - POST (_ipmachine_:8080/lemmePosTalismaneJson)

#### Corps des requêtes
**POST** _ipmachine_:8080/lemmePosTalismane : 
	
	{
      "id": "1",
      "corpText": "1\tRandonnée\n2\tdécouverte\n3\td'\n4\tun\n5\tsite\n6\td'\n7\tabord\n8\t,\n9\tle\n10\tSanctuaire\n11\t...\n\n",
      "name": "test",
      "lang": "fr"
	}
    
**POST** _ipmachine_:8080/lemmePosTalismaneJson :

	{
      "id": "1",
      "corpText": "1\tRandonnée\n2\tdécouverte\n3\td'\n4\tun\n5\tsite\n6\td'\n7\tabord\n8\t,\n9\tle\n10\tSanctuaire\n11\t...\n\n",
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
        1    Randonnée    randonner    VPP    VPP    n=s|g=f|t=K
        2    découverte    découvrir    VPP    VPP    n=s|g=f|t=K
        3    d'    de    P    P
        4    un    un    DET    DET    n=s|g=m
        5    site    site    NC    NC    n=s|g=m
        6    d'    de    P    P
        7    abord    abord    NC    NC    n=s|g=m
        8    ,    ,    PONCT    PONCT
        9    le    le    DET    DET    n=s|g=m
        10    Sanctuaire    _    NPP    NPP
        11    ...    ...    PONCT    PONCT

      </corpText>
    </request>
	
  **POST** _ipmachine_:8080/tokenizerTreeTaggerJson :
  
  	{
      "id": "1",
      "corpText": "1\tRandonnée\trandonner\tVPP\tVPP\tn=s|g=f|t=K\t\n2\tdécouverte\tdécouvrir\tVPP\tVPP\tn=s|g=f|t=K\t\n3\td'\tde\tP\tP\t\t\n4\tun\tun\tDET\tDET\tn=s|g=m\t\n5\tsite\tsite\tNC\tNC\tn=s|g=m\t\n6\td'\tde\tP\tP\t\t\n7\tabord\tabord\tNC\tNC\tn=s|g=m\t\n8\t,\t,\tPONCT\tPONCT\t\t\n9\tle\tle\tDET\tDET\tn=s|g=m\t\n10\tSanctuaire\t_\tNPP\tNPP\t\t\n11\t...\t...\tPONCT\tPONCT\t\t\n\n",
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
 - LemmePosTalismane.war
 - /Talismane

Le _DockerFile_ est décrit dans le dossier Docker.

Créer une image Docker à la racine du dossier et la publier sur _DockerHub_ : 

	docker build -t name:tagname .
    docker tag name:tagname user/repository:tagname
    docker push user/repository:tagname

Exemple :
	
    docker build -t lemmepos-talismane:2.0 .
    docker tag lemmepos-talismane:2.0 dpham1/perdido:lemmepos-talismane
    docker push dpham1/perdido:lemmepos-talismane

Instancier un conteneur _Docker_ en le liant à un port de la machine. On souhaite rediriger les requêtes sur le conteneur :
 
 - Image locale

    	docker run -p portmachine:portconteneur name:tagname
    
    
	Exemple : 

		docker run -p 82:8080 lemmepos-talismane:2.0
    
    
 - Image distante

    	docker run -p portmachine:portconteneur user/repository:tagname
    
    
	Exemple : 

		docker run -p 82:8080 dpham1/perdido:lemmepos-talismane
    
 
	
Désormais les requêtes s'effectuent sur l'adresse de la machine qui a instancié le conteneur. La machine est en écoute sur le port 82 et redirige les requêtes sur le conteneur.
