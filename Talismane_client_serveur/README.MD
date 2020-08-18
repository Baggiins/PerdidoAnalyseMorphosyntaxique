#
_Talismane_ peut fonctionner en mode Client/Seveur afin d'accélérer les traitements. Dans la version initiale ( Talismane_lemmepos et Talismane_tokenizer ) la requête POST exécute une commande type bash pour charger Talismane et effectuer le traitement. 


Dans cette version la requête POST exécute une commande type bash pour demander au serveur _Talismane_ un traitement. On supprime donc la phase de chargement de _Talismane_ pour accelérer le traitement, mais on instancie un conteneur suplémentaire.

Le conteneur suplémentaire correspond au serveur Talismane. C'est à l'initialisation du serveur que l'on décrit son comportement, c'est pourquoi nous proposons 6 images _Docker_ :
 
  - Tokenizer fr
  - Tokenizer en
  - Lemme/Pos fr
  - Lemme/Pos en
  - Global fr
  - Global en


#
Le client _Talismane_ (/Client_Talismane) réagit à une requête POST.Lle corps de la requête est la suivante :

	{
    	"id":"1",
        "name":"test",
        "lang":"fr",
        "corpText":"Randonnée découverte d'un site d'abord, le Sanctuaire ...",
        "port":"82",
        "ipAddress":"192.168.24.1"
     }
     
On ajoute désormais l'adresse et le port sur lequel tourne le serveur Taslimane.
Le même Client est utilisé pour tous les types de traitement car c'est le serveur qui possède un comportement unique. 


### Modification du comportement du client

Le client est initialement implémenté dans :
	
    talismane-5.2.0/talismane_examples/src/main/java/com/joliciel/talismane/examples/TalismaneClient.java
    
Le client affichait la réponse du serveur de la manière suivante :

	Client: Randonnée découverte d'un site d'abord, le Sanctuaire ...
	Sending input to server: 
    		Randonnée découverte d'un site d'abord, le Sanctuaire ...
            
    Server: 1	Randonnée
    Server: 2	d'
    Server:	3	un
    Server:	4	site
    Server:	5	d'abord
    Server:	6	,
    Server:	7	le
    Server:	8	Sanctuaire
    Server:	9	...
    
 Pour modifier son comportement il suffit d'importer le projet (Maven), d'effectuer les modification et de l'exporter au format jar (Maven -> package ) 

