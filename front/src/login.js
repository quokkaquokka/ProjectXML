import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';



@inject(Router)
export class Login {
    constructor() {
        this.email = null;
        this.password = null
    }

    async toLogin() {
        var data = {
            email : this.email,
            password : this.password
        }
        console.log(this.email, this.password)
        const response = await axios.post('http://'+ config.host +'/user/authentification', data);
        console.log(response);
        // set cookies pour la session
    }
}