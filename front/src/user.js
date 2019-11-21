import config from './config'
import axios from "axios";


export class User {
  heading = 'Welcome to the user page!';
    constructor() {
        this.users = null;
    }

    activate() {
        return this.getUsers();
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
          email: this.email
      };
      const response = await axios.post('http://'+ config.host + '/user/add/', data);
  
  }
}
