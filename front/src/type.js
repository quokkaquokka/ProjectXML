import config from './config'
import axios from "axios";


export class Type {
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
        const response = await axios.get('http://'+ config.host +'/type/');
        this.types = response.data.hits;
    }

    async addType() {
        var data = {
            name: this.name
        };
        const response = await axios.post('http://'+ config.host + '/type/', data);
        this.getTypes();
    }

    async updateType(objectID, name) {
        var data = {
            name: name,
            objectID: objectID
        };
        const response = await axios.put('http://'+ config.host + '/type/', data);

        document.getElementById(objectID.toString(10)).disabled = true;
        document.getElementById("updt" + objectID.toString(10)).style= "display: visible" ;
        document.getElementById("save" + objectID.toString(10)).style= "display: none";
        this.getTypes
    }

    async removeType(objectID){
        const response = await axios.delete('http://'+ config.host +'/type/' + objectID);
        this.getTypes();
    }

    changed(objectID)
    {
        document.getElementById(objectID.toString(10)).disabled = false;
        document.getElementById("updt" + objectID.toString(10)).style= "display: none";
        document.getElementById("save" + objectID.toString(10)).style= "display: visible";
    }
}
