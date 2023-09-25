import {Song} from "./lib/jasmine_examples/Song.js";
import { Player } from "./lib/jasmine_examples/Player.js";

function isPlaying() {


    let songTest = new Song("Badr I hardly knew ya")
    let player = new Player()

    player.play(songTest)

    if(player.isPlaying === true){
        return player.currentlyPlayingSong.title
    }
}

console.log(isPlaying())