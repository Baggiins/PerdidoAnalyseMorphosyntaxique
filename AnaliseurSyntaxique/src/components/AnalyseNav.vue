<template>
  <div>
    <h1> Choix de l'analyse : </h1>
    <ul>
      <li><router-link to="AnalyseurSyntaxique"> Saisie directe </router-link></li>
      <li><router-link to="AnalyseurUpload"> Upload un fichier </router-link></li>
      <li><router-link to="AnalyseTali"> Talismane mode client/serveur </router-link></li>
      <li><router-link to="AnalyseTaliUp"> Talismane mode client/serveur (upload de fichier) </router-link></li>
    </ul>
  </div>
</template>

<script>
    export default {
        name: "AnalyseNav"
    }
</script>

<style scoped>

</style>
