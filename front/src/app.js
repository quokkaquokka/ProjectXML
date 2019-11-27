import {PLATFORM} from 'aurelia-pal';

export class App {
  configureRouter(config, router) {
    config.title = 'Aurelia';
    config.map([
      {
        route: ['', 'welcome'],
        name: 'welcome',
        moduleId: PLATFORM.moduleName('./welcome'),
        nav: true,
        title: 'welcome'
      },
      {
        route: ['medias'],
        name: 'medias',
        moduleId: PLATFORM.moduleName('./medias'),
        nav: true,
        title: 'medias'
      },
      {
        route: ['user'],
        name: 'user',
        moduleId: PLATFORM.moduleName('./user'),
        nav: true,
        title: 'user'
      },
      {
        route: ['myProfil'],
        name: 'myProfil',
        moduleId: PLATFORM.moduleName('./myProfil'),
        nav: true,
        title: 'myProfil'
      },
      {
        route: ['type'],
        name: 'type',
        moduleId: PLATFORM.moduleName('./type'),
        nav: true,
        title: 'type'
      },
      {
        route: ['login'],
        name: 'login',
        moduleId: PLATFORM.moduleName('./login'),
        nav: true,
        title: 'login'
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
