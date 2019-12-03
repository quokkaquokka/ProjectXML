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
		this.firstname = '';
		this.address = '';
		this.postalcode = '';
		this.city = '';
        this.password = "";
        this.email = "";
    }

    async addUser() {
    	if (this.name !== "" && this.password !== "" && this.email !== "") {
			console.log("ADD " + this.name);
			var data = {
				name: this.name,
				firstname: this.firstname,
				address: this.address,
				postalcode: this.postalcode,
				city: this.city,
				email: this.email,
				password: this.password
			};
			const response = await axios.post('http://'+ config.host + '/user/add/', data);
			this.router.navigate('login');
		}
  }

}
