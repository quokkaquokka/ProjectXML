import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';



@inject(Router)
export class Media {
  heading = 'media!';
    constructor() {
        this.media = null;
        this.publicator = null;
    }

    activate(params) {
        console.log(params.objectID)
        return this.getMedia(params.objectID);
    }

    async getMedia(objectID) {
        const response = await axios.get('http://'+ config.host +'/media/get/'+ objectID);
        this.media = response.data.hits[0];
        const responseUser = await axios.get('http://'+ config.host +'/user/get/'+ this.media.uid);
        this.publicator = responseUser.data.hits[0];
        console.log(responseUser.data);
    }
}