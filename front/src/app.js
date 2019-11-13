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
        route: ['media'],
        name: 'media',
        moduleId: PLATFORM.moduleName('./media'),
        nav: true,
        title: 'media'
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
      }
    ]);

    this.router = router;
  }
}
