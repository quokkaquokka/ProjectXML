import config from './config'
import axios from "axios";
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";

@inject(Router)
export class Signup {
  heading = 'Sign Up';
    constructor(router) {
        this.router=router;
        this.name = "";
        this.password = "";
        this.email = "";
    }

    async addUser() {
    	if (this.name !== "" && this.password !== "" && this.email !== "") {
			var data = {
				name: this.name,
				firstname: this.firstname,
				adress: this.adress,
				postalcode: this.postalcode,
				city: this.city,
				email: this.email,
				password: this.password
			};
			const response = await axios.post('http://'+ config.host + '/user/', data);
			this.router.navigate('login');
		}
  }

}
