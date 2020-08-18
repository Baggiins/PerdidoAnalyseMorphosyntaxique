import Vue from 'vue'
import Router from 'vue-router'
import Navigate from '../components/Navigate'
import AnalyseurSyntaxique from '../components/AnalyseurSyntaxique'
import AnalyseurUpload from '../components/AnalyseurUpload'
import AnalyseNav from '../components/AnalyseNav'
import AnalyseTali from '../components/AnalyseTali'
import AnalyseTaliUp from '../components/AnalyseTaliUp'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Navigate',
      component: Navigate
    },
    {
      path: '/AnalyseurSyntaxique',
      name: AnalyseurSyntaxique,
      component:AnalyseurSyntaxique
    },
    {
      path: '/AnalyseurUpload',
      name: AnalyseurUpload,
      component:AnalyseurUpload
    },
    {
      path: '/AnalyseNav',
      name: AnalyseNav,
      component:AnalyseNav
    },
    {
      path: '/AnalyseTali',
      name: AnalyseTali,
      component:AnalyseTali
    },
    {
      path:'/AnalyseTaliUp',
      name: AnalyseTaliUp,
      component: AnalyseTaliUp
    }
  ]
})
