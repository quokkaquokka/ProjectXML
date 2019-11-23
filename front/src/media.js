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
<<<<<<< HEAD
        this.isEdit = null;
    }

    activate(params) {
        // this.isEdit === true , si le user est le meme que celui qui a poster
        this.isEdit = params.isEdit;
        (params.isEdit === 'false' ? this.isEdit = false: this.isEdit = true) 
        return this.getMedia(params.objectID);
        
=======
		this.comments = null;//H
		//this.isEdit = true;//H
    }

    activate(params) {
        console.log(params.objectID)
		this.getComments();//H
        this.getMedia(params.objectID);
>>>>>>> 3e508ab048a6194c5130c516cf26922208cf8bf5
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
	
	async getComments() {//H
		const response = await axios.get('http://'+ config.host +'/comment/getAll/');
        this.comments = response.data.hits;
        console.log(this.comments);
	}
	
	clicked()//H
    {
        document.getElementById("commt").style= "display: none";
		this.isEdit = !this.isEdit;
	}
	
	async addComment() {//H
        var data = {
            commtext: this.commtext,
            grade : this.grade,
            uid: this.user.objectID
        };
        const response = await axios.post('http://'+ config.host + '/comment/add', data);

    }
}