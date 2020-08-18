<template>
  <div class="AnalyseurSyntaxique">
    <label>Saisir un ID : <input type="text" v-model="request.id"/></label><br>
    <label>Saisir un nom : <input type="text" v-model="request.name"/></label><br>
    <label>Saisir la langue du texte (obligatoire pour la tokenisation ou la lemmatisation/étiquetage seule):</label><br>
    <select v-model="selected">
      <option disabled value="">Langue</option>
      <option>French</option>
      <option>English</option>
      <option>Spanish</option>
      <option>Portuguese</option>
      <option>Italian</option>
      <option>Romanian</option>
      <option>Catalan</option>
      <option>German</option>
    </select>
    <span>Sélectionné : {{ selected }}</span>
    <h2>Saisir un texte :</h2><br>
    <textarea style="height:100px; width: 500px;" v-model="request.corpText" placeholder="Saisir le texte ici ..."></textarea><br>
    <button @click='validTextDetect(request)'> Detection de la langue </button><br>
    <h3>Analyse avec Tree-Tagger</h3>
    <button @click='validTextToken(request)'> Tokenisation du texte </button>
    <button @click='validTextLemma(request)'> Lem/Pos du texte </button><br>
    <button @click='validTextGlobal(request)'> Analyse Globale </button>
    <br>
    <h3>Analyse avec Talismane</h3>
    <button @click='validTextTokenT(request)'> Tokenisation du texte </button>
    <button @click='validTextLemmaT(request)'> Lem/Pos du texte </button><br>
    <button @click='validTextGlobalT(request)'> Analyse Globale </button>
    <h2>Langue du texte :</h2>
    <span>{{recuDetectorLang.lang}}</span>
    <h2>Tokenisation du texte :</h2>
    <textarea rows="20" cols="50">{{recuTokenizer.corpText}}</textarea>
    <h2>Lem/Pos du texte :</h2>
    <textarea rows="20" cols="60">{{recuLemmePos.corpText}}</textarea>
  </div>
</template>

