/**
 * The interface Song defines a song.
 */
public interface Song {
  
	/**
	 * Retruns the title of the song.
	 * @return The title of the song.
	 */
	public String getTitle();
	
	/**
	 * Returns the play time of the song.
	 * @return The play time of the song.
	 */
	public double getPlayTime();
	
	/**
	 * Returns whether the song is playing.
	 * @return True, if the song is playing.
	 */
	public boolean isPlaying();
	
	/**
	 * Starts the song.
	 * @throws JukeBoxException If the song is already playing.
	 */
	public void start() throws JukeBoxException;
}
