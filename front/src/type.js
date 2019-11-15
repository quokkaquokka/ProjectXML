import config from './config'
import axios from "axios";


export class User {
  heading = 'Welcome to the type page!';
    constructor() {
        this.types = null;
        this.name = "";
        this.isEdit = false;
    }

    activate() {
        return this.getTypes();
    }

    async getTypes() {
        const response = await axios.get('http://'+ config.host +'/type/getAll/');
        this.types = response.data.hits;
    }

    async addType() {
        console.log("ADD " + this.name);
        var data = {
            name: this.name
        };
        const response = await axios.post('http://'+ config.host + '/type/add/', data);
    
    }

    async updateType(objectID, name) {
        console.log("UPDATE " , objectID , name);
        var data = {
            name: name,
            objectID: objectID
        };


        const response = await axios.post('http://'+ config.host + '/type/update/', data);

        document.getElementById(objectID.toString(10)).disabled = true;
        document.getElementById("updt" + objectID.toString(10)).style= "display: visible" ;
        document.getElementById("save" + objectID.toString(10)).style= "display: none";
    }

    async removeType(objectID){
        // console.log("objectID ", objectID);
        const response = await axios.get('http://'+ config.host +'/type/delete/' + objectID);
        // console.log(response);
        
    }

    changed(objectID)
    {
        console.log(objectID, document.getElementById("updt596062982").style= "display: none")
        document.getElementById(objectID.toString(10)).disabled = false;
        document.getElementById("updt" + objectID.toString(10)).style= "display: none";
        document.getElementById("save" + objectID.toString(10)).style= "display: visible";
    }
}