
# Application Client


# Utilisation
Cette application prend un texte, un nom et un id en entrée et traite le texte selont les option voulu : 
 - detection de la langue
 - tokenisation du texte
 - lemmatisation/etiquetage du texte
 
Les deux dernier points peuvent être réalisé avec TreeTagger ou avec Talismane.
Le nom du texte est optionnel.
Il est aussi possible d'effectuer les traitements sur le contenu d 'un fichier texte (par "drag and drop" ou par recherche dans l’arborescence de fichier).

## Detection de la langue
La detection de la langue n'a besoin que d'un texte et d'un ID pour fonctionner.

## Lemmatisation/Etiquetage et Tokenisation
Pour pouvoir utilisé ces traitements, il est __obligatoire__ précisé la langue du texte avant d'effectuer le traitement avec la selection de langue proposé.
## Talismane en mode client/serveur
Il est possible d'effectué les traitement avec talismane en mode client/serveur.
Pour se faire, il faut rentré dans le code du client (dans "AnalyseTali.vue" et "AnalyseTaliUp.vue") l'adresse du client de talismane.
Puis, depuis le formulaire des ces pages, il faut préciser l'adresse et le port du conteneur du serveur Talismane voulu.

# Adresse des services
Pour fonctionné correctement, il est nécessaire de précisé l'adresse IP et le port des différents services qui seront déployé depuis les conteneurs docker ou directement sur la machine à cet endroit du code (dans _"AnalyseurSyntaxique.vue"_) :


	export default {  
	data(){  
    return {  
      selected: '',  
      request : [],  
      recuDetectorLang : '',  
      recuTokenizer : '',  
      recuLemmePos : '',  
      httpD:'http://10.8.19.63:81', //Adresse ip du conteneur de l'application de detection de la langue  
      httpTTr:'http://10.8.18.83:81', //Adresse ip du conteneur de l'application de tokenisation avec Tree-Tagger  
      httpLTr:'http://10.8.19.63:82', //Adresse ip du conteneur de l'application de lemmatisation avec Tree-Tagger 
      httpTTa:'http://10.8.18.83:82', //Adresse ip du conteneur de l'application de tokenisation avec Talismane  
      httpLTa:'http://10.8.19.63:83' //Adresse ip du conteneur de l'application de lemmatisation avec Talismane  
      }  
      },
Une fois ceci fait correctement, l'application est prête à fonctionner correctement.
# Déploiement du client
L'exécution du client nécessite d'installer _npm_, qui est un gestionnaire de dépendance de javascript, avec la commande suivante :

	apt install npm
Il faut ensuite finalisé l'installation des dépendance du projet en se plaçant dans le répertoire du projet et en exécutant la commande suivante :

	npm install
Pour lancé le client, placez vous à la racine du répertoire et lancez la commandes suivante :

	npm run dev
Une fois le serveur lancé, le terminal vous indique normalement sur quelle adresse le client tourne (normalement sur localhost:8081) 

