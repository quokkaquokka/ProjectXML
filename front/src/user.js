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
}