<script>
  import axios from 'axios'
  import Vue from 'vue'
  import VueResource from 'vue-resource'
  Vue.use(VueResource);
    export default {
      data(){
        return {
          selected: '',
          request : [],
          recuDetectorLang : '',
          recuTokenizer : '',
          recuLemmePos : '',
          httpD:'http://localhost:81',    //Adresse ip du conteneur de l'application de detection de la langue
          httpTTr:'http://localhost:82',  //Adresse ip du conteneur de l'application de tokenisation avec Tree-Tagger
          httpLTr:'http://localhost:83',  //Adresse ip du conteneur de l'application de lemmatisation avec Tree-Tagger
          httpTTa:'http://localhost:84',  //Adresse ip du conteneur de l'application de tokenisation avec Talismane
          httpLTa:'http://localhost:85'   //Adresse ip du conteneur de l'application de lemmatisation avec Talismane
        }
      },
      methods : {
        validTextDetect(request){
          let _this=this;
          axios.post(_this.httpD +'/LangAnalysisJson',
            {
            "id":request.id,
            "name":request.name,
            "corpText":request.corpText
          }
          ).then(reponse => {console.log(reponse); _this.recuDetectorLang=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau du detecteur:" + erreur));
        },
        validTextToken(request){
          let lang = 'un';
          if (this.selected === 'French'){
            lang = 'fr';
          }
          if (this.selected === 'Spanish'){
            lang = 'es';
          }
          if (this.selected === 'Portuguese'){
            lang = 'pt';
          }
          if (this.selected === 'Italian'){
            lang = 'it';
          }
          if (this.selected === 'Romanian'){
            lang = 'ro';
          }
          if (this.selected === 'Catalan'){
            lang = 'ca';
          }
          if (this.selected === 'German'){
            lang = 'de';
          }
          if (this.selected === 'English'){
            lang = 'en';
          }
          let _this=this;
          axios.post(_this.httpTTr +'/tokenizerTreeTaggerJson',{
            "id":request.id,
            "lang":lang,
            "name":request.name,
            "corpText":request.corpText
          }).then(reponse => {console.log(reponse); _this.recuTokenizer=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau du tokeniseur:" + erreur))
        },
        validTextLemma(request){
          let lang = 'un';
          if (this.selected === 'French'){
            lang = 'fr';
          }
          if (this.selected === 'Spanish'){
            lang = 'es';
          }
          if (this.selected === 'Portuguese'){
            lang = 'pt';
          }
          if (this.selected === 'Italian'){
            lang = 'it';
          }
          if (this.selected === 'Romanian'){
            lang = 'ro';
          }
          if (this.selected === 'Catalan'){
            lang = 'ca';
          }
          if (this.selected === 'German'){
            lang = 'de';
          }
          if (this.selected === 'English'){
            lang = 'en';
          }
          let _this=this;
          axios.post(_this.httpLTr + '/lemmePosTreeTaggerJson',{
            "id":request.id,
            "lang":lang,
            "name":request.name,
            "corpText":request.corpText
          })
            .then(reponse => {console.log(reponse); _this.recuLemmePos=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau du lemmatiseur" + erreur))
        },
        async validTextGlobal(request){
          let _this=this;
          await axios.post(_this.httpD +'/LangAnalysisJson',
            {
              "id":request.id,
              "name":request.name,
              "corpText":request.corpText
            })
            .then(reponse => {console.log(reponse); _this.recuDetectorLang=reponse.data;})
            .catch(erreur => console.log("Attention, erreur au niveau de la detection du global" + erreur));
          await axios.post(_this.httpTTr +'/tokenizerTreeTaggerJson',
            {
              "id":_this.recuDetectorLang.id,
              "lang":_this.recuDetectorLang.lang,
              "name":_this.recuDetectorLang.name,
              "corpText":_this.recuDetectorLang.corpText
            })
            .then(reponse => {console.log(reponse); _this.recuTokenizer=reponse.data})
            .catch(erreur => console.log(_this.recu + "Attention, erreur au niveau de la tokenisation du global" + erreur));
          await axios.post(_this.httpLTr +'/lemmePosTreeTaggerJson',
            {
              "id":_this.recuTokenizer.id,
              "lang":_this.recuTokenizer.lang,
              "name":_this.recuTokenizer.name,
              "corpText":_this.recuTokenizer.corpText
            })
            .then(reponse => {console.log(reponse); _this.recuLemmePos=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau de la lemmatisation du global" + erreur));
        },
        validTextTokenT(request){
          let lang = 'un';
          if (this.selected === 'French'){
            lang = 'fr';
          }
          if (this.selected === 'Spanish'){
            lang = 'es';
          }
          if (this.selected === 'Portuguese'){
            lang = 'pt';
          }
          if (this.selected === 'Italian'){
            lang = 'it';
          }
          if (this.selected === 'Romanian'){
            lang = 'ro';
          }
          if (this.selected === 'Catalan'){
            lang = 'ca';
          }
          if (this.selected === 'German'){
            lang = 'de';
          }
          if (this.selected === 'English'){
            lang = 'en';
          }
          let _this=this;
          axios.post(_this.httpTTa +'/tokenizerTalismaneJson',{
            "id":request.id,
            "lang":lang,
            "name":request.name,
            "corpText":request.corpText
          }).then(reponse => {console.log(reponse); _this.recuTokenizer=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau du tokeniseur:" + erreur))
        },
        validTextLemmaT(request){
          let lang = 'un';
          if (this.selected === 'French'){
            lang = 'fr';
          }
          if (this.selected === 'Spanish'){
            lang = 'es';
          }
          if (this.selected === 'Portuguese'){
            lang = 'pt';
          }
          if (this.selected === 'Italian'){
            lang = 'it';
          }
          if (this.selected === 'Romanian'){
            lang = 'ro';
          }
          if (this.selected === 'Catalan'){
            lang = 'ca';
          }
          if (this.selected === 'German'){
            lang = 'de';
          }
          if (this.selected === 'English'){
            lang = 'en';
          }
          let _this=this;
          axios.post(_this.httpLTa +'/lemmePosTalismaneJson',{
            "id":request.id,
            "lang":lang,
            "name":request.name,
            "corpText":request.corpText
          })
            .then(reponse => {console.log(reponse); _this.recuLemmePos=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau du lemmatiseur" + erreur))
        },
        async validTextGlobalT(request){
          let _this=this;
          await axios.post(_this.httpD +'/LangAnalysisJson',
            {
              "id":request.id,
              "name":request.name,
              "corpText":request.corpText
            })
            .then(reponse => {console.log(reponse); _this.recuDetectorLang=reponse.data;})
            .catch(erreur => console.log("Attention, erreur au niveau de la detection du global" + erreur));
          await axios.post(_this.httpTTa +'/tokenizerTalismaneJson',
            {
              "id":_this.recuDetectorLang.id,
              "lang":_this.recuDetectorLang.lang,
              "name":_this.recuDetectorLang.name,
              "corpText":_this.recuDetectorLang.corpText
            })
            .then(reponse => {console.log(reponse); _this.recuTokenizer=reponse.data})
            .catch(erreur => console.log(_this.recu + "Attention, erreur au niveau de la tokenisation du global" + erreur));
          await axios.post(_this.httpLTa +'/lemmePosTalismaneJson',
            {
              "id":_this.recuTokenizer.id,
              "lang":_this.recuTokenizer.lang,
              "name":_this.recuTokenizer.name,
              "corpText":_this.recuTokenizer.corpText
            })
            .then(reponse => {console.log(reponse); _this.recuLemmePos=reponse.data})
            .catch(erreur => console.log("Attention, erreur au niveau de la lemmatisation du global" + erreur));
        }
      }
    }
</script>
<style scoped>
</style>
