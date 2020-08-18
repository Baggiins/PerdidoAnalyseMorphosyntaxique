<template>
  <div class="AnalyseTaliUp">
    <label>Saisir un ID : <input type="text" v-model="request.id"/></label><br>
    <label>Choisir un fichier :</label><br>
    <vue-dropzone ref="myVueDropzone" id="dropzone" :options="dropzoneOptions" @vdropzone-file-added="vfileAdded"></vue-dropzone>
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
    <h3>Analyse</h3>
    <button @click='validTextTokenT(request)'> Tokenisation du texte </button><br>
    <button @click='validTextTokenT(request)'> Analyse Globale </button>
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
  import vue2Dropzone from 'vue2-dropzone'
  import 'vue2-dropzone/dist/vue2Dropzone.min.css'
  Vue.use(VueResource);
  export default {
    components: {
      vueDropzone: vue2Dropzone,
      request : [],
    },
    data(){
      return {
        dropzoneOptions: {
          url: 'https://httpbin.org/post',
          thumbnailWidth: 150,
          maxFilesize: 0.5,
          headers: {"My-Awesome-Header": "header value"}
        },
        selected: '',
        request : [],
        recuDetectorLang : '',
        recuTokenizer : '',
        recuLemmePos : '',
        httpD:'http://localhost:81',    //Adresse ip du conteneur de l'application de detection de la langue
        httpTTa:'http://localhost:84',  //Adresse ip du conteneur de l'application cliente de Talismane
      }
    },
    methods : {
      vfileAdded(file){
        let _this = this;
        let reader = new FileReader();
        reader.onload = function(e) {
          _this.request.corpText = e.target.result;
          console.log(_this.request.corpText);
        };
        reader.readAsText(file);
        _this.request.name = file.name;
      },
      validTextDetect(request){
        let _this=this;
        axios.post(_this.httpD +'/LangAnalysisJson',
          {
            "id":request.id,
            "name":_this.request.name,
            "corpText":_this.request.corpText
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
          "name":_this.request.name,
          "corpText":_this.request.corpText,
          "ipAddress":request.ipAddress,
          "port":request.port
        }).then(reponse => {console.log(reponse); _this.recuTokenizer=reponse.data})
          .catch(erreur => console.log("Attention, erreur au niveau du tokeniseur:" + erreur))
      }
    }
  }
</script>

<style scoped>

</style>
