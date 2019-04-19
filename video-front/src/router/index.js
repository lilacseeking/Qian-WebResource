import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
const Home = (resolve) => {
  import('components/home/home').then((module) => {
    resolve(module)
  })
}
const Bought = (resolve) => {
  import('components/bought/bought').then((module) => {
    resolve(module)
  })
}
const PersonalCenter = (resolve) => {
  import('components/personal-center/personal-center').then((module) => {
    resolve(module)
  })
}
const mycourse = (resolve) => {
  import('components/personal-center/child/mycourse').then((module) => {
    resolve(module)
  })
}
const video = (resolve) => {
  import('components/videoStudy/video').then((module) => {
    resolve(module)
  })
}
// const mycourse = (resolve) => {
//   import('components/personal-center/mycourse').then((module) => {
//     resolve(module)
//   })
// }
const personalCC = (resolve) => {
  import('components/personal-center/child/personalCC').then((module) => {
    resolve(module)
  })
}
const SeeMore = (resolve) => {
  import('components/see-more/see-more').then((module) => {
    resolve(module)
  })
}
export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      component: Home
    },
    {
      path: '/bought',
      component: Bought
    },
    {
      path: '/personal-center',
      component: PersonalCenter,
      redirect: '/personal-center/mycourse',
      children: [
        {
          path: 'mycourse',
          component: mycourse
        },
        {
          path: 'personalCC',
          component: personalCC
        }
      ]
    },
    {
      path: '/allCourse',
      component: () => import('components/allCourse/allCourse')
    },
    {
      path: '/teacherRegister',
      component: () => import('components/loginRegister/teacherRegister')
    },
    {
      path: '/SearchCourse',
      component: () => import('components/searchCourse/SearchCourse')
    },
    {
      path: '/more',
      component: SeeMore
    },
    {
      path: '/video',
      component: video
    }
  ]
})
