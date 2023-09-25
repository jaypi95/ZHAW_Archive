import java.util.List;

/**
 * The interface JukeBox defines a juke box.
 */
public interface JukeBox {
  
  /**
	 * Adds given song to the playlist.
	 * @param song to add
	 */
	public void addSong(Song song);

	/**
	 * Plays given song.
	 * 
	 * @param songTitle song title to play
	 * @throws JukeBoxException in case if the song does not exist
	 *                          or if it is currently playing 
	 */
	public void playTitle(String songTitle) throws JukeBoxException;

	/**
	 * Returns the song which is currently playing.
	 * @return song object
	 */
	public Song getActualSong();
	
	/**
	 * Returns the playlist.
	 * @return list of all song objects added to this jukebox
	 */
	public List<Song> getPlayList();
}