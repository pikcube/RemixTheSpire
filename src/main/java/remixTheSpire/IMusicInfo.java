package remixTheSpire;

import com.badlogic.gdx.audio.Music;

/**
 * Interface representing a modded song.
 */
public interface IMusicInfo {

    /**
     * Called to generate a Music object to be played by gdx.
     * @return An instance of gdx.audio.Music to play.
     */
    Music GetMusic();

    /**
     * Called immediately before loading new music to determine if this song can be patched in. Can be used to dynamically include or exclude this song based on game state.
     * @return When true, the song is included as a candidate to replace the base game music. When false, this song is ignored by the music loader.
     */
    boolean IsEnabled();
}
