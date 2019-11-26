import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';


@inject(Router)
export class MyProfil {
  heading = 'List of media!';
    constructor(router) {
        this.router = router;
        this.medias = null;
        this.user = null;
        this.types = null;
        
        
        this.isEdit = true;
        this.delete=null;

        this.name = null;
        this.author = null;
        this.icon = null;
        this.keyWords = null;
        this.date = null;
        this.selectType = null;
    }

    activate() {
        this.getMedias();
        this.getUser();
        this.getTypes()
    }

    deactivate(){
        this.medias = null;
        this.user = null;
        this.types = null;


        this.name = null;
        this.author = null;
        this.icon = null;
        this.keyWords = null;
        this.date = null;
        this.selectType = null;
    }

    async getTypes() {
        const response = await axios.get('http://'+ config.host +'/type/getAll/');
        this.types = response.data.hits;
        console.log(this.types);

    }

    async getUser() {
        // ATTTENTION LE USER EST CHARGER A LA MAIN
        const response = await axios.get('http://'+ config.host +'/user/get/' + '594439792');
        this.user = response.data.hits[0];
        console.log(this.user);
    }

    async getMedias() {
        const response = await axios.get('http://'+ config.host +'/media/getAll/');
        this.medias = response.data.hits;
        console.log(this.medias);
    }

    goDetails(objectID) {
        this.router.navigateToRoute('media', { objectID: objectID, isEdit: false});
    }

    goDetailsUpdate(objectID) {
        this.router.navigateToRoute('media', { objectID: objectID, isEdit: true});
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


       const response = await axios.post('http://'+ config.host + '/user/update', data);
        document.getElementById("updt").style= "display: visible" ;
        document.getElementById("save").style= "display: none";
        this.isEdit = !this.isEdit;
        this.deactivate();
        this.activate();
    }

    async addMedia() {
        var data = {
            name: this.name,
            author : this.author,
            icon: this.icon,
            keyWords : this.keyWords,
            date: this.date,
            type: this.selectType,
            uid: this.user.objectID
        };
        const response = await axios.post('http://'+ config.host + '/media/add', data);
        this.deactivate();
        this.activate();
    }

    async deleteMedia(objectID){
      const response = await axios.get('http://'+ config.host +'/media/delete/'+ objectID);
      this.delete = response.data;
      console.log(this.delete);
      this.deactivate();
      this.activate();
    }
}
