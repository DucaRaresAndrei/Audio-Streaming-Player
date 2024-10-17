package app.audio.Collections;

import app.Admin;
import app.audio.Files.Song;
import app.user.Artist;
import app.audio.Files.AudioFile;
import java.util.List;


/**
 * The type Album.
 */
public final class Album extends AudioCollection {
    private final List<Song> songs;
    private int timestamp;
    private int releaseYear;
    private String description;

    /**
     * Instantiates a new Album.
     *
     * @param name        the name
     * @param owner       the owner
     * @param timestamp   the timestamp
     * @param releaseYear the release year
     * @param description the description
     * @param songs       the songs
     */
    public Album(final String name, final String owner, final int timestamp, final int releaseYear,
                 final String description, final List<Song> songs) {
        super(name, owner);
        this.songs = songs;
        this.releaseYear = releaseYear;
        this.description = description;
        this.timestamp = timestamp;
    }

    /**
     * Gets artist.
     *
     * @return the artist
     */
    public Artist getArtist() {
        Admin admin = Admin.getInstance();
        for (Artist artist : admin.getArtists()) {
            if (artist.getUsername().equals(getOwner())) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Gets total likes.
     *
     * @return the total likes
     */
    public int getTotalLikes() {
        int totalLikes = 0;

        for (Song song : songs) {
            totalLikes += song.getLikes();
        }

        return totalLikes;
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets release year.
     *
     * @return the release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    public String getOwner() {
        return super.getOwner();
    }

    public String getName() {
        return super.getName();
    }

    /**
     * @return if it matches description
     */
    public boolean matchesDescription(final String otherDescription) {
        return getDescription().toLowerCase().startsWith(otherDescription.toLowerCase());
    }

    /**
     * @return if it matches owner
     */
    public boolean matchesOwner(final String user) {
        return getOwner().toLowerCase().startsWith(user.toLowerCase());
    }
}
