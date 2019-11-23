import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';



@inject(Router)
export class Media {
  heading = 'media!';
    constructor() {
		this.nComment = '';
        this.media = null;
        this.publicator = null;
        this.isEdit = null;
    }

    activate(params) {
        // this.isEdit === true , si le user est le meme que celui qui a poster
        this.isEdit = true;
        (params.isEdit === 'false' ? this.isEdit = true: this.isEdit = false) 
        this.getMedia(params.objectID);
        this.comments = null;
        return this.getComments(params.objectID);
		
    }

    async getMedia(objectID) {
        const response = await axios.get('http://'+ config.host +'/media/get/'+ objectID);
        this.media = response.data.hits[0];
        const responseUser = await axios.get('http://'+ config.host +'/user/get/'+ this.media.uid);
        this.publicator = responseUser.data.hits[0];
    }

    async updateMedia() {
        var keywords = (this.media.keyWords || []).join(',');
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
	
	async getComments(mediaID) {
        console.log("yuiolkj");
		const response = await axios.get('http://'+ config.host +'/comment/get/' + mediaID);
        this.comments = response.data.hits;
        console.log(response);
	}
	
	clicked()
    {
		console.log("ADD comment");
        document.getElementById("commt").style= "display: none";
		this.isEdit = !this.isEdit;
	}
	
	async addComment() {
		console.log("ADD comment");
        var data = {
			mediaID: this.media.objectID, 
            text: document.getElementById("commt").value,
            grade: "5",
            publisherID: "594439792"
        };
        const response = await axios.post('http://'+ config.host + '/comment/add', data);
    }
}