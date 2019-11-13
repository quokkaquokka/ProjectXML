import config from './config'
import axios from "axios";
import cors from 'cors';

export class Test {
  heading = 'Welcome to the user page!';
  constructor(){
    this.users = null;
}

activate() {
  return this.getUsers();
}

async getUsers() {
  //   'http://'+ config.host+'/employees/get/' + '577000232'



  const response = await axios.get('http://'+ config.host +'/employees/getAll/');
  this.users = response.data.hits;
  console.log(this.users);
}
}
