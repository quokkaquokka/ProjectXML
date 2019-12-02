import {PLATFORM} from 'aurelia-pal';

export class App {
  configureRouter(config, router) {
    config.title = 'Aurelia';
    config.map([
      {
        route: ['','medias'],
        name: 'medias',
        moduleId: PLATFORM.moduleName('./medias'),
        nav: true,
        title: 'Medias',
        settings:{
          img: 'fas fa-icons',
          data: 'neutre'
        }
      },
      {
        route: ['user'],
        name: 'user',
        moduleId: PLATFORM.moduleName('./user'),
        nav: true,
        title: 'Users',
        settings:{
          img: 'fas fa-users',
          data: 'true'
        }
      },
      {
        route: ['myProfil'],
        name: 'myProfil',
        moduleId: PLATFORM.moduleName('./myProfil'),
        nav: true,
        title: 'My Profile',
        settings:{
          img: 'fas fa-user-circle',
          data: 'true'
        }
      },
      {
        route: ['type'],
        name: 'type',
        moduleId: PLATFORM.moduleName('./type'),
        nav: true,
        title: 'Types',
        settings:{
          img: 'fas fa-cog',
          data: 'true'
        }
      },
      {
        route: ['login'],
        name: 'login',
        moduleId: PLATFORM.moduleName('./login'),
        nav: true,
        title: 'Log in',
        settings:{
          img: 'fas fa-sign-in-alt',
          data: 'false'
        }
      },
      {
        route: ['logout'],
        name: 'Log out',
        moduleId: PLATFORM.moduleName('./logout'),
        nav: true,
        title: 'Log out',
        settings:{
          img: 'fas fa-sign-out-alt',
          data: 'true'
        }
      },
      {
        route: ['media'],
        name: 'media',
        moduleId: PLATFORM.moduleName('./media'),
        nav: false,
        title: 'Media'
      },
      {
        route: ['userMedias'],
        name: 'userMedias',
        moduleId:PLATFORM.moduleName('./userMedias'),
        nav: false,
        title: 'User medias'
      },
      {
        route: ['signup'],
        name: 'signup',
        moduleId:PLATFORM.moduleName('./signup'),
        nav: true,
        title: 'Sign Up',
        settings:{
          img: 'fas fa-user-plus',
          data: 'false'
        }
      }
    ]);

    this.router = router;
  }
}
