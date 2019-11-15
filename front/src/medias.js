import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';

@inject(Router)
export class Medias {
  heading = 'List of media!';
    constructor(router) {
        this.medias = null;
        this.router = router;
    }

    activate() {
        return this.getMedias();
    }

    async getMedias() {
        const response = await axios.get('http://'+ config.host +'/media/getAll/');
        this.medias = response.data.hits;
        console.log(this.medias);
    }

    goDetails(objectID) {
        this.router.navigateToRoute('media', { objectID: objectID});
    }
}