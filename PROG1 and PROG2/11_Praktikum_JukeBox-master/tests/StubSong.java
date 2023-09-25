public class StubSong implements Song {

    private String songTitle;
    private int flipflop;
    private int count;

    public StubSong(String songTitle){
        this.songTitle = songTitle;
        flipflop = 0;
        count = 0;
    }

    public String getTitle(){
        return songTitle;
    }

    public double getPlayTime(){
        double playTime = 0.0;
        return playTime;
    }

    public boolean isPlaying(){
        if(flipflop == 0) {
            flipflop = 1;
            return false;
        } else {
            flipflop = 0;
            return true;
        }
    }

    public void start() throws JukeBoxException{
        if(count == 0){
            count++;
        } else {
            throw new JukeBoxException("Song already playing");
        }

    }

}
