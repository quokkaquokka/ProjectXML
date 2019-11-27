import axios from "axios";
import {PLATFORM} from 'aurelia-pal';
import {inject}  from "aurelia-framework";
import {Router}  from "aurelia-router";
import config from './config';



@inject(Router)
export class Media {
    constructor() {
		this.nComment = '';
        this.media = null;
        this.publicator = null;
        this.isEdit = null;
        this.selectedType = null;
        this.updateKeyWords = null;
		this.stars = "0";
    }

    activate(params) {
        // this.isEdit === true , si le user est le meme que celui qui a poster
        this.isEdit = true;
        (params.isEdit === 'false' ? this.isEdit = true: this.isEdit = false) 
        this.getMedia(params.objectID);
        this.comments = null;
        
        this.getTypes();
        return this.getComments(params.objectID);
		
    }

    deactivate() {
      this.nComment = '';
      this.media = null;
      this.publicator = null;
      this.isEdit = null;
      this.selectedType = null;
      this.updateKeyWords = null;
      this.stars = "0";
    }

    async getMedia(objectID) {
        const response = await axios.get('http://'+ config.host +'/media/get/'+ objectID);
        this.media = response.data.hits[0];
        console.log(response)
        this.updateKeyWords = this.media.keyWords;
        this.updateKeyWords = (this.updateKeyWords || []).join(',');
        const responseUser = await axios.get('http://'+ config.host +'/user/get/'+ this.media.uid);
        this.publicator = responseUser.data.hits[0];
    }

    async getTypes() {
        const response = await axios.get('http://'+ config.host +'/type/getAll/');
        this.types = response.data.hits;
    }

    async getComments(mediaID) {
		const response = await axios.get('http://'+ config.host +'/comment/get/' + mediaID);
        this.comments = response.data.hits;
        console.log(response);
	}

    async updateMedia() {
        // var keywords = (this.media.keyWords || []).join(',');
        console.log(this.selectedType || this.media.type);
        var data = {
            name: this.media.name,
            objectID: this.media.objectID,
            author: this.media.author,
            date: this.media.date,
            uid: this.media.uid,
            type: this.selectedType || this.media.type,
            keyWords: this.updateKeyWords
        };

        const response = await axios.put('http://'+ config.host + '/media/update/', data)
        .then(resp => {
            this.media = data
        });

        data.isEdit = 'false';

        this.isEdit = !this.isEdit;
        this.deactivate();
        this.activate(data);
    }
	

	
	
	async addComment() {
		if (this.stars.localeCompare("0") == 0) {
			alert("rating needed to comment on media");
			return;
		}
		console.log("ADD comment");
        var data = {
			mediaID: this.media.objectID, 
            text: document.getElementById("commt").value,
            grade: this.stars,
            publisherID: "594439792",   // ID A MODIFIER
			publisherName: "Camille Moutte"  // A MODIFIER
        };
		this.stars = "0";
    const response = await axios.post('http://'+ config.host + '/comment/add', data);
    this.deactivate();
    this.activate();
  }
	
	async firstStar() {
		this.stars = "1";
		document.getElementById("star1").value = "★";
		document.getElementById("star2").value = "☆";
		document.getElementById("star3").value = "☆";
		document.getElementById("star4").value = "☆";
		document.getElementById("star5").value = "☆";
	}
	async secondStar() {
		this.stars = "2";
		document.getElementById("star1").value = "★";
		document.getElementById("star2").value = "★";
		document.getElementById("star3").value = "☆";
		document.getElementById("star4").value = "☆";
		document.getElementById("star5").value = "☆";
	}
	async thirdStar() {
		this.stars = "3";
		document.getElementById("star1").value = "★";
		document.getElementById("star2").value = "★";
		document.getElementById("star3").value = "★";
		document.getElementById("star4").value = "☆";
		document.getElementById("star5").value = "☆";
	}
	async fourthStar() {
		this.stars = "4";
		document.getElementById("star1").value = "★";
		document.getElementById("star2").value = "★";
		document.getElementById("star3").value = "★";
		document.getElementById("star4").value = "★";
		document.getElementById("star5").value = "☆";
	}
	async fifthStar() {
		this.stars = "5";
		document.getElementById("star1").value = "★";
		document.getElementById("star2").value = "★";
		document.getElementById("star3").value = "★";
		document.getElementById("star4").value = "★";
		document.getElementById("star5").value = "★";
	}
}
