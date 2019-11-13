import config from './config'
import axios from "axios";


export class Media {
  heading = 'List of media!';
    constructor() {
        this.medias = null;
    }

    activate() {
        return this.getMedias();
    }

    async getMedias() {
        const response = await axios.get('http://'+ config.host +'/media/getAll/');
        this.medias = response.data.hits;
        console.log(this.medias);
    }
}