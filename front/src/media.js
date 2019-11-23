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
        this.isEdit = null;
    }

    activate(params) {
        // this.isEdit === true , si le user est le meme que celui qui a poster
        this.isEdit = params.isEdit;
        (params.isEdit === 'false' ? this.isEdit = false: this.isEdit = true) 
        return this.getMedia(params.objectID);
        
    }

    async getMedia(objectID) {
        const response = await axios.get('http://'+ config.host +'/media/get/'+ objectID);
        this.media = response.data.hits[0];
        const responseUser = await axios.get('http://'+ config.host +'/user/get/'+ this.media.uid);
        this.publicator = responseUser.data.hits[0];
    }

    async updateMedia() {
        var keywords = (this.media.keyWords || []).join(',');
        //if(this.media.keyWords != null)
        //  keywords = this.media.keyWords.toString()
        var data = {
            name: this.media.name,
            objectID: this.media.objectID,
            author: this.media.author,
            date: this.media.date,
            uid: this.media.uid, 
            keyWords: keywords
        };

        const response = await axios.post('http://'+ config.host + '/media/update/', data);
        this.isEdit = !this.isEdit;
    }
}