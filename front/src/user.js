import config from './config'
import axios from "axios";
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";

@inject(Router)
export class User {
  heading = 'List of the users';
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
      return true;
  }

    async getUsers() {
        const response = await axios.get('http://'+ config.host +'/user/');
        this.users = response.data.hits;
    }

    goUserMedias(objectID){
      
      this.router.navigateToRoute('userMedias', { objectID: objectID, isEdit: false});
    }
}
