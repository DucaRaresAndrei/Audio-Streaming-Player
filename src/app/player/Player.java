package app.player;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.user.Artist;
import app.user.ArtistThings.UsingUser;
import app.user.Host;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Player.
 */
public final class Player {
    private String owner;
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    private boolean paused;
    private PlayerSource source;
    @Getter
    private String type;
    private final int skipTime = 90;

    private ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();


    /**
     * Instantiates a new Player.
     *
     * @param owner the owner
     */
    public Player(final String owner) {
        this.owner = owner;
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
    }

    /**
     * Stop using by search.
     */
    public void stopUsingBySearch() {
        Admin admin = Admin.getInstance();
        for (Artist artist : admin.getArtists()) {
            for (UsingUser usingUser : artist.getUsingUsers()) {
                if (usingUser.getUser().getName().equals(owner)) {
                    usingUser.setUsingArtis(false);
                    break;
                }
            }
        }

        for (Host host : admin.getHosts()) {
            for (UsingUser usingUser : host.getUsingUsers()) {
                if (usingUser.getUser().getName().equals(owner)) {
                    usingUser.setUsingArtis(false);
                    break;
                }
            }
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }
        stopUsingBySearch();

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        source = null;
        shuffle = false;
    }

    /**
     *
     */
    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark =
                    new PodcastBookmark(source.getAudioCollection().getName(),
                                        source.getIndex(),
                                        source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    /**
     * Check song using.
     *
     * @param entry the entry
     */
    public void checkSongUsing(final Song entry) {
        String artistName = entry.getArtist();
        Admin admin = Admin.getInstance();

        for (Artist artist : admin.getArtists()) {
            if (artist.getUsername().equals(artistName)) {
                for (UsingUser usingUser : artist.getUsingUsers()) {
                    if (usingUser.getUser().getName().equals(this.owner)) {
                        usingUser.setUsingArtis(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * Check playlist using.
     *
     * @param entry the entry
     */
    public void checkPlaylistUsing(final Playlist entry) {
        for (Song song : entry.getSongs()) {
            checkSongUsing(song);
        }
    }

    /**
     * Check album using.
     *
     * @param entry the entry
     */
    public void checkAlbumUsing(final Album entry) {
        String artistName = entry.getOwner();
        Admin admin = Admin.getInstance();

        for (Artist artist : admin.getArtists()) {
            if (artist.getUsername().equals(artistName)) {
                for (UsingUser usingUser : artist.getUsingUsers()) {
                    if (usingUser.getUser().getName().equals(this.owner)) {
                        usingUser.setUsingArtis(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * Check podcast using.
     *
     * @param entry the entry
     */
    public void checkPodcastUsing(final Podcast entry) {
        String hostName = entry.getOwner();
        Admin admin = Admin.getInstance();

        for (Host host : admin.getHosts()) {
            if (host.getUsername().equals(hostName)) {
                for (UsingUser usingUser : host.getUsingUsers()) {
                    if (usingUser.getUser().getName().equals(this.owner)) {
                        usingUser.setUsingArtis(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * Create source player source.
     *
     * @param newType      the type
     * @param entry        the entry
     * @param newBookmarks the bookmarks
     * @return the player source
     */
    public PlayerSource createSource(final String newType,
                                            final LibraryEntry entry,
                                            final List<PodcastBookmark> newBookmarks) {
        if ("song".equals(newType)) {
            checkSongUsing((Song) entry);
            return new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
        } else if ("playlist".equals(newType)) {
            checkPlaylistUsing((Playlist) entry);
            return new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
        } else if ("podcast".equals(newType)) {
            checkPodcastUsing((Podcast) entry);
            return createPodcastSource((AudioCollection) entry, newBookmarks);
        } else if ("album".equals(newType)) {
            checkAlbumUsing((Album) entry);
            return new PlayerSource(Enums.PlayerSourceType.ALBUM, (AudioCollection) entry);
        }

        return null;
    }

    /**
     *
     */
    private static PlayerSource createPodcastSource(final AudioCollection collection,
                                                    final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }

    /**
     * Sets source.
     *
     * @param entry      the entry
     * @param sourceType the sourceType
     */
    public void setSource(final LibraryEntry entry, final String sourceType) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        this.type = sourceType;
        this.source = createSource(sourceType, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /**
     * Pause.
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Shuffle.
     *
     * @param seed the seed
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST
                || source.getType() == Enums.PlayerSourceType.ALBUM) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }

    /**
     * Repeat enums . repeat mode.
     *
     * @return the enums . repeat mode
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }

    /**
     * Simulate player.
     *
     * @param time the time
     */
    public void simulatePlayer(final int time) {
        int elapsedTime = time;
        if (!paused) {
            if (source == null) {
                return;
            }

            while (elapsedTime >= source.getDuration()) {
                elapsedTime -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-elapsedTime);
            }
        }
    }

    /**
     * Next.
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    /**
     * Prev.
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     * Skip next.
     */
    public void skipNext() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(-skipTime);
        }
    }

    /**
     * Skip prev.
     */
    public void skipPrev() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(skipTime);
        }
    }

    /**
     * Gets current audio file.
     *
     * @return the current audio file
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     * Gets paused.
     *
     * @return the paused
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     * Gets shuffle.
     *
     * @return the shuffle
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     * Gets stats.
     *
     * @return the stats
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }

    /**
     * Gets repeat mode.
     *
     * @return the repeat mode
     */
    public Enums.RepeatMode getRepeatMode() {
        return repeatMode;
    }

    /**
     * Is shuffle boolean.
     *
     * @return the boolean
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /**
     * Is paused boolean.
     *
     * @return the boolean
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public PlayerSource getSource() {
        return source;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
