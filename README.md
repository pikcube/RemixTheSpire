# Remix The Spire (Beta)

A framework for replacing the music in Slay the Spire.

# Quick Start

Setting up Remix the Spire as adding it as a dependency to your pom.xml file.

```xml
<dependency>
    <groupId>remixTheSpire</groupId>
    <artifactId>remixTheSpire</artifactId>
    <version>0.1.0</version>
    <scope>system</scope>
    <systemPath>[Path To Steam]/steamapps/workshop/content/646570/3565867760/remixTheSpire.jar</systemPath>
</dependency>
```


# Replacing a Song

Replacing a song in the base game is as easy as registering it with the music loader.

```java
MusicLoader.Register(MusicKey.[TRACK], new MusicInfo("[PATH]"));
```

`MusicKey.[TRACK]` is an enum that represents the various audio tracks in the game.

    MusicKey.MainMenu -> Background music that plays on the title screen
    MusicKey.Exordium -> Default background music for Act 1
    MusicKey.TheCity -> Default background music for Act 2
    MusicKey.TheBeyond -> Default background music for Act 3
    MusicKey.TheEnding -> Default background music for Act 4

    MusicKey.ExordiumBoss -> Theme that plays when fighting the boss for Act 1
    MusicKey.TheCityBoss -> Theme that plays when fighting the boss for Act 2
    MusicKey.TheBeyondBoss -> Theme that plays when fighting the boss for Act 3
    MusicKey.HeartBoss -> Theme that plays when fighting The Heart
    
    MusicKey.ShopTheme -> Theme that plays when shopping with the merchant
    MusicKey.ShrineTheme -> Theme that plays during Shrine events or when opening a boss chest
    MusicKey.LagavulinTheme -> Theme that plays after waking Lagavulin
    MusicKey.ExordiumBossMindBloom -> Theme that plays when fighting an Act 1 Boss during Mind Bloom
    MusicKey.CreditsTheme -> Theme that plays during the credits
    MusicKey.BossKilledStinger -> Theme that plays after beating an Act
    MusicKey.HeartKilledStinger -> Theme that plays after beating the heart
    MusicKey.GameOverStinger -> Theme that plays when the player dies

`[PATH]` is the path to the audio file relative to your project's resources directory. The audio file can be an ogg, mp3, or wav file.

# Adding Multiple Audio Files

Multiple audio files can be registered under the same key to allow for track randomization.

```java
MusicLoader.Register(MusicKey.Exordium, new MusicInfo("theme1.ogg");
MusicLoader.Register(MusicKey.Exordium, new MusicInfo("theme2.ogg");
```

This works even if multiple mods attempt to modify the same track in the game. All music mods will be placed into the track list and one will be randomly selected when the track is called.

# Conditional Playback

You can control which audio file is played using conditional playback. Simply pass a function to the MusicInfo constructor that returns true when the song may be played and false when the song shouldn't be played.

```java
MusicInfo info = new MusicInfo("theme1.ogg", () -> IsTheme1Enabled);
MusicLoader.Register(MusicKey.Exordium, info);
```

You may pass any function that takes no arguments and returns a boolean to the constructor. Before swapping the audio track, the music loader will call your function to determine if this track is eligible to replace your existing audio track. If your function returns false (or throws an exception), your song will not be played.

Your function is only called when queuing up the next audio file. It does not affect the currently playing song. Additionally, if multiple songs are enabled for the same music track, one will be selected at random.

# Removing an audio file

You can permanently remove an audio file by calling `Deregister`.

```java
MusicLoader.Deregister(MusicKey.[TRACK], [MUSICINFO]);
```

Audio files are removed by reference, so store a reference to any musicinfo you want to remove.

# The IMusicInfo Interface

If you want to play audio that isn't an ogg, mp3, or wav file, you can create your own class that implements the IMusicInfo Interface.

Your class must implement a function `GetMusic()` that returns a object implementing the `gdx.audio.Music`interface. Information about libGDX audio can be found [here.](https://libgdx.com/wiki/audio/streaming-music). It also needs to implement a function `IsEnabled()` that returns true when the audio file should be played by the Music Loader.
