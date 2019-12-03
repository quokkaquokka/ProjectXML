import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';


@inject(Router)
export class MyProfil {
  heading = 'My Profile';
    constructor(router) {
        this.router = router;
        this.medias = null;
        this.user = null;
        this.types = null;

        this.isEdit = true;
        //this.delete=null;

        this.name = null;
        this.author = null;
        this.icon = null;
        this.keyWords = null;
        this.date = null;
        this.selectType = null;
    }

    activate() {
        this.getUser();

        //this.getMedias();
        return this.getTypes()
    }

    async getTypes() {
        const response = await axios.get('http://'+ config.host +'/type/');
        this.types = response.data.hits;
    }

    async getUser() {
        var objectID = localStorage.getItem("objectID");
        const response = await axios.get('http://'+ config.host +'/user/' + objectID);
        this.user = response.data.hits[0];
        this.getMedias();
    }

    async getMedias() {
        const response = await axios.get('http://'+ config.host +'/media/getAuthor/' + this.user.objectID);
        this.medias = response.data.hits;
    }

    goDetails(objectID) {
        this.router.navigateToRoute('media', { objectID: objectID, isEdit: true});
    }

    goDetailsUpdate(objectID) {
        this.router.navigateToRoute('media', { objectID: objectID, isEdit: false});
    }

    changed(objectID)
    {
        document.getElementById("updt").style= "display: none";
        document.getElementById("save").style= "display: visible";
        this.isEdit = !this.isEdit;
    }

    async updateUser() {
        var data = {
            name : this.user.name,
            firstname : this.user.firstname,
            email: this.user.email,
            address: this.user.address,
            postalcode: this.user.postalcode,
            city: this.user.city,
            objectID: this.user.objectID
        };


       const response = await axios.put('http://'+ config.host + '/user/', data);
        document.getElementById("updt").style= "display: visible" ;
        document.getElementById("save").style= "display: none";
        this.isEdit = !this.isEdit;
        this.getUser();
    }

    async addMedia() {
        var data = {
            name: this.name || " ",
            author : this.author || " ",
            icon: this.icon || " ",
            date: this.date || " ",
            type: this.selectType || " ",          
            uid: this.user.objectID,
            keyWords : this.keyWords || " "
        };
        const response = await axios.post('http://'+ config.host + '/media/', data);
        this.getMedias();
    }

    async deleteMedia(objectID){
      const response = await axios.delete('http://'+ config.host +'/media/'+ objectID);
      location.reload(); 
      this.getMedias();
    }
}
