package remixTheSpire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.audio.MockMusic;

import java.util.concurrent.Callable;

public class MusicInfo implements IMusicInfo
{
    public String Path;
    private final Callable<Boolean> CheckIsEnabled;

    /**
     * Represents a song to play. It is always enabled.
     * @param path The relative path to your music inside your project's resources folder. Must be either a .wav, .mp3, or .ogg file.
     */
    public MusicInfo(String path)
    {
        this(path, () -> true);
    }

    /**
     * Represents a song to play. It is only enabled when isEnabled returns true.
     * @param path The relative path to your music inside your project's resources folder. Must be either a .wav, .mp3, or .ogg file.
     * @param isEnabled Function called to determine whether to include this song as a candidate for replacement. Can be used to dynamically include or exclude this song based on game state or mod configuration.
     */
    public MusicInfo(String path, Callable<Boolean> isEnabled)
    {
        Path = path;
        CheckIsEnabled = isEnabled;
    }

    /**
     * Called immediately before loading new music. Can be used to dynamically include or exclude this song based on game state.
     * @return When true, the song is included as a candidate to replace the base game music. When false, this song is ignored by the music loader.
     */
    public boolean IsEnabled()
    {
        try {
            return CheckIsEnabled.call();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Called to generate a Music object to be played by gdx.
     * @return An instance of gdx.audio.Music to play.
     */
    public Music GetMusic()
    {
        if(Gdx.audio == null)
        {
            return new MockMusic();
        }
        return Gdx.audio.newMusic(Gdx.files.internal(Path));
    }
}
