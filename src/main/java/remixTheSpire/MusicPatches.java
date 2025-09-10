package remixTheSpire;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

public class MusicPatches {
    private static MusicKey GetKey(String key) {
        switch(key)
        {
            case "TheCity":
                return MusicKey.TheCity;
            case "TheBeyond":
                return MusicKey.TheBeyond;
            case "TheEnding":
                return MusicKey.TheEnding;
            case "MENU":
                return MusicKey.MainMenu;
            case "Exordium":
                return MusicKey.Exordium;
            case "SHOP":
                return MusicKey.ShopTheme;
            case "SHRINE":
                return MusicKey.ShrineTheme;
            case "MINDBLOOM":
                return MusicKey.ExordiumBossMindBloom;
            case "BOSS_BOTTOM":
                return MusicKey.ExordiumBoss;
            case "BOSS_CITY":
                return MusicKey.TheCityBoss;
            case "BOSS_BEYOND":
                return MusicKey.TheBeyondBoss;
            case "BOSS_ENDING":
                return MusicKey.HeartBoss;
            case "ELITE":
                return MusicKey.LagavulinTheme;
            case "CREDITS":
                return MusicKey.CreditsTheme;
            case "STS_BossVictoryStinger_1_v3_MUSIC.ogg":
            case "STS_BossVictoryStinger_2_v3_MUSIC.ogg":
            case "STS_BossVictoryStinger_3_v3_MUSIC.ogg":
            case "STS_BossVictoryStinger_4_v3_MUSIC.ogg":
                return MusicKey.BossKilledStinger;
            case "STS_EndingStinger_v1.ogg":
                return MusicKey.HeartKilledStinger;
            case "STS_DeathStinger_1_v3_MUSIC.ogg":
            case "STS_DeathStinger_2_v3_MUSIC.ogg":
            case "STS_DeathStinger_3_v3_MUSIC.ogg":
            case "STS_DeathStinger_4_v3_MUSIC.ogg":
                return MusicKey.GameOverStinger;
            default:
                RemixTheSpire.logger.atError().log("Missing key: {}", key);
                return null;
        }
    }

    @SpirePatch2(clz = com.megacrit.cardcrawl.audio.MainMusic.class,
            method = "getSong")
    public static class MainMusic
    {
        public static SpireReturn<Music> Prefix(String key)
        {
            MusicKey keyEnum = GetKey(key);

            if(keyEnum == null)
            {
                return SpireReturn.Continue();
            }

            return SpireReturn.Return(MusicLoader.GetMainMusic(keyEnum));
        }
    }

    @SpirePatch2(clz = com.megacrit.cardcrawl.audio.TempMusic.class,
            method = "getSong")
    public static class TempMusic
    {
        public static SpireReturn<Music> Prefix(String key)
        {
            MusicKey keyEnum = GetKey(key);

            if(keyEnum == null)
            {
                return SpireReturn.Continue();
            }

            return SpireReturn.Return(MusicLoader.GetMainMusic(keyEnum));
        }
    }
}
