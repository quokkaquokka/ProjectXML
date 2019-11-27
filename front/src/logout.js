import {PLATFORM} from 'aurelia-pal';
import { inject } from "aurelia-framework";
import {BindingSignaler} from 'aurelia-templating-resources';
import { Router } from "aurelia-router";

@inject(BindingSignaler, Router)
export class Logout {
  
  constructor(signaler, router){
    this.router = router;
    this.signaler = signaler;
  }

  activate() {
    localStorage.clear();
    this.signaler.signal('my-signal')
    this.router.navigate('medias');
  }
}