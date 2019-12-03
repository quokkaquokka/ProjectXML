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


        this.name = null;
        this.author = null;
        this.icon = null;
        this.keyWords = null;
        this.date = null;
        this.selectType = null;
    }

    activate(params) {
        this.getUser(params.objectID);
        return this.getTypes()
    }

    async getTypes() {
        const response = await axios.get('http://'+ config.host +'/All/');
        this.types = response.data.hits;

    }

    async getUser(objectID) {
        const response = await axios.get('http://'+ config.host +'/user/' + objectID);
        this.user = response.data.hits[0];
        this.getMedias();
    }

    async getMedias() {
        const response = await axios.get('http://'+ config.host +'/media/getAuthor/' + this.user.objectID);
        this.medias = response.data.hits;
    }

    goDetails(objectID) {
        this.router.navigateToRoute('media', { objectID: objectID, isEdit: false});
    }
}
