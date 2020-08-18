<template>
  <div class="AnalyseTali">
    <label>Saisir un ID : <input type="text" v-model="request.id"/></label><br>
    <label>Saisir un nom : <input type="text" v-model="request.name"/></label><br>
    <label>Saisir l'IP du serveur Talismane :<input type="text" v-model="request.ipAddress"/></label><br>
    <label>Saisir le port du serveur Talismane :<input type="text" v-model="request.port"/></label><br>
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
    <h3>Analyse</h3>
    <button @click='validTextTokenT(request)'> Tokenisation du texte </button><br>
    <button @click='validTextTokenT(request)'> Analyse Globale </button>
    <h2>Langue du texte :</h2>
    <span>{{recuDetectorLang.lang}}</span>
    <h2>Résultat du traitement :</h2>
    <textarea rows="20" cols="50">{{recuTokenizer.corpText}}</textarea>
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
        httpTTa:'http://localhost:83',  //Adresse ip du conteneur de l'application cliente de Talismane
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
        axios.post(_this.httpTTa +'/clientTalismaneJson',{
          "id":request.id,
          "lang":lang,
          "name":request.name,
          "ipAddress":request.ipAddress,
          "port":request.port,
          "corpText":_this.request.corpText
        }).then(reponse => {console.log(_this.request.corpText);console.log(reponse); _this.recuTokenizer=reponse.data})
          .catch(erreur => console.log("Attention, erreur au niveau du tokeniseur:" + erreur))
      }
    }
  }
</script>

<style scoped>

</style>
