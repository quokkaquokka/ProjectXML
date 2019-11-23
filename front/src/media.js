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
		this.comments = null;//H
		//this.isEdit = true;//H
    }

    activate(params) {
        console.log(params.objectID)
		this.getComments();//H
        this.getMedia(params.objectID);
    }

    async getMedia(objectID) {
        const response = await axios.get('http://'+ config.host +'/media/get/'+ objectID);
        this.media = response.data.hits[0];
        const responseUser = await axios.get('http://'+ config.host +'/user/get/'+ this.media.uid);
        this.publicator = responseUser.data.hits[0];
        console.log(responseUser.data);
    }
	
	async getComments() {//H
		const response = await axios.get('http://'+ config.host +'/comment/getAll/');
        this.comments = response.data.hits;
        console.log(this.comments);
	}
	
	clicked()//H
    {
		console.log("ADD comment");
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