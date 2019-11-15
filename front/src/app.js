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
        route: ['test'],
        name: 'test',
        moduleId: PLATFORM.moduleName('./test'),
        nav: true,
        title: 'test'
      },
      {
        route: ['user'],
        name: 'user',
        moduleId: PLATFORM.moduleName('./user'),
        nav: true,
        title: 'user'
      },
      {
        route: ['type'],
        name: 'type',
        moduleId: PLATFORM.moduleName('./type'),
        nav: true,
        title: 'type'
      },
      {
        route: ['media'],
        name: 'media',
        moduleId: PLATFORM.moduleName('./media'),
        nav: false,
        title: 'media'
      }
    ]);

    this.router = router;
  }
}
