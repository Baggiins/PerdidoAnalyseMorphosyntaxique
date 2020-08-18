# Perdido analyse Morphosyntaxique

## Présentation

Ce dépôt contient les différents du modules du prétraitement :


* Détection de langue
* Tokenizer Talismane
* Tokenizer Tree Tagger
* Lemmatizer / Pos Tagger Talismane
* Lemmatizer / Pos Tagger Tree Tagger
* Et un client pour faciliter les requêtes (AnalyserSyntaxique)


Chaque dossier comporte :
* l'application JAVA
* un dossier complet pour créer une image Docker
* un readme du détail d'utilisation

## Structure

Le dossier /Talismane_lemmepos_client et /Talismane_lemmepos_serveur est une ébauche non fonctionnelle d'un module de _lemmatisation_ et _postagging_ de Talismane en mode Client/Serveur. 


Le comportement du serveur est décrit à l'initialisation (_startModule_ _endModule_).

Le serveur Talismane est lancé avec la commande suivante : 

	java -Xmx1G -Dconfig.file=talismane-fr-5.2.0.conf -jar talismane-core-5.2.0.jar --analyse --startModule=postagger --endModule=postagger --sessionId=fr --mode=server --encoding=UTF8 

Le client Talismane est lancé avec la commande suivante : 

	java -jar talismane-examples-5.2.0.jar TalismaneClient localhost 7272 


Lorsque les argumenents _startModule_ et _endModule_ sont positionnés sur _"tokeniser"_ cela fonctionne, le serveur renvoie en réponse un texte "tokenisé". 


Lorsque l'argumenent _startModule_ est positionné sur "_tokeniser_" et l'argumenent _endModule_ est égale à "_postagger_", le serveur renvoie en réponse un texte étiqueté. 


Mais si on souhaite isoler les traitements et n'effectuer que le _postagging_, le serveur ne réponds jamais et le client ne réagit plus. 

La commande suivante n'a aucun effet : 

	echo "1\tCe\n2\tcircuit\n3\ttout\n4\tprès\n5\tde\n6\tPau\n7\toffre\n\n" | java -jar talismane-examples-5.2.0.jar TalismaneClient localhost 7272 


L'arrêt brutal du client engendre une console log du serveur : 

	18:06:30.259 [TalismaneServerThread] DEBUG com.joliciel.talismane.corpus.TokenPerLineCorpusReader - sentenceCount: 1
    18:06:30.261 [TalismaneServerThread] DEBUG com.joliciel.talismane.Talismane - Total time for Talismane.process(): 24215
    18:06:30.261 [TalismaneServerThread] ERROR com.joliciel.talismane.TalismaneServerThread - Didn't match pattern "%INDEX%	%TOKEN%". Compiled to: "(\d+)	(.*?)". On line 1: 
               $1	Ce
    com.joliciel.talismane.TalismaneException: Didn't match pattern "%INDEX%	%TOKEN%". Compiled to: "(\d+)	(.*?)". On line 1: 
                                                       $1	Ce
        at com.joliciel.talismane.corpus.CorpusLineReader.read(CorpusLineReader.java:138)
        at com.joliciel.talismane.corpus.TokenPerLineCorpusReader.hasNextSentence(TokenPerLineCorpusReader.java:244)
        at com.joliciel.talismane.Talismane.analyse(Talismane.java:427)
        at com.joliciel.talismane.TalismaneServerThread.run(TalismaneServerThread.java:55)
    Exception in thread "TalismaneServerThread" java.lang.RuntimeException: com.joliciel.talismane.TalismaneException: Didn't match pattern "%INDEX%	%TOKEN%". Compiled to: "(\d+)	(.*?)". On line 1: 
                                               $1	Ce
        at com.joliciel.talismane.TalismaneServerThread.run(TalismaneServerThread.java:63)
    Caused by: com.joliciel.talismane.TalismaneException: Didn't match pattern "%INDEX%	%TOKEN%". Compiled to: "(\d+)	(.*?)". On line 1: 
                                                               $1	Ce
        at com.joliciel.talismane.corpus.CorpusLineReader.read(CorpusLineReader.java:138)
        at com.joliciel.talismane.corpus.TokenPerLineCorpusReader.hasNextSentence(TokenPerLineCorpusReader.java:244)
        at com.joliciel.talismane.Talismane.analyse(Talismane.java:427)
        at com.joliciel.talismane.TalismaneServerThread.run(TalismaneServerThread.java:55)



**Note** : L'exécution de le l'application client de Talismane par la commande (java -jar talismane-examples-5.2.0.jar TalismaneClient localhost 7272) donne la main à l'utilisateur pour qu'il rentre le texte à traiter sur la console. L'utilisation de la commande _echo_ permet de rentrer le texte à traiter et de lancer le client en une seule ligne de commande. 
 
**Piste de réflexion** : Lorsque le serveur effectue la tokenisation et la lemmatisation/postagging à la chaîne, le traitement se déroule normalement et le serveur renvoie un texte étiqueté. De plus la tokenisation seule fonctionne et renvoie une réponse dans le format :

	%INDEX%		%TOKEN%

Si l'envoie d'un texte sous ce format ne fonctionne pas pour le traitement de lemme/postagging alors la réponse du serveur suite à une tokenisation est différente de la chaîne de caractère qu'il créé au sein du serveur.


Le code source de Talismane se trouve à l'adresse suivante : 

	https://github.com/joliciel-informatique/talismane/releases

Le code du _tokenizer_ se trouve dans le package :

	/talismane_core/src/main/java/com/joliciel/talismane

Il faudrait repérer quelle variable correspond au résultat du traitement du _tokenizer_ et l'analyser pour respecter le format d'entré du postagger.
