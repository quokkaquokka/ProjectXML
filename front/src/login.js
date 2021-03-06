import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import {BindingSignaler} from 'aurelia-templating-resources';
import config from './config';



@inject(Router, BindingSignaler)
export class Login {
    constructor(router, signaler) {
        this.email = null;
        this.password = null
        this.signaler = signaler;
        this.router = router;
    }

    async toLogin() {
        var data = {
            email : this.email,
            password : this.password
        }
        const response = await axios.post('http://'+ config.host +'/user/authentification', data);
        var user = response.data.hits[0];
        localStorage.setItem("email", user.email);
        localStorage.setItem("objectID", user.objectID);
        localStorage.setItem("name", user.name + ' ' + user.firstname);
        this.signaler.signal('my-signal');
        this.router.navigate('myProfil')
    }
}