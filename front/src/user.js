import config from './config'
import axios from "axios";
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";

@inject(Router)
export class User {
  heading = 'Welcome to the user page!';
    constructor(router) {
        this.users = null;
        this.router=router;
    }

    activate() {
        return this.getUsers();
    }

    async getSearch() {
      if (document.getElementById('searchBar').value == ""){
        this.activate();
        return true;
      }
      const response = await axios.get('http://'+ config.host +'/user/search/' + document.getElementById('searchBar').value);
      this.users = response.data.hits;
      console.log(this.users);
      return true;
  }

    async getUsers() {
        const response = await axios.get('http://'+ config.host +'/user/getAll/');
        this.users = response.data.hits;
    }

    async addUser() {
      console.log("ADD " + this.name);
      var data = {
          name: this.name,
          firstname: this.firstname,
          adress: this.adress,
          postalcode: this.postalcode,
          city: this.city,
          email: this.email,
          password: this.password
      };
      const response = await axios.post('http://'+ config.host + '/user/add/', data);
      this.getUsers();
  }

    goUserMedias(objectID){
      this.router.navigateToRoute('userMedias', { objectID: objectID});
    }
}
