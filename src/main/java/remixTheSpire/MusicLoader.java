package remixTheSpire;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Static class responsible for determining which song to play.
 */
public class MusicLoader
{
    private static boolean isInit = false;
    private static final Random musicRng = new Random();
    private static final HashMap<MusicKey, ArrayList<IMusicInfo>> RegisteredMusic = new HashMap<>();
    private static final HashMap<MusicKey, ArrayList<IMusicInfo>> DefaultMusic = new HashMap<>();

    /**
     * Register a music file to play instead of the base game music.
     * If multiple songs are registered with the same MusicKey, one will be selected at random.
     *
     * @param key Enum representing the base game music to replace.
     * @param info The IMusicInfo to play instead.
     * @return True if the song was registered successfully. False if the song was already registered.
     */
    public static boolean Register(MusicKey key, IMusicInfo info)
    {
        Initialize();
        ArrayList<IMusicInfo> list = RegisteredMusic.get(key);
        if(!list.contains(info))
        {
            list.add(info);
            return true;
        }

        return false;
    }

    /**
     * Remove an IMusicInfo if it was registered previously.
     * @param key Enum representing the base game music to replace.
     * @param info The IMusicInfo to remove.
     * @return True if the IMusicInfo was removed, False if the MusicLoader did not contain an instance of info to remove.
     */
    public static boolean Deregister(MusicKey key, IMusicInfo info)
    {
        Initialize();
        return RegisteredMusic.get(key).remove(info);
    }

    static void Initialize()
    {
        if(isInit)
        {
            return;
        }

        AddKey(MusicKey.Exordium);
        RegisterDefault(MusicKey.Exordium, new MusicInfo("audio/music/STS_Level1_NewMix_v1.ogg"));
        RegisterDefault(MusicKey.Exordium, new MusicInfo("audio/music/STS_Level1-2_v2.ogg"));

        AddKey(MusicKey.TheCity);
        RegisterDefault(MusicKey.TheCity, new MusicInfo("audio/music/STS_Level2_NewMix_v1.ogg"));
        RegisterDefault(MusicKey.TheCity, new MusicInfo("audio/music/STS_Level2-2_v2.ogg"));

        AddKey(MusicKey.TheBeyond);
        RegisterDefault(MusicKey.TheBeyond, new MusicInfo("audio/music/STS_Level3_v2.ogg"));
        RegisterDefault(MusicKey.TheBeyond, new MusicInfo("audio/music/STS_Level3-2_v2.ogg"));

        AddKey(MusicKey.TheEnding);
        RegisterDefault(MusicKey.TheEnding, new MusicInfo("audio/music/STS_Act4_BGM_v2.ogg"));

        AddKey(MusicKey.MainMenu);
        RegisterDefault(MusicKey.MainMenu, new MusicInfo("audio/music/STS_MenuTheme_NewMix_v1.ogg"));

        AddKey(MusicKey.ShopTheme);
        RegisterDefault(MusicKey.ShopTheme, new MusicInfo("audio/music/STS_Merchant_NewMix_v1.ogg"));

        AddKey(MusicKey.ShrineTheme);
        RegisterDefault(MusicKey.ShrineTheme, new MusicInfo("audio/music/STS_Shrine_NewMix_v1.ogg"));

        AddKey(MusicKey.ExordiumBossMindBloom);
        RegisterDefault(MusicKey.ExordiumBossMindBloom, new MusicInfo("audio/music/STS_Boss1MindBloom_v1.ogg"));

        AddKey(MusicKey.ExordiumBoss);
        RegisterDefault(MusicKey.ExordiumBoss, new MusicInfo("audio/music/STS_Boss1_NewMix_v1.ogg"));

        AddKey(MusicKey.TheCityBoss);
        RegisterDefault(MusicKey.TheCityBoss, new MusicInfo("audio/music/STS_Boss2_NewMix_v1.ogg"));

        AddKey(MusicKey.TheBeyondBoss);
        RegisterDefault(MusicKey.TheBeyondBoss, new MusicInfo("audio/music/STS_Boss3_NewMix_v1.ogg"));

        AddKey(MusicKey.HeartBoss);
        RegisterDefault(MusicKey.HeartBoss, new MusicInfo("audio/music/STS_Boss4_v6.ogg"));

        AddKey(MusicKey.LagavulinTheme);
        RegisterDefault(MusicKey.LagavulinTheme, new MusicInfo("audio/music/STS_EliteBoss_NewMix_v1.ogg"));

        AddKey(MusicKey.CreditsTheme);
        RegisterDefault(MusicKey.CreditsTheme, new MusicInfo("audio/music/STS_Credits_v5.ogg"));

        AddKey(MusicKey.BossKilledStinger);
        RegisterDefault(MusicKey.BossKilledStinger, new MusicInfo("audio/music/STS_BossVictoryStinger_1_v3_MUSIC.ogg"));
        RegisterDefault(MusicKey.BossKilledStinger, new MusicInfo("audio/music/STS_BossVictoryStinger_2_v3_MUSIC.ogg"));
        RegisterDefault(MusicKey.BossKilledStinger, new MusicInfo("audio/music/STS_BossVictoryStinger_3_v3_MUSIC.ogg"));
        RegisterDefault(MusicKey.BossKilledStinger, new MusicInfo("audio/music/STS_BossVictoryStinger_4_v3_MUSIC.ogg"));

        AddKey(MusicKey.HeartKilledStinger);
        RegisterDefault(MusicKey.HeartKilledStinger, new MusicInfo("audio/music/STS_EndingStinger_v1.ogg"));

        AddKey(MusicKey.GameOverStinger);
        RegisterDefault(MusicKey.GameOverStinger, new MusicInfo("audio/music/STS_DeathStinger_1_v3_MUSIC.ogg"));
        RegisterDefault(MusicKey.GameOverStinger, new MusicInfo("audio/music/STS_DeathStinger_2_v3_MUSIC.ogg"));
        RegisterDefault(MusicKey.GameOverStinger, new MusicInfo("audio/music/STS_DeathStinger_3_v3_MUSIC.ogg"));
        RegisterDefault(MusicKey.GameOverStinger, new MusicInfo("audio/music/STS_DeathStinger_4_v3_MUSIC.ogg"));

        isInit = true;
    }

    private static void AddKey(MusicKey key)
    {
        RegisteredMusic.put(key, new ArrayList<>());
        DefaultMusic.put(key, new ArrayList<>());
    }

    private static void RegisterDefault(MusicKey key, IMusicInfo info)
    {
        DefaultMusic.get(key).add(info);
    }

    static Music GetMainMusic(MusicKey key)
    {
        Initialize();
        ArrayList<IMusicInfo> musicList = RegisteredMusic.get(key).stream()
                .filter(IMusicInfo::IsEnabled)
                .collect(Collectors.toCollection(ArrayList::new));
        if(musicList.isEmpty())
        {
            ArrayList<IMusicInfo> defaultList = DefaultMusic.get(key);
            return defaultList.get(musicRng.nextInt(defaultList.size())).GetMusic();
        }
        IMusicInfo musicInfo = musicList.get(musicRng.nextInt(musicList.size()));
        return musicInfo.GetMusic();
    }
}
