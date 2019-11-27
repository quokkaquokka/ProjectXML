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
        title: 'medias',
        settings:{
          img: 'fas fa-icons',
          data: 'neutre'
        }
      },
      {
        route: ['myProfil'],
        name: 'myProfil',
        moduleId: PLATFORM.moduleName('./myProfil'),
        nav: true,
        title: 'myProfil',
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
        title: 'type',
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
        title: 'login',
        settings:{
          img: 'fas fa-sign-in-alt',
          data: 'false'
        }
      },
      {
        route: 'logout',
        name: 'Log out',
        moduleId: PLATFORM.moduleName('./logout'),
        nav: true,
        title: 'logout',
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
        title: 'media'
      },
      {
        route: ['userMedias'],
        name: 'userMedias',
        moduleId:PLATFORM.moduleName('./userMedias'),
        nav: false,
        title: 'userMedias'
      }
    ]);

    this.router = router;
  }
}